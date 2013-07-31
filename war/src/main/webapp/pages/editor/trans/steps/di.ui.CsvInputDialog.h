<form id="${formId}" class="fly-dialog-form">
	<fly:tabView id="${formId}_tab" >
		<fly:tab id="tab_base" title="基本">

			<fly:gridLayout column="4" itemWidth="20%,29%,20%,29%" itemMargin="10">
				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:name" text="步骤名称" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem cols="3">
					<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
						validate="required:true"
						value="${stepMeta.name}" />
					<fly:inputText id="${formId}:transId" name="${formId}:transId" type="hidden"
						value="${transId}" />
					<fly:inputText id="${formId}:stepMetaName" name="${formId}:stepMetaName" type="hidden"
						value="${stepMetaName}" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:filename" text="文件名" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem cols="3">
					<fly:inputText id="${formId}:filename" name="${formId}:filename" type="text"
						validate="required:true"
						value="${stepMetaInterface.filename}" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:delimiter" text="列分隔符" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:delimiter" name="${formId}:delimiter" type="text"
						value="${stepMetaInterface.delimiter}" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:enclosure" text="封闭符" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:enclosure" name="${formId}:enclosure" type="text"
						value="${stepMetaInterface.enclosure}" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:bufferSize" text="NIO 缓冲大小" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:bufferSize" name="${formId}:bufferSize" type="text"
						value="${stepMetaInterface.bufferSize}" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:lazyConversionActive" text="简易转换" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:lazyConversionActive" name="${formId}:lazyConversionActive" type="checkbox"
						value="${stepMetaInterface.lazyConversionActive}" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:headerPresent" text="包含列头行" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:headerPresent" name="${formId}:headerPresent" type="checkbox"
						value="${stepMetaInterface.headerPresent}" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:addResultFile" text="添加文件名到结果中" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:addResultFile" name="${formId}:addResultFile" type="checkbox"
						value="${stepMetaInterface.addResultFile}" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:runningInParallel" text="并发运行" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:runningInParallel" name="${formId}:runningInParallel" type="checkbox"
						value="${stepMetaInterface.runningInParallel}" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:newlinePossibleInFields" text="字段中有回车换行" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:newlinePossibleInFields" name="${formId}:newlinePossibleInFields" type="checkbox"
						value="${stepMetaInterface.newlinePossibleInFields}" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:rowNumField" text="行号字段（可选）" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:rowNumField" name="${formId}:rowNumField" type="text"
						value="${stepMetaInterface.rowNumField}" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:encoding" text="文件编码" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:encoding" name="${formId}:encoding" type="text"
						value="${stepMetaInterface.encoding}" />
				</fly:gridLayoutItem>

			</fly:gridLayout>



		</fly:tab>
		<fly:tab id="tab_fields" title="字段">
			<fly:dataGrid id="${formId}:parameter" singleSelect="true" data="" height="300">
				<fly:columns>
					<fly:row>
						<fly:column field="key" title="名称" width="100" editor="text" align="center" />
						<fly:column field="value" title="列" width="100" editor="text" align="center" />
						<fly:column field="desc" title="类型" width="70" editor="text" align="center" />
						<fly:column field="desc" title="格式" width="100" editor="text" align="center" />
						<fly:column field="desc" title="长度" width="70" editor="text" align="center" />
						<fly:column field="desc" title="精度" width="70" editor="text" align="center" />
						<fly:column field="desc" title="货币" width="70" editor="text" align="center" />
						<fly:column field="desc" title="十进展" width="70" editor="text" align="center" />
						<fly:column field="desc" title="组" width="70" editor="text" align="center" />
						<fly:column field="desc" title="去除空字符的方式" width="120" editor="text" align="center" />
						<fly:column field="desc" title="重复" width="70" editor="text" align="center" />
					</fly:row>
				</fly:columns>
				<fly:toolbar>
					<fly:pushbutton id="${formId}:selectedFiles:remove" iconCls="ui-icon-closethick" onclick="Flywet.editors.toolbarButton.deleteRow('${formId}:selectedFiles');" title="删除" />
				</fly:toolbar>
			</fly:dataGrid>
		</fly:tab>
	</fly:tabView>

</form>
