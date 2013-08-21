/**
 * @classDescription The base function which all other series types inherit from. The data in the series is stored
 * in various arrays.
 *
 * - First, series.options.data contains all the original config options for
 * each point whether added by options or methods like series.addPoint.
 * - Next, series.data contains those values converted to points, but in case the series data length
 * exceeds the cropThreshold, or if the data is grouped, series.data doesn't contain all the points. It
 * only contains the points that have been created on demand.
 * - Then there's series.points that contains all currently visible point objects. In case of cropping,
 * the cropped-away points are not part of this array. The series.points array starts at series.cropStart
 * compared to series.data and series.options.data. If however the series data is grouped, these can't
 * be correlated one to one.
 * - series.xData and series.processedXData contain clean x values, equivalent to series.data and series.points.
 * - series.yData and series.processedYData contain clean x values, equivalent to series.data and series.points.
 *
 * @param {Object} chart
 * @param {Object} options
 */
$FC.Series = function () {};

$FC.Series.prototype = {

	isCartesian: true,
	type: 'line',
	pointClass: $FC.Point,
	sorted: true, // requires the data to be sorted
	requireSorting: true,
	pointAttrToOptions: { // mapping between SVG attributes and the corresponding options
		stroke: 'lineColor',
		'stroke-width': 'lineWidth',
		fill: 'fillColor',
		r: 'radius'
	},
	colorCounter: 0,
	init: function (chart, options) {
		var series = this,
			eventType,
			events,
			linkedTo,
			chartSeries = chart.series;

		series.chart = chart;
		series.options = options = series.setOptions(options); // merge with plotOptions

		// bind the axes
		series.bindAxes();

		// set some variables
		$FC.extend(series, {
			name: options.name,
			state: $FC.NORMAL_STATE,
			pointAttr: {},
			visible: options.visible !== false, // true by default
			selected: options.selected === true // false by default
		});
		
		// special
		if ($FC.useCanVG) {
			options.animation = false;
		}

		// register event listeners
		events = options.events;
		for (eventType in events) {
			addEvent(series, eventType, events[eventType]);
		}
		if (
			(events && events.click) ||
			(options.point && options.point.events && options.point.events.click) ||
			options.allowPointSelect
		) {
			chart.runTrackerClick = true;
		}

		series.getColor();
		series.getSymbol();

		// set the data
		series.setData(options.data, false);
		
		// Mark cartesian
		if (series.isCartesian) {
			chart.hasCartesianSeries = true;
		}

		// Register it in the chart
		chartSeries.push(series);
		series._i = chartSeries.length - 1;
		
		// Sort series according to index option (#248, #1123)
		$FC.stableSort(chartSeries, function (a, b) {
			return $FC.pick(a.options.index, a._i) - $FC.pick(b.options.index, a._i);
		});
		$FC.each(chartSeries, function (series, i) {
			series.index = i;
			series.name = series.name || 'Series ' + (i + 1);
		});

		// Linked series
		linkedTo = options.linkedTo;
		series.linkedSeries = [];
		if ($FC.isString(linkedTo)) {
			if (linkedTo === ':previous') {
				linkedTo = chartSeries[series.index - 1];
			} else {
				linkedTo = chart.get(linkedTo);
			}
			if (linkedTo) {
				linkedTo.linkedSeries.push(series);
				series.linkedParent = linkedTo;
			}
		}
	},
	
	/**
	 * Set the xAxis and yAxis properties of cartesian series, and register the series
	 * in the axis.series array
	 */
	bindAxes: function () {
		var series = this,
			seriesOptions = series.options,
			chart = series.chart,
			axisOptions;
			
		if (series.isCartesian) {
			
			$FC.each(['xAxis', 'yAxis'], function (AXIS) { // repeat for xAxis and yAxis
				
				$FC.each(chart[AXIS], function (axis) { // loop through the chart's axis objects
					
					axisOptions = axis.options;
					
					// apply if the series xAxis or yAxis option mathches the number of the 
					// axis, or if undefined, use the first axis
					if ((seriesOptions[AXIS] === axisOptions.index) ||
							(seriesOptions[AXIS] !== $FC.UNDEFINED && seriesOptions[AXIS] === axisOptions.id) ||
							(seriesOptions[AXIS] === $FC.UNDEFINED && axisOptions.index === 0)) {
						
						// register this series in the axis.series lookup
						axis.series.push(series);
						
						// set this series.xAxis or series.yAxis reference
						series[AXIS] = axis;
						
						// mark dirty for redraw
						axis.isDirty = true;
					}
				});

				// The series needs an X and an Y axis
				if (!series[AXIS]) {
					error(18, true);
				}

			});
		}
	},


	/**
	 * Return an auto incremented x value based on the pointStart and pointInterval options.
	 * This is only used if an x value is not given for the point that calls autoIncrement.
	 */
	autoIncrement: function () {
		var series = this,
			options = series.options,
			xIncrement = series.xIncrement;

		xIncrement = $FC.pick(xIncrement, options.pointStart, 0);

		series.pointInterval = $FC.pick(series.pointInterval, options.pointInterval, 1);

		series.xIncrement = xIncrement + series.pointInterval;
		return xIncrement;
	},

	/**
	 * Divide the series data into segments divided by null values.
	 */
	getSegments: function () {
		var series = this,
			lastNull = -1,
			segments = [],
			i,
			points = series.points,
			pointsLength = points.length;

		if (pointsLength) { // no action required for []
			
			// if connect nulls, just remove null points
			if (series.options.connectNulls) {
				i = pointsLength;
				while (i--) {
					if (points[i].y === null) {
						points.splice(i, 1);
					}
				}
				if (points.length) {
					segments = [points];
				}
				
			// else, split on null points
			} else {
				$FC.each(points, function (point, i) {
					if (point.y === null) {
						if (i > lastNull + 1) {
							segments.push(points.slice(lastNull + 1, i));
						}
						lastNull = i;
					} else if (i === pointsLength - 1) { // last value
						segments.push(points.slice(lastNull + 1, i + 1));
					}
				});
			}
		}
		
		// register it
		series.segments = segments;
	},
	
	/**
	 * Set the series options by merging from the options tree
	 * @param {Object} itemOptions
	 */
	setOptions: function (itemOptions) {
		var chart = this.chart,
			chartOptions = chart.options,
			plotOptions = chartOptions.plotOptions,
			typeOptions = plotOptions[this.type],
			options;

		this.userOptions = itemOptions;

		options = $FC.merge(
			typeOptions,
			plotOptions.series,
			itemOptions
		);
		
		// the tooltip options are merged between global and series specific options
		this.tooltipOptions = $FC.merge(chartOptions.tooltip, options.tooltip);
		
		// Delte marker object if not allowed (#1125)
		if (typeOptions.marker === null) {
			delete options.marker;
		}
		
		return options;

	},
	/**
	 * Get the series' color
	 */
	getColor: function () {
		var options = this.options,
			userOptions = this.userOptions,
			defaultColors = this.chart.options.colors,
			counters = this.chart.counters,
			color,
			colorIndex;

		color = options.color || $FC.defaultPlotOptions[this.type].color;

		if (!color && !options.colorByPoint) {
			if ($FC.defined(userOptions._colorIndex)) { // after Series.update()
				colorIndex = userOptions._colorIndex;
			} else {
				userOptions._colorIndex = counters.color;
				colorIndex = counters.color++;
			}
			color = defaultColors[colorIndex];
		}
		
		this.color = color;
		counters.wrapColor(defaultColors.length);
	},
	/**
	 * Get the series' symbol
	 */
	getSymbol: function () {
		var series = this,
			userOptions = series.userOptions,
			seriesMarkerOption = series.options.marker,
			chart = series.chart,
			defaultSymbols = chart.options.symbols,
			counters = chart.counters,
			symbolIndex;

		series.symbol = seriesMarkerOption.symbol;
		if (!series.symbol) {
			if ($FC.defined(userOptions._symbolIndex)) { // after Series.update()
				symbolIndex = userOptions._symbolIndex;
			} else {
				userOptions._symbolIndex = counters.symbol;
				symbolIndex = counters.symbol++;
			}
			series.symbol = defaultSymbols[symbolIndex];
		}

		// don't substract radius in image symbols (#604)
		if (/^url/.test(series.symbol)) {
			seriesMarkerOption.radius = 0;
		}
		counters.wrapSymbol(defaultSymbols.length);
	},

	/**
	 * Get the series' symbol in the legend. This method should be overridable to create custom 
	 * symbols through Highcharts.seriesTypes[type].prototype.drawLegendSymbols.
	 * 
	 * @param {Object} legend The legend object
	 */
	drawLegendSymbol: function (legend) {
		
		var options = this.options,
			markerOptions = options.marker,
			radius,
			legendOptions = legend.options,
			legendSymbol,
			symbolWidth = legendOptions.symbolWidth,
			renderer = this.chart.renderer,
			legendItemGroup = this.legendGroup,
			verticalCenter = legend.baseline - $FC.mathRound(renderer.fontMetrics(legendOptions.itemStyle.fontSize).b * 0.3),
			attr;
			
		// Draw the line
		if (options.lineWidth) {
			attr = {
				'stroke-width': options.lineWidth
			};
			if (options.dashStyle) {
				attr.dashstyle = options.dashStyle;
			}
			this.legendLine = renderer.path([
				M,
				0,
				verticalCenter,
				L,
				symbolWidth,
				verticalCenter
			])
			.attr(attr)
			.add(legendItemGroup);
		}
		
		// Draw the marker
		if (markerOptions && markerOptions.enabled) {
			radius = markerOptions.radius;
			this.legendSymbol = legendSymbol = renderer.symbol(
				this.symbol,
				(symbolWidth / 2) - radius,
				verticalCenter - radius,
				2 * radius,
				2 * radius
			)
			.add(legendItemGroup);
			legendSymbol.isMarker = true;
		}
	},

	/**
	 * Add a point dynamically after chart load time
	 * @param {Object} options Point options as given in series.data
	 * @param {Boolean} redraw Whether to redraw the chart or wait for an explicit call
	 * @param {Boolean} shift If shift is true, a point is shifted off the start
	 *    of the series as one is appended to the end.
	 * @param {Boolean|Object} animation Whether to apply animation, and optionally animation
	 *    configuration
	 */
	addPoint: function (options, redraw, shift, animation) {
		var series = this,
			seriesOptions = series.options,
			data = series.data,
			graph = series.graph,
			area = series.area,
			chart = series.chart,
			xData = series.xData,
			yData = series.yData,
			zData = series.zData,
			names = series.names,
			currentShift = (graph && graph.shift) || 0,
			dataOptions = seriesOptions.data,
			point;

		setAnimation(animation, chart);

		// Make graph animate sideways
		if (shift) {
			$FC.each([graph, area, series.graphNeg, series.areaNeg], function (shape) {
				if (shape) {
					shape.shift = currentShift + 1;
				}
			});
		}
		if (area) {
			area.isArea = true; // needed in animation, both with and without shift
		}
		
		// Optional redraw, defaults to true
		redraw = $FC.pick(redraw, true);

		// Get options and push the point to xData, yData and series.options. In series.generatePoints
		// the Point instance will be created on demand and pushed to the series.data array.
		point = { series: series };
		series.pointClass.prototype.applyOptions.apply(point, [options]);
		xData.push(point.x);
		yData.push(series.toYData ? series.toYData(point) : point.y);
		zData.push(point.z);
		if (names) {
			names[point.x] = point.name;
		}
		dataOptions.push(options);

		// Generate points to be added to the legend (#1329) 
		if (seriesOptions.legendType === 'point') {
			series.generatePoints();
		}

		// Shift the first point off the parallel arrays
		// todo: consider series.removePoint(i) method
		if (shift) {
			if (data[0] && data[0].remove) {
				data[0].remove(false);
			} else {
				data.shift();
				xData.shift();
				yData.shift();
				zData.shift();
				dataOptions.shift();
			}
		}

		// redraw
		series.isDirty = true;
		series.isDirtyData = true;
		if (redraw) {
			series.getAttribs(); // #1937
			chart.redraw();
		}
	},

	/**
	 * Replace the series data with a new set of data
	 * @param {Object} data
	 * @param {Object} redraw
	 */
	setData: function (data, redraw) {
		var series = this,
			oldData = series.points,
			options = series.options,
			chart = series.chart,
			firstPoint = null,
			xAxis = series.xAxis,
			names = xAxis && xAxis.categories && !xAxis.categories.length ? [] : null,
			i;

		// reset properties
		series.xIncrement = null;
		series.pointRange = xAxis && xAxis.categories ? 1 : options.pointRange;

		series.colorCounter = 0; // for series with colorByPoint (#1547)
		
		// parallel arrays
		var xData = [],
			yData = [],
			zData = [],
			dataLength = data ? data.length : [],
			turboThreshold = $FC.pick(options.turboThreshold, 1000), // docs: 0 to disable
			pt,
			pointArrayMap = series.pointArrayMap,
			valueCount = pointArrayMap && pointArrayMap.length,
			hasToYData = !!series.toYData;

		// In turbo mode, only one- or twodimensional arrays of numbers are allowed. The
		// first value is tested, and we assume that all the rest are defined the same
		// way. Although the 'for' loops are similar, they are repeated inside each
		// if-else conditional for max performance.
		if (turboThreshold && dataLength > turboThreshold) { 
			
			// find the first non-null point
			i = 0;
			while (firstPoint === null && i < dataLength) {
				firstPoint = data[i];
				i++;
			}
		
		
			if ($FC.isNumber(firstPoint)) { // assume all points are numbers
				var x = $FC.pick(options.pointStart, 0),
					pointInterval = $FC.pick(options.pointInterval, 1);

				for (i = 0; i < dataLength; i++) {
					xData[i] = x;
					yData[i] = data[i];
					x += pointInterval;
				}
				series.xIncrement = x;
			} else if (isArray(firstPoint)) { // assume all points are arrays
				if (valueCount) { // [x, low, high] or [x, o, h, l, c]
					for (i = 0; i < dataLength; i++) {
						pt = data[i];
						xData[i] = pt[0];
						yData[i] = pt.slice(1, valueCount + 1);
					}
				} else { // [x, y]
					for (i = 0; i < dataLength; i++) {
						pt = data[i];
						xData[i] = pt[0];
						yData[i] = pt[1];
					}
				}
			} /* else {
				error(12); // Highcharts expects configs to be numbers or arrays in turbo mode
			}*/
		} else {
			for (i = 0; i < dataLength; i++) {
				if (data[i] !== $FC.UNDEFINED) { // stray commas in oldIE
					pt = { series: series };
					series.pointClass.prototype.applyOptions.apply(pt, [data[i]]);
					xData[i] = pt.x;
					yData[i] = hasToYData ? series.toYData(pt) : pt.y;
					zData[i] = pt.z;
					if (names && pt.name) {
						names[i] = pt.name;
					}
				}
			}
		}
		
		// Forgetting to cast strings to numbers is a common caveat when handling CSV or JSON		
		if ($FC.isString(yData[0])) {
			error(14, true);
		} 

		series.data = [];
		series.options.data = data;
		series.xData = xData;
		series.yData = yData;
		series.zData = zData;
		series.names = names;

		// destroy old points
		i = (oldData && oldData.length) || 0;
		while (i--) {
			if (oldData[i] && oldData[i].destroy) {
				oldData[i].destroy();
			}
		}

		// reset minRange (#878)
		if (xAxis) {
			xAxis.minRange = xAxis.userMinRange;
		}

		// redraw
		series.isDirty = series.isDirtyData = chart.isDirtyBox = true;
		if ($FC.pick(redraw, true)) {
			chart.redraw(false);
		}
	},

	/**
	 * Remove a series and optionally redraw the chart
	 *
	 * @param {Boolean} redraw Whether to redraw the chart or wait for an explicit call
	 * @param {Boolean|Object} animation Whether to apply animation, and optionally animation
	 *    configuration
	 */

	remove: function (redraw, animation) {
		var series = this,
			chart = series.chart;
		redraw = $FC.pick(redraw, true);

		if (!series.isRemoving) {  /* prevent triggering native event in jQuery
				(calling the remove function from the remove event) */
			series.isRemoving = true;

			// fire the event with a default handler of removing the point
			fireEvent(series, 'remove', null, function () {


				// destroy elements
				series.destroy();


				// redraw
				chart.isDirtyLegend = chart.isDirtyBox = true;
				if (redraw) {
					chart.redraw(animation);
				}
			});

		}
		series.isRemoving = false;
	},

	/**
	 * Process the data by cropping away unused data points if the series is longer
	 * than the crop threshold. This saves computing time for lage series.
	 */
	processData: function (force) {
		var series = this,
			processedXData = series.xData, // copied during slice operation below
			processedYData = series.yData,
			dataLength = processedXData.length,
			croppedData,
			cropStart = 0,
			cropped,
			distance,
			closestPointRange,
			xAxis = series.xAxis,
			i, // loop variable
			options = series.options,
			cropThreshold = options.cropThreshold,
			isCartesian = series.isCartesian,
			dataExtremes = {};

		// If the series data or axes haven't changed, don't go through this. Return false to pass
		// the message on to override methods like in data grouping. 
		if (isCartesian && !series.isDirty && !xAxis.isDirty && !series.yAxis.isDirty && !force) {
			return false;
		}
		

		// optionally filter out points outside the plot area
		if (isCartesian && series.sorted && (!cropThreshold || dataLength > cropThreshold || series.forceCrop)) {
			var extremes = xAxis.getExtremes(),
				min = extremes.min,
				max = extremes.max;

			// it's outside current extremes
			if (processedXData[dataLength - 1] < min || processedXData[0] > max) {
				processedXData = [];
				processedYData = [];
			
			// only crop if it's actually spilling out
			} else if (processedXData[0] < min || processedXData[dataLength - 1] > max) {
				croppedData = this.cropData(series.xData, series.yData, min, max);
				processedXData = croppedData.xData;
				processedYData = croppedData.yData;
				cropStart = croppedData.start;
				cropped = true;
			}
		}
		
		
		// Find the closest distance between processed points
		for (i = processedXData.length - 1; i >= 0; i--) {
			distance = processedXData[i] - processedXData[i - 1];
			if (distance > 0 && (closestPointRange === $FC.UNDEFINED || distance < closestPointRange)) {
				closestPointRange = distance;

			// Unsorted data is not supported by the line tooltip, as well as data grouping and 
			// navigation in Stock charts (#725) and width calculation of columns (#1900)
			} else if (distance < 0 && series.requireSorting) {
				error(15);
			}

			// don't search dataMin/dataMax again for cropped data
			if (!cropped && processedYData) {
				series.getDataMinMax(processedYData[i], dataExtremes);
			}
		}
		
		// Record the properties
		series.cropped = cropped; // undefined or true
		series.cropStart = cropStart;
		series.processedXData = processedXData;
		series.processedYData = processedYData;
		series.dataMax = cropped ? croppedData.dataMax : dataExtremes.max;
		series.dataMin = cropped ? croppedData.dataMin : dataExtremes.min;

		if (options.pointRange === null) { // null means auto, as for columns, candlesticks and OHLC
			series.pointRange = closestPointRange || 1;
		}
		series.closestPointRange = closestPointRange;
		
	},

	/**
	 * Iterate over xData and crop values between min and max. Returns object containing crop start/end
	 * cropped xData with corresponding part of yData, dataMin and dataMax within the cropped range
	 */
	cropData: function (xData, yData, min, max) {
		var series = this,
			dataLength = xData.length,
			cropStart = 0,
			cropEnd = dataLength,
			dataExtremes = {},
			i;

		// iterate up to find slice start
		for (i = 0; i < dataLength; i++) {
			if (xData[i] >= min) {
				cropStart = $FC.mathMax(0, i - 1);
				break;
			}
		}

		// proceed to find slice end
		for (; i < dataLength; i++) {
			series.getDataMinMax(yData[i], dataExtremes);

			if (xData[i] > max) {
				cropEnd = i + 1;
				break;
			}
		}

		return {
			xData: xData.slice(cropStart, cropEnd),
			yData: yData.slice(cropStart, cropEnd),
			dataMin: dataExtremes.min,
			dataMax: dataExtremes.max,
			start: cropStart,
			end: cropEnd
		};
	},
	
	/**
	 * Process single yData point, find dataMin/dataMax and store them in the object referenced by 2nd argument
	 */
	getDataMinMax: function (y, e) {
		var j;

		// exit if y is null
		if (y === null || y === $FC.UNDEFINED) { return; }

		// handle arrays, like OHLC or range points
		j = y.length;

		if (j) {
			while (j--) {
				// handle null values in arrays
				if (y[j] !== null) {
					// find extreme values or set current if dataMin or dataMax is null
					if (y[j] < e.min || e.min === $FC.UNDEFINED) { e.min = y[j]; }
					if (y[j] > e.max || e.max === $FC.UNDEFINED) { e.max = y[j]; }
				}
			}
		} else {
			// set default values
			if (e.min === $FC.UNDEFINED) { e.min = y; }
			if (e.max === $FC.UNDEFINED) { e.max = y; }
			
			// set extremes
			if (e.min > y) { e.min = y; }
			if (e.max < y) { e.max = y; }
		}
	},

	/**
	 * Generate the data point after the data has been processed by cropping away
	 * unused points and optionally grouped in Highcharts Stock.
	 */
	generatePoints: function () {
		var series = this,
			options = series.options,
			dataOptions = options.data,
			data = series.data,
			dataLength,
			processedXData = series.processedXData,
			processedYData = series.processedYData,
			pointClass = series.pointClass,
			processedDataLength = processedXData.length,
			cropStart = series.cropStart || 0,
			cursor,
			hasGroupedData = series.hasGroupedData,
			point,
			points = [],
			i;

		if (!data && !hasGroupedData) {
			var arr = [];
			arr.length = dataOptions.length;
			data = series.data = arr;
		}

		for (i = 0; i < processedDataLength; i++) {
			cursor = cropStart + i;
			if (!hasGroupedData) {
				if (data[cursor]) {
					point = data[cursor];
				} else if (dataOptions[cursor] !== $FC.UNDEFINED) { // #970
					data[cursor] = point = (new pointClass()).init(series, dataOptions[cursor], processedXData[i]);
				}
				points[i] = point;
			} else {
				// splat the y data in case of ohlc data array
				points[i] = (new pointClass()).init(series, [processedXData[i]].concat(splat(processedYData[i])));
			}
		}

		// Hide cropped-away points - this only runs when the number of points is above cropThreshold, or when
		// swithching view from non-grouped data to grouped data (#637)	
		if (data && (processedDataLength !== (dataLength = data.length) || hasGroupedData)) {
			for (i = 0; i < dataLength; i++) {
				if (i === cropStart && !hasGroupedData) { // when has grouped data, clear all points
					i += processedDataLength;
				}
				if (data[i]) {
					data[i].destroyElements();
					data[i].plotX = $FC.UNDEFINED; // #1003
				}
			}
		}

		series.data = data;
		series.points = points;
	},

	/**
	 * Adds series' points value to corresponding stack
	 */
	setStackedPoints: function (isLast) {
		if (!this.options.stacking || (this.visible !== true && this.chart.options.chart.ignoreHiddenSeries !== false)) {
			return;
		}

		var series = this,
			xData = series.processedXData,
			yData = series.processedYData,
			yDataLength = yData.length,
			seriesOptions = series.options,
			threshold = seriesOptions.threshold,
			stackOption = seriesOptions.stack,
			stacking = seriesOptions.stacking,
			stackKey = series.stackKey,
			negKey = '-' + stackKey,
			yAxis = series.yAxis,
			xAxis = series.xAxis,
			stacks = yAxis.stacks,
			oldStacks = yAxis.oldStacks,
			cropped = series.cropped,
			stacksMax = yAxis.stacksMax,
			xExtremes = xAxis.getExtremes(),
			isNegative,
			total,
			stack,
			prev,
			key,
			i,
			x,
			y;

		if (isLast) {
			prev = {};
			prev[stackKey] = 0;
			prev[negKey] = 0;
		}

		// loop over the non-null y values and read them into a local array
		for (i = 0; i < yDataLength; i++) {
			x = xData[i];
			y = yData[i];

			// Read stacked values into a stack based on the x value,
			// the sign of y and the stack key. Stacking is also handled for null values (#739)
			isNegative = y < threshold;
			key = isNegative ? negKey : stackKey;

			// Set default stacksMax value for this stack
			if (!stacksMax[key]) {
				stacksMax[key] = y;
			}

			// Create empty object for this stack if it doesn't exist yet
			if (!stacks[key]) {
				stacks[key] = {};
			}

			// Initialize StackItem for this x
			if (oldStacks[key] && oldStacks[key][x]) {
				stacks[key][x] = oldStacks[key][x];
				stacks[key][x].total = null;
			} else if (!stacks[key][x]) {
				stacks[key][x] = new StackItem(yAxis, yAxis.options.stackLabels, isNegative, x, stackOption, stacking);
			}

			// If the StackItem doesn't exist, create it first
			if (series.getExtremesFromAll || cropped || ((xData[i + 1] || x) >= xExtremes.min && (xData[i - 1] || x) <= xExtremes.max)) {
				stack = stacks[key][x];
				total = stack.total;


				// add value to the stack total
				stack.addValue(y);

				stack.cacheExtremes(series, [total, total + y]);

				if (isLast) {
					stack.addValue(prev[key]);
				}



				if (stack.total > stacksMax[key] && !isNegative) {
					stacksMax[key] = stack.total;
				} else if (stack.total < stacksMax[key] && isNegative) {
					stacksMax[key] = stack.total;
				}

				if (isLast) {
					prev[key] = total;
				}
			}
		}

		// reset old stacks
		yAxis.oldStacks = {};
	},

	/**
	 * Calculate x and y extremes for visible data
	 */
	getExtremes: function () {
		var series = this,
			dataMax = series.dataMax,
			dataMin = series.dataMin,
			xData = series.processedXData,
			yData = series.processedYData,
			xAxis = series.xAxis,
			xExtremes = xAxis.getExtremes(),
			croppedData,
			onSeries;

		// handle flag series
		if (series.options.onSeries) {
			onSeries = series.chart.get(series.options.onSeries);
			return onSeries.getExtremes();
		}

		if (!series.cropped) {
			croppedData = series.cropData(xData, yData, xExtremes.min, xExtremes.max);
			dataMax = croppedData.dataMax;
			dataMin = croppedData.dataMin;
		}

		return {
			dataMax: dataMax,
			dataMin: dataMin
		};
	},

	/**
	 * Translate data points from raw data values to chart specific positioning data
	 * needed later in drawPoints, drawGraph and drawTracker.
	 */
	translate: function () {
		if (!this.processedXData) { // hidden series
			this.processData();
		}
		this.generatePoints();
		var series = this,
			options = series.options,
			stacking = options.stacking,
			xAxis = series.xAxis,
			categories = xAxis.categories,
			yAxis = series.yAxis,
			points = series.points,
			dataLength = points.length,
			hasModifyValue = !!series.modifyValue,
			i,
			pointPlacement = options.pointPlacement, // docs: accept numbers
			dynamicallyPlaced = pointPlacement === 'between' || $FC.isNumber(pointPlacement),
			threshold = options.threshold;

		
		// Translate each point
		for (i = 0; i < dataLength; i++) {
			var point = points[i],
				xValue = point.x,
				yValue = point.y,
				yBottom = point.low,
				stack = yAxis.stacks[(yValue < threshold ? '-' : '') + series.stackKey],
				pointStack,
				pointStackTotal;

			// Discard disallowed y values for log axes
			if (yAxis.isLog && yValue <= 0) {
				point.y = yValue = null;
			}
			
			// Get the plotX translation
			point.plotX = xAxis.translate(xValue, 0, 0, 0, 1, pointPlacement); // Math.round fixes #591

			// Calculate the bottom y value for stacked series
			if (stacking && series.visible && stack && stack[xValue]) {


				pointStack = stack[xValue];
				pointStackTotal = pointStack.total;
				pointStack.cum = yBottom = pointStack.cum - yValue; // start from top
				yValue = yBottom + yValue;
				
				if (pointStack.cum === 0) {
					yBottom = $FC.pick(threshold, yAxis.min);
				}
				
				if (yAxis.isLog && yBottom <= 0) { // #1200, #1232
					yBottom = null;
				}
				
				if (stacking === 'percent') {
					yBottom = pointStackTotal ? yBottom * 100 / pointStackTotal : 0;
					yValue = pointStackTotal ? yValue * 100 / pointStackTotal : 0;
				}

				point.percentage = pointStackTotal ? point.y * 100 / pointStackTotal : 0;
				point.total = point.stackTotal = pointStackTotal;
				point.stackY = yValue;

				// Place the stack label
				pointStack.setOffset(series.pointXOffset || 0, series.barW || 0);
				
			}

			// Set translated yBottom or remove it
			point.yBottom = $FC.defined(yBottom) ? 
				yAxis.translate(yBottom, 0, 1, 0, 1) :
				null;
			
			// general hook, used for Highstock compare mode
			if (hasModifyValue) {
				yValue = series.modifyValue(yValue, point);
			}

			// Set the the plotY value, reset it for redraws
			point.plotY = (typeof yValue === 'number' && yValue !== Infinity) ? 
				$FC.mathRound(yAxis.translate(yValue, 0, 1, 0, 1) * 10) / 10 : // Math.round fixes #591
				$FC.UNDEFINED;
			
			// Set client related positions for mouse tracking
			point.clientX = dynamicallyPlaced ? xAxis.translate(xValue, 0, 0, 0, 1) : point.plotX; // #1514
				
			point.negative = point.y < (threshold || 0);

			// some API data
			point.category = categories && categories[point.x] !== $FC.UNDEFINED ?
				categories[point.x] : point.x;


		}

		// now that we have the cropped data, build the segments
		series.getSegments();
	},
	/**
	 * Memoize tooltip texts and positions
	 */
	setTooltipPoints: function (renew) {
		var series = this,
			points = [],
			pointsLength,
			low,
			high,
			xAxis = series.xAxis,
			axisLength = xAxis ? (xAxis.tooltipLen || xAxis.len) : series.chart.plotSizeX, // tooltipLen and tooltipPosName used in polar
			point,
			nextPoint,
			i,
			tooltipPoints = []; // a lookup array for each pixel in the x dimension

		// don't waste resources if tracker is disabled
		if (series.options.enableMouseTracking === false) {
			return;
		}

		// renew
		if (renew) {
			series.tooltipPoints = null;
		}

		// concat segments to overcome null values
		$FC.each(series.segments || series.points, function (segment) {
			points = points.concat(segment);
		});

		// Reverse the points in case the X axis is reversed
		if (xAxis && xAxis.reversed) {
			points = points.reverse();
		}

		// Polar needs additional shaping
		if (series.orderTooltipPoints) {
			series.orderTooltipPoints(points);
		}

		// Assign each pixel position to the nearest point
		pointsLength = points.length;
		for (i = 0; i < pointsLength; i++) {
			point = points[i];
			nextPoint = points[i + 1];
			
			// Set this range's low to the last range's high plus one
			low = points[i - 1] ? high + 1 : 0;
			// Now find the new high
			high = points[i + 1] ?
				$FC.mathMax(0, $FC.mathFloor((point.clientX + (nextPoint ? (nextPoint.wrappedClientX || nextPoint.clientX) : axisLength)) / 2)) :
				axisLength;

			while (low >= 0 && low <= high) {
				tooltipPoints[low++] = point;
			}
		}
		series.tooltipPoints = tooltipPoints;
	},

	/**
	 * Format the header of the tooltip
	 */
	tooltipHeaderFormatter: function (point) {
		var series = this,
			tooltipOptions = series.tooltipOptions,
			xDateFormat = tooltipOptions.xDateFormat,
			dateTimeLabelFormats = tooltipOptions.dateTimeLabelFormats,
			xAxis = series.xAxis,
			isDateTime = xAxis && xAxis.options.type === 'datetime',
			headerFormat = tooltipOptions.headerFormat,
			closestPointRange = xAxis && xAxis.closestPointRange,
			n;
			
		// Guess the best date format based on the closest point distance (#568)
		if (isDateTime && !xDateFormat) {
			if (closestPointRange) {
				for (n in timeUnits) {
					if (timeUnits[n] >= closestPointRange) {
						xDateFormat = dateTimeLabelFormats[n];
						break;
					}
				}
			} else {
				xDateFormat = dateTimeLabelFormats.day;
			}
		}
		
		// Insert the header date format if any
		if (isDateTime && xDateFormat && $FC.isNumber(point.key)) {
			headerFormat = headerFormat.replace('{point.key}', '{point.key:' + xDateFormat + '}');
		}
		
		return $FC.format(headerFormat, {
			point: point,
			series: series
		});
	},

	/**
	 * Series mouse over handler
	 */
	onMouseOver: function () {
		var series = this,
			chart = series.chart,
			hoverSeries = chart.hoverSeries;

		// set normal state to previous series
		if (hoverSeries && hoverSeries !== series) {
			hoverSeries.onMouseOut();
		}

		// trigger the event, but to save processing time,
		// only if defined
		if (series.options.events.mouseOver) {
			fireEvent(series, 'mouseOver');
		}

		// hover this
		series.setState($FC.HOVER_STATE);
		chart.hoverSeries = series;
	},

	/**
	 * Series mouse out handler
	 */
	onMouseOut: function () {
		// trigger the event only if listeners exist
		var series = this,
			options = series.options,
			chart = series.chart,
			tooltip = chart.tooltip,
			hoverPoint = chart.hoverPoint;

		// trigger mouse out on the point, which must be in this series
		if (hoverPoint) {
			hoverPoint.onMouseOut();
		}

		// fire the mouse out event
		if (series && options.events.mouseOut) {
			fireEvent(series, 'mouseOut');
		}


		// hide the tooltip
		if (tooltip && !options.stickyTracking && (!tooltip.shared || series.noSharedTooltip)) {
			tooltip.hide();
		}

		// set normal state
		series.setState();
		chart.hoverSeries = null;
	},

	/**
	 * Animate in the series
	 */
	animate: function (init) {
		var series = this,
			chart = series.chart,
			renderer = chart.renderer,
			clipRect,
			markerClipRect,
			animation = series.options.animation,
			clipBox = chart.clipBox,
			inverted = chart.inverted,
			sharedClipKey;

		// Animation option is set to true
		if (animation && !$FC.isObject(animation)) {
			animation = defaultPlotOptions[series.type].animation;
		}
		sharedClipKey = '_sharedClip' + animation.duration + animation.easing;

		// Initialize the animation. Set up the clipping rectangle.
		if (init) { 
			
			// If a clipping rectangle with the same properties is currently present in the chart, use that. 
			clipRect = chart[sharedClipKey];
			markerClipRect = chart[sharedClipKey + 'm'];
			if (!clipRect) {
				chart[sharedClipKey] = clipRect = renderer.clipRect(
					$FC.extend(clipBox, { width: 0 })
				);
				
				chart[sharedClipKey + 'm'] = markerClipRect = renderer.clipRect(
					-99, // include the width of the first marker
					inverted ? -chart.plotLeft : -chart.plotTop, 
					99,
					inverted ? chart.chartWidth : chart.chartHeight
				);
			}
			series.group.clip(clipRect);
			series.markerGroup.clip(markerClipRect);
			series.sharedClipKey = sharedClipKey;

		// Run the animation
		} else { 
			clipRect = chart[sharedClipKey];
			if (clipRect) {
				clipRect.animate({
					width: chart.plotSizeX
				}, animation);
				chart[sharedClipKey + 'm'].animate({
					width: chart.plotSizeX + 99
				}, animation);
			}

			// Delete this function to allow it only once
			series.animate = null;
			
			// Call the afterAnimate function on animation complete (but don't overwrite the animation.complete option
			// which should be available to the user).
			series.animationTimeout = setTimeout(function () {
				series.afterAnimate();
			}, animation.duration);
		}
	},
	
	/**
	 * This runs after animation to land on the final plot clipping
	 */
	afterAnimate: function () {
		var chart = this.chart,
			sharedClipKey = this.sharedClipKey,
			group = this.group;
			
		if (group && this.options.clip !== false) {
			group.clip(chart.clipRect);
			this.markerGroup.clip(); // no clip
		}
		
		// Remove the shared clipping rectancgle when all series are shown		
		setTimeout(function () {
			if (sharedClipKey && chart[sharedClipKey]) {
				chart[sharedClipKey] = chart[sharedClipKey].destroy();
				chart[sharedClipKey + 'm'] = chart[sharedClipKey + 'm'].destroy();
			}
		}, 100);
	},

	/**
	 * Draw the markers
	 */
	drawPoints: function () {
		var series = this,
			pointAttr,
			points = series.points,
			chart = series.chart,
			plotX,
			plotY,
			i,
			point,
			radius,
			symbol,
			isImage,
			graphic,
			options = series.options,
			seriesMarkerOptions = options.marker,
			pointMarkerOptions,
			enabled,
			isInside,
			markerGroup = series.markerGroup;

		if (seriesMarkerOptions.enabled || series._hasPointMarkers) {
			
			i = points.length;
			while (i--) {
				point = points[i];
				plotX = $FC.mathFloor(point.plotX); // #1843
				plotY = point.plotY;
				graphic = point.graphic;
				pointMarkerOptions = point.marker || {};
				enabled = (seriesMarkerOptions.enabled && pointMarkerOptions.enabled === $FC.UNDEFINED) || pointMarkerOptions.enabled;
				isInside = chart.isInsidePlot($FC.mathRound(plotX), plotY, chart.inverted); // #1858
				
				// only draw the point if y is defined
				if (enabled && plotY !== $FC.UNDEFINED && !isNaN(plotY) && point.y !== null) {

					// shortcuts
					pointAttr = point.pointAttr[point.selected ? $FC.SELECT_STATE : $FC.NORMAL_STATE];
					radius = pointAttr.r;
					symbol = $FC.pick(pointMarkerOptions.symbol, series.symbol);
					isImage = symbol.indexOf('url') === 0;

					if (graphic) { // update
						graphic
							.attr({ // Since the marker group isn't clipped, each individual marker must be toggled
								visibility: isInside ? ($FC.hasSVG ? 'inherit' : $FC.VISIBLE) : HIDDEN
							})
							.animate($FC.extend({
								x: plotX - radius,
								y: plotY - radius
							}, graphic.symbolName ? { // don't apply to image symbols #507
								width: 2 * radius,
								height: 2 * radius
							} : {}));
					} else if (isInside && (radius > 0 || isImage)) {
						point.graphic = graphic = chart.renderer.symbol(
							symbol,
							plotX - radius,
							plotY - radius,
							2 * radius,
							2 * radius
						)
						.attr(pointAttr)
						.add(markerGroup);
					}
					
				} else if (graphic) {
					point.graphic = graphic.destroy(); // #1269
				}
			}
		}

	},

	/**
	 * Convert state properties from API naming conventions to SVG attributes
	 *
	 * @param {Object} options API options object
	 * @param {Object} base1 SVG attribute object to inherit from
	 * @param {Object} base2 Second level SVG attribute object to inherit from
	 */
	convertAttribs: function (options, base1, base2, base3) {
		var conversion = this.pointAttrToOptions,
			attr,
			option,
			obj = {};

		options = options || {};
		base1 = base1 || {};
		base2 = base2 || {};
		base3 = base3 || {};

		for (attr in conversion) {
			option = conversion[attr];
			obj[attr] = $FC.pick(options[option], base1[attr], base2[attr], base3[attr]);
		}
		return obj;
	},

	/**
	 * Get the state attributes. Each series type has its own set of attributes
	 * that are allowed to change on a point's state change. Series wide attributes are stored for
	 * all series, and additionally point specific attributes are stored for all
	 * points with individual marker options. If such options are not defined for the point,
	 * a reference to the series wide attributes is stored in point.pointAttr.
	 */
	getAttribs: function () {
		var series = this,
			seriesOptions = series.options,
			normalOptions = $FC.defaultPlotOptions[series.type].marker ? seriesOptions.marker : seriesOptions,
			stateOptions = normalOptions.states,
			stateOptionsHover = stateOptions[$FC.HOVER_STATE],
			pointStateOptionsHover,
			seriesColor = series.color,
			normalDefaults = {
				stroke: seriesColor,
				fill: seriesColor
			},
			points = series.points || [], // #927
			i,
			point,
			seriesPointAttr = [],
			pointAttr,
			pointAttrToOptions = series.pointAttrToOptions,
			hasPointSpecificOptions,
			negativeColor = seriesOptions.negativeColor,
			key;

		// series type specific modifications
		if (seriesOptions.marker) { // line, spline, area, areaspline, scatter

			// if no hover radius is given, default to normal radius + 2
			stateOptionsHover.radius = stateOptionsHover.radius || normalOptions.radius + 2;
			stateOptionsHover.lineWidth = stateOptionsHover.lineWidth || normalOptions.lineWidth + 1;
			
		} else { // column, bar, pie

			// if no hover color is given, brighten the normal color
			stateOptionsHover.color = stateOptionsHover.color ||
				Color(stateOptionsHover.color || seriesColor)
					.brighten(stateOptionsHover.brightness).get();
		}

		// general point attributes for the series normal state
		seriesPointAttr[$FC.NORMAL_STATE] = series.convertAttribs(normalOptions, normalDefaults);

		// HOVER_STATE and SELECT_STATE states inherit from normal state except the default radius
		$FC.each([$FC.HOVER_STATE, $FC.SELECT_STATE], function (state) {
			seriesPointAttr[state] =
					series.convertAttribs(stateOptions[state], seriesPointAttr[$FC.NORMAL_STATE]);
		});

		// set it
		series.pointAttr = seriesPointAttr;


		// Generate the point-specific attribute collections if specific point
		// options are given. If not, create a referance to the series wide point
		// attributes
		i = points.length;
		while (i--) {
			point = points[i];
			normalOptions = (point.options && point.options.marker) || point.options;
			if (normalOptions && normalOptions.enabled === false) {
				normalOptions.radius = 0;
			}
			
			if (point.negative && negativeColor) {
				point.color = point.fillColor = negativeColor;
			}
			
			hasPointSpecificOptions = seriesOptions.colorByPoint || point.color; // #868
			
			// check if the point has specific visual options
			if (point.options) {
				for (key in pointAttrToOptions) {
					if ($FC.defined(normalOptions[pointAttrToOptions[key]])) {
						hasPointSpecificOptions = true;
					}
				}
			}

			// a specific marker config object is defined for the individual point:
			// create it's own attribute collection
			if (hasPointSpecificOptions) {
				normalOptions = normalOptions || {};
				pointAttr = [];
				stateOptions = normalOptions.states || {}; // reassign for individual point
				pointStateOptionsHover = stateOptions[HOVER_STATE] = stateOptions[HOVER_STATE] || {};

				// Handle colors for column and pies
				if (!seriesOptions.marker) { // column, bar, point
					// if no hover color is given, brighten the normal color
					pointStateOptionsHover.color =
						Color(pointStateOptionsHover.color || point.color)
							.brighten(pointStateOptionsHover.brightness ||
								stateOptionsHover.brightness).get();

				}

				// normal point state inherits series wide normal state
				pointAttr[$FC.NORMAL_STATE] = series.convertAttribs($FC.extend({
					color: point.color // #868
				}, normalOptions), seriesPointAttr[$FC.NORMAL_STATE]);

				// inherit from point normal and series hover
				pointAttr[$FC.HOVER_STATE] = series.convertAttribs(
					stateOptions[$FC.HOVER_STATE],
					seriesPointAttr[$FC.HOVER_STATE],
					pointAttr[$FC.NORMAL_STATE]
				);
				
				// inherit from point normal and series hover
				pointAttr[$FC.SELECT_STATE] = series.convertAttribs(
					stateOptions[$FC.SELECT_STATE],
					seriesPointAttr[$FC.SELECT_STATE],
					pointAttr[$FC.NORMAL_STATE]
				);

				// Force the fill to negativeColor on markers
				if (point.negative && seriesOptions.marker && negativeColor) {
					pointAttr[$FC.NORMAL_STATE].fill = pointAttr[$FC.HOVER_STATE].fill = pointAttr[$FC.SELECT_STATE].fill = 
						series.convertAttribs({ fillColor: negativeColor }).fill;
				}


			// no marker config object is created: copy a reference to the series-wide
			// attribute collection
			} else {
				pointAttr = seriesPointAttr;
			}

			point.pointAttr = pointAttr;

		}

	},
	/**
	 * Update the series with a new set of options
	 */
	update: function (newOptions, redraw) {
		var chart = this.chart,
			// must use user options when changing type because this.options is merged
			// in with type specific plotOptions
			oldOptions = this.userOptions,
			oldType = this.type;

		// Do the merge, with some forced options
		newOptions = $FC.merge(oldOptions, {
			animation: false,
			index: this.index,
			pointStart: this.xData[0] // when updating after addPoint
		}, { data: this.options.data }, newOptions);

		// Destroy the series and reinsert methods from the type prototype
		this.remove(false);
		$FC.extend(this, seriesTypes[newOptions.type || oldType].prototype);
		

		this.init(chart, newOptions);
		if ($FC.pick(redraw, true)) {
			chart.redraw(false);
		}
	},

	/**
	 * Clear DOM objects and free up memory
	 */
	destroy: function () {
		var series = this,
			chart = series.chart,
			issue134 = /AppleWebKit\/533/.test(userAgent),
			destroy,
			i,
			data = series.data || [],
			point,
			prop,
			axis;

		// add event hook
		fireEvent(series, 'destroy');

		// remove all events
		removeEvent(series);
		
		// erase from axes
		$FC.each(['xAxis', 'yAxis'], function (AXIS) {
			axis = series[AXIS];
			if (axis) {
				erase(axis.series, series);
				axis.isDirty = axis.forceRedraw = true;
			}
		});

		// remove legend items
		if (series.legendItem) {
			series.chart.legend.destroyItem(series);
		}

		// destroy all points with their elements
		i = data.length;
		while (i--) {
			point = data[i];
			if (point && point.destroy) {
				point.destroy();
			}
		}
		series.points = null;

		// Clear the animation timeout if we are destroying the series during initial animation
		clearTimeout(series.animationTimeout);

		// destroy all SVGElements associated to the series
		$FC.each(['area', 'graph', 'dataLabelsGroup', 'group', 'markerGroup', 'tracker',
				'graphNeg', 'areaNeg', 'posClip', 'negClip'], function (prop) {
			if (series[prop]) {

				// issue 134 workaround
				destroy = issue134 && prop === 'group' ?
					'hide' :
					'destroy';

				series[prop][destroy]();
			}
		});

		// remove from hoverSeries
		if (chart.hoverSeries === series) {
			chart.hoverSeries = null;
		}
		erase(chart.series, series);

		// clear all members
		for (prop in series) {
			delete series[prop];
		}
	},

	/**
	 * Draw the data labels
	 */
	drawDataLabels: function () {
		
		var series = this,
			seriesOptions = series.options,
			options = seriesOptions.dataLabels,
			points = series.points,
			pointOptions,
			generalOptions,
			str,
			dataLabelsGroup;
		
		if (options.enabled || series._hasPointLabels) {
						
			// Process default alignment of data labels for columns
			if (series.dlProcessOptions) {
				series.dlProcessOptions(options);
			}

			// Create a separate group for the data labels to avoid rotation
			dataLabelsGroup = series.plotGroup(
				'dataLabelsGroup', 
				'data-labels', 
				series.visible ? VISIBLE : HIDDEN, 
				options.zIndex || 6
			);
			
			// Make the labels for each point
			generalOptions = options;
			$FC.each(points, function (point) {
				
				var enabled,
					dataLabel = point.dataLabel,
					labelConfig,
					attr,
					name,
					rotation,
					connector = point.connector,
					isNew = true;
				
				// Determine if each data label is enabled
				pointOptions = point.options && point.options.dataLabels;
				enabled = generalOptions.enabled || (pointOptions && pointOptions.enabled);
				
				
				// If the point is outside the plot area, destroy it. #678, #820
				if (dataLabel && !enabled) {
					point.dataLabel = dataLabel.destroy();
				
				// Individual labels are disabled if the are explicitly disabled 
				// in the point options, or if they fall outside the plot area.
				} else if (enabled) {
					
					// Create individual options structure that can be extended without 
					// affecting others
					options = $FC.merge(generalOptions, pointOptions);

					rotation = options.rotation;
					
					// Get the string
					labelConfig = point.getLabelConfig();
					str = options.format ?
						$FC.format(options.format, labelConfig) : 
						options.formatter.call(labelConfig, options);
					
					// Determine the color
					options.style.color = $FC.pick(options.color, options.style.color, series.color, 'black');
	
					
					// update existing label
					if (dataLabel) {
						
						if ($FC.defined(str)) {
							dataLabel
								.attr({
									text: str
								});
							isNew = false;
						
						} else { // #1437 - the label is shown conditionally
							point.dataLabel = dataLabel = dataLabel.destroy();
							if (connector) {
								point.connector = connector.destroy();
							}
						}
						
					// create new label
					} else if ($FC.defined(str)) {
						attr = {
							//align: align,
							fill: options.backgroundColor,
							stroke: options.borderColor,
							'stroke-width': options.borderWidth,
							r: options.borderRadius || 0,
							rotation: rotation,
							padding: options.padding,
							zIndex: 1
						};
						// Remove unused attributes (#947)
						for (name in attr) {
							if (attr[name] === $FC.UNDEFINED) {
								delete attr[name];
							}
						}
						
						dataLabel = point.dataLabel = series.chart.renderer[rotation ? 'text' : 'label']( // labels don't support rotation
							str,
							0,
							-999,
							null,
							null,
							null,
							options.useHTML
						)
						.attr(attr)
						.css(options.style)
						.add(dataLabelsGroup)
						.shadow(options.shadow);
						
					}
					
					if (dataLabel) {
						// Now the data label is created and placed at 0,0, so we need to align it
						series.alignDataLabel(point, dataLabel, options, null, isNew);
					}
				}
			});
		}
	},
	
	/**
	 * Align each individual data label
	 */
	alignDataLabel: function (point, dataLabel, options, alignTo, isNew) {
		var chart = this.chart,
			inverted = chart.inverted,
			plotX = $FC.pick(point.plotX, -999),
			plotY = $FC.pick(point.plotY, -999),
			bBox = dataLabel.getBBox(),
			alignAttr; // the final position;
				
		// The alignment box is a singular point
		alignTo = $FC.extend({
			x: inverted ? chart.plotWidth - plotY : plotX,
			y: $FC.mathRound(inverted ? chart.plotHeight - plotX : plotY),
			width: 0,
			height: 0
		}, alignTo);
		
		// Add the text size for alignment calculation
		$FC.extend(options, {
			width: bBox.width,
			height: bBox.height
		});

		// Allow a hook for changing alignment in the last moment, then do the alignment
		if (options.rotation) { // Fancy box alignment isn't supported for rotated text
			alignAttr = {
				align: options.align,
				x: alignTo.x + options.x + alignTo.width / 2,
				y: alignTo.y + options.y + alignTo.height / 2
			};
			dataLabel[isNew ? 'attr' : 'animate'](alignAttr);
		} else {
			dataLabel.align(options, null, alignTo);
			alignAttr = dataLabel.alignAttr;
		}
		
		// Show or hide based on the final aligned position
		dataLabel.attr({
			visibility: options.crop === false ||
					(chart.isInsidePlot(alignAttr.x, alignAttr.y) && chart.isInsidePlot(alignAttr.x + bBox.width, alignAttr.y + bBox.height)) ?
				(chart.renderer.isSVG ? 'inherit' : VISIBLE) : 
				HIDDEN
		});
				
	},
	
	/**
	 * Return the graph path of a segment
	 */
	getSegmentPath: function (segment) {		
		var series = this,
			segmentPath = [],
			step = series.options.step;
			
		// build the segment line
		$FC.each(segment, function (point, i) {
			
			var plotX = point.plotX,
				plotY = point.plotY,
				lastPoint;

			if (series.getPointSpline) { // generate the spline as defined in the SplineSeries object
				segmentPath.push.apply(segmentPath, series.getPointSpline(segment, point, i));

			} else {

				// moveTo or lineTo
				segmentPath.push(i ? $FC.L : $FC.M);

				// step line?
				if (step && i) {
					lastPoint = segment[i - 1];
					if (step === 'right') {
						segmentPath.push(
							lastPoint.plotX,
							plotY
						);
						
					} else if (step === 'center') {
						segmentPath.push(
							(lastPoint.plotX + plotX) / 2,
							lastPoint.plotY,
							(lastPoint.plotX + plotX) / 2,
							plotY
						);
						
					} else {
						segmentPath.push(
							plotX,
							lastPoint.plotY
						);
					}
				}

				// normal line to next point
				segmentPath.push(
					point.plotX,
					point.plotY
				);
			}
		});
		
		return segmentPath;
	},

	/**
	 * Get the graph path
	 */
	getGraphPath: function () {
		var series = this,
			graphPath = [],
			segmentPath,
			singlePoints = []; // used in drawTracker

		// Divide into segments and build graph and area paths
		$FC.each(series.segments, function (segment) {
			
			segmentPath = series.getSegmentPath(segment);
			
			// add the segment to the graph, or a single point for tracking
			if (segment.length > 1) {
				graphPath = graphPath.concat(segmentPath);
			} else {
				singlePoints.push(segment[0]);
			}
		});

		// Record it for use in drawGraph and drawTracker, and return graphPath
		series.singlePoints = singlePoints;
		series.graphPath = graphPath;
		
		return graphPath;
		
	},
	
	/**
	 * Draw the actual graph
	 */
	drawGraph: function () {
		var series = this,
			options = this.options,
			props = [['graph', options.lineColor || this.color]],
			lineWidth = options.lineWidth,
			dashStyle =  options.dashStyle,
			graphPath = this.getGraphPath(),
			negativeColor = options.negativeColor;
			
		if (negativeColor) {
			props.push(['graphNeg', negativeColor]);
		}
		
		// draw the graph
		$FC.each(props, function (prop, i) {
			var graphKey = prop[0],
				graph = series[graphKey],
				attribs;
			
			if (graph) {
				stop(graph); // cancel running animations, #459
				graph.animate({ d: graphPath });
	
			} else if (lineWidth && graphPath.length) { // #1487
				attribs = {
					stroke: prop[1],
					'stroke-width': lineWidth,
					zIndex: 1 // #1069
				};
				if (dashStyle) {
					attribs.dashstyle = dashStyle;
				}

				series[graphKey] = series.chart.renderer.path(graphPath)
					.attr(attribs)
					.add(series.group)
					.shadow(!i && options.shadow);
			}
		});
	},
	
	/**
	 * Clip the graphs into the positive and negative coloured graphs
	 */
	clipNeg: function () {
		var options = this.options,
			chart = this.chart,
			renderer = chart.renderer,
			negativeColor = options.negativeColor || options.negativeFillColor,
			translatedThreshold,
			posAttr,
			negAttr,
			graph = this.graph,
			area = this.area,
			posClip = this.posClip,
			negClip = this.negClip,
			chartWidth = chart.chartWidth,
			chartHeight = chart.chartHeight,
			chartSizeMax = $FC.mathMax(chartWidth, chartHeight),
			yAxis = this.yAxis,
			above,
			below;
		
		if (negativeColor && (graph || area)) {
			translatedThreshold = $FC.mathRound(yAxis.toPixels(options.threshold || 0, true));
			above = {
				x: 0,
				y: 0,
				width: chartSizeMax,
				height: translatedThreshold
			};
			below = {
				x: 0,
				y: translatedThreshold,
				width: chartSizeMax,
				height: chartSizeMax
			};
			
			if (chart.inverted) {

				above.height = below.y = chart.plotWidth - translatedThreshold;
				if (renderer.isVML) {
					above = {
						x: chart.plotWidth - translatedThreshold - chart.plotLeft,
						y: 0,
						width: chartWidth,
						height: chartHeight
					};
					below = {
						x: translatedThreshold + chart.plotLeft - chartWidth,
						y: 0,
						width: chart.plotLeft + translatedThreshold,
						height: chartWidth
					};
				}
			}
			
			if (yAxis.reversed) {
				posAttr = below;
				negAttr = above;
			} else {
				posAttr = above;
				negAttr = below;
			}
		
			if (posClip) { // update
				posClip.animate(posAttr);
				negClip.animate(negAttr);
			} else {
				
				this.posClip = posClip = renderer.clipRect(posAttr);
				this.negClip = negClip = renderer.clipRect(negAttr);
				
				if (graph && this.graphNeg) {
					graph.clip(posClip);
					this.graphNeg.clip(negClip);	
				}
				
				if (area) {
					area.clip(posClip);
					this.areaNeg.clip(negClip);
				} 
			} 
		}	
	},

	/**
	 * Initialize and perform group inversion on series.group and series.markerGroup
	 */
	invertGroups: function () {
		var series = this,
			chart = series.chart;

		// Pie, go away (#1736)
		if (!series.xAxis) {
			return;
		}
		
		// A fixed size is needed for inversion to work
		function setInvert() {			
			var size = {
				width: series.yAxis.len,
				height: series.xAxis.len
			};
			
			$FC.each(['group', 'markerGroup'], function (groupName) {
				if (series[groupName]) {
					series[groupName].attr(size).invert();
				}
			});
		}

		addEvent(chart, 'resize', setInvert); // do it on resize
		addEvent(series, 'destroy', function () {
			removeEvent(chart, 'resize', setInvert);
		});

		// Do it now
		setInvert(); // do it now
		
		// On subsequent render and redraw, just do setInvert without setting up events again
		series.invertGroups = setInvert;
	},
	
	/**
	 * General abstraction for creating plot groups like series.group, series.dataLabelsGroup and 
	 * series.markerGroup. On subsequent calls, the group will only be adjusted to the updated plot size.
	 */
	plotGroup: function (prop, name, visibility, zIndex, parent) {
		var group = this[prop],
			isNew = !group;
		
		// Generate it on first call
		if (isNew) {	
			this[prop] = group = this.chart.renderer.g(name)
				.attr({
					visibility: visibility,
					zIndex: zIndex || 0.1 // IE8 needs this
				})
				.add(parent);
		}
		// Place it on first and subsequent (redraw) calls
		group[isNew ? 'attr' : 'animate'](this.getPlotBox());
		return group;		
	},

	/**
	 * Get the translation and scale for the plot area of this series
	 */
	getPlotBox: function () {
		return {
			translateX: this.xAxis ? this.xAxis.left : this.chart.plotLeft, 
			translateY: this.yAxis ? this.yAxis.top : this.chart.plotTop,
			scaleX: 1, // #1623
			scaleY: 1
		};
	},
	
	/**
	 * Render the graph and markers
	 */
	render: function () {
		var series = this,
			chart = series.chart,
			group,
			options = series.options,
			animation = options.animation,
			doAnimation = animation && !!series.animate && 
				chart.renderer.isSVG, // this animation doesn't work in IE8 quirks when the group div is hidden,
				// and looks bad in other oldIE
			visibility = series.visible ? $FC.VISIBLE : $FC.HIDDEN,
			zIndex = options.zIndex,
			hasRendered = series.hasRendered,
			chartSeriesGroup = chart.seriesGroup;
		
		// the group
		group = series.plotGroup(
			'group', 
			'series', 
			visibility, 
			zIndex, 
			chartSeriesGroup
		);
		
		series.markerGroup = series.plotGroup(
			'markerGroup', 
			'markers', 
			visibility, 
			zIndex, 
			chartSeriesGroup
		);
		
		// initiate the animation
		if (doAnimation) {
			series.animate(true);
		}

		// cache attributes for shapes
		series.getAttribs();

		// SVGRenderer needs to know this before drawing elements (#1089, #1795)
		group.inverted = series.isCartesian ? chart.inverted : false;
		
		// draw the graph if any
		if (series.drawGraph) {
			series.drawGraph();
			series.clipNeg();
		}

		// draw the data labels (inn pies they go before the points)
		series.drawDataLabels();
		
		// draw the points
		series.drawPoints();


		// draw the mouse tracking area
		if (series.options.enableMouseTracking !== false) {
			series.drawTracker();
		}
		
		// Handle inverted series and tracker groups
		if (chart.inverted) {
			series.invertGroups();
		}
		
		// Initial clipping, must be defined after inverting groups for VML
		if (options.clip !== false && !series.sharedClipKey && !hasRendered) {
			group.clip(chart.clipRect);
		}

		// Run the animation
		if (doAnimation) {
			series.animate();
		} else if (!hasRendered) {
			series.afterAnimate();
		}

		series.isDirty = series.isDirtyData = false; // means data is in accordance with what you see
		// (See #322) series.isDirty = series.isDirtyData = false; // means data is in accordance with what you see
		series.hasRendered = true;
	},
	
	/**
	 * Redraw the series after an update in the axes.
	 */
	redraw: function () {
		var series = this,
			chart = series.chart,
			wasDirtyData = series.isDirtyData, // cache it here as it is set to false in render, but used after
			group = series.group,
			xAxis = series.xAxis,
			yAxis = series.yAxis;

		// reposition on resize
		if (group) {
			if (chart.inverted) {
				group.attr({
					width: chart.plotWidth,
					height: chart.plotHeight
				});
			}

			group.animate({
				translateX: $FC.pick(xAxis && xAxis.left, chart.plotLeft),
				translateY: $FC.pick(yAxis && yAxis.top, chart.plotTop)
			});
		}

		series.translate();
		series.setTooltipPoints(true);

		series.render();
		if (wasDirtyData) {
			fireEvent(series, 'updatedData');
		}
	},

	/**
	 * Set the state of the graph
	 */
	setState: function (state) {
		var series = this,
			options = series.options,
			graph = series.graph,
			graphNeg = series.graphNeg,
			stateOptions = options.states,
			lineWidth = options.lineWidth,
			attribs;

		state = state || $FC.NORMAL_STATE;

		if (series.state !== state) {
			series.state = state;

			if (stateOptions[state] && stateOptions[state].enabled === false) {
				return;
			}

			if (state) {
				lineWidth = stateOptions[state].lineWidth || lineWidth + 1;
			}

			if (graph && !graph.dashstyle) { // hover is turned off for dashed lines in VML
				attribs = {
					'stroke-width': lineWidth
				};
				// use attr because animate will cause any other animation on the graph to stop
				graph.attr(attribs);
				if (graphNeg) {
					graphNeg.attr(attribs);
				}
			}
		}
	},

	/**
	 * Set the visibility of the graph
	 *
	 * @param vis {Boolean} True to show the series, false to hide. If UNDEFINED,
	 *        the visibility is toggled.
	 */
	setVisible: function (vis, redraw) {
		var series = this,
			chart = series.chart,
			legendItem = series.legendItem,
			showOrHide,
			ignoreHiddenSeries = chart.options.chart.ignoreHiddenSeries,
			oldVisibility = series.visible;

		// if called without an argument, toggle visibility
		series.visible = vis = series.userOptions.visible = vis === $FC.UNDEFINED ? !oldVisibility : vis;
		showOrHide = vis ? 'show' : 'hide';

		// show or hide elements
		$FC.each(['group', 'dataLabelsGroup', 'markerGroup', 'tracker'], function (key) {
			if (series[key]) {
				series[key][showOrHide]();
			}
		});

		
		// hide tooltip (#1361)
		if (chart.hoverSeries === series) {
			series.onMouseOut();
		}


		if (legendItem) {
			chart.legend.colorizeItem(series, vis);
		}


		// rescale or adapt to resized chart
		series.isDirty = true;
		// in a stack, all other series are affected
		if (series.options.stacking) {
			$FC.each(chart.series, function (otherSeries) {
				if (otherSeries.options.stacking && otherSeries.visible) {
					otherSeries.isDirty = true;
				}
			});
		}

		// show or hide linked series
		$FC.each(series.linkedSeries, function (otherSeries) {
			otherSeries.setVisible(vis, false);
		});

		if (ignoreHiddenSeries) {
			chart.isDirtyBox = true;
		}
		if (redraw !== false) {
			chart.redraw();
		}

		fireEvent(series, showOrHide);
	},

	/**
	 * Show the graph
	 */
	show: function () {
		this.setVisible(true);
	},

	/**
	 * Hide the graph
	 */
	hide: function () {
		this.setVisible(false);
	},


	/**
	 * Set the selected state of the graph
	 *
	 * @param selected {Boolean} True to select the series, false to unselect. If
	 *        UNDEFINED, the selection state is toggled.
	 */
	select: function (selected) {
		var series = this;
		// if called without an argument, toggle
		series.selected = selected = (selected === $FC.UNDEFINED) ? !series.selected : selected;

		if (series.checkbox) {
			series.checkbox.checked = selected;
		}

		fireEvent(series, selected ? 'select' : 'unselect');
	},

	/**
	 * Draw the tracker object that sits above all data labels and markers to
	 * track mouse events on the graph or points. For the line type charts
	 * the tracker uses the same graphPath, but with a greater stroke width
	 * for better control.
	 */
	drawTracker: function () {
		var series = this,
			options = series.options,
			trackByArea = options.trackByArea,
			trackerPath = [].concat(trackByArea ? series.areaPath : series.graphPath),
			trackerPathLength = trackerPath.length,
			chart = series.chart,
			pointer = chart.pointer,
			renderer = chart.renderer,
			snap = chart.options.tooltip.snap,
			tracker = series.tracker,
			cursor = options.cursor,
			css = cursor && { cursor: cursor },
			singlePoints = series.singlePoints,
			singlePoint,
			i,
			onMouseOver = function () {
				if (chart.hoverSeries !== series) {
					series.onMouseOver();
				}
			};

		// Extend end points. A better way would be to use round linecaps,
		// but those are not clickable in VML.
		if (trackerPathLength && !trackByArea) {
			i = trackerPathLength + 1;
			while (i--) {
				if (trackerPath[i] === $FC.M) { // extend left side
					trackerPath.splice(i + 1, 0, trackerPath[i + 1] - snap, trackerPath[i + 2], $FC.L);
				}
				if ((i && trackerPath[i] === $FC.M) || i === trackerPathLength) { // extend right side
					trackerPath.splice(i, 0, $FC.L, trackerPath[i - 2] + snap, trackerPath[i - 1]);
				}
			}
		}

		// handle single points
		for (i = 0; i < singlePoints.length; i++) {
			singlePoint = singlePoints[i];
			trackerPath.push($FC.M, singlePoint.plotX - snap, singlePoint.plotY,
				L, singlePoint.plotX + snap, singlePoint.plotY);
		}
		
		

		// draw the tracker
		if (tracker) {
			tracker.attr({ d: trackerPath });

		} else { // create
				
			series.tracker = tracker = renderer.path(trackerPath)
				.attr({
					'class': $FC.PREFIX + 'tracker',
					'stroke-linejoin': 'round', // #1225
					visibility: series.visible ? $FC.VISIBLE : $FC.HIDDEN,
					stroke: $FC.TRACKER_FILL,
					fill: trackByArea ? $FC.TRACKER_FILL : $FC.NONE,
					'stroke-width' : options.lineWidth + (trackByArea ? 0 : 2 * snap),
					zIndex: 2
				})
				.addClass($FC.PREFIX + 'tracker')
				.on('mouseover', onMouseOver)
				.on('mouseout', function (e) { pointer.onTrackerMouseOut(e); })
				.css(css)
				.add(series.markerGroup);
				
			if ($FC.hasTouch) {
				tracker.on('touchstart', onMouseOver);
			} 
		}

	}

}; // end Series prototype