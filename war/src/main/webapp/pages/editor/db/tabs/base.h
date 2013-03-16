<fly:composition>
	<fly:layout type="float" column="2">
		<fly:layoutItem cols="2">
			<fly:labelObject for="${formId}:name" title="连接名称" labelDiv="2">
				<fly:inputText id="${formId}:id" name="${formId}:id" type="hidden" 
					value="${dbMeta.objectId.id}" />
				<fly:inputText id="${formId}:name" name="${formId}:name" type="text" 
					validate="required:true"
					value="${dbMeta.name}" />
			</fly:labelObject>
		</fly:layoutItem>
	</fly:layout>
	<fly:layout type="float" column="2" itemWidth="40%,60%">
		<fly:layoutItem>
			<fly:labelObject for="${formId}:connectionType" title="连接类型" type="labelSingle">
				<fly:selectMenu id="${formId}:connectionType" name="${formId}:connectionType" size="10" value="${dbMeta.databaseInterface.pluginId}"
					onchange="Plywet.database.changeConnectionType('${formId}')">
					<fly:options items="${dbTypes}" value="ids[0]" label="name" />
				</fly:selectMenu>
			</fly:labelObject>
			<fly:labelObject for="${formId}:accessType" title="连接方式" type="labelSingle">
				<fly:selectMenu id="${formId}:accessType" name="${formId}:accessType" size="4" value="${dbMeta.accessType}"
					onchange="Plywet.database.changeConnectionType('${formId}')">
					<fly:options items="${accessTypes}" value="0" label="1" />
				</fly:selectMenu>
			</fly:labelObject>
		</fly:layoutItem>
		<fly:layoutItem>
			<fly:labelObject title="设置" type="fieldset">
				<div id="${formId}:connectionFieldset">
					<fly:include src="setting/_setting.h" />
				</div>
			</fly:labelObject>
		</fly:layoutItem>
	</fly:layout>
</fly:composition>