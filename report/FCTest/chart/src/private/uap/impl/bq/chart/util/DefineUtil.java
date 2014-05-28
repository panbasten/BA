package uap.impl.bq.chart.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nc.vo.pub.BusinessRuntimeException;
import nc.vo.pub.format.exception.FormatException;

import org.dom4j.Attribute;
import org.dom4j.Element;

import uap.impl.bq.chart.repository.RepositoryFactory;
import uap.util.bq.chart.ChartUtil;
import uap.vo.bq.chart.define.ConstraintTerm;
import uap.vo.bq.chart.define.DataBindingGroupDefine;
import uap.vo.bq.chart.define.DataBindingGroupDefine.DataBindingGroupDefineInfo;
import uap.vo.bq.chart.define.PropertyDefine;
import uap.vo.bq.chart.define.PropertyDefine.PropertyDefineInfo;
import uap.vo.bq.chart.define.PropertyGroupDefine;
import uap.vo.bq.chart.define.PropertyGroupDefine.PropertyGroupDefineInfo;
import uap.vo.bq.chart.define.StyleApplyObject;
import uap.vo.bq.chart.define.StyleGroupDefine;
import uap.vo.bq.chart.define.StyleGroupDefine.StyleGroupDefineInfo;

public class DefineUtil {
	//
	/**
	 * 获取指定节点的指定属性值
	 *
	 * @param elem
	 * @param attri
	 * @return String 值对象
	 */
	public static String getAttributeValue(Element elem, String attri) {
		if (elem == null)
			return null;
		Attribute objAttri = elem.attribute(attri);

		if (objAttri == null)
			return null;

		return objAttri.getText();
	}

	/**
	 * 获取指定节点的指定属性值, 判断其是否为true， 返回 true false
	 *
	 * @param elem
	 * @param attri
	 * @return String 值对象
	 */
	public static boolean getAttributeValue_boolean(Element elem, String attri) {
		if (elem == null)
			return false;
		Attribute objAttri = elem.attribute(attri);

		if (objAttri == null)
			return false;

		return objAttri.getText().equals("true");
	}

	/**
	 * 判断指定属性是否为隐藏属性
	 *
	 * @param objDictionaty
	 * @param code
	 * @return true,表示隐藏；反之，显示
	 */
	public static boolean isHidePropertyDefine(
			Map<String, PropertyDefineInfo> objDictionaty, String code) {
		PropertyDefineInfo objInfo = objDictionaty.get(code);
		if (objInfo == null) {
			return false;
		} else if (objInfo.isHide == null) {
			return false;
		} else if (objInfo.isHide.equals("true")) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 构建 PropertyGroupDefine 结构
	 *
	 * @param elemGroupDefine
	 * @return PropertyGroupDefine对象
	 */
	public static PropertyGroupDefine parserGroupDefine(Element elemGroupDefine) {
		if (elemGroupDefine == null) {
			return null;
		}
		PropertyGroupDefineInfo objPropertyGroupDefineInfo = parserGroupDefineInfo(elemGroupDefine);
		return (null == objPropertyGroupDefineInfo) ? null
				: new PropertyGroupDefine(objPropertyGroupDefineInfo);
	}

	/**
	 * 构建 PropertyGroupDefineInfo对象结构
	 *
	 * @param elemGroupDefine
	 * @return PropertyGroupDefineInfo 对象
	 */
	public static PropertyGroupDefineInfo parserGroupDefineInfo(
			Element elemGroupDefine) {
		try {
			PropertyGroupDefineInfo objPropertyGroupDefineInfo = new PropertyGroupDefineInfo();
			objPropertyGroupDefineInfo.code = DefineUtil.getAttributeValue(
					elemGroupDefine, "code");
			
			objPropertyGroupDefineInfo.name = ChartUtil.FromLangRes(elemGroupDefine, "name"); 
//			objPropertyGroupDefineInfo.name = DefineUtil.getAttributeValue(elemGroupDefine, "name");
			objPropertyGroupDefineInfo.relativeChange = DefineUtil
					.getAttributeValue_boolean(elemGroupDefine,
							"relativeChange");
			objPropertyGroupDefineInfo.allowRepeat = DefineUtil
					.getAttributeValue_boolean(elemGroupDefine, "repeatable");

			String groupType = DefineUtil.getAttributeValue(elemGroupDefine,
					"type");
			if (groupType != null) {
				objPropertyGroupDefineInfo.type = RepositoryFactory
						.getInstance().getTypeRepository().getType(groupType);
				String editorType = DefineUtil.getAttributeValue(
						elemGroupDefine, "editor");
				objPropertyGroupDefineInfo.editorDefine = RepositoryFactory
						.getInstance().getTypeRepository()
						.getEditorDefine(groupType, editorType);
			}

			// Destruct The PropertyGroupDefine XML .
			ArrayList<PropertyDefine> objPropertyDefineList = new ArrayList<PropertyDefine>();
			List<Element> propertyList = elemGroupDefine.elements();
			for (Element elemItem : propertyList) {
				PropertyDefine objPropertyDefine = parserPropertyDefine(elemItem);
				if (objPropertyDefine != null) {
					objPropertyDefineList.add(objPropertyDefine);
				}
			}

			if (!objPropertyDefineList.isEmpty()) {
				objPropertyGroupDefineInfo.properties = (PropertyDefine[]) objPropertyDefineList
						.toArray(new PropertyDefine[objPropertyDefineList
								.size()]);
			}

			return objPropertyGroupDefineInfo;
		} catch (FormatException e) {
			throw new BusinessRuntimeException(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("0502004_0","00502004-0063"), e);
		}
	}

	/**
	 * 解析 propertyDefine节点,构建 propertyDefine 内存结构
	 *
	 * @param elemPropertyDefine
	 * @return PropertyDefine对象
	 */
	public static PropertyDefine parserPropertyDefine(Element elemPropertyDefine) {
		if (elemPropertyDefine == null) {
			return null;
		}
		return new PropertyDefine(parserPropertyDefineInfo(elemPropertyDefine));
	}

	/**
	 * 构建PropertyDefineInfo对象内存结构
	 *
	 * @param elemPropertyDefine
	 * @return PropertyDefineInfo对象
	 */
	public static PropertyDefineInfo parserPropertyDefineInfo(
			Element elemPropertyDefine) {
		try {
			// Get Some Attributes Of Element .
			PropertyDefine.PropertyDefineInfo objPropertyDefineInfo = new PropertyDefine.PropertyDefineInfo();
			objPropertyDefineInfo.code = DefineUtil.getAttributeValue(
					elemPropertyDefine, "code");
			objPropertyDefineInfo.name = ChartUtil.FromLangRes(elemPropertyDefine, "name"); //DefineUtil.getAttributeValue(elemPropertyDefine, "name");
			String propertyType = ChartUtil.FromLangRes(elemPropertyDefine, "type");
			objPropertyDefineInfo.type = RepositoryFactory.getInstance()
					.getTypeRepository().getType(propertyType);
			String editorType = DefineUtil.getAttributeValue(
					elemPropertyDefine, "editor");
			objPropertyDefineInfo.editorDefine = RepositoryFactory
					.getInstance().getTypeRepository()
					.getEditorDefine(propertyType, editorType);
			objPropertyDefineInfo.description = ChartUtil.FromLangRes(
					elemPropertyDefine, "description");
			objPropertyDefineInfo.range = DefineUtil.getAttributeValue(
					elemPropertyDefine, "range");
			objPropertyDefineInfo.isNLS = DefineUtil.getAttributeValue(
					elemPropertyDefine, "isNLS");
			String generate = DefineUtil.getAttributeValue(elemPropertyDefine,
					"generate");
			objPropertyDefineInfo.generate = (generate == null
					|| generate.isEmpty() || generate.equals("true") || generate
					.equals("1")) ? true : false;
			objPropertyDefineInfo.relativeChange = DefineUtil
					.getAttributeValue_boolean(elemPropertyDefine,
							"relativeChange");
			String hideType = DefineUtil.getAttributeValue(elemPropertyDefine,
					"hide");
			objPropertyDefineInfo.isHide = (hideType == null || hideType
					.isEmpty()) ? "false" : hideType;
			List<ConstraintTerm> constraintTermArrayList = parserConstraintTermDefine(elemPropertyDefine);
			if (constraintTermArrayList != null) {
				objPropertyDefineInfo.constraintTerms = (ConstraintTerm[]) constraintTermArrayList
						.toArray(new ConstraintTerm[constraintTermArrayList
								.size()]);
			}
			return objPropertyDefineInfo;
		} catch (FormatException e) {
			throw new BusinessRuntimeException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("0502004_0","00502004-0063"), e);
		}
	}

	/**
	 * 解析 ConstraintTerm 节点,生成ConstraintTerm列表对象
	 *
	 * @param elemItem
	 * @return List<ConstraintTerm> 列表对象
	 */
	public static List<ConstraintTerm> parserConstraintTermDefine(
			Element elemItem) {
		List<ConstraintTerm> constraintTermArrayList = new ArrayList<ConstraintTerm>();
		List<Element> elemList = elemItem.elements();
		for (Element elemConstraintTerm : elemList) {
			constraintTermArrayList.add(new ConstraintTerm(ChartUtil.FromLangRes(elemConstraintTerm, "value"), ChartUtil.FromLangRes(elemConstraintTerm, "name")/*DefineUtil.getAttributeValue(elemConstraintTerm, "name")*/));
		}
		return constraintTermArrayList.size() == 0 ? null
				: constraintTermArrayList;
	}

	// <styleGroupDefine code="styleApply" name="样式应用" type="fonts">
	// <applyObject code="标题" name=""
	// includeGroups="FC_PYRAMID_AND_FUNNEL_FONT_PROPERTIES"></applyObject>
	// <applyObject code="副标题" name=""
	// includeGroups="FC_PYRAMID_AND_FUNNEL_FONT_PROPERTIES"></applyObject>
	// <applyObject code="数据标签" name=""
	// includeGroups="FC_PYRAMID_AND_FUNNEL_FONT_PROPERTIES"></applyObject>
	// <applyObject code="数据标签" name=""
	// includeGroups="FC_PYRAMID_AND_FUNNEL_FONT_PROPERTIES"></applyObject>
	// <applyObject code="数据标签" name=""
	// includeGroups="FC_PYRAMID_AND_FUNNEL_FONT_PROPERTIES"></applyObject>
	// </styleGroupDefine>
	/**
	 * 解析 styleGroupDefine 节点,生成styleGroupDefine内存结构
	 *
	 * @param elemPropertyGroup
	 * @return styleGroupDefine对象
	 */
	public static StyleGroupDefine parserStyleGroupDefine(
			Element elemPropertyGroup) {
		try {
			StyleGroupDefineInfo styleGroupDefineInfo = new StyleGroupDefineInfo();
			styleGroupDefineInfo.code = DefineUtil.getAttributeValue(
					elemPropertyGroup, "code");
			styleGroupDefineInfo.name = ChartUtil.FromLangRes(elemPropertyGroup, "name"); // DefineUtil.getAttributeValue(elemPropertyGroup, "name");
			styleGroupDefineInfo.rawType = DefineUtil.getAttributeValue(
					elemPropertyGroup, "rawType");
			styleGroupDefineInfo.relativeChange = DefineUtil
					.getAttributeValue_boolean(elemPropertyGroup,
							"relativeChange");
			styleGroupDefineInfo.allowRepeat = DefineUtil
					.getAttributeValue_boolean(elemPropertyGroup, "repeatable");
			String type = ChartUtil.FromLangRes(elemPropertyGroup,"type"); //DefineUtil.getAttributeValue(elemPropertyGroup,"type");
			if (type != null) {
				styleGroupDefineInfo.type = RepositoryFactory.getInstance()
						.getTypeRepository().getType(type);
				String editorName = DefineUtil.getAttributeValue(
						elemPropertyGroup, "editorDefine");
				styleGroupDefineInfo.editorDefine = RepositoryFactory
						.getInstance().getTypeRepository()
						.getEditorDefine(type, editorName);
			}

			// TODO: modify type的定义需要确定
			// styleGroupDefineInfo.propertyType =
			// getAttributeValue(elemPropertyGroup, "type");
			List<StyleApplyObject> styleApplyObjectList = new ArrayList<StyleApplyObject>();
			List<Element> applyObjectList = elemPropertyGroup.elements();
			for (Element elemApplyObject : applyObjectList) {
				String code = DefineUtil.getAttributeValue(elemApplyObject,
						"code");
				String name = ChartUtil.FromLangRes(elemApplyObject,"name");// DefineUtil.getAttributeValue(elemApplyObject,"name");
				String includes = DefineUtil.getAttributeValue(elemApplyObject,
						"includeGroups");
				includes = includes.trim();
				styleApplyObjectList.add(new StyleApplyObject(code, name,
						includes.split(",")));
			}
			styleGroupDefineInfo.styleApplyObjects = (StyleApplyObject[]) styleApplyObjectList
					.toArray(new StyleApplyObject[styleApplyObjectList.size()]);
			return new StyleGroupDefine(styleGroupDefineInfo);

		} catch (FormatException e) {
			throw new BusinessRuntimeException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("0502004_0","00502004-0072")/*@res "合并公共库中propertyGroup组时候，出现错误*/, e);
		}

	}

	// <dataBindingGroupDefine code="bindingSeries" name="系列详细设置"
	// bindingGroup="series">
	// <propertyDefine code="color" name="颜色" type="color"
	// description="(color) 序列的颜色"/>
	// <propertyDefine code="alpha" name="透明度" type="integerEditMode"
	// range="0-100" description="(alpha) Alpha 颜色值, 取值范围 0-100"/>
	// <propertyDefine code="ratio" name="颜色渐变比例" type="integerEditMode"
	// range="0-100" description="(ratio) 颜色填充比率"/>
	// <propertyDefine code="showValues" name="显示标签" type="boolenEditMode"
	// description="(showValues) 设置是否显示数值"/>
	// <propertyDefine code="includeInLegend" name="显示图例" type="boolenEditMode"
	// description="(includeInLegend) 设置是否显示在图例中"/>
	// </dataBindingGroupDefine>
	/**
	 * 解析 dataBindingGroupDefine 节点，生成内存对象
	 *
	 * @param elemPropertyGroup
	 * @return DataBindingGroupDefine对象
	 */
	public static DataBindingGroupDefine parserDataBindingGroupDefine(
			Element elemPropertyGroup) {
		try {
			if (null == elemPropertyGroup) {
				return null;
			}
			DataBindingGroupDefine objDataBindingGroupDefine = null;
			PropertyGroupDefineInfo objPropertyGroupDefineInfo = parserGroupDefineInfo(elemPropertyGroup);
			if (null != objPropertyGroupDefineInfo) {
				objDataBindingGroupDefine = new DataBindingGroupDefine(
						new DataBindingGroupDefineInfo(
								objPropertyGroupDefineInfo,
								DefineUtil.getAttributeValue(elemPropertyGroup,
										"bindingGroup")));
			}
			return objDataBindingGroupDefine;
		} catch (Exception e) {
			throw new BusinessRuntimeException(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("0502004_0","00502004-0073")/*@res "解析 dataBindingGroupDefine 节点时候，出现错误*/, e);
		}
	}

//	public static String FromLangRes (Element elem, String name){
//		String nameid = DefineUtil.getAttributeValue(elem, name);
//		if (nameid !=null && nameid.startsWith("{") && nameid.endsWith("}")){
//			nameid = nameid.replace("{", "").replace("}", "");
//			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("0502004_0",nameid);
//		}
//		else{
//			return nameid;
//		}
//		
//	}

}