package uap.impl.bq.chart.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.map.LinkedMap;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import uap.impl.bq.chart.util.BackEndServerInfoUtil;
import uap.impl.bq.chart.util.DefineUtil;
import uap.itf.bq.chart.IChartThemeService;
import uap.util.bq.chart.ChartUtil;
import uap.util.bq.chart.DefaultUtil;
import uap.vo.bq.chart.define.ChartTheme;
import uap.vo.bq.chart.define.ChartTheme.ChartPaletteColor;
import uap.vo.bq.chart.define.ChartTheme.ChartStyle;
import uap.vo.bq.chart.model.PropertyGroup;

public class ChartThemeServiceImpl implements IChartThemeService {
	private boolean hasInitialized = false;
	private LinkedMap chartThemeMap = new LinkedMap();
	
	private LinkedMap chartColorMap = new LinkedMap();
	private LinkedMap chartStyleMap = new LinkedMap();
	private static ChartThemeServiceImpl instance = new ChartThemeServiceImpl();

	@Override
	public ChartStyle getChartStyle(String code) {
		if (!instance.hasInitialized) {
			instance.hasInitialized = true;
			instance.initialize();
		}
		
		if (instance.chartStyleMap.size() == 0){
			return null;
		}
		
		return (ChartStyle)instance.chartStyleMap.get(code);
	}

	@Override
	public ChartStyle[] getChartStyles(String[] codes) {
		if (!instance.hasInitialized) {
			instance.hasInitialized = true;
			instance.initialize();
		}
		/* 如果参数为空， 怎获取所有的主题数据 */
		if (codes == null || codes.length == 0){
			return (ChartStyle[]) instance.chartStyleMap.values().toArray(new ChartTheme[instance.chartStyleMap.size()]);
		}
		
		List<ChartStyle> tmpStyles = new ArrayList<ChartStyle>();
		for (String code : codes){
			ChartStyle theme = (ChartStyle) instance.chartStyleMap.get(code);
			if (null != theme){
				tmpStyles.add(theme);
			}
		}
		
		return tmpStyles.toArray(new ChartStyle[tmpStyles.size()]);
	}
	
	@Override
	public ChartPaletteColor getChartPaletteColor(String code) {
		if (!instance.hasInitialized) {
			instance.hasInitialized = true;
			instance.initialize();
		}
		if (null != code){
			return (ChartPaletteColor) instance.chartColorMap.get(code);
		}
		return null;
	}

	@Override
	public ChartPaletteColor[] getChartPaletteColors() {
		if (!instance.hasInitialized) {
			instance.hasInitialized = true;
			instance.initialize();
		}
		return (ChartPaletteColor[]) instance.chartColorMap.values().toArray(new ChartPaletteColor[instance.chartColorMap.size()]);
	}

	@Override
	public ChartTheme getChartTheme(String code) {
		if (!instance.hasInitialized) {
			instance.hasInitialized = true;
			instance.initialize();
		}
		if (null != code){
			return (ChartTheme) instance.chartThemeMap.get(code);
		}
		return null;
	}

	@Override
	public ChartTheme[] getChartThemes(String[] codes) {
		if (!instance.hasInitialized) {
			instance.hasInitialized = true;
			instance.initialize();
		}
		if (null == codes || codes.length == 0){
			return (ChartTheme[]) instance.chartThemeMap.values().toArray(new ChartTheme[instance.chartThemeMap.size()]);
		}
		return null;
	}

	@Override
	public boolean updateChartThemes() {
		instance.hasInitialized = true;
		return instance.initialize();
	}

	// ---------------------------------------------------
	private boolean initialize() {
		chartThemeMap.clear();
		
		String rootPath = BackEndServerInfoUtil.getServerRootPath();

		return parserChartTheme(rootPath + "/themes/");
	}

	private boolean parserChartTheme(String filePath) {
		try {
			File file = new File(filePath);
			if (!file.isDirectory()) {
				return false;
			}
			// Search the Special Directory.
			File[] fileList = file.listFiles();
			for (File xmlFile : fileList) {
				if (!xmlFile.isDirectory()) {
					SAXReader saxReader = new SAXReader();
					Document xmlDoc = saxReader.read(xmlFile);
					if (xmlDoc == null) {
						continue;
					}
					
					String fileName = xmlFile.getAbsolutePath();
					if (fileName.endsWith("chartTheme.xml")){
						parserChartThemes(xmlDoc.getRootElement());
					}
					else if (fileName.endsWith("chartStyle.xml")){
						parserChartStyles (xmlDoc.getRootElement());
					}

				} else {
					parserChartTheme(xmlFile.getAbsolutePath());
				}
			}
			return true;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private void parserChartThemes(Element elemChartThemes) {
		try {
			if (null == elemChartThemes) {
				return ;
			}
			// 
			for (Element elemChartTheme : elemChartThemes.elements()){
				ChartTheme chartTheme = parserChartTheme ( elemChartTheme);
				if (null != chartTheme){
					this.chartThemeMap.put(chartTheme.getCode(),chartTheme);
				}
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
	
	private void parserChartStyles(Element elemChartStyles) {
		try {
			if (null == elemChartStyles) {
				return ;
			}
			// 
			for (Element elemChartStyle : elemChartStyles.elements()){
				if (elemChartStyle.getName().equals("appearanceStyles")){
					for (Element elemStyle : elemChartStyle.elements()){
						ChartStyle chartStyle = parserChartStyle (elemStyle);
						if (null != chartStyle){
							this.chartStyleMap.put(chartStyle.getCode(),chartStyle);
						}
					}
				}
				else if(elemChartStyle.getName().equals("colorStyles")){
					for (Element elemColor : elemChartStyle.elements()){
						ChartPaletteColor color = parserChartColor (elemColor);
						if (null != color){
							this.chartColorMap.put(color.getCode(),color);
						}
					}
				}
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
	
	private ChartTheme parserChartTheme (Element elemChartTheme){
		try{
			ChartTheme chartTheme = new ChartTheme ();
			String code = DefineUtil.getAttributeValue(elemChartTheme, "code");
			String name = ChartUtil.FromLangRes(elemChartTheme, "name");
			String colorCode = ChartUtil.FromLangRes(elemChartTheme, "colorCode");
			String styleCode = ChartUtil.FromLangRes(elemChartTheme, "styleCode");
			chartTheme.setCode(code);
			chartTheme.setName(name);
			chartTheme.setColorCode(colorCode);
			chartTheme.setStyleCode(styleCode);
			
			return chartTheme;
		}
		catch (Exception e){
			throw new RuntimeException(e);
		}
	}
	
	private ChartStyle parserChartStyle (Element elemChartTheme){
		try{
			ChartStyle chartStyle = new ChartStyle ();
			String code = DefineUtil.getAttributeValue(elemChartTheme, "code");
			String name = ChartUtil.FromLangRes(elemChartTheme, "name");
			
			chartStyle.setCode(code);
			chartStyle.setName(name);
			
			
			for (Element elemItem : elemChartTheme.elements()){

				String tagName = elemItem.getName();
				if (tagName.endsWith("Group")) {
					PropertyGroup propertyGroup = null;
					if (tagName.equals("styleGroup")) {
						
						propertyGroup = DefaultUtil.parserStyleGroup(elemItem);
					} else if (tagName.equals("dataBindingRefGroup")) {
						
						propertyGroup = DefaultUtil.parserDataBindingRefGroup(elemItem);
					} else {
						
						propertyGroup = DefaultUtil.parserPropertyGroup(elemItem);
					}
					//
					if (null != propertyGroup) {
						chartStyle.addPropertyGroup(propertyGroup);
					}
				} 
				
			}
			
			return chartStyle;
		}
		catch (Exception e){
			throw new RuntimeException(e);
		}
	}

	private ChartPaletteColor parserChartColor (Element elemChartColor){
		try{
			String code = DefineUtil.getAttributeValue(elemChartColor, "code");
			String name = ChartUtil.FromLangRes(elemChartColor, "name");
			PropertyGroup propertyGroup = null;
			
			for (Element elemItem : elemChartColor.elements()){
				String tagName = elemItem.getName();
				if (tagName.endsWith("propertyGroup")) {
					
					propertyGroup = DefaultUtil.parserPropertyGroup(elemItem);
					break;
				} 
			}
			
			return new ChartPaletteColor (code, name, propertyGroup);
		}
		catch (Exception e){
			throw new RuntimeException(e);
		}
	}
	
}
