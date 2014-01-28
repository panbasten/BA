<form id="portal_upload_form" enctype="multipart/form-data">

	<fly:gridLayout column="2" itemWidth="40%,58%">
	
		<fly:gridLayoutItem>
			<fly:labelObject text="${text}" buddy="fs" style="margin:5px 30px 5px 5px;float:right;" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:inputText id="fs" name="fs" type="file" size="30" />
		</fly:gridLayoutItem>

	</fly:gridLayout>
	
	<fly:inputText id="pDialogId" name="pDialogId" type="hidden" value="${pDialogId}" />
	<fly:inputText id="rootDir" name="rootDir" type="hidden" value="${rootDir}" />
	<fly:inputText id="fileName" name="fileName" type="hidden" value="${fileName}" />
	<fly:inputText id="workDir" name="workDir" type="hidden" value="${workDir}" />
	<fly:inputText id="category" name="category" type="hidden" value="${category}" />
	<fly:inputText id="actionId" name="actionId" type="hidden" value="${actionId}" />
	<fly:inputText id="actionParams" name="actionParams" type="hidden" value="${actionParams}" />

</form>
