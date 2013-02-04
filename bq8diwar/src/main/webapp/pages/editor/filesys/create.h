<form action="rest/fsop/create" method="POST" id="fs_create_form">
	<bq:layout type="float" column="1">
		<bq:layoutItem>
			<bq:labelObject for="dirName" title="输入目录名" labelDiv="2">
				<bq:inputText id="dirName" name="dirName" type="text" validate="required:true" />
			</bq:labelObject>
		</bq:layoutItem>
	</bq:layout>
	<input type="hidden" id="rootId" name="rootId" value="${rootId}"></input>
	<input type="hidden" id="path" name="path" value="${path}"></input>
	<input type="hidden" id="category" name="category" value="${category}"></input>
</form>