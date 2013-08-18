<fly:composition>
	<fly:verticalLayout margin="10">
		<fly:dataGrid id="dbPlugin" singleSelect="true" height="450" data="${dbPlugin}">
			<fly:columns>
				<fly:row>
					<fly:column field="s1" title="ID" width="100" align="center" />
					<fly:column field="s2" title="名称" width="100" align="center" />
					<fly:column field="s3" title="描述" width="200" align="left" />
					<fly:column field="s4" title="类库" width="100" align="center" />
					<fly:column field="s6" title="主类" width="200" align="left" />
				</fly:row>
			</fly:columns>
		</fly:dataGrid>
	</fly:verticalLayout>
</fly:composition>