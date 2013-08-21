/**
 * Set the default options for bar
 */
$FC.defaultPlotOptions.bar = $FC.merge($FC.defaultPlotOptions.column);

/**
 * The Bar series class
 */
$FC.BarSeries = $FC.extendClass($FC.ColumnSeries, {
	type: 'bar',
	inverted: true
});
$FC.seriesTypes.bar = $FC.BarSeries;