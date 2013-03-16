<form action="rest/fsop/rename" method="POST" id="fs_rename_form">
	<fly:layout type="float" column="1">
		<fly:layoutItem>
			<fly:labelObject for="newname" title="输入新名称" labelDiv="2">
				<fly:inputText id="newName" name="newName" type="text" validate="required:true" value="${srcName}" />
			</fly:labelObject>
		</fly:layoutItem>
	</fly:layout>
	<input type="hidden" id="rootId" name="rootId" value="${rootId}"></input>
	<input type="hidden" id="path" name="path" value="${path}"></input>
	<input type="hidden" id="category" name="category" value="${category}"></input>
</form>