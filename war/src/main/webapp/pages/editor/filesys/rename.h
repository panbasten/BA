<form action="rest/fsop/rename" method="POST" id="fs_rename_form">
	
	<fly:gridLayout column="4" itemWidth="15%,35%,15%,35%">
		<fly:gridLayoutItem>
			<fly:labelObject for="newname" title="输入新名称" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem cols="3">
			<fly:inputText id="newName" name="newName" type="text" validate="required:true" value="${srcName}" />
		</fly:gridLayoutItem>
	</fly:gridLayout>
	
	<fly:inputText id="rootId" name="rootId" type="hidden" value="${rootId}" />
	<fly:inputText id="path" name="path" type="hidden" value="${path}" />
	<fly:inputText id="category" name="category" type="hidden" value="${category}" />
	
</form>