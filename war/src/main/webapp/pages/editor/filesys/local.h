<form action="rest/fslocal/setting" method="POST" id="fs_fslocal_form">
	<fly:layout type="float" column="1">
		<fly:layoutItem>
			<fly:labelObject for="desc" title="目录名称" labelDiv="2">
				<fly:inputText id="desc" name="desc" type="text" validate="required:true" value="${directory.desc}" />
			</fly:labelObject>
			<fly:labelObject for="path" title="目录路径" labelDiv="2">
				<fly:inputText id="path" name="path" type="text" validate="required:true" value="${directory.path}" />
			</fly:labelObject>
			<fly:labelObject for="notes" title="备注" labelDiv="2">
				<fly:inputText id="notes" name="notes" type="text" validate="required:true" value="${directory.notes}" />
			</fly:labelObject>
		</fly:layoutItem>
	</fly:layout>
	<input type="hidden" id="id" name="id" value="${directory.id}"></input>
</form>