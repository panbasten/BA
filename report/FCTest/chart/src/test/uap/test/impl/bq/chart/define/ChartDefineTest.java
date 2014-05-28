package uap.test.impl.bq.chart.define;

import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

// Xml 解析调优
public class ChartDefineTest {
	
	public String ReadXmlByDom4JIterator (Document xmlDocument) throws DocumentException{
		String xmlContent = "";
		
		Element rootElement = xmlDocument.getRootElement();
		for (Iterator<Element> itElement = rootElement.elementIterator(); itElement.hasNext();){
			Element elem = (Element)itElement.next();
			xmlContent += elem.asXML();
		}
		
		return xmlContent;
	}
	
	public String ReadXmlByDom4JXPath (Document xmlDocument) throws DocumentException{
		String xmlContent = "";
		Element rootElement = xmlDocument.getRootElement();
		List<?> elemList = rootElement.selectNodes("./node()");
		for (Iterator<?> it = elemList.iterator(); it.hasNext();){
			Object objElem = it.next();
			if (objElem instanceof Element){
				xmlContent += ((Element)objElem).asXML();
			}
		}
		return xmlContent;
	}
	
	public String ReadXmlByDom4JElements (Document xmlDocument) throws DocumentException{
		String xmlContent = "";
		Element rootElement = xmlDocument.getRootElement();
		List<Element> elemList = rootElement.elements();
		//for (Iterator<?> it = elemList.iterator(); it.hasNext();){
		for (Element elem : elemList){
				xmlContent += elem.asXML();
		}
		return xmlContent;
	}
	
	// ------------------------main-------------------------------------------------
//	public static void main (String[] args) throws DocumentException{
//		ChartDefineTest obj = new ChartDefineTest();
//		SAXReader reader = new SAXReader();
//		Document xmlDocument = reader.read(new File ("d:/midResult.xml"));
//		long t0 = System.currentTimeMillis();
//		String xml0 = obj.ReadXmlByDom4JXPath(xmlDocument);
//		// System.out.println(xml0);
//		System.out.print("ReadXmlByDom4JXPath 耗时：");
//		System.out.println (System.currentTimeMillis() - t0);
//		
//		System.out.println("===================================");
//		long t1 = System.currentTimeMillis();
//		String xml1 = obj.ReadXmlByDom4JIterator(xmlDocument);
//		//System.out.println(xml1);
//		System.out.print("ReadXmlByDom4JIterator 耗时：");
//		System.out.println (System.currentTimeMillis() - t1);
//		
//		System.out.println("===================================");
//		long t2 = System.currentTimeMillis();
//		String xml2 = obj.ReadXmlByDom4JElements(xmlDocument);
//		// System.out.println(xml1);
//		System.out.print("ReadXmlByDom4JElements 耗时：");
//		System.out.println (System.currentTimeMillis() - t2);
//				
//		
//	}

}
