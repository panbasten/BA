package uap.vo.bq.chart.layout;

import java.io.Serializable;

import com.ufida.iufo.pub.tools.AppDebug;
import com.ufida.iufo.pub.tools.DeepCopyUtilities;

/**
 * 布局
 * 
 * @author zhanglld
 *
 */
public class Layout implements Serializable ,Cloneable{
	private static final long serialVersionUID = 1L;
	
	/* 布局中的Tab页数组 */
	private TabPage[] tabPages;

	public Layout(TabPage[] tabPages){
		this.tabPages = tabPages;
	}
	
	public TabPage[] getTabPages() {
		return tabPages;
	}

	@Override
	public Object clone(){
		try {
			Layout copy = (Layout) super.clone();
			copy.tabPages = (TabPage[]) DeepCopyUtilities.copy(this.tabPages);
			return copy;
		}  catch (RuntimeException e) {
			AppDebug.error("Layout clone error !");
			AppDebug.error(e);
			throw e;
		} catch (Exception e)  {
			AppDebug.error("Layout clone error");
			AppDebug.error(e);
			throw new RuntimeException("Layout clone error", e);
		}
	}
	
	
}
