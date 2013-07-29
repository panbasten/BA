<div id="${editorId}">
	<div id="${editorId}StepBar" class="fly-flow-step-bar ui-tab-side-container ui-corner-top">
		<ul id="${editorId}StepBar-ul" class="ui-tabs">
			<fly:foreach items="${transStepBar}" var="entity" indexVar="entityIndex">
				<li>
					<a href="#${editorId}-step-${entityIndex}">${entity}</a>
				</li>
			</fly:foreach>
		</ul>
		<div id="${editorId}PluginPanel" class="ui-tab-panel-container ui-scrollbar" style="overflow-x: hidden;overflow-y:auto;">
			<fly:foreach items="${transStepBar}" var="entity" indexVar="entityIndex">
				<div id="${editorId}-step-${entityIndex}" class="fly-flow-step-panel">
					<fly:browse target="${editorId}-step-${entityIndex}" value="${transStepBrowses[entityIndex]}" />
				</div>
			</fly:foreach>
		</div>
	</div>
	<div id="${editorId}Content" class="fly-flow-content">
		<div id="${editorId}EditorToolbar" class="ui-toolbar ui-widget-header ui-helper-clearfix ui-corner-all">
			<fly:pushbutton id="trans_run" iconCls="ui-icon-editor-run" onclick="Flywet.editors.trans.action.run();" title="运行" />
			<fly:pushbutton id="trans_runreturn" iconCls="ui-icon-editor-return" onclick="" title="重复运行" />
			<fly:pushbutton id="trans_pause" iconCls="ui-icon-editor-pause" onclick="" title="暂停" state="disabled" />
			<fly:pushbutton id="trans_stop" iconCls="ui-icon-editor-stop" onclick="" title="停止" state="disabled" />
			<fly:pushbutton id="trans_validate" iconCls="ui-icon-editor-validate" onclick="Flywet.editors.trans.action.check();" title="验证" />
			<fly:pushbutton id="trans_analize" iconCls="ui-icon-editor-analize" onclick="Flywet.editors.trans.action.analyse();" title="分析对数据库影响" />
			<fly:pushbutton id="trans_show" iconCls="ui-icon-editor-show" onclick="" title="显示结果" />
			<fly:pushbutton type="separator" />
			<fly:pushbutton id="trans_edit" iconCls="ui-icon-editor-edit" onclick="Flywet.editors.trans.action.changeOuterControlType('inner:dealCreateHop','edit');" title="画线" />
			<fly:pushbutton id="trans_magnify" iconCls="ui-icon-editor-zoom-in" onclick="Flywet.editors.trans.action.changeOuterControlType('magnify');" title="放大" />
			<fly:pushbutton id="trans_lessen" iconCls="ui-icon-editor-zoom-out" onclick="Flywet.editors.trans.action.changeOuterControlType('lessen');" title="缩小" />
			<fly:pushbutton id="trans_partMagnify" iconCls="ui-icon-editor-zoom-part" onclick="Flywet.editors.trans.action.changeOuterControlType('partMagnify');" title="局部放大" />
			<fly:pushbutton id="trans_zoom_100" iconCls="ui-icon-editor-zoom-100" onclick="Flywet.editors.trans.action.editorZoom100();" title="100%" />
			<fly:pushbutton id="trans_zoom_fit" iconCls="ui-icon-editor-zoom-fit" onclick="Flywet.editors.trans.action.editorFix();" title="合适比例" />
			<fly:pushbutton id="trans_screenMove" iconCls="ui-icon-editor-move" onclick="Flywet.editors.trans.action.changeOuterControlType('screenMove');" title="屏幕移动" />
			<fly:pushbutton type="separator" />
			<fly:pushbutton id="trans_grid_show" iconCls="ui-icon-grid-show" onclick="Flywet.editors.trans.action.showGrid();" title="显示网格" />
			<fly:pushbutton id="trans_grid_close" iconCls="ui-icon-grid-close" onclick="Flywet.editors.trans.action.closeGrid();" title="贴近网格" state="disabled" />
			<fly:pushbutton type="separator" />
			<fly:pushbutton id="trans_cut" iconCls="ui-icon-system-cut" onclick="" title="剪切" state="disabled" />
			<fly:pushbutton id="trans_copy" iconCls="ui-icon-system-copy" onclick="" title="复制" state="disabled" />
			<fly:pushbutton id="trans_paste" iconCls="ui-icon-system-paste" onclick="" title="粘贴" state="disabled" />
			<fly:pushbutton id="trans_delete" iconCls="ui-icon-system-delete" onclick="Flywet.editors.trans.action.deleteSelectedElFromOutset();" title="删除" state="disabled" />
			<fly:pushbutton type="separator" />
			<fly:pushbutton id="trans_save" iconCls="ui-icon-save" onclick="Flywet.editors.trans.action.save();" title="保存" />
			<fly:pushbutton id="trans_saveas" iconCls="ui-icon-saveas" onclick="Flywet.editors.trans.action.saveas();" title="另存为..." />
			<fly:pushbutton id="trans_save_xml" iconCls="ui-icon-save-xml" onclick="Flywet.editors.trans.action.saveXml();" title="保存为xml文件并下载" />
			<fly:pushbutton id="trans_save_image" iconCls="ui-icon-save-image" onclick="Flywet.editors.trans.action.saveImage();" title="保存为图片并下载" />
		</div>
		<div id="${editorId}EditorPanel" oncontextmenu="return false;" class="ui-helper-clearfix">
		</div>
	</div>
	<div id="${editorId}PropBar" class="fly-flow-prop-bar ui-corner-top">
		<div id="${editorId}ThumbPanel" class="ui-widget-panel">
			<div class="ui-widget-header ui-helper-clearfix ui-corner-top">
				<div class="ui-toolbar-group-left">缩略图</div>
				<div class="ui-toolbar-group-right">
					<span id="${editorId}ThumbClose" class="ui-button ui-icon ui-icon-circle-minus" onclick="Flywet.editors.trans.toggleContent('thumbPane');"></span>
				</div>
			</div>
			<div id="${editorId}ThumbContent" class="ui-widget-content">
			</div>
		</div>
		<div id="${editorId}DSPanel" class="ui-widget-panel">
			<div class="ui-widget-header ui-helper-clearfix ui-corner-top">
				<div class="ui-toolbar-group-left">数据源</div>
				<div class="ui-toolbar-group-right">
					<span id="${editorId}DSClose" class="ui-button ui-icon ui-icon-circle-minus" onclick="Flywet.editors.trans.toggleContent('dsPane');"></span>
				</div>
			</div>
			<div id="${editorId}DSContent" class="ui-widget-content" style="overflow: auto;">
				
			</div>
		</div>
	</div>
</div>
