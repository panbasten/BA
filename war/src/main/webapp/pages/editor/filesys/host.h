<form action="rest/host/setting" method="POST" id="fs_host_form">
	
	<fly:gridLayout column="4" itemWidth="15%,35%,15%,35%">
		<fly:gridLayoutItem>
			<fly:labelObject buddy="desc" text="主机名称" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="desc" name="desc" type="text" validate="required:true" value="${host.desc}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="code" text="主机编码" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="code" name="code" type="text" validate="required:true" value="${host.code}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="ip" text="IP" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="ip" name="ip" type="text" validate="required:true" value="${host.ip}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="port" text="端口" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="port" name="port" type="text" validate="required:true" value="${host.port}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="username" text="用户名" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="username" name="username" type="text" validate="required:true" value="${host.username}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="password" text="密码" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="password" name="password" type="password" validate="required:true" value="${host.password}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="type" text="连接类型" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<select id="type" name="type" size="2">
				<option value="ftp">FTP</option>
				<option value="sftp">SFTP</option>
			</select>
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="mode" text="连接模式" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<select id="mode" name="mode" size="2">
				<option value="active">主动模式</option>
				<option value="passvie">被动模式</option>
			</select>
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="notes" text="备注" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="notes" name="notes" type="text" value="${host.notes}" />
		</fly:gridLayoutItem>
	</fly:gridLayout>
	
	<fly:inputText id="id" name="id" type="hidden" value="${host.id}" />
</form>