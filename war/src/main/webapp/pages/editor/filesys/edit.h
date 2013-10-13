<form method="post" id="fs_edit_form">

	<fly:gridLayout column="2" itemWidth="30%,70%">
		<fly:gridLayoutItem>
			<fly:labelObject buddy="desc" text="输入新名称" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:inputText id="desc" name="desc" type="text" validate="required:true" value="${dir.desc}" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:labelObject buddy="path" text="文件路径" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:inputText id="path" name="path" type="text" validate="required:true" value="${dir.path}" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:labelObject buddy="notes" text="描述" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<textarea id="notes" name="notes" rows="4">
				${dir.notes}
			</textarea>
		</fly:gridLayoutItem>

	</fly:gridLayout>

	<fly:inputText id="rootId" name="rootId" type="hidden" value="${id}" />
	<fly:inputText id="category" name="category" type="hidden" value="${category}" />

</form>
