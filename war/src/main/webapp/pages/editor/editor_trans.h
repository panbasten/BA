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
			<fly:pushbutton id="trans_run" icon="ui-icon-editor-run" onclick="" title="运行" />
			<fly:pushbutton id="trans_runstep" icon="ui-icon-editor-runstep" onclick="" title="单步运行" />
			<fly:pushbutton id="trans_runreturn" icon="ui-icon-editor-return" onclick="" title="重复运行" />
			<fly:pushbutton id="trans_pause" icon="ui-icon-editor-pause" onclick="" title="暂停" state="disabled" />
			<fly:pushbutton id="trans_stop" icon="ui-icon-editor-stop" onclick="" title="停止" state="disabled" />
			<fly:pushbutton id="trans_validate" icon="ui-icon-editor-validate" onclick="" title="验证" />
			<fly:pushbutton id="trans_analize" icon="ui-icon-editor-analize" onclick="" title="分析对数据库影响" />
			<fly:pushbutton id="trans_show" icon="ui-icon-editor-show" onclick="" title="显示结果" />
			<fly:pushbutton type="separator" />
			<fly:pushbutton id="trans_edit" icon="ui-icon-editor-edit" onclick="Plywet.editors.trans.action.changeOuterControlType('trans','inner:dealCreateHop','edit');" title="画线" />
			<fly:pushbutton id="trans_magnify" icon="ui-icon-editor-zoom-in" onclick="Plywet.editors.trans.action.changeOuterControlType('trans','magnify');" title="放大" />
			<fly:pushbutton id="trans_lessen" icon="ui-icon-editor-zoom-out" onclick="Plywet.editors.trans.action.changeOuterControlType('trans','lessen');" title="缩小" />
			<fly:pushbutton id="trans_partMagnify" icon="ui-icon-editor-zoom-part" onclick="Plywet.editors.trans.action.changeOuterControlType('trans','partMagnify');" title="局部放大" />
			<fly:pushbutton id="trans_zoom_100" icon="ui-icon-editor-zoom-100" onclick="Plywet.editors.trans.action.editorZoom100('trans');" title="100%" />
			<fly:pushbutton id="trans_zoom_fit" icon="ui-icon-editor-zoom-fit" onclick="Plywet.editors.trans.action.editorFix('trans');" title="合适比例" />
			<fly:pushbutton id="trans_screenMove" icon="ui-icon-editor-move" onclick="Plywet.editors.trans.action.changeOuterControlType('trans','screenMove');" title="屏幕移动" />
			<fly:pushbutton type="separator" />
			<fly:pushbutton id="trans_grid_show" icon="ui-icon-grid-show" onclick="Plywet.editors.trans.action.showGrid('trans');" title="显示网格" />
			<fly:pushbutton id="trans_grid_close" icon="ui-icon-grid-close" onclick="Plywet.editors.trans.action.closeGrid('trans');" title="贴近网格" state="disabled" />
			<fly:pushbutton type="separator" />
			<fly:pushbutton id="trans_cut" icon="ui-icon-system-cut" onclick="" title="剪切" state="disabled" />
			<fly:pushbutton id="trans_copy" icon="ui-icon-system-copy" onclick="" title="复制" state="disabled" />
			<fly:pushbutton id="trans_paste" icon="ui-icon-system-paste" onclick="" title="粘贴" state="disabled" />
			<fly:pushbutton id="trans_delete" icon="ui-icon-system-delete" onclick="Plywet.editors.trans.action.deleteSelectedElFromOutset('trans');" title="删除" state="disabled" />
			<fly:pushbutton type="separator" />
			<fly:pushbutton id="trans_save" icon="ui-icon-save" onclick="Plywet.editors.trans.action.save('trans');" title="保存" />
			<fly:pushbutton id="trans_saveas" icon="ui-icon-saveas" onclick="" title="另存为..." />
			<fly:pushbutton id="trans_save_xml" icon="ui-icon-save-xml" onclick="" title="保存为xml文件并下载" />
			<fly:pushbutton id="trans_save_image" icon="ui-icon-save-image" onclick="" title="保存为图片并下载" />
		</div>
		<div id="${editorId}EditorPanel" oncontextmenu="return false;" class="ui-helper-clearfix">
		</div>
	</div>
	<div id="${editorId}PropBar" class="fly-flow-prop-bar ui-corner-top">
		<div id="${editorId}ThumbPanel" class="ui-widget-panel">
			<div class="ui-widget-header ui-helper-clearfix ui-corner-top">
				<div class="ui-toolbar-group-left">缩略图</div>
				<div class="ui-toolbar-group-right">
					<span id="${editorId}ThumbClose" class="ui-button ui-icon ui-icon-circle-minus" onclick="Plywet.editors.trans.toggleContent('thumbPane');"></span>
				</div>
			</div>
			<div id="${editorId}ThumbContent" class="ui-widget-content">
			</div>
		</div>
		<div id="${editorId}DSPanel" class="ui-widget-panel">
			<div class="ui-widget-header ui-helper-clearfix ui-corner-top">
				<div class="ui-toolbar-group-left">数据源</div>
				<div class="ui-toolbar-group-right">
					<span id="${editorId}DSClose" class="ui-button ui-icon ui-icon-circle-minus" onclick="Plywet.editors.trans.toggleContent('dsPane');"></span>
				</div>
			</div>
			<div id="${editorId}DSContent" class="ui-widget-content" style="overflow: auto;">
				
			</div>
		</div>
	</div>
</div>