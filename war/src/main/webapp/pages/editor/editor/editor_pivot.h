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
					<span id="${editorId}MeasureClose" class="ui-button ui-icon ui-icon-circle-minus" onclick="Flywet.editors.pivot.toggleContent('${editorId}MeasureContent');"></span>
				</div>
			</div>
			<div id="${editorId}MeasureContent" class="ui-widget-content">
			</div>
		</div>
	</div>
	<div id="${editorId}Content" class="fly-flow-content">
		<div id="${editorId}AttrsLeftBar" class="fly-pivot-attrs-left-bar fly-editor-side-bar">
			<div id="${editorId}PagePanel" class="ui-widget-panel">
				<div class="ui-widget-header ui-helper-clearfix ui-corner-top">
					<div class="ui-toolbar-group-left">页面</div>
					<div class="ui-toolbar-group-right">
						<span id="${editorId}PageClose" class="ui-button ui-icon ui-icon-circle-minus" onclick="Flywet.editors.pivot.toggleContent('${editorId}MeasureContent');"></span>
					</div>
				</div>
				<div id="${editorId}PageContent" class="ui-widget-content">
				</div>
			</div>
			
			<div id="${editorId}FilterPanel" class="ui-widget-panel">
				<div class="ui-widget-header ui-helper-clearfix ui-corner-top">
					<div class="ui-toolbar-group-left">筛选器</div>
					<div class="ui-toolbar-group-right">
						<span id="${editorId}FilterClose" class="ui-button ui-icon ui-icon-circle-minus" onclick="Flywet.editors.pivot.toggleContent('${editorId}MeasureContent');"></span>
					</div>
				</div>
				<div id="${editorId}FilterContent" class="ui-widget-content">
				</div>
			</div>
			
			<div id="${editorId}MarkPanel" class="ui-widget-panel">
				<div class="ui-widget-header ui-helper-clearfix ui-corner-top">
					<div class="ui-toolbar-group-left">标记</div>
					<div class="ui-toolbar-group-right">
						<span id="${editorId}MarkClose" class="ui-button ui-icon ui-icon-circle-minus" onclick="Flywet.editors.pivot.toggleContent('${editorId}MeasureContent');"></span>
					</div>
				</div>
				<div id="${editorId}MarkContent" class="ui-widget-content">
				</div>
			</div>
		
		</div>
		<div id="${editorId}Content2">
			<div id="${editorId}AttrsTopBar">
				<div id="${editorId}ColumnPanel" class="fly-input-group">
				  <span class="fly-input-group-addon">列</span>
				  <span class="fly-input-group-body"></span>
				</div>
				<div id="${editorId}RowPanel" class="fly-input-group">
				  <span class="fly-input-group-addon">行</span>
				  <span class="fly-input-group-body"></span>
				</div>
			</div>
			<div id="${editorId}BodyWrapper">
				<div id="${editorId}Body"></div>
			</div>
		</div>
	</div>
	

</div>
