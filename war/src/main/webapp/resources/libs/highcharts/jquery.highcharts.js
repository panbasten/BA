Flywet.widget.HighCharts = function(cfg) {
	this.cfg = $.extend({}, Flywet.HighCharts.container, cfg);
	this.id = this.cfg.id;
	this.jqId = Flywet.escapeClientId(this.id);

	this.init();
};

Flywet.extend(Flywet.widget.HighCharts, Flywet.widget.BaseWidget);

Flywet.widget.HighCharts.prototype.init = function() {
	if (this.cfg.parent || this.cfg.parentId) {
		this.parent = this.cfg.parent
				|| $(Flywet.escapeClientId(this.cfg.parentId));
		this.jq = $(this.parent).find(this.jqId);
		if (this.jq.length == 0) {
			this.jq = $("<div></div>");
		}
		this.parent.append(this.jq);
	} else {
		this.jq = $(this.jqId);
	}

	this.jq.width(this.cfg.width).height(this.cfg.height).css({
				"min-width" : this.cfg["min-width"],
				"min-height" : this.cfg["min-height"],
				"margin" : this.cfg["margin"]
			});

	this.data = Flywet.merge({chart:{type:this.cfg.type}}, Flywet.HighCharts.defaults, 
			Flywet.HighCharts[this.cfg.type], this.cfg.data);

	this.jq.highcharts(this.data);
};

Flywet.HighCharts = {
	container : {
		id : null,
		type : "column",
		width : 400,
		height : 300,
		"min-width" : 100,
		"min-height" : 100,
		margin : "0 auto"
	},
	defaults : {
		yAxis: {
			title: {
				text: ""
			}
		},
		legend: {
            shadow: false
        },
        tooltip: {
        	useHTML: true,
            formatter: function() {
                return '<span style="font-size:1.2em;font-weight:bolder;">' + this.point.category + '</span><table>' +
                	'<tr><td style="color:' + this.series.color + ';padding:0">' + this.series.name + ': </td>' +
                    '<td style="padding:0">' + this.point.y + '</td></tr>' +
                	'</table>';
            }
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            }
        }
	},
	
	barStacked : {
		chart: {
			type: 'bar'
		},
		tooltip: {
            formatter: function() {
                return '<span style="font-size:1.2em;font-weight:bolder;">' + this.point.category + '</span><table>' +
                	'<tr><td style="color:' + this.series.color + ';padding:0;">' + this.series.name + ': </td>' +
                    '<td style="padding:0">' + this.y + '</td></tr>' +
                    '<tr><td style="padding:0;">合计: </td>' +
                    '<td style="padding:0;">' + this.point.stackTotal + '</td></tr>' +
                	'</table>';
            }
        },
		plotOptions: {
            series: {
                stacking: 'normal'
            }
        }
	},
	
	barStackedPercent : {
		chart: {
			type: 'bar'
		},
		tooltip: {
            formatter: function() {
                return '<span style="font-size:1.2em;font-weight:bolder;">' + this.point.category + '</span><table>' +
                	'<tr><td style="color:' + this.series.color + ';padding:0;">' + this.series.name + ': </td>' +
                    '<td style="padding:0">' + this.y + '</td></tr>' +
                    '<tr><td style="padding:0;">合计: </td>' +
                    '<td style="padding:0;">' + this.point.stackTotal + '</td></tr>' +
                	'</table>';
            }
        },
		plotOptions: {
            series: {
                stacking: 'percent'
            }
        }
	},
	
	barPlacement : {
		chart: {
			type: 'bar'
		},
		plotOptions: {
            series: {
                grouping: false
            }
        }
	},
	
	columnStacked : {
		chart: {
			type: 'column'
		},
        tooltip: {
            formatter: function() {
                return '<span style="font-size:1.2em;font-weight:bolder;">' + this.point.category + '</span><table>' +
                	'<tr><td style="color:' + this.series.color + ';padding:0;">' + this.series.name + ': </td>' +
                    '<td style="padding:0">' + this.y + '</td></tr>' +
                    '<tr><td style="padding:0;">合计: </td>' +
                    '<td style="padding:0;">' + this.point.stackTotal + '</td></tr>' +
                	'</table>';
            }
        },
        plotOptions: {
            column: {
                stacking: 'normal'
            }
        }
	},
	
	columnStackedPercent : {
		chart: {
			type: 'column'
		},
		tooltip: {
            formatter: function() {
                return '<span style="font-size:1.2em;font-weight:bolder;">' + this.point.category + '</span><table>' +
                	'<tr><td style="color:' + this.series.color + ';padding:0;">' + this.series.name + ': </td>' +
                    '<td style="padding:0">' + this.y + '</td></tr>' +
                    '<tr><td style="padding:0;">合计: </td>' +
                    '<td style="padding:0;">' + this.point.stackTotal + '</td></tr>' +
                	'</table>';
            }
        },
		plotOptions: {
            column: {
                stacking: 'percent'
            }
        }
	},
	
	columnPlacement : {
		chart: {
			type: 'column'
		},
		plotOptions: {
            column: {
                grouping: false
            }
        }
	}
};
