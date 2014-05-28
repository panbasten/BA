package nc.tc.uap.vo.bq.chart.model;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import uap.util.bq.chart.ServerInfoUtil;
import uap.vo.bq.chart.model.ChangeHandleException;
import uap.vo.bq.chart.model.ChartModel;
import uap.vo.bq.chart.model.ChartModelFactory;
import uap.vo.bq.chart.model.ChartModelListener;
import uap.vo.bq.chart.model.PropertyGroup;

import com.ufida.iufo.pub.tools.AppDebug;
import com.yonyou.uat.dbmanagement.DBManage;
import com.yonyou.uat.framework.BaseTestCase;

public class ChartModelTest extends BaseTestCase {
	ChartModel chartModel = null;
	Object retObj;
	DBManage dbManage = null;

	@BeforeClass
	public void BeforeClass() {
		//ServerInfoUtil.setLocalModel();
		chartModel = ChartModelFactory.createChartModel("MSColumn2D");
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
	public void cloneTest() {
		// Invoke tested method
		Object retObj = chartModel.clone();

		// Verify result is ok

		// Verify Object1 == Object2
		Assert.assertNotNull(retObj);
	}

	@Test(description = "", dependsOnMethods = {}, groups = "", timeOut = 100000)
	public void fireChange() throws ClassNotFoundException {
		try {
			chartModel.addListener(new ChartModelListener(){

				@Override
				public void onChange(ChartModel chartModel)
						throws ChangeHandleException {
					System.out.println("onChange!");
				}
				
			});
			chartModel.fireChange();
		} catch (ChangeHandleException e) {
			Assert.assertEquals(false, true);
			AppDebug.debug(e);
		}

		// Verify result is ok

		// Verify Object1 == Object2
		Assert.assertEquals("true", "true");
	}
	
	@Test(description = "", dependsOnMethods = {}, groups = "", timeOut = 100000)
	public void doPropertyChange() throws ClassNotFoundException {
		// Invoke tested method
		ChartModel retObj =(ChartModel) chartModel.clone();
		PropertyGroup[] propGroups = retObj.getPropertyGroups();
		int size = propGroups.length;
					
		try {
			retObj.fireChange();
		} catch (ChangeHandleException e) {
			// TODO Auto-generated catch block
			AppDebug.debug(e);
		}
				
		int sizeAfter = retObj.getPropertyGroups().length;
		
		//doPropertyChange ��ɾ����datadefine�ı䣬relativeChange���ԣ��飬 sizeAfterҪС��
		Assert.assertTrue (size>=sizeAfter);
		
		//MSColumn2D groupline  vLine ����ΪrelativeChange�Ѿ��� �����MSColumn2D ��һ������
		// delete by wangqzh . �������ߵĲ�����ȫ�����ڶ����ļ��е�Ĭ�����ݣ����Ը����п���Ϊ0 ��Ҳ���ܴ��� 0����ȷ��
		// 
//		if ( retObj.getChartDefineCode().equals("MSColumn2D")){
//			int sizeLine = retObj.getPropertyGroupsByDefineCode("FC_TREND_LINES").size();
//			sizeLine += retObj.getPropertyGroupsByDefineCode("FC_VERTICAL_TREND_LINES").size();
//			Assert.assertTrue(sizeLine == 1);	
//		}
	}
}
