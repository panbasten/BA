<fly:composition>

	<fly:gridLayout column="4">
		<fly:gridLayoutItem>
			<fly:labelObject for="${formId}:usingConnectionPool" title="使用连接池" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem cols="3">
			<fly:inputText id="${formId}:usingConnectionPool" name="${formId}:usingConnectionPool" type="checkbox" 
					value="${dbMeta.usingConnectionPool}"
					interaction="[{method:'enable',val:true,param:['${formId}:initialPoolSize','${formId}:maximumPoolSize','${formId}:poolingParameters']}]" />
		</fly:gridLayoutItem>
	</fly:gridLayout>
	
	<fly:vBoxLayout>
		<fly:fieldSet title="连接池大小">
			<fly:gridLayout column="4" itemWidth="15%,35%,15%,35%">
				<fly:gridLayoutItem>
					<fly:labelObject for="${formId}:initialPoolSize" title="初始化大小" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:initialPoolSize" name="${formId}:initialPoolSize" type="text" 
						value="${dbMeta.initialPoolSize}"
						disabled="${!dbMeta.usingConnectionPool}" />
				</fly:gridLayoutItem>
				
				<fly:gridLayoutItem>
					<fly:labelObject for="${formId}:maximumPoolSize" title="最大空闲空间" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:maximumPoolSize" name="${formId}:maximumPoolSize" type="text" 
						value="${dbMeta.maximumPoolSize}"
						disabled="${!dbMeta.usingConnectionPool}" />
				</fly:gridLayoutItem>
			</fly:gridLayout>
		</fly:fieldSet>
		
		<fly:labelObject title="命名参数" />
		
		<fly:grid id="${formId}:poolingParameters" singleSelect="false" checkOnSelect="false" selectable="false" height="120" data="${poolingParameters}" disabled="${!dbMeta.usingConnectionPool}">
			<fly:columns>
				<fly:row>
					<fly:column field="key" title="参数名" width="150" align="center" />
					<fly:column field="value" title="值" width="150" editor="text" align="center" />
				</fly:row>
			</fly:columns>
		</fly:grid>
		
		<fly:labelObject title="描述" />
		
		<div id="${formId}:description" style="width:100%;height:50px;" class="ui-show-description-panel">
		</div>
	</fly:vBoxLayout>

</fly:composition>