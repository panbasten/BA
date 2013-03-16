<form action="rest/fsop/create" method="POST" id="fs_create_form">
	<fly:layout type="float" column="1">
		<fly:layoutItem>
			<fly:labelObject for="dirName" title="输入目录名" labelDiv="2">
				<fly:inputText id="dirName" name="dirName" type="text" validate="required:true" />
			</fly:labelObject>
		</fly:layoutItem>
	</fly:layout>
	<input type="hidden" id="rootId" name="rootId" value="${rootId}"></input>
	<input type="hidden" id="path" name="path" value="${path}"></input>
	<input type="hidden" id="category" name="category" value="${category}"></input>
</form>