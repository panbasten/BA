<div id="editorContent-navi-${entity.code}" class="fly-editor-tab">
	<div id="editorContent-navi-${entity.code}-bc" class="navbar navbar-inverse">
		<div class="container-fluid">
			<div class="navbar-brand">
				数据源
			</div>
			<div class="navbar-right">
	            <fly:pushbutton btnStyle="link" label="新增" title="新增"
	            	onclick="Flywet.db.create()" />
	            <fly:pushbutton btnStyle="link" label="编辑" title="编辑"
	            	onclick="Flywet.db.edit()" />
	            <fly:pushbutton btnStyle="link" label="删除" title="删除"
	            	onclick="Flywet.db.remove()" />
			</div>
		</div>
	</div>
	<div id="editorContent-navi-${entity.code}-bp" class="fly-editor-content-height-browse-panel">
	</div>
</div>
