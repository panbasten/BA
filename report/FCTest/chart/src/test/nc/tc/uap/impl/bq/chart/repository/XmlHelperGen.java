package nc.tc.uap.impl.bq.chart.repository;

/**
 * xml 文件生成辅助类， 使用stringbuf方式， 生成xml， 支持 附加 StringBuilder 等
 * @author hbyxl
 *
 */
public class XmlHelperGen {
	private StringBuilder xmlBuf = null;
	
	/**
	 * 获得保护xml文件头（<?xml version..）的stringbuilder 毒性， 
	 * @return
	 */
	public static StringBuilder getXml(){
		StringBuilder xmlBuf = new StringBuilder();
		xmlBuf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		return xmlBuf;
	}
	
	/**
	 * 附加到stringbuilder 内部xmlbuf应该为null
	 * @param buf 出入的stringbuilder
	 */
	public void attachBuf(StringBuilder buf){
		assert(xmlBuf == null);
		xmlBuf = buf;
	}
	
	/**
	 * 解除对StringBuilder的附加， 返回StringBuilder 对象
	 * @return
	 */
	public StringBuilder detachBuf(){
		StringBuilder buf = xmlBuf;
		xmlBuf = null;
		return buf;
	}
	
	/**
	 * 追加 xml node 无结束， “<node" 样式
	 * @param elm
	 */
	public void addElement(String elm){
		xmlBuf.append("<");
		xmlBuf.append(elm);
	}
	
	/**
	 * 追加 属性信息   " attr='abc'" 样式
	 * @param attr 属性名称
	 * @param attrVal 属性值
	 */
	public void addAttr(String attr, String attrVal){
		xmlBuf.append(" ");
		xmlBuf.append(attr);
		xmlBuf.append("=");
		xmlBuf.append("'").append(attrVal).append("'");
	}
	
	/**
	 * 完成 node 属性， “</abc>" 样式
	 * @param elm
	 */
	public void endElement(String elm){
		xmlBuf.append("</");
		xmlBuf.append(elm);
		xmlBuf.append(">");
	}
	
	/**
	 * 追加  “/>" 样式的简写的xml node 节点
	 */
	public void endElementShortter(){
		xmlBuf.append("/>");
	}
	
	public void endAttr(){
		xmlBuf.append(">");
	}
	
	public String toString(){
		return xmlBuf.toString();
	}
}
