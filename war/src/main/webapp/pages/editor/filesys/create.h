<form action="rest/fsop/create" method="POST" id="fs_create_form">
	<fly:gridLayout column="2" itemWidth="30%,70%">
		<fly:gridLayoutItem>
			<fly:labelObject buddy="dirName" text="输入目录名" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:inputText id="dirName" name="dirName" type="text" validate="required:true" />
		</fly:gridLayoutItem>
	</fly:gridLayout>
	
	<fly:inputText id="rootId" name="rootId" type="hidden" value="${rootId}" />
	
	<fly:inputText id="path" name="path" type="hidden" value="${path}" />
	
	<fly:inputText id="category" name="category" type="hidden" value="${category}" />
	
</form>