package uap.bq.chart.cache;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import nc.bs.framework.common.NCLocator;
import nc.vo.ml.MultiLangContext;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import uap.bq.chart.utils.ChartMultiLangUtils;
import uap.itf.bq.chart.IChartDefineService;
import uap.vo.bq.chart.define.ChartDefine;
import uap.vo.bq.chart.define.ChartDefineDigest;
import uap.vo.bq.chart.define.ConstraintTerm;
import uap.vo.bq.chart.define.DefaultSetting;
import uap.vo.bq.chart.define.PropertyDefine;
import uap.vo.bq.chart.define.PropertyGroupDefine;
import uap.vo.bq.chart.define.StyleApplyObject;
import uap.vo.bq.chart.define.StyleGroupDefine;
import uap.vo.bq.chart.model.Property;
import uap.vo.bq.chart.model.PropertyGroup;
import uap.vo.bq.chart.model.databinding.IChartDataBinding;
import uap.vo.bq.chart.model.dataset.AbsChartDataElementProxy;
import uap.vo.bq.chart.model.dataset.ChartDataset;
import uap.vo.bq.chart.model.dataset.ChartDataset.ChartDataElement;
import uap.vo.bq.chart.model.dataset.ChartHeader;
import uap.vo.bq.chart.model.dataset.ChartHeaderCell;
import uap.vo.bq.chart.model.dataset.IChartDataElement;

/**
 * 
* @ClassName: ChartDefineCache 
* @Description: ͳ��ͼ����Cache
* @author  guohch
* @date 2013��12��3�� ����2:51:34 
*
 */
public class ChartDefineCache {

	public static ChartDefineCache instance = new ChartDefineCache();

	private Hashtable<String, ChartDefine> chartDefineCache = new Hashtable<String, ChartDefine>();

	private ChartDefineDigest[] chartDefineDigests = new ChartDefineDigest[0];

	/**
	 * ��������ֱ���
	 */
	private Integer cacheLang = MultiLangContext.getInstance().getCurrentLangSeq();

	private ChartDefineCache() {

	}

	/**
	 * 
	* @Description: ��ȡ����
	 */
	public static ChartDefineCache getInstance() {
		if (instance.cacheLang != MultiLangContext.getInstance().getCurrentLangSeq()) {
			instance.clearCache();
		}
		return instance;
	}

	/**
	 * 
	* @Description: ��ȡchartDefine
	 */
	public synchronized ChartDefine getChartDefine(String chartDefineCode) {
		if (StringUtils.isEmpty(chartDefineCode)) {
			return null;
		}
		ChartDefine chartDefine = chartDefineCache.get(chartDefineCode);
		if (chartDefine == null) {
			IChartDefineService cahrtDefineService = NCLocator.getInstance().lookup(IChartDefineService.class);
			chartDefine = cahrtDefineService.getChartDefine(chartDefineCode);
			if (chartDefine == null) {
				return null;
			}
			chartDefine = processChartDefineMultilang(chartDefine);
			chartDefineCache.put(chartDefine.getCode(), chartDefine);
		}
		return chartDefine;
	}

	/**
	 * 
	* @Description: ����ѹ��chartDefineCodes��ChartDefine��������
	 */
	public synchronized ChartDefine[] getChartDefines(String[] chartDefineCodes) {
		if (ArrayUtils.isEmpty(chartDefineCodes)) {
			return new ChartDefine[0];
		}
		List<String> notInCacheDefineCodes = new ArrayList<String>();
		for (String defineCode : chartDefineCodes) {
			ChartDefine chartDefine = chartDefineCache.get(defineCode);
			if (chartDefine == null) {
				notInCacheDefineCodes.add(defineCode);
			}
		}

		if (notInCacheDefineCodes.size() > 0) {
			IChartDefineService cahrtDefineService = NCLocator.getInstance().lookup(IChartDefineService.class);
			ChartDefine[] chartDefines = cahrtDefineService.getChartDefines(notInCacheDefineCodes
					.toArray(new String[notInCacheDefineCodes.size()]));
			for (ChartDefine chartDefine : chartDefines) {
				chartDefine = processChartDefineMultilang(chartDefine);
				if (chartDefine != null) {
					chartDefineCache.put(chartDefine.getCode(), chartDefine);
				}
			}
		}
		List<ChartDefine> chartDefines = new ArrayList<ChartDefine>();
		for (String defineCode : chartDefineCodes) {
			ChartDefine chartDefine = chartDefineCache.get(defineCode);
			chartDefines.add(chartDefine);
		}

		return chartDefines.toArray(new ChartDefine[chartDefines.size()]);

	}

	/**
	 * ��õ�ǰ���е�ͳ��ͼժҪ
	 * 
	 * @return ͳ��ͼ����ժҪ����
	 */
	public synchronized ChartDefineDigest[] getChartDefineDigests() {
		if (ArrayUtils.isEmpty(chartDefineDigests)) {
			IChartDefineService cahrtDefineService = NCLocator.getInstance().lookup(IChartDefineService.class);
			ChartDefineDigest[] chartDefineDigests = cahrtDefineService.getChartDefineDigests();
			List<ChartDefineDigest> chartDefineDigestsList = new ArrayList<ChartDefineDigest>();
			for (ChartDefineDigest chartDefineDigest : chartDefineDigests) {
				chartDefineDigestsList.add(processChartDefineDigestMultilang(chartDefineDigest));
			}
			this.chartDefineDigests = chartDefineDigestsList.toArray(new ChartDefineDigest[chartDefineDigestsList
					.size()]);
		}
		return chartDefineDigests;
	}

	public synchronized void clearCache() {
		this.chartDefineCache.clear();
		this.chartDefineDigests = new ChartDefineDigest[0];

	}

	/**
	 * ���ﴦ�� digest
	* @Description: TODO guohch
	 */
	private ChartDefineDigest processChartDefineDigestMultilang(ChartDefineDigest chartDefineDigest) {
		if (chartDefineDigest == null) {
			return null;
		}
		ChartDefineDigest copyChartDefineDigest = (ChartDefineDigest) chartDefineDigest.clone();
		String multiNameId = copyChartDefineDigest.getName();
		if (StringUtils.isNotEmpty(multiNameId)) {
			String multiName = ChartMultiLangUtils.getResValue(multiNameId);
			copyChartDefineDigest.setName(multiName);
		}
		String multiTypeId = copyChartDefineDigest.getType();
		if (StringUtils.isNotEmpty(multiTypeId)) {
			String multiType = ChartMultiLangUtils.getResValue(multiTypeId);
			copyChartDefineDigest.setType(multiType);
		}
		String multiCategoryId = copyChartDefineDigest.getCategory();
		if (StringUtils.isNotEmpty(multiCategoryId)) {
			String multiCategory = ChartMultiLangUtils.getResValue(multiCategoryId);
			copyChartDefineDigest.setCategory(multiCategory);
		}

		return copyChartDefineDigest;
	}

	/**
	 * ���ﴦ��ChartDefine
	* @Description: TODO guohch
	 */
	private ChartDefine processChartDefineMultilang(ChartDefine chartDefine) {
		if (chartDefine == null) {
			return null;
		}
		ChartDefine copyChartDefine = (ChartDefine) chartDefine.clone();
		//�������
		String multiNameId = copyChartDefine.getName();
		if (StringUtils.isNotEmpty(multiNameId)) {
			String multiName = ChartMultiLangUtils.getResValue(multiNameId);
			copyChartDefine.setName(multiName);
		}
		String multiTypeId = copyChartDefine.getType();
		if (StringUtils.isNotEmpty(multiTypeId)) {
			String multiType = ChartMultiLangUtils.getResValue(multiTypeId);
			copyChartDefine.setType(multiType);
		}
		String multiCategoryId = copyChartDefine.getCategory();
		if (StringUtils.isNotEmpty(multiCategoryId)) {
			String multiCategory = ChartMultiLangUtils.getResValue(multiCategoryId);
			copyChartDefine.setCategory(multiCategory);
		}
		//�����Զ���
		//1.Ĭ������
		DefaultSetting defaultSetting = copyChartDefine.getDefaultSetting();
		if (defaultSetting != null) {
			//a.Ĭ������
			PropertyGroup[] propertyGroups = defaultSetting.getPropertyGroups();
			if (!ArrayUtils.isEmpty(propertyGroups)) {
				for (PropertyGroup propertyGroup : propertyGroups) {
					List<Property> properties = propertyGroup.getProperties();
					if (properties != null && properties.size() > 0) {
						for (Property property : properties) {
							String mulCode = property.getMulCode();
							if (StringUtils.isNotEmpty(mulCode)) {
								property.setMulValue(ChartMultiLangUtils.getResValue(mulCode));
							}
						}
					}
				}
			}

			//b.Ĭ�����ݰ�
			IChartDataBinding[] dataBindings = defaultSetting.getDataBindings();
			if (!ArrayUtils.isEmpty(dataBindings)) {
				for (IChartDataBinding dataBinding : dataBindings) {
					if (dataBinding instanceof AbsChartDataElementProxy) {
						IChartDataElement chartDataElement = ((AbsChartDataElementProxy) dataBinding)
								.getChartDataElement();
						if (chartDataElement instanceof ChartDataElement) {
							String mulCode = ((ChartDataElement) chartDataElement).getMulCode();
							if (StringUtils.isNotEmpty(mulCode)) {
								String mulCaption = ChartMultiLangUtils.getResValue(mulCode);
								((ChartDataElement) chartDataElement).setMulCaption(mulCaption);
							}
						}
					}
				}
			}

			//c. Ĭ������
			ChartDataset[] datasets = defaultSetting.getDatasets();
			if (!ArrayUtils.isEmpty(datasets)) {
				for (ChartDataset chartDataset : datasets) {
					ChartHeader chartHeader = chartDataset.getChartHeader();
					if (chartHeader != null) {
						ChartHeaderCell[] headerCells = chartHeader.getHeaderCells();
						if (!ArrayUtils.isEmpty(headerCells)) {
							for (ChartHeaderCell chartHeaderCell : headerCells) {
								IChartDataElement chartDataElement = chartHeaderCell.getChartDataElement();
								String mulCode = ((ChartDataElement) chartDataElement).getMulCode();
								if (StringUtils.isNotEmpty(mulCode)) {
									String mulCaption = ChartMultiLangUtils.getResValue(mulCode);
									((ChartDataElement) chartDataElement).setMulCaption(mulCaption);
								}

							}
						}

					}
				}
			}

		}

		//2.�����鶨��
		PropertyGroupDefine[] propertyGroupDefines = copyChartDefine.getPropertyGroupDefines();
		if (!ArrayUtils.isEmpty(propertyGroupDefines)) {
			for (PropertyGroupDefine propertyGroupDefine : propertyGroupDefines) {
				if (propertyGroupDefine != null) {
					//a.����name
					String multiPropGropDefNameId = propertyGroupDefine.getName();
					String multiPropGropDefName = ChartMultiLangUtils.getResValue(multiPropGropDefNameId);
					propertyGroupDefine.setName(multiPropGropDefName);
					//b.�����Զ���
					PropertyDefine[] properties = propertyGroupDefine.getProperties();
					if (!ArrayUtils.isEmpty(properties)) {
						for (PropertyDefine propertyDefine : properties) {
							if (propertyDefine != null) {
								String multiPropertyDefineNameId = propertyDefine.getName();
								if (StringUtils.isNotEmpty(multiPropertyDefineNameId)) {
									String multiPropertyDefineName = ChartMultiLangUtils
											.getResValue(multiPropertyDefineNameId);
									propertyDefine.setName(multiPropertyDefineName);
								}
								String descriptionId = propertyDefine.getDescription();
								if (StringUtils.isNotEmpty(descriptionId)) {
									String description = ChartMultiLangUtils.getResValue(descriptionId);
									propertyDefine.setDescription(description);
								}
								String multiDefaultValueId = propertyDefine.getDefaultValue();
								if (StringUtils.isNotEmpty(multiDefaultValueId)) {
									String multiDefaultValue = ChartMultiLangUtils.getResValue(multiDefaultValueId);
									propertyDefine.setDefaultValue(multiDefaultValue);
								}
								
								//ö��ֵ
								ConstraintTerm[] constraintTerms = propertyDefine.getConstraintTerms();
								if(!ArrayUtils.isEmpty(constraintTerms)){
									for (ConstraintTerm constraintTerm : constraintTerms) {
										String nameMultiCode = constraintTerm.getName();
										if (StringUtils.isNotEmpty(nameMultiCode)) {
											String multiName = ChartMultiLangUtils.getResValue(nameMultiCode);
											constraintTerm.setName(multiName);
										}
									}
									
								}

							}
						}
					}

					if (propertyGroupDefine instanceof StyleGroupDefine) {
						StyleGroupDefine styleGroupDefine = (StyleGroupDefine) propertyGroupDefine;
						StyleApplyObject[] styleApplyObjects = styleGroupDefine.getStyleApplyObjects();
						if (!ArrayUtils.isEmpty(styleApplyObjects)) {
							for (StyleApplyObject styleApplyObject : styleApplyObjects) {
								if (styleApplyObject != null) {
									String styleApplyNameId = styleApplyObject.getName();
									if (StringUtils.isNotEmpty(styleApplyNameId)) {
										String styleApplyName = ChartMultiLangUtils.getResValue(styleApplyNameId);
										styleApplyObject.setName(styleApplyName);
									}
								}

							}
						}
					}

				}

			}
		}
		
		DefaultSetting noDataDefaultSetting = copyChartDefine.getNoDataDefaultSetting();
		if (noDataDefaultSetting != null) {
			//a.Ĭ������
			PropertyGroup[] propertyGroups = noDataDefaultSetting.getPropertyGroups();
			if (!ArrayUtils.isEmpty(propertyGroups)) {
				for (PropertyGroup propertyGroup : propertyGroups) {
					List<Property> properties = propertyGroup.getProperties();
					if (properties != null && properties.size() > 0) {
						for (Property property : properties) {
							String mulCode = property.getMulCode();
							if (StringUtils.isNotEmpty(mulCode)) {
								property.setMulValue(ChartMultiLangUtils.getResValue(mulCode));
							}
						}
					}
				}
			}

			//b.Ĭ�����ݰ�
			IChartDataBinding[] dataBindings = noDataDefaultSetting.getDataBindings();
			if (!ArrayUtils.isEmpty(dataBindings)) {
				for (IChartDataBinding dataBinding : dataBindings) {
					if (dataBinding instanceof AbsChartDataElementProxy) {
						IChartDataElement chartDataElement = ((AbsChartDataElementProxy) dataBinding)
								.getChartDataElement();
						if (chartDataElement instanceof ChartDataElement) {
							String mulCode = ((ChartDataElement) chartDataElement).getMulCode();
							if (StringUtils.isNotEmpty(mulCode)) {
								String mulCaption = ChartMultiLangUtils.getResValue(mulCode);
								((ChartDataElement) chartDataElement).setMulCaption(mulCaption);
							}
						}
					}
				}
			}

			//c. Ĭ������
			ChartDataset[] datasets = noDataDefaultSetting.getDatasets();
			if (!ArrayUtils.isEmpty(datasets)) {
				for (ChartDataset chartDataset : datasets) {
					ChartHeader chartHeader = chartDataset.getChartHeader();
					if (chartHeader != null) {
						ChartHeaderCell[] headerCells = chartHeader.getHeaderCells();
						if (!ArrayUtils.isEmpty(headerCells)) {
							for (ChartHeaderCell chartHeaderCell : headerCells) {
								IChartDataElement chartDataElement = chartHeaderCell.getChartDataElement();
								String mulCode = ((ChartDataElement) chartDataElement).getMulCode();
								if (StringUtils.isNotEmpty(mulCode)) {
									String mulCaption = ChartMultiLangUtils.getResValue(mulCode);
									((ChartDataElement) chartDataElement).setMulCaption(mulCaption);
								}

							}
						}

					}
				}
			}

		}
		

		return copyChartDefine;
	}

}
