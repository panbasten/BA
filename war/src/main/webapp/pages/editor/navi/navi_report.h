<div id="editorContent-navi-${entity.code}" class="fly-editor-tab">
	<div id="editorContent-navi-${entity.code}-bc" class="ui-breadcrumb ui-widget-header ui-helper-clearfix ui-corner-all">
		<div class="ui-toolbar-group-left" style="width: 20%;">
			<ul>
			</ul>
		</div>
		<div class="ui-toolbar-group-right">
			<fly:pushbutton icon="ui-icon-folder-open" 
				label="新增" title="新增" onclick="Plywet.report.add()" />
			<fly:pushbutton icon="ui-icon-folder-open" 
				label="编辑" title="编辑" onclick="Plywet.report.edit()" />
			<fly:pushbutton icon="ui-icon-folder-open" 
				label="删除" title="删除" onclick="Plywet.report.remove()" />
			<fly:pushbutton icon="ui-icon-folder-open" 
				label="创建目录" title="创建目录" onclick="Plywet.report.createDir()" />
			<fly:pushbutton icon="ui-icon-folder-open" 
				label="上传" title="上传" onclick="Plywet.report.uploadFile()" />
			<fly:pushbutton icon="ui-icon-folder-open" 
				label="下载" title="下载" onclick="Plywet.report.downloadFile()" />
		</div>
	</div>
	<div id="editorContent-navi-${entity.code}-bp" class="fly-editor-content-height-browse-panel">
	</div>
</div>
