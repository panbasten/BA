<fly:composition freeLayout="N">
	<fly:gridLayout column="4">
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="连接名称" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem cols="3">
			<fly:inputText id="${formId}:id" name="${formId}:id" type="hidden" 
				value="${dbMeta.objectId.id}" />
			<fly:inputText id="${formId}:name" name="${formId}:name" type="text" 
				validate="required:true"
				value="${dbMeta.name}" />
		</fly:gridLayoutItem>
	</fly:gridLayout>
	<fly:gridLayout column="2" itemWidth="40%,60%">
		<fly:gridLayoutItem>
			<fly:verticalLayout margin="0">
				<fly:labelObject buddy="${formId}:connectionType" text="连接类型"></fly:labelObject>
				<fly:selectMenu id="${formId}:connectionType" name="${formId}:connectionType" size="10" value="${dbMeta.databaseInterface.pluginId}"
					onchange="Plywet.database.changeConnectionType('${formId}')">
					<fly:options items="${dbTypes}" value="ids[0]" label="name" />
				</fly:selectMenu>
				<fly:labelObject buddy="${formId}:accessType" text="连接方式"></fly:labelObject>
				<fly:selectMenu id="${formId}:accessType" name="${formId}:accessType" size="4" value="${dbMeta.accessType}"
					onchange="Plywet.database.changeConnectionType('${formId}')"
					margin-bottom="10">
					<fly:options items="${accessTypes}" value="0" label="1" />
				</fly:selectMenu>
			</fly:verticalLayout>
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:fieldSet id="${formId}:connectionFieldset" title="设置">
				<fly:horizontalLayout id="${formId}:connection">
					<fly:include src="setting/_setting.h" />
				</fly:horizontalLayout>
			</fly:fieldSet>
		</fly:gridLayoutItem>
	</fly:gridLayout>
	
</fly:composition>