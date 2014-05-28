package uap.ui.bq.chart.component;

import java.awt.Image;
import java.awt.event.AWTEventListener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import uap.bq.chart.generator.DataGenerateResult;
import uap.vo.bq.chart.define.ChartDefine;
import uap.vo.bq.chart.model.ChartImplType;
import uap.vo.bq.chart.model.ChartModel;
import uap.vo.bq.chart.model.ChartModelListener;
import uap.vo.bq.chart.model.ImageCreatedListener;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;

class ChartPanelPrivateData {
	private boolean initialized = false;
	private String currentPanel = "";
	/* ��������������JS���õ� */
	private String serverRoot = null;
	/* ��ǰChartModel */
	private ChartModel model = null;
	/* ��ǰ�Ƿ���ͼƬģʽ */
	private boolean isImageModel = false;
	/* ͳ��ͼ������� */
	private ChartDefine chartDefine = null;
	/* �õ���WebBrowser���� */
	private JWebBrowser webBrowser = null;
	/* ��ǰPanel��ֱ��������壬����CardLayout */
	private JLabel waitPanel = null;
	private JPanel browserPanel = null;
	/* ͼƬ��� */
	private ImagePanel imgPanel = null;
	/* �Ƿ������л�ͼƬ */
	private boolean disableSwitchImage = false;
	/* ͳ��ͼʵ������ */
	private ChartImplType chartImplType = ChartImplType.FUSIONCHARTS;
	/* �仯�������б� */
	private List<ChartModelListener> listenerList = new ArrayList<ChartModelListener>();
	/* ����ת�����֮��ִ�н������ */
	private DataGenerateResult resultExecuteInfo = null;

	private WeakReference<AWTEventListener> globalAWTEventListener = null;

	public AWTEventListener getGlobalAWTEventListener() {
		return globalAWTEventListener != null ? globalAWTEventListener.get()
				: null;
	}

	public void setGlobalAWTEventListener(
			AWTEventListener globalAWTEventListener) {
		this.globalAWTEventListener = new WeakReference<AWTEventListener>(
				globalAWTEventListener);
	}

	private boolean hasRendered = true;

	private boolean componentResized = false;
	private boolean mouseDown = false;

	public boolean isComponentResized() {
		return componentResized;
	}

	public void setComponentResized(boolean componentResized) {
		this.componentResized = componentResized;
	}

	public boolean isMouseDown() {
		return mouseDown;
	}

	public void setMouseDown(boolean mouseDown) {
		this.mouseDown = mouseDown;
	}

	private Image image = null;

	private ImageCreatedListener imageCreatedListener = null;

	public ImageCreatedListener getImageCreatedListener() {
		return imageCreatedListener;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void setImageCreatedListener(
			ImageCreatedListener imageCreatedListener) {
		this.imageCreatedListener = imageCreatedListener;
	}

	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	public String getCurrentPanel() {
		return currentPanel;
	}

	public void setCurrentPanel(String currentPanel) {
		this.currentPanel = currentPanel;
	}

	public String getServerRoot() {
		return serverRoot;
	}

	public void setServerRoot(String serverRoot) {
		this.serverRoot = serverRoot;
	}

	public ChartModel getModel() {
		return model;
	}

	public void setModel(ChartModel model) {
		this.model = model;
	}

	public boolean isImageModel() {
		return isImageModel;
	}

	public void setImageModel(boolean isImageModel) {
		this.isImageModel = isImageModel;
	}

	public void setDisableSwitchImage(boolean disable) {
		this.disableSwitchImage = disable;
	}

	public boolean isDisableSwitchImage() {
		return this.disableSwitchImage;
	}

	public ChartDefine getChartDefine() {
		return chartDefine;
	}

	public void setChartDefine(ChartDefine chartDefine) {
		this.chartDefine = chartDefine;
	}

	public JWebBrowser getWebBrowser() {
		return webBrowser;
	}

	public void setWebBrowser(JWebBrowser webBrowser) {
		this.webBrowser = webBrowser;
	}

	public JLabel getWaitPanel() {
		return waitPanel;
	}

	public void setWaitPanel(JLabel waitPanel) {
		this.waitPanel = waitPanel;
	}

	public JPanel getBrowserPanel() {
		return browserPanel;
	}

	public void setBrowserPanel(JPanel browserPanel) {
		this.browserPanel = browserPanel;
	}

	public ImagePanel getImgPanel() {
		return imgPanel;
	}

	public void setImgPanel(ImagePanel imgPanel) {
		this.imgPanel = imgPanel;
	}

	public ChartImplType getChartImplType() {
		return chartImplType;
	}

	public void setChartImplType(ChartImplType chartImplType) {
		this.chartImplType = chartImplType;
	}

	public List<ChartModelListener> getListenerList() {
		return listenerList;
	}

	public void setListenerList(List<ChartModelListener> listenerList) {
		this.listenerList = listenerList;
	}

	public DataGenerateResult getResultExecuteInfo() {
		return resultExecuteInfo;
	}

	public void setResultExecuteInfo(DataGenerateResult resultExecuteInfo) {
		this.resultExecuteInfo = resultExecuteInfo;
	}

	public boolean isHasRendered() {
		return hasRendered;
	}

	public void setHasRendered(boolean hasRendered) {
		this.hasRendered = hasRendered;
	}

	public void clear() {
		this.browserPanel = null;
		this.chartDefine = null;
		this.initialized = false;
		if (this.globalAWTEventListener != null) {
			this.globalAWTEventListener.clear();
		}
		this.model = null;
		this.image = null;
		this.imgPanel = null;
		this.imageCreatedListener = null;
		if (this.listenerList != null) {
			this.listenerList.clear();
		}
		this.resultExecuteInfo = null;
		this.webBrowser = null;
		this.waitPanel = null;
	}

	public void clearSomeResource() {
		this.browserPanel = null;
		this.initialized = false;
		if (this.globalAWTEventListener != null) {
			this.globalAWTEventListener.clear();
		}
		this.image = null;
		this.imgPanel = null;
		this.imageCreatedListener = null;
		if (this.listenerList != null) {
			this.listenerList.clear();
		}
		this.resultExecuteInfo = null;
		this.webBrowser = null;
		this.waitPanel = null;
	}

}