<div id="${editorId}">
	<div id="${editorId}StepBar" class="fly-flow-step-bar ui-tab-side-container ui-corner-all">
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
			<fly:pushbutton id="dashboard_edit_component" icon="ui-icon-dashboard-edit" 
				onclick="" title="编辑控件" />
			<fly:pushbutton id="dashboard_edit_signal_slot" icon="ui-icon-dashboard-signal" 
				onclick="" title="编辑信号/槽" />
			<fly:pushbutton id="dashboard_edit_buddy" icon="ui-icon-dashboard-buddy" 
				onclick="" title="编辑伙伴" />
			<fly:pushbutton id="dashboard_edit_tab_sequence" icon="ui-icon-dashboard-tab" 
				onclick="" title="编辑Tab顺序" />
			<fly:pushbutton type="separator" />
			<fly:pushbutton id="dashboard_layout_horizontal" icon="ui-icon-dashboard-layout-horizontal" 
				onclick="" title="水平布局" />
			<fly:pushbutton id="dashboard_layout_vertical" icon="ui-icon-dashboard-layout-vertical" 
				onclick="" title="垂直布局" />
			<fly:pushbutton id="dashboard_layout_grid" icon="ui-icon-dashboard-layout-grid" 
				onclick="" title="栅格布局" />
			<fly:pushbutton id="dashboard_layout_broke" icon="ui-icon-dashboard-layout-broke" 
				onclick="" title="打破布局" />
			<fly:pushbutton type="separator" />
			<fly:pushbutton id="dashboard_resize" icon="ui-icon-dashboard-resize" 
				onclick="" title="调整尺寸" />
			<fly:pushbutton id="dashboard_preview" icon="ui-icon-dashboard-preview" 
				onclick="" title="预览" />
		</div>
		
		<div id="${editorId}EditorPanel" oncontextmenu="return false;" class="fly-editor ui-helper-clearfix" 
			style="margin:0 5px;position:relative;overflow:auto;">
				<!-- margin 需要处理按字号处理高度，还要考虑是否有边框，以及边框宽度 -->
		</div>
		
		<div id="${editorId}SignalBar" class="fly-flow-signal-bar">
			<div class="ui-widget-header ui-helper-clearfix ui-corner-top">
				<div class="ui-toolbar-group-left">
					<fly:pushbutton icon="ui-icon-plusthick" onclick="Plywet.editors.dashboard.action.signal_add_on_click();" title="添加" />
					<fly:pushbutton icon="ui-icon-closethick" onclick="Plywet.editors.dashboard.action.signal_delete_on_click();" title="删除" />
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
					<span id="${editorId}StructClose" class="ui-button ui-icon ui-icon-circle-minus" onclick="Plywet.editors.dashboard.toggleContent('structPane');"></span>
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
