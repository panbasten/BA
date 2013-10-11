<form action="rest/portalet/uploadone" method="POST" id="portal_upload_form" enctype="multipart/form-data"
		target="portal_upload_space_frame">

	<fly:gridLayout column="2" itemWidth="40%,58%">
	
		<fly:gridLayoutItem>
			<fly:labelObject text="${text}" buddy="fs" style="margin:5px 30px 5px 5px;float:right;" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:inputText id="fs" name="fs" type="file" size="30" />
		</fly:gridLayoutItem>

	</fly:gridLayout>
	
	<fly:inputText id="rootDir" name="rootDir" type="hidden" value="${rootDir}" />
	<fly:inputText id="fileName" name="fileName" type="hidden" value="${fileName}" />
	<fly:inputText id="category" name="category" type="hidden" value="${category}" />

	<iframe id="portal_upload_space_frame" name="portal_upload_space_frame" class="fly-space-frame" onload="Flywet.Portal.uploadResult(this);"></iframe>
</form>
