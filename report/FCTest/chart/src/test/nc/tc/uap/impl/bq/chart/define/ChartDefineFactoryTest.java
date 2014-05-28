package nc.tc.uap.impl.bq.chart.define;

import java.io.File;
import java.io.IOException;
import java.util.List;

import nc.vo.pub.format.exception.FormatException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import uap.impl.bq.chart.define.ChartDefineFactory;
import uap.impl.bq.chart.util.BackEndServerInfoUtil;
import uap.util.bq.chart.ChartUtil;
import uap.vo.bq.chart.define.APIDefine;
import uap.vo.bq.chart.define.ChartDefine;
import uap.vo.bq.chart.define.ChartDefineDigest;
import uap.vo.bq.chart.define.DefaultSetting;
import uap.vo.bq.chart.define.EventDefine;
import uap.vo.bq.chart.define.Icon;
import uap.vo.bq.chart.define.PropertyGroupDefine;
import uap.vo.bq.chart.model.PropertyGroup;

import com.ufida.iufo.pub.tools.AppDebug;
import com.yonyou.uat.framework.BaseTestCase;

public class ChartDefineFactoryTest extends BaseTestCase {
	ChartDefineFactory chartDefineFactory = null;

	@BeforeClass
	public void BeforeClass() {

		chartDefineFactory = ChartDefineFactory.getInstance();
	}

	@AfterClass
	public void AfterClass() {
	}

	@BeforeMethod
	public void BeforeMethod() {

	}

	@AfterMethod
	public void AfterMethod() {

	}
	

	@Test(description = "", dependsOnMethods = {}, groups = "", timeOut = 100000)
	public void generatorChartDefine() {
		//ServerInfoUtil.setLocalModel();
		// Construct method parameters
		String root = BackEndServerInfoUtil.getServerRootPath();
		String filePath = root + "/chartDefines/MSColumn2D/chartDefine.xml";
		File file = new File(filePath);
		// if (file==null) {
		// Assert.assertEquals(false,true);
		// return ;
		// }

		// Invoke tested method
		ChartDefine retObj = null;
		try {
			retObj = chartDefineFactory.generatorChartDefine(file);
		} catch (FormatException e) {
			Assert.assertEquals(false, true);
			AppDebug.debug(e);
		}catch (Exception e) {
			
		}

		// Verify result is ok

		// Verify Object1 == Object2
		Assert.assertNotNull(retObj);
		Assert.assertNotNull(retObj.getApiDefines());
		Assert.assertNotNull(retObj.getCategory());
//		Assert.assertEquals(retObj.getCategory(), "基本统计图");
		Assert.assertNotNull(retObj.getChartDefineFilePath());
		Assert.assertEquals(retObj.getChartDefineFilePath(),
				file.getAbsolutePath());
		Assert.assertNotNull(retObj.getCode());
		Assert.assertEquals(retObj.getCode(), "MSColumn2D");
		Assert.assertNotNull(retObj.getDefaultSetting());
		Assert.assertNotNull(retObj.getEventDefines());
		Assert.assertNotNull(retObj.getIcon());
		Assert.assertNotNull(retObj.getName());
//		Assert.assertEquals(retObj.getName(), "多系列2D柱状图");
		Assert.assertNotNull(retObj.getPropertyGroupDefines());
		Assert.assertNotNull(retObj.getType());
//		Assert.assertEquals(retObj.getType(), "柱状图");
	}
	
	@Test(description = "", dependsOnMethods = {}, groups = "", timeOut = 100000)
	public void testMSLineChartDefineXml() throws FormatException, IOException {
		compare("MSLine");
	}
	
	@Test(description = "", dependsOnMethods = {}, groups = "", timeOut = 100000)
	public void testMSAreaChartDefineXml() throws FormatException, IOException {
		compare("MSArea");
	}
	
	@Test(description = "", dependsOnMethods = {}, groups = "", timeOut = 100000)
	public void testPie2DChartDefineXml() throws FormatException, IOException {
		compare("Pie2D");
	}
	
	@Test(description = "", dependsOnMethods = {}, groups = "", timeOut = 100000)
	public void testMSColumn2DChartDefineXml() throws FormatException, IOException {
		compare("MSColumn2D");
	}
	
	private boolean compare(String chartcode) throws FormatException, IOException{
		//String root = BackEndServerInfoUtil.getServerRootPath();
		//String filePath = root + "/chartDefines/"+ chartcode +"/chartDefine.xml";
		
		ChartDefine retObj = null;
		retObj = chartDefineFactory.getChartDefine(chartcode);
		
		File xmlfile = new File(retObj.getChartDefineFilePath());
		SAXReader saxReader = new SAXReader();
		Document xmlDoc = null;
		try {
			xmlDoc = saxReader.read(xmlfile);
		} catch (DocumentException e) {
			AppDebug.debug(e);
		}
		Assert.assertNotNull(xmlDoc);
		Element rootelement = xmlDoc.getRootElement();
		
		
		Assert.assertNotNull(retObj);
		Assert.assertNotNull(retObj.getApiDefines());
		Assert.assertNotNull(retObj.getCategory());
		Assert.assertEquals(retObj.getCategory(), ChartUtil.FromLangRes(rootelement, "category"));//.attribute().getText());
		Assert.assertNotNull(retObj.getChartDefineFilePath());
		Assert.assertEquals(retObj.getChartDefineFilePath(),
				xmlfile.getAbsolutePath());
		Assert.assertNotNull(retObj.getCode());
		Assert.assertEquals(retObj.getCode(), rootelement.attribute("code").getText());
		Assert.assertNotNull(retObj.getDefaultSetting());
		Assert.assertNotNull(retObj.getEventDefines());
		Assert.assertNotNull(retObj.getIcon());
		Assert.assertNotNull(retObj.getName());
		Assert.assertEquals(retObj.getName(), ChartUtil.FromLangRes(rootelement,"name"));
		Assert.assertNotNull(retObj.getPropertyGroupDefines());
		Assert.assertNotNull(retObj.getType());
		Assert.assertEquals(retObj.getType(), ChartUtil.FromLangRes(rootelement,"type"));
		
		Element iconelement = rootelement.element("icon");
		compareIcon(retObj,iconelement);
		
		Element propertyGroupDefineselement = rootelement.element("propertyGroupDefines");
		comparepropertyGroupDefines(retObj,propertyGroupDefineselement);
		
		Element defaultSettingelement = rootelement.element("defaultSetting");
		comparedefaultSetting(retObj,defaultSettingelement);
		
		Element apiDefineselement = rootelement.element("apiDefines");
		compareapiDefines(retObj,apiDefineselement);
		
		Element eventDefineselement = rootelement.element("eventDefines");
		compareeventDefines(retObj,eventDefineselement);
		
		return true;
	}
	
	private void compareeventDefines(ChartDefine retObj,Element eventDefineselement){
		
		EventDefine[] eds = retObj.getEventDefines();
		List<Element> eventDefineelements = eventDefineselement.elements("eventDefine");
		Assert.assertEquals(eds.length,eventDefineelements.size());
		
	}
	
	private void compareapiDefines(ChartDefine retObj,Element apiDefineselement){
		
		APIDefine[] ads = retObj.getApiDefines();
		List<Element> apiDefineelements = apiDefineselement.elements("apiDefine");
		Assert.assertEquals(ads.length,apiDefineelements.size());
		
	}
	
	private void comparedefaultSetting(ChartDefine retObj,Element defaultSettingelement){
		
		DefaultSetting ds = retObj.getDefaultSetting();
		List<Element> elements = defaultSettingelement.elements();
		int i = 0;
		PropertyGroup[] propergroups = ds.getPropertyGroups();
		for (PropertyGroup group : propergroups){
		//for(int i=0; i<propergroups.length; i++){
			Assert.assertEquals(group.getRefCode(),elements.get(i++).attributeValue("refCode"));
		}
		
//		List<DatasetDefine> datasetDefines = ds.getDatasetDefines();
//		List<Element> datasetDefineelements = defaultSettingelement.elements("datasetDefine");
//		Assert.assertEquals(datasetDefines.size(),datasetDefineelements.size());
//		for(int i=0; i<datasetDefines.size(); i++){
//			Assert.assertEquals(datasetDefines.get(i).getCode(),datasetDefineelements.get(i).attributeValue("code"));
//			Assert.assertEquals(datasetDefines.get(i).getName(),datasetDefineelements.get(i).attributeValue("name"));
//		}
	}
	
	private void comparepropertyGroupDefines(ChartDefine retObj,Element propertyGroupDefineselement){
		PropertyGroupDefine[] propertyGroupDefines = retObj.getPropertyGroupDefines();
		List<Element> elements = propertyGroupDefineselement.elements();
		
		Assert.assertEquals(propertyGroupDefines.length,elements.size());
		
		for(int i=0;i<propertyGroupDefines.length;i++){
			Assert.assertEquals(propertyGroupDefines[i].getCode(),elements.get(i).attributeValue("code"));
//			Assert.assertEquals(propertyGroupDefines[i].getName(),elements.get(i).attributeValue("name"));
		}
	}
	
	private void compareIcon(ChartDefine retObj,Element iconelement){
		Icon icon = retObj.getIcon();
		
		Assert.assertEquals(icon.getBigIcon(),iconelement.elementText("bigIcon"));
		Assert.assertEquals(icon.getLittleIcon(),iconelement.elementText("littleIcon"));
		Assert.assertEquals(icon.getMouseOverIcon(),iconelement.elementText("mouseOverIcon"));
		Assert.assertEquals(icon.getPressedIcon(),iconelement.elementText("pressedIcon"));
		
	}

	@Test(description = "", dependsOnMethods = {}, groups = "", timeOut = 100000)
	public void getChartDefine() throws FormatException, IOException {

		// Construct method parameters
		String chartDefineCode = "MSColumn2D";

		// Invoke tested method
		ChartDefine retObj = null;
		retObj = chartDefineFactory.getChartDefine(chartDefineCode);

		// Verify result is ok

		// Verify Object1 == Object2
		Assert.assertNotNull(retObj);
		Assert.assertNotNull(retObj.getApiDefines());
		Assert.assertNotNull(retObj.getCategory());
//		Assert.assertEquals(retObj.getCategory(), "基本统计图");
		Assert.assertNotNull(retObj.getChartDefineFilePath());
		File file = new File(retObj.getChartDefineFilePath());
		Assert.assertEquals(retObj.getChartDefineFilePath(),
				file.getAbsolutePath());
		Assert.assertNotNull(retObj.getCode());
		Assert.assertEquals(retObj.getCode(), "MSColumn2D");
		Assert.assertNotNull(retObj.getDefaultSetting());
		Assert.assertNotNull(retObj.getEventDefines());
		Assert.assertNotNull(retObj.getIcon());
		Assert.assertNotNull(retObj.getName());
//		Assert.assertEquals(retObj.getName(), "2D柱状图");
		Assert.assertNotNull(retObj.getPropertyGroupDefines());
		Assert.assertNotNull(retObj.getType());
//		Assert.assertEquals(retObj.getType(), "柱状图");
	}

	@Test(description = "", dependsOnMethods = {}, groups = "", timeOut = 100000)
	public void getChartDefineDigests() throws FormatException, IOException {

		// Construct method parameters

		// Invoke tested method
		ChartDefineDigest[] retObj = {};
		retObj = chartDefineFactory.getChartDefineDigests();

		// Verify result is ok

		// Verify Object1 == Object2
		Assert.assertNotNull(retObj);
		Assert.assertEquals(retObj.length > 0, true);
		for (ChartDefineDigest cdd : retObj) {
			ChartDefine cd = null;
			Assert.assertNotNull(cdd.getCode());
			cd = chartDefineFactory.getChartDefine(cdd.getCode());

			Assert.assertNotNull(cdd.getCode());
			Assert.assertEquals(cdd.getCode(), cd.getCode());
			Assert.assertNotNull(cdd.getCategory());
			Assert.assertEquals(cdd.getCategory(), cd.getCategory());
			Assert.assertNotNull(cdd.getIcon());
			Assert.assertEquals(cdd.getIcon(), cd.getIcon());
			Assert.assertNotNull(cdd.getName());
			Assert.assertEquals(cdd.getName(), cd.getName());
			Assert.assertNotNull(cdd.getType());
			Assert.assertEquals(cdd.getType(), cd.getType());
		}
	}

	@Test(description = "", dependsOnMethods = {}, groups = "", timeOut = 100000)
	public void getChartDefines() throws FormatException, IOException {

		// Construct method parameters
		String[] chartDefineCodes = { "MSColumn2D" };

		// Invoke tested method
		ChartDefine[] retObj = {};
		retObj = chartDefineFactory.getChartDefines(chartDefineCodes);

		// Verify result is ok

		// Verify Object1 == Object2
		Assert.assertNotNull(retObj);
		Assert.assertEquals(retObj.length, chartDefineCodes.length);
	}
}
