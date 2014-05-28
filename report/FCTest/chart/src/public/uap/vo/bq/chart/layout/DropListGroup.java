package uap.vo.bq.chart.layout;

import com.ufida.iufo.pub.tools.AppDebug;
import com.ufida.iufo.pub.tools.DeepCopyUtilities;

/**
 * 可以下拉的包括很多属性组的组
 * 
 * @author zhanglld
 *
 */
public class DropListGroup implements LayoutElement {
	private static final long serialVersionUID = 1L;

	private String name;
	private String type;
	private String refPropertyGroupCode;
	private LayoutElement[] children;

	/**
	 * 构造器
	 * @param name 下拉组的名称
	 * @param children 子元素
	 */
	public DropListGroup(String name, String type, String refPropertyGroupCode, LayoutElement[] children) {
		this.name = name;
		this.type = type;
		this.refPropertyGroupCode = refPropertyGroupCode;
		this.children = children;
	}

	/**
	 * 获得子元素数组
	 * @return
	 */
	public LayoutElement[] getChildren() {
		return children;
	}

	/**
	 * 获得名称
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 获得类型
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * 获得引用属性组编码
	 * @return
	 */
	public String getRefPropertyGroupCode() {
		return refPropertyGroupCode;
	}

	@Override
	public void setName(String name) {
		this.name = name;

	}

	@Override
	public Object clone() {
		try {
			DropListGroup copy = (DropListGroup) super.clone();
			copy.children = (LayoutElement[]) DeepCopyUtilities.copy(this.children);
			copy.name = this.name;
			copy.type = this.type;
			copy.refPropertyGroupCode = this.refPropertyGroupCode;
			return copy;
		} catch (RuntimeException e) {
			AppDebug.error("DropListGroup clone error !");
			AppDebug.error(e);
			throw e;
		} catch (Exception e) {
			AppDebug.error("DropListGroup clone error");
			AppDebug.error(e);
			throw new RuntimeException("DropListGroup clone error", e);
		}
	}
}
