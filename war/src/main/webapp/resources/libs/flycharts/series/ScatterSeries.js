/**
 * Set the default options for scatter
 */
$FC.defaultPlotOptions.scatter = $FC.merge($FC.defaultSeriesOptions, {
	lineWidth: 0,
	tooltip: {
		headerFormat: '<span style="font-size: 10px; color:{series.color}">{series.name}</span><br/>',
		pointFormat: 'x: <b>{point.x}</b><br/>y: <b>{point.y}</b><br/>',
		followPointer: true
	},
	stickyTracking: false
});

/**
 * The scatter series class
 */
$FC.ScatterSeries = $FC.extendClass($FC.Series, {
	type: 'scatter',
	sorted: false,
	requireSorting: false,
	noSharedTooltip: true,
	trackerGroups: ['markerGroup'],

	drawTracker: $FC.ColumnSeries.prototype.drawTracker,
	
	setTooltipPoints: $FC.noop
});
$FC.seriesTypes.scatter = $FC.ScatterSeries;