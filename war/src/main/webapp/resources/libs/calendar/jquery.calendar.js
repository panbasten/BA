(function($) {
	/**
	 * 改变尺寸
	 */
	function _resize(target) {
		var opts = $.data(target, "calendar").options;
		var t = $(target);
		if (opts.fit == true) {
			var p = t.parent();
			opts.width = p.width();
			opts.height = p.height();
		}
		var header = t.find(".ui-calendar-header");
		t._outerWidth(opts.width);
		t._outerHeight(opts.height);
		t.find(".ui-calendar-body")._outerHeight(t.height() - header._outerHeight());
	}
	
	/**
	 * 初始化
	 */
	function _init(target) {
		$(target).addClass("ui-calendar ui-widget ui-helper-clearfix ui-corner-all").wrapInner(
			"<div class=\"ui-calendar-header\">"
				+ "<div class=\"ui-calendar-prevmonth ui-icon ui-icon-triangle-1-w\"></div>"
				+ "<div class=\"ui-calendar-nextmonth ui-icon ui-icon-triangle-1-e\"></div>"
				+ "<div class=\"ui-calendar-prevyear ui-icon ui-icon-triangle-2-w-w\"></div>"
				+ "<div class=\"ui-calendar-nextyear ui-icon ui-icon-triangle-2-e-e\"></div>"
				+ "<div class=\"ui-calendar-title ui-widget ui-widget-header\">"
				+ "<span>Aprial 2010</span>"
				+ "</div>"
				+ "</div>"
				+ "<div class=\"ui-calendar-body ui-widget\">"
				+ "<div class=\"ui-calendar-menu\">"
				+ "<div class=\"ui-calendar-menu-year-inner\">"
				+ "<span class=\"ui-calendar-menu-prev ui-icon ui-icon-triangle-1-w\"></span>"
				+ "<span><input class=\"ui-calendar-menu-year\" type=\"text\"></input></span>"
				+ "<span class=\"ui-calendar-menu-next ui-icon ui-icon-triangle-1-e\"></span>"
				+ "</div>"
				+ "<div class=\"ui-calendar-menu-month-inner\">"
				+ "</div>" + "</div>" + "</div>");
		$(target).find(".ui-calendar-title span").hover(function() {
			$(this).addClass("hover");
		}, function() {
			$(this).removeClass("hover");
		}).click(function() {
			var menu = $(target).find(".ui-calendar-menu");
			if (menu.is(":visible")) {
				menu.hide();
			} else {
				_initMenu(target);
			}
		});
		
		$(".ui-calendar-prevmonth,.ui-calendar-nextmonth,.ui-calendar-prevyear,.ui-calendar-nextyear", target)
			.hover(function() {
					$(this).addClass("hover");
				}, function() {
					$(this).removeClass("hover");
				});
		
		$(target).find(".ui-calendar-nextmonth").click(function() {
			_addMonth(target, 1);
		});
		$(target).find(".ui-calendar-prevmonth").click(function() {
			_addMonth(target, -1);
		});
		$(target).find(".ui-calendar-nextyear").click(function() {
			_addYear(target, 1);
		});
		$(target).find(".ui-calendar-prevyear").click(function() {
			_addYear(target, -1);
		});
		$(target).bind("_resize", function() {
			var _8 = $.data(target, "calendar").options;
			if (_8.fit == true) {
				_resize(target);
			}
			return false;
		});
	}
	
	/**
	 * 改变月份
	 * @param target
	 * @param step 移动的月份数
	 */
	function _addMonth(target, step) {
		var opts = $.data(target, "calendar").options;
		opts.month += step;
		if (opts.month > 12) {
			opts.year++;
			opts.month = 1;
		} else {
			if (opts.month < 1) {
				opts.year--;
				opts.month = 12;
			}
		}
		_initBody(target);
		var inner = $(target).find(".ui-calendar-menu-month-inner");
		inner.find("td.active").removeClass("active");
		inner.find("td:eq(" + (opts.month - 1) + ")").addClass("active");
	}
	
	/**
	 * 改变年份
	 * @param target
	 * @param step 移动的年份数
	 */
	function _addYear(target, step) {
		var opts = $.data(target, "calendar").options;
		opts.year += step;
		_initBody(target);
		var year = $(target).find(".ui-calendar-menu-year");
		year.val(opts.year);
	}
	
	/**
	 * 初始化选择菜单
	 */
	function _initMenu(target) {
		var opts = $.data(target, "calendar").options;
		$(target).find(".ui-calendar-menu").show();
		if ($(target).find(".ui-calendar-menu-month-inner").is(":empty")) {
			$(target).find(".ui-calendar-menu-month-inner").empty();
			var t = $("<table></table>").appendTo(
					$(target).find(".ui-calendar-menu-month-inner"));
			var idx = 0;
			for ( var i = 0; i < 3; i++) {
				var tr = $("<tr></tr>").appendTo(t);
				for ( var j = 0; j < 4; j++) {
					$("<td class=\"ui-calendar-menu-month\"></td>").html(
							opts.months[idx++]).attr("abbr", idx).appendTo(tr);
				}
			}
			$(target).find(".ui-calendar-menu-prev,.ui-calendar-menu-next").hover(
					function() {
						$(this).addClass("hover");
					}, function() {
						$(this).removeClass("hover");
					});
			$(target).find(".ui-calendar-menu-next").click(function() {
				var y = $(target).find(".ui-calendar-menu-year");
				if (!isNaN(y.val())) {
					y.val(parseInt(y.val()) + 1);
				}
			});
			$(target).find(".ui-calendar-menu-prev").click(function() {
				var y = $(target).find(".ui-calendar-menu-year");
				if (!isNaN(y.val())) {
					y.val(parseInt(y.val() - 1));
				}
			});
			$(target).find(".ui-calendar-menu-year").keypress(function(e) {
				if (e.keyCode == 13) {
					setYearMonth();
				}
			});
			$(target).find(".ui-calendar-menu-month").hover(function() {
				$(this).addClass("hover");
			}, function() {
				$(this).removeClass("hover");
			}).click(function() {
				var menu = $(target).find(".ui-calendar-menu");
				menu.find(".active").removeClass(
						"active");
				$(this).addClass("active");
				setYearMonth();
			});
		}
		
		function setYearMonth() {
			var menu = $(target).find(".ui-calendar-menu");
			var year = menu.find(".ui-calendar-menu-year").val();
			var month = menu.find(".active").attr("abbr");
			if (!isNaN(year)) {
				opts.year = parseInt(year);
				opts.month = parseInt(month);
				_initBody(target);
			}
			menu.hide();
		}
		
		var body = $(target).find(".ui-calendar-body");
		var menu = $(target).find(".ui-calendar-menu");
		var year = menu.find(".ui-calendar-menu-year-inner");
		var month = menu.find(".ui-calendar-menu-month-inner");
		year.find("input").val(opts.year).focus();
		month.find("td.active").removeClass("active");
		month.find("td:eq(" + (opts.month - 1) + ")")
				.addClass("active");
		menu._outerWidth(body._outerWidth());
		menu._outerHeight(body._outerHeight());
		month._outerHeight(menu.height() - year._outerHeight());
	}
	
	/**
	 * 获得格式化后的星期数组
	 */
	function _getWeeks(target, year, month) {
		var opts = $.data(target, "calendar").options;
		var dateInMonth = [];
		var dateNumber = new Date(year, month, 0).getDate();
		for ( var i = 1; i <= dateNumber; i++) {
			dateInMonth.push( [ year, month, i ]);
		}
		var weeks = [], week = [];
		var widx = 0;
		while (dateInMonth.length > 0) {
			var date = dateInMonth.shift();
			week.push(date);
			var day = new Date(date[0], date[1] - 1, date[2]).getDay();
			if (widx == day) {
				day = 0;
			} else {
				if (day == (opts.firstDay == 0 ? 7 : opts.firstDay) - 1) {
					weeks.push(week);
					week = [];
				}
			}
			widx = day;
		}
		if (week.length) {
			weeks.push(week);
		}
		var firstWeek = weeks[0];
		if (firstWeek.length < 7) {
			while (firstWeek.length < 7) {
				var fwd = firstWeek[0];
				var day = new Date(fwd[0], fwd[1] - 1, fwd[2] - 1);
				firstWeek.unshift( [ day.getFullYear(), day.getMonth() + 1, day.getDate() ]);
			}
		} else {
			var fwd = firstWeek[0];
			var w = [];
			for ( var i = 1; i <= 7; i++) {
				var day = new Date(fwd[0], fwd[1] - 1, fwd[2] - i);
				w.unshift( [ day.getFullYear(), day.getMonth() + 1, day.getDate() ]);
			}
			weeks.unshift(w);
		}
		var lastWeek = weeks[weeks.length - 1];
		while (lastWeek.length < 7) {
			var lwd = lastWeek[lastWeek.length - 1];
			var day = new Date(lwd[0], lwd[1] - 1, lwd[2] + 1);
			lastWeek.push( [ day.getFullYear(), day.getMonth() + 1, day.getDate() ]);
		}
		if (weeks.length < 6) {
			var lwd = lastWeek[lastWeek.length - 1];
			var w = [];
			for ( var i = 1; i <= 7; i++) {
				var _2a = new Date(lwd[0], lwd[1] - 1, lwd[2] + i);
				w.push( [ _2a.getFullYear(), _2a.getMonth() + 1,
						_2a.getDate() ]);
			}
			weeks.push(w);
		}
		return weeks;
	}
	
	/**
	 * 初始化Body
	 */
	function _initBody(target) {
		var opts = $.data(target, "calendar").options;
		$(target).find(".ui-calendar-title span").html(
				opts.months[opts.month - 1] + " " + opts.year);
		var body = $(target).find("div.ui-calendar-body");
		body.find(">table").remove();
		var t = $("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><thead></thead><tbody></tbody></table>")
				.prependTo(body);
		var tr = $("<tr></tr>").appendTo(t.find("thead"));
		for ( var i = opts.firstDay; i < opts.weeks.length; i++) {
			tr.append("<th>" + opts.weeks[i] + "</th>");
		}
		for ( var i = 0; i < opts.firstDay; i++) {
			tr.append("<th>" + opts.weeks[i] + "</th>");
		}
		var weeks = _getWeeks(target, opts.year, opts.month);
		for ( var i = 0; i < weeks.length; i++) {
			var _33 = weeks[i];
			var tr = $("<tr></tr>").appendTo(t.find("tbody"));
			for ( var j = 0; j < _33.length; j++) {
				var day = _33[j];
				$("<td class=\"ui-calendar-day ui-calendar-other-month\"></td>")
						.attr("abbr", day[0] + "," + day[1] + "," + day[2])
						.html(day[2]).appendTo(tr);
			}
		}
		t.find("td[abbr^=\"" + opts.year + "," + opts.month + "\"]").removeClass(
				"ui-calendar-other-month");
		var now = new Date();
		var abbr = now.getFullYear() + "," + (now.getMonth() + 1) + ","
				+ now.getDate();
		t.find("td[abbr=\"" + abbr + "\"]").addClass("ui-calendar-today");
		if (opts.current) {
			t.find(".active").removeClass("active");
			var abbr = opts.current.getFullYear() + ","
					+ (opts.current.getMonth() + 1) + ","
					+ opts.current.getDate();
			t.find("td[abbr=\"" + abbr + "\"]").addClass("active");
		}
		var saturday = 6 - opts.firstDay;
		var sunday = saturday + 1;
		if (saturday >= 7) {
			saturday -= 7;
		}
		if (sunday >= 7) {
			sunday -= 7;
		}
		t.find("tr").find("td:eq(" + saturday + ")").addClass("ui-calendar-saturday");
		t.find("tr").find("td:eq(" + sunday + ")").addClass("ui-calendar-sunday");
		t.find("td").hover(function() {
			$(this).addClass("hover");
		}, function() {
			$(this).removeClass("hover");
		}).click(function() {
			t.find(".active").removeClass("active");
			$(this).addClass("active");
			var day = $(this).attr("abbr").split(",");
			opts.current = new Date(day[0], parseInt(day[1]) - 1, day[2]);
			opts.onSelect.call(target, opts.current);
		});
	}
	
	$.fn.calendar = function(options, param) {
		if (typeof options == "string") {
			return $.fn.calendar.methods[options](this, param);
		}
		options = options || {};
		return this.each(function() {
			var calendar = $.data(this, "calendar");
			if (calendar) {
				$.extend(calendar.options, options);
			} else {
				calendar = $.data(this, "calendar", {
					options : $.extend( {}, $.fn.calendar.defaults,
							$.fn.calendar.parseOptions(this), options)
				});
				_init(this);
			}
			if (calendar.options.border == false) {
				$(this).addClass("ui-calendar-noborder");
			}
			_resize(this);
			_initBody(this);
			$(this).find("div.ui-calendar-menu").hide();
		});
	};
	
	$.fn.calendar.methods = {
		options : function(jq) {
			return $.data(jq[0], "calendar").options;
		},
		resize : function(jq) {
			return jq.each(function() {
				_resize(this);
			});
		},
		moveTo : function(jq, date) {
			return jq.each(function() {
				$(this).calendar( {
					year : date.getFullYear(),
					month : date.getMonth() + 1,
					current : date
				});
			});
		}
	};
	
	$.fn.calendar.parseOptions = function(target) {
		var t = $(target);
		return $.extend( {}, Flywet.parseOptions(target, [ "width", "height", {
			firstDay : "number",
			fit : "boolean",
			border : "boolean"
		} ]));
	};
	
	$.fn.calendar.defaults = {
		width : 180,
		height : 180,
		fit : false,
		border : true,
		firstDay : 0,
		weeks : [ "S", "M", "T", "W", "T", "F", "S" ],
		months : [ "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
				"Sep", "Oct", "Nov", "Dec" ],
		year : new Date().getFullYear(),
		month : new Date().getMonth() + 1,
		current : new Date(),
		onSelect : function(_3e) {
		}
	};
	
})(jQuery);
