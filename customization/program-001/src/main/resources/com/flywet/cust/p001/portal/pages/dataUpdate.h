<form method="POST" id="portal_menu_form" enctype="multipart/form-data"
		target="portal_upload_space_frame">

	<fly:gridLayout column="2" itemWidth="40%,58%">
		<fly:gridLayoutItem>
			<fly:labelObject text="11城市月数据：" buddy="fs1" style="margin:5px 30px 5px 5px;float:right;" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:inputText id="fs1" name="fs1" type="file" size="30" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:labelObject text="11城市季数据 ：" buddy="fs2" style="margin:5px 30px 5px 5px;float:right;" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:inputText id="fs2" name="fs2" type="file" size="30" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:labelObject text=" 142县站数据：" buddy="fs3" style="margin:5px 30px 5px 5px;float:right;" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:inputText id="fs3" name="fs3" type="file" size="30" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:labelObject text="海温数据：" buddy="fs4" style="margin:5px 30px 5px 5px;float:right;" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:inputText id="fs4" name="fs4" type="file" size="30" />
		</fly:gridLayoutItem>

	</fly:gridLayout>

	<iframe id="portal_upload_space_frame" name="portal_upload_space_frame" class="fly-space-frame" onload="Flywet.filesys.uploadResult(this);"></iframe>
</form>
