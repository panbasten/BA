package uap.impl.bq.chart.repository;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.vo.pub.format.exception.FormatException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import uap.impl.bq.chart.util.BackEndServerInfoUtil;
import uap.impl.bq.chart.util.DefineUtil;
import uap.vo.bq.chart.define.EditorDefine;
import uap.vo.bq.chart.define.Type;

/**
 * TypeRepository 的工厂类
 *
 * @author hbyxl
 *
 */
public class TypeRepositoryFactory {
	private static TypeRepositoryFactory instance = new TypeRepositoryFactory();

	public TypeRepository getTypeRepository() throws FormatException {
		Map<String, Type> typeMap = new HashMap<String, Type>();
		List<Type> typeList = new ArrayList<Type>();
		generatTypeMaps(typeMap, typeList);
		return new TypeRepository(typeMap, typeList);
	}

	public static TypeRepositoryFactory getInstance() {
		return instance;
	}

	/**
	 * 获得图表所有的类型
	 *
	 * @param typeMap
	 *            返回 key-value的map结果
	 * @throws FormatException
	 */
	private void generatTypeMaps(Map<String, Type> typeMap, List<Type> typeList)
			throws FormatException {

		String xmlPath = BackEndServerInfoUtil.getServerRootPath()
				+ "/repository" + "/typeRepository.xml";
		SAXReader reader = new SAXReader();

		try {
			Document typesDocument = reader.read(new File(xmlPath));
			Element rootElement = typesDocument.getRootElement();
			if (null == rootElement) {
				throw new FormatException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("0502004_0","00502004-0065")/*@res "\"Xml根节点为空！"*/);
			}
			parserTypeRepository(rootElement, typeMap, typeList);

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			throw new FormatException(xmlPath + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("0502004_0","00502004-0064"), e);
		}

	}

	/**
	 * 解析Types xml 节点
	 *
	 * @param elemTypes
	 *            Types 元素节点
	 * @param typeMap
	 * @return
	 * @throws FormatException
	 */
	private int parserTypeRepository(Element elemTypes,
			Map<String, Type> typeMap, List<Type> typeList)
			throws FormatException {
		try {
			List<Element> elemTypeList = elemTypes.elements();
			int typeCount = 0;
			for (Element elemType : elemTypeList) {
				if (elemType.getName().equals("Type")) {
					Type type = parserPropertyType(elemType);
					if (null != type) {
						typeMap.put(type.getCode(), type);
						typeList.add(type);
						typeCount++;
					}
				}
			}
			return typeCount;
		} catch (Exception e) {
			throw new FormatException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("0502004_0","00502004-0067")/*@res "解析 TypeRepository.xml 失败"*/, e);
		}
	}

	// 解析类型
	// <propertyType code="grpCode" name="grpName">
	// <editor name="grpColorEd_1">
	// <swEditor implClass="com.yonyou.bq.chart.editor.SWAxisEditor"/>
	// <lwEditor implClass="com.yonyou.bq.chart.editor.LWAxisEditor"/>
	// </editor>
	// <editor name="grpColorEd_2">
	// <swEditor implClass="com.yonyou.bq.chart.editor.SWAxisEditor"/>
	// <lwEditor implClass="com.yonyou.bq.chart.editor.LWAxisEditor"/>
	// </editor>
	// </propertyType>

	/**
	 * 解析Type 节点
	 *
	 * @param elemType
	 * @return Type结果值
	 */
	private Type parserPropertyType(Element elemType) throws FormatException {
		try {
			Type.PropertyTypeInfo typeInfo = new Type.PropertyTypeInfo();
			typeInfo.code = DefineUtil.getAttributeValue(elemType, "code");
			typeInfo.name = DefineUtil.getAttributeValue(elemType, "name");
			typeInfo.valueType = DefineUtil.getAttributeValue(elemType,
					"valueType");
			typeInfo.enumValue = DefineUtil.getAttributeValue(elemType,
					"enumValue");

			List<EditorDefine> editorList = new ArrayList<EditorDefine>();
			List<Element> elemEditorList = elemType.elements();
			if (elemEditorList != null) {
				for (Element elemEditor : elemEditorList) {
					String editorName = DefineUtil.getAttributeValue(
							elemEditor, "name");
					String editorSW = "", editorLW = "";
					List<Element> ls = elemEditor.elements();
					for (Element elemItem : ls) {
						if (elemItem.getName().equals("swEditor")) {
							editorSW = DefineUtil.getAttributeValue(elemItem,
									"implClass");
						} else if (elemItem.getName().equals("lwEditor")) {
							editorLW = DefineUtil.getAttributeValue(elemItem,
									"implClass");
						}
					}
					editorList.add(new EditorDefine(editorName, editorSW,
							editorLW));
				}

				typeInfo.editorDefines = editorList
						.toArray(new EditorDefine[editorList.size()]);
			}

			return new Type(typeInfo);
		} catch (Exception e) {
			throw new FormatException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("0502004_0","00502004-0068")/*@res "解析PropertyType节点失败"*/, e);
		}

	}

}