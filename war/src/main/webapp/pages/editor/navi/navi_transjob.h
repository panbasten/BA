<div id="editorContent-navi-${entity.code}" class="fly-editor-tab">
	<div id="editorContent-navi-trans-bc" class="ui-breadcrumb ui-widget-header ui-helper-clearfix ui-corner-all ui-tranc-priv">
		<div class="ui-toolbar-group-left" style="width: 20%;">
			<ul>
			</ul>
		</div>
		<div class="ui-toolbar-group-right">
			<fly:pushbutton icon="ui-icon-folder-open"
            	label="新增" title="新增"
            	onclick="Plywet.transjob.create()" />
            <fly:pushbutton icon="ui-icon-folder-open"
            	label="编辑" title="编辑"
            	onclick="Plywet.transjob.edit()" />
            <fly:pushbutton icon="ui-icon-folder-open"
            	label="删除" title="删除"
            	onclick="Plywet.transjob.remove()" />
            <fly:pushbutton icon="ui-icon-folder-open"
            	label="创建目录" title="创建目录"
            	onclick="Plywet.transjob.createDir()" />
			<fly:pushbutton icon="ui-icon-folder-open"
            	label="上传" title="上传"
            	onclick="Plywet.transjob.uploadFile()" />
            <fly:pushbutton icon="ui-icon-folder-open"
            	label="下载" title="下载"
            	onclick="Plywet.transjob.downloadFile()" />
		</div>
	</div>
	<div id="editorContent-navi-trans-bp" class="fly-editor-content-height-browse-panel">
	</div>
</div>
