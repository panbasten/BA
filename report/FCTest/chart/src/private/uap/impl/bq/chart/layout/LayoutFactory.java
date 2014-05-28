package uap.impl.bq.chart.layout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import nc.vo.pub.format.exception.FormatException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.SAXReader;

import uap.impl.bq.chart.cache.GeneralCache;
import uap.impl.bq.chart.util.BackEndServerInfoUtil;
import uap.util.bq.chart.ChartUtil;
import uap.vo.bq.chart.layout.BR;
import uap.vo.bq.chart.layout.DropListGroup;
import uap.vo.bq.chart.layout.Element;
import uap.vo.bq.chart.layout.ElementGroup;
import uap.vo.bq.chart.layout.Layout;
import uap.vo.bq.chart.layout.LayoutElement;
import uap.vo.bq.chart.layout.Line;
import uap.vo.bq.chart.layout.TabPage;
import uap.vo.bq.chart.layout.TabPage.TabPageInfo;

/**
 * 解析Layout xml文件，返回Layout对象
 * @author zhanglld
 *
 */
public class LayoutFactory {
	private static final String ELEMENT_TABPAGE = "tabPage";
	private static final String ELEMENT_ELEMENTGROUP = "elementGroup";
	private static final String ELEMENT_DROPLISTGROUP = "dropListGroup";

	private static final String ELEMENT_BR = "br";
	private static final String ELEMENT_ELEMENT = "element";
	private static final String ELEMENT_LINE = "line";

	private static final String TABPAGE_FIELD_CODE = "code";
	private static final String TABPAGE_FIELD_NAME = "name";
	private static final String TABPAGE_FIELD_TABALIGN = "tabAlign";

	private static final String DROPLIST_FIELD_NAME = "name";
	private static final String DROPLIST_FIELD_TYPE = "type";
	private static final String DROPLIST_FIELD_REFPROPERTYGROUP = "refPropertyGroup";

	private static final String ELEMENTGROUP_FIELD_REFPROPERTYGROUP = "refPropertyGroup";
	private static final String ELEMENTGROUP_FIELD_NAME = "name";
	private static final String ELEMENTGROUP_FIELD_ALIGN = "align";
	private static final String ELEMENTGROUP_FIELD_BORDER = "border";
	private static final String ELEMENTGROUP_FIELD_BORDERCOLOR = "borderColor";

	private static final String LINE_FIELD_VALUE = "value";

	private static final String ELEMENT_FIELD_REFPROPERTYGROUP = "refPropertyGroup";
	private static final String ELEMENT_FIELD_REFPROPERTY = "refProperty";
	private static final String ELEMENT_FIELD_NAME = "name";
	private static final String ELEMENT_FIELD_ISCONTROLLER = "isController";
	private static final String ELEMENT_FIELD_MULCONTROLLER = "mulController";

	private static final LayoutFactory instance = new LayoutFactory();

	public static LayoutFactory getInstance(){
		return instance;
	}

	/**
	 * 获得布局
	 * 通常用于测试
	 * @param defineFileXML 定义文件XML内容
	 * @param chartDefineCode 统计图定义编码
	 * @return
	 * @throws FormatException
	 */
	public Layout getLayout(String defineFileXML, String chartDefineCode) throws FormatException{
		Layout layout = (Layout) GeneralCache.getInstance(this.getClass().getName()).get(chartDefineCode);
		if(layout != null){
			return layout;
		}

		Document xmlDoc;
		try {
			xmlDoc = DocumentHelper.parseText(defineFileXML);
		} catch (DocumentException e) {
			throw new FormatException(defineFileXML + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("0502004_0","00502004-0063")/*@res "\"格式不正确！"*/, e);
		}

		return getLayout(xmlDoc, chartDefineCode);
	}

	/**
	 * 获得布局
	 * @param xmlFile
	 * @param chartDefineCode
	 * @return
	 * @throws FormatException
	 */
	private Layout getLayout(File xmlFile, String chartDefineCode) throws FormatException{
		Layout layout = (Layout) GeneralCache.getInstance(this.getClass().getName()).get(chartDefineCode);
		if(layout != null){
			return layout;
		}

		SAXReader reader = new SAXReader();

		Document xmlDoc;
		try {
			xmlDoc = reader.read(xmlFile);
		} catch (DocumentException e) {
			throw new FormatException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("0502004_0","00502004-0063"), e);
		}

		return getLayout(xmlDoc, chartDefineCode);
	}

	/**
	 * 获得布局
	 * @param doc
	 * @param chartDefineCode
	 * @return
	 * @throws FormatException
	 */
	private Layout getLayout(Document doc, String chartDefineCode) throws FormatException{
		org.dom4j.Element root = doc.getRootElement();
		ArrayList<TabPage> tabPageList = new ArrayList<TabPage>();
		List<org.dom4j.Element> elements = root.elements();
		for(org.dom4j.Element xmlElement : elements){
			tabPageList.add(buildTabPage(xmlElement));
		}

		Layout layout = new Layout(tabPageList.toArray(new TabPage[tabPageList.size()]));

		GeneralCache.getInstance(this.getClass().getName()).put(chartDefineCode, layout);

		return layout;
	}

	/**
	 * 获得布局
	 * @param chartDefineCode 统计图定义编码
	 * @return
	 * @throws FormatException
	 */
	public Layout getLayout(String chartDefineCode) throws FormatException{
		String defineFile = BackEndServerInfoUtil.getServerRootPath() + "/chartDefines/" + chartDefineCode + "/chartLayout.xml";

		return getLayout(new File(defineFile), chartDefineCode);
	}

	/**
	 * 构造TagPage
	 * @param xmlElement
	 * @return
	 */
	private TabPage buildTabPage(org.dom4j.Element xmlElement){
		List<LayoutElement> layoutElementList = new ArrayList<LayoutElement>();

		List<org.dom4j.Element> elements = xmlElement.elements();
		for(org.dom4j.Element child : elements){
			if(child.getName().equals(ELEMENT_TABPAGE)){
				layoutElementList.add(buildTabPage(child));
			} else if(child.getName().equals(ELEMENT_ELEMENTGROUP)){
				layoutElementList.add(buildElementGroup(child));
			} else if(child.getName().equals(ELEMENT_DROPLISTGROUP)){
				layoutElementList.add(buildDropListGroup(child));
			} else if(child.getName().equals(ELEMENT_BR)){
				layoutElementList.add(buildBR(child));
			} else if(child.getName().equals(ELEMENT_LINE)){
				layoutElementList.add(buildLine(child));
			}
		}

		TabPageInfo info = new TabPageInfo();
		info.code = xmlElement.attributeValue(TABPAGE_FIELD_CODE);
		info.name = ChartUtil.FromLangRes(xmlElement, TABPAGE_FIELD_NAME);// xmlElement.attributeValue(TABPAGE_FIELD_NAME);
		info.refPropertyGroup = xmlElement.attributeValue(ELEMENTGROUP_FIELD_REFPROPERTYGROUP);
		info.tabAlign = TabPage.TabAlign.genTabAlign((xmlElement.attributeValue(TABPAGE_FIELD_TABALIGN)));
		info.children = layoutElementList.toArray(new LayoutElement[layoutElementList.size()]);

		return new TabPage(info);
	}

	/**
	 * 构造DropListGroup
	 * @param xmlElement
	 * @return
	 */
	private DropListGroup buildDropListGroup(org.dom4j.Element xmlElement){
		List<LayoutElement> layoutElementList = new ArrayList<LayoutElement>();

		List<org.dom4j.Element> elements = xmlElement.elements();
		for(org.dom4j.Element child : elements){
			if(child.getName().equals(ELEMENT_ELEMENTGROUP)){
				layoutElementList.add(buildElementGroup(child));
			} else if(child.getName().equals(ELEMENT_DROPLISTGROUP)){
				layoutElementList.add(buildDropListGroup(child));
			} else if(child.getName().equals(ELEMENT_BR)){
				layoutElementList.add(buildBR(child));
			} else if(child.getName().equals(ELEMENT_LINE)){
				layoutElementList.add(buildLine(child));
				// TODO: modifyby wangqzh. add tabpage and element decode.
			}else if(child.getName().equals(ELEMENT_TABPAGE)){
				layoutElementList.add(buildTabPage(child));
			}else if(child.getName().equals(ELEMENT_ELEMENT)){
				layoutElementList.add(buildElement(child));
			}
		}

		return new DropListGroup(ChartUtil.FromLangRes(xmlElement, DROPLIST_FIELD_NAME)/*xmlElement.attributeValue(DROPLIST_FIELD_NAME)*/
				, xmlElement.attributeValue(DROPLIST_FIELD_TYPE)
				, xmlElement.attributeValue(DROPLIST_FIELD_REFPROPERTYGROUP)
				, layoutElementList.toArray(new LayoutElement[layoutElementList.size()]));
	}

	/**
	 * 构造ElementGroup
	 * @param xmlElement
	 * @return
	 */
	private ElementGroup buildElementGroup(org.dom4j.Element xmlElement){
		List<LayoutElement> layoutElementList = new ArrayList<LayoutElement>();

		List<org.dom4j.Element> elements = xmlElement.elements();
		for(org.dom4j.Element child : elements){
			if(child.getName().equals(ELEMENT_ELEMENTGROUP)){
				layoutElementList.add(buildElementGroup(child));
			} else if(child.getName().equals(ELEMENT_DROPLISTGROUP)){
				layoutElementList.add(buildDropListGroup(child));
			} else if(child.getName().equals(ELEMENT_BR)){
				layoutElementList.add(buildBR(child));
			} else if(child.getName().equals(ELEMENT_LINE)){
				layoutElementList.add(buildLine(child));
				// TODO: modifyby wangqzh. add tabpage and element decode.
			}else if(child.getName().equals(ELEMENT_TABPAGE)){
				layoutElementList.add(buildTabPage(child));
			}else if(child.getName().equals(ELEMENT_ELEMENT)){
				layoutElementList.add(buildElement(child));
			}
		}

		ElementGroup.ElementGroupInfo info = new ElementGroup.ElementGroupInfo();
		info.align = ElementGroup.Align.valueOf(xmlElement.attributeValue(ELEMENTGROUP_FIELD_ALIGN));
		info.border = Integer.valueOf(xmlElement.attributeValue(ELEMENTGROUP_FIELD_BORDER));
		info.borderColor = xmlElement.attributeValue(ELEMENTGROUP_FIELD_BORDERCOLOR);
		info.children = layoutElementList.toArray(new LayoutElement[layoutElementList.size()]);
		info.name = ChartUtil.FromLangRes(xmlElement, ELEMENTGROUP_FIELD_NAME);// xmlElement.attributeValue(ELEMENTGROUP_FIELD_NAME);
		info.refPropertyGroup = xmlElement.attributeValue(ELEMENTGROUP_FIELD_REFPROPERTYGROUP);

		return new ElementGroup(info);
	}

	/**
	 * 构造Line
	 * @param xmlElement
	 * @return
	 */
	private Line buildLine(org.dom4j.Element xmlElement){
		String value = xmlElement.attributeValue(LINE_FIELD_VALUE);
		if(value == null){
			value = Line.LineType.DOT.getValue();
		}

		return new Line(Line.LineType.genLineType(value));
	}

	private BR buildBR(org.dom4j.Element xmlElement){
		return BR.instance;
	}

	private Element buildElement(org.dom4j.Element xmlElement){
		Element.ElementInfo info = new Element.ElementInfo();
		info.refPropertyCode = xmlElement.attributeValue(ELEMENT_FIELD_REFPROPERTY);
		info.refPropertyGroupCode = xmlElement.attributeValue(ELEMENT_FIELD_REFPROPERTYGROUP);
		info.name = ChartUtil.FromLangRes(xmlElement,ELEMENT_FIELD_NAME); //xmlElement.attributeValue(ELEMENT_FIELD_NAME);
		String isController = xmlElement.attributeValue(ELEMENT_FIELD_ISCONTROLLER);
		String mulController = xmlElement.attributeValue(ELEMENT_FIELD_MULCONTROLLER);
		if(isController != null){
			isController = isController.toLowerCase();
			if(ChartUtil.isTrue(isController, false)){
				info.isController = true;
			}
		}
		
		if(mulController != null){
			//mulController = mulController.toLowerCase();
			info.mulController = mulController;
		}

		return new Element(info);
	}

}