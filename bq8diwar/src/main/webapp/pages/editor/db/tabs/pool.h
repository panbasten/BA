<bq:composition>
	<bq:layout type="float" column="2">
		<bq:layoutItem>
			<bq:labelObject for="${formId}:usingConnectionPool" title="使用连接池">
				<bq:inputText id="${formId}:usingConnectionPool" name="${formId}:usingConnectionPool" type="checkbox" 
					value="${dbMeta.usingConnectionPool}"
					interaction="[{method:'enable',val:true,param:['${formId}:initialPoolSize','${formId}:maximumPoolSize','${formId}:poolingParameters']}]" />
			</bq:labelObject>
		</bq:layoutItem>
		
		<bq:layoutItem cols="2" id="${formId}:usingConnectionPoolDiv">
			<bq:labelObject type="fieldset" title="连接池大小">
				<bq:layout type="float" column="2">
					<bq:layoutItem>
						<bq:labelObject for="${formId}:initialPoolSize" title="初始化大小">
							<bq:inputText id="${formId}:initialPoolSize" name="${formId}:initialPoolSize" type="text" 
								value="${dbMeta.initialPoolSize}"
								disabled="${!dbMeta.usingConnectionPool}" />
						</bq:labelObject>
					</bq:layoutItem>
					<bq:layoutItem>
						<bq:labelObject for="${formId}:maximumPoolSize" title="最大空闲空间">
							<bq:inputText id="${formId}:maximumPoolSize" name="${formId}:maximumPoolSize" type="text" 
								value="${dbMeta.maximumPoolSize}"
								disabled="${!dbMeta.usingConnectionPool}" />
						</bq:labelObject>
					</bq:layoutItem>
				</bq:layout>
			</bq:labelObject>
		</bq:layoutItem>
		
		<bq:layoutItem cols="2">
			<bq:labelObject title="命名参数" type="labelSingle">
				<bq:grid id="${formId}:poolingParameters" singleSelect="false" checkOnSelect="false" selectable="false" height="120" data="${poolingParameters}" disabled="${!dbMeta.usingConnectionPool}">
					<bq:columns>
						<bq:row>
							<bq:column field="key" title="参数名" width="150" align="center" />
							<bq:column field="value" title="值" width="150" editor="text" align="center" />
						</bq:row>
					</bq:columns>
				</bq:grid>
			</bq:labelObject>
		</bq:layoutItem>
		
		<bq:layoutItem cols="2">
			<bq:labelObject title="描述" type="labelSingle">
				<div id="${formId}:description" style="width:100%;height:50px;" class="ui-show-description-panel">
				</div>
			</bq:labelObject>
		</bq:layoutItem>
	</bq:layout>
</bq:composition>