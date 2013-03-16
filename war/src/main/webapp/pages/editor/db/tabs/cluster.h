<fly:composition>
	<fly:layout type="float" column="2">
		<fly:layoutItem>
			<fly:labelObject for="${formId}:partitioned" title="使用集群">
				<fly:inputText id="${formId}:partitioned" name="${formId}:partitioned" type="checkbox" 
					value="${dbMeta.partitioned}"
					interaction="[{method:'enable',val:true,param:['${formId}:partitioningInformations']}]" />
			</fly:labelObject>
		</fly:layoutItem>
		
		<fly:layoutItem cols="2">
			<fly:labelObject title="命名参数" type="labelSingle">
				<fly:grid id="${formId}:partitioningInformations" singleSelect="true" selectable="false" height="250" data="${partitioningInformations}" disabled="${!dbMeta.partitioned}">
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
				</fly:grid>
			</fly:labelObject>
		</fly:layoutItem>
	</fly:layout>
</fly:composition>