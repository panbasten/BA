<div id="${editorId}">
	<div id="${editorId}StepBar" class="fly-flow-step-bar ui-tab-side-container ui-corner-top">
		<ul id="${editorId}StepBar-ul" class="ui-tabs">
			<fly:foreach items="${formStepBar}" var="entity" indexVar="entityIndex">
				<li>
					<a href="#${editorId}-step-${entityIndex}">${entity}</a>
				</li>
			</fly:foreach>
		</ul>
		<div id="${editorId}PluginPanel" class="ui-tab-panel-container ui-scrollbar" style="overflow-x: hidden;overflow-y:auto;">
			<fly:foreach items="${formStepBar}" var="entity" indexVar="entityIndex">
				<div id="${editorId}-step-${entityIndex}" class="fly-flow-step-panel">
					<fly:browse target="${editorId}-step-${entityIndex}" value="${formStepBrowses[entityIndex]}" />
				</div>
			</fly:foreach>
		</div>
	</div>
	<div id="${editorId}Content" class="fly-flow-content">
		<div id="${editorId}EditorToolbar" class="ui-toolbar ui-widget-header ui-helper-clearfix ui-corner-all">
			<fly:pushbutton id="form_edit_component" icon="ui-icon-editor-edit-component" 
				onclick="" title="编辑控件" />
			<fly:pushbutton id="form_edit_signal_slot" icon="ui-icon-editor-edit-signal-slot" 
				onclick="" title="编辑信号/槽" />
			<fly:pushbutton id="form_edit_buddy" icon="ui-icon-editor-edit-buddy" 
				onclick="" title="编辑伙伴" />
			<fly:pushbutton id="form_edit_tab_sequence" icon="ui-icon-editor-edit-tab-sequence" 
				onclick="" title="编辑Tab顺序" />
			<fly:pushbutton type="separator" />
			<fly:pushbutton id="form_layout_horizontal" icon="ui-icon-editor-layout-horizontal" 
				onclick="" title="水平布局" />
			<fly:pushbutton id="form_layout_vertical" icon="ui-icon-editor-layout-vertical" 
				onclick="" title="垂直布局" />
			<fly:pushbutton id="form_layout_grid" icon="ui-icon-editor-layout-grid" 
				onclick="" title="栅格布局" />
			<fly:pushbutton id="form_layout_broke" icon="ui-icon-editor-layout-broke" 
				onclick="" title="打破布局" />
			<fly:pushbutton type="separator" />
			<fly:pushbutton id="form_resize" icon="ui-icon-editor-resize" 
				onclick="" title="调整尺寸" />
		</div>
		<div id="${editorId}EditorPanel" oncontextmenu="return false;" class="ui-helper-clearfix" 
			style="margin:0 5px;position:relative;overflow:auto;">
			<div class="fly-form-editor-content" style="width:600px;height:400px;">
				<!-- margin 需要处理按字号处理高度，还要考虑是否有边框，以及边框宽度 -->
				<div class="fly-form-editor-component" style="width:70px;height:30px;top:30px;left:30px;"><label style="width:70px;height:16px;margin: 7px 0;">Find what:</label></div>
				<div class="fly-form-editor-component" style="width:150px;height:30px;top:30px;left:100px;"><input type="text" style="width:148px;height:18px;margin: 5px 0;" value="" /></div>
			
			</div>
			<canvas id="myCanvas" width="610" height="410" class="fly-form-editor-canvas"></canvas>
		</div>
	</div>
	
	<script>
		var myCanvas=document.getElementById("myCanvas");
		var ctx=myCanvas.getContext("2d");
		ctx.lineWidth=1;



	Plywet.widget.FlowChartUtils.drawResizer(ctx, {x:0,y:0,width:600,height:400},{x:5,y:5});

	Plywet.widget.FlowChartUtils.drawRect(ctx, {x:30,y:30,width:70,height:30}, {strokeStyle:'#f00',fillStyle:'#f00',isFill:true}, "line", {x:5,y:5});
	
	Plywet.widget.FlowChartUtils.drawResizer(ctx, {x:30,y:30,width:70,height:30},{x:5,y:5},{fillStyle:'#a0a0a0'});

		




	</script>
	
	<div id="${editorId}PropBar" class="fly-flow-prop-bar ui-corner-top">
		<div id="${editorId}StructPanel" class="ui-widget-panel">
			<div class="ui-widget-header ui-helper-clearfix ui-corner-top" style="border: medium none;">
				<div class="ui-toolbar-group-left">结构</div>
				<div class="ui-toolbar-group-right">
					<span id="${editorId}StructClose" class="ui-button ui-icon ui-icon-circle-minus" onclick="Plywet.editors.form.toggleContent('structPane');"></span>
				</div>
			</div>
			<div class="ui-widget-content" style="overflow:auto;">
				<p>11111</p>
				<p>11111</p>
				<p>11111</p>
				<p>11111</p>
				<p>11111</p>
				<p>11111</p>
				<p>11111</p>
				<p>11111</p>
				<p>11111</p>
				<p>11111</p>
			</div>
		</div>
		<div id="${editorId}PropPanel" class="ui-widget-panel">
			<div class="ui-widget-header ui-helper-clearfix ui-corner-top">
				<div class="ui-toolbar-group-left">属性</div>
				<div class="ui-toolbar-group-right">
					<span id="${editorId}PropClose" class="ui-button ui-icon ui-icon-circle-minus" onclick="Plywet.editors.form.toggleContent('propPane');"></span>
				</div>
			</div>
			<div class="ui-widget-content">
			</div>
		</div>
	</div>
</div>