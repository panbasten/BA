package uap.vo.bq.chart.layout;

import com.ufida.iufo.pub.tools.AppDebug;
import com.ufida.iufo.pub.tools.DeepCopyUtilities;

/**
 * ���������İ����ܶ����������
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
	 * ������
	 * @param name �����������
	 * @param children ��Ԫ��
	 */
	public DropListGroup(String name, String type, String refPropertyGroupCode, LayoutElement[] children) {
		this.name = name;
		this.type = type;
		this.refPropertyGroupCode = refPropertyGroupCode;
		this.children = children;
	}

	/**
	 * �����Ԫ������
	 * @return
	 */
	public LayoutElement[] getChildren() {
		return children;
	}

	/**
	 * �������
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * �������
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * ����������������
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
