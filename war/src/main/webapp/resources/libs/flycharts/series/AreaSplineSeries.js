/**
 * Set the default options for areaspline
 */
$FC.defaultPlotOptions.areaspline = $FC.merge($FC.defaultPlotOptions.area);

/**
 * AreaSplineSeries object
 */
$FC.AreaSplineSeries = $FC.extendClass($FC.SplineSeries, {
		type: 'areaspline',
		closedStacks: true, // instead of following the previous graph back, follow the threshold back
		
		// Mix in methods from the area series
		getSegmentPath: $FC.AreaSeries.prototype.getSegmentPath,
		closeSegment: $FC.AreaSeries.prototype.closeSegment,
		drawGraph: $FC.AreaSeries.prototype.drawGraph,
		drawLegendSymbol: $FC.AreaSeries.prototype.drawLegendSymbol
	});
$FC.seriesTypes.areaspline = $FC.AreaSplineSeries;