package uap.ui.bq.chart.component;

import java.lang.ref.WeakReference;

import uap.vo.bq.chart.model.ImageCreatedListener.ChartImageEvent;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserFunction;

import com.ufida.iufo.pub.tools.AppDebug;
 class OnChartRenderCompletedFunction extends WebBrowserFunction {

		WeakReference<ChartPanel> chartPanelRef = null;
		public OnChartRenderCompletedFunction(ChartPanel chartPanel){
			super("chartRenderCompleted");
			chartPanelRef = new WeakReference<ChartPanel>(chartPanel);
		}

		@Override
		public Object invoke(final JWebBrowser webBrowser, final Object... args) {
			ChartPanel chartPanel = chartPanelRef.get();
			if(chartPanel != null){
				updateImagePanelData(chartPanel,true);
				doAfterImageCreated(chartPanel);
				chartPanel.getData().setHasRendered(true);
			}
			chartPanel.updateChartSizeRenderisOk();
			return "";
		}
		
		
		private void updateImagePanelData(ChartPanel chartPanel,boolean changed) {
			if (!chartPanel.isPanelValid()) {
				return;
			}
			if (changed) {
				chartPanel.applyChartImageToPanel(true);
			} else {
				chartPanel.applyChartImageToPanel(false);
			}

			if (chartPanel.isImageModel()) {
				chartPanel.switchPanelTo(ChartPanel.PANEL_IMAGECHART);
				AppDebug.debug("updateImagePanelData: switchPanelTo(PANEL_IMAGECHART)");
			}
		}
		
		/**
		 * 图片生成后调用事件
		 */
		private void doAfterImageCreated(ChartPanel chartPanel) {
			if (chartPanel.getData().getImageCreatedListener() != null) {
				chartPanel.getData().getImageCreatedListener()
						.onImageCreated(new ChartImageEvent(this, 0, null));
			}
		}
	}