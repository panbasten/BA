<form action="rest/host/setting" method="POST" id="fs_host_form">
	<fly:layout type="float" column="1">
		<fly:layoutItem>
			<fly:labelObject for="desc" title="主机名称" labelDiv="2">
				<fly:inputText id="desc" name="desc" type="text" validate="required:true" value="${host.desc}" />
			</fly:labelObject>
			<fly:labelObject for="code" title="主机编码" labelDiv="2">
				<fly:inputText id="code" name="code" type="text" validate="required:true" value="${host.code}" />
			</fly:labelObject>
			<fly:labelObject for="ip" title="IP" labelDiv="2">
				<fly:inputText id="ip" name="ip" type="text" validate="required:true" value="${host.ip}" />
			</fly:labelObject>
			<fly:labelObject for="port" title="端口" labelDiv="2">
				<fly:inputText id="port" name="port" type="text" validate="required:true" value="${host.port}" />
			</fly:labelObject>
			<fly:labelObject for="username" title="用户名" labelDiv="2">
				<fly:inputText id="username" name="username" type="text" validate="required:true" value="${host.username}" />
			</fly:labelObject>
			<fly:labelObject for="password" title="密码" labelDiv="2">
				<fly:inputText id="password" name="password" type="password" validate="required:true" value="${host.password}" />
			</fly:labelObject>
			<fly:labelObject for="type" title="连接类型" labelDiv="2">
				<select id="type" name="type" size="2">
					<option value="ftp">FTP</option>
					<option value="sftp">SFTP</option>
				</select>
			</fly:labelObject>
			<fly:labelObject for="mode" title="连接模式" labelDiv="2">
				<select id="mode" name="mode" size="2">
					<option value="active">主动模式</option>
					<option value="passvie">被动模式</option>
				</select>
			</fly:labelObject>
			<fly:labelObject for="notes" title="备注" labelDiv="2">
				<fly:inputText id="notes" name="notes" type="text" value="${host.notes}" />
			</fly:labelObject>
		</fly:layoutItem>
	</fly:layout>
	<input type="hidden" id="id" name="id" value="${host.id}"></input>
</form>