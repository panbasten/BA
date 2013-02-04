<form id="${formId}" class="hb-dialog-form" method="post">
	<bq:tabView id="${formId}_tab" >
		<bq:tab id="${formId}_tab_base" title="基本">
			<bq:include src="tabs/base.h" />
		</bq:tab>
		<bq:tab id="${formId}_tab_advance" title="高级">
			<bq:include src="tabs/advance.h" />
		</bq:tab>
		<bq:tab id="${formId}_tab_option" title="选项">
			<bq:include src="tabs/option.h" />
		</bq:tab>
		<bq:tab id="${formId}_tab_pool" title="连接池">
			<bq:include src="tabs/pool.h" />
		</bq:tab>
		<bq:tab id="${formId}tab_cluster" title="集群">
			<bq:include src="tabs/cluster.h" />
		</bq:tab>
	</bq:tabView>
</form>