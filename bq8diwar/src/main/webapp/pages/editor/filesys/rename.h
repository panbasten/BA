<form action="rest/fsop/rename" method="POST" id="fs_rename_form">
	<bq:layout type="float" column="1">
		<bq:layoutItem>
			<bq:labelObject for="newname" title="输入新名称" labelDiv="2">
				<bq:inputText id="newName" name="newName" type="text" validate="required:true" value="${srcName}" />
			</bq:labelObject>
		</bq:layoutItem>
	</bq:layout>
	<input type="hidden" id="rootId" name="rootId" value="${rootId}"></input>
	<input type="hidden" id="path" name="path" value="${path}"></input>
	<input type="hidden" id="category" name="category" value="${category}"></input>
</form>