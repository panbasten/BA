<fly:composition>
	<fly:layout type="float" column="2">
		<fly:layoutItem>
			<fly:labelObject for="${formId}:usingConnectionPool" title="使用连接池">
				<fly:inputText id="${formId}:usingConnectionPool" name="${formId}:usingConnectionPool" type="checkbox" 
					value="${dbMeta.usingConnectionPool}"
					interaction="[{method:'enable',val:true,param:['${formId}:initialPoolSize','${formId}:maximumPoolSize','${formId}:poolingParameters']}]" />
			</fly:labelObject>
		</fly:layoutItem>
		
		<fly:layoutItem cols="2" id="${formId}:usingConnectionPoolDiv">
			<fly:labelObject type="fieldset" title="连接池大小">
				<fly:layout type="float" column="2">
					<fly:layoutItem>
						<fly:labelObject for="${formId}:initialPoolSize" title="初始化大小">
							<fly:inputText id="${formId}:initialPoolSize" name="${formId}:initialPoolSize" type="text" 
								value="${dbMeta.initialPoolSize}"
								disabled="${!dbMeta.usingConnectionPool}" />
						</fly:labelObject>
					</fly:layoutItem>
					<fly:layoutItem>
						<fly:labelObject for="${formId}:maximumPoolSize" title="最大空闲空间">
							<fly:inputText id="${formId}:maximumPoolSize" name="${formId}:maximumPoolSize" type="text" 
								value="${dbMeta.maximumPoolSize}"
								disabled="${!dbMeta.usingConnectionPool}" />
						</fly:labelObject>
					</fly:layoutItem>
				</fly:layout>
			</fly:labelObject>
		</fly:layoutItem>
		
		<fly:layoutItem cols="2">
			<fly:labelObject title="命名参数" type="labelSingle">
				<fly:grid id="${formId}:poolingParameters" singleSelect="false" checkOnSelect="false" selectable="false" height="120" data="${poolingParameters}" disabled="${!dbMeta.usingConnectionPool}">
					<fly:columns>
						<fly:row>
							<fly:column field="key" title="参数名" width="150" align="center" />
							<fly:column field="value" title="值" width="150" editor="text" align="center" />
						</fly:row>
					</fly:columns>
				</fly:grid>
			</fly:labelObject>
		</fly:layoutItem>
		
		<fly:layoutItem cols="2">
			<fly:labelObject title="描述" type="labelSingle">
				<div id="${formId}:description" style="width:100%;height:50px;" class="ui-show-description-panel">
				</div>
			</fly:labelObject>
		</fly:layoutItem>
	</fly:layout>
</fly:composition>