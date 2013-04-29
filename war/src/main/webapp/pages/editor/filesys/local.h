<form action="rest/fslocal/setting" method="POST" id="fs_fslocal_form">
	<fly:gridLayout column="4" itemWidth="15%,35%,15%,35%">
		<fly:gridLayoutItem>
			<fly:labelObject buddy="desc" text="目录名称" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="desc" name="desc" type="text" validate="required:true" value="${directory.desc}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="path" text="目录路径" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="path" name="path" type="text" validate="required:true" value="${directory.path}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="notes" text="备注" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="notes" name="notes" type="text" validate="required:true" value="${directory.notes}" />
		</fly:gridLayoutItem>
	</fly:gridLayout>
	
	<fly:inputText id="id" name="id" type="hidden" value="${directory.id}" />
</form>