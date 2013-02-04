<div id="${editorId}" style="display:none;">
	<div id="${editorId}StepBar" class="hb-flow-step-bar hb-editor-stepbar-content-height ui-tab-side-container ui-corner-top">
		<ul id="${editorId}StepBar-ul" class="ui-tabs">
			<bq:foreach items="${transStepBar}" var="entity" indexVar="entityIndex">
				<li>
					<a href="#trans-step-${entityIndex}">${entity}</a>
				</li>
			</bq:foreach>
		</ul>
		<div id="plugin_panel" class="ui-tab-panel-container hb-editor-stepbar-height-no-padding ui-scrollbar" style="overflow-x: hidden;overflow-y:auto;">
			<bq:foreach items="${transStepBar}" var="entity" indexVar="entityIndex">
				<div id="trans-step-${entityIndex}" class="hb-flow-step-panel">
					<bq:browse target="trans-step-${entityIndex}" value="${transStepBrowses[entityIndex]}" />
				</div>
			</bq:foreach>
		</div>
	</div>
	<div id="transContent" class="hb-flow-content hb-editor-content-height hb-editor-content-width">
		<div id="transEditorToolbar" class="ui-toolbar ui-widget-header ui-helper-clearfix ui-corner-all">
			<bq:button id="trans_run" icon="ui-icon-editor-run" onclick="" title="运行" />
			<bq:button type="separator" />
			<bq:button id="trans_edit" icon="ui-icon-editor-edit" onclick="" title="画线" />
		</div>
		<div id="transEditorPanel" oncontextmenu="return false;" class="hb-editor-content-width-no-padding hb-editor-content-height-editor ui-helper-clearfix" style="margin:0 5px;">
		</div>
	</div>
	<div id="transPropBar" class="hb-flow-prop-bar hb-editor-stepbar-content-height ui-corner-top">
		<div class="ui-widget-header ui-helper-clearfix" style="border: medium none;">
			<div class="ui-toolbar-group-left">缩略图</div>
			<div class="ui-toolbar-group-right">
				<span id="thumbClose" class="ui-button ui-icon ui-icon-circle-minus" onclick="YonYou.desktop.toggleContent('transOverviewPanel',this);"></span>
			</div>
		</div>
		<div id="transOverviewPanel" class="hb-overview ui-widget-content ui-helper-clearfix">
		</div>
		<div class="ui-widget-header ui-helper-clearfix ui-corner-top">
			<div class="ui-toolbar-group-left">数据源</div>
			<div class="ui-toolbar-group-right">
				<span id="datasourceClose" class="ui-button ui-icon ui-icon-circle-minus" onclick="YonYou.desktop.toggleContent('datasource',this);"></span>
			</div>
		</div>
		<div id="datasource" class="ui-widget-content ui-helper-clearfix">
			
		</div>
	</div>
</div>