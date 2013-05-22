<fly:composition freeLayout="N">
	<fly:foreach items="${connectionSettings}" var="settingFileStr">
		<fly:include src="${settingFileStr}" />
	</fly:foreach>
</fly:composition>