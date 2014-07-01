<div id="editorContent-navi-${entity.code}" class="fly-editor-tab">
	<div id="editorContent-navi-${entity.code}-bc" class="navbar navbar-inverse">
		<div class="container-fluid">
			<div class="navbar-brand">
				<ul class="breadcrumb">
				</ul>
			</div>
			<div class="navbar-right">
	            <fly:pushbutton id="fs-btn-create" show="N" btnStyle="link"
	            	label="新增" title="新增" onclick="Flywet.filesys.create()" />
	            <fly:pushbutton id="fs-btn-edit" show="N" btnStyle="link"
	            	label="编辑" title="编辑" onclick="Flywet.filesys.edit()" />
	            <fly:pushbutton id="fs-btn-remove" show="N" btnStyle="link"
	            	label="删除" title="删除" onclick="Flywet.filesys.remove()" />
	            <fly:pushbutton id="fs-btn-create-dir" show="N" btnStyle="link"
	            	label="创建目录" title="创建目录" onclick="Flywet.filesys.createDir()" />
	            <fly:pushbutton id="fs-btn-upload-file" show="N" btnStyle="link"
	            	label="上传" title="上传" onclick="Flywet.filesys.uploadFile()" />
	            <fly:pushbutton id="fs-btn-download-file" show="N" btnStyle="link"
	            	label="下载" title="下载" onclick="Flywet.filesys.downloadFile()" />
			</div>
		</div>
	</div>
	<div id="editorContent-navi-${entity.code}-bp" class="fly-editor-content-height-browse-panel">
	</div>
	<iframe id="filesys-space-frame" class="ui-space-frame"></iframe>
</div>
