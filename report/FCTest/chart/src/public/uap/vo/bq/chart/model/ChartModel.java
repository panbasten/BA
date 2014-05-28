package uap.vo.bq.chart.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import uap.bq.chart.cache.ChartDefineCache;
import uap.vo.bq.chart.define.ChartDefine;
import uap.vo.bq.chart.define.DefaultSetting;
import uap.vo.bq.chart.model.databinding.ChartDataBindingMgr;
import uap.vo.bq.chart.model.databinding.IChartDataBinding;
import uap.vo.bq.chart.model.dataset.ChartDataset;

/**
 * ChartModel
 * 
 * @author zhanglld
 * 
 */
public class ChartModel implements IModelObject {
	private static final long serialVersionUID = 1L;

	/* ���õ�chartDefine�� code */
	private String chartDefineCode;
	/* �����Ƿ�ı� */
	private boolean isChanged = false;
	/* �洢�޸ĵ����� */
	private List<PropertyGroup> propertyGroups = new RecordChangeList<PropertyGroup>();
	/* ���ݼ��� �����ᣬ ϵ��� */
	private ChartDataBindingMgr chartDataBindingMgr = new ChartDataBindingMgr();
	/* ���ݼ��洢 */
	private transient List<ChartDataset> datasets = new ArrayList<ChartDataset>();
	/* һЩ���󻺴� */
	private transient Map<String, Object> chartCacheDataMap = new HashMap<String, Object>();
	// ���������л�
	private transient List<ChartModelListener> changeListeners = new ArrayList<ChartModelListener>();

	/**
	 * ����ͳ��ͼcode����chartmodel����
	 * 
	 * @param chartDefineCode
	 */
	public ChartModel(String chartDefineCode) {
		((RecordChangeList<PropertyGroup>) propertyGroups).setSuperObject(this);

		this.chartDefineCode = chartDefineCode;
		DefaultSetting defaultSetting = (DefaultSetting) ChartDefineCache.getInstance().getChartDefine(chartDefineCode)
				.getDefaultSetting().clone();
		PropertyGroup[] propertyGroups = defaultSetting.getPropertyGroups();
		for (PropertyGroup group : propertyGroups) {
			this.propertyGroups.add(group);
		}

		IChartDataBinding[] chartDataBindings = defaultSetting
				.getDataBindings();
		for (IChartDataBinding cdb : chartDataBindings) {
			this.chartDataBindingMgr.add(cdb);
		}
	}
	
	
	public ChartModel(ChartDefine define) {
		 
		DefaultSetting defaultSetting = define.getNoDataDefaultSetting();
		((RecordChangeList<PropertyGroup>) propertyGroups).setSuperObject(this);

		this.chartDefineCode = define.getCode();
		/*DefaultSetting defaultSetting = (DefaultSetting) ChartDefineCache.getInstance().getChartDefine(chartDefineCode)
				.getDefaultSetting().clone();*/
		PropertyGroup[] propertyGroups = defaultSetting.getPropertyGroups();
		for (PropertyGroup group : propertyGroups) {
			this.propertyGroups.add(group);
		}

		IChartDataBinding[] chartDataBindings = defaultSetting
				.getDataBindings();
		for (IChartDataBinding cdb : chartDataBindings) {
			this.chartDataBindingMgr.add(cdb);
		}
		if(defaultSetting.getDatasets()!=null)
			for(ChartDataset data : defaultSetting.getDatasets()){
			   datasets.add(data);
		    }
		
	}
	
	/**
	 * �õ�����Ϣ������
	 * @return
	 */
	public PropertyGroup[] getDataBindingPropertyGroups(){
		List<PropertyGroup> result = new LinkedList<PropertyGroup>();
		for(PropertyGroup group : this.getPropertyGroups()){
			if (group instanceof DataBindingRefGroup)
				result.add(group);
		}
		return result.toArray(new PropertyGroup[result.size()]);
	}

	/**
	 * ��ȡ��ǰģ����ͳ��ͼ��code
	 * 
	 * @return
	 */
	public String getChartDefineCode() {
		return chartDefineCode;
	}

	public String getChartCaption() {
		return (String) getChartCacheDataMap().get("FUSIONCHART_CAPTION");
	}

	/**
	 * ����������
	 * 
	 * @param propertyGroups
	 */
	public void setPropertyGroups(List<PropertyGroup> propertyGroups) {
		if (this.propertyGroups.equals(propertyGroups)) {
			return;
		}

		this.propertyGroups.clear();
		for (PropertyGroup propertyGroup : propertyGroups) {
			PropertyGroup group = (PropertyGroup) propertyGroup;
			this.propertyGroups.add(group);
		}
	}

	public void setPropertyGroups(PropertyGroup[] propertyGroups) {
		if (this.propertyGroups == null || this.propertyGroups.size() == 0) {
			return;
		}
		this.propertyGroups.clear();

		this.propertyGroups.addAll(Arrays.asList(propertyGroups));
	}

	/**
	 * ����ָ�����������code��ȡָ������code�����Զ���
	 * 
	 * @param groupCode
	 * @param code
	 * @return
	 */
	public Property getProperty(String groupCode, String code) {
		PropertyGroup group = getPropertyGroup(groupCode);
		for (Property prop : group.getProperties()) {
			if (prop.getRefCode().equals(code)) {
				return prop;
			}
		}
		return null;
	}

	/**
	 * ���ݶ�������������
	 * 
	 * @param code
	 * @return
	 */
	public PropertyGroup[] getPropertyGroups(String... codes) {
		if (this.propertyGroups == null || this.propertyGroups.size() == 0) {
			return null;
		}

		List<PropertyGroup> ls = new ArrayList<PropertyGroup>();
		for (PropertyGroup grp : this.propertyGroups) {
			if (Arrays.asList(codes).contains(grp.getRefCode())) {
				ls.add(grp);
			}
		}
		return ls.toArray(new PropertyGroup[ls.size()]);
	}

	public PropertyGroup getPropertyGroup(String code) {
		for (PropertyGroup grp : this.propertyGroups) {
			if (grp.getCode().equals(code)) {
				return grp;
			}
		}
		return null;
	}

	public PropertyGroup[] getPropertyGroupsByClass(
			Class<? extends PropertyGroup> classDesc) {
		List<PropertyGroup> ls = new ArrayList<PropertyGroup>();
		for (PropertyGroup grp : this.propertyGroups) {
			if (grp.getClass().getName().equals(classDesc.getName())) {
				ls.add(grp);
			}
		}
		return ls.toArray(new PropertyGroup[ls.size()]);
	}

	/**
	 * ��ȡԤ���ߵ��������б�
	 * 
	 * @return
	 */
	public PropertyGroup[] getWarnningPropertyGroups() {
		List<PropertyGroup> grpList = new ArrayList<PropertyGroup>();
		for (PropertyGroup grp : this.propertyGroups) {
			if (grp instanceof WarningPropertyGroup) {
				grpList.add(grp);
			}
		}
		return grpList.toArray(new PropertyGroup[grpList.size()]);
	}

	/**
	 * ��ȡͳ��ͼ����������
	 * 
	 * @return
	 */
	public PropertyGroup[] getPropertyGroups() {
		return propertyGroups.toArray(new PropertyGroup[propertyGroups.size()]);
	}

	
	
	public List<PropertyGroup> getPropertyGroupsList() {
		return propertyGroups;
	}
	
	
	public void addPropertyGroup(PropertyGroup... group) {
		propertyGroups.addAll(Arrays.asList(group));
	}
	
	public void addPropertyGroup(List<PropertyGroup> groups){
		propertyGroups.addAll(groups);
	}

	public void removePropertyGroup(PropertyGroup... group) {
		if (propertyGroups == null || propertyGroups.size() == 0) {
			return;
		}

		for (PropertyGroup d : group) {
			propertyGroups.remove(d);
		}
	}

	public void removePropertyGroup(String... refCode) {
		if (propertyGroups == null || propertyGroups.size() == 0) {
			return;
		}

		List<String> ls = Arrays.asList(refCode);
		List<PropertyGroup> group = new ArrayList<PropertyGroup>();
		for (PropertyGroup d : propertyGroups) {
			if (ls.contains(d.getRefCode())) {
				group.add(d);
			}
		}
		propertyGroups.removeAll(group);
	}

	/**
	 * ��ȡ���ݰ��б�
	 * 
	 * @return
	 */
	public IChartDataBinding[] getDataBindings() {
		return this.chartDataBindingMgr.getChartBindings();
	}

	/**
	 * �������ݰ�
	 * 
	 * @param dataBindings
	 */
	public void setDataBindings(IChartDataBinding[] dataBindings) {

		this.chartDataBindingMgr.clear();
		for (IChartDataBinding dataBinding : dataBindings) {
			this.chartDataBindingMgr.add(dataBinding);
		}

		setChange(true);
	}

	/**
	 * ��ȡ���ݰ󶨹������
	 * 
	 * @return
	 */
	public ChartDataBindingMgr getChartDataBindingMgr() {
		return chartDataBindingMgr;
	}

	/**
	 * �������ݰ󶨹������
	 * 
	 * @param chartDataBindingMgr
	 */
	public void setChartDataBindingMgr(ChartDataBindingMgr chartDataBindingMgr) {
		this.chartDataBindingMgr = chartDataBindingMgr;
	}

	/**
	 * ��ȡ״̬�ı�ļ������б�
	 * 
	 * @return
	 */
	public ChartModelListener[] getChangeListeners() {
		if (this.changeListeners == null) {
			changeListeners = new ArrayList<ChartModelListener>();
		}
		return changeListeners.toArray(new ChartModelListener[changeListeners
				.size()]);
	}

	public void setChangeListeners(ChartModelListener[] listeners) {
		if (this.changeListeners == null) {
			changeListeners = new ArrayList<ChartModelListener>();
		}
		changeListeners.clear();
		for (ChartModelListener ls : listeners) {
			changeListeners.add(ls);
		}
	}

	/**
	 * �������ݼ�
	 * 
	 * @param datasets
	 */
	public void setDatasets(ChartDataset[] datasets) {
		if (datasets == null)
			return;

		if (this.getDatasets() == datasets) {
			return;
		}

		getDatasetList().clear();
		for (ChartDataset dataset : datasets) {
			getDatasetList().add(dataset);
		}

		setChange(true);
	}
	
	private List<ChartDataset> getDatasetList(){
		if(this.datasets == null){
			this.datasets = new ArrayList<ChartDataset>();
		}
		return datasets;
	}

	/**
	 * ��ȡchartmodel�е����ݼ�
	 * 
	 * @return
	 */
	public ChartDataset[] getDatasets() {
		if (this.datasets == null) {
			return new ChartDataset[0];
		}
		return this.datasets.toArray(new ChartDataset[datasets.size()]);
	}

	public boolean isDatasetChanged() {
		for (ChartDataset dataset : getDatasetList()) {
			if (dataset.isChanged()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * ����dataset��code��ȡdataset
	 * 
	 * @param code
	 * @return
	 */
	public ChartDataset getDataset(String code) {
		for (ChartDataset dataset : getDatasets()) {
			if (dataset.getCode().equals(code)) {
				return dataset;
			}
		}
		return null;
	}

	@Override
	public boolean isChanged() {
		return isChanged;
	}

	/**
	 * �������
	 * 
	 * @throws ChangeHandleException
	 * @throws ClassNotFoundException
	 */
	public void fireChange() throws ChangeHandleException,
			ClassNotFoundException {
		if (changeListeners != null) {
			for (ChartModelListener listener : changeListeners) {
				listener.onChange(this);
			}
			setUnChange();
		}
	}

	/**
	 * ��������δ���
	 */
	private void setUnChange() {
		for (int i = 0; i < this.changeListeners.size(); i++) {
			((RecordChangeList<PropertyGroup>) propertyGroups).setChange(false);
			IChartDataBinding[] chartDataBindings = chartDataBindingMgr
					.getChartBindings();
			for (IChartDataBinding dbi : chartDataBindings) {
				dbi.setChanged(false);
			}
		}

		isChanged = false;
	}

	/**
	 * ����ģ�͵ĸı�״̬
	 */
	@Override
	public void setChange(boolean isChanged) {
		this.isChanged = isChanged;
		if (!isChanged) {
			((RecordChangeList<PropertyGroup>) propertyGroups).setChange(false);
			IChartDataBinding[] chartDataBindings = chartDataBindingMgr
					.getChartBindings();
			for (IChartDataBinding dbi : chartDataBindings) {
				dbi.setChanged(false);
			}
			for (ChartDataset ds : getDatasetList()) {
				ds.setChanged(isChanged);
			}
		}
	}

	/**
	 * ��ñ������
	 * 
	 * @return
	 */
	public List<PropertyGroup> getChangedGroups() {
		List<PropertyGroup> changedGroupList = new ArrayList<PropertyGroup>();
		if (((RecordChangeList<PropertyGroup>) propertyGroups).isChanged()) {
			for (PropertyGroup group : this.propertyGroups) {
				if (group.isChanged()) {
					changedGroupList.add(group);
				}
			}
		}
		return changedGroupList;
	}

	/**
	 * ��ñ�������ݰ�
	 * 
	 * @return
	 */
	public IChartDataBinding[] getChangedDataBindings() {
		List<IChartDataBinding> changedList = new ArrayList<IChartDataBinding>();
		IChartDataBinding[] chartDataBindings = chartDataBindingMgr
				.getChartBindings();
		for (IChartDataBinding dbi : chartDataBindings) {
			changedList.add(dbi);
		}
		return changedList.toArray(new IChartDataBinding[changedList.size()]);
	}

	/**
	 * ��ȡģ�ͻ���ӳ������
	 * 
	 * @return
	 */
	protected Map<String, Object> getChartCacheDataMap() {
		if (chartCacheDataMap == null) {
			chartCacheDataMap = new HashMap<String, Object>();
		}
		return chartCacheDataMap;
	}

	/**
	 * key in {"categories", ...}
	 * 
	 * @param key
	 * @return
	 */
	public Object getChartCacheData(String key) {
		return getChartCacheDataMap().get(key);
	}

	/**
	 * ���û��������Ͷ���
	 * 
	 * @param key
	 * @param value
	 */
	public void putChartCacheData(String key, Object value) {
		getChartCacheDataMap().put(key, value);
	}

	/**
	 * ���Ӽ�����
	 * 
	 * @param changeListener
	 */
	public void addListener(ChartModelListener changeListener) {
		if (this.changeListeners == null) {
			changeListeners = new ArrayList<ChartModelListener>();
		}
		this.changeListeners.add(changeListener);
	}

	/**
	 * �Ƴ�������
	 * 
	 * @param changeListener
	 */
	public void removeListener(ChartModelListener changeListener) {
		if (this.changeListeners == null) {
			changeListeners = new ArrayList<ChartModelListener>();
		}
		changeListeners.remove(changeListener);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object clone() {
		ChartModel newModel = null;
		try {
			newModel = (ChartModel) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
		RecordChangeList<PropertyGroup> newGroupList = (RecordChangeList<PropertyGroup>) ((RecordChangeList<PropertyGroup>) propertyGroups)
				.clone();
		newGroupList.setSuperObject(newModel);

		newModel.propertyGroups = (RecordChangeList<PropertyGroup>) newGroupList;
		try {
			newModel.chartDataBindingMgr = (ChartDataBindingMgr) this.chartDataBindingMgr
					.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}

		return newModel;
	}
}
