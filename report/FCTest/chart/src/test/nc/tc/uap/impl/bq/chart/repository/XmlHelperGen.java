package nc.tc.uap.impl.bq.chart.repository;

/**
 * xml �ļ����ɸ����࣬ ʹ��stringbuf��ʽ�� ����xml�� ֧�� ���� StringBuilder ��
 * @author hbyxl
 *
 */
public class XmlHelperGen {
	private StringBuilder xmlBuf = null;
	
	/**
	 * ��ñ���xml�ļ�ͷ��<?xml version..����stringbuilder ���ԣ� 
	 * @return
	 */
	public static StringBuilder getXml(){
		StringBuilder xmlBuf = new StringBuilder();
		xmlBuf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		return xmlBuf;
	}
	
	/**
	 * ���ӵ�stringbuilder �ڲ�xmlbufӦ��Ϊnull
	 * @param buf �����stringbuilder
	 */
	public void attachBuf(StringBuilder buf){
		assert(xmlBuf == null);
		xmlBuf = buf;
	}
	
	/**
	 * �����StringBuilder�ĸ��ӣ� ����StringBuilder ����
	 * @return
	 */
	public StringBuilder detachBuf(){
		StringBuilder buf = xmlBuf;
		xmlBuf = null;
		return buf;
	}
	
	/**
	 * ׷�� xml node �޽����� ��<node" ��ʽ
	 * @param elm
	 */
	public void addElement(String elm){
		xmlBuf.append("<");
		xmlBuf.append(elm);
	}
	
	/**
	 * ׷�� ������Ϣ   " attr='abc'" ��ʽ
	 * @param attr ��������
	 * @param attrVal ����ֵ
	 */
	public void addAttr(String attr, String attrVal){
		xmlBuf.append(" ");
		xmlBuf.append(attr);
		xmlBuf.append("=");
		xmlBuf.append("'").append(attrVal).append("'");
	}
	
	/**
	 * ��� node ���ԣ� ��</abc>" ��ʽ
	 * @param elm
	 */
	public void endElement(String elm){
		xmlBuf.append("</");
		xmlBuf.append(elm);
		xmlBuf.append(">");
	}
	
	/**
	 * ׷��  ��/>" ��ʽ�ļ�д��xml node �ڵ�
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
