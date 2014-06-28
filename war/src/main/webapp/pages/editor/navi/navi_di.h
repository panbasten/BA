<div id="editorContent-navi-${entity.code}" class="fly-editor-tab">
	<div id="editorContent-navi-${entity.code}-bc" class="navbar navbar-inverse">
		<div class="container-fluid">
			<div class="navbar-brand">
				<ul class="breadcrumb">
				</ul>
			</div>
			<div class="navbar-right">
				<fly:pushbutton btnStyle="link" label="新增" title="新增">
					<fly:menuItem text="新增转换" iconCls="ui-icon-folder-open" onclick="Flywet.di.create('trans')"></fly:menuItem>
					<fly:menuItem text="新增作业" iconCls="ui-icon-folder-open" onclick="Flywet.di.create('job')"></fly:menuItem>
					<fly:menuItem text="新增目录" iconCls="ui-icon-folder-open" onclick="Flywet.di.createDir()"></fly:menuItem>
				</fly:pushbutton>
	            <fly:pushbutton btnStyle="link" label="编辑" title="编辑"
	            	onclick="Flywet.di.edit()" />
	            <fly:pushbutton btnStyle="link" label="删除" title="删除"
	            	onclick="Flywet.di.remove()" />
				<fly:pushbutton btnStyle="link" label="上传" title="上传"
	            	onclick="Flywet.di.uploadFile()" />
	            <fly:pushbutton btnStyle="link" label="下载" title="下载"
	            	onclick="Flywet.di.downloadFile()" />
			</div>
		</div>
	</div>
	<div id="editorContent-navi-${entity.code}-bp" class="fly-editor-content-height-browse-panel">
	</div>
</div>
