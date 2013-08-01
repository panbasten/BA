<form id="${formId}" class="fly-dialog-form">
	<fly:gridLayout column="4" itemWidth="20%,30%,18%,30%" itemMargin="10" marginBottom="10">
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="步骤名称" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem cols="3">
			<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
				validate="required:true" value="" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="XML/A Url" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem cols="3">
			<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
				validate="required:true" value="" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="用户名" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
				validate="required:true" value="" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="密码" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:name" name="${formId}:name" type="password"
				validate="required:true" value="" />
		</fly:gridLayoutItem>

	</fly:gridLayout>

	<fly:verticalLayout margin="10">
		<fly:labelObject buddy="${formId}:selectedFiles" text="MDX查询" />
		<fly:inputTextarea id="${formId}:name" name="${formId}:name" rows="8"
				validate="required:true" value="" />
	</fly:verticalLayout>

	<fly:gridLayout column="4" itemWidth="20%,30%,18%,30%" itemMargin="10" marginBottom="10">
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="替换脚本中的变量?" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem cols="3">
			<fly:inputText id="${formId}:name" name="${formId}:name" type="checkbox"
				validate="required:true" value="" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="类别" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem cols="3">
			<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
				validate="required:true" value="" />
		</fly:gridLayoutItem>
	</fly:gridLayout>
</form>
