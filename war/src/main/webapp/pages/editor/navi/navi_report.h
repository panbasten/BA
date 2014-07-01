<div id="editorContent-navi-${entity.code}" class="fly-editor-tab">
	<div id="editorContent-navi-${entity.code}-bc" class="navbar navbar-inverse">
		<div class="container-fluid">
			<div class="navbar-brand">
				<ul class="breadcrumb">
				</ul>
			</div>
			<div class="navbar-right">
				<fly:pushbutton btnStyle="link" label="新增" title="新增" onclick="Flywet.report.add()" />
				<fly:pushbutton btnStyle="link" label="编辑" title="编辑" onclick="Flywet.report.edit()" />
				<fly:pushbutton btnStyle="link" label="删除" title="删除" onclick="Flywet.report.remove()" />
				<fly:pushbutton btnStyle="link" label="创建目录" title="创建目录" onclick="Flywet.report.createDir()" />
				<fly:pushbutton btnStyle="link" label="上传" title="上传" onclick="Flywet.report.uploadFile()" />
				<fly:pushbutton btnStyle="link" label="下载" title="下载" onclick="Flywet.report.downloadFile()" />
			</div>
		</div>
	</div>
	<div id="editorContent-navi-${entity.code}-bp" class="fly-editor-content-height-browse-panel">
	</div>
</div>
