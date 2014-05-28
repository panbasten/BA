package nc.tc.uap.util.bq.chart;
import nc.vo.pub.format.exception.FormatException;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.testng.*;

import java.util.List;

import uap.vo.bq.chart.define.ChartDefine;
import uap.vo.bq.chart.model.ChartModel;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.ufida.iufo.pub.tools.AppDebug;
import com.yonyou.uat.framework.BaseTestCase;

import uap.bq.chart.generator.ChartGenerateException;
import uap.bq.chart.generator.DataGenerateResult;
import uap.impl.bq.chart.define.ChartDefineFactory;
import uap.util.bq.chart.ChartUtil;
import uap.util.bq.chart.ServerInfoUtil;

public class ChartUtilTest extends BaseTestCase {
  ChartUtil chartUtil=null;
  Object retObj;
  
  @BeforeClass 
  public void BeforeClass(){
    chartUtil=new ChartUtil();
  }
  
  @AfterClass 
  public void AfterClass(){
  }
  
  @BeforeMethod 
  public void BeforeMethod(){
   
  }
  
  @AfterMethod 
  public void AfterMethod(){
   
  }
  
  @Test(description="",dependsOnMethods={},groups="",timeOut=100000) 
  public void generateDisplayData() throws FormatException, IOException{
    //ServerInfoUtil.setLocalModel();
    //Construct method parameters
    String chartDefineCode = "MSColumn2D";
    ChartDefine define = ChartDefineFactory.getInstance().getChartDefine(chartDefineCode);
    ChartModel model = new ChartModel (chartDefineCode);
    
    
    //Invoke tested method
    DataGenerateResult retObj=null;
    try {
      retObj=ChartUtil.generateDisplayData(define,model);
      String xml = retObj.toChartDataXml().toString();
      CheckXmlFormat (xml);
     // Assert.assertNotNull(retObj.categoryDatas);
    }
    catch(DocumentException e) {
    	AppDebug.error(e);
    }
    catch ( ChartGenerateException e) {
    	AppDebug.error(e);
    } catch (ClassNotFoundException e) {
    	AppDebug.error(e);
	}
    
  }
  
  private void CheckXmlFormat (String xml) throws DocumentException{
	  Document doc = DocumentHelper.parseText(xml);
	  Assert.assertNotNull(doc);
	  Element elemChart = doc.getRootElement();
	  Assert.assertNotNull(elemChart);
	  
	  List<Element> elemList = elemChart.elements();
	  Assert.assertNotNull(elemList);
	  Assert.assertNotEquals(elemList.size(), 0);
	  // MSColumn2D 必有节点
	  String categories="";
	  String dataset = "";
	  int categoryCount = 0, setCount = 0;
	  for (Element elemItem : elemList){
		  if (elemItem.getName().equals("categories")){
			  categories = elemItem.getName();
			  List<Element> ls = elemItem.elements();
			  Assert.assertNotNull(ls);
			  Assert.assertNotEquals(ls.size(), 0);
			  
			  for (Element elemCategory : ls){
				  if (elemCategory.getName().equals("category")){
				  Assert.assertEquals(elemCategory.getName(),"category");
				  Attribute attri = elemCategory.attribute("label");
				  Assert.assertNotNull (attri);
				  // 由于多分类轴的功能，需要自动分组线，要显示最后一个分组线，则在vline后需要加一个空category才能
				  // 正常显示最后一个分组线, 所以不能有下边的断言判断。
				  // Assert.assertNotEquals(attri.getText(), "");
				  categoryCount++;
				  }
			  }
		  }
		  else if (elemItem.getName().equals("dataset")){
			  dataset = elemItem.getName();
			  List<Element> ls = elemItem.elements();
			  Assert.assertNotNull(ls);
			  Assert.assertNotEquals(ls.size(), 0);
			  setCount = 0;
			  for (Element elemDataset : ls){
				  Assert.assertEquals(elemDataset.getName(),"set");
				  Attribute attri = elemDataset.attribute("value");
				  Assert.assertNotNull (attri);
				  //Assert.assertNotEquals(attri.getText(), "");
				  setCount++;
			  }
			  /* 在多分组情况下，显示最后一个分组线需要额外的一个空category，因此category的数目应该大于等于set的数目 */
			  Assert.assertEquals(categoryCount >= setCount, true);
		  }
		  else if (elemItem.getName().equals("styles")){
			  List<Element> ls = elemItem.elements();
			  Assert.assertNotNull(ls);
			  Assert.assertNotEquals(ls.size(), 0);
			  
			  String application = "", definition = "";
			  for (Element elemStyle : ls){
				  if (elemStyle.getName().equals("application")){
					  application = elemStyle.getName();
					  Assert.assertNotNull(elemStyle.element("apply"));
				  }
				  else if (elemStyle.getName().equals("definition")){
					  definition = elemStyle.getName();
					  Element elem = elemStyle.element("style");
					  Assert.assertNotNull(elem);
					  Attribute attri = elem.attribute("type");
					  Assert.assertNotNull(attri);
				  }
				  else{
					  Assert.assertNotNull(null);
				  }
			  }
			  Assert.assertNotEquals(application, "");
			  Assert.assertNotEquals(definition, "");
		  }
		  else if (elemItem.getName().equals("trendlines")){
			  List<Element> ls = elemItem.elements();
			  Assert.assertNotNull(ls);
			  Assert.assertNotEquals(ls.size(), 0);
			  Element elem = elemItem.element("line");
			  Assert.assertNotNull(elem);
		  }
	  }
	  Assert.assertNotEquals(categories, "");
	  Assert.assertNotEquals(dataset, "");
	  
  }
}
