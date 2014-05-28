package uap.impl.bq.chart.repository;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import nc.vo.pub.format.exception.FormatException;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.Document;

import uap.impl.bq.chart.util.BackEndServerInfoUtil;
import uap.impl.bq.chart.util.DefineUtil;
import uap.vo.bq.chart.define.PropertyGroupDefine;


/**
 * 生成PropertyRepository 的工程类
 * @author hbyxl
 *
 */
public class PropertyRepositoryFactory {

	private static PropertyRepositoryFactory instance =  new PropertyRepositoryFactory();

	public static PropertyRepositoryFactory getInstance(){
		return instance;
	}

	public PropertyRepository getPropertyRepository() throws FormatException{
		String filePath = BackEndServerInfoUtil.getServerRootPath() + "/repository";
		filePath = filePath + "/propertyRepository.xml";

		List<PropertyGroupDefine> propertyList = new ArrayList<PropertyGroupDefine>();
		Map<String, PropertyGroupDefine> propertyMap = generatorPropertyGroupsMap(filePath, propertyList);
		return new PropertyRepository(propertyMap, propertyList);
	}

	/**
	 * 解析 web/repository/proertyRepository.xml 文件， 获得所有公共属性的Map， 用于初始化PropertyRepository对象
	 * @param 文件路径
	 * @return
	 * @throws FormatException
	 */
	private static Map<String, PropertyGroupDefine> generatorPropertyGroupsMap (String filePath, List<PropertyGroupDefine> propertyList) throws FormatException{

		try {
			Map<String, PropertyGroupDefine> propertyGroupMap = new HashMap<String, PropertyGroupDefine>();

			SAXReader reader = new SAXReader ();
			Document propertyGroupsDocument = reader.read(new File(filePath));
			if (null == propertyGroupsDocument){
				return null;
			}

			Element rootElement = propertyGroupsDocument.getRootElement();
			if (null == rootElement){
				return null;
			}

			List<Element> propertyGroups = rootElement.elements();
			for (Element elemGroup : propertyGroups){
				PropertyGroupDefine chtGroup = null;
				if (elemGroup.getName().equals("propertyGroupDefine")){
					chtGroup = DefineUtil.parserGroupDefine(elemGroup);
				}else if (elemGroup.getName().equals("styleGroupDefine")){
					chtGroup = DefineUtil.parserStyleGroupDefine(elemGroup);
				}else if (elemGroup.getName().equals("dataBindingGroupDefine")){
					chtGroup = DefineUtil.parserDataBindingGroupDefine(elemGroup);
				}

				if (null != chtGroup){
					propertyGroupMap.put(chtGroup.getCode(), chtGroup);
					propertyList.add(chtGroup);
				}
			}

			return propertyGroupMap;
		} catch (DocumentException e) {
			throw new FormatException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("0502004_0","00502004-0064")/*@res "XML文件\""*/ + filePath + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("0502004_0","00502004-0063")/*@res "\"格式不正确！"*/, e);
		}

	}

}