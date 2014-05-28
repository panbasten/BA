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
	 * ��ȡָ���ڵ��ָ������ֵ
	 *
	 * @param elem
	 * @param attri
	 * @return String ֵ����
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
	 * ��ȡָ���ڵ��ָ������ֵ, �ж����Ƿ�Ϊtrue�� ���� true false
	 *
	 * @param elem
	 * @param attri
	 * @return String ֵ����
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
	 * �ж�ָ�������Ƿ�Ϊ��������
	 *
	 * @param objDictionaty
	 * @param code
	 * @return true,��ʾ���أ���֮����ʾ
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
	 * ���� PropertyGroupDefine �ṹ
	 *
	 * @param elemGroupDefine
	 * @return PropertyGroupDefine����
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
	 * ���� PropertyGroupDefineInfo����ṹ
	 *
	 * @param elemGroupDefine
	 * @return PropertyGroupDefineInfo ����
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
	 * ���� propertyDefine�ڵ�,���� propertyDefine �ڴ�ṹ
	 *
	 * @param elemPropertyDefine
	 * @return PropertyDefine����
	 */
	public static PropertyDefine parserPropertyDefine(Element elemPropertyDefine) {
		if (elemPropertyDefine == null) {
			return null;
		}
		return new PropertyDefine(parserPropertyDefineInfo(elemPropertyDefine));
	}

	/**
	 * ����PropertyDefineInfo�����ڴ�ṹ
	 *
	 * @param elemPropertyDefine
	 * @return PropertyDefineInfo����
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
	 * ���� ConstraintTerm �ڵ�,����ConstraintTerm�б����
	 *
	 * @param elemItem
	 * @return List<ConstraintTerm> �б����
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

	// <styleGroupDefine code="styleApply" name="��ʽӦ��" type="fonts">
	// <applyObject code="����" name=""
	// includeGroups="FC_PYRAMID_AND_FUNNEL_FONT_PROPERTIES"></applyObject>
	// <applyObject code="������" name=""
	// includeGroups="FC_PYRAMID_AND_FUNNEL_FONT_PROPERTIES"></applyObject>
	// <applyObject code="���ݱ�ǩ" name=""
	// includeGroups="FC_PYRAMID_AND_FUNNEL_FONT_PROPERTIES"></applyObject>
	// <applyObject code="���ݱ�ǩ" name=""
	// includeGroups="FC_PYRAMID_AND_FUNNEL_FONT_PROPERTIES"></applyObject>
	// <applyObject code="���ݱ�ǩ" name=""
	// includeGroups="FC_PYRAMID_AND_FUNNEL_FONT_PROPERTIES"></applyObject>
	// </styleGroupDefine>
	/**
	 * ���� styleGroupDefine �ڵ�,����styleGroupDefine�ڴ�ṹ
	 *
	 * @param elemPropertyGroup
	 * @return styleGroupDefine����
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

			// TODO: modify type�Ķ�����Ҫȷ��
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
			throw new BusinessRuntimeException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("0502004_0","00502004-0072")/*@res "�ϲ���������propertyGroup��ʱ�򣬳��ִ���*/, e);
		}

	}

	// <dataBindingGroupDefine code="bindingSeries" name="ϵ����ϸ����"
	// bindingGroup="series">
	// <propertyDefine code="color" name="��ɫ" type="color"
	// description="(color) ���е���ɫ"/>
	// <propertyDefine code="alpha" name="͸����" type="integerEditMode"
	// range="0-100" description="(alpha) Alpha ��ɫֵ, ȡֵ��Χ 0-100"/>
	// <propertyDefine code="ratio" name="��ɫ�������" type="integerEditMode"
	// range="0-100" description="(ratio) ��ɫ������"/>
	// <propertyDefine code="showValues" name="��ʾ��ǩ" type="boolenEditMode"
	// description="(showValues) �����Ƿ���ʾ��ֵ"/>
	// <propertyDefine code="includeInLegend" name="��ʾͼ��" type="boolenEditMode"
	// description="(includeInLegend) �����Ƿ���ʾ��ͼ����"/>
	// </dataBindingGroupDefine>
	/**
	 * ���� dataBindingGroupDefine �ڵ㣬�����ڴ����
	 *
	 * @param elemPropertyGroup
	 * @return DataBindingGroupDefine����
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
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("0502004_0","00502004-0073")/*@res "���� dataBindingGroupDefine �ڵ�ʱ�򣬳��ִ���*/, e);
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