<bq:composition>
	<bq:foreach items="${connectionSettings}" var="settingFileStr">
		<bq:include src="${settingFileStr}" />
	</bq:foreach>
</bq:composition>