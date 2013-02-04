<form action="rest/fslocal/setting" method="POST" id="fs_fslocal_form">
	<bq:layout type="float" column="1">
		<bq:layoutItem>
			<bq:labelObject for="desc" title="目录名称" labelDiv="2">
				<bq:inputText id="desc" name="desc" type="text" validate="required:true" value="${directory.desc}" />
			</bq:labelObject>
			<bq:labelObject for="path" title="目录路径" labelDiv="2">
				<bq:inputText id="path" name="path" type="text" validate="required:true" value="${directory.path}" />
			</bq:labelObject>
			<bq:labelObject for="notes" title="备注" labelDiv="2">
				<bq:inputText id="notes" name="notes" type="text" validate="required:true" value="${directory.notes}" />
			</bq:labelObject>
		</bq:layoutItem>
	</bq:layout>
	<input type="hidden" id="id" name="id" value="${directory.id}"></input>
</form>