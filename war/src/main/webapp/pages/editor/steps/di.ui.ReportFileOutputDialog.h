<div id="${editorId}" style="display:none;">
	<div id="${editorId}StepBar" class="fly-flow-step-bar fly-editor-stepbar-content-height ui-tab-side-container ui-corner-top">
		<ul id="${editorId}StepBar-ul" class="ui-tabs">
			<fly:foreach items="${transStepBar}" var="entity" indexVar="entityIndex">
				<li>
					<a href="#trans-step-${entityIndex}">${entity}</a>
				</li>
			</fly:foreach>
		</ul>
		<div id="plugin_panel" class="ui-tab-panel-container fly-editor-stepbar-height-no-padding ui-scrollbar" style="overflow-x: hidden;overflow-y:auto;">
			<fly:foreach items="${transStepBar}" var="entity" indexVar="entityIndex">
				<div id="trans-step-${entityIndex}" class="fly-flow-step-panel">
					<fly:browse target="trans-step-${entityIndex}" value="${transStepBrowses[entityIndex]}" />
				</div>
			</fly:foreach>
		</div>
	</div>
	<div id="transContent" class="fly-flow-content fly-editor-content-height fly-editor-content-width">
		<div id="transEditorToolbar" class="ui-toolbar ui-widget-header ui-helper-clearfix ui-corner-all">
            <fly:pushbutton id="trans_run" icon="ui-icon-editor-run" onclick="" title="运行" />
            <fly:pushbutton type="separator" />
            <fly:pushbutton id="trans_edit" icon="ui-icon-editor-edit" onclick="" title="画线" />
		</div>
		<div id="transEditorPanel" oncontextmenu="return false;" class="fly-editor-content-width-no-padding fly-editor-content-height-editor ui-helper-clearfix" style="margin:0 5px;">
		</div>
	</div>
	<div id="transPropBar" class="fly-flow-prop-bar fly-editor-stepbar-content-height ui-corner-top">
		<div class="ui-widget-header ui-helper-clearfix" style="border: medium none;">
			<div class="ui-toolbar-group-left">缩略图</div>
			<div class="ui-toolbar-group-right">
				<span id="thumbClose" class="ui-button ui-icon ui-icon-circle-minus" onclick="Plywet.desktop.toggleContent('transOverviewPanel',this);"></span>
			</div>
		</div>
		<div id="transOverviewPanel" class="fly-overview ui-widget-content ui-helper-clearfix">
		</div>
		<div class="ui-widget-header ui-helper-clearfix ui-corner-top">
			<div class="ui-toolbar-group-left">数据源</div>
			<div class="ui-toolbar-group-right">
				<span id="datasourceClose" class="ui-button ui-icon ui-icon-circle-minus" onclick="Plywet.desktop.toggleContent('datasource',this);"></span>
			</div>
		</div>
		<div id="datasource" class="ui-widget-content ui-helper-clearfix">
			
		</div>
	</div>
</div>
