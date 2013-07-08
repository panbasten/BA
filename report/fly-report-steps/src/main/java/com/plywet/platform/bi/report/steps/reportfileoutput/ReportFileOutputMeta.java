package com.plywet.platform.bi.report.steps.reportfileoutput;

import java.util.List;
import java.util.Map;

import org.pentaho.di.core.CheckResultInterface;
import org.pentaho.di.core.Counter;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.exception.KettlePageException;
import org.pentaho.di.core.exception.KettleXMLException;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.repository.ObjectId;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStepMeta;
import org.pentaho.di.trans.step.StepDataInterface;
import org.pentaho.di.trans.step.StepInterface;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.w3c.dom.Node;

public class ReportFileOutputMeta extends BaseStepMeta implements
		StepMetaInterface {

	@Override
	public void check(List<CheckResultInterface> remarks, TransMeta transMeta,
			StepMeta stepMeta, RowMetaInterface prev, String[] input,
			String[] output, RowMetaInterface info) {
		// TODO Auto-generated method stub

	}

	@Override
	public StepInterface getStep(StepMeta stepMeta,
			StepDataInterface stepDataInterface, int copyNr,
			TransMeta transMeta, Trans trans) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StepDataInterface getStepData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadXML(Node stepnode, List<DatabaseMeta> databases,
			Map<String, Counter> counters) throws KettleXMLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void readRep(Repository rep, ObjectId idStep,
			List<DatabaseMeta> databases, Map<String, Counter> counters)
			throws KettleException {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveRep(Repository rep, ObjectId idTransformation,
			ObjectId idStep) throws KettleException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDefault() {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadPage(Map<String, List<String>> parameterHolder)
			throws KettlePageException {
		// TODO Auto-generated method stub
		
	}

}
