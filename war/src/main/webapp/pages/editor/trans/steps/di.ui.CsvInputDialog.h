<form id="${formId}" class="fly-dialog-form">
	<fly:gridLayout column="4" itemWidth="15%,34%,15%,34%" itemMargin="10">
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="步骤名称" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem cols="3">
			<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
				validate="required:true"
				value="${stepMeta.name}" />
			<fly:inputText id="${formId}:transId" name="${formId}:transId" type="hidden"
				value="${transId}" />
			<fly:inputText id="${formId}:stepMetaName" name="${formId}:stepMetaName" type="hidden"
				value="${stepMetaName}" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:filename" text="文件名" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem cols="3">
			<fly:inputText id="${formId}:filename" name="${formId}:filename" type="text"
				validate="required:true"
				value="${stepMetaInterface.filename}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:delimiter" text="分隔符" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:delimiter" name="${formId}:delimiter" type="text"
				value="${stepMetaInterface.delimiter}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:enclosure" text="附件" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:enclosure" name="${formId}:enclosure" type="text"
				value="${stepMetaInterface.enclosure}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:bufferSize" text="NIO 缓冲量" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:bufferSize" name="${formId}:bufferSize" type="text"
				value="${stepMetaInterface.bufferSize}" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:lazyConversionActive" text="懒转换" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:lazyConversionActive" name="${formId}:lazyConversionActive" type="checkbox"
				value="${stepMetaInterface.lazyConversionActive}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:lazyConversionActive" text="懒转换" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:lazyConversionActive" name="${formId}:lazyConversionActive" type="checkbox"
				value="${stepMetaInterface.lazyConversionActive}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:headerPresent" text="存在标题行" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:headerPresent" name="${formId}:headerPresent" type="checkbox"
				value="${stepMetaInterface.headerPresent}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:addResultFile" text="增加文件名到结果" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:addResultFile" name="${formId}:addResultFile" type="text"
				value="${stepMetaInterface.addResultFile}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:addResultFile" text="增加文件名到结果" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:addResultFile" name="${formId}:addResultFile" type="text"
				value="${stepMetaInterface.addResultFile}" />
		</fly:gridLayoutItem>

	</fly:gridLayout>
</form>