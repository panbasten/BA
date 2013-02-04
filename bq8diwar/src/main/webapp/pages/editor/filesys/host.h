<form action="rest/host/setting" method="POST" id="fs_host_form">
	<bq:layout type="float" column="1">
		<bq:layoutItem>
			<bq:labelObject for="desc" title="主机名称" labelDiv="2">
				<bq:inputText id="desc" name="desc" type="text" validate="required:true" value="${host.desc}" />
			</bq:labelObject>
			<bq:labelObject for="code" title="主机编码" labelDiv="2">
				<bq:inputText id="code" name="code" type="text" validate="required:true" value="${host.code}" />
			</bq:labelObject>
			<bq:labelObject for="ip" title="IP" labelDiv="2">
				<bq:inputText id="ip" name="ip" type="text" validate="required:true" value="${host.ip}" />
			</bq:labelObject>
			<bq:labelObject for="port" title="端口" labelDiv="2">
				<bq:inputText id="port" name="port" type="text" validate="required:true" value="${host.port}" />
			</bq:labelObject>
			<bq:labelObject for="username" title="用户名" labelDiv="2">
				<bq:inputText id="username" name="username" type="text" validate="required:true" value="${host.username}" />
			</bq:labelObject>
			<bq:labelObject for="password" title="密码" labelDiv="2">
				<bq:inputText id="password" name="password" type="password" validate="required:true" value="${host.password}" />
			</bq:labelObject>
			<bq:labelObject for="type" title="连接类型" labelDiv="2">
				<select id="type" name="type" size="2">
					<option value="ftp">FTP</option>
					<option value="sftp">SFTP</option>
				</select>
			</bq:labelObject>
			<bq:labelObject for="mode" title="连接模式" labelDiv="2">
				<select id="mode" name="mode" size="2">
					<option value="active">主动模式</option>
					<option value="passvie">被动模式</option>
				</select>
			</bq:labelObject>
			<bq:labelObject for="notes" title="备注" labelDiv="2">
				<bq:inputText id="notes" name="notes" type="text" value="${host.notes}" />
			</bq:labelObject>
		</bq:layoutItem>
	</bq:layout>
	<input type="hidden" id="id" name="id" value="${host.id}"></input>
</form>