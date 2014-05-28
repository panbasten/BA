package nc.tc.uap.impl.bq.chart.layout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import nc.tc.uap.impl.bq.chart.XMLCompareUtil;
import nc.tc.uap.impl.bq.chart.XMLCompareUtil.CompareType;
import nc.vo.jcom.lang.StringUtil;
import nc.vo.pub.format.exception.FormatException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import uap.impl.bq.chart.layout.LayoutFactory;
import uap.impl.bq.chart.util.BackEndServerInfoUtil;
import uap.util.bq.chart.ServerInfoUtil;
import uap.vo.bq.chart.layout.BR;
import uap.vo.bq.chart.layout.DropListGroup;
import uap.vo.bq.chart.layout.Element;
import uap.vo.bq.chart.layout.ElementGroup;
import uap.vo.bq.chart.layout.Layout;
import uap.vo.bq.chart.layout.LayoutElement;
import uap.vo.bq.chart.layout.Line;
import uap.vo.bq.chart.layout.TabPage;

import com.ufida.iufo.pub.tools.AppDebug;
import com.yonyou.uat.dbmanagement.DBManage;
import com.yonyou.uat.framework.BaseTestCase;

public class LayoutFactoryTest extends BaseTestCase {
	LayoutFactory layoutFactory = null;
	Object retObj;
	

	@BeforeClass
	public void BeforeClass() {
		layoutFactory = LayoutFactory.getInstance();
	}

	@AfterClass
	public void AfterClass() {
	}

	@BeforeMethod
	public void BeforeMethod() {
		

	}

	@AfterMethod
	public void afterMethod() {
		// dbManage.tableRollBack();
	}

	@Test(description = "", dependsOnMethods = {}, groups = "", timeOut = 100000)
	public void getLayout() throws FormatException, IOException, DocumentException {
		//ServerInfoUtil.setLocalModel();
		File file = new File(BackEndServerInfoUtil.getServerRootPath() + "/chartDefines");
		for(String fileName : file.list()){
			
			fileName = "MSColumn2D"; 
			
			// Construct method parameters
			String chartDefineCode = fileName;
	
			// Invoke tested method
			Layout layout1 = null;
			try {
				layout1 = layoutFactory.getLayout(chartDefineCode);
			
	
			String xml1 = toXML(layout1);
			Layout layout2 = layoutFactory.getLayout(xml1, chartDefineCode);
			String xml2 = toXML(layout2);
			Assert.assertEquals(xml1, xml2);
			//System.out.println(xml2);
			
			// String savepath1=BackEndServerInfoUtil.getServerRootPath() + "/test-chartLayout.xml";
			// toXMLdoc(xml2,savepath1);
			
			XMLCompareUtil.compare(xml2
					, getFileContent(file.getAbsolutePath() + File.separator + fileName + File.separator + "chartLayout.xml")
					, CompareType.INCLUDE);
			} catch (FormatException e) {
				AppDebug.debug(e);
			}
		}
	}
	
	
	public boolean toXMLdoc(String xml, String saveToFile) throws DocumentException{
		  try{
				 Document document = DocumentHelper.parseText(xml);
				 if (xml != null && document != null){
					 OutputFormat format = OutputFormat.createPrettyPrint();
					 format.setEncoding("GB2312");
					 XMLWriter writer = new XMLWriter (new FileWriter (new File (saveToFile)), format);
					 writer.write(document);
					 writer.close();
					 return true;
				 }
			  }
			  catch (IOException e){
				  throw new DocumentException ("error");
			  }
			  return false;
	  }
	
	
	private static String getFileContent(String xmlPath) throws IOException{
		StringBuilder content = new StringBuilder();
		InputStreamReader reader = new InputStreamReader(new FileInputStream(xmlPath), "UTF-8");
		
		char[] buffer = new char[1024];
		for(;;){
			int count = reader.read(buffer);
			if(count == -1){
				break;
			}else {
				content.append(new String(buffer, 0, count));
			}
		}
		
		return content.toString();
	}
	
	public static String toXML(Layout layout){
		StringBuilder xml = new StringBuilder();
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<layout>\n");
		TabPage[] pages = layout.getTabPages();
		for(TabPage page : pages){
			appendXML(xml, page);
		}
		
		xml.append("</layout>");
		
		return xml.toString();
	}
	
	public static void appendXML(StringBuilder xml, TabPage tabPage){
		xml.append("<tabPage refPropertyGroup=\"").append(tabPage.getRefPropertyGroup()).append("\" code=\"");
		if (!StringUtil.isEmpty(tabPage.getName()))
			xml.append(tabPage.getCode()).append("\" name=\"{").append(tabPage.getName()).append("}\" tabAlign=\"");
		else
			xml.append(tabPage.getCode()).append("\" name=\"").append(tabPage.getName()).append("\" tabAlign=\"");
		xml.append(tabPage.getTabAlign()).append("\">\n");
		for(LayoutElement element : tabPage.getChildren()){
			if(element instanceof BR){
				appendXML(xml, (BR)element);
			}else if(element instanceof DropListGroup){
				appendXML(xml, (DropListGroup)element);
			}else if(element instanceof ElementGroup){
				appendXML(xml, (ElementGroup)element);
			}else if(element instanceof Line){
				appendXML(xml, (Line)element);
			}else if(element instanceof DropListGroup){
				appendXML(xml, (DropListGroup)element);
			}else if(element instanceof TabPage){
				appendXML(xml, (TabPage)element);
			}else if(element instanceof Element){
				appendXML(xml, (Element)element);
			}
		}
		
		xml.append("</tabPage>\n");
	}
	
	public static void appendXML(StringBuilder xml, BR br){
		xml.append("<br></br>\n");
	}
	
	public static void appendXML(StringBuilder xml, Line line){
		xml.append("<line type=\"").append(line.getType()).append("\"></line>\n");
	}
	
	public static void appendXML(StringBuilder xml, ElementGroup elementGroup){
		xml.append("<elementGroup refPropertyGroup=\"").append(elementGroup.getRefPropertyGroup()).append("\"");
		if (!StringUtil.isEmpty(elementGroup.getName()))
			xml.append(" name=\"{").append(elementGroup.getName()).append("}\" align = \"").append(elementGroup.getAlign());
		else
			xml.append(" name=\"").append(elementGroup.getName()).append("\" align = \"").append(elementGroup.getAlign());
		xml.append("\" border= \"").append(elementGroup.getBorder()).append("\" borderColor=\"");
		xml.append(elementGroup.getBorderColor()).append("\">\n");
		for(LayoutElement element : elementGroup.getChildren()){
			if(element instanceof BR){
				appendXML(xml, (BR)element);
			}else if(element instanceof DropListGroup){
				appendXML(xml, (DropListGroup)element);
			}else if(element instanceof ElementGroup){
				appendXML(xml, (ElementGroup)element);
			}else if(element instanceof Line){
				appendXML(xml, (Line)element);
			}else if(element instanceof DropListGroup){
				appendXML(xml, (DropListGroup)element);
			}else if(element instanceof TabPage){
				appendXML(xml, (TabPage)element);
			}else if(element instanceof Element){
				appendXML(xml, (Element)element);
			}else if(element instanceof TabPage){
				appendXML(xml, (TabPage)element);
			}
		}
		
		xml.append("</elementGroup>\n");
	}
	
	public static void appendXML(StringBuilder xml, DropListGroup dropListGroup){
		if (!StringUtil.isEmpty(dropListGroup.getName()))
			xml.append("<dropListGroup name=\"{").append(dropListGroup.getName()).append("}\"");
		else
			xml.append("<dropListGroup name=\"{").append(dropListGroup.getName()).append("}\"");
		xml.append(" refPropertyGroup=\"").append(dropListGroup.getRefPropertyGroupCode()).append("\"");
		xml.append(" type=\"").append(dropListGroup.getName()).append("\">");
		for(LayoutElement element : dropListGroup.getChildren()){
			if(element instanceof BR){
				appendXML(xml, (BR)element);
			}else if(element instanceof DropListGroup){
				appendXML(xml, (DropListGroup)element);
			}else if(element instanceof ElementGroup){
				appendXML(xml, (ElementGroup)element);
			}else if(element instanceof Line){
				appendXML(xml, (Line)element);
			}else if(element instanceof DropListGroup){
				appendXML(xml, (DropListGroup)element);
			}else if(element instanceof TabPage){
				appendXML(xml, (TabPage)element);
			}else if(element instanceof Element){
				appendXML(xml, (Element)element);
			}else if(element instanceof TabPage){
				appendXML(xml, (TabPage)element);
			}
		}
		
		xml.append("</dropListGroup>\n");
	}
	
	public static void appendXML(StringBuilder xml, Element element){
		xml.append("<element refPropertyGroup=\"").append(element.getRefPropertyGroupCode()).append("\"");
		xml.append(" refProperty=\"").append(element.getRefPropertyCode()).append("\"");
		if (!StringUtil.isEmpty(element.getName()))
			xml.append(" name=\"{").append(element.getName()).append("}\"");
		else
			xml.append(" name=\"").append(element.getName()).append("\"");
		xml.append(" isController=\"").append(element.isController()).append("\">");
		xml.append("</element>\n");
	}
}
