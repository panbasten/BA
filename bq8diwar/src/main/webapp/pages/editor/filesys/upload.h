<form action="rest/fsop/upload" method="POST" id="fs_upload_form" enctype="multipart/form-data" target="space_frame">
	<div id="files_div">
		<input type="file" name="file"></input>
	</div>
	<input type="hidden" id="rootId" name="rootId" value="${rootId}"></input>
	<input type="hidden" id="path" name="path" value="${path}"></input>
	<input type="hidden" id="category" name="category" value="${category}"></input>
	<iframe id="space_frame"></iframe>
</form>