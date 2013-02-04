<bq:composition>
	<bq:layout type="float" column="2">
		<bq:layoutItem>
			<bq:labelObject for="${formId}:partitioned" title="使用集群">
				<bq:inputText id="${formId}:partitioned" name="${formId}:partitioned" type="checkbox" 
					value="${dbMeta.partitioned}"
					interaction="[{method:'enable',val:true,param:['${formId}:partitioningInformations']}]" />
			</bq:labelObject>
		</bq:layoutItem>
		
		<bq:layoutItem cols="2">
			<bq:labelObject title="命名参数" type="labelSingle">
				<bq:grid id="${formId}:partitioningInformations" singleSelect="true" selectable="false" height="250" data="${partitioningInformations}" disabled="${!dbMeta.partitioned}">
					<bq:columns>
						<bq:row>
							<bq:column field="partitionId" title="分区ID" width="100" editor="text" align="center" />
							<bq:column field="hostname" title="主机名称" width="100" editor="text" align="center" />
							<bq:column field="port" title="端口号" width="100" editor="numberbox" align="center" />
							<bq:column field="databaseName" title="数据库名称" width="100" editor="text" align="center" />
							<bq:column field="username" title="用户名" width="100" editor="text" align="center" />
							<bq:column field="password" title="密码" width="100" editor="text" align="center" />
						</bq:row>
					</bq:columns>
				</bq:grid>
			</bq:labelObject>
		</bq:layoutItem>
	</bq:layout>
</bq:composition>