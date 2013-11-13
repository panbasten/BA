<fly:composition freeLayout="N">
	<fly:verticalLayout margin="10">
		<fly:labelObject text="月预测方法评估：" buddy="monthPredictEva" style="margin-bottom:5px;" />
		
		<fly:dataGrid id="monthPredictEva" singleSelect="true" data="${monthPredictEva}" height="320">
			<fly:columns>
				<fly:row>
					<fly:column title="" width="100" align="center" />
					<fly:column title="气温" colspan="3" align="center" />
					<fly:column title="降水" colspan="3" align="center" />
				</fly:row>
				<fly:row>
					<fly:column field="yearMonth" title="" width="100" align="center" />
					<fly:column field="s1" title="海温预测" width="100" align="center" />
					<fly:column field="s2" title="统计预测" width="100" align="center" />
					<fly:column field="s3" title="MODES预测" width="100" align="center" />
					<fly:column field="s4" title="海温预测" width="100" align="center" />
					<fly:column field="s5" title="统计预测" width="100" align="center" />
					<fly:column field="s6" title="MODES预测" width="100" align="center" />
				</fly:row>
			</fly:columns>
		</fly:dataGrid>
	</fly:verticalLayout>
</fly:composition>