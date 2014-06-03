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
		"min-width" : 400,
		"min-height" : 300,
		margin : "0 auto"
	},
	defaults : {
		yAxis: {
			min: 0
		},
		legend: {
            align: 'right',
            x: -70,
            verticalAlign: 'top',
            y: 20,
            floating: true,
            backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || 'white',
            borderColor: '#CCC',
            borderWidth: 1,
            shadow: false
        },
        tooltip: {
        	useHTML: true,
            formatter: function() {
                return '<span style="font-size:10px">' + this.point.category + '</span><table>' +
                	'<tr><td style="color:' + this.series.color + ';padding:0">' + this.series.name + ': </td>' +
                    '<td style="padding:0"><b>' + this.point.y + '</b></td></tr>' +
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
	
	stackedColumn : {
		chart: {
			type: 'column'
		},
		yAxis: {
			stackLabels: {
                enabled: true,
                style: {
                    fontWeight: 'bold',
                    color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
                }
            }
		},
        tooltip: {
            formatter: function() {
                return '<b>'+ this.x +'</b><br/>'+
                    this.series.name +': '+ this.y +'<br/>'+
                    '合计: '+ this.point.stackTotal;
            }
        },
        plotOptions: {
            column: {
                stacking: 'normal',
                dataLabels: {
                    enabled: true,
                    color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white',
                    style: {
                        textShadow: '0 0 3px black, 0 0 3px black'
                    }
                }
            }
        }
	}
};
