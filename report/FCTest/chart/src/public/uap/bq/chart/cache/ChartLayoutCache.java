package uap.bq.chart.cache;

import java.util.Hashtable;

import nc.bs.framework.common.NCLocator;
import nc.vo.ml.MultiLangContext;
import nc.vo.pub.format.exception.FormatException;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import uap.bq.chart.utils.ChartMultiLangUtils;
import uap.itf.bq.chart.ILayoutService;
import uap.vo.bq.chart.layout.DropListGroup;
import uap.vo.bq.chart.layout.ElementGroup;
import uap.vo.bq.chart.layout.Layout;
import uap.vo.bq.chart.layout.LayoutElement;
import uap.vo.bq.chart.layout.TabPage;

import com.ufida.iufo.pub.tools.AppDebug;

/**
 * 只允许客户端使用
* @Description: TODO guohch
* @author  guohch
* @date 2014年1月18日 下午1:04:06 
*
 */
public class ChartLayoutCache {

	public static ChartLayoutCache instance = new ChartLayoutCache();

	private Hashtable<String, Layout> chartLayoutCache = new Hashtable<String, Layout>();

	/**
	 * 缓存的语种编码
	 */
	private Integer cacheLang = MultiLangContext.getInstance().getCurrentLangSeq();

	private ChartLayoutCache() {

	}

	/**
	 * 
	* @Description: 获取单例
	 */
	public static ChartLayoutCache getInstance() {
		if (instance.cacheLang != MultiLangContext.getInstance().getCurrentLangSeq()) {
			instance.clearCache();
		}
		return instance;
	}

	/**
	 * 
	* @Description: 获取chartDefine
	 */
	public synchronized Layout getChartLayout(String chartDefineCode) {
		if (StringUtils.isEmpty(chartDefineCode)) {
			return null;
		}
		Layout layout = chartLayoutCache.get(chartDefineCode);
		if (layout == null) {
			ILayoutService layoutService = NCLocator.getInstance().lookup(ILayoutService.class);
			try {
				layout = layoutService.getLayout(chartDefineCode);
			} catch (FormatException e) {
				AppDebug.error(e);
				AppDebug.error("Get layout error!");
			}
			if (layout == null) {
				return null;
			}
			layout = processLayoutMultilang(layout);

			chartLayoutCache.put(chartDefineCode, layout);
		}
		return layout;
	}

	public synchronized void clearCache() {
		this.chartLayoutCache.clear();
	}

	private Layout processLayoutMultilang(Layout layout) {
		if (layout == null) {
			return null;
		}
		TabPage[] tabPages = layout.getTabPages();
		for (TabPage tabPage : tabPages) {
			//自身名称多语
			String multiLangNameId = tabPage.getName();
			if (StringUtils.isNotEmpty(multiLangNameId)) {
				String multiLangName = ChartMultiLangUtils.getResValue(multiLangNameId);
				tabPage.setName(multiLangName);
			}

			//包含的子属性的 多语
			LayoutElement[] children = tabPage.getChildren();
			processChildrens(children);
		}

		return layout;
	}

	/**
	 * 
	* @Description: 递归处理属性结构 layout
	 */
	private void processLayoutElementMultiLang(LayoutElement layoutElement) {
		if (layoutElement != null) {
			String elementMultiCode = layoutElement.getName();
			if (StringUtils.isNotEmpty(elementMultiCode)) {
				String multiName = ChartMultiLangUtils.getResValue(elementMultiCode);
				layoutElement.setName(multiName);
			}
			if (layoutElement instanceof DropListGroup) {
				LayoutElement[] childrens = ((DropListGroup) layoutElement).getChildren();
				processChildrens(childrens);

			} else if (layoutElement instanceof ElementGroup) {
				LayoutElement[] childrens = ((ElementGroup) layoutElement).getChildren();
				processChildrens(childrens);
			} else if(layoutElement instanceof TabPage){
				LayoutElement[] childrens = ((TabPage) layoutElement).getChildren();
				processChildrens(childrens);
			}
		}
	}

	/**
	 * 
	* @Description: 处理children
	 */
	private void processChildrens(LayoutElement[] childrens) {
		if (!ArrayUtils.isEmpty(childrens)) {
			for (LayoutElement children : childrens) {
				processLayoutElementMultiLang(children);
			}
		}
	}

}
