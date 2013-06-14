<fly:composition freeLayout="N">

	<fly:gridLayout column="4">
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:partitioned" text="使用集群" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem cols="3">
			<fly:inputText id="${formId}:partitioned" name="${formId}:partitioned" type="checkbox" 
				value="${dbMeta.partitioned}"
				interaction="[{method:'enable',val:true,param:['${formId}:partitioningInformations']}]"
				class="ui-layout-div ui-helper-clearfix" />
		</fly:gridLayoutItem>
	</fly:gridLayout>
	
	<fly:verticalLayout>
		<fly:labelObject text="命名参数" />
		<fly:dataGrid id="${formId}:partitioningInformations" singleSelect="true" height="250" data="${partitioningInformations}" disabled="${!dbMeta.partitioned}">
			<fly:columns>
				<fly:row>
					<fly:column field="partitionId" title="分区ID" width="100" editor="text" align="center" />
					<fly:column field="hostname" title="主机名称" width="100" editor="text" align="center" />
					<fly:column field="port" title="端口号" width="100" editor="numberbox" align="center" />
					<fly:column field="databaseName" title="数据库名称" width="100" editor="text" align="center" />
					<fly:column field="username" title="用户名" width="100" editor="text" align="center" />
					<fly:column field="password" title="密码" width="100" editor="text" align="center" />
				</fly:row>
			</fly:columns>
			<fly:toolbar>
				<fly:pushbutton id="${formId}:partitioningInformations:append" icon="ui-icon-plusthick" onclick="Plywet.editors.toolbarButton.addRow('${formId}:partitioningInformations');" title="添加" />
				<fly:pushbutton id="${formId}:partitioningInformations:remove" icon="ui-icon-closethick" onclick="Plywet.editors.toolbarButton.deleteRow('${formId}:partitioningInformations');" title="删除" />
			</fly:toolbar>
		</fly:dataGrid>
	</fly:verticalLayout>

</fly:composition>
