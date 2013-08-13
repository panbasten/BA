<form method="post" id="create_user_form">

	<fly:gridLayout column="2" itemWidth="30%,70%" itemMargin="10">
		<fly:gridLayoutItem>
			<fly:labelObject buddy="title" text="title" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="title" name="title" type="text" validate="required:true" value="" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="url" text="url" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="url" name="url" type="text" validate="required:true" value="" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="width" text="width" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="width" name="width" type="text" validate="required:true" value="" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="height" text="height" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="height" name="height" type="text" validate="required:true" value="" />
		</fly:gridLayoutItem>
	</fly:gridLayout>

</form>
