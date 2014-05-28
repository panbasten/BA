package nc.tc.uap.impl.bq.chart;

import java.util.List;

import junit.framework.Assert;

import nc.vo.pub.format.exception.FormatException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Attribute;

import com.ufida.iufo.pub.tools.AppDebug;

/**
 * XML比较工具
 * @author zhanglld
 *
 */
public class XMLCompareUtil {

	/**
	 * 比较两个XML的内容
	 * @param xml1
	 * @param xml2
	 * @param compareType 可以是相等，也可以是第一个包含（属性更多，Element应该相同）第二个
	 * @throws FormatException
	 */
	public static void compare(String xml1, String xml2, CompareType compareType)
			throws FormatException {
		Document doc1, doc2;
		try {
			doc1 = DocumentHelper.parseText(xml1);
		} catch (DocumentException e) {
			throw new FormatException("XML1文件\"" + xml1 + "\"格式不正确！", e);
		}

		try {
			doc2 = DocumentHelper.parseText(xml2);
		} catch (DocumentException e) {
			throw new FormatException("XML2文件\"" + xml2 + "\"格式不正确！", e);
		}

		Element root1 = doc1.getRootElement();
		Element root2 = doc2.getRootElement();
		
	    if(compareType.equals(CompareType.INCLUDE_LESS))compare(root1, root2);
	    	
		//compare(root1, root2, compareType);
	}
	

	

	/**
	 * 比较
	 * @param element1
	 * @param element2
	 * @param compareType
	 */
	private static void compare(Element element1, Element element2,
			CompareType compareType) {
		Assert.assertEquals(element1.getName(), element2.getName());
//		Assert.assertEquals(element1.attributeCount(),
//				element1.attributeCount());
		if(element1.elements().size()!=element2.elements().size()){
			AppDebug.debug("element1.getName():  "+element1.getName()+"  element2.getName():  "+element2.getName());
		}
		
		//Assert.assertEquals(true,element1.elements().size()>=element2.elements().size());

		if (CompareType.EQUALS.equals(compareType)) {
			for (Attribute attribute : element1.attributes()) {
				Assert.assertNotNull(element2.attribute(attribute.getName()));
				Assert.assertEquals(attribute.getValue(),
						element2.attributeValue(attribute.getName()));
			}
		}
		
		for (Attribute attribute : element2.attributes()) {
			
			//排除xsi;noNamespaceSchemaLocation属性
			String att_name=attribute.getName();
			if(att_name.equals("xsi")||att_name.equals("noNamespaceSchemaLocation"))break;
			
			
			Object attributeName = element1.attribute(attribute.getName());
			if(attributeName == null){
				AppDebug.debug("Element2中的属性" + attribute.getName() + "在Element1中不存在！");
				AppDebug.debug("Element2 info: " + element2.toString());
				AppDebug.debug("Element1 info: " + element1.toString());
			}
			Assert.assertNotNull(element1.attribute(attribute.getName()));
		}

		for (int i = 0; i < element2.elements().size(); i ++){
			Element subElement1 = element1.elements().get(i); 
			
			//fuzw
			//Element subElement1=element1.element(subElement2.getName());
			
			Element subElement2 = element2.elements().get(i); 
			Assert.assertNotNull(subElement2);
			compare(subElement1, subElement2, compareType);
		}
	}
	
	
	private static void compare(Element element1, Element element2){
		
		Assert.assertEquals(element1.getName(), element2.getName());
		
		if(element1.elements().size()<element2.elements().size()){
			AppDebug.debug("Element2 子元素的个数大于 Element1");
			AppDebug.debug("Element2 info: " + element2.toString());
			AppDebug.debug("Element1 info: " + element1.toString());
			Assert.assertEquals(true,element1.elements().size()>=element2.elements().size());
		}
		
		
		for (int i = 0; i < element2.elements().size(); i ++){
			
			Element subElement2 = element2.elements().get(i); 
			
			List<Element> subElements1 = element1.elements(subElement2.getName());
			
			if(subElements1==null || subElements1.size()==0){
				AppDebug.debug("Element2中的子元素" + subElement2.getName() + "在Element1中不存在！");
				AppDebug.debug("subElement2 info: " + subElement2.toString());
				AppDebug.debug("Element2 info: " + element2.toString());
				AppDebug.debug("Element1 info: " + element1.toString());
				Assert.assertEquals(false,subElements1==null || subElements1.size()==0);
			}
			
			for (Element e:subElements1){
				if(compareElement(e,subElement2)){
					compare(e,subElement2);
					break;
				}
			}
		}
		
	}
	
	private static boolean compareElement(Element element1, Element element2){
		
		//if(element1.elements().size()<=element2.elements().size())return false;
		
        for (Attribute attribute : element2.attributes()) {

			//排除xsi;noNamespaceSchemaLocation属性
			String att_name=attribute.getName();
			if(att_name.equals("xsi")||att_name.equals("noNamespaceSchemaLocation"))break;
			
			Attribute attributeName = element1.attribute(attribute.getName());
			if(attributeName==null)return false;
			if(!attributeName.getValue().equals(attribute.getValue()))return false;
			
		 }
		
		return true;
	}
	

	/**
	 * 比较类型
	 * 
	 * @author zhanglld
	 *
	 */
	public static enum CompareType {
		EQUALS, INCLUDE,INCLUDE_LESS;
	}
}
