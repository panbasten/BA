<div id="${editorId}">
	<div id="${editorId}Bar" class="fly-pivot-bar fly-editor-side-bar">
		<div id="${editorId}DimensionPanel" class="ui-widget-panel">
			<div class="ui-widget-header ui-helper-clearfix ui-corner-top">
				<div class="ui-toolbar-group-left">维度</div>
				<div class="ui-toolbar-group-right">
					<span id="${editorId}DimensionClose" class="ui-button ui-icon ui-icon-circle-minus" onclick="Flywet.editors.pivot.toggleContent('${editorId}DimensionContent');"></span>
				</div>
			</div>
			<div id="${editorId}DimensionContent" class="ui-widget-content">
			</div>
		</div>
		<div id="${editorId}MeasurePanel" class="ui-widget-panel">
			<div class="ui-widget-header ui-helper-clearfix ui-corner-top">
				<div class="ui-toolbar-group-left">度量</div>
				<div class="ui-toolbar-group-right">
					<span id="${editorId}MeasureDSClose" class="ui-button ui-icon ui-icon-circle-minus" onclick="Flywet.editors.pivot.toggleContent('${editorId}MeasureContent');"></span>
				</div>
			</div>
			<div id="${editorId}MeasureContent" class="ui-widget-content">
			</div>
		</div>
	</div>
	<div id="${editorId}Content" class="fly-flow-content">
		<div id="${editorId}Body"></div>
	</div>
	

</div>
