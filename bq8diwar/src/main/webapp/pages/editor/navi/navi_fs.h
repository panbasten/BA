<div id="editorContent-navi-${entity.code}" class="hb-editor-tab">
	<div id="editorContent-navi-filesys-bc" class="ui-breadcrumb ui-widget-header ui-helper-clearfix ui-corner-all">
		<div class="ui-toolbar-group-left" style="width: 20%;">
			<ul>
			</ul>
		</div>
		<div class="ui-toolbar-group-right">
			<bq:button icon="ui-icon-folder-open" label="新增" title="新增" onclick="YonYou.filesys.add()" />
			<bq:button icon="ui-icon-folder-open" label="编辑" title="编辑" onclick="YonYou.filesys.edit()" />
			<bq:button icon="ui-icon-folder-open" label="移除" title="移除" onclick="YonYou.filesys.remove()" />
			<bq:button icon="ui-icon-folder-open" label="创建目录" title="创建目录" onclick="YonYou.filesys.createDir()" />
			<bq:button icon="ui-icon-folder-open" label="重命名" title="重命名" onclick="YonYou.filesys.rename()" />
			<bq:button icon="ui-icon-folder-open" label="复制" title="复制" onclick="YonYou.filesys.copy()" />
			<bq:button icon="ui-icon-folder-open" label="剪切" title="剪切" onclick="YonYou.filesys.cut()" />
			<bq:button icon="ui-icon-folder-open" label="粘贴" title="粘贴" onclick="YonYou.filesys.paste()" />
			<bq:button icon="ui-icon-folder-open" label="删除" title="删除" onclick="YonYou.filesys.delFile()" />
			<bq:button icon="ui-icon-folder-open" label="上传" title="上传" onclick="YonYou.filesys.uploadFile()" />
			<bq:button icon="ui-icon-folder-open" label="下载" title="下载" onclick="YonYou.filesys.downloadFile()" />
		</div>
	</div>
	<div id="editorContent-navi-filesys-bp" class="hb-editor-content-height-browse-panel">
	</div>
	<iframe id="space_frame"></iframe>
</div>