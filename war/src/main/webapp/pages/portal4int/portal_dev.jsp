<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
		
		<title></title>
        
		<link rel="shortcut icon" href="<%=path%>/resources/images/logo.ico" />
		<link rel="icon" href="<%=path%>/resources/images/logo.ico" />
		
		<!-- replace href="<%=path%>/resources/styles/default.compressed.css" type="style" -->
		<link href="<%=path%>/resources/styles/reset.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/styles/default.css" rel="stylesheet"
			type="text/css" />
		<!-- replace end -->
			
		<!-- replace href="<%=path%>/resources/libs.compressed.css" type="style" -->
		<link href="<%=path%>/resources/libs/jquery/jquery-ui.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/core/core.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/menu/menu.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/metro/metro.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/easytabs/easytabs.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/breadcrumb/breadcrumb.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/layout/layout.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/toolbar/toolbar.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/scrollbar/scrollbar.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/search/search.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/popup/popup.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/browsePanel/browsePanel.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/tree/tree.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/dialog/dialog.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/gridlayout/gridlayout.css" rel="stylesheet"
			type="text/css" />
		
		<link href="<%=path%>/resources/libs/panel/panel.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/combo/combo.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/combo/combobox.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/validate/validatebox.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/grid/pagination.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/grid/datagrid.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/spreadsheet/spreadsheet.css" rel="stylesheet"
			type="text/css" />
		<!-- replace end -->
		
		<link href="<%=path%>/resources/styles/themes/aristo/theme.css" rel="stylesheet"
			type="text/css" />
			
		<link href="<%=path%>/resources/styles/portal4int.css" rel="stylesheet"
			type="text/css" />
		
			
			
			
		<!-- replace src="<%=path%>/resources/libs.pack.js" type="script" -->
		<script type="text/javascript"
			src="<%=path%>/resources/libs/jquery/jquery.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/core/core.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/form/form.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/menu/menu.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/metro/metro.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/layout/layout.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/breadcrumb/breadcrumb.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/easytabs/easytabs.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/scrollbar/scrollbar.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/search/search.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/popup/popup.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/flowchart/excanvas.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/flowchart/flowchart.utils.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/flowchart/flowchart.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/dialog/dialog.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/button/button.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/browsePanel/browsePanel.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/gridlayout/gridlayout.js"></script>
		

		<script type="text/javascript"
			src="<%=path%>/resources/libs/drag/jquery.draggable.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/drag/jquery.droppable.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/resizable/jquery.resizable.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/tree/jquery.tree.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/panel/jquery.panel.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/validate/jquery.validatebox.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/combo/jquery.combo.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/combo/jquery.combobox.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/number/jquery.numberbox.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/grid/jquery.pagination.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/grid/jquery.datagrid.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/treegrid/jquery.treegrid.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/fusioncharts/FusionCharts.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/fusioncharts/fly.fusioncharts.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/spreadsheet/jquery.spreadsheet.js"></script>
		<!-- replace end -->
			
			
		<!-- replace src="<%=path%>/resources/portal4int.pack.js" type="script" -->
		<script type="text/javascript"
			src="<%=path%>/resources/scripts/portal4int.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/scripts/preload_for_portal4int.js"></script>
		<!-- replace end -->
		
		<script type="text/javascript">
		
		</script>
			
	</head>
	<body id="idBody">
	
		<div id="fly_portal_cover" class="fly_portal_cover" style="display:none;">
			<div id="fly_portal_cover_img">
				<img src="resources/images/portal/loading.gif" />
			</div>
		</div>

		<div class="navbar navbar-default navbar-fixed-top" role="navigation">
	      <div class="container">
	        <div class="navbar-header">
	          <span class="navbar-brand">经营数据管理平台</span>
	        </div>
	        
	        <div class="navbar-collapse collapse">
	          <ul id="fly_portal_menu_1st_level" class="nav navbar-nav navbar-right">
	          	<!-- 1st level menu -->
	          </ul>
	        </div><!--/.nav-collapse -->
	      </div>
	    </div>
	
	
	    <div class="container" id="idMainContainer">
	
	      <div class="row">
	      
	        <div class="col-xs-6 col-md-3" id="fly_portal_menu_2ed_level" role="navigation">
	          <!-- 2ed & 3th level menu -->
	        </div><!--/span-->

	        <div class="col-xs-12 col-md-9" style="height:3750px;" id="idMainView" role="view">
	          
	          <div id="idMainView2"></div>
	          
	        </div><!--/span-->
	
	      </div><!--/row-->
	
	      <hr>
	
	      <footer>
	      	<div class="container">
	      	  <div class="row">
	      	    <div class="col-md-2 text-muted">
	              <p>经营数据管理平台</p>
	            </div>
	            <div class="col-md-3 text-muted">
	              <a href="">收藏本站</a>
	              |
	              <a href="">联系我们</a>
	            </div>
	            <div class="col-md-5 text-muted">
	              <p>系统意见反馈联系 927363 &copy;畅游天下2014</p>
	            </div>
	        </div>
	      </footer>
	
	    </div>
		
	</body>
	<script type="text/javascript">
		$(document).ready( function() {
        
        	Flywet.Portal4int.initPage();
        	$(window).bind('resize', function() {
				Flywet.Portal4int.resize();
			});
        
        
        
        var chartDataJson = {
			  "chart": {
			    "caption": "Category wise Sales for 1996",
			    "xaxisname": "Month",
			    "palette": "2",
			    "animation": "1",
			    "formatnumberscale": "0",
			    "numberprefix": "$",
			    "showvalues": "0",
			    "numdivlines": "4",
			    "legendposition": "BOTTOM",
			    "bgColor" : "#eeeeee",
			    "bgAlpha" : 100
			  },
			  "categories": [
			    {
			      "category": [
			        {
			          "label": "Jan"
			        },
			        {
			          "label": "Feb"
			        },
			        {
			          "label": "Mar"
			        },
			        {
			          "label": "Apr"
			        },
			        {
			          "label": "May"
			        },
			        {
			          "label": "Jun"
			        }
			      ]
			    }
			  ],
			  "dataset": [
			    {
			      "seriesname": "Beverages",
			      "data": [
			        {
			          "value": "11675.25"
			        },
			        {
			          "value": "28558"
			        },
			        {
			          "value": "35869.5"
			        },
			        {
			          "value": "28729.25"
			        },
			        {
			          "value": "24960"
			        },
			        {
			          "value": "4107"
			        }
			      ]
			    },
			    {
			      "seriesname": "Condiments",
			      "data": [
			        {
			          "value": "6252.6"
			        },
			        {
			          "value": "5000.45"
			        },
			        {
			          "value": "6727.2"
			        },
			        {
			          "value": "11018.3"
			        },
			        {
			          "value": "10737.5"
			        },
			        {
			          "value": "1074"
			        }
			      ]
			    },
			    {
			      "seriesname": "Confections",
			      "data": [
			        {
			          "value": "9191.98"
			        },
			        {
			          "value": "11839.85"
			        },
			        {
			          "value": "10574.05"
			        },
			        {
			          "value": "24302.85"
			        },
			        {
			          "value": "10309.78"
			        },
			        {
			          "value": "1333.2"
			        }
			      ]
			    },
			    {
			      "seriesname": "Dairy Products",
			      "data": [
			        {
			          "value": "10590.5"
			        },
			        {
			          "value": "18835.5"
			        },
			        {
			          "value": "11889.5"
			        },
			        {
			          "value": "14990.8"
			        },
			        {
			          "value": "36452.1"
			        },
			        {
			          "value": "636"
			        }
			      ]
			    },
			    {
			      "seriesname": "Grains/Cereals",
			      "data": [
			        {
			          "value": "5810.75"
			        },
			        {
			          "value": "12517.75"
			        },
			        {
			          "value": "4183.75"
			        },
			        {
			          "value": "3510.5"
			        },
			        {
			          "value": "5789.25"
			        },
			        {
			          "value": "4421"
			        }
			      ]
			    },
			    {
			      "seriesname": "Meat/Poultry",
			      "data": [
			        {
			          "value": "14706.43"
			        },
			        {
			          "value": "3965.17"
			        },
			        {
			          "value": "25377.86"
			        },
			        {
			          "value": "4203.66"
			        },
			        {
			          "value": "26680.88"
			        },
			        {
			          "value": "48"
			        }
			      ]
			    },
			    {
			      "seriesname": "Produce",
			      "data": [
			        {
			          "value": "13047.15"
			        },
			        {
			          "value": "1639"
			        },
			        {
			          "value": "1202.8"
			        },
			        {
			          "value": "13168"
			        },
			        {
			          "value": "15073"
			        },
			        {
			          "value": "1333.05"
			        }
			      ]
			    },
			    {
			      "seriesname": "Seafood",
			      "data": [
			        {
			          "value": "6201.6"
			        },
			        {
			          "value": "14394.6"
			        },
			        {
			          "value": "12841.69"
			        },
			        {
			          "value": "9902.09"
			        },
			        {
			          "value": "10531.66"
			        },
			        {
			          "value": "1042.8"
			        }
			      ]
			    }
			  ],
			  "styles": {
			    "definition": [
			      {
			        "type": "font",
			        "name": "CaptionFont",
			        "color": "666666",
			        "size": "15"
			      },
			      {
			        "type": "font",
			        "name": "SubCaptionFont",
			        "bold": "0"
			      }
			    ],
			    "application": [
			      {
			        "toobject": "caption",
			        "styles": "CaptionFont"
			      },
			      {
			        "toobject": "SubCaption",
			        "styles": "SubCaptionFont"
			      }
			    ]
			  }
			};
			
			
			
			var pivotData = {
				"body" : {
					"row" : [ [ {
						"colspan" : "1",
						"indent" : "0",
						"style" : "even",
						"drillExpand" : {
							"id" : "wcf52bf3d56",
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
							"id" : "wcfb682fe61",
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
			};
			
			var normaldiv = {
        					"name" : "region2",
        					"regionObject" : {
        						"type" : "Table",
        						"regionData" : {
        							"data" : {
        								row : [
        								
	        								[
	        									{
	        										"colspan" : "2",
	        										"rowspan" : "1",
	        										"border" : {
	        											"bottom-style" : "thin",
														"right-style" : "thin"
													},
	        										"value" : "指标"
	        									}
	        									
	        								]
        								
        								]
        							},
        							"type" : "Normal"
        						}
        					},
        					
        					"startPosition" : {
        						"cidx" : 6,
        						"ridx" : 0
        					}
        				};
        	

        	var aa = {
        			"showHScroll": false,
        			"showVScroll": false,
        			"sheet" : [ {
        				"region" : [ {
        					"name" : "region1",
        					"regionObject" : {
        						"type" : "Table",
        						"regionData" : {
        							"data" : pivotData,
        							"type" : "Pivot"
        						}
        					},
        					"startPosition" : {
        						"cidx" : 0,
        						"ridx" : 0
        					}
        				},
        				
        				{
        					"name" : "region2",
        					"regionObject" : {
        						"type" : "Chart",
        						"regionData" : {
        							"data" : chartDataJson,
        							"type" : "MSColumn3D"
        						}
        					},
        					
        					"startPosition" : {
        						"cidx" : 3,
        						"ridx" : 4
        					},
        					
        					"endPosition" : {
        						"cidx" : 5,
        						"ridx" : 16
        					}
        				},
        				
        				
	       				{
        					"name" : "region3",
        					"regionObject" : {
        						"type" : "Chart",
        						"regionData" : {
        							"data" : chartDataJson,
        							"type" : "MSColumn3D"
        						}
        					},
        					
        					"startPosition" : {
        						"cidx" : 0,
        						"ridx" : 4
        					},
        					
        					"endPosition" : {
        						"cidx" : 2,
        						"ridx" : 16
        					}
        				},
        				
        				
        				{
        					"name" : "region4",
        					"regionObject" : {
        						"type" : "Chart",
        						"regionData" : {
        							"data" : chartDataJson,
        							"type" : "MSArea"
        						}
        					},
        					
        					"startPosition" : {
        						"cidx" : 0,
        						"ridx" : 18
        					},
        					
        					"endPosition" : {
        						"cidx" : 2,
        						"ridx" : 32
        					}
        				}
        				
        				 ],
        				"defaultColWidth" : 140,
        				colNum: 6,
                        rowNum: 28,
                        showColHead: false,
                		showRowHead: false,
                		showGrid: false,
                		//rowsHeight: {
                		//	"r_0" : 40
                 		//},
                 		//colsWidth: {
                		//	"c_3" : 80
                		//	,"c_4" : 80
                		//	,"c_5" : 80
                 		//},
        				"defaultRowHeight" : 25,
        				"sheetName" : "测试Sheet1",
        				"startPosition" : {
        					"cidx" : 5,
        					"ridx" : 3
        				}
        			} ]
        		};
        		
        		
        		
        	//$("#idMainView2").spreadsheet(aa);

        });
	</script>
</html>