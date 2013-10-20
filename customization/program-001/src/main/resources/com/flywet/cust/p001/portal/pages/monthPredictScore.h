<fly:composition freeLayout="N">
	<fly:verticalLayout margin="10">
		<fly:labelObject text="月预测评分：(气温/降水)" buddy="monthPredictScore" style="margin-bottom:5px;" />
		
		<fly:dataGrid id="monthPredictScore" singleSelect="true" data="${monthPredictScore}" height="320">
			<fly:columns>
				<fly:row>
					<fly:column field="yearMonth" title="" width="100" align="center" />
					<fly:column field="s1" title="符号预测正确站数" width="130" align="center" />
					<fly:column field="s2" title="一级异常预测正确站数" width="130" align="center" />
					<fly:column field="s3" title="二级异常预测正确站数" width="130" align="center" />
					<fly:column field="s4" title="综合评分(Ps)" width="100" align="center" />
					<fly:column field="s5" title="符号一致率(Pc)" width="100" align="center" />
					<fly:column field="s6" title="分级评分(Pg)" width="100" align="center" />
					<fly:column field="s7" title="距平相关系数(ACC)" width="130" align="center" />
				</fly:row>
			</fly:columns>
		</fly:dataGrid>
	</fly:verticalLayout>
</fly:composition>