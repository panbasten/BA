package nc.tc.uap.vo.bq.chart.model;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import uap.vo.bq.chart.model.dataset.ChartBody;
import uap.vo.bq.chart.model.dataset.ChartBodyRow;
import uap.vo.bq.chart.model.dataset.ChartDataCell;
import uap.vo.bq.chart.model.dataset.ChartDataset;
import uap.vo.bq.chart.model.dataset.ChartDataset.ChartDataElement;
import uap.vo.bq.chart.model.dataset.ChartHeader;
import uap.vo.bq.chart.model.dataset.ChartHeaderCell;
import uap.vo.bq.chart.model.dataset.IChartDataElement;

import com.yonyou.uat.framework.BaseTestCase;
public class DatasetTest extends BaseTestCase {
  ChartDataset dataset=null;
  Object retObj;
 
  @BeforeClass 
  public void BeforeClass(){
	IChartDataElement datasetCode = new ChartDataElement("111","");
    dataset = new ChartDataset(datasetCode);
    ChartHeader header = new ChartHeader (datasetCode);
    header.addHeaderCell(new ChartHeaderCell(new ChartDataElement("0", "one feild")));
    header.addHeaderCell(new ChartHeaderCell(new ChartDataElement("0", "two feild")));
    header.addHeaderCell(new ChartHeaderCell(new ChartDataElement("0", "thire feild")));
    header.addHeaderCell(new ChartHeaderCell(new ChartDataElement("0", "fore feild")));
	//
	ChartBody chartBody = new ChartBody (datasetCode);
	int count = header.getHeaderCellCount();
	for (int i = 0; i < count; ++i){
		chartBody.addChartBodyRow(new ChartBodyRow(new ChartDataCell(new ChartDataElement(String.valueOf(i),"value-" + String.valueOf(i)))));
	}

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
  public void testToString(){
	  
		String result = dataset.toString();
    
		StringBuilder str = new StringBuilder();
		str.append("code: null\n");
		str.append("name: null\n");
		str.append("rows: {\n");
		str.append("	aaa,bbb\n");
		str.append("}");
		
		//Assert.assertEquals(result, str.toString());
  }
}
