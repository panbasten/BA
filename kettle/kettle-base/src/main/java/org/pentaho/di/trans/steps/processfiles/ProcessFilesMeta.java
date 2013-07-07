/*******************************************************************************
 *
 * Pentaho Data Integration
 *
 * Copyright (C) 2002-2012 by Pentaho : http://www.pentaho.com
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/

package org.pentaho.di.trans.steps.processfiles;
import java.util.List;
import java.util.Map;

import org.pentaho.di.core.CheckResult;
import org.pentaho.di.core.CheckResultInterface;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.Counter;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.exception.KettlePageException;
import org.pentaho.di.core.exception.KettleXMLException;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.xml.XMLHandler;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.repository.ObjectId;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.shared.SharedObjectInterface;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStepMeta;
import org.pentaho.di.trans.step.StepDataInterface;
import org.pentaho.di.trans.step.StepInterface;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.w3c.dom.Node;

/*
 * Created on 03-Juin-2008
 * 
 */

public class ProcessFilesMeta extends BaseStepMeta implements StepMetaInterface
{
	private static Class<?> PKG = ProcessFilesMeta.class; // for i18n purposes, needed by Translator2!!   $NON-NLS-1$

	private boolean addresultfilenames;
	private boolean overwritetargetfile;
	private boolean createparentfolder;
	public boolean simulate;
	
    /** dynamic filename */
    private String       sourcefilenamefield;
    private String       targetfilenamefield;
    
	/** Operations type */
	private int operationType;
	
	/**
	 * The operations description
	 */
	public final static String operationTypeDesc[] = {
			BaseMessages.getString(PKG, "ProcessFilesMeta.operationType.Copy"),
			BaseMessages.getString(PKG, "ProcessFilesMeta.operationType.Move"),
			BaseMessages.getString(PKG, "ProcessFilesMeta.operationType.Delete")};
	
	/**
	 * The operations type codes
	 */
	public final static String operationTypeCode[] = { "copy", "move", "delete" };

	public final static int OPERATION_TYPE_COPY = 0;

	public final static int OPERATION_TYPE_MOVE = 1;

	public final static int OPERATION_TYPE_DELETE = 2;

    
    public ProcessFilesMeta()
    {
        super(); // allocate BaseStepMeta
    }
    public int getOperationType() {
		return operationType;
	}
	public static int getOperationTypeByDesc(String tt) {
		if (tt == null)
			return 0;

		for (int i = 0; i < operationTypeDesc.length; i++) {
			if (operationTypeDesc[i].equalsIgnoreCase(tt))
				return i;
		}
		// If this fails, try to match using the code.
		return getOperationTypeByCode(tt);
	}
	public void setOperationType(int operationType) {
		this.operationType = operationType;
	}
	public static String getOperationTypeDesc(int i) {
		if (i < 0 || i >= operationTypeDesc.length)
			return operationTypeDesc[0];
		return operationTypeDesc[i];
	}
    /**
     * @return Returns the sourcefilenamefield.
     */
    public String getDynamicSourceFileNameField()
    {
        return sourcefilenamefield;
    }

    /**
     * @param sourcefilenamefield The sourcefilenamefield to set.
     */
    public void setDynamicSourceFileNameField(String sourcefilenamefield)
    {
        this.sourcefilenamefield = sourcefilenamefield;
    }

    /**
     * @return Returns the targetfilenamefield.
     */
    public String getDynamicTargetFileNameField()
    {
        return targetfilenamefield;
    }

    /**
     * @param targetfilenamefield The targetfilenamefield to set.
     */
    public void setDynamicTargetFileNameField(String targetfilenamefield)
    {
        this.targetfilenamefield = targetfilenamefield;
    }
    public boolean isaddTargetFileNametoResult()
    {
    	return addresultfilenames;
    }
    public boolean isOverwriteTargetFile()
    {
    	return overwritetargetfile;
    }
    public boolean isCreateParentFolder()
    {
    	return createparentfolder;
    }
    
    
    public void setaddTargetFileNametoResult(boolean addresultfilenames)
    {
    	this.addresultfilenames=addresultfilenames;
    }
    
    public void setOverwriteTargetFile(boolean overwritetargetfile)
    {
    	this.overwritetargetfile=overwritetargetfile;
    }
    public void setCreateParentFolder(boolean createparentfolder)
    {
    	this.createparentfolder=createparentfolder;
    }
    public void setSimulate(boolean simulate)
    {
    	this.simulate=simulate;
    }
    public boolean isSimulate()
    {
    	return this.simulate;
    }
    
    @Override
    public void loadPage(Map<String, List<String>> parameterHolder)
    	throws KettlePageException
    {
    	// TODO
    }
    
	public void loadXML(Node stepnode, List<DatabaseMeta> databases, Map<String, Counter> counters)
		throws KettleXMLException
	{
		readData(stepnode, databases);
	}
 

    public Object clone()
    {
        ProcessFilesMeta retval = (ProcessFilesMeta) super.clone();
       
        return retval;
    }

    public void setDefault()
    {
        addresultfilenames=false;
        overwritetargetfile=false;
        createparentfolder=false;
        simulate=true;
        operationType=OPERATION_TYPE_COPY;
    }
 
    public String getXML()
    {
        StringBuffer retval = new StringBuffer();

        retval.append("    " + XMLHandler.addTagValue("sourcefilenamefield", sourcefilenamefield)); //$NON-NLS-1$ //$NON-NLS-2$
        retval.append("    " + XMLHandler.addTagValue("targetfilenamefield", targetfilenamefield)); 
        retval.append("    ").append(XMLHandler.addTagValue("operation_type",getOperationTypeCode(operationType)));
        retval.append("    ").append(XMLHandler.addTagValue("addresultfilenames",       addresultfilenames));
        retval.append("    ").append(XMLHandler.addTagValue("overwritetargetfile",       overwritetargetfile));
        retval.append("    ").append(XMLHandler.addTagValue("createparentfolder",       createparentfolder)); 
        retval.append("    ").append(XMLHandler.addTagValue("simulate",       simulate)); 
        
        return retval.toString();
    }
	private static String getOperationTypeCode(int i) {
		if (i < 0 || i >= operationTypeCode.length)
			return operationTypeCode[0];
		return operationTypeCode[i];
	}
	private void readData(Node stepnode, List<? extends SharedObjectInterface> databases)
	throws KettleXMLException
	{
		try
		{
			sourcefilenamefield = XMLHandler.getTagValue(stepnode, "sourcefilenamefield"); 
			targetfilenamefield = XMLHandler.getTagValue(stepnode, "targetfilenamefield"); 
			operationType = getOperationTypeByCode(Const.NVL(XMLHandler.getTagValue(stepnode,	"operation_type"), ""));
	        addresultfilenames  = "Y".equalsIgnoreCase(XMLHandler.getTagValue(stepnode, "addresultfilenames"));   
	        overwritetargetfile  = "Y".equalsIgnoreCase(XMLHandler.getTagValue(stepnode, "overwritetargetfile"));   
	        createparentfolder  = "Y".equalsIgnoreCase(XMLHandler.getTagValue(stepnode, "createparentfolder"));   
	        simulate  = "Y".equalsIgnoreCase(XMLHandler.getTagValue(stepnode, "simulate")); 
	        
		}
	    catch (Exception e)
	    {
	        throw new KettleXMLException(BaseMessages.getString(PKG, "ProcessFilesMeta.Exception.UnableToReadStepInfo"), e); //$NON-NLS-1$
	    }
    }
    private static int getOperationTypeByCode(String tt) {
		if (tt == null)
			return 0;

		for (int i = 0; i < operationTypeCode.length; i++) {
			if (operationTypeCode[i].equalsIgnoreCase(tt))
				return i;
		}
		return 0;
	}
	public void readRep(Repository rep, ObjectId id_step, List<DatabaseMeta> databases, Map<String, Counter> counters)
	throws KettleException
	{
        try
        {
        	sourcefilenamefield = rep.getStepAttributeString(id_step, "sourcefilenamefield"); //$NON-NLS-1$
        	targetfilenamefield = rep.getStepAttributeString(id_step, "targetfilenamefield");
        	operationType = getOperationTypeByCode(Const.NVL(rep.getStepAttributeString(id_step, "operation_type"), ""));
        	addresultfilenames  = rep.getStepAttributeBoolean(id_step, "addresultfilenames"); 
        	overwritetargetfile  = rep.getStepAttributeBoolean(id_step, "overwritetargetfile"); 
        	createparentfolder  = rep.getStepAttributeBoolean(id_step, "createparentfolder"); 
        	simulate  = rep.getStepAttributeBoolean(id_step, "simulate"); 
        	
        }
        catch (Exception e)
        {
            throw new KettleException(BaseMessages.getString(PKG, "ProcessFilesMeta.Exception.UnexpectedErrorReadingStepInfo"), e); //$NON-NLS-1$
        }
    }

    public void saveRep(Repository rep, ObjectId id_transformation, ObjectId id_step) throws KettleException
    {
        try
        {
            rep.saveStepAttribute(id_transformation, id_step, "sourcefilenamefield", sourcefilenamefield); //$NON-NLS-1$
            rep.saveStepAttribute(id_transformation, id_step, "targetfilenamefield", targetfilenamefield); //$NON-NLS-1$
            rep.saveStepAttribute(id_transformation, id_step, "operation_type", getOperationTypeCode(operationType));
            rep.saveStepAttribute(id_transformation, id_step, "addresultfilenames",          addresultfilenames);
            rep.saveStepAttribute(id_transformation, id_step, "overwritetargetfile",          overwritetargetfile);
            rep.saveStepAttribute(id_transformation, id_step, "createparentfolder",          createparentfolder);
            rep.saveStepAttribute(id_transformation, id_step, "simulate",          simulate);
            
        }
        catch (Exception e)
        {
            throw new KettleException(BaseMessages.getString(PKG, "ProcessFilesMeta.Exception.UnableToSaveStepInfo") + id_step, e); //$NON-NLS-1$
        }
    }

    public void check(List<CheckResultInterface> remarks, TransMeta transMeta, StepMeta stepMeta, RowMetaInterface prev, String input[], String output[], RowMetaInterface info)
	{
        CheckResult cr;
        String error_message = ""; //$NON-NLS-1$

        // source filename
        if (Const.isEmpty(sourcefilenamefield))
        {
            error_message = BaseMessages.getString(PKG, "ProcessFilesMeta.CheckResult.SourceFileFieldMissing"); //$NON-NLS-1$
            cr = new CheckResult(CheckResult.TYPE_RESULT_ERROR, error_message, stepMeta);
            remarks.add(cr);
        }
        else
        {
            error_message = BaseMessages.getString(PKG, "ProcessFilesMeta.CheckResult.TargetFileFieldOK"); //$NON-NLS-1$
            cr = new CheckResult(CheckResult.TYPE_RESULT_OK, error_message, stepMeta);
            remarks.add(cr);
        }
        if (operationType!=OPERATION_TYPE_DELETE && Const.isEmpty(targetfilenamefield))
        {
            error_message = BaseMessages.getString(PKG, "ProcessFilesMeta.CheckResult.TargetFileFieldMissing"); //$NON-NLS-1$
            cr = new CheckResult(CheckResult.TYPE_RESULT_ERROR, error_message, stepMeta);
            remarks.add(cr);
        }
        else
        {
            error_message = BaseMessages.getString(PKG, "ProcessFilesMeta.CheckResult.SourceFileFieldOK"); //$NON-NLS-1$
            cr = new CheckResult(CheckResult.TYPE_RESULT_OK, error_message, stepMeta);
            remarks.add(cr);
        }
        
        // See if we have input streams leading to this step!
        if (input.length > 0)
        {
            cr = new CheckResult(CheckResult.TYPE_RESULT_OK, BaseMessages.getString(PKG, "ProcessFilesMeta.CheckResult.ReceivingInfoFromOtherSteps"), stepMeta); //$NON-NLS-1$
            remarks.add(cr);
        }
        else
        {
            cr = new CheckResult(CheckResult.TYPE_RESULT_ERROR, BaseMessages.getString(PKG, "ProcessFilesMeta.CheckResult.NoInpuReceived"), stepMeta); //$NON-NLS-1$
            remarks.add(cr);
        }

    }

    public StepInterface getStep(StepMeta stepMeta, StepDataInterface stepDataInterface, int cnr, TransMeta transMeta, Trans trans)
    {
        return new ProcessFiles(stepMeta, stepDataInterface, cnr, transMeta, trans);
    }

    public StepDataInterface getStepData()
    {
        return new ProcessFilesData();
    }

    public boolean supportsErrorHandling()
    {
        return true;
    }
}
