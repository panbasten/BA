<form action="rest/fs/items/createsubmit" method="post" id="fs_create_form">

	<fly:gridLayout column="2" itemWidth="30%,70%">
		<fly:gridLayoutItem>
			<fly:labelObject buddy="desc" text="输入新名称" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:inputText id="desc" name="desc" type="text" validate="required:true" value="" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:labelObject buddy="path" text="文件路径" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:inputText id="path" name="path" type="text" validate="required:true" value="" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:labelObject buddy="notes" text="描述" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<textarea id="notes" name="notes" rows="4">
			</textarea>
		</fly:gridLayoutItem>

	</fly:gridLayout>

	<fly:inputText id="category" name="category" type="hidden" value="${category}" />

</form>
