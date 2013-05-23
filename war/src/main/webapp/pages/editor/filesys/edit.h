<form action="rest/fs/items/editsubmit" method="post" id="fs_edit_${id}_form">

	<fly:gridLayout column="2" itemWidth="30%,70%">
		<fly:gridLayoutItem>
			<fly:labelObject buddy="description" text="输入新名称" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:inputText id="description" type="text" validate="required:true" value="" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:labelObject buddy="path" text="文件路径" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:inputText id="path" type="text" validate="required:true" value="" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:labelObject buddy="notes" text="描述" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<textarea id="notes" rows="3" style="margin:5px;">
			</textarea>
		</fly:gridLayoutItem>

	</fly:gridLayout>

	<fly:inputText id="rootId" name="rootId" type="hidden" value="" />
	<fly:inputText id="path" name="path" type="hidden" value="" />
	<fly:inputText id="category" name="category" type="hidden" value="" />

</form>
