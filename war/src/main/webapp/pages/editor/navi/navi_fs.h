<div id="editorContent-navi-${entity.code}" class="fly-editor-tab">
	<div id="editorContent-navi-filesys-bc" class="ui-breadcrumb ui-widget-header ui-helper-clearfix ui-corner-all">
		<div class="ui-toolbar-group-left" style="width: 20%;">
			<ul>
			</ul>
		</div>
		<div class="ui-toolbar-group-right">
            <fly:pushbutton icon="ui-icon-folder-open" label="新增" title="新增" onclick="Plywet.filesys.create()" />
            <fly:pushbutton icon="ui-icon-folder-open" label="编辑" title="编辑" onclick="Plywet.filesys.edit()" />
            <fly:pushbutton icon="ui-icon-folder-open" label="移除" title="移除" onclick="Plywet.filesys.remove()" />
            <fly:pushbutton icon="ui-icon-folder-open" label="创建目录" title="创建目录" onclick="Plywet.filesys.createDir()" />
            <fly:pushbutton icon="ui-icon-folder-open" label="上传" title="上传" onclick="Plywet.filesys.uploadFile()" />
            <fly:pushbutton icon="ui-icon-folder-open" label="下载" title="下载" onclick="Plywet.filesys.downloadFile()" />
		</div>
	</div>
	<div id="editorContent-navi-filesys-bp" class="fly-editor-content-height-browse-panel">
	</div>
	<iframe id="space_frame"></iframe>
</div>
