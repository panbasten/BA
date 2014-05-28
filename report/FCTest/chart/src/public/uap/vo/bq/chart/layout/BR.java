package uap.vo.bq.chart.layout;

import com.ufida.iufo.pub.tools.AppDebug;

/**
 * 换行符
 * @author zhanglld
 *
 */
public class BR implements LayoutElement{
	private static final long serialVersionUID = 1L;
	
	/* 唯一实例 */
	public static final BR instance = new BR();
	
	/**
	 * 构造器
	 */
	private BR(){}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object clone() {
		try {
			BR copy = (BR) super.clone();
			return copy;
		} catch (RuntimeException e) {
			AppDebug.error("BR clone error !");
			AppDebug.error(e);
			throw e;
		} catch (Exception e) {
			AppDebug.error("BR clone error");
			AppDebug.error(e);
			throw new RuntimeException("BR clone error", e);
		}
	}
}
