package uap.bq.chart.component;

import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.Image;

import javax.swing.JFrame;

import uap.vo.bq.chart.define.ChartDefine;
import uap.vo.bq.chart.model.ChartModel;
import uap.vo.bq.chart.model.ExportImageReadyEvent;
import uap.vo.bq.chart.model.ImageCreatedListener;

import com.ufida.iufo.pub.tools.AppDebug;

public class FusionChartsImageUtil {
	
	static public void generateChartToImage(ChartModel model, ChartDefine define,
			int width, int height, final ExportImageReadyEvent notifyEvent) {
		
		ImageFusionChart obj = new ImageFusionChart(width, height) {

			@Override
			public void exportChartImage(Image image) {
				super.exportChartImage(image);
				if (notifyEvent != null) {
					notifyEvent.onImageReady(image);
				}
			}
		};
		obj.generateImage(define, model);
	}
	
	protected static class ImageFusionChart implements ImageCreatedListener {
		private Image image = null;
		private JFrame jf = null;
		private int width = 0, height = 0;

		public ImageFusionChart(int width, int height) {
			this.width = width;
			this.height = height;
		}

		public void generateImage(final ChartDefine chartDefine,
				final ChartModel chartModel) {
			

			try {
				if (chartDefine != null && chartModel != null) {
					jf = new JFrame("ImageFusionChartFrame");
					final FusionImageChartPanel panel = new FusionImageChartPanel(this.width,this.height);
					panel.createChart(chartDefine, chartModel);
					panel.setImageCreatedListener(
							ImageFusionChart.this);
					jf.getContentPane().setLayout(new BorderLayout());
					jf.getContentPane().add(panel, BorderLayout.CENTER);// */
					jf.invalidate();
					jf.pack();
					jf.setSize(ImageFusionChart.this.width,
							ImageFusionChart.this.height);
					jf.setVisible(true);
				}
			} catch (Exception e) {
				AppDebug.debug(e);
			}
			
		}

		public Image getImage() {
			return image;
		}

		public void clear() {
			if (jf != null) {
				jf.dispose();
			}
			image = null;
			jf = null;
		}

		public void onImageCreated(Event event) {
			if (event instanceof ChartImageEvent) {
				if (((ChartImageEvent) event).getSource() instanceof FusionImageChartPanel) {
					FusionImageChartPanel panel = (FusionImageChartPanel) ((ChartImageEvent) event)
							.getSource();
					image = panel.getChartImage();
					exportChartImage(image);
					this.clear();
				}
			}
		}

		public void exportChartImage(Image image) {
		}

	}

	protected class ExportImageReady implements ExportImageReadyEvent {
		private Image image = null;
		@Override
		public void onImageReady(Image image) {
			this.image = image;
			
		}

		public Image getImage() {	
			return image;

		}
	}
}
