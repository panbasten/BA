<form action="rest/fs/action/upload" method="POST" id="fs_upload_form" enctype="multipart/form-data"
		target="fs_upload_space_frame">

	<fly:gridLayout column="2" itemWidth="30%,70%">
		<fly:gridLayoutItem>
			<fly:labelObject text="上传文件1：" buddy="fs1" style="margin:5px 30px 5px 5px;float:right;" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:inputText id="fs1" name="fs1" type="file" size="30" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:labelObject text="上传文件2：" buddy="fs2" style="margin:5px 30px 5px 5px;float:right;" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:inputText id="fs2" name="fs2" type="file" size="30" style="border:0 none;" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:labelObject text="上传文件3：" buddy="fs3" style="margin:5px 30px 5px 5px;float:right;" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:inputText id="fs3" name="fs3" type="file" size="30" style="border:0 none;" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:labelObject text="上传文件4：" buddy="fs4" style="margin:5px 30px 5px 5px;float:right;" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:inputText id="fs4" name="fs4" type="file" size="30" style="border:0 none;" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:labelObject text="上传文件5：" buddy="fs5" style="margin:5px 30px 5px 5px;float:right;" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:inputText id="fs5" name="fs5" type="file" size="30" style="border:0 none;" />
		</fly:gridLayoutItem>
	</fly:gridLayout>

	<input type="hidden" id="rootId" name="rootId" value="${rootId}"></input>
	<input type="hidden" id="path" name="path" value="${path}"></input>
	<input type="hidden" id="category" name="category" value="${category}"></input>
	<iframe id="fs_upload_space_frame" name="fs_upload_space_frame" class="ui-space-frame" onload="Plywet.filesys.uploadResult(this);"></iframe>
</form>
