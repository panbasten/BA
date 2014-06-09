package com.flywet.platform.bi.pivot.model.context;

public interface IResourceContext extends IContext {
	// 得到图片的数据byte[]
	public byte[] getData();

	// 获得文件的扩展名
	public String getExt();
}
