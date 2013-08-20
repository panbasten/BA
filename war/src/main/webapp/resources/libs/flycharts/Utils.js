/**
 * FlyCharts的工具类
 */
$FC = {
	// 一些缩写
	UNDEFINED : undefined,
	doc : document,
	win : window,
	math : Math,
	mathRound : Math.round,
	mathFloor : Math.floor,
	mathCeil : Math.ceil,
	mathMax : Math.max,
	mathMin : Math.min,
	mathAbs : Math.abs,
	mathCos : Math.cos,
	mathSin : Math.sin,
	mathPI : Math.PI,
	deg2rad : Math.PI * 2 / 360,
	hasTouch : document.documentElement.ontouchstart !== undefined,
	
	// 一些变量
	userAgent : navigator.userAgent,
	isOpera : window.opera,
	isIE : /msie/i.test(navigator.userAgent) && !isOpera,
	docMode8 : document.documentMode === 8,
	isWebKit : /AppleWebKit/.test(navigator.userAgent),
	isFirefox : /Firefox/.test(navigator.userAgent),
	isTouchDevice : /(Mobile|Android|Windows Phone)/.test(navigator.userAgent),
	SVG_NS : 'http://www.w3.org/2000/svg',

	
	
	// some constants for frequently used strings
	DIV : 'div',
	ABSOLUTE : 'absolute',
	RELATIVE : 'relative',
	HIDDEN : 'hidden',
	PREFIX : 'highcharts-',
	VISIBLE : 'visible',
	PX : 'px',
	NONE : 'none',
	M : 'M',
	L : 'L',
	
	NORMAL_STATE : '',
	HOVER_STATE : 'hover',
	SELECT_STATE : 'select',
	MILLISECOND : 'millisecond',
	SECOND : 'second',
	MINUTE : 'minute',
	HOUR : 'hour',
	DAY : 'day',
	WEEK : 'week',
	MONTH : 'month',
	YEAR : 'year',

	// constants for attributes
	LINEAR_GRADIENT : 'linearGradient',
	STOPS : 'stops',
	STROKE_WIDTH : 'stroke-width',
	
	noop : function () {},
	
	/**
	 * 使用b的成员扩展a对象
	 * 
	 * @param {Object}
	 *            a 带扩展对象
	 * @param {Object}
	 *            b 扩展源对象
	 */
	extend : function (a, b) {
		var n;
		if (!a) {
			a = {};
		}
		for (n in b) {
			a[n] = b[n];
		}
		return a;
	},
		
	/**
	 * 用于深度合并两个或者多个对象，返回结果。<br>
	 * Previously this function redirected to jQuery.extend(true), but this had
	 * two limitations. First, it deep merged arrays, which lead to workarounds
	 * in Highcharts. Second, it copied properties from extended prototypes.
	 */
	merge : function () {
		var i,
			len = arguments.length,
			ret = {},
			doCopy = function (copy, original) {
				var value, key;

				for (key in original) {
					if (original.hasOwnProperty(key)) {
						value = original[key];

						// An object is replacing a primitive
						if (typeof copy !== 'object') {
							copy = {};
						}
							
						// Copy the contents of objects, but not arrays or DOM
						// nodes
						if (value && typeof value === 'object' && Object.prototype.toString.call(value) !== '[object Array]'
								&& typeof value.nodeType !== 'number') {
							copy[key] = doCopy(copy[key] || {}, value);
					
						// Primitives and arrays are copied over directly
						} else {
							copy[key] = original[key];
						}
					}
				}
				return copy;
			};

		// For each argument, extend the return
		for (i = 0; i < len; i++) {
			ret = doCopy(ret, arguments[i]);
		}

		return ret;
	},

	/**
	 * Take an array and turn into a hash with even number arguments as keys and
	 * odd numbers as values. Allows creating constants for commonly used style
	 * properties, attributes etc. Avoid it in performance critical situations
	 * like looping
	 */
	hash : function () {
		var i = 0,
			args = arguments,
			length = args.length,
			obj = {};
		for (; i < length; i++) {
			obj[args[i++]] = args[i];
		}
		return obj;
	},

	/**
	 * parseInt方法的缩写
	 * 
	 * @param {Object}
	 *            s
	 * @param {Number}
	 *            mag Magnitude
	 */
	pInt : function (s, mag) {
		return parseInt(s, mag || 10);
	},

	/**
	 * 检查字符串
	 * 
	 * @param {Object}
	 *            s
	 */
	isString : function (s) {
		return typeof s === 'string';
	},

	/**
	 * 检查对象
	 * 
	 * @param {Object}
	 *            obj
	 */
	isObject : function (obj) {
		return typeof obj === 'object';
	},

	/**
	 * 检查数组
	 * 
	 * @param {Object}
	 *            obj
	 */
	isArray : function (obj) {
		return Object.prototype.toString.call(obj) === '[object Array]';
	},

	/**
	 * 检查数字
	 * 
	 * @param {Object}
	 *            n
	 */
	isNumber : function (n) {
		return typeof n === 'number';
	},

	log2lin : function (num) {
		return Math.log(num) / Math.LN10;
	},
	lin2log : function (num) {
		return Math.pow(10, num);
	},

	/**
	 * 移除数组中最后一次出现的该元素
	 * 
	 * @param {Array}
	 *            arr
	 * @param {Mixed}
	 *            item
	 */
	erase : function (arr, item) {
		var i = arr.length;
		while (i--) {
			if (arr[i] === item) {
				arr.splice(i, 1);
				break;
			}
		}
		// return arr;
	},

	/**
	 * 如果对象不为null或者undefined，则返回true<br>
	 * 类似：MooTools的$.defined
	 * 
	 * @param {Object}
	 *            obj
	 */
	defined : function (obj) {
		return obj !== undefined && obj !== null;
	},

	/**
	 * 设置一个或多个属性，也用于获得一个属性。不能实现jQuery的attr，<br>
	 * 原因是该方法会试图在SVG元素上设置expando属性，这是不允许的。
	 * 
	 * @param {Object}
	 *            elem 接收属性(s)的DOM元素
	 * @param {String|Object}
	 *            prop 属性值或者键值对对象
	 * @param {String}
	 *            value 如果只有一个属性，将返回属性值
	 */
	attr : function (elem, prop, value) {
		var key,
			setAttribute = 'setAttribute',
			ret;

		// if the prop is a string
		if (this.isString(prop)) {
			// set the value
			if (this.defined(value)) {

				elem[setAttribute](prop, value);

			// get the value
			} else if (elem && elem.getAttribute) { 
				// elem not defined when printing pie demo...
				ret = elem.getAttribute(prop);
			}

		// else if prop is defined, it is a hash of key/value pairs
		} else if (this.defined(prop) && isObject(prop)) {
			for (key in prop) {
				elem[setAttribute](key, prop[key]);
			}
		}
		return ret;
	},
	
	/**
	 * 如果对象不是数组，则返回一个包含其对象的数组。<br>
	 * 类似：MooTools的$.splat
	 */
	splat : function (obj) {
		return isArray(obj) ? obj : [obj];
	},

	/**
	 * 返回第一个定义值。<br>
	 * 类似：MooTools的$.pick
	 */
	pick : function () {
		var args = arguments,
			i,
			arg,
			length = args.length;
		for (i = 0; i < length; i++) {
			arg = args[i];
			if (typeof arg !== 'undefined' && arg !== null) {
				return arg;
			}
		}
	},

	/**
	 * 设置给定元素的CSS
	 * 
	 * @param {Object}
	 *            el
	 * @param {Object}
	 *            styles Style object with camel case property names
	 */
	css : function (el, styles) {
		if (this.isIE) {
			if (styles && styles.opacity !== undefined) {
				styles.filter = 'alpha(opacity=' + (styles.opacity * 100) + ')';
			}
		}
		this.extend(el.style, styles);
	},

	/**
	 * 使用属性和样式创建一个元素的工具方法
	 * 
	 * @param {Object}
	 *            tag
	 * @param {Object}
	 *            attribs
	 * @param {Object}
	 *            styles
	 * @param {Object}
	 *            parent
	 * @param {Object}
	 *            nopad
	 */
	createElement : function (tag, attribs, styles, parent, nopad) {
		var el = document.createElement(tag);
		if (attribs) {
			this.extend(el, attribs);
		}
		if (nopad) {
			this.css(el, {padding: 0, border: NONE, margin: 0});
		}
		if (styles) {
			this.css(el, styles);
		}
		if (parent) {
			parent.appendChild(el);
		}
		return el;
	},

	/**
	 * 使用新成员扩展原型类
	 * 
	 * @param {Object}
	 *            parent
	 * @param {Object}
	 *            members
	 */
	extendClass : function (parent, members) {
		var object = function () {};
		object.prototype = new parent();
		this.extend(object.prototype, members);
		return object;
	},

	/**
	 * 格式化一个数字，并且按照设置返回一个字符串
	 * 
	 * @param {Number}
	 *            number 数字
	 * @param {Number}
	 *            decimals 小数位数
	 * @param {String}
	 *            decPoint 小数点，默认使用默认语言的设置
	 * @param {String}
	 *            thousandsSep 千位分隔符，默认使用默认语言的设置
	 */
	numberFormat : function (number, decimals, decPoint, thousandsSep) {
		var lang = defaultOptions.lang,
			// http://kevin.vanzonneveld.net/techblog/article/javascript_equivalent_for_phps_number_format/
			n = number,
			c = decimals === -1 ?
				((n || 0).toString().split('.')[1] || '').length : // preserve
																	// decimals
				(isNaN(decimals = this.mathAbs(decimals)) ? 2 : decimals),
			d = decPoint === undefined ? lang.decimalPoint : decPoint,
			t = thousandsSep === undefined ? lang.thousandsSep : thousandsSep,
			s = n < 0 ? "-" : "",
			i = String(this.pInt(n = this.mathAbs(+n || 0).toFixed(c))),
			j = i.length > 3 ? i.length % 3 : 0;

		return s + (j ? i.substr(0, j) + t : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + t) +
			(c ? d + this.mathAbs(n - i).toFixed(c).slice(2) : "");
	},

	/**
	 * 通过在数字前面加0来格式化固定长度
	 * 
	 * @param {Number}
	 *            number
	 * @param {Number}
	 *            length
	 */
	pad : function (number, length) {
		// Create an array of the remaining length +1 and join it with 0's
		return new Array((length || 2) + 1 - String(number).length).join(0) + number;
	},

	/**
	 * 用一个扩展的函数包裹一个方法，保留原始功能
	 * 
	 * @param {Object}
	 *            obj 方法的上下文对象
	 * @param {String}
	 *            method 用于扩展的方法名称
	 * @param {Function}
	 *            func 方法反馈包装器。该方法使用和原始方法相同的参数调用，不同之处就是原有方法位于参数的首位。
	 */
	wrap : function (obj, method, func) {
		var proceed = obj[method];
		obj[method] = function () {
			var args = Array.prototype.slice.call(arguments);
			args.unshift(proceed);
			return func.apply(this, args);
		};
	},

	/**
	 * Based on http://www.php.net/manual/en/function.strftime.php
	 * 
	 * @param {String}
	 *            format
	 * @param {Number}
	 *            timestamp
	 * @param {Boolean}
	 *            capitalize
	 */
	dateFormat : function (format, timestamp, capitalize) {
		if (!defined(timestamp) || isNaN(timestamp)) {
			return 'Invalid date';
		}
		format = pick(format, '%Y-%m-%d %H:%M:%S');

		var date = new Date(timestamp),
			key, // used in for constuct below
			// get the basic time values
			hours = date[getHours](),
			day = date[getDay](),
			dayOfMonth = date[getDate](),
			month = date[getMonth](),
			fullYear = date[getFullYear](),
			lang = defaultOptions.lang,
			langWeekdays = lang.weekdays,

			// List all format keys. Custom formats can be added from the
			// outside.
			replacements = this.extend({

				// Day
				'a': langWeekdays[day].substr(0, 3), // Short weekday, like
														// 'Mon'
				'A': langWeekdays[day], // Long weekday, like 'Monday'
				'd': pad(dayOfMonth), // Two digit day of the month, 01 to 31
				'e': dayOfMonth, // Day of the month, 1 through 31

				// Week (none implemented)
				// 'W': weekNumber(),

				// Month
				'b': lang.shortMonths[month], // Short month, like 'Jan'
				'B': lang.months[month], // Long month, like 'January'
				'm': pad(month + 1), // Two digit month number, 01 through 12

				// Year
				'y': fullYear.toString().substr(2, 2), // Two digits year, like
														// 09 for 2009
				'Y': fullYear, // Four digits year, like 2009

				// Time
				'H': pad(hours), // Two digits hours in 24h format, 00
									// through 23
				'I': pad((hours % 12) || 12), // Two digits hours in 12h
												// format, 00 through 11
				'l': (hours % 12) || 12, // Hours in 12h format, 1 through 12
				'M': pad(date[getMinutes]()), // Two digits minutes, 00
												// through 59
				'p': hours < 12 ? 'AM' : 'PM', // Upper case AM or PM
				'P': hours < 12 ? 'am' : 'pm', // Lower case AM or PM
				'S': pad(date.getSeconds()), // Two digits seconds, 00
												// through 59
				'L': pad(this.mathRound(timestamp % 1000), 3) // Milliseconds
															// (naming from
															// Ruby)
			}, Highcharts.dateFormats);


		// do the replaces
		for (key in replacements) {
			while (format.indexOf('%' + key) !== -1) { // regex would do it in
														// one line, but this is
														// faster
				format = format.replace('%' + key, typeof replacements[key] === 'function' ? replacements[key](timestamp) : replacements[key]);
			}
		}

		// Optionally capitalize the string and return
		return capitalize ? format.substr(0, 1).toUpperCase() + format.substr(1) : format;
	},

	/**
	 * 格式化单个变量。类似sprintf, 但没有%前缀
	 */
	formatSingle : function (format, val) {
		var floatRegex = /f$/,
			decRegex = /\.([0-9])/,
			lang = defaultOptions.lang,
			decimals;

		if (floatRegex.test(format)) { // float
			decimals = format.match(decRegex);
			decimals = decimals ? decimals[1] : -1;
			val = numberFormat(
				val,
				decimals,
				lang.decimalPoint,
				format.indexOf(',') > -1 ? lang.thousandsSep : ''
			);
		} else {
			val = dateFormat(format, val);
		}
		return val;
	},

	/**
	 * 根据Python的String.format规则的子集来格式化字符串。
	 */
	format : function (str, ctx) {
		var splitter = '{',
			isInside = false,
			segment,
			valueAndFormat,
			path,
			i,
			len,
			ret = [],
			val,
			index;
		
		while ((index = str.indexOf(splitter)) !== -1) {
			
			segment = str.slice(0, index);
			if (isInside) { // we're on the closing bracket looking back
				
				valueAndFormat = segment.split(':');
				path = valueAndFormat.shift().split('.'); // get first and
															// leave format
				len = path.length;
				val = ctx;

				// Assign deeper paths
				for (i = 0; i < len; i++) {
					val = val[path[i]];
				}

				// Format the replacement
				if (valueAndFormat.length) {
					val = formatSingle(valueAndFormat.join(':'), val);
				}

				// Push the result and advance the cursor
				ret.push(val);
				
			} else {
				ret.push(segment);
				
			}
			str = str.slice(index + 1); // the rest
			isInside = !isInside; // toggle
			splitter = isInside ? '}' : '{'; // now look for next matching
												// bracket
		}
		ret.push(str);
		return ret.join('');
	},

	/**
	 * 获得数字的量级
	 */
	getMagnitude : function (num) {
		return Math.pow(10, this.mathFloor(Math.log(num) / Math.LN10));
	},

	/**
	 * 获得一个间隔，规格化为1, 2, 2.5 and 5的倍数
	 * 
	 * @param {Number}
	 *            interval
	 * @param {Array}
	 *            multiples
	 * @param {Number}
	 *            magnitude
	 * @param {Object}
	 *            options
	 */
	normalizeTickInterval : function (interval, multiples, magnitude, options) {
		var normalized, i;

		// round to a tenfold of 1, 2, 2.5 or 5
		magnitude = pick(magnitude, 1);
		normalized = interval / magnitude;

		// multiples for a linear scale
		if (!multiples) {
			multiples = [1, 2, 2.5, 5, 10];

			// the allowDecimals option
			if (options && options.allowDecimals === false) {
				if (magnitude === 1) {
					multiples = [1, 2, 5, 10];
				} else if (magnitude <= 0.1) {
					multiples = [1 / magnitude];
				}
			}
		}

		// normalize the interval to the nearest multiple
		for (i = 0; i < multiples.length; i++) {
			interval = multiples[i];
			if (normalized <= (multiples[i] + (multiples[i + 1] || multiples[i])) / 2) {
				break;
			}
		}

		// multiply back to the correct magnitude
		interval *= magnitude;

		return interval;
	},

	/**
	 * Get a normalized tick interval for dates. Returns a configuration object
	 * with unit range (interval), count and name. Used to prepare data for
	 * getTimeTicks. Previously this logic was part of getTimeTicks, but as
	 * getTimeTicks now runs of segments in stock charts, the normalizing logic
	 * was extracted in order to prevent it for running over again for each
	 * segment having the same interval. #662, #697.
	 */
	normalizeTimeTickInterval : function (tickInterval, unitsOption) {
		var units = unitsOption || [[
					MILLISECOND, // unit name
					[1, 2, 5, 10, 20, 25, 50, 100, 200, 500] // allowed
																// multiples
				], [
					SECOND,
					[1, 2, 5, 10, 15, 30]
				], [
					MINUTE,
					[1, 2, 5, 10, 15, 30]
				], [
					HOUR,
					[1, 2, 3, 4, 6, 8, 12]
				], [
					DAY,
					[1, 2]
				], [
					WEEK,
					[1, 2]
				], [
					MONTH,
					[1, 2, 3, 4, 6]
				], [
					YEAR,
					null
				]],
			unit = units[units.length - 1], // default unit is years
			interval = timeUnits[unit[0]],
			multiples = unit[1],
			count,
			i;
			
		// loop through the units to find the one that best fits the
		// tickInterval
		for (i = 0; i < units.length; i++) {
			unit = units[i];
			interval = timeUnits[unit[0]];
			multiples = unit[1];


			if (units[i + 1]) {
				// lessThan is in the middle between the highest multiple and
				// the next unit.
				var lessThan = (interval * multiples[multiples.length - 1] +
							timeUnits[units[i + 1][0]]) / 2;

				// break and keep the current unit
				if (tickInterval <= lessThan) {
					break;
				}
			}
		}

		// prevent 2.5 years intervals, though 25, 250 etc. are allowed
		if (interval === timeUnits[YEAR] && tickInterval < 5 * interval) {
			multiples = [1, 2, 5];
		}
		
		// prevent 2.5 years intervals, though 25, 250 etc. are allowed
		if (interval === timeUnits[YEAR] && tickInterval < 5 * interval) {
			multiples = [1, 2, 5];
		}

		// get the count
		count = normalizeTickInterval(
			tickInterval / interval, 
			multiples,
			unit[0] === YEAR ? getMagnitude(tickInterval / interval) : 1 // #1913
		);
		
		return {
			unitRange: interval,
			count: count,
			unitName: unit[0]
		};
	},

	/**
	 * Set the tick positions to a time unit that makes sense, for example on
	 * the first of each month or on every Monday. Return an array with the time
	 * positions. Used in datetime axes as well as for grouping data on a
	 * datetime axis.
	 * 
	 * @param {Object}
	 *            normalizedInterval The interval in axis values (ms) and the
	 *            count
	 * @param {Number}
	 *            min The minimum in axis values
	 * @param {Number}
	 *            max The maximum in axis values
	 * @param {Number}
	 *            startOfWeek
	 */
	getTimeTicks : function (normalizedInterval, min, max, startOfWeek) {
		var tickPositions = [],
			i,
			higherRanks = {},
			useUTC = defaultOptions.global.useUTC,
			minYear, // used in months and years as a basis for Date.UTC()
			minDate = new Date(min),
			interval = normalizedInterval.unitRange,
			count = normalizedInterval.count;

		if (defined(min)) { // #1300
			if (interval >= timeUnits[SECOND]) { // second
				minDate.setMilliseconds(0);
				minDate.setSeconds(interval >= timeUnits[MINUTE] ? 0 :
					count * this.mathFloor(minDate.getSeconds() / count));
			}
		
			if (interval >= timeUnits[MINUTE]) { // minute
				minDate[setMinutes](interval >= timeUnits[HOUR] ? 0 :
					count * this.mathFloor(minDate[getMinutes]() / count));
			}
		
			if (interval >= timeUnits[HOUR]) { // hour
				minDate[setHours](interval >= timeUnits[DAY] ? 0 :
					count * this.mathFloor(minDate[getHours]() / count));
			}
		
			if (interval >= timeUnits[DAY]) { // day
				minDate[setDate](interval >= timeUnits[MONTH] ? 1 :
					count * this.mathFloor(minDate[getDate]() / count));
			}
		
			if (interval >= timeUnits[MONTH]) { // month
				minDate[setMonth](interval >= timeUnits[YEAR] ? 0 :
					count * this.mathFloor(minDate[getMonth]() / count));
				minYear = minDate[getFullYear]();
			}
		
			if (interval >= timeUnits[YEAR]) { // year
				minYear -= minYear % count;
				minDate[setFullYear](minYear);
			}
		
			// week is a special case that runs outside the hierarchy
			if (interval === timeUnits[WEEK]) {
				// get start of current week, independent of count
				minDate[setDate](minDate[getDate]() - minDate[getDay]() +
					pick(startOfWeek, 1));
			}
		
		
			// get tick positions
			i = 1;
			minYear = minDate[getFullYear]();
			var time = minDate.getTime(),
				minMonth = minDate[getMonth](),
				minDateDate = minDate[getDate](),
				timezoneOffset = useUTC ? 
					0 : 
					(24 * 3600 * 1000 + minDate.getTimezoneOffset() * 60 * 1000) % (24 * 3600 * 1000); // #950
		
			// iterate and add tick positions at appropriate values
			while (time < max) {
				tickPositions.push(time);
		
				// if the interval is years, use Date.UTC to increase years
				if (interval === timeUnits[YEAR]) {
					time = makeTime(minYear + i * count, 0);
		
				// if the interval is months, use Date.UTC to increase months
				} else if (interval === timeUnits[MONTH]) {
					time = makeTime(minYear, minMonth + i * count);
		
				// if we're using global time, the interval is not fixed as it
				// jumps
				// one hour at the DST crossover
				} else if (!useUTC && (interval === timeUnits[DAY] || interval === timeUnits[WEEK])) {
					time = makeTime(minYear, minMonth, minDateDate +
						i * count * (interval === timeUnits[DAY] ? 1 : 7));
		
				// else, the interval is fixed and we use simple addition
				} else {
					time += interval * count;
				}
		
				i++;
			}
		
			// push the last time
			tickPositions.push(time);


			// mark new days if the time is dividible by day (#1649, #1760)
			each(grep(tickPositions, function (time) {
				return interval <= timeUnits[HOUR] && time % timeUnits[DAY] === timezoneOffset;
			}), function (time) {
				higherRanks[time] = DAY;
			});
		}


		// record information on the chosen unit - for dynamic label formatter
		tickPositions.info = this.extend(normalizedInterval, {
			higherRanks: higherRanks,
			totalRange: interval * count
		});

		return tickPositions;
	},

	/**
	 * Utility method that sorts an object array and keeping the order of equal
	 * items. ECMA script standard does not specify the behaviour when items are
	 * equal.
	 */
	stableSort : function (arr, sortFunction) {
		var length = arr.length,
			sortValue,
			i;

		// Add index to each item
		for (i = 0; i < length; i++) {
			arr[i].ss_i = i; // stable sort index
		}

		arr.sort(function (a, b) {
			sortValue = sortFunction(a, b);
			return sortValue === 0 ? a.ss_i - b.ss_i : sortValue;
		});

		// Remove index from items
		for (i = 0; i < length; i++) {
			delete arr[i].ss_i; // stable sort index
		}
	},

	/**
	 * Non-recursive method to find the lowest member of an array. Math.min
	 * raises a maximum call stack size exceeded error in Chrome when trying to
	 * apply more than 150.000 points. This method is slightly slower, but safe.
	 */
	arrayMin : function (data) {
		var i = data.length,
			min = data[0];

		while (i--) {
			if (data[i] < min) {
				min = data[i];
			}
		}
		return min;
	},

	/**
	 * Non-recursive method to find the lowest member of an array. Math.min
	 * raises a maximum call stack size exceeded error in Chrome when trying to
	 * apply more than 150.000 points. This method is slightly slower, but safe.
	 */
	arrayMax : function (data) {
		var i = data.length,
			max = data[0];

		while (i--) {
			if (data[i] > max) {
				max = data[i];
			}
		}
		return max;
	},

	/**
	 * Utility method that destroys any SVGElement or VMLElement that are
	 * properties on the given object. It loops all properties and invokes
	 * destroy if there is a destroy method. The property is then delete'ed.
	 * 
	 * @param {Object}
	 *            The object to destroy properties on
	 * @param {Object}
	 *            Exception, do not destroy this property, only delete it.
	 */
	destroyObjectProperties : function (obj, except) {
		var n;
		for (n in obj) {
			// If the object is non-null and destroy is defined
			if (obj[n] && obj[n] !== except && obj[n].destroy) {
				// Invoke the destroy
				obj[n].destroy();
			}

			// Delete the property from the object.
			delete obj[n];
		}
	},

	/**
	 * 丢弃一个元素到回收站或者删除
	 * 
	 * @param {Object}
	 *            The HTML node to discard
	 */
	discardElement : function (element) {
		// create a garbage bin element, not part of the DOM
		if (!garbageBin) {
			garbageBin = createElement(DIV);
		}

		// move the node and empty bin
		if (element) {
			garbageBin.appendChild(element);
		}
		garbageBin.innerHTML = '';
	},

	/**
	 * 提供错误调试
	 */
	error : function (code, stop) {
		var msg = 'flychart error #' + code;
		if (stop) {
			throw msg;
		} else if (window.console) {
			console.log(msg);
		}
	},

	/**
	 * 修复JS四舍五入浮点数错误
	 * 
	 * @param {Number}
	 *            num
	 */
	correctFloat : function (num) {
		return parseFloat(
			num.toPrecision(14)
		);
	},

	/**
	 * Set the global animation to either a given value, or fall back to the
	 * given chart's animation option
	 * 
	 * @param {Object}
	 *            animation
	 * @param {Object}
	 *            chart
	 */
	setAnimation : function (animation, chart) {
		globalAnimation = pick(animation, chart.animation);
	},
	
	/**
	 * A direct link to jQuery methods. MooTools and Prototype adapters must be implemented for each case of method.
	 * @param {Object} elem The HTML element
	 * @param {String} method Which method to run on the wrapped element
	 */
	adapterRun: function (elem, method) {
		return $(elem)[method]();
	},
	
	/**
	 * Map an array
	 * @param {Array} arr
	 * @param {Function} fn
	 */
	map: function (arr, fn) {
		//return jQuery.map(arr, fn);
		var results = [],
			i = 0,
			len = arr.length;
		for (; i < len; i++) {
			results[i] = fn.call(arr[i], arr[i], i, arr);
		}
		return results;

	},
	
	/**
	 * Get the position of an element relative to the top left of the page
	 */
	offset: function (el) {
		return $(el).offset();
	},

	/**
	 * Add an event listener
	 * @param {Object} el A HTML element or custom object
	 * @param {String} event The event type
	 * @param {Function} fn The event handler
	 */
	addEvent: function (el, event, fn) {
		$(el).bind(event, fn);
	},

	/**
	 * Remove event added with addEvent
	 * @param {Object} el The object
	 * @param {String} eventType The event type. Leave blank to remove all events.
	 * @param {Function} handler The function to remove
	 */
	removeEvent: function (el, eventType, handler) {
		// workaround for jQuery issue with unbinding custom events:
		// http://forum.jQuery.com/topic/javascript-error-when-unbinding-a-custom-event-using-jQuery-1-4-2
		var func = doc.removeEventListener ? 'removeEventListener' : 'detachEvent';
		if (doc[func] && el && !el[func]) {
			el[func] = function () {};
		}

		$(el).unbind(eventType, handler);
	},

	/**
	 * Fire an event on a custom object
	 * @param {Object} el
	 * @param {String} type
	 * @param {Object} eventArguments
	 * @param {Function} defaultFunction
	 */
	fireEvent: function (el, type, eventArguments, defaultFunction) {
		var event = $.Event(type),
			detachedType = 'detached' + type,
			defaultPrevented;

		// Remove warnings in Chrome when accessing layerX and layerY. Although Highcharts
		// never uses these properties, Chrome includes them in the default click event and
		// raises the warning when they are copied over in the extend statement below.
		//
		// To avoid problems in IE (see #1010) where we cannot delete the properties and avoid
		// testing if they are there (warning in chrome) the only option is to test if running IE.
		if (!isIE && eventArguments) {
			delete eventArguments.layerX;
			delete eventArguments.layerY;
		}

		extend(event, eventArguments);

		// Prevent jQuery from triggering the object method that is named the
		// same as the event. For example, if the event is 'select', jQuery
		// attempts calling el.select and it goes into a loop.
		if (el[type]) {
			el[detachedType] = el[type];
			el[type] = null;
		}

		// Wrap preventDefault and stopPropagation in try/catch blocks in
		// order to prevent JS errors when cancelling events on non-DOM
		// objects. #615.
		/*jslint unparam: true*/
		$.each(['preventDefault', 'stopPropagation'], function (i, fn) {
			var base = event[fn];
			event[fn] = function () {
				try {
					base.call(event);
				} catch (e) {
					if (fn === 'preventDefault') {
						defaultPrevented = true;
					}
				}
			};
		});
		/*jslint unparam: false*/

		// trigger it
		$(el).trigger(event);

		// attach the method
		if (el[detachedType]) {
			el[type] = el[detachedType];
			el[detachedType] = null;
		}

		if (defaultFunction && !event.isDefaultPrevented() && !defaultPrevented) {
			defaultFunction(event);
		}
	},
	
	/**
	 * Extension method needed for MooTools
	 */
	washMouseEvent: function (e) {
		var ret = e.originalEvent || e;
		
		// computed by jQuery, needed by IE8
		if (ret.pageX === UNDEFINED) { // #1236
			ret.pageX = e.pageX;
			ret.pageY = e.pageY;
		}
		
		return ret;
	},

	/**
	 * Animate a HTML element or SVG element wrapper
	 * @param {Object} el
	 * @param {Object} params
	 * @param {Object} options jQuery-like animation options: duration, easing, callback
	 */
	animate: function (el, params, options) {
		var $el = $(el);
		if (!el.style) {
			el.style = {}; // #1881
		}
		if (params.d) {
			el.toD = params.d; // keep the array form for paths, used in $.fx.step.d
			params.d = 1; // because in jQuery, animating to an array has a different meaning
		}

		$el.stop();
		$el.animate(params, options);

	},
	/**
	 * Stop running animation
	 */
	stop: function (el) {
		$(el).stop();
	}
};

$FC.hasSVG = !!$FC.doc.createElementNS && !!$FC.doc.createElementNS($FC.SVG_NS, 'svg').createSVGRect;
$FC.hasBidiBug = $FC.isFirefox && parseInt($FC.userAgent.split('Firefox/')[1], 10) < 4;
$FC.useCanVG = !$FC.hasSVG && !$FC.isIE && !!$FC.doc.createElement('canvas').getContext;
$FC.hasTouch = $FC.doc.documentElement.ontouchstart !== undefined;

$FC.symbolSizes = {};
$FC.idCounter = 0;
$FC.symbolSizes = {};
$FC.idCounter = 0;

/*
 * Empirical lowest possible opacities for TRACKER_FILL
 * IE6: 0.002
 * IE7: 0.002
 * IE8: 0.002
 * IE9: 0.00000000001 (unlimited)
 * IE10: 0.0001 (exporting only)
 * FF: 0.00000000001 (unlimited)
 * Chrome: 0.000001
 * Safari: 0.000001
 * Opera: 0.00000000001 (unlimited)
 */
$FC.TRACKER_FILL = 'rgba(192,192,192,' + ($FC.hasSVG ? 0.0001 : 0.002) + ')'; // invisible but clickable


/**********************
 * Helper class that contains variuos counters that are local to the chart.
 **********************/
$FC.ChartCounters = function () {
	this.color = 0;
	this.symbol = 0;
};

$FC.ChartCounters.prototype = {
	/**
	 * Wraps the color counter if it reaches the specified length.
	 */
	wrapColor : function (length) {
		if (this.color >= length) {
			this.color = 0;
		}
	},

	/**
	 * Wraps the symbol counter if it reaches the specified length.
	 */
	wrapSymbol : function (length) {
		if (this.symbol >= length) {
			this.symbol = 0;
		}
	}
};



/**************************
 * 路径差值算法
 **************************/
$FC.pathAnim = {
	/**
	 * 准备从一点到另一点动画路径的开始和解决值
	 */
	init: function (elem, fromD, toD) {
		fromD = fromD || '';
		var shift = elem.shift,
			bezier = fromD.indexOf('C') > -1,
			numParams = bezier ? 7 : 3,
			endLength,
			slice,
			i,
			start = fromD.split(' '),
			end = [].concat(toD), // copy
			startBaseLine,
			endBaseLine,
			sixify = function (arr) { // in splines make move points have six parameters like bezier curves
				i = arr.length;
				while (i--) {
					if (arr[i] === M) {
						arr.splice(i + 1, 0, arr[i + 1], arr[i + 2], arr[i + 1], arr[i + 2]);
					}
				}
			};

		if (bezier) {
			sixify(start);
			sixify(end);
		}

		// pull out the base lines before padding
		if (elem.isArea) {
			startBaseLine = start.splice(start.length - 6, 6);
			endBaseLine = end.splice(end.length - 6, 6);
		}

		// if shifting points, prepend a dummy point to the end path
		if (shift <= end.length / numParams) {
			while (shift--) {
				end = [].concat(end).splice(0, numParams).concat(end);
			}
		}
		elem.shift = 0; // reset for following animations

		// copy and append last point until the length matches the end length
		if (start.length) {
			endLength = end.length;
			while (start.length < endLength) {

				//bezier && sixify(start);
				slice = [].concat(start).splice(start.length - numParams, numParams);
				if (bezier) { // disable first control point
					slice[numParams - 6] = slice[numParams - 2];
					slice[numParams - 5] = slice[numParams - 1];
				}
				start = start.concat(slice);
			}
		}

		if (startBaseLine) { // append the base lines for areas
			start = start.concat(startBaseLine);
			end = end.concat(endBaseLine);
		}
		return [start, end];
	},

	/**
	 * 插入路径中的每个值，返回数组
	 */
	step: function (start, end, pos, complete) {
		var ret = [],
			i = start.length,
			startVal;

		if (pos === 1) { // land on the final path without adjustment points appended in the ends
			ret = complete;

		} else if (i === end.length && pos < 1) {
			while (i--) {
				startVal = parseFloat(start[i]);
				ret[i] =
					isNaN(startVal) ? // a letter instruction like M or L
						start[i] :
						pos * (parseFloat(end[i] - startVal)) + startVal;

			}
		} else { // if animation is finished or length not matching, land on right value
			ret = end;
		}
		return ret;
	}
};

// 用于存放页面中所有的图表
$FC.charts = [];