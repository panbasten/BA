<fly:composition freeLayout="N">

	<fly:gridLayout column="4">
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:usingConnectionPool" text="使用连接池" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem cols="3">
			<fly:inputText id="${formId}:usingConnectionPool" name="${formId}:usingConnectionPool" type="checkbox" 
					value="${dbMeta.usingConnectionPool}"
					class="ui-layout-div ui-helper-clearfix"
					interaction="[{method:'enable',val:true,param:['${formId}:poolSize','${formId}:initialPoolSize','${formId}:maximumPoolSize','${formId}:poolingParameters']}]" />
		</fly:gridLayoutItem>
	</fly:gridLayout>
	
	<fly:verticalLayout>
		<fly:fieldSet title="连接池大小" id="${formId}:poolSize" disabled="${!dbMeta.usingConnectionPool}">
			<fly:gridLayout column="4" itemWidth="15%,35%,15%,35%">
				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:initialPoolSize" text="初始化大小" disabled="${!dbMeta.usingConnectionPool}" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:initialPoolSize" name="${formId}:initialPoolSize" type="text" 
						value="${dbMeta.initialPoolSize}"
						class="ui-helper-clearfix"
						disabled="${!dbMeta.usingConnectionPool}" />
				</fly:gridLayoutItem>
				
				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:maximumPoolSize" text="最大空闲空间" disabled="${!dbMeta.usingConnectionPool}" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:maximumPoolSize" name="${formId}:maximumPoolSize" type="text" 
						value="${dbMeta.maximumPoolSize}"
						class="ui-helper-clearfix"
						disabled="${!dbMeta.usingConnectionPool}" />
				</fly:gridLayoutItem>
			</fly:gridLayout>
		</fly:fieldSet>
		
		<fly:labelObject text="命名参数" buddy="${formId}:poolingParameters" disabled="${!dbMeta.usingConnectionPool}" />
		
		<fly:dataGrid id="${formId}:poolingParameters" singleSelect="false" checkOnSelect="false" height="120" data="${poolingParameters}" disabled="${!dbMeta.usingConnectionPool}">
			<fly:columns>
				<fly:row>
					<fly:column field="key" title="参数名" width="150" align="center" />
					<fly:column field="value" title="值" width="150" editor="text" align="center" />
				</fly:row>
			</fly:columns>
		</fly:dataGrid>
		
		<fly:labelObject text="描述" />
		
		<div id="${formId}:description" style="width:100%;height:50px;" class="ui-show-description-panel">
		</div>
	</fly:verticalLayout>

</fly:composition>
