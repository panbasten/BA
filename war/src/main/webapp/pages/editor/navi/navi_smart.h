<div id="editorContent-navi-${entity.code}" class="fly-editor-tab">
	<div id="editorContent-navi-${entity.code}-bc" class="ui-breadcrumb ui-widget-header ui-helper-clearfix ui-corner-all">
		<div class="ui-toolbar-group-left" style="width: 20%;">
			<ul>
			</ul>
		</div>
		<div class="ui-toolbar-group-right">
            <fly:pushbutton iconCls="ui-icon-folder-open" btnStyle="link"
            	label="新增" title="新增">
				<fly:menuItem text="新增语义模型" iconCls="ui-icon-folder-open" onclick="Flywet.smart.create('smart')"></fly:menuItem>
				<fly:menuItem text="新增多维模型" iconCls="ui-icon-folder-open" onclick="Flywet.smart.create('cube')"></fly:menuItem>
				<fly:menuItem text="新增目录" iconCls="ui-icon-folder-open" onclick="Flywet.smart.createDir()"></fly:menuItem>
			</fly:pushbutton>
            <fly:pushbutton iconCls="ui-icon-folder-open" btnStyle="link"
            	label="编辑" title="编辑"
            	onclick="Flywet.smart.edit()" />
            <fly:pushbutton iconCls="ui-icon-folder-open" btnStyle="link"
            	label="删除" title="删除"
            	onclick="Flywet.smart.remove()" />
			<fly:pushbutton iconCls="ui-icon-folder-open" btnStyle="link"
            	label="上传" title="上传"
            	onclick="Flywet.smart.uploadFile()" />
            <fly:pushbutton iconCls="ui-icon-folder-open" btnStyle="link"
            	label="下载" title="下载"
            	onclick="Flywet.smart.downloadFile()" />
		</div>
	</div>
	<div id="editorContent-navi-${entity.code}-bp" class="fly-editor-content-height-browse-panel">
	</div>
</div>
