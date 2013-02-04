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
			<bq:button id="trans_runstep" icon="ui-icon-editor-runstep" onclick="" title="单步运行" />
			<bq:button id="trans_runreturn" icon="ui-icon-editor-return" onclick="" title="重复运行" />
			<bq:button id="trans_pause" icon="ui-icon-editor-pause" onclick="" title="暂停" state="disabled" />
			<bq:button id="trans_stop" icon="ui-icon-editor-stop" onclick="" title="停止" state="disabled" />
			<bq:button id="trans_validate" icon="ui-icon-editor-validate" onclick="" title="验证" />
			<bq:button id="trans_analize" icon="ui-icon-editor-analize" onclick="" title="分析对数据库影响" />
			<bq:button id="trans_show" icon="ui-icon-editor-show" onclick="" title="显示结果" />
			<bq:button type="separator" />
			<bq:button id="trans_edit" icon="ui-icon-editor-edit" onclick="YonYou.editor.changeOuterControlType('trans','inner:dealCreateHop','edit');" title="画线" />
			<bq:button id="trans_magnify" icon="ui-icon-editor-zoom-in" onclick="YonYou.editor.changeOuterControlType('trans','magnify');" title="放大" />
			<bq:button id="trans_lessen" icon="ui-icon-editor-zoom-out" onclick="YonYou.editor.changeOuterControlType('trans','lessen');" title="缩小" />
			<bq:button id="trans_partMagnify" icon="ui-icon-editor-zoom-part" onclick="YonYou.editor.changeOuterControlType('trans','partMagnify');" title="局部放大" />
			<bq:button id="trans_zoom_100" icon="ui-icon-editor-zoom-100" onclick="YonYou.editor.editorZoom100('trans');" title="100%" />
			<bq:button id="trans_zoom_fit" icon="ui-icon-editor-zoom-fit" onclick="YonYou.editor.editorFix('trans');" title="合适比例" />
			<bq:button id="trans_screenMove" icon="ui-icon-editor-move" onclick="YonYou.editor.changeOuterControlType('trans','screenMove');" title="屏幕移动" />
			<bq:button type="separator" />
			<bq:button id="trans_grid_show" icon="ui-icon-grid-show" onclick="YonYou.editor.showGrid('trans');" title="显示网格" />
			<bq:button id="trans_grid_close" icon="ui-icon-grid-close" onclick="YonYou.editor.closeGrid('trans');" title="贴近网格" state="disabled" />
			<bq:button type="separator" />
			<bq:button id="trans_cut" icon="ui-icon-system-cut" onclick="" title="剪切" state="disabled" />
			<bq:button id="trans_copy" icon="ui-icon-system-copy" onclick="" title="复制" state="disabled" />
			<bq:button id="trans_paste" icon="ui-icon-system-paste" onclick="" title="粘贴" state="disabled" />
			<bq:button id="trans_delete" icon="ui-icon-system-delete" onclick="YonYou.editor.deleteSelectedElFromOutset('trans');" title="删除" state="disabled" />
			<bq:button type="separator" />
			<bq:button id="trans_save" icon="ui-icon-save" onclick="YonYou.editor.save('trans');" title="保存" />
			<bq:button id="trans_saveas" icon="ui-icon-saveas" onclick="" title="另存为..." />
			<bq:button id="trans_save_xml" icon="ui-icon-save-xml" onclick="" title="保存为xml文件并下载" />
			<bq:button id="trans_save_image" icon="ui-icon-save-image" onclick="" title="保存为图片并下载" />
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