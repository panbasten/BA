package uap.test.impl.bq.chart.repository;

import nc.vo.pub.format.exception.FormatException;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ufida.iufo.pub.tools.AppDebug;

import uap.impl.bq.chart.repository.PropertyRepository;
import uap.impl.bq.chart.repository.RepositoryFactory;
import uap.impl.bq.chart.repository.TypeRepository;
import uap.vo.bq.chart.define.PropertyDefine;
import uap.vo.bq.chart.define.PropertyGroupDefine;
import uap.vo.bq.chart.model.ChartModel;
import uap.vo.bq.chart.model.ChartModelFactory;

public class RepositoryFactoryTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {	
		//ChartModel md = ChartModelFactory.createChartModel("MSColumn2D");
		//String str = md.toXml();
		
		/*
		Document doc = DocumentHelper.createDocument();
		Element elm = doc.addElement("asd");
		//str = elm.asXML();
		Document doc2 = DocumentHelper.createDocument();
		doc2.add(elm);*/		
		
		//pr tests
		try {
			PropertyRepository pr = RepositoryFactory.getInstance().getPropertyRepository();
			String str = prToString(pr);
			
			TypeRepository tr = RepositoryFactory.getInstance().getTypeRepository();
			str = trToString(tr);
			
			int a = 32;
			
		} catch (FormatException e) {
			AppDebug.error(e);
		}
		
	}

	private static String prToString(PropertyRepository pr) {
		StringBuffer strBuf = new StringBuffer();
		PropertyGroupDefine[] defines = pr.getPropertyGroupDefines();
		for (int i = 0; i < defines.length; i++) {
			strBuf.append("grpCode:");
			strBuf.append(defines[i].getCode());
			strBuf.append("\n");
			strBuf.append("grpName:");
			strBuf.append(pr.getPropertyGroupDefine(defines[i].getCode())
					.getName());
			strBuf.append("\n");

			PropertyDefine[] props = defines[i].getProperties();
			for (int t = 0; t < props.length; t++) {

				strBuf.append("--code:");
				strBuf.append(props[t].getCode());
				strBuf.append("\n");
				strBuf.append("--name:");
				strBuf.append(props[t].getName());
			}

		}

		return strBuf.toString();
	}

	private static String trToString(TypeRepository pr) {
		StringBuffer strBuf = new StringBuffer();
		for (int i = 0; i < pr.getTypes().length; i++) {
			String code = pr.getTypes()[i].getCode();
			strBuf.append("TypeCode:");
			strBuf.append(code);
			strBuf.append("\n");
			strBuf.append("TypeName:");
			strBuf.append(pr.getType(code).getName());
			strBuf.append("\n");
			strBuf.append("editor:");
			for (int t = 0; t < pr.getTypes()[i]
					.getEditorDefines().length; t++) {

				strBuf.append(pr.getTypes()[i].getEditorDefines()[t]
						.getEditorName());
				strBuf.append(",");
			}

			strBuf.append("\n");
		}
		
		return strBuf.toString();
	}

}
