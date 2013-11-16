<div id="${editorId}">
	<div id="${editorId}StepBar" class="fly-flow-step-bar ui-tab-side-container ui-corner-top">
		<ul id="${editorId}StepBar-ul" class="ui-tabs">
			<fly:foreach items="${dashboardStepBar}" var="entity" indexVar="entityIndex">
				<li>
					<a href="#${editorId}-step-${entityIndex}">${entity}</a>
				</li>
			</fly:foreach>
		</ul>
		<div id="${editorId}PluginPanel" class="ui-tab-panel-container ui-scrollbar" style="overflow-x: hidden;overflow-y:auto;">
			<fly:foreach items="${dashboardStepBar}" var="entity" indexVar="entityIndex">
				<div id="${editorId}-step-${entityIndex}" class="fly-flow-step-panel">
					<fly:browse target="${editorId}-step-${entityIndex}" value="${dashboardStepBrowses[entityIndex]}" />
				</div>
			</fly:foreach>
		</div>
	</div>
	<div id="${editorId}Content" class="fly-flow-content">
		<div id="${editorId}EditorToolbar" class="ui-toolbar ui-widget-header ui-helper-clearfix ui-corner-top">
			<fly:pushbutton id="dashboard_editor_component" iconCls="ui-icon-dashboard-edit"
				onclick="Flywet.editors.dashboard.action.editorComponent();" title="编辑控件" state="active" />
			<fly:pushbutton id="dashboard_editor_signal_slot" iconCls="ui-icon-dashboard-signal"
				onclick="Flywet.editors.dashboard.action.editorSignalSlot();" title="编辑信号/槽" />
			<fly:pushbutton id="dashboard_editor_buddy" iconCls="ui-icon-dashboard-buddy"
				onclick="Flywet.editors.dashboard.action.editorBuddy();" title="编辑伙伴" />
			<fly:pushbutton id="dashboard_editor_tab_sequence" iconCls="ui-icon-dashboard-tab"
				onclick="Flywet.editors.dashboard.action.editorTab();" title="编辑Tab顺序" />
			<fly:pushbutton type="separator" />
			<fly:pushbutton id="dashboard_layout_horizontal" iconCls="ui-icon-dashboard-layout-horizontal" 
				onclick="Flywet.editors.dashboard.action.layoutHorizontal();" title="水平布局" state="disabled" />
			<fly:pushbutton id="dashboard_layout_vertical" iconCls="ui-icon-dashboard-layout-vertical" 
				onclick="Flywet.editors.dashboard.action.layoutVertical();" title="垂直布局" state="disabled" />
			<fly:pushbutton id="dashboard_layout_grid" iconCls="ui-icon-dashboard-layout-grid" 
				onclick="Flywet.editors.dashboard.action.layoutGrid();" title="栅格布局" state="disabled" />
			<fly:pushbutton id="dashboard_layout_broke" iconCls="ui-icon-dashboard-layout-broke" 
				onclick="Flywet.editors.dashboard.action.layoutBroke();" title="打破布局" state="disabled" />
			<fly:pushbutton type="separator" />
			<fly:pushbutton id="dashboard_resize" iconCls="ui-icon-dashboard-resize" 
				onclick="Flywet.editors.dashboard.action.resize();" title="调整尺寸" />
			<fly:pushbutton id="dashboard_preview" iconCls="ui-icon-dashboard-preview" 
				onclick="Flywet.editors.dashboard.action.preview();" title="预览" />
			<fly:pushbutton id="dashboard_save" iconCls="ui-icon-save" 
				onclick="Flywet.editors.dashboard.action.save();" title="保存" />
			<fly:pushbutton type="separator" />
			<fly:combobox id="dashboard_size" name="dashboard_size" editable="false" type="text" value="" width="80">
				<fly:option value="0" label="1" />
			</fly:combobox>
		</div>
		
		<div id="${editorId}EditorPanel" oncontextmenu="return false;" class="fly-editor ui-helper-clearfix" 
			style="margin:0 5px;overflow:auto;">
				<!-- margin 需要处理按字号处理高度，还要考虑是否有边框，以及边框宽度 -->
				<div id="${editorId}EditorPanelWrapper" class="fly-editor-wrapper fly-dashboard-editor-wrapper">
					<div id="${editorId}EditorPanelContent" class="fly-editor-content fly-dashboard-editor-content"></div>
				</div>
		</div>
		
		<div id="${editorId}SignalBar" class="fly-flow-signal-bar">
			<div class="ui-widget-header ui-helper-clearfix ui-corner-top">
				<div class="ui-toolbar-group-left">
					<fly:pushbutton iconCls="ui-icon-plusthick" onclick="Flywet.editors.dashboard.action.signal_add_on_click();" title="添加" />
					<fly:pushbutton iconCls="ui-icon-closethick" onclick="Flywet.editors.dashboard.action.signal_delete_on_click();" title="删除" />
				</div>
				<div class="ui-toolbar-group-right">
				</div>
			</div>
			<div id="${editorId}SignalPanelContent" class="ui-widget-content ui-corner-bottom" style="overflow:auto;">
				
			</div>
		</div>
	</div>
	
	<div id="${editorId}PropBar" class="fly-flow-prop-bar">
		<div id="${editorId}StructPanel" class="ui-widget-panel">
			<div class="ui-widget-header ui-helper-clearfix ui-corner-top">
				<div class="ui-toolbar-group-left">结构</div>
				<div class="ui-toolbar-group-right">
					<span id="${editorId}StructClose" class="ui-button ui-icon ui-icon-circle-minus" onclick="Flywet.editors.dashboard.toggleContent('structPane');"></span>
				</div>
			</div>
			<div id="${editorId}StructPanelContent" class="ui-widget-content ui-corner-bottom" style="overflow:auto;">
				
			</div>
		</div>
		<div id="${editorId}PropPanel" class="ui-widget-panel" style="padding:0;">
			<div id="${editorId}PropPanelContent" class="ui-widget-content">
				
			</div>
		</div>
	</div>
</div>
