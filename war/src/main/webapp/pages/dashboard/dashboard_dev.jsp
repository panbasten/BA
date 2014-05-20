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
		
		<link href="<%=path%>/resources/styles/bootstrap.css" rel="stylesheet"
			type="text/css" />
			
		<link href="<%=path%>/resources/libs/spreadsheet/spreadsheet.css" rel="stylesheet"
			type="text/css" />
			
			
		<link href="<%=path%>/resources/styles/bootstrap-theme.css" rel="stylesheet"
			type="text/css" />
			
		<link href="<%=path%>/resources/styles/dashboard.css" rel="stylesheet"
			type="text/css" />
		
			
			
			
			
			
		<script type="text/javascript" src="<%=path%>/resources/libs/jquery/jquery.js">
        </script>
        <script type="text/javascript" src="<%=path%>/resources/libs/core/core.js">
        </script>
        
        <script type="text/javascript" src="<%=path%>/resources/libs/fusioncharts/FusionCharts.js">
        </script>
        <script type="text/javascript" src="<%=path%>/resources/libs/fusioncharts/fly.fusioncharts.js">
        </script>
        
        <script type="text/javascript" src="<%=path%>/resources/libs/spreadsheet/jquery.spreadsheet.js">
        </script>	
			
			
		<!-- replace src="<%=path%>/resources/dashboard.pack.js" type="script" -->
		<script type="text/javascript"
			src="<%=path%>/resources/scripts/dashboard.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/scripts/preload_for_dashboard.js"></script>
		<!-- replace end -->
		
		<script type="text/javascript">
		
		</script>
			
	</head>
	<body id="idBody">

		<div class="navbar navbar-default navbar-fixed-top" role="navigation">
	      <div class="container">
	        <div class="navbar-header">
	          <span class="navbar-brand">经营数据管理平台</span>
	        </div>
	        
	        <div class="navbar-collapse collapse">
	          <ul class="nav navbar-nav navbar-right">
	            <li><a href="#">数据简报</a></li>
	            <li class="active"><a href="#">经营分析周报</a></li>
	          </ul>
	        </div><!--/.nav-collapse -->
	      </div>
	    </div>
	
	
	    <div class="container" id="idMainContainer">
	
	      <div class="row">
	      
	        <div class="col-xs-6 col-md-3" id="idSidebar" role="navigation">
	          <div class="list-group">
	            <a href="#" class="list-group-item active">首页</a>
	            <a href="#" class="list-group-item">整体健康度</a>
	            <a href="#" class="list-group-item">14年重点产品分析</a>
	            <a href="#" class="list-group-item">端游运营分析</a>
	            <a href="#" class="list-group-item">端游研发状态</a>
	            <a href="#" class="list-group-item">页游运营分析</a>
	            <a href="#" class="list-group-item">页游研发与海外代理</a>
	            <a href="#" class="list-group-item">7Road上线产品及代理运营收入</a>
	            <a href="#" class="list-group-item">7Road研发项目状态</a>
	            <a href="#" class="list-group-item">Mobogenie</a>
	            <a href="#" class="list-group-item">17173总体经营分析</a>
	            <a href="#" class="list-group-item">17173媒体和视频</a>
	            <a href="#" class="list-group-item">17173PC客户端</a>
	            <a href="#" class="list-group-item">17173移动APP</a>
	            <a href="#" class="list-group-item">17173研发中项目</a>
	            <a href="#" class="list-group-item">网络应用研发部</a>
	          </div>
	        </div><!--/span-->

	        <div class="col-xs-12 col-md-9" style="height:840px;" id="idMainView" role="view">
	          
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
	        										"caption" : {
	        											"caption" : "指标"
	        										}
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
        		
        		
        		
        	$("#idMainView2").spreadsheet(aa);

        });
	</script>
</html>