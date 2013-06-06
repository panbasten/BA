<form action="rest/fs/items/folder/createsubmit" method="post" id="fs_folder_create_form">

	<fly:gridLayout column="2" itemWidth="30%,70%">
		<fly:gridLayoutItem>
			<fly:labelObject buddy="desc" text="文件夹名称" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="desc" name="desc" type="text" validate="required:true" value="" />
		</fly:gridLayoutItem>
	</fly:gridLayout>

	<fly:inputText id="category" name="category" type="hidden" value="${category}" />
	<fly:inputText id="rootId" name="rootId" type="hidden" value="${rootId}" />
	<fly:inputText id="path" name="path" type="hidden" value="${path}" />

</form>
