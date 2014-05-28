package uap.bq.chart.component;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JPanel;

import uap.bq.chart.ChartPropConst;
import uap.bq.chart.generator.ChartGenerateException;
import uap.bq.chart.generator.DataGenerateResult;
import uap.util.bq.chart.ChartUtil;
import uap.util.bq.chart.ServerInfoUtil;
import uap.vo.bq.chart.define.ChartDefine;
import uap.vo.bq.chart.model.ChangeHandleException;
import uap.vo.bq.chart.model.ChartImplType;
import uap.vo.bq.chart.model.ChartModel;
import uap.vo.bq.chart.model.ChartModelListener;
import uap.vo.bq.chart.model.ImageCreatedListener;
import uap.vo.bq.chart.model.ImageCreatedListener.ChartImageEvent;
import chrriis.dj.nativeswing.swtimpl.NativeComponent;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserAdapter;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserFunction;

import com.ufida.iufo.pub.tools.AppDebug;

public class FusionImageChartPanel extends JPanel{
	private static final long serialVersionUID = 1L;

	private static NativeSwingException nsException = null;

	private ChartPanelPrivateData chartPanelPrivateData = new ChartPanelPrivateData();

	/**
	 * 确保打开DJNativeSwing本地接口
	 */
	static {
		try {
			NativeSwingHelper.openNativeInterface();
		} catch (IOException e) {
			nsException = new NativeSwingException("open DJNativeSwing error!", e);
			throw nsException;

		}
	}

	@Override
	protected void finalize() throws Throwable {
		getData().getWebBrowser().disposeNativePeer();
		getData().clear();
		super.finalize();

	}

	/***
	 * 面板私有数据类
	 * 
	 * @author wangqzh
	 * 
	 */
	static class ChartPanelPrivateData {
		private boolean initialized = false;
		
		/* 服务器根，用于JS引用等 */
		private String serverRoot = null;
		/* 当前ChartModel */
		private ChartModel model = null;
		/* 统计图定义对象 */
		private ChartDefine chartDefine = null;
		/* 用到的WebBrowser对象 */
		private JWebBrowser webBrowser = null;
		/* 统计图实现类型 */
		private ChartImplType chartImplType = ChartImplType.FUSIONCHARTS;
		/* 数据转化完成之后执行结果数据 */
		private DataGenerateResult resultExecuteInfo = null;

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


		public ChartImplType getChartImplType() {
			return chartImplType;
		}

		public void setChartImplType(ChartImplType chartImplType) {
			this.chartImplType = chartImplType;
		}

		
		public DataGenerateResult getResultExecuteInfo() {
			return resultExecuteInfo;
		}

		public void setResultExecuteInfo(DataGenerateResult resultExecuteInfo) {
			this.resultExecuteInfo = resultExecuteInfo;
		}

		public void clear() {
			initialized = false;	
			serverRoot = null;
			model = null;
			chartDefine = null;
			webBrowser = null;
			resultExecuteInfo = null;
			imageCreatedListener = null;
		}

	}

	/**
	 * 构造器
	 */
	public FusionImageChartPanel(int width, int height) {
		if (nsException != null) {
			throw nsException;
		}
		this.setSize(width, height);
		this.setPreferredSize(new Dimension(width, height));
		getData().setServerRoot(ServerInfoUtil.getServerRootURL());
	}
	

	private ChartPanelPrivateData getData() {
		return this.chartPanelPrivateData;
	}

	public ChartModel getChartModel() {
		return getData().getModel();
	}
	
	public ChartDefine getChartDefine(){
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
				

				getData().getModel().fireChange();
			} catch (ChangeHandleException e) {
				AppDebug.debug(e);
				throw e;
			}
		}
	}


	public boolean isPanelValid() {
		return this.getData() != null && this.getData().isInitialized();
	}



	
	public void setImageCreatedListener (
			ImageCreatedListener imageCreatedListener){
		getData().setImageCreatedListener(imageCreatedListener);
	}
	
	public Image getChartImage(){
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
		
		checkNativeSwingInterface();
		
		getData().setChartDefine(define);
		getData().setModel(model);

		// 创建统计图过程
		createChartPanels();

		registeComponentListener();

		getData().getModel().setChange(false);

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
		/* 解决浏览器中统计图在长时间的操作后（30分钟左右）出现黑屏(底层无限线程重绘) */
		getData().setWebBrowser(
				new JWebBrowser(JWebBrowser.constrainVisibility(), JWebBrowser
						.destroyOnFinalization()));
		// getData().setWebBrowser(new
		// JWebBrowser(JWebBrowser.constrainVisibility()));
		getData().getWebBrowser().setBarsVisible(false);
		getData().getWebBrowser().setStatusBarVisible(false);
		getData().getWebBrowser().setSize(this.getWidth(), this.getHeight());

		getData().getWebBrowser().setJavascriptEnabled(true);
		getData().getWebBrowser().setRequestFocusEnabled(false);
		getData().getWebBrowser().setHTMLContent(generateHTML());
		getData().getWebBrowser().setPreferredSize(
				new Dimension(this.getWidth(), this.getHeight()));

		registeWebBrowserListener();
	
		this.add(getData().getWebBrowser(),BorderLayout.CENTER);
		getData().setInitialized(true);
		return true;
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

	/**
	 * 当面板大小发生改变的时候，面板相应的大小调整事件
	 */

	private void onChartPanelResized() {

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
						
		updateChartSize();	
			
		
	}

	/**
	 * 注册webBrowser事件，主要监听js脚本发出的调用事件
	 * 
	 * @param browser
	 * @return
	 */
	private Object registeWebBrowserFunctions(JWebBrowser browser) {

		browser.registerFunction(new WebBrowserFunction("chartRenderCompleted") {
			@Override
			public Object invoke(final JWebBrowser webBrowser,
					final Object... args) {

				getData().setImage(generateChartToImage());
				doAfterImageCreated(getData().getImage());
			
				return "";
			}
		});
		return browser;
	}

	/**
	 * 为WebBrowser添加监听器
	 */
	private void registeWebBrowserListener() {
		getData().getWebBrowser().addWebBrowserListener(
				new WebBrowserAdapter() {
					public void loadingProgressChanged(WebBrowserEvent e) {
						if (e.getWebBrowser().getLoadingProgress() == 100) {
							registeWebBrowserFunctions(e.getWebBrowser());
						}
					}
				});

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
		
		html.append("</script>\n");
		html.append("<SCRIPT LANGUAGE='Javascript' SRC='");
		html.append(getData().getServerRoot()).append(
				"/javascripts/swEmbedded.js'></SCRIPT>\n");
		html.append("</html>");
		
		return html.toString();
	}




	/**
	 * 执行导出图片
	 * 
	 * @return
	 */
	private Image generateChartToImage() {
		NativeComponent nativeComponent = getData().getWebBrowser()
				.getNativeComponent();
		Dimension originalSize = nativeComponent.getSize();
		Dimension imageSize = new Dimension(this.getWidth(), this.getHeight());
		nativeComponent.setSize(imageSize);
		BufferedImage image = new BufferedImage(imageSize.width,
				imageSize.height, BufferedImage.TYPE_INT_RGB);
		nativeComponent.paintComponent(image);
		nativeComponent.setSize(originalSize);
		
		return image;
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
	 * 更新图的大小
	 * 
	 * @throws ChartGenerateException
	 */
	public void updateChartSize() {
		// 面板没有初始化
		if (!isPanelValid()) {
			return;
		}

		getData().getWebBrowser().executeJavascript(
				"updateSize(" + getWidth() + "," + getHeight() + ")");

	}


	protected void onBeforeChartXml(DataGenerateResult resultExecuteInfo) {
		/** 处理<chart>节点数据 */
		StringBuilder sb = resultExecuteInfo
				.getChartBlock(DataGenerateResult.BQ_BLOCK_CHART);
		/** 图片模式下去掉动画效果 */
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
	 * 图片生成后调用事件
	 */
	private void doAfterImageCreated(Image image) {
		if (this.getData().getImageCreatedListener() != null) {
			this.getData().getImageCreatedListener()
					.onImageCreated(new ChartImageEvent(this, 0, image));
		}
	}

}
