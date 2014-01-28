<div id="#{editorId}">
	<div id="#{editorId}StepBar" class="hb-flow-step-bar hb-editor-stepbar-content-height tab-side-container ui-corner-top">
		<ul class="tabs hb-editor-stepbar-content-height">
			#{navigator_items}
		</ul>
		<div id="plugin_panel" class="panel-container hb-editor-stepbar-height-no-padding" style="overflow-x: hidden;overflow-y:auto;">
			#{plugin_category}
		</div>
	</div>
	<div id="transContent" class="hb-flow-content hb-editor-content-height hb-editor-content-width">
		<div class="ui-toolbar ui-widget-header ui-helper-clearfix ui-corner-all">
			<bq:button id="test" icon="ui-icon-newwin" label="添加" title="只有图片按钮" />
			<bq:button id="test11" icon="ui-icon-refresh" title="只有图片按钮" />
			<bq:button id="test22" icon="ui-icon-shuffle" title="只有图片按钮" />
			<bq:button id="test33" icon="ui-icon-folder-open" title="只有图片按钮" />
			<bq:button type="separator" />
			<bq:button id="test1" label="正常按钮" title="正常按钮" />
			<bq:button id="test2" icon="aaa" label="无效按钮" title="无效按钮" state="disabled" />
			<bq:button id="test3" icon="aaa" label="激活按钮" title="激活按钮" state="active" />
			<bq:button type="separator" />
			<bq:button id="trans_screenMove" icon="ui-icon-newwin" onclick="YonYou.editor.changeOuterControlType('trans','screenMove');" title="屏幕移动" />
		</div>
		<div id="transEditorPanel" oncontextmenu="return false;" class="hb-editor-content-height-browse-panel hb-editor-content-width-no-padding ui-helper-clearfix" style="margin:0 5px;">
		</div>
	</div>
	<div id="transPropBar" class="hb-flow-prop-bar hb-editor-stepbar-content-height ui-corner-top">
		<div class="ui-widget-header ui-helper-clearfix" style="border: medium none;">
			<div class="ui-toolbar-group-left">缩略图</div>
			<div class="ui-toolbar-group-right">
				<span id="thumbClose" class="ui-button ui-icon ui-icon-circle-minus" onclick="YonYou.desktop.toggleContent('thumb',this);"></span>
			</div>
		</div>
		<div id="thumb" class="ui-widget-content ui-helper-clearfix">
			缩略图
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