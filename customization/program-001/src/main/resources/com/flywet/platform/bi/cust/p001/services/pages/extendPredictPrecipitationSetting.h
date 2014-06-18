<form method="post" id="portal_menu_form">
	<fly:gridLayout
		column="3" itemWidth="40%,28%,28%" itemMargin="10"
		class="fly_portal_menu_content">
		
		<fly:gridLayoutItem>
			<span style="float: left;">填报日期：</span>
		</fly:gridLayoutItem>
		<fly:gridLayoutItem cols="2">
			<fly:selectMenu id="epp_title" name="epp_title" style="float:left;" value="${currentText}" onchange="extendPredictPrecipitationSettingUpdata()">
                <fly:options value="0" label="1" items="${months}" />
            </fly:selectMenu>
		</fly:gridLayoutItem>
	</fly:gridLayout>
	
	<fly:gridLayout
		column="4" itemWidth="30%,23%,23%,23%" itemMargin="10"
		id="epp_body" name="epp_body"
		class="fly_portal_menu_content">
		
		<fly:gridLayoutItem>
			<span></span>
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<span>预测过程数</span>
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<span>正确数</span>
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<span>漏报数</span>
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="s1" text="环流异常相似年" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="s1" name="s1" type="text" value="${s1}" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="s2" name="s2" type="text" value="${s2}" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="s3" name="s3" type="text" value="${s3}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="s4" text="过程相似年" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="s4" name="s4" type="text" value="${s4}" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="s5" name="s5" type="text" value="${s5}" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="s6" name="s6" type="text" value="${s6}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="s7" text="低频天气图" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="s7" name="s7" type="text" value="${s7}" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="s8" name="s8" type="text" value="${s8}" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="s9" name="s9" type="text" value="${s9}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem cols="4">
			<fly:labelObject buddy="desc" text="预测和实况" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem cols="4">
			<textarea id="desc" name="desc" style="width:100%;" rows="8">
			${desc}
			</textarea>
		</fly:gridLayoutItem>
		
	</fly:gridLayout>
	
	<fly:inputText id="year" name="year" type="hidden" value="${year}" />
	<fly:inputText id="month" name="month" type="hidden" value="${month}" />

	<fly:includeJs src="page.js"/>
</form>
