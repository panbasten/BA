<div id="${editorId}" style="display:none;">
	<div id="${editorId}StepBar" class="hb-flow-step-bar hb-editor-stepbar-content-height ui-tab-side-container ui-corner-top">
		<ul id="${editorId}StepBar-ul" class="ui-tabs">
			<bq:foreach items="${formStepBar}" var="entity" indexVar="entityIndex">
				<li>
					<a href="#${editorId}-step-${entityIndex}">${entity}</a>
				</li>
			</bq:foreach>
		</ul>
		<div id="plugin_panel" class="ui-tab-panel-container hb-editor-stepbar-height-no-padding ui-scrollbar" style="overflow-x: hidden;overflow-y:auto;">
			<bq:foreach items="${formStepBar}" var="entity" indexVar="entityIndex">
				<div id="${editorId}-step-${entityIndex}" class="hb-flow-step-panel">
					<bq:browse target="${editorId}-step-${entityIndex}" value="${formStepBrowses[entityIndex]}" />
				</div>
			</bq:foreach>
		</div>
	</div>
	<div id="${editorId}Content" class="hb-flow-content hb-editor-content-height hb-editor-content-width">
		<div id="${editorId}EditorToolbar" class="ui-toolbar ui-widget-header ui-helper-clearfix ui-corner-all">
			<bq:pushbutton id="trans_run" icon="ui-icon-editor-run" onclick="" title="运行" />
			<bq:pushbutton type="separator" />
			<bq:pushbutton id="trans_edit" icon="ui-icon-editor-edit" onclick="" title="画线" />
		</div>
		<div id="${editorId}EditorPanel" oncontextmenu="return false;" class="hb-editor-content-width-no-padding hb-editor-content-height-editor ui-helper-clearfix" style="margin:0 5px;">
		</div>
	</div>
	<div id="${editorId}PropBar" class="hb-flow-prop-bar hb-editor-stepbar-content-height ui-corner-top">
		<div class="ui-widget-header ui-helper-clearfix" style="border: medium none;">
			<div class="ui-toolbar-group-left">结构</div>
			<div class="ui-toolbar-group-right">
				<span id="thumbClose" class="ui-button ui-icon ui-icon-circle-minus" onclick="YonYou.desktop.toggleContent('${editorId}OverviewPanel',this);"></span>
			</div>
		</div>
		<div id="${editorId}OverviewPanel" class="hb-overview ui-widget-content ui-helper-clearfix">
		</div>
		<div class="ui-widget-header ui-helper-clearfix ui-corner-top">
			<div class="ui-toolbar-group-left">属性</div>
			<div class="ui-toolbar-group-right">
				<span id="datasourceClose" class="ui-button ui-icon ui-icon-circle-minus" onclick="YonYou.desktop.toggleContent('datasource',this);"></span>
			</div>
		</div>
		<div id="datasource" class="ui-widget-content ui-helper-clearfix">
			
		</div>
	</div>
</div>