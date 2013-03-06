package com.yonyou.bq8.di.core.utils;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.lang.StringUtils;

/**
 * 操作文件系统的工具类
 * 
 * @author 潘巍（Peter Pan）
 * @since 2011-4-20 下午05:28:25
 */
public class BQFileUtils {

	public static InputStream getInputStream(String fileName, Class<?> cls)
			throws FileNotFoundException {
		InputStream inputStream = cls.getResourceAsStream(fileName);
		if (inputStream == null) {
			inputStream = cls.getResourceAsStream("/" + fileName);
		}
		if (inputStream == null) {
			inputStream = new FileInputStream(fileName);
		}
		return inputStream;
	}

	/**
	 * 工具方法：判断对象是否是目录
	 * 
	 * @param f
	 * @return
	 */
	public static boolean isDirectory(File f) {
		if (f != null && f.exists() && f.isDirectory()) {
			return true;
		}
		return false;
	}

	/**
	 * 工具方法：从磁盘上读取图片文件，存储到一个BufferedImage对象中
	 * 
	 * @param data
	 *            图片的相对路径
	 * @param format
	 *            图片文件后缀
	 * @return
	 * 
	 */
	public static BufferedImage bitmapToImage(String data, String format)
			throws IOException {
		final InputStream inb = new FileInputStream(data);
		final ImageReader rdr = ImageIO.getImageReadersByFormatName(format)
				.next();
		final ImageInputStream imageInput = ImageIO.createImageInputStream(inb);
		rdr.setInput(imageInput);
		final BufferedImage image = rdr.read(0);
		inb.close();
		return image;
	}

	/**
	 * 工具方法：将一个BufferedImage对象保存到磁盘上
	 * 
	 * @param image
	 *            待保存的BufferedImage对象
	 * @param data
	 *            图片的相对路径
	 * @param format
	 *            图片后缀
	 * 
	 */
	public static void imageToBitmap(BufferedImage image, String data,
			String format) throws IOException {
		final OutputStream inb = new FileOutputStream(data);
		final ImageWriter wrt = ImageIO.getImageWritersByFormatName(format)
				.next();
		final ImageInputStream imageInput = ImageIO
				.createImageOutputStream(inb);
		wrt.setOutput(imageInput);
		wrt.write(image);
		inb.close();
	}

	/**
	 * 工具方法：缩放图片
	 * 
	 * @param img
	 *            用于缩放的原始图片
	 * @param targetWidth
	 *            目标宽度（像素）
	 * @param targetHeight
	 *            目标高度（像素）
	 * @param hint
	 *            渲染算法选择提示 {@code RenderingHints.KEY_INTERPOLATION} (e.g.
	 *            {@code RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR},
	 *            {@code RenderingHints.VALUE_INTERPOLATION_BILINEAR}, {@code
	 *            RenderingHints.VALUE_INTERPOLATION_BICUBIC})
	 * @param higherQuality
	 *            如果为true，该方法将使用多步缩放机制，进而提供高质量的图片 (仅用于缩小的情况下，及{@code
	 *            targetWidth} 或者{@code targetHeight}小于原始尺寸，并且通常用于{@code
	 *            BILINEAR}提示)
	 * @return 原始图的缩放版本 {@code BufferedImage}
	 */
	public static BufferedImage getScaledInstance(BufferedImage img,
			int targetWidth, int targetHeight, Object hint,
			boolean higherQuality) {
		final int type = img.getTransparency() == Transparency.OPAQUE ? BufferedImage.TYPE_INT_RGB
				: BufferedImage.TYPE_INT_ARGB;
		BufferedImage ret = (BufferedImage) img;
		int w;
		int h;
		if (higherQuality) {
			// 使用多步技术：开始时是原始大小
			w = img.getWidth();
			h = img.getHeight();
		} else {
			// 使用单步技术 :直接使用目标尺寸
			w = targetWidth;
			h = targetHeight;
		}

		do {
			if (higherQuality && w > targetWidth) {
				w /= 2;
				if (w < targetWidth) {
					w = targetWidth;
				}
			}

			if (higherQuality && h > targetHeight) {
				h /= 2;
				if (h < targetHeight) {
					h = targetHeight;
				}
			}

			final BufferedImage tmp = new BufferedImage(w, h, type);
			final Graphics2D g2 = tmp.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
			g2.drawImage(ret, 0, 0, w, h, null);
			g2.dispose();

			ret = tmp;
		} while (w != targetWidth || h != targetHeight);

		return ret;
	}

	/**
	 * 工具方法：通过文件路径名称获得URL对象<br>
	 * 首先，将filename当做全路径，判断是否存在该文件，<br>
	 * 如果存在转换URL；<br>
	 * 如果不存在，当做以类加载器为根的相对路径看待，判断是否存在该文件。
	 * 
	 * @param filename
	 * @return
	 * @throws MalformedURLException
	 */
	public static URL getURL(String filename) throws MalformedURLException {
		return getURL(filename, BQFileUtils.class);
	}

	public static URL getURL(String filename, Class<?> cls)
			throws MalformedURLException {
		URL url;
		File file = new File(filename);
		if (file.exists()) {
			url = file.toURI().toURL();
		} else {
			ClassLoader classLoader = cls.getClassLoader();
			url = classLoader.getResource(filename);
		}
		return url;
	}

	/**
	 * 工具方法：通过文件
	 * 
	 * @param filename
	 * @return
	 * @throws MalformedURLException
	 */
	public static File getFile(String filename) throws MalformedURLException {
		URL url = getURL(filename);
		return toFile(url);
	}

	public static File getFile(String filename, Class<?> cls)
			throws MalformedURLException {
		URL url = getURL(filename, cls);
		return toFile(url);
	}

	/**
	 * 工具方法：通过URL获得File对象
	 * 
	 * @param url
	 * @return
	 */
	public static File toFile(URL url) {
		File f = new File(url.getFile());
		if (f.isFile() || f.isDirectory()) {
			return f;
		}
		return null;
	}

	/**
	 * 目录路径拼接
	 * 
	 * @param dir1
	 *            parent目录
	 * @param dir2
	 *            子目录
	 * @return
	 */
	public static String dirAppend(String dir1, String dir2) {
		if (dir1 == null || dir2 == null) {
			return null;
		}
		if (!dir1.endsWith("\\") && !dir1.endsWith("/")) {
			dir1 = directoryComplete(dir1);
		}
		if (dir2.endsWith("\\") || dir2.endsWith("/")) {
			dir2 = StringUtils.removeStart(dir2, "\\");
			dir2 = StringUtils.removeStart(dir2, "/");
		}

		return dir1 + dir2;
	}

	/**
	 * 目录完整化，如果目录不是以\\或者/结尾的话则自动补齐
	 * 
	 * @param dir
	 * @return
	 */
	public static String directoryComplete(String dir) {
		if (StringUtils.isEmpty(dir)) {
			return null;
		}

		if (dir.endsWith("\\") || dir.endsWith("/")) {
			return dir;
		}

		return dir + "/";
	}

	/**
	 * 目录分割，将一个完整路径字符串按照文件系统分隔符分割为多个目录
	 * 
	 * @param dir
	 * @return
	 */
	public static List<String> dirSplit(String dir) {
		List<String> dirs = new ArrayList<String>();
		dir = StringUtils.replace(dir, "\\", "/");
		String[] dirArr = StringUtils.split(dir, "/");
		for (String item : dirArr) {
			dirs.add(item);
		}
		return dirs;
	}

}