<form method="post" id="portal_menu_form">
	<fly:gridLayout
		column="9" itemWidth="15%,10%,10%,10%,10%,10%,10%,10%,10%" itemMargin="10"
		class="fly_portal_menu_content">
		
		<fly:gridLayoutItem cols="9">
			<span style="float: left;">填报日期：${currentText}</span>
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<span></span>
		</fly:gridLayoutItem>
		<fly:gridLayoutItem cols="4">
			<span>气温</span>
		</fly:gridLayoutItem>
		<fly:gridLayoutItem cols="4">
			<span>降水</span>
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<span>城市名称</span>
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<span>海温预测</span>
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<span>统计预测</span>
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<span>MODES预测</span>
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<span>实况</span>
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<span>海温预测</span>
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<span>统计预测</span>
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<span>MODES预测</span>
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<span>实况</span>
		</fly:gridLayoutItem>
		
		<fly:foreach items="${cityCodes}" var="cityCode" indexVar="cityIdx">
		
			<fly:gridLayoutItem>
				<span>${cityNames[cityIdx]}</span>
				<fly:inputText id="city" name="city" type="hidden" value="${cityCode}" />
			</fly:gridLayoutItem>
			<fly:gridLayoutItem>
				<fly:selectMenu id="s1" name="s1" value="${detailMap[cityCode][1]}">
					<fly:options items="${tempOpts}" value="0" label="1" />
				</fly:selectMenu>
			</fly:gridLayoutItem>
			<fly:gridLayoutItem>
				<fly:selectMenu id="s2" name="s2" value="${detailMap[cityCode][2]}">
					<fly:options items="${tempOpts}" value="0" label="1" />
				</fly:selectMenu>
			</fly:gridLayoutItem>
			<fly:gridLayoutItem>
				<fly:selectMenu id="s3" name="s3" value="${detailMap[cityCode][3]}">
					<fly:options items="${tempOpts}" value="0" label="1" />
				</fly:selectMenu>
			</fly:gridLayoutItem>
			<fly:gridLayoutItem>
				<fly:selectMenu id="s4" name="s4" value="${detailMap[cityCode][4]}">
					<fly:options items="${tempOpts}" value="0" label="1" />
				</fly:selectMenu>
			</fly:gridLayoutItem>
			<fly:gridLayoutItem>
				<fly:selectMenu id="s5" name="s5" value="${detailMap[cityCode][5]}">
					<fly:options items="${preOpts}" value="0" label="1" />
				</fly:selectMenu>
			</fly:gridLayoutItem>
			<fly:gridLayoutItem>
				<fly:selectMenu id="s6" name="s6" value="${detailMap[cityCode][6]}">
					<fly:options items="${preOpts}" value="0" label="1" />
				</fly:selectMenu>
			</fly:gridLayoutItem>
			<fly:gridLayoutItem>
				<fly:selectMenu id="s7" name="s7" value="${detailMap[cityCode][7]}">
					<fly:options items="${preOpts}" value="0" label="1" />
				</fly:selectMenu>
			</fly:gridLayoutItem>
			<fly:gridLayoutItem>
				<fly:selectMenu id="s8" name="s8" value="${detailMap[cityCode][8]}">
					<fly:options items="${preOpts}" value="0" label="1" />
				</fly:selectMenu>
			</fly:gridLayoutItem>
		
		</fly:foreach>
		
	</fly:gridLayout>
	
	<fly:inputText id="year" name="year" type="hidden" value="${year}" />
	<fly:inputText id="month" name="month" type="hidden" value="${month}" />

</form>
