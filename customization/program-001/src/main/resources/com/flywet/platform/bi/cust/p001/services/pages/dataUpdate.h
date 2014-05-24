<form id="portal_upload_form" enctype="multipart/form-data">

	<fly:gridLayout column="2" itemWidth="60%,38%" itemMargin="5">
		<fly:gridLayoutItem>
			<fly:labelObject text="11城市月数据(city_mon_197101-thismonth.txt)：" buddy="fs1" style="width:auto;float:right;" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:inputText id="fs1" name="fs1" type="file" size="30" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:labelObject text="11城市季数据(data_11cityAndHebei_season_1982-thisYear.txt) ：" buddy="fs2" style="width:auto;float:right;" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:inputText id="fs2" name="fs2" type="file" size="30" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:labelObject text=" 142县站数据(上月)(142staMondep_lastmonth.txt)：" buddy="fs3" style="width:auto;float:right;" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:inputText id="fs3" name="fs3" type="file" size="30" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject text=" 142县站数据(历史)(142staMon_history.txt)：" buddy="fs4" style="width:auto;float:right;" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:inputText id="fs4" name="fs4" type="file" size="30" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:labelObject text="格点海温数据(sst.mnmean.nc)：" buddy="fs5" style="width:auto;float:right;" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:inputText id="fs5" name="fs5" type="file" size="30" />
		</fly:gridLayoutItem>

	</fly:gridLayout>
	
</form>
