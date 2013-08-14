<fly:composition>
	<fly:verticalLayout>
		
		<div style="width:400px;height:100px;margin:10px;" class="fly-systools-about-title"></div>
		<div style="width:100%;height:30px;"><div style="float:right;margin:0 30px;"><font class="fly-systools-about-bold">${licVersion}</font><font>${validDays}</font></div></div>
		<div style="width:100%;height:30px;"><div style="float:left;margin:0 30px;">${version}</div></div>
		<div style="width:100%;height:30px;"><div style="float:left;margin:0 30px;">${licTerms}</div></div>
		<div style="width:100%;height:30px;"><div style="float:left;margin:0 30px;">${licTo}</div></div>
		<div style="width:100%;height:40px;"><div style="float:left;margin:0 50px;" class="fly-systools-about-bold">${customer}</div></div>
		<fly:horizontalLayout style="margin-left:25px;margin-right:25px;">
			<fly:foreach items="${moduleCodes}" var="moduleCode" indexVar="moduleIdx">
				<fly:test var="${moduleValids[moduleIdx]}">
					<div class="fly-module-icon fly-module-icon-${moduleCode}" title="${moduleHelpTexts[moduleIdx]}"></div>
				</fly:test>
				<fly:test var="${!moduleValids[moduleIdx]}">
					<div class="fly-module-icon fly-module-icon-${moduleCode} fly-module-icon-disabled" title="${moduleHelpTexts[moduleIdx]}"></div>
				</fly:test>
			</fly:foreach>
		</fly:horizontalLayout>
		<div style="width:100%;height:20px;"><div style="float:left;margin:0 30px;">${licCopyright[0]}</div></div>
		<div style="width:100%;height:20px;"><div style="float:left;margin:0 30px;">${licCopyright[1]}</div></div>
		<div style="width:100%;height:20px;"><div style="float:left;margin:0 30px;">${licCopyright[2]}</div></div>
		<div style="width:100%;"><div style="float:left;margin:0 22px;padding:8px;background-color:#3F3F3F;">${licCopyright[3]}</div></div>
	
	</fly:verticalLayout>

</fly:composition>