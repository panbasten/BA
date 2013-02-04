<bq:composition>
	<bq:layout type="float" column="2">
		<bq:layoutItem cols="2">
			<bq:labelObject for="${formId}:name" title="连接名称" labelDiv="2">
				<bq:inputText id="${formId}:id" name="${formId}:id" type="hidden" 
					value="${dbMeta.objectId.id}" />
				<bq:inputText id="${formId}:name" name="${formId}:name" type="text" 
					validate="required:true"
					value="${dbMeta.name}" />
			</bq:labelObject>
		</bq:layoutItem>
	</bq:layout>
	<bq:layout type="float" column="2" itemWidth="40%,60%">
		<bq:layoutItem>
			<bq:labelObject for="${formId}:connectionType" title="连接类型" type="labelSingle">
				<bq:selectMenu id="${formId}:connectionType" name="${formId}:connectionType" size="10" value="${dbMeta.databaseInterface.pluginId}"
					onchange="YonYou.database.changeConnectionType('${formId}')">
					<bq:options items="${dbTypes}" value="ids[0]" label="name" />
				</bq:selectMenu>
			</bq:labelObject>
			<bq:labelObject for="${formId}:accessType" title="连接方式" type="labelSingle">
				<bq:selectMenu id="${formId}:accessType" name="${formId}:accessType" size="4" value="${dbMeta.accessType}"
					onchange="YonYou.database.changeConnectionType('${formId}')">
					<bq:options items="${accessTypes}" value="0" label="1" />
				</bq:selectMenu>
			</bq:labelObject>
		</bq:layoutItem>
		<bq:layoutItem>
			<bq:labelObject title="设置" type="fieldset">
				<div id="${formId}:connectionFieldset">
					<bq:include src="setting/_setting.h" />
				</div>
			</bq:labelObject>
		</bq:layoutItem>
	</bq:layout>
</bq:composition>