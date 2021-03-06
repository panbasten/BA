<fly:composition freeLayout="N">

	<fly:gridLayout column="4" margin="10">
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:usingConnectionPool" text="使用连接池" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem cols="3">
			<fly:inputText id="${formId}:usingConnectionPool" name="${formId}:usingConnectionPool" type="checkbox" 
					value="${dbMeta.usingConnectionPool}"
					class="ui-layout-div clearfix"
					interaction="[{method:'enable',val:true,param:['${formId}:poolSize','${formId}:initialPoolSize','${formId}:maximumPoolSize','${formId}:poolingParameters']}]" />
		</fly:gridLayoutItem>
	</fly:gridLayout>
	
	<fly:verticalLayout margin="10">
		<fly:fieldSet title="连接池大小" id="${formId}:poolSize" disabled="${!dbMeta.usingConnectionPool}">
			<fly:gridLayout column="4" itemWidth="20%,29%,20%,29%" itemMargin="10">
				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:initialPoolSize" text="初始化大小" disabled="${!dbMeta.usingConnectionPool}" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:initialPoolSize" name="${formId}:initialPoolSize" type="text" 
						value="${dbMeta.initialPoolSize}"
						class="clearfix"
						disabled="${!dbMeta.usingConnectionPool}" />
				</fly:gridLayoutItem>
				
				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:maximumPoolSize" text="最大空闲空间" class="for-grid-layout-item" disabled="${!dbMeta.usingConnectionPool}" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:maximumPoolSize" name="${formId}:maximumPoolSize" type="text" 
						value="${dbMeta.maximumPoolSize}"
						class="clearfix"
						disabled="${!dbMeta.usingConnectionPool}" />
				</fly:gridLayoutItem>
			</fly:gridLayout>
		</fly:fieldSet>
		
		<fly:labelObject class="ui-layout-no-bottom-margin" text="命名参数" buddy="${formId}:poolingParameters" disabled="${!dbMeta.usingConnectionPool}" marginTop="10" />
		
		<fly:dataGrid id="${formId}:poolingParameters" singleSelect="false" checkOnSelect="false" height="120" data="${poolingParameters}" disabled="${!dbMeta.usingConnectionPool}">
			<fly:columns>
				<fly:row>
					<fly:column field="key" title="参数名" width="150" align="center" />
					<fly:column field="value" title="值" width="150" editor="text" align="center" />
				</fly:row>
			</fly:columns>
		</fly:dataGrid>
		
		<fly:labelObject text="描述" marginTop="10" />
		
		<div id="${formId}:description" style="width:100%;height:50px;" class="ui-show-description-panel">
		</div>
	</fly:verticalLayout>

</fly:composition>
