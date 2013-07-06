<div id="editorContent-navi-${entity.code}" class="fly-editor-tab">
	<div id="editorContent-navi-db-bc" class="ui-breadcrumb ui-widget-header ui-helper-clearfix ui-corner-all">
		<div class="ui-toolbar-group-left" style="width: 20%;">
			数据源
		</div>
		<div class="ui-toolbar-group-right">
            <fly:pushbutton icon="ui-icon-folder-open" 
            	label="新增" title="新增"
            	onclick="Plywet.database.create()" />
            <fly:pushbutton icon="ui-icon-folder-open" 
            	label="编辑" title="编辑"
            	onclick="Plywet.database.edit()" />
            <fly:pushbutton icon="ui-icon-folder-open" 
            	label="删除" title="删除"
            	onclick="Plywet.database.remove()" />
		</div>
	</div>
	<div id="editorContent-navi-db-bp" class="fly-editor-content-height-browse-panel">
	</div>
</div>
