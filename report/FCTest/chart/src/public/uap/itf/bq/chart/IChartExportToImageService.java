package uap.itf.bq.chart;

import java.awt.Image;

import uap.vo.bq.chart.define.ChartDefine;
import uap.vo.bq.chart.model.ChartModel;

public interface IChartExportToImageService {
	public static class ExportImageParameter{
		private ChartModel model;
		private ChartDefine define;
		private int width;
		private int height;
		
		public ExportImageParameter (ChartModel model,ChartDefine define,int width,int height){
			this.model = model;
			this.define = define;
			this.width = width;
			this.height = height;
		}

		public ChartModel getModel() {
			return model;
		}

		public void setModel(ChartModel model) {
			this.model = model;
		}

		public ChartDefine getDefine() {
			return define;
		}

		public void setDefine(ChartDefine define) {
			this.define = define;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}
		
		
	}
	/**
	 * 导出单张图片
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Image generateChartToImage(ExportImageParameter param) throws Exception ;
	/**
	 * 导出多张图片
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Image[] generateChartToImage(ExportImageParameter[] param) throws Exception ;
}
