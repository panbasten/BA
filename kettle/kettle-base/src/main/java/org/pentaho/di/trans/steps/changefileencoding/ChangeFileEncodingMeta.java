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

package org.pentaho.di.trans.steps.changefileencoding;

import org.w3c.dom.Node;
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
import org.pentaho.di.repository.Repository;
import org.pentaho.di.repository.ObjectId;
import org.pentaho.di.shared.SharedObjectInterface;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStepMeta;
import org.pentaho.di.trans.step.StepDataInterface;
import org.pentaho.di.trans.step.StepInterface;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;


public class ChangeFileEncodingMeta extends BaseStepMeta implements StepMetaInterface
{
	private static Class<?> PKG = ChangeFileEncoding.class; // for i18n purposes, needed by Translator2!!   $NON-NLS-1$

	private boolean addsourceresultfilenames;
	private boolean addtargetresultfilenames;
	
    /** dynamic filename */
    private String       filenamefield;
    
    private String targetfilenamefield;
    private String targetencoding;
    private String sourceencoding;
	private boolean createparentfolder;
	
    
    public ChangeFileEncodingMeta()
    {
        super(); // allocate BaseStepMeta
    }

    /**
     * @return Returns the filenamefield.
     */
    public String getDynamicFilenameField()
    {
        return filenamefield;
    }

    /**
     * @param filenamefield The filenamefield to set.
     */
    public void setDynamicFilenameField(String filenamefield)
    {
        this.filenamefield = filenamefield;
    }
    
    /**
     * @return Returns the targetfilenamefield.
     */
    public String getTargetFilenameField()
    {
        return targetfilenamefield;
    }

    /**
     * @param targetfilenamefield The targetfilenamefield to set.
     */
    public void setTargetFilenameField(String targetfilenamefield)
    {
        this.targetfilenamefield = targetfilenamefield;
    }
    
    
    /**
     * @return Returns the sourceencoding.
     */
    public String getSourceEncoding()
    {
        return sourceencoding;
    }

    /**
     * @param encoding The sourceencoding to set.
     */
    public void setSourceEncoding(String encoding)
    {
        this.sourceencoding = encoding;
    }
    /**
     * @return Returns the targetencoding.
     */
    public String getTargetEncoding()
    {
        return targetencoding;
    }

    /**
     * @param encoding The targetencoding to set.
     */
    public void setTargetEncoding(String encoding)
    {
        this.targetencoding = encoding;
    }
    public boolean addSourceResultFilenames()
    {
    	return addsourceresultfilenames;
    }
    
    public void setaddSourceResultFilenames(boolean addresultfilenames)
    {
    	this.addsourceresultfilenames=addresultfilenames;
    }
    public boolean addTargetResultFilenames()
    {
    	return addtargetresultfilenames;
    }
    
    public void setaddTargetResultFilenames(boolean addresultfilenames)
    {
    	this.addtargetresultfilenames=addresultfilenames;
    }
    
    public boolean isCreateParentFolder()
    {
    	return createparentfolder;
    }
    
    public void setCreateParentFolder(boolean createparentfolder)
    {
    	this.createparentfolder=createparentfolder;
    }
	public void loadXML(Node stepnode, List<DatabaseMeta> databases, Map<String, Counter> counters)
		throws KettleXMLException
	{
		readData(stepnode, databases);
	}
 
	@Override
    public void loadPage(Map<String, List<String>> parameterHolder)
    	throws KettlePageException{
    	// TODO
    }

    public Object clone()
    {
        ChangeFileEncodingMeta retval = (ChangeFileEncodingMeta) super.clone();
       
        return retval;
    }

    public void setDefault()
    {
        addsourceresultfilenames=false;
        addtargetresultfilenames=false;
        targetfilenamefield=null;
        sourceencoding=	System.getProperty("file.encoding");;
        targetencoding=null;
        createparentfolder=false;
    }


    public String getXML()
    {
        StringBuffer retval = new StringBuffer();

        retval.append("    " + XMLHandler.addTagValue("filenamefield", filenamefield)); //$NON-NLS-1$ //$NON-NLS-2$
        retval.append("    " + XMLHandler.addTagValue("targetfilenamefield", targetfilenamefield)); 
        retval.append("    " + XMLHandler.addTagValue("sourceencoding", sourceencoding)); 
        retval.append("    " + XMLHandler.addTagValue("targetencoding", targetencoding)); 
        retval.append("    ").append(XMLHandler.addTagValue("addsourceresultfilenames",       addsourceresultfilenames));
        retval.append("    ").append(XMLHandler.addTagValue("addtargetresultfilenames",       addtargetresultfilenames));
        retval.append("    ").append(XMLHandler.addTagValue("createparentfolder",       createparentfolder));
        
        return retval.toString();
    }

    private void readData(Node stepnode, List<? extends SharedObjectInterface> databases)
	throws KettleXMLException
	{
    	try
    	{
            filenamefield = XMLHandler.getTagValue(stepnode, "filenamefield"); //$NON-NLS-1$
            targetfilenamefield = XMLHandler.getTagValue(stepnode, "targetfilenamefield"); 
            sourceencoding = XMLHandler.getTagValue(stepnode, "sourceencoding"); 
            targetencoding = XMLHandler.getTagValue(stepnode, "targetencoding"); 
            addsourceresultfilenames = "Y".equalsIgnoreCase(XMLHandler.getTagValue(stepnode, "addsourceresultfilenames"));  
            addtargetresultfilenames = "Y".equalsIgnoreCase(XMLHandler.getTagValue(stepnode, "addtargetresultfilenames")); 
            createparentfolder  = "Y".equalsIgnoreCase(XMLHandler.getTagValue(stepnode, "createparentfolder"));  
            
        }
        catch (Exception e)
        {
            throw new KettleXMLException(BaseMessages.getString(PKG, "ChangeFileEncodingMeta.Exception.UnableToReadStepInfo"), e); //$NON-NLS-1$
        }
    }

    public void readRep(Repository rep, ObjectId id_step, List<DatabaseMeta> databases, Map<String, Counter> counters)
	throws KettleException
	{
    	try
		{
            filenamefield = rep.getStepAttributeString(id_step, "filenamefield"); //$NON-NLS-1$
            targetfilenamefield = rep.getStepAttributeString(id_step, "targetfilenamefield"); 
            sourceencoding = rep.getStepAttributeString(id_step, "sourceencoding"); 
            targetencoding = rep.getStepAttributeString(id_step, "targetencoding"); 
            
            addsourceresultfilenames  = rep.getStepAttributeBoolean(id_step, "addsourceresultfilenames"); 
            addtargetresultfilenames  = rep.getStepAttributeBoolean(id_step, "addtargetresultfilenames"); 
            createparentfolder  = rep.getStepAttributeBoolean(id_step, "createparentfolder"); 
            
        }
        catch (Exception e)
        {
            throw new KettleException(BaseMessages.getString(PKG, "ChangeFileEncodingMeta.Exception.UnexpectedErrorReadingStepInfo"), e); //$NON-NLS-1$
        }
    }

    public void saveRep(Repository rep, ObjectId id_transformation, ObjectId id_step) throws KettleException
    {
        try
        {
            rep.saveStepAttribute(id_transformation, id_step, "filenamefield", filenamefield); //$NON-NLS-1$
            rep.saveStepAttribute(id_transformation, id_step, "targetfilenamefield", targetfilenamefield);
            rep.saveStepAttribute(id_transformation, id_step, "sourceencoding", sourceencoding);
            rep.saveStepAttribute(id_transformation, id_step, "targetencoding", targetencoding);
            
			rep.saveStepAttribute(id_transformation, id_step, "addsourceresultfilenames",          addsourceresultfilenames);
			rep.saveStepAttribute(id_transformation, id_step, "addtargetresultfilenames",          addtargetresultfilenames);
			rep.saveStepAttribute(id_transformation, id_step, "createparentfolder",          createparentfolder);
			
        }
        catch (Exception e)
        {
            throw new KettleException(BaseMessages.getString(PKG, "ChangeFileEncodingMeta.Exception.UnableToSaveStepInfo") + id_step, e); //$NON-NLS-1$
        }
    }

	public void check(List<CheckResultInterface> remarks, TransMeta transMeta, StepMeta stepMeta, RowMetaInterface prev, String input[], String output[], RowMetaInterface info)
	{
        CheckResult cr;
        String error_message = ""; //$NON-NLS-1$


        if (Const.isEmpty(filenamefield))
        {
            error_message = BaseMessages.getString(PKG, "ChangeFileEncodingMeta.CheckResult.FileFieldMissing"); //$NON-NLS-1$
            cr = new CheckResult(CheckResult.TYPE_RESULT_ERROR, error_message, stepMeta);
            remarks.add(cr);
        }
        else
        {
            error_message = BaseMessages.getString(PKG, "ChangeFileEncodingMeta.CheckResult.FileFieldOK"); //$NON-NLS-1$
            cr = new CheckResult(CheckResult.TYPE_RESULT_OK, error_message, stepMeta);
            remarks.add(cr);
        }
        if (Const.isEmpty(targetfilenamefield))
        {
            error_message = BaseMessages.getString(PKG, "ChangeFileEncodingMeta.CheckResult.TargetFileFieldMissing"); //$NON-NLS-1$
            cr = new CheckResult(CheckResult.TYPE_RESULT_ERROR, error_message, stepMeta);
            remarks.add(cr);
        }
        else
        {
            error_message = BaseMessages.getString(PKG, "ChangeFileEncodingMeta.CheckResult.TargetFileFieldOK"); //$NON-NLS-1$
            cr = new CheckResult(CheckResult.TYPE_RESULT_OK, error_message, stepMeta);
            remarks.add(cr);
        }
        String realSourceEncoding=transMeta.environmentSubstitute(getSourceEncoding());
        if (Const.isEmpty(realSourceEncoding))
        {
            error_message = BaseMessages.getString(PKG, "ChangeFileEncodingMeta.CheckResult.SourceEncodingMissing"); //$NON-NLS-1$
            cr = new CheckResult(CheckResult.TYPE_RESULT_ERROR, error_message, stepMeta);
            remarks.add(cr);
        }
        else
        {
            error_message = BaseMessages.getString(PKG, "ChangeFileEncodingMeta.CheckResult.SourceEncodingOK"); //$NON-NLS-1$
            cr = new CheckResult(CheckResult.TYPE_RESULT_OK, error_message, stepMeta);
            remarks.add(cr);
        }
        String realTargetEncoding=transMeta.environmentSubstitute(getTargetEncoding());
        if (Const.isEmpty(realTargetEncoding))
        {
            error_message = BaseMessages.getString(PKG, "ChangeFileEncodingMeta.CheckResult.TargetEncodingMissing"); //$NON-NLS-1$
            cr = new CheckResult(CheckResult.TYPE_RESULT_ERROR, error_message, stepMeta);
            remarks.add(cr);
        }
        else
        {
            error_message = BaseMessages.getString(PKG, "ChangeFileEncodingMeta.CheckResult.TargetEncodingOK"); //$NON-NLS-1$
            cr = new CheckResult(CheckResult.TYPE_RESULT_OK, error_message, stepMeta);
            remarks.add(cr);
        }
        
        
        // See if we have input streams leading to this step!
        if (input.length > 0)
        {
            cr = new CheckResult(CheckResult.TYPE_RESULT_OK, BaseMessages.getString(PKG, "ChangeFileEncodingMeta.CheckResult.ReceivingInfoFromOtherSteps"), stepMeta); //$NON-NLS-1$
            remarks.add(cr);
        }
        else
        {
            cr = new CheckResult(CheckResult.TYPE_RESULT_ERROR, BaseMessages.getString(PKG, "ChangeFileEncodingMeta.CheckResult.NoInpuReceived"), stepMeta); //$NON-NLS-1$
            remarks.add(cr);
        }

    }

    public StepInterface getStep(StepMeta stepMeta, StepDataInterface stepDataInterface, int cnr, TransMeta transMeta, Trans trans)
    {
        return new ChangeFileEncoding(stepMeta, stepDataInterface, cnr, transMeta, trans);
    }

    public StepDataInterface getStepData()
    {
        return new ChangeFileEncodingData();
    }

    public boolean supportsErrorHandling()
    {
        return true;
    }

}
