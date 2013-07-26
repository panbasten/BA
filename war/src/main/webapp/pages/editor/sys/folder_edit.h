<form method="post" id="folder_edit_form">

	<fly:gridLayout column="2" itemWidth="30%,70%" itemMargin="10">
		<fly:gridLayoutItem>
			<fly:labelObject buddy="desc" text="目录名称" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="desc" name="desc" type="text" validate="required:true" value="${desc}" />
		</fly:gridLayoutItem>
	</fly:gridLayout>

	<fly:inputText id="pDirId" name="pDirId" type="hidden" value="${pDirId}" />
	<fly:inputText id="dirId" name="dirId" type="hidden" value="${dirId}" />

</form>
