<form action="rest/portalet/upload" method="POST" id="portal_upload_form" enctype="multipart/form-data"
		target="portal_upload_space_frame">

	<fly:gridLayout column="2" itemWidth="40%,58%">
	
		<fly:foreach items="${files}" var="file" indexVar="fileIndexVar">
		
			<fly:gridLayoutItem>
				<fly:labelObject text="${file}" buddy="fs${fileIndexVar}" style="margin:5px 30px 5px 5px;float:right;" />
			</fly:gridLayoutItem>
	
			<fly:gridLayoutItem>
				<fly:inputText id="fs${fileIndexVar}" name="fs${fileIndexVar}" type="file" size="30" />
			</fly:gridLayoutItem>
		
		</fly:foreach>

	</fly:gridLayout>
	
	<fly:inputText id="filesNum" name="filesNum" type="hidden" value="${filesNum}" />
	<fly:inputText id="rootDir" name="rootDir" type="hidden" value="${rootDir}" />
	<fly:inputText id="workDir" name="workDir" type="hidden" value="${workDir}" />
	<fly:inputText id="category" name="category" type="hidden" value="${category}" />

	<iframe id="portal_upload_space_frame" name="portal_upload_space_frame" class="fly-space-frame" onload="Flywet.Portal.uploadResult(this);"></iframe>
</form>
