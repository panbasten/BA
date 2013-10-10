<form method="POST" id="portal_menu_form" enctype="multipart/form-data"
		target="portal_upload_space_frame">

	<fly:gridLayout column="2" itemWidth="30%,70%">
	
		<fly:foreach items="${files}" var="file" indexVar="fileIndexVar">
		
			<fly:gridLayoutItem>
				<fly:labelObject text="上传文件${fileIndexVar}：" buddy="fs${fileIndexVar}" style="margin:5px 30px 5px 5px;float:right;" />
			</fly:gridLayoutItem>
	
			<fly:gridLayoutItem>
				<fly:inputText id="fs${fileIndexVar}" name="fs${fileIndexVar}" type="file" size="30" />
			</fly:gridLayoutItem>
		
		</fly:foreach>

	</fly:gridLayout>
	
	<fly:inputText id="filePath" name="filePath" type="hidden" value="${filePath}" />
	<fly:inputText id="fileType" name="fileType" type="hidden" value="${fileType}" />

	<iframe id="portal_upload_space_frame" name="portal_upload_space_frame" class="fly-space-frame" onload=""></iframe>
</form>
