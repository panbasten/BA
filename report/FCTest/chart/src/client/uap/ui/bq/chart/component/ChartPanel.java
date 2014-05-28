package uap.ui.bq.chart.component;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import uap.bq.chart.ChartPropConst;
import uap.bq.chart.component.NativeSwingException;
import uap.bq.chart.component.NativeSwingHelper;
import uap.bq.chart.generator.ChartGenerateException;
import uap.bq.chart.generator.DataGenerateResult;
import uap.util.bq.chart.ChartUtil;
import uap.util.bq.chart.ServerInfoUtil;
import uap.vo.bq.chart.define.APIDefine;
import uap.vo.bq.chart.define.ChartDefine;
import uap.vo.bq.chart.define.Input;
import uap.vo.bq.chart.model.ChangeHandleException;
import uap.vo.bq.chart.model.ChartModel;
import uap.vo.bq.chart.model.ChartModelListener;
import uap.vo.bq.chart.model.IChartJavaScript;
import uap.vo.bq.chart.model.ImageCreatedListener;
import uap.vo.bq.chart.model.Property;
import uap.vo.bq.chart.model.PropertyGroup;
import uap.vo.bq.chart.model.databinding.IChartDataBinding;
import chrriis.dj.nativeswing.swtimpl.NativeComponent;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserAdapter;

import com.ufida.iufo.pub.tools.AppDebug;

/**
 * 统计图面板
 * 
 * @author zhanglld
 */
public class ChartPanel extends JPanel implements ChartModelListener,
		IChartJavaScript {

	private static final long serialVersionUID = 1L;

	private static NativeSwingException nsException = null;

	// 第一个面板：请等待提示；第二个面板：统计图面板；第三个面板：图片面板
	static String PANEL_WAITLOADING = "waitting-panel";
	static String PANEL_FUSIONCHART = "fusionchart-panel";
	static String PANEL_IMAGECHART = "imagemode-panel";

	/*
	 * 内存还剩多少个此类实例对象
	 */
	static int countChartPane = 0;
	/*
	 * 一共创建过多少个实例对象
	 */
	static int sumChartPane = 0;

	private ChartPanelPrivateData chartPanelPrivateData = new ChartPanelPrivateData();

	/**
	 * 确保打开DJNativeSwing本地接口
	 */
	static {
		try {
			NativeSwingHelper.openNativeInterface();
		} catch (IOException e) {
			nsException = new NativeSwingException(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
							"0502004_0", "00502004-0058")/*
														 * @res
														 * "打开DJNativeSwing本地接口失败！"
														 */, e);
			throw nsException;

		}
	}

	/*
	 * @Override protected void finalize() throws Throwable { clearRescs();
	 * super.finalize(); }
	 */

	public void destroyObj() {

	}

	private boolean freeResourcedone = false;
	//private boolean cancelFreeResource = false;

	

	
	
	
	
	
	

	private void clearRescs() {
		this.getChartModel().removeListener(this);
		clearNativeSwingRescs();
		getData().clearSomeResource();
		this.removeAll();
		
	}

	private void clearNativeSwingRescs(){
		if (sumChartPane-countChartPane>2){
			JWebBrowser webBrowser = getData()==null ? null: getData().getWebBrowser();
			if(webBrowser != null){
				invokeLaterDisposeNativePeer(webBrowser);
			    //webBrowser.disposeNativePeer();
		    }
		}
		countChartPane--;
	}
	
	
	private void invokeLaterDisposeNativePeer(final JWebBrowser webBrowser){
		if(webBrowser == null)
			return ;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {	
				webBrowser.disposeNativePeer();
			}
		});
	}
	
	private void invokeLaterReCreateChart(){
		if (freeResourcedone) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {	
					reCreateChart();
				}
			});
			//AppDebug.debug("ChartPanel "+ID+" run invokeLaterReCreateChart() !!!!!!!!!!!! ");
		}
		
	}
	
	
	private void reCreateChart() {
		if (freeResourcedone) {
			try {
				// Thread.sleep(1000);
				ChartPanel.this.removeAll();
				ChartPanel.this.createChart(getChartDefine(),
						getChartModel());
				//AppDebug.debug("ChartPanel "+ID+" run reCreateChart() !!!!!!!!!!!!!!! ");
				reRender();
			} catch (ChartGenerateException e) {
				AppDebug.debug(e);
			} catch (ClassNotFoundException e) {
				AppDebug.debug(e);
			} catch (Exception e) {
				AppDebug.debug(e);
			} finally {
				freeResourcedone = false;
			}
		}
	}
	
	
	private void reRender(){
		oldWidth = 0;
		oldHeight = 0;
		onChartPanelResized();
	}
	
	
	
	
	@Override
	public void addNotify() {
		invokeLaterReCreateChart();
		//invokeLaterReCreateChart();
		super.addNotify();
		//AppDebug.debug("ChartPanel "+ID+" run addNotify() !!!!!!!!!!!!!! ");
	}

	
	
	@Override
	public void removeNotify() {
		freeResourcedone = true;
		clearRescs();
		super.removeNotify();
		//AppDebug.debug("ChartPanel "+ID+" run removeNotify() !!!!!!!!!!!!!!! ");
	}
	

	//static  int NoID = 0;
	//private int ID = 0;
	
	/**
	 * 构造器
	 */
	public ChartPanel() {
		if (nsException != null) {
			throw nsException;
		}
		//NoID++;
		//ID = NoID;
		getData().setServerRoot(ServerInfoUtil.getServerRootURL());
	}

	/**
	 * 构造器
	 */
	public ChartPanel(int width, int height) {
		super();
		this.setPreferredSize(new Dimension(width, height));
		if (nsException != null) {
			throw nsException;
		}
		//NoID++;
		//ID = NoID;
		getData().setServerRoot(ServerInfoUtil.getServerRootURL());
	}

	ChartPanelPrivateData getData() {
		return this.chartPanelPrivateData;
	}

	public ChartModel getChartModel() {
		if (getData() == null)
			return null;
		return getData().getModel();
	}

	public ChartDefine getChartDefine() {
		return getData().getChartDefine();
	}

	public void setChartModel(ChartModel chartModel)
			throws ChangeHandleException, ClassNotFoundException {

		if (getData().getModel() != null) {
			try {
				ChartModelListener[] listeners = getData().getModel()
						.getChangeListeners();
				getData().setModel(chartModel);
				getData().getModel().setChangeListeners(listeners);

				boolean hasRegisted = false;
				for (ChartModelListener ls : getData().getModel()
						.getChangeListeners()) {
					if (ls == this) {
						hasRegisted = true;
						break;
					}
				}
				if (!hasRegisted) {
					getData().getModel().addListener(this);
				}

				getData().getModel().fireChange();
			} catch (ChangeHandleException e) {
				AppDebug.debug(e);
				throw e;
			}
		}
	}

	public void setDisableSwitchImage(boolean disable) {
		if (isPanelValid()) {
			getData().setDisableSwitchImage(disable);
		}
	}

	/**
	 * 是否为图形模式
	 * 
	 * @return
	 */
	public boolean isImageModel() {
		return getData().isImageModel();
	}

	public boolean isPanelValid() {
		return this.getData() != null && this.getData().isInitialized();
	}

	/**
	 * 设置当前为图形模式
	 * 
	 * @param isImageMode
	 */
	public void setImageModel(boolean isImageMode) {
		if (getData() != null) {
			getData().setImageModel(isImageMode);

			if (getData().isInitialized()) {
				try {
					updateChartData();
				} catch (ChartGenerateException e) {
					AppDebug.debug(e);
				} catch (ClassNotFoundException e) {
					AppDebug.debug(e);
				}
			}
		}
	}

	public void disableSwitchImage(boolean disable) {
		getData().setDisableSwitchImage(disable);
	}

	public void setImageCreatedListener(
			ImageCreatedListener imageCreatedListener) {
		getData().setImageCreatedListener(imageCreatedListener);
	}

	public Image getChartImage() {
		return getData().getImage();
	}

	/**
	 * 生成统计图
	 * 
	 * @param define
	 * @param chartModel
	 * @throws ChartGenerateException
	 * @throws ClassNotFoundException
	 */
	public void createChart(ChartDefine define, ChartModel model)
			throws ChartGenerateException, ClassNotFoundException {
		long tStartTime = System.currentTimeMillis();
		checkNativeSwingInterface();
		if (getData().getModel() != null) {
			getData().getModel().removeListener(this);
		}
		getData().setChartDefine(define);
		getData().setModel(model);

		boolean hasRegisted = false;
		for (ChartModelListener ls : model.getChangeListeners()) {
			if (ls == this) {
				hasRegisted = true;
				break;
			}
		}
		if (!hasRegisted) {
			model.addListener(this);
		}

		if (getData().isInitialized()) {
			updateChartData();
			return;
		}
		// 创建统计图过程
		createChartPanels();

		registeComponentListener();

		registeGlobalEventListener();

		getData().getModel().setChange(false);

		AppDebug.debug("##Generate BQChart（Create）cost time："
				+ (System.currentTimeMillis() - tStartTime) + " ms.");
	}

	

	/**
	 * 生成统计图
	 * 
	 * @param implType
	 * @param chartDefineCode
	 * @param dataXML
	 * @param width
	 * @param height
	 * @throws ChartGenerateException
	 * @throws ClassNotFoundException
	 */
	private boolean createChartPanels() throws ChartGenerateException,
			ClassNotFoundException {
		if (this.getWidth() == 0) {
			return false;
		}

		this.setLayout(new BorderLayout());

		

		getData().setWaitPanel(
				new JLabel(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
						.getStrByID("0502004_0", "00502004-0059"),
						JLabel.CENTER));
		getData().setImgPanel(new ImagePanel());

		JPanel browserPanel = new JPanel(new BorderLayout()) {
			@Override
			public void removeNotify() {
				this.removeAll();
				super.removeNotify();
				
			}
		};

		getData().setBrowserPanel(browserPanel);

		createWebBrowser();

		if(getData().getBrowserPanel() == null){
			AppDebug.debug("ChartPanel  getData().getBrowserPanel() == null !!!!!!!!!!!!!!");
			return  false;
		}
			
		
		this.add(getData().getBrowserPanel(), BorderLayout.CENTER);

		this.add(getData().getWaitPanel());
		this.add(getData().getImgPanel());
		// this.add(new JLabel("Label  "+sumChartPane));

		switchPanelTo(PANEL_WAITLOADING);
		getData().setInitialized(true);

		return true;
	}

	
	
	
	private void createWebBrowser() {

		try {
			getData().setWebBrowser(
					new JWebBrowser(JWebBrowser.constrainVisibility(), 
							        JWebBrowser.destroyOnFinalization(),
							        JWebBrowser.proxyComponentHierarchy()) );
			countChartPane++;
			sumChartPane++;
			getData().getWebBrowser().setBarsVisible(false);
			getData().getWebBrowser().setStatusBarVisible(false);
			getData().getWebBrowser()
					.setSize(this.getWidth(), this.getHeight());
			getData().getWebBrowser().setJavascriptEnabled(true);
			getData().getWebBrowser().setRequestFocusEnabled(false);
			getData().getWebBrowser().setHTMLContent(generateHTML());
			getData().getWebBrowser().setPreferredSize(
					new Dimension(this.getWidth(), this.getHeight()));

			registeWebBrowserListener();
			// registeMouseListener();

			registeWebBrowserFunctions(getData().getWebBrowser());

			if(getData().getBrowserPanel() == null){
				AppDebug.debug("ChartPanel  getData().getBrowserPanel() == null !!!!!!!!!!!!!!!!");
				return ;
			}
				
			getData().getBrowserPanel().add(getData().getWebBrowser(),BorderLayout.CENTER);
		} catch (Exception e1) {
			AppDebug.debug(e1);
		}

	}

	private void registeComponentListener() {
		addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {
				// synchronized (this) {
				onChartPanelResized();
				// }
			}

		});
	}

	/*
	@Override
	public void repaint() {
		// 为了解决事件派发线程中 resized 事件监听不到时 通过repaint 方法来同步修改
		// 解决自由报表发布节点 图表出不来问题
		if (this.getWidth() != oldWidth || this.getHeight() != oldHeight) {
			onChartPanelResized();
		}

		super.repaint();
	}*/

	/**
	 * 当面板大小发生改变的时候，面板相应的大小调整事件
	 */
	private int oldWidth = 0, oldHeight = 0;

	private void onChartPanelResized() {

		//AppDebug.debug("ChartPanel "+ID+" run onChartPanelResized() !!!!!!!!!!!!!! ");
		
		if (getData() == null)
			return;
		if (!isPanelValid()) {

			try {
				createChartPanels();
			} catch (ChartGenerateException e1) {
				AppDebug.debug(e1);
			} catch (ClassNotFoundException e1) {
				AppDebug.debug(e1);
			}

		}

		if (getData().getBrowserPanel() != null) {
			if (!isImageModel()
					&& getData().isHasRendered()
					&& (Math.abs(this.getWidth() - oldWidth) > 20 || Math
							.abs(this.getHeight() - oldHeight) > 20)) {
				getData().getBrowserPanel().setSize(getWidth(), getHeight());
				getData().setComponentResized(true);
				getData().setHasRendered(false);
				oldWidth = this.getWidth();
				oldHeight = this.getHeight();
				updateChartSize();
				// AppDebug.debug("onChartPanelResized");
			} else if (isImageModel()) {
				getData().getBrowserPanel().setSize(getWidth(), getHeight());
				getData().setComponentResized(true);
			}
		}
	}

	/**
	 * 注册webBrowser事件，主要监听js脚本发出的调用事件
	 * 
	 * @param browser
	 * @return
	 */
	private Object registeWebBrowserFunctions(JWebBrowser browser) {
		// WebBrowserFunction onClickFunction = new WebBrowserFunction() {
		//
		// };
		browser.registerFunction(new OnClickFunction(this));
		browser.registerFunction(new OnRightClickFunction(this));
		// browser.registerFunction(new WebBrowserFunction("_onMouseOut") {
		// @Override
		// public Object invoke(final JWebBrowser webBrowser,
		// final Object... args) {
		//
		// Point pointOnScreen = MouseInfo.getPointerInfo().getLocation();
		// SwingUtilities.convertPointFromScreen(pointOnScreen,
		// getData().getWebBrowser().getParent());
		//
		// if (!getData().getWebBrowser().getBounds()
		// .contains(pointOnScreen) && getData().isHasRendered()) {
		// applyChartImageToPanel(true);
		// switchPanelTo(PANEL_IMAGECHART);
		// // AppDebug.debug("## onMouseOut");
		// doAfterImageCreated();
		// }
		//
		// return "";
		//
		// }
		// });
		browser.registerFunction(new OnChartRenderCompletedFunction(this));
		return browser;
	}

	/**
	 * 为WebBrowser添加监听器
	 */
	private void registeWebBrowserListener() {
		WebBrowserAdapter webBrowserListener = new WebBrowserListener(this);
		getData().getWebBrowser().addWebBrowserListener(webBrowserListener);

	}

	/**
	 * 获得HTML的内容
	 * 
	 * @param apiDefines
	 * @return
	 * @throws ChartGenerateException
	 * @throws ClassNotFoundException
	 */
	private String generateHTML() throws ChartGenerateException,
			ClassNotFoundException {
		long tBeginTime = System.currentTimeMillis();
		try {
			getData().setResultExecuteInfo(
					ChartUtil.generateDisplayData(getData().getChartDefine(),
							getData().getModel()));
		} catch (ClassNotFoundException e) {
			AppDebug.error(e);
			throw e;
		}

		// 设置执行结果缓存数据
		onBeforeChartXml(getData().getResultExecuteInfo());

		String dataXML = getData().getResultExecuteInfo().toChartDataXml()
				.toString();

		getData().getModel().putChartCacheData(ChartPropConst.KEY_CATEGORIES,
				getData().getResultExecuteInfo().getCategoryLabels());
		getData().getModel().putChartCacheData(ChartPropConst.KEY_DATAXML,
				dataXML);

		APIDefine[] apiDefines = getApiDefines();

		StringBuilder html = new StringBuilder();
		html.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><html>\n");
		html.append("<head>\n");
		html.append("<META HTTP-EQUIV=\"pragma\" CONTENT=\"no-cache\">");
		html.append("<META HTTP-EQUIV=\"Content-Type\" content=\"text/html;charset=utf-8\"/>\n");
		html.append("<META HTTP-EQUIV=\"Cache-Control\" CONTENT=\"no-cache, must-revalidate\">");
		html.append("<META HTTP-EQUIV=\"expires\" CONTENT=\"0\">\n");
		html.append("<SCRIPT LANGUAGE='Javascript' SRC='");
		html.append(getData().getServerRoot()).append(
				"/javascripts/bqCharts.js'></SCRIPT>\n");
		html.append("<SCRIPT LANGUAGE='Javascript' SRC='")
				.append(getData().getServerRoot())
				.append("/fusioncharts/FusionCharts.js'></SCRIPT>\n");
		html.append("<SCRIPT LANGUAGE='Javascript' SRC='")
				.append(getData().getServerRoot()).append("/chartDefines/");
		html.append(getData().getChartDefine().getCode())
				.append("/fusionCharts/")
				.append(getData().getChartDefine().getCode())
				.append(".js'></SCRIPT>\n");
		html.append("<script language='javascript'>\n");
		// 设置根路径
		html.append("setServerRoot(\"").append(getData().getServerRoot())
				.append("\");\n");
		html.append("var chartDefine = new ChartDefine('")
				.append(getData().getChartDefine().getCode()).append("');");
		for (int i = 0; apiDefines != null && i < apiDefines.length; i++) {
			html.append("chartDefine.addAPIParameterTypesMapping(\"")
					.append(apiDefines[i].getCode()).append("\"");
			Input[] inputs = apiDefines[i].getInputs();
			if (inputs != null && inputs.length > 0) {
				html.append(",[");
			}
			for (int j = 0; j < inputs.length; j++) {
				html.append("\"").append(inputs[i].getCode()).append("\",");
			}

			if (html.charAt(html.length() - 1) == ',') {
				html.delete(html.length() - 1, html.length());
			}

			if (inputs != null && inputs.length > 0) {
				html.append("]");
			}

			html.append(");\n");
		}

		html.append("addChartDefine(chartDefine);\n");
		html.append("</script>\n");
		html.append("</head>\n");
		html.append("<body bgcolor=\"#ffffff\" style='overflow:hidden;padding:0;margin:0;'");
		html.append("onMouseDown='_onMouseDown()' onMouseUp='_onMouseUp()'>\n");
		html.append("<div id='chartdiv' style='margin:0;padding:0;display:absolute;top:0;left:0;");
		html.append("width:").append(this.getWidth()).append(";height:")
				.append(this.getHeight()).append("' align=\"left\">\n");
		html.append("</div>\n");
		html.append("</body>\n");
		html.append("<script language='javascript'>\n");
		// 创建图
		html.append("var data = \"").append(dataXML).append("\"\n");
		html.append("var bqChart = createBQChart(\"")
				.append(getData().getChartImplType().getCode()).append("\",\"")
				.append(getData().getChartDefine().getCode()).append("\"");
		html.append(",\"chartdiv\",data,").append(this.getWidth()).append(",")
				.append(this.getHeight()).append(");\n");
		// if (!isRunTimeMode() &&
		// ChartUtil.isEmptyDatasets(getData().getModel().getDatasets())){
		// html.append("var bqCaptionLabel = bqChart.getRawChartObj().getChartAttribute (\"caption\");\n");
		// html.append("bqChart.getRawChartObj().setChartAttribute (\"caption\", bqCaptionLabel +\"(空数据集显示)\");\n");
		// }
		html.append("</script>\n");
		html.append("<SCRIPT LANGUAGE='Javascript' SRC='");
		html.append(getData().getServerRoot()).append(
				"/javascripts/swEmbedded.js'></SCRIPT>\n");
		html.append("</html>");
		AppDebug.debug("## generateHTML cost time:"
				+ (System.currentTimeMillis() - tBeginTime) + "ms.");
		return html.toString();
	}

	/**
	 * 将FusionCharts导出为图片到面板
	 * 
	 * @param card
	 * @param webBrowser
	 */
	void applyChartImageToPanel(boolean force) {
		if (!isPanelValid()) {
			return;
		}
		if (force) {
			Image image = generateChartToImage();
			getData().setImage(image);
			getData().getImgPanel().setImage(image);
		} else {
			getData().getImgPanel().setImage(getData().getImage());
		}
	}

	/**
	 * 执行导出图片
	 * 
	 * @return
	 */
	Image generateChartToImage() {
		NativeComponent nativeComponent = getData().getWebBrowser()
				.getNativeComponent();
		Dimension originalSize = nativeComponent.getSize();
		Dimension imageSize = new Dimension(this.getWidth(), this.getHeight());
		nativeComponent.setSize(imageSize);
		BufferedImage image = new BufferedImage(imageSize.width,
				imageSize.height, BufferedImage.TYPE_INT_RGB);
		nativeComponent.paintComponent(image);
		nativeComponent.setSize(originalSize);
		image.setRGB(1, 1, Color.BLUE.getRGB());
		return image;
	}

	/**
	 * 当相关值改变时，（数据绑定改变）， 删除ChartDefine中relativeChange为true 的属性+属性组
	 */
	private void cleanRelativePropertyChanged() {

		ChartDefine cdefine = getData().getChartDefine();
		PropertyGroup[] propertyGroups = getData().getModel()
				.getPropertyGroups();

		// delete PropertyGroup
		PropertyGroup group = null;
		for (int i = propertyGroups.length - 1; i >= 0; i--) {

			group = propertyGroups[i];
			if (group == null)
				continue;

			if (cdefine.isRelativePropertyGroup(group.getRefCode())) {
				getData().getModel().removePropertyGroup(group);
				continue;
			}

			// delete property of propertyGroup
			Property prop = null;
			for (int t = group.getProperties().size() - 1; t >= 0; t--) {
				prop = group.getProperties().get(t);
				if (prop == null)
					continue;

				if (cdefine.isRelativeProperty(group.getRefCode(),
						prop.getRefCode())) {
					group.getProperties().remove(t);
				}
			}
		}
	}

	/*
	 * 检查是否有DJNativeSwing接口打开的异常
	 */
	private void checkNativeSwingInterface() {
		if (nsException != null) {
			throw nsException;
		}
	}

	/**
	 * 更新图的数据
	 * 
	 * @throws ChartGenerateException
	 * @throws ClassNotFoundException
	 */

	private void updateChartData() throws ChartGenerateException,
			ClassNotFoundException {
		long tStartTime = System.currentTimeMillis();
		if (getData().getWebBrowser() == null) {
			createChartPanels();
			return;
		} else {
			// 清空相关属性的修改

			if (getData().getModel().isDatasetChanged()) {
				this.cleanRelativePropertyChanged();
			}

			// boolean isfaceChanged = true;
			List<PropertyGroup> changedGroups = getData().getModel()
					.getChangedGroups();
			for (PropertyGroup group : changedGroups) {
				String className = group.getClass().getName();
				if (group.isChanged()) {
					if (!className.equals("PropertyGroup")
							&& !className.endsWith(".PropertyGroup")) {
						// isfaceChanged &= false;
					}
				}
			}
			IChartDataBinding[] changedGroups1 = getData().getModel()
					.getDataBindings();
			for (IChartDataBinding group : changedGroups1) {
				// isfaceChanged &= !group.isChanged();
			}
			// if (
			// ((AbstractModelElement<PropertyGroup>)this.model.getPropertyGroups()).isChanged()
			// ){
			// this.doPropertyRelativeChange();
			// }

			// if(isImageModel){
			// switchPanelTo(PANEL_FUSIONCHART);
			// }

			try {
				// if (isfaceChanged){
				//
				// StringBuilder chartXml = new StringBuilder ();
				//
				// String tmpChart =
				// ChartUtil.generateChartFaceData(chartDefine,
				// model).dataXml;//(chartDefine, model, false);
				// int endPosition = tmpChart.indexOf(">");
				// chartXml.append(tmpChart.substring(0, endPosition+1));
				// endPosition = resultExecuteInfo.dataXml.indexOf(">");
				//
				// chartXml.append(resultExecuteInfo.dataXml.substring(endPosition+1,
				// resultExecuteInfo.dataXml.length()));
				// resultExecuteInfo.dataXml = chartXml.toString();
				// }
				// else
				getData()
						.setResultExecuteInfo(
								ChartUtil.generateDisplayData(getData()
										.getChartDefine(),
										getData().getModel(), false));
				onBeforeChartXml(getData().getResultExecuteInfo());
				String dataXml = getData().getResultExecuteInfo()
						.toChartDataXml().toString();
				getData().getModel().putChartCacheData(
						ChartPropConst.KEY_CATEGORIES,
						getData().getResultExecuteInfo().getCategoryLabels());
				getData().getModel().putChartCacheData(
						ChartPropConst.KEY_DATAXML, dataXml);
				getData().getWebBrowser().executeJavascript(
						"updateData('" + getData().getChartImplType() + "','"
								+ getData().getChartDefine().getCode() + "',\""
								+ dataXml + "\"," + getWidth() + ","
								+ getHeight() + ")");

				AppDebug.debug("## UpdateChartData cost time: "
						+ (System.currentTimeMillis() - tStartTime) + "ms.");
			} catch (ClassNotFoundException e) {

				AppDebug.error(e);
			}
			getData().getModel().setChange(false);
		}
	}

	/**
	 * 更新图的大小
	 * 
	 * @throws ChartGenerateException
	 */
	public void updateChartSize() {
		// 面板没有初始化
		if (!isPanelValid()) {
			return;
		}

		if (isImageModel()) {
			switchPanelTo(PANEL_IMAGECHART);
		}

		getData().getWebBrowser().executeJavascript(
				"updateSize(" + getWidth() + "," + getHeight() + ")");

		if (!updateChartSizeRenderisOk) {
			getData().getWebBrowser().executeJavascript(
					"getChartFromId(\"chartdiv\").render(\"chartdiv\")");
		}

		updateChartSizeRenderisOk = false;
		// getData().getWebBrowser().executeJavascript("getChartFromId(\"chartdiv\").resizeTo("+getWidth()
		// + "," + getHeight()+")");

		getData().setComponentResized(false);

	}

	/**
	 * 表示改变图表大小，Flash渲染成功
	 */
	public void updateChartSizeRenderisOk() {
		updateChartSizeRenderisOk = true;
	}

	private boolean updateChartSizeRenderisOk = true;

	private boolean hasFlashRendered() {
		Object objRet = getData().getWebBrowser().executeJavascriptWithResult(
				"return getChartFromId(\"chartdiv\").hasRendered();");
		if (objRet != null) {
			boolean nAlpha = Boolean.parseBoolean(objRet.toString());
			return nAlpha;
		}
		return true;
	}

	/**
	 * 获取分类轴上的标签数组，可以用这个选中的值作为用户自定义分组线
	 * 
	 * @return
	 */
	public String[] getCategoryAxisData() {
		return getData().getResultExecuteInfo().getCategoryLabels();
	}

	/**
	 * 用于在图片和Browser之间互相切换
	 * 
	 * @param name
	 */
	void switchPanelTo(String name) {

		if (ServerInfoUtil.isLocalModel())
			return;
		if (getData() == null)
			return;
		if (!getData().isInitialized())
			return;

		if (getData().isDisableSwitchImage()) {
			return;
		}
		SwitchPanelRunner switchPanelRunner = new SwitchPanelRunner(this, name);
		switchPanelRunner.dorun();
		//SwingUtilities.invokeLater(switchPanelRunner);
	}

	void checkImageAndGen() {
		Image image = getData().getImage();
		if (null != image && image instanceof BufferedImage) {
			BufferedImage bi = (BufferedImage) image;
			if (bi.getRGB(1, 1) != Color.BLUE.getRGB()) {
				applyChartImageToPanel(true);
			}
		} else if (null == image) {
			applyChartImageToPanel(true);
		}
	}

	/**
	 * 获取api接口函数的定义
	 * 
	 * @return
	 */
	private APIDefine[] getApiDefines() {
		// APIDefine[] defines = new APIDefine[2];
		// Input[] inputs = new Input[2];
		// inputs[0] = new Input("name", "名称", String.class.getName());
		// inputs[1] = new Input("name", "值", String.class.getName());
		//
		// defines[0] = new APIDefine("说Hello", "sayHello", inputs, null);
		//
		// inputs = new Input[2];
		// inputs[0] = new Input("wang", "汪汪叫", String.class.getName());
		// inputs[1] = new Input("miao", "喵喵叫", String.class.getName());
		//
		// defines[1] = new APIDefine("动物叫", "bark", inputs, null);

		return null;
	}

	/**
	 * 调用API
	 * 
	 * @param apiName
	 * @param paras
	 * @return
	 */
	public Object callAPI(String apiName, Object[] paras) {
		return null;
	}

	@Override
	public void onChange(ChartModel chartModel) throws ChangeHandleException,
			ClassNotFoundException {
		try {

			updateChartData();

		} catch (ChartGenerateException e) {
			throw new ChangeHandleException(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
							"0502004_0", "00502004-0060")/* @res "统计图处理失败！" */,
					e);
		}
	}

	/**
	 * 浏览器单击
	 * 
	 * @param args
	 */

	void callSWLeftClick(int x, int y, String data) {
		final int cx = x;
		final int cy = y;
		final String jsonData = data;
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// String data = "";
				// setChartCaption("AAAA") ;
				// data = getChartCaption() ;
				// setChartSubCaption("BBBB") ;
				// data = getChartSubCaption();
				//
				// setChartBgColor("FF0000");
				// data = getChartBgColor() ;
				//
				// setChartBgAlpha(5) ;
				// int n = getChartBgAlpha() ;
				//
				// setChartBorderColor("00FF00") ;
				// data = getChartBorderColor() ;
				//
				// setChartBorderWidth(3);
				// n = getChartBorderWidth() ;
				//
				// setChartBorderAlpha(30);
				// n = getChartBorderAlpha();
				//
				// setCanvasBgColor("0000FF");
				// data = getCanvasBgColor();
				//
				// setCanvasBgAlpha(50);
				// n = getCanvasBgAlpha();
				//
				// setCanvasBorderColor("ff00ff");
				// data = getCanvasBorderColor();
				//
				// setCanvasBorderWidth(3) ;
				// n = getCanvasBorderWidth() ;
				//
				// setCanvasBorderAlpha(60);
				// n = getCanvasBorderAlpha();
				//
				// setShowLegend(false);
				// boolean b = isShowLegend();
				//
				// setLegendPosition("LEFT");
				// data = getLegendPosition();
				//
				// setInteractiveLegend(true);
				// b = isInteractiveLegend();
				//
				// setHGridLineNums(8);
				// b = isShowHGridLine() ;
				//
				// n = getHGridLineNums();
				// b = isDashedHGridLine();
				// setDashedHGridLine(true);
				// b = isShowVGridLine() ;
				// setVGridLineNums(3);
				// n = getVGridLineNums();
				// b = isDashedVGridLine();
				// setDashedVGridLine(true);
				//
				onClick(cx, cy, jsonData);
			}
		});
	}

	/**
	 * 浏览器右击
	 * 
	 * @param args
	 */
	void callSWRightClick(int x, int y, String data) {
		final int cx = x;
		final int cy = y;
		final String jsonData = data;

		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			AppDebug.debug(e);
		}

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// switchPanel(PANEL_FUSIONCHART);
				onRightClick(cx, cy, jsonData);
			}
		});
	}

	/**
	 * 鼠标左击事件
	 * 
	 * @param x
	 * @param y
	 * @param keyValuePairList
	 *            形如：{系列/轴定义编码}={系列/轴的值}
	 */
	protected void onClick(int x, int y, String jsonData) {
		//createWebBrowser();

	}

	/**
	 * 鼠标右击事件
	 * 
	 * @param x
	 * @param y
	 * @param parameters
	 *            形如：{系列/轴定义编码}={系列/轴的值}
	 */
	protected void onRightClick(int x, int y, String jsonData) {
	}

	protected void onBeforeChartXml(DataGenerateResult resultExecuteInfo) {
		/** 处理<chart>节点数据 */
		StringBuilder sb = resultExecuteInfo
				.getChartBlock(DataGenerateResult.BQ_BLOCK_CHART);
		/** 图片模式下去掉动画效果 */
		if (this.isImageModel()) {

			if (sb.length() != 0) {
				// 处理取消动画
				int pos = sb.indexOf("animation");
				if (-1 != pos) {
					pos = sb.indexOf("'", pos);
					sb.deleteCharAt(pos + 1);
					sb.insert(pos + 1, "0");
				} else {
					sb.insert(sb.length() - 1, " animation='0'");
				}

			}
		}
		/** 获取caption */
		int pos = sb.indexOf("caption");
		if (-1 != pos) {
			int pos0 = pos = sb.indexOf("'", pos);
			int pos1 = sb.indexOf("'", pos0 + 1);
			String caption = sb.substring(pos0, pos1);
			// AppDebug.debug("*** " + caption);
			if (caption != null)
				getData().getModel().putChartCacheData(
						ChartPropConst.KEY_FUSIONCHART_CAPTION, caption);
		}
	}

	/**
	 * 注册监听器
	 * 
	 * @param listener
	 */
	public void registerListener(ChartModelListener listener) {
		getData().getListenerList().add(listener);
	}

	private void registeGlobalEventListener() {
		getData().setGlobalAWTEventListener(new GlobalEventListener(this));
		Toolkit.getDefaultToolkit().addAWTEventListener(
				getData().getGlobalAWTEventListener(),
				AWTEvent.MOUSE_WHEEL_EVENT_MASK
						| MouseEvent.MOUSE_MOTION_EVENT_MASK
						| MouseEvent.MOUSE_EVENT_MASK);
	}

	/**
	 * 注销监听器
	 * 
	 * @param listener
	 */
	public void removeListener(ChartModelListener listener) {
		getData().getListenerList().remove(listener);
	}

	/**
	 * 添加图片生成事件
	 * 
	 * @param imageCreatedListener
	 */
	public void addImageCreatedListener(
			ImageCreatedListener imageCreatedListener) {
		this.getData().setImageCreatedListener(imageCreatedListener);
	}

	/**
	 * 获取缓存图片
	 * 
	 * @param width
	 * @param height
	 * @return
	 */
	public Image getChartImage(int width, int height) {

		return this.getData().getImage();// .getImgWrapper(width, height);
	}

	protected static class GlobalEventListener implements AWTEventListener {
		private WeakReference<ChartPanel> panel = null;

		public GlobalEventListener(ChartPanel chartPanel) {
			panel = new WeakReference<ChartPanel>(chartPanel);
		}

		@Override
		public void eventDispatched(AWTEvent event) {

			if (event instanceof MouseEvent) {
				MouseEvent mEvent = (MouseEvent) event;

				if (panel == null || panel.get() == null
						|| panel.get().getData() == null) {
					Toolkit.getDefaultToolkit().removeAWTEventListener(this);
					return;
				}

				if (!panel.get().isImageModel())
					return;

				// if (mEvent.getID() == MouseWheelEvent.MOUSE_WHEEL) {
				//
				// Point point = MouseInfo.getPointerInfo()
				// .getLocation();// mEvent.getPoint();
				// SwingUtilities.convertPointFromScreen(
				// point, panel.get());
				// // if
				// // (!ChartPanel.this.getBounds().contains(point))
				// {
				// panel.get().switchPanelTo(PANEL_IMAGECHART);
				// }
				//
				// } else
				// if (mEvent.getID() == MouseEvent.MOUSE_DRAGGED) {
				// AppDebug.debug("MOUSE_DRAGGED: " + mEvent
				// .getSource()
				// .getClass()
				// .getName());
				//
				// } else
				if (mEvent.getID() == MouseEvent.MOUSE_RELEASED) {
					AppDebug.debug("MOUSE_RELEASED: "
							+ mEvent.getSource().getClass().getName());

					if (mEvent.getButton() == MouseEvent.BUTTON1
							&& panel.get().getData().isComponentResized()) {

						panel.get().updateChartSize();
					}
				}
			}
		}

	}

	/********************************************************************************************************/

	@Override
	public String getChartCaption() {

		Object objRet = getData()
				.getWebBrowser()
				.executeJavascriptWithResult(
						"return getChartFromId(\"chartdiv\").getChartAttribute (\"caption\");");
		if (objRet != null)
			return objRet.toString();
		else
			return null;
	}

	@Override
	public void setChartCaption(String chartCaption) {
		getData().getWebBrowser().executeJavascriptWithResult(
				"getChartFromId(\"chartdiv\").setChartAttribute (\"caption\",\""
						+ chartCaption + "\");");
	}

	@Override
	public String getChartSubCaption() {
		Object objRet = getData()
				.getWebBrowser()
				.executeJavascriptWithResult(
						"return getChartFromId(\"chartdiv\").getChartAttribute (\"subCaption\");");
		if (objRet != null)
			return objRet.toString();
		else
			return null;
	}

	@Override
	public void setChartSubCaption(String chartSubCaption) {
		getData().getWebBrowser().executeJavascriptWithResult(
				"getChartFromId(\"chartdiv\").setChartAttribute (\"subCaption\",\""
						+ chartSubCaption + "\");");
	}

	@Override
	public String getChartBgColor() {
		Object objRet = getData()
				.getWebBrowser()
				.executeJavascriptWithResult(
						"return getChartFromId(\"chartdiv\").getChartAttribute (\"bgColor\");");
		if (objRet != null)
			return objRet.toString();
		else
			return null;
	}

	@Override
	public void setChartBgColor(String chartBgColor) {
		getData().getWebBrowser().executeJavascriptWithResult(
				"getChartFromId(\"chartdiv\").setChartAttribute (\"bgColor\",\""
						+ chartBgColor + "\");");
	}

	@Override
	public int getChartBgAlpha() {
		Object objRet = getData()
				.getWebBrowser()
				.executeJavascriptWithResult(
						"return getChartFromId(\"chartdiv\").getChartAttribute (\"bgAlpha\");");
		if (objRet != null) {
			int nAlpha = Integer.parseInt((String) objRet);
			return nAlpha;
		}

		return -1;
	}

	@Override
	public void setChartBgAlpha(int chartBgAlpha) {
		getData().getWebBrowser().executeJavascriptWithResult(
				"getChartFromId(\"chartdiv\").setChartAttribute (\"bgAlpha\",\""
						+ chartBgAlpha + "\");");
	}

	@Override
	public String getChartBorderColor() {
		Object objRet = getData()
				.getWebBrowser()
				.executeJavascriptWithResult(
						"return getChartFromId(\"chartdiv\").getChartAttribute (\"borderColor\");");
		if (objRet != null)
			return objRet.toString();
		else
			return null;
	}

	@Override
	public void setChartBorderColor(String chartBorderColor) {
		getData().getWebBrowser().executeJavascriptWithResult(
				"getChartFromId(\"chartdiv\").setChartAttribute (\"borderColor\",\""
						+ chartBorderColor + "\");");
	}

	@Override
	public int getChartBorderWidth() {
		Object objRet = getData()
				.getWebBrowser()
				.executeJavascriptWithResult(
						"return getChartFromId(\"chartdiv\").getChartAttribute (\"borderThickness\");");
		if (objRet != null) {
			int nAlpha = Integer.parseInt((String) objRet);
			return nAlpha;
		}

		return -1;
	}

	@Override
	public void setChartBorderWidth(int chartBorderWidth) {
		getData().getWebBrowser().executeJavascriptWithResult(
				"getChartFromId(\"chartdiv\").setChartAttribute (\"borderThickness\",\""
						+ chartBorderWidth + "\");");
	}

	@Override
	public int getChartBorderAlpha() {
		Object objRet = getData()
				.getWebBrowser()
				.executeJavascriptWithResult(
						"return getChartFromId(\"chartdiv\").getChartAttribute (\"borderAlpha\");");
		if (objRet != null) {
			int nAlpha = Integer.parseInt((String) objRet);
			return nAlpha;
		}

		return -1;
	}

	@Override
	public void setChartBorderAlpha(int chartBorderAlpha) {
		getData().getWebBrowser().executeJavascriptWithResult(
				"getChartFromId(\"chartdiv\").setChartAttribute (\"borderAlpha\",\""
						+ chartBorderAlpha + "\");");
	}

	@Override
	public String getCanvasBgColor() {
		Object objRet = getData()
				.getWebBrowser()
				.executeJavascriptWithResult(
						"return getChartFromId(\"chartdiv\").getChartAttribute (\"canvasbgColor\");");
		if (objRet != null)
			return objRet.toString();
		else
			return null;
	}

	@Override
	public void setCanvasBgColor(String canvasBgColor) {
		getData().getWebBrowser().executeJavascriptWithResult(
				"getChartFromId(\"chartdiv\").setChartAttribute (\"canvasbgColor\",\""
						+ canvasBgColor + "\");");
	}

	@Override
	public int getCanvasBgAlpha() {
		Object objRet = getData()
				.getWebBrowser()
				.executeJavascriptWithResult(
						"return getChartFromId(\"chartdiv\").getChartAttribute (\"canvasbgAlpha\");");
		if (objRet != null) {
			int nAlpha = Integer.parseInt((String) objRet);
			return nAlpha;
		}

		return -1;
	}

	@Override
	public void setCanvasBgAlpha(int canvasBgAlpha) {
		getData().getWebBrowser().executeJavascriptWithResult(
				"getChartFromId(\"chartdiv\").setChartAttribute (\"canvasbgAlpha\",\""
						+ canvasBgAlpha + "\");");
	}

	@Override
	public String getCanvasBorderColor() {
		Object objRet = getData()
				.getWebBrowser()
				.executeJavascriptWithResult(
						"return getChartFromId(\"chartdiv\").getChartAttribute (\"canvasBorderColor\");");
		if (objRet != null)
			return objRet.toString();
		else
			return null;
	}

	@Override
	public void setCanvasBorderColor(String canvasBorderColor) {
		getData().getWebBrowser().executeJavascriptWithResult(
				"getChartFromId(\"chartdiv\").setChartAttribute (\"canvasBorderColor\",\""
						+ canvasBorderColor + "\");");
	}

	@Override
	public int getCanvasBorderWidth() {
		Object objRet = getData()
				.getWebBrowser()
				.executeJavascriptWithResult(
						"return getChartFromId(\"chartdiv\").getChartAttribute (\"canvasBorderThickness\");");
		if (objRet != null) {
			int nAlpha = Integer.parseInt((String) objRet);
			return nAlpha;
		}

		return -1;
	}

	@Override
	public void setCanvasBorderWidth(int canvasBorderWidth) {
		getData().getWebBrowser().executeJavascriptWithResult(
				"getChartFromId(\"chartdiv\").setChartAttribute (\"canvasBorderThickness\",\""
						+ canvasBorderWidth + "\");");
	}

	@Override
	public int getCanvasBorderAlpha() {
		Object objRet = getData()
				.getWebBrowser()
				.executeJavascriptWithResult(
						"return getChartFromId(\"chartdiv\").getChartAttribute (\"canvasBorderAlpha\");");
		if (objRet != null) {
			int nAlpha = Integer.parseInt((String) objRet);
			return nAlpha;
		}

		return -1;
	}

	@Override
	public void setCanvasBorderAlpha(int canvasBorderAlpha) {
		getData().getWebBrowser().executeJavascriptWithResult(
				"getChartFromId(\"chartdiv\").setChartAttribute (\"canvasBorderAlpha\",\""
						+ canvasBorderAlpha + "\");");
	}

	@Override
	public boolean isShowLegend() {
		Object objRet = getData()
				.getWebBrowser()
				.executeJavascriptWithResult(
						"return getChartFromId(\"chartdiv\").getChartAttribute (\"showLegend\");");
		if (objRet != null) {
			boolean nAlpha = Boolean.parseBoolean((String) objRet);
			return nAlpha;
		}

		return false;
	}

	@Override
	public void setShowLegend(boolean showLegend) {
		getData().getWebBrowser().executeJavascriptWithResult(
				"getChartFromId(\"chartdiv\").setChartAttribute (\"showLegend\",\""
						+ (showLegend ? "1" : "0") + "\");");
	}

	@Override
	public String getLegendPosition() {
		Object objRet = getData()
				.getWebBrowser()
				.executeJavascriptWithResult(
						"return getChartFromId(\"chartdiv\").getChartAttribute (\"legendPosition\");");
		if (objRet != null)
			return objRet.toString();
		else
			return null;
	}

	@Override
	public void setLegendPosition(String legendPosition) {
		getData().getWebBrowser().executeJavascriptWithResult(
				"getChartFromId(\"chartdiv\").setChartAttribute (\"legendPosition\",\""
						+ legendPosition + "\");");
	}

	@Override
	public boolean isInteractiveLegend() {
		Object objRet = getData()
				.getWebBrowser()
				.executeJavascriptWithResult(
						"return getChartFromId(\"chartdiv\").getChartAttribute (\"interactiveLegend\");");
		if (objRet != null) {
			boolean nAlpha = Boolean.parseBoolean((String) objRet)
					|| objRet.toString().equals("1");
			return nAlpha;
		}

		return false;
	}

	@Override
	public void setInteractiveLegend(boolean interactiveLegend) {
		getData().getWebBrowser().executeJavascriptWithResult(
				"getChartFromId(\"chartdiv\").setChartAttribute (\"interactiveLegend\",\""
						+ (interactiveLegend ? "1" : "0") + "\");");
	}

	@Override
	public boolean isShowHGridLine() {
		Object objRet = getData()
				.getWebBrowser()
				.executeJavascriptWithResult(
						"return getChartFromId(\"chartdiv\").getChartAttribute (\"numDivLines\");");
		if (objRet != null) {
			if (!objRet.toString().equals("0"))
				return true;
		}

		return false;
	}

	@Override
	public void setHGridLineNums(int showGridLine) {
		getData().getWebBrowser().executeJavascriptWithResult(
				"getChartFromId(\"chartdiv\").setChartAttribute (\"numDivLines\",\""
						+ showGridLine + "\");");
	}

	@Override
	public int getHGridLineNums() {
		Object objRet = getData()
				.getWebBrowser()
				.executeJavascriptWithResult(
						"return getChartFromId(\"chartdiv\").getChartAttribute (\"numDivLines\");");
		if (objRet != null) {
			int nAlpha = Integer.parseInt((String) objRet);
			return nAlpha;
		}

		return -1;
	}

	@Override
	public boolean isDashedHGridLine() {
		Object objRet = getData()
				.getWebBrowser()
				.executeJavascriptWithResult(
						"return getChartFromId(\"chartdiv\").getChartAttribute (\"divLineIsDashed\");");
		if (objRet != null) {
			boolean nAlpha = Boolean.parseBoolean((String) objRet)
					|| objRet.toString().equals("1");
			return nAlpha;
		}

		return false;
	}

	@Override
	public void setDashedHGridLine(boolean dashedGridLine) {
		getData().getWebBrowser().executeJavascriptWithResult(
				"getChartFromId(\"chartdiv\").setChartAttribute (\"divLineIsDashed\",\""
						+ (dashedGridLine ? "1" : "0") + "\");");
	}

	@Override
	public boolean isShowVGridLine() {
		Object objRet = getData()
				.getWebBrowser()
				.executeJavascriptWithResult(
						"return getChartFromId(\"chartdiv\").getChartAttribute (\"numVDivLines\");");
		if (objRet != null) {
			if (!objRet.toString().equals("0"))
				return true;
		}

		return false;
	}

	@Override
	public void setVGridLineNums(int showGridLine) {
		getData().getWebBrowser().executeJavascriptWithResult(
				"getChartFromId(\"chartdiv\").setChartAttribute (\"numVDivLines\",\""
						+ showGridLine + "\");");
	}

	@Override
	public int getVGridLineNums() {
		Object objRet = getData()
				.getWebBrowser()
				.executeJavascriptWithResult(
						"return getChartFromId(\"chartdiv\").getChartAttribute (\"numVDivLines\");");
		if (objRet != null) {
			int nAlpha = Integer.parseInt((String) objRet);
			return nAlpha;
		}

		return -1;
	}

	@Override
	public boolean isDashedVGridLine() {
		Object objRet = getData()
				.getWebBrowser()
				.executeJavascriptWithResult(
						"return getChartFromId(\"chartdiv\").getChartAttribute (\"vDivLineIsDashed\");");
		if (objRet != null) {
			boolean nAlpha = Boolean.parseBoolean((String) objRet)
					|| objRet.toString().equals("1");
			return nAlpha;
		}

		return false;
	}

	@Override
	public void setDashedVGridLine(boolean dashedGridLine) {
		getData().getWebBrowser().executeJavascriptWithResult(
				"getChartFromId(\"chartdiv\").setChartAttribute (\"vDivLineIsDashed\",\""
						+ (dashedGridLine ? "1" : "0") + "\");");
	}

}