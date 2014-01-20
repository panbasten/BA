var aa = {
        width: 800
        ,height: 600

        ,offsetCellNumber: 3

        ,s_src : "../../images/default/s.gif"
        ,btn_src : "../../images/report/"
        ,sheet: [
         	{
             	sheetName: "测试Sheet1"
         		,colNum: 20
                ,rowNum: 50
                //,top : 0
                //,left : 0
                ,defaultColWidth : 140
                ,defaultRowHeight : 25
                ,showColHead: true
        		,showRowHead: true

        		,rowsHeight: {
        			"r_0" : 40
         		}
             	,colsWidth: {
        			"c_3" : 80
        			,"c_4" : 80
        			,"c_5" : 80
         		}
        		
        		,"region" : [ {
        			"name" : "region1",
        			"regionObject" : {
        				"type" : "TableRegion",
        				"regionData" : {
        					"data" : {
        						"body" : {
        							"row" : [ [ {
        								"colspan" : "1",
        								"indent" : "0",
        								"style" : "even",
        								"drillExpand" : {
        									"id" : "wcfdc4f3cba",
        									"img" : "drill-position-expand"
        								},
        								"caption" : {
        									"caption" : "All Media"
        								},
        								"rowspan" : "1",
        								"_TAG" : "row-heading"
        							}, {
        								"colspan" : "1",
        								"indent" : "0",
        								"style" : "even",
        								"drillExpand" : {
        									"id" : "wcfd546beb6",
        									"img" : "drill-position-expand"
        								},
        								"caption" : {
        									"caption" : "All Products"
        								},
        								"rowspan" : "1",
        								"_TAG" : "row-heading"
        							}, {
        								"style" : "even",
        								"value" : "266,773",
        								"_TAG" : "cell"
        							}, {
        								"style" : "even",
        								"value" : "225,627.23",
        								"_TAG" : "cell"
        							}, {
        								"style" : "even",
        								"value" : "565,238.13",
        								"_TAG" : "cell"
        							} ] ]
        						},
        						"slicer" : {
        							"members" : [ {
        								"level" : "Year",
        								"caption" : "1997",
        								"depth" : "0"
        							} ]
        						},
        						"head" : {
        							"row" : [ [ {
        								"colspan" : "2",
        								"_TAG" : "corner",
        								"rowspan" : "1"
        							}, {
        								"colspan" : "3",
        								"style" : "span",
        								"drillOther" : {
        									"img" : "drill-position-other"
        								},
        								"caption" : {
        									"caption" : "指标"
        								},
        								"rowspan" : "1",
        								"_TAG" : "heading-heading"
        							} ], [ {
        								"colspan" : "1",
        								"style" : "even",
        								"drillOther" : {
        									"img" : "drill-position-other"
        								},
        								"caption" : {
        									"caption" : "Promotion Media"
        								},
        								"rowspan" : "1",
        								"_TAG" : "heading-heading"
        							}, {
        								"colspan" : "1",
        								"style" : "even",
        								"drillOther" : {
        									"img" : "drill-position-other"
        								},
        								"caption" : {
        									"caption" : "Product"
        								},
        								"rowspan" : "1",
        								"_TAG" : "heading-heading"
        							}, {
        								"colspan" : "1",
        								"style" : "even",
        								"drillOther" : {
        									"img" : "drill-position-other"
        								},
        								"caption" : {
        									"caption" : "Unit Sales"
        								},
        								"rowspan" : "1",
        								"_TAG" : "column-heading"
        							}, {
        								"colspan" : "1",
        								"style" : "odd",
        								"drillOther" : {
        									"img" : "drill-position-other"
        								},
        								"caption" : {
        									"caption" : "Store Cost"
        								},
        								"rowspan" : "1",
        								"_TAG" : "column-heading"
        							}, {
        								"colspan" : "1",
        								"style" : "even",
        								"drillOther" : {
        									"img" : "drill-position-other"
        								},
        								"caption" : {
        									"caption" : "Store Sales"
        								},
        								"rowspan" : "1",
        								"_TAG" : "column-heading"
        							} ] ]
        						}
        					},
        					"type" : "PivotData"
        				}
        			},
        			"startPosition" : {
        				"ridx" : 1,
        				"cidx" : 1
        			}
        		} ]
        	}
    	]
    };