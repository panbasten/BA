package uap.impl.bq.chart.service;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import uap.bq.chart.component.FusionChartsImageUtil;
import uap.itf.bq.chart.IChartExportToImageService;
import uap.vo.bq.chart.define.ChartDefine;
import uap.vo.bq.chart.model.ChartModel;
import uap.vo.bq.chart.model.ExportImageReadyEvent;

public class ChartExportToImageServiceImpl implements IChartExportToImageService {
	private static class ImageResult {
		Image image = null;
	}
	/**
	 * …˙≥…Õº∆¨
	 * @param model
	 * @param define
	 * @param width
	 * @param height
	 * @return
	 * @throws Exception
	 */
	private Image generateChartToImage(ChartModel model, ChartDefine define,
			int width, int height) throws Exception {
		
		
		final ImageResult result = new ImageResult();
		FusionChartsImageUtil.generateChartToImage(model, define, width, height, 
				new ExportImageReadyEvent() {

					@Override
					public void onImageReady(Image newImg) {
						if (null != newImg) {
//							try {
//								ImageIO.write((BufferedImage)newImg, "jpg", new File("d:\\temp\\333.jpg"));
//							} catch (IOException ex) {
//								ex.printStackTrace();
//							}
							result.image = newImg;
						}
					}
		});
		
		int timeout = 100; // 50√Î
		
		for (;;) {
			if (result.image != null) {
				return result.image;
			}
			// ≥¨ ±
			if (timeout-- == 0){
				break;
			}
			
			Thread.sleep(500);
		}
		
		return null;
	}

	@Override
	public Image generateChartToImage(ExportImageParameter param) throws Exception {
		if (param != null){
			ChartDefine define = param.getDefine();
			if (define != null && param.getModel() != null && param.getWidth() != 0 && param.getHeight() != 0){
				return generateChartToImage (param.getModel(),define,param.getWidth(),param.getHeight());
			}
		}
		return null;
	}

	@Override
	public Image[] generateChartToImage(ExportImageParameter[] params) throws Exception {
		if (params != null && params.length != 0){
			List<Image> imageList= new ArrayList<Image>();
			for (ExportImageParameter p : params){
				imageList.add(generateChartToImage(p));
			}
			
			if (imageList.size() != 0){
				return imageList.toArray(new Image[imageList.size()]);
			}
			
		}
		return new Image[0];
	}
}
