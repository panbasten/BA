<fly:composition freeLayout="N">
	<fly:gridLayout column="4" itemWidth="15%,35%,15%,35%" itemMargin="10">
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="转换名称" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem cols="3">
			<fly:inputText id="${formId}:name" name="${formId}:name" type="text" 
				validate="required:true"
				style="margin-right:20px;"
				value="${transMeta.name}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:directoryId" text="目录" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem cols="3">
			<fly:inputText id="${formId}:directoryId" name="${formId}:directoryId" type="text"
				value="" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:description" text="描述" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem cols="3">
			<fly:inputText id="${formId}:description" name="${formId}:description" type="text"
				value="${transMeta.description}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:extendedDescription" text="扩展描述" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem cols="3">
			<fly:inputTextarea id="${formId}:extendedDescription" name="${formId}:extendedDescription"
				value="${transMeta.extendedDescription}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:transstatus" text="状态" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:selectMenu id="${formId}:transstatus" name="${formId}:transstatus" type="text"
				value="${transMeta.transstatus}">
				<fly:options items="${di:transStatusOptions()}" value="0" label="1" />
			</fly:selectMenu>
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:transversion" class="for-grid-layout-item" text="版本" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:transversion" name="${formId}:transversion" type="text"
				value="${transMeta.transversion}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="创建者" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<div class="ui-show-text">${transMeta.createdUser}</div>
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" class="for-grid-layout-item" text="创建日期" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<div class="ui-show-text">${transMeta.name}</div>
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="最后修改者" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<div class="ui-show-text">${transMeta.modifiedUser}</div>
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" class="for-grid-layout-item" text="最后修改日期" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<div class="ui-show-text">${transMeta.name}</div>
		</fly:gridLayoutItem>
		
	</fly:gridLayout>
	
</fly:composition>
