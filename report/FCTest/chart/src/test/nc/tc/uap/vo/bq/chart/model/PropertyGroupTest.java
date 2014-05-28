package nc.tc.uap.vo.bq.chart.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nc.vo.pub.format.exception.FormatException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import uap.impl.bq.chart.define.ChartDefineFactory;
import uap.util.bq.chart.ServerInfoUtil;
import uap.vo.bq.chart.define.ChartDefine;
import uap.vo.bq.chart.model.ChartModel;
import uap.vo.bq.chart.model.ChartModelFactory;
import uap.vo.bq.chart.model.PropertyGroup;

import com.yonyou.uat.framework.BaseTestCase;

public class PropertyGroupTest extends BaseTestCase {
	ChartDefine define = null;
	ChartModel model = null;
	List<PropertyGroup> groupList;
	Object retObj;

	@BeforeClass
	public void BeforeClass() throws FormatException, IOException {
		//ServerInfoUtil.setLocalModel();
		ChartDefineFactory factory;
		factory = ChartDefineFactory.getInstance();
		define = factory.getChartDefine("MSColumn2D");
		model = ChartModelFactory.createChartModel("MSColumn2D");
		PropertyGroup group = new PropertyGroup("test");
		PropertyGroup group2 = new PropertyGroup("test");
		PropertyGroup group3 = new PropertyGroup("test");
		groupList = new ArrayList<PropertyGroup>();
		groupList.add(group);
		groupList.add(group2);
		groupList.add(group3);
		model.setPropertyGroups(groupList);
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
	public void getCode() {
		for(int i = 0; i < groupList.size(); i ++){
			PropertyGroup group = groupList.get(i);
			System.out.println(group.getCode());
			//Assert.assertEquals(group.getCode(), "test." + i);	
		}
	}
}
