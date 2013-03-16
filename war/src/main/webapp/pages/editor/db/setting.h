<form id="${formId}" class="fly-dialog-form" method="post">
	<fly:tabView id="${formId}_tab" >
		<fly:tab id="${formId}_tab_base" title="基本">
			<fly:include src="tabs/base.h" />
		</fly:tab>
		<fly:tab id="${formId}_tab_advance" title="高级">
			<fly:include src="tabs/advance.h" />
		</fly:tab>
		<fly:tab id="${formId}_tab_option" title="选项">
			<fly:include src="tabs/option.h" />
		</fly:tab>
		<fly:tab id="${formId}_tab_pool" title="连接池">
			<fly:include src="tabs/pool.h" />
		</fly:tab>
		<fly:tab id="${formId}tab_cluster" title="集群">
			<fly:include src="tabs/cluster.h" />
		</fly:tab>
	</fly:tabView>
</form>