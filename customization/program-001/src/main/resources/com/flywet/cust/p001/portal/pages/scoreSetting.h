<form method="post" id="portal_menu_form">
	<fly:gridLayout
		column="3" itemWidth="40%,28%,28%" itemMargin="10"
		class="fly_portal_menu_content">
		
		<fly:gridLayoutItem cols="3">
			<span style="float: left;">填报日期：${currentText}</span>
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<span></span>
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<span>气温</span>
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<span>降水</span>
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="s1_0" text="符号预测正确站数" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="s1_0" name="s1_0" type="text" value="${s1[0]}" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="s1_1" name="s1_1" type="text" value="${s1[1]}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="s2_0" text="一级异常预测正确站数" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="s2_0" name="s2_0" type="text" value="${s2[0]}" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="s2_1" name="s2_1" type="text" value="${s2[1]}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="s3_0" text="二级异常预测正确站数" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="s3_0" name="s3_0" type="text" value="${s3[0]}" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="s3_1" name="s3_1" type="text" value="${s3[1]}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="s4_0" text="综合评分（Ps）" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="s4_0" name="s4_0" type="text" value="${s4[0]}" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="s4_1" name="s4_1" type="text" value="${s4[1]}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="s5_0" text="符号一致率（Pc）" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="s5_0" name="s5_0" type="text" value="${s5[0]}" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="s5_1" name="s5_1" type="text" value="${s5[1]}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="s6_0" text="分级评分（Pg）" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="s6_0" name="s6_0" type="text" value="${s6[0]}" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="s6_1" name="s6_1" type="text" value="${s6[1]}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="s7_0" text="距平相关系数（ACC）" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="s7_0" name="s7_0" type="text" value="${s7[0]}" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="s7_1" name="s7_1" type="text" value="${s7[1]}" />
		</fly:gridLayoutItem>
		
	</fly:gridLayout>
	
	<fly:inputText id="year" name="year" type="hidden" value="${year}" />
	<fly:inputText id="month" name="month" type="hidden" value="${month}" />

</form>
