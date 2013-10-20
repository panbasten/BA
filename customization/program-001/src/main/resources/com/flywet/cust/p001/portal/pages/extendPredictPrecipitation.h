<fly:composition freeLayout="N">
	<fly:verticalLayout margin="10">
		<fly:labelObject text="延伸期降水过程预测评估：" buddy="extendPredictEva" style="margin-bottom:5px;" />
		
		<fly:dataGrid id="extendPredictEva" singleSelect="true" data="${extendPredictEva}" height="320">
			<fly:columns>
				<fly:row>
					<fly:column title="" width="100" align="center" />
					<fly:column title="环流异常相似年" colspan="3" align="center" />
					<fly:column title="过程相似年" colspan="3" align="center" />
					<fly:column title="低频天气图" colspan="3" align="center" />
				</fly:row>
				<fly:row>
					<fly:column field="yearMonth" title="" width="100" align="center" />
					<fly:column field="s1" title="预测过程数" width="100" align="center" />
					<fly:column field="s2" title="正确数" width="100" align="center" />
					<fly:column field="s3" title="漏报数" width="100" align="center" />
					<fly:column field="s4" title="预测过程数" width="100" align="center" />
					<fly:column field="s5" title="正确数" width="100" align="center" />
					<fly:column field="s6" title="漏报数" width="100" align="center" />
					<fly:column field="s7" title="预测过程数" width="100" align="center" />
					<fly:column field="s8" title="正确数" width="100" align="center" />
					<fly:column field="s9" title="漏报数" width="100" align="center" />
				</fly:row>
			</fly:columns>
		</fly:dataGrid>
	</fly:verticalLayout>
</fly:composition>