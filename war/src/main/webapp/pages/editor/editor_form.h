<div id="${editorId}" style="display:none;">
	<div id="${editorId}StepBar" class="fly-flow-step-bar fly-editor-stepbar-content-height ui-tab-side-container ui-corner-top">
		<ul id="${editorId}StepBar-ul" class="ui-tabs">
			<fly:foreach items="${formStepBar}" var="entity" indexVar="entityIndex">
				<li>
					<a href="#${editorId}-step-${entityIndex}">${entity}</a>
				</li>
			</fly:foreach>
		</ul>
		<div id="plugin_panel" class="ui-tab-panel-container fly-editor-stepbar-height-no-padding ui-scrollbar" style="overflow-x: hidden;overflow-y:auto;">
			<fly:foreach items="${formStepBar}" var="entity" indexVar="entityIndex">
				<div id="${editorId}-step-${entityIndex}" class="fly-flow-step-panel">
					<fly:browse target="${editorId}-step-${entityIndex}" value="${formStepBrowses[entityIndex]}" />
				</div>
			</fly:foreach>
		</div>
	</div>
	<div id="${editorId}Content" class="fly-flow-content fly-editor-content-height fly-editor-content-width">
		<div id="${editorId}EditorToolbar" class="ui-toolbar ui-widget-header ui-helper-clearfix ui-corner-all">
			<fly:pushbutton id="trans_run" icon="ui-icon-editor-run" onclick="" title="运行" />
			<fly:pushbutton type="separator" />
			<fly:pushbutton id="trans_edit" icon="ui-icon-editor-edit" onclick="" title="画线" />
		</div>
		<div id="${editorId}EditorPanel" oncontextmenu="return false;" class="fly-editor-content-width-no-padding fly-editor-content-height-editor ui-helper-clearfix" 
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
		var cxt=myCanvas.getContext("2d");
		cxt.lineWidth=1;

		cxt.fillStyle='#000';
		cxt.strokeStyle='#00f';
		cxt.fillRect(3,3,4,4);
		cxt.strokeRect(2,2,6,6);
		cxt.fillRect(3,403,4,4);
		cxt.strokeRect(2,402,6,6);
		cxt.fillRect(603,403,4,4);
		cxt.strokeRect(602,402,6,6);
		cxt.fillRect(603,3,4,4);
		cxt.strokeRect(602,2,6,6);

		cxt.strokeStyle='#f00';
		cxt.strokeRect(35,35,70,30);

		cxt.fillStyle='#a0a0a0';
		cxt.strokeStyle='#00f';
		cxt.fillRect(33,33,4,4);
		cxt.strokeRect(32,32,6,6);
		cxt.fillRect(33,63,4,4);
		cxt.strokeRect(32,62,6,6);
		cxt.fillRect(103,63,4,4);
		cxt.strokeRect(102,62,6,6);
		cxt.fillRect(103,33,4,4);
		cxt.strokeRect(102,32,6,6);
	</script>
	
	<div id="${editorId}PropBar" class="fly-flow-prop-bar fly-editor-stepbar-content-height ui-corner-top">
		<div class="ui-widget-header ui-helper-clearfix" style="border: medium none;">
			<div class="ui-toolbar-group-left">结构</div>
			<div class="ui-toolbar-group-right">
				<span id="thumbClose" class="ui-button ui-icon ui-icon-circle-minus" onclick="Plywet.desktop.toggleContent('${editorId}OverviewPanel',this);"></span>
			</div>
		</div>
		<div id="${editorId}OverviewPanel" class="fly-overview ui-widget-content ui-helper-clearfix">
		</div>
		<div class="ui-widget-header ui-helper-clearfix ui-corner-top">
			<div class="ui-toolbar-group-left">属性</div>
			<div class="ui-toolbar-group-right">
				<span id="datasourceClose" class="ui-button ui-icon ui-icon-circle-minus" onclick="Plywet.desktop.toggleContent('datasource',this);"></span>
			</div>
		</div>
		<div id="datasource" class="ui-widget-content ui-helper-clearfix">
			
		</div>
	</div>
</div>