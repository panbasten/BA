<div id="${editorId}">
	<div id="${editorId}StepBar" class="fly-flow-step-bar ui-tab-side-container">
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
		<div id="${editorId}EditorToolbar" class="fly-editor-toolbar navbar">
			<fly:pushbuttongroup>
				<fly:pushbutton id="trans_run" iconCls="ui-icon ui-icon-editor-run" btnStyle="primary" btnSize="mini" onclick="Flywet.editors.trans.action.run();" title="运行" />
				<fly:pushbutton id="trans_runreturn" iconCls="ui-icon ui-icon-editor-return" btnStyle="primary" btnSize="mini" onclick="" title="重复运行" />
				<fly:pushbutton id="trans_pause" iconCls="ui-icon ui-icon-editor-pause" btnStyle="primary" btnSize="mini" onclick="" title="暂停" />
				<fly:pushbutton id="trans_stop" iconCls="ui-icon ui-icon-editor-stop" btnStyle="primary" btnSize="mini" onclick="" title="停止" />
				<fly:pushbutton id="trans_validate" iconCls="ui-icon ui-icon-editor-validate" btnStyle="primary" btnSize="mini" onclick="Flywet.editors.trans.action.check();" title="验证" />
				<fly:pushbutton id="trans_analize" iconCls="ui-icon ui-icon-editor-analize" btnStyle="primary" btnSize="mini" onclick="Flywet.editors.trans.action.analyse();" title="分析对数据库影响" />
				<fly:pushbutton id="trans_show" iconCls="ui-icon ui-icon-editor-show" btnStyle="primary" btnSize="mini" onclick="Flywet.editors.trans.action.showLog();" title="显示结果" />
			</fly:pushbuttongroup>
			<fly:pushbuttongroup>
				<fly:pushbutton id="trans_edit" iconCls="ui-icon ui-icon-editor-edit" btnStyle="primary" btnSize="mini" onclick="Flywet.editors.trans.action.changeOuterControlType('inner:dealCreateHop','edit');" title="画线" />
				<fly:pushbutton id="trans_magnify" iconCls="ui-icon ui-icon-editor-zoom-in" btnStyle="primary" btnSize="mini" onclick="Flywet.editors.trans.action.changeOuterControlType('magnify');" title="放大" />
				<fly:pushbutton id="trans_lessen" iconCls="ui-icon ui-icon-editor-zoom-out" btnStyle="primary" btnSize="mini" onclick="Flywet.editors.trans.action.changeOuterControlType('lessen');" title="缩小" />
				<fly:pushbutton id="trans_partMagnify" iconCls="ui-icon ui-icon-editor-zoom-part" btnStyle="primary" btnSize="mini" onclick="Flywet.editors.trans.action.changeOuterControlType('partMagnify');" title="局部放大" />
				<fly:pushbutton id="trans_zoom_100" iconCls="ui-icon ui-icon-editor-zoom-100" btnStyle="primary" btnSize="mini" onclick="Flywet.editors.trans.action.editorZoom100();" title="100%" />
				<fly:pushbutton id="trans_zoom_fit" iconCls="ui-icon ui-icon-editor-zoom-fit" btnStyle="primary" btnSize="mini" onclick="Flywet.editors.trans.action.editorFix();" title="合适比例" />
				<fly:pushbutton id="trans_screenMove" iconCls="ui-icon ui-icon-editor-move" btnStyle="primary" btnSize="mini" onclick="Flywet.editors.trans.action.changeOuterControlType('screenMove');" title="屏幕移动" />
			</fly:pushbuttongroup>
			<fly:pushbuttongroup>
				<fly:pushbutton id="trans_grid_show" iconCls="ui-icon ui-icon-grid-show" btnStyle="primary" btnSize="mini" onclick="Flywet.editors.trans.action.showGrid();" title="显示网格" />
				<fly:pushbutton id="trans_grid_close" iconCls="ui-icon ui-icon-grid-close" btnStyle="primary" btnSize="mini" onclick="Flywet.editors.trans.action.closeGrid();" title="贴近网格" />
			</fly:pushbuttongroup>
			<fly:pushbuttongroup>
				<fly:pushbutton id="trans_cut" iconCls="ui-icon ui-icon-system-cut" btnStyle="primary" btnSize="mini" onclick="" title="剪切" />
				<fly:pushbutton id="trans_copy" iconCls="ui-icon ui-icon-system-copy" btnStyle="primary" btnSize="mini" onclick="" title="复制" />
				<fly:pushbutton id="trans_paste" iconCls="ui-icon ui-icon-system-paste" btnStyle="primary" btnSize="mini" onclick="" title="粘贴" />
				<fly:pushbutton id="trans_delete" iconCls="ui-icon ui-icon-system-delete" btnStyle="primary" btnSize="mini" onclick="Flywet.editors.trans.action.deleteSelectedElFromOutset();" title="删除" />
			</fly:pushbuttongroup>
			<fly:pushbuttongroup>
				<fly:pushbutton id="trans_save" iconCls="ui-icon ui-icon-save" btnStyle="primary" btnSize="mini" onclick="Flywet.editors.trans.action.save();" title="保存" />
				<fly:pushbutton id="trans_saveas" iconCls="ui-icon ui-icon-saveas" btnStyle="primary" btnSize="mini" onclick="Flywet.editors.trans.action.saveas();" title="另存为..." />
				<fly:pushbutton id="trans_save_xml" iconCls="ui-icon ui-icon-save-xml" btnStyle="primary" btnSize="mini" onclick="Flywet.editors.trans.action.saveXml();" title="保存为xml文件并下载" />
				<fly:pushbutton id="trans_save_image" iconCls="ui-icon ui-icon-save-image" btnStyle="primary" btnSize="mini" onclick="Flywet.editors.trans.action.saveImage();" title="保存为图片并下载" />
			</fly:pushbuttongroup>
		</div>
		<div id="${editorId}EditorPanel" oncontextmenu="return false;" class="clearfix">
		</div>
	</div>
	<div id="${editorId}PropBar" class="fly-flow-prop-bar ui-corner-top fly-editor-side-bar-only">
		<div id="${editorId}ThumbPanel" class="panel">
			<div class="navbar navbar-inverse">
				<div class="ui-toolbar-group-left">缩略图</div>
				<div class="ui-toolbar-group-right">
					<span id="${editorId}ThumbClose" class="ui-icon ui-icon-circle-minus" onclick="Flywet.editors.trans.toggleContent('thumbPane');"></span>
				</div>
			</div>
			<div id="${editorId}ThumbContent">
			</div>
		</div>
		<div id="${editorId}DSPanel" class="panel">
			<div class="navbar navbar-inverse fly-editor-side-bar-only">
				<div class="ui-toolbar-group-left">数据源</div>
				<div class="ui-toolbar-group-right">
					<span id="${editorId}DSClose" class="ui-icon ui-icon-circle-minus" onclick="Flywet.editors.trans.toggleContent('dsPane');"></span>
				</div>
			</div>
			<fly:tree id="${editorId}DSContent" class="ui-widget-content"
				style="overflow: auto;" dnd="true" data="${transObjectTree}" />
		</div>
	</div>
	<div id="${editorId}LogBar" class="fly-flow-log-bar ui-corner-top">
		<fly:tabView>
			<fly:tab id="${editorId}LogBar_transLog" title="转换日志表">
				<fly:dataGrid id="${editorId}LogBarTransLog" singleSelect="true" height="205" data="">
					<fly:columns>
						<fly:row>
							<fly:column field="key" title="批次ID" width="70" align="center" />
							<fly:column field="value" title="状态" width="50" align="center" />
							<fly:column field="value" title="读" width="50" align="center" />
							<fly:column field="value" title="写" width="50" align="center" />
							<fly:column field="value" title="更新" width="50" align="center" />
							<fly:column field="value" title="输入" width="50" align="center" />
							<fly:column field="value" title="输出" width="50" align="center" />
							<fly:column field="value" title="舍弃" width="50" align="center" />
							<fly:column field="value" title="错误" width="50" align="center" />
							<fly:column field="value" title="启动日期" width="70" align="center" />
							<fly:column field="value" title="结束日期" width="70" align="center" />
							<fly:column field="value" title="日志日期" width="70" align="center" />
							<fly:column field="value" title="依赖日期" width="70" align="center" />
							<fly:column field="value" title="启动日期" width="70" align="center" />
							<fly:column field="value" title="执行服务器" width="70" align="center" />
							<fly:column field="value" title="执行用户" width="70" align="center" />
						</fly:row>
					</fly:columns>
				</fly:dataGrid>
			</fly:tab>
			<fly:tab id="${editorId}LogBar_stepLog" title="步骤日志表">
				<fly:dataGrid id="${editorId}LogBarStepLog" singleSelect="true" height="205" data="">
					<fly:columns>
						<fly:row>
							<fly:column field="key" title="批次ID" width="70" align="center" />
							<fly:column field="value" title="编号" width="50" align="center" />
							<fly:column field="value" title="日志日期" width="70" align="center" />
							<fly:column field="value" title="转换名称" width="70" align="center" />
							<fly:column field="value" title="步骤名称" width="70" align="center" />
							<fly:column field="value" title="复制" width="50" align="center" />
							<fly:column field="value" title="读" width="50" align="center" />
							<fly:column field="value" title="写" width="50" align="center" />
							<fly:column field="value" title="更新" width="50" align="center" />
							<fly:column field="value" title="输入" width="50" align="center" />
							<fly:column field="value" title="输出" width="50" align="center" />
							<fly:column field="value" title="舍弃" width="50" align="center" />
							<fly:column field="value" title="错误" width="50" align="center" />
							<fly:column field="value" title="输入缓冲区" width="70" align="center" />
							<fly:column field="value" title="输出缓冲区" width="70" align="center" />
						</fly:row>
					</fly:columns>
				</fly:dataGrid>
			</fly:tab>
			<fly:tab id="${editorId}LogBar_logTable" title="日志通道表">
				<fly:dataGrid id="${editorId}LogBarLogTable" singleSelect="true" height="205" data="">
					<fly:columns>
						<fly:row>
							<fly:column field="key" title="批次ID" width="70" align="center" />
							<fly:column field="value" title="通道ID" width="50" align="center" />
							<fly:column field="value" title="日志日期" width="70" align="center" />
							<fly:column field="value" title="类型" width="50" align="center" />
							<fly:column field="value" title="名称" width="50" align="center" />
							<fly:column field="value" title="复制" width="50" align="center" />
							<fly:column field="value" title="目录" width="50" align="center" />
							<fly:column field="value" title="文件名" width="50" align="center" />
							<fly:column field="value" title="ID" width="50" align="center" />
							<fly:column field="value" title="修订" width="50" align="center" />
							<fly:column field="value" title="父通道ID" width="70" align="center" />
							<fly:column field="value" title="根通道ID" width="70" align="center" />
						</fly:row>
					</fly:columns>
				</fly:dataGrid>
			</fly:tab>
			<fly:tab id="${editorId}LogBar_log" title="日志">
				aaaa
			</fly:tab>
			<fly:tab id="${editorId}LogBar_step" title="步骤度量">
				<fly:dataGrid id="${editorId}LogBarStep" singleSelect="true" height="205" data="">
					<fly:columns>
						<fly:row>
							<fly:column field="key" title="步骤名称" width="150" align="center" />
							<fly:column field="value" title="复制的记录行数" width="150" align="center" />
							<fly:column field="value" title="读" width="50" align="center" />
							<fly:column field="value" title="写" width="50" align="center" />
							<fly:column field="value" title="输入" width="50" align="center" />
							<fly:column field="value" title="输出" width="50" align="center" />
							<fly:column field="value" title="更新" width="50" align="center" />
							<fly:column field="value" title="舍弃" width="50" align="center" />
							<fly:column field="value" title="错误" width="50" align="center" />
							<fly:column field="value" title="激活" width="50" align="center" />
							<fly:column field="value" title="时间" width="50" align="center" />
							<fly:column field="value" title="速度(记录行/秒)" width="150" align="center" />
							<fly:column field="value" title="Pri/in/out" width="100" align="center" />
						</fly:row>
					</fly:columns>
				</fly:dataGrid>
			</fly:tab>
			<fly:tab id="${editorId}LogBar_graph" title="性能图">
				aaaa
			</fly:tab>
		</fly:tabView>
	</div>
</div>
