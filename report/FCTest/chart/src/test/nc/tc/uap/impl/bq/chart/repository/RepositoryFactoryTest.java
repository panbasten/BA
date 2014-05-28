package nc.tc.uap.impl.bq.chart.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import nc.tc.uap.impl.bq.chart.XMLCompareUtil;
import nc.tc.uap.impl.bq.chart.XMLCompareUtil.CompareType;
import nc.vo.pub.format.exception.FormatException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import uap.impl.bq.chart.repository.PropertyRepository;
import uap.impl.bq.chart.repository.RepositoryFactory;
import uap.impl.bq.chart.repository.TypeRepository;
import uap.impl.bq.chart.util.BackEndServerInfoUtil;
import uap.util.bq.chart.ServerInfoUtil;
import uap.vo.bq.chart.define.ConstraintTerm;
import uap.vo.bq.chart.define.EditorDefine;
import uap.vo.bq.chart.define.PropertyDefine;
import uap.vo.bq.chart.define.PropertyGroupDefine;
import uap.vo.bq.chart.define.Type;

import com.ufida.iufo.pub.tools.AppDebug;
import com.yonyou.uat.framework.BaseTestCase;

/**
 * Repository 工程类测试类， 支持 个数， 特定名称测试
 * @author hbyxl
 *
 */
public class RepositoryFactoryTest extends BaseTestCase {
	
	private String propertyPath;// = BackEndServerInfoUtil.getServerRootPath() + "/repository/propertyRepository.xml";
	private String typePath;// = BackEndServerInfoUtil.getServerRootPath() + "/repository/typeRepository.xml";

	public String[] testKeyArr = {"sfd","fsdf"};
	
	private PropertyRepository propertyRepository = null;
	private TypeRepository typeRepository = null;
	
	private void init(){
		//ServerInfoUtil.setLocalModel();
		propertyPath = BackEndServerInfoUtil.getServerRootPath() + "/repository/propertyRepository.xml";
		typePath = BackEndServerInfoUtil.getServerRootPath() + "/repository/typeRepository.xml";
		try {
			propertyRepository = RepositoryFactory.getInstance().getPropertyRepository();
		} catch (FormatException e) {
			//Assert.assertEquals(false, true);
			AppDebug.debug(e);
		}
		try {
			typeRepository = RepositoryFactory.getInstance().getTypeRepository();
		} catch (FormatException e) {
			//Assert.assertEquals(false, true);
			AppDebug.debug(e);
		}
	}
	
	private int getTypeCount(){
		int count = 0;
		try {
			SAXReader reader = new SAXReader ();
			Document propertyGroupsDocument;
			File file = new File(typePath);
			
			propertyGroupsDocument = reader.read(file);
			Assert.assertNotEquals(propertyGroupsDocument, null);
			Element rootElement = propertyGroupsDocument.getRootElement();
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			AppDebug.debug(e);
		}
		return count;
	}
	
	private long getPropertyCount(){
		return 0;
	}
	
	
	private String toXml_TypeRepository(){
		Type[] typeArr = this.typeRepository.getTypes();
		XmlHelperGen xmlGen = new XmlHelperGen();
		StringBuilder xmlBuf = XmlHelperGen.getXml(); 
		xmlGen.attachBuf(xmlBuf);
		xmlGen.addElement("typeRepository");
		xmlGen.endAttr();
		for ( Type ty : typeArr ){
			xmlGen.addElement("Type");
			xmlGen.addAttr("code", ty.getCode());
			xmlGen.addAttr("name", ty.getName());
			
		if(ty.getEditorDefines()==null||ty.getEditorDefines().length==0)
				xmlGen.endElementShortter();
		else{
			xmlGen.endAttr();
			
			xmlGen.addElement("editor");
			xmlGen.addAttr("name", ty.getEditorDefines()[0].getEditorName() );
			xmlGen.endAttr();
			
			for (EditorDefine def : ty.getEditorDefines() ){
				xmlGen.addElement("swEditor");
				xmlGen.addAttr("implClass", def.getSwEditorImplClass());
				xmlGen.endElementShortter();
				xmlGen.addElement("lwEditor");
				xmlGen.addAttr("implClass", def.getLwEditorImplClass());
				xmlGen.endElementShortter();
			}
			xmlGen.endElement("editor");	
			xmlGen.endElement("Type");
		}
		}
		xmlGen.endElement("typeRepository");
		
		return xmlGen.toString();
	}
	
	private String toXml_PropertyRepository(){
		PropertyGroupDefine[] propDefineArr = this.propertyRepository.getPropertyGroupDefines();
		XmlHelperGen xmlGen = new XmlHelperGen();
		StringBuilder xmlBuf = XmlHelperGen.getXml(); 
		xmlGen.attachBuf(xmlBuf);
		xmlGen.addElement("propertyRepository");
		xmlGen.endAttr();
		
		for ( PropertyGroupDefine groupDef : propDefineArr){
			
			xmlGen.addElement("propertyGroupDefine");
			xmlGen.addAttr("code", groupDef.getCode());
			xmlGen.addAttr("name", "{"+groupDef.getName()+"}");
			if(groupDef.getType()!=null)
				xmlGen.addAttr("type", groupDef.getType().getName());
			if(groupDef.getEditorDefine()!=null)xmlGen.addAttr("editorDefine", groupDef.getEditorDefine().getEditorName());
			if(groupDef.isRelativeChange())xmlGen.addAttr("relativeChange", "true");
			if(groupDef.isRepeatable())xmlGen.addAttr("repeatable", "true");
			
			xmlGen.endAttr();
			for ( PropertyDefine propDef : groupDef.getProperties() ){
				xmlGen.addElement("propertyDefine");
				xmlGen.addAttr("code", propDef.getCode());
				xmlGen.addAttr("name", "{"+propDef.getName()+"}");
				if (propDef.getType()!=null)
					xmlGen.addAttr("type", propDef.getType().getName());
				else
					xmlGen.addAttr("type", "");
				if(propDef.getRange()!=null)xmlGen.addAttr("range", propDef.getRange());
				xmlGen.addAttr("description", propDef.getDescription());
				
				if(propDef.isRelativeChange())xmlGen.addAttr("relativeChange", "true");
				
				ConstraintTerm[] cts=propDef.getConstraintTerms();
				if(cts==null||cts.length==0)xmlGen.endElementShortter();
				else{
					xmlGen.endAttr();
					for(ConstraintTerm ct:cts){
						xmlGen.addElement("ConstraintTerm");
						if (ct.getName().length() != ct.getName().getBytes().length){
							xmlGen.addAttr("name", "{"+ct.getName()+"}");
						}
						else{
							xmlGen.addAttr("name", ct.getName());
						}
						if (ct.getValue().length() != ct.getValue().getBytes().length){
							xmlGen.addAttr("value", "{"+ct.getValue()+"}");
						}
						else{
							xmlGen.addAttr("value", ct.getValue());
						}
						
						xmlGen.endElementShortter();
					}
					xmlGen.endElement("propertyDefine");
				}
				
			}
			xmlGen.endElement("propertyGroupDefine");
		}
		
		xmlGen.endElement("propertyRepository");
		
		return xmlGen.toString();
	}
	
	private static String getFileContent(String xmlPath) throws IOException{
		StringBuilder content = new StringBuilder();
		InputStreamReader reader = new InputStreamReader(new FileInputStream(xmlPath), "UTF-8");
		
		char[] buffer = new char[1024];
		for(;;){
			int count = reader.read(buffer);
			/*if(count==buffer.length){
				content.append(new String(buffer));
			}else*/
			if(count == -1){
				break;
			}else {
				if(count==buffer.length){
					content.append(new String(buffer));
				}else{
					content.append(new String(buffer).substring(0, count));
				}
			}
		}
		
		return content.toString();
	}
  
  public boolean toXML(String xml, String saveToFile) throws DocumentException{
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
	
	
  @Test(description = "", dependsOnMethods = {}, groups = "", timeOut = 100000)
  public void fun() throws FormatException, IOException, DocumentException {
	  init();
	// String s= propertyRepository.toString();
	 String propertyXml = toXml_PropertyRepository();
	 //String savepath1=BackEndServerInfoUtil.getServerRootPath() + "/test-propertyRepository.xml";
	 //toXML(propertyXml,savepath1);
	 XMLCompareUtil.compare(propertyXml, getFileContent(this.propertyPath), CompareType.INCLUDE);
	 String typeXml = toXml_TypeRepository();
	// String savepath2=BackEndServerInfoUtil.getServerRootPath() + "/test-typeRepository.xml";
	// toXML(typeXml,savepath2);
	 XMLCompareUtil.compare(typeXml, getFileContent(this.typePath), CompareType.INCLUDE);
  }
  @BeforeMethod
  public void beforeMethod() {
  }

  @AfterMethod
  public void afterMethod() {
  }

  @BeforeClass
  public void beforeClass() {
	  
  }

  @AfterClass
  public void afterClass() {
	 
  }

}
