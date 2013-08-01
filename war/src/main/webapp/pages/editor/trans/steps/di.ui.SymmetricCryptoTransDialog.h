<form id="${formId}" class="fly-dialog-form">
	<fly:verticalLayout>
		<fly:gridLayout column="2" itemWidth="30%,65%" itemMargin="10" marginBottom="10">
			<fly:gridLayoutItem>
				<fly:labelObject buddy="${formId}:name" text="步骤名称" />
			</fly:gridLayoutItem>
			<fly:gridLayoutItem>
				<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
					validate="required:true" value="" />
			</fly:gridLayoutItem>
		</fly:gridLayout>

		<fly:fieldSet title="加密设置" margin="10">
			<fly:gridLayout column="2" itemWidth="20%,30%,18%,30%" itemMargin="10">
				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:1" text="操作" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:1" name="${formId}:1" type="text"
						value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:2" text="算法" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:2" name="${formId}:2" type="text"
						validate="required:true"
						value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:1" text="方案" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:1" name="${formId}:1" type="text"
						value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:2" text="密钥" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:2" name="${formId}:2" type="text"
						validate="required:true"
						value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:1" text="密钥来自于字段" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:1" name="${formId}:1" type="checkbox"
						value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:2" text="密钥字段" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:2" name="${formId}:2" type="text"
						validate="required:true"
						value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:1" text="以二进制读取密钥" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:1" name="${formId}:1" type="checkbox"
						value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:2" text="算法" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:2" name="${formId}:2" type="text"
						validate="required:true"
						value="" />
				</fly:gridLayoutItem>
			</fly:gridLayout>
		</fly:fieldSet>

		<fly:fieldSet title="明文" margin="10">
			<fly:gridLayout column="2" itemWidth="20%,30%,18%,30%" itemMargin="10">
				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:1" text="明文字段" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:1" name="${formId}:1" type="text"
						value="" />
				</fly:gridLayoutItem>
			</fly:gridLayout>
		</fly:fieldSet>

		<fly:fieldSet title="密文" margin="10">
			<fly:gridLayout column="4" itemWidth="20%,30%,20%,28%" itemMargin="10">
				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:1" text="密文字段" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:1" name="${formId}:1" type="text"
						value="" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:1" text="以二进制保存密文" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:1" name="${formId}:1" type="checkbox"
						value="" />
				</fly:gridLayoutItem>
			</fly:gridLayout>
		</fly:fieldSet>
	</fly:verticalLayout>
</form>
