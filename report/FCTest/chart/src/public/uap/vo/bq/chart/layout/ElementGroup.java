package uap.vo.bq.chart.layout;

import com.ufida.iufo.pub.tools.AppDebug;
import com.ufida.iufo.pub.tools.DeepCopyUtilities;

/**
 * Ԫ���飬һ��Ԫ����һ����Ӧ�������������
 *
 * @author zll
 *
 */
public class ElementGroup implements LayoutElement{
	private static final long serialVersionUID = 1L;

	/* ���������� */
	private String refPropertyGroup;
	/* ���� */
	private String name;
	/* ���� */
	private Align align;
	/* �߿��� */
	private Integer border;
	/* �߿���ɫ */
	private String borderColor;
	/* ������Ԫ�� */
	private LayoutElement[] children;

	private boolean isValidate = true;

	public boolean isValidate() {
		return isValidate;
	}

	public void setValidate(boolean isValidate) {
		this.isValidate = isValidate;
	}

	public ElementGroup(ElementGroupInfo info){
		this.refPropertyGroup = info.refPropertyGroup;
		this.name = info.name;
		this.align = info.align;
		this.border = info.border;
		this.borderColor = info.borderColor;
		this.children = info.children;
	}

	public LayoutElement[] getChildren() {
		return children;
	}

	public String getRefPropertyGroup() {
		return refPropertyGroup;
	}

	public Integer getBorder() {
		return border;
	}

	public String getBorderColor() {
		return borderColor;
	}

	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	public Align getAlign() {
		return align;
	}
	
	@Override
	public Object clone() {
		try {
			ElementGroup copy = (ElementGroup) super.clone();
			copy.refPropertyGroup = this.refPropertyGroup;
			copy.name = this.name;
			copy.align = this.align;
			copy.border = this.border;
			copy.borderColor = this.borderColor;
			copy.isValidate = this.isValidate;
			copy.children = (LayoutElement[]) DeepCopyUtilities.copy(this.children);
			return copy;
		} catch (RuntimeException e) {
			AppDebug.error("ElementGroup clone error !");
			AppDebug.error(e);
			throw e;
		} catch (Exception e) {
			AppDebug.error("ElementGroup clone error");
			AppDebug.error(e);
			throw new RuntimeException("ElementGroup clone error", e);
		}
	}

	public static class ElementGroupInfo{
		public String refPropertyGroup;
		public String name;
		public Align align;
		public Integer border;
		public String borderColor;
		public LayoutElement[] children;
	}

	/**
	 * ����
	 *
	 * @author zll
	 *
	 */
	public enum Align {
		V("V", nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("0502004_0","00502004-0079")/*@res "��ֱ"*/), H("H", nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("0502004_0","00502004-0080")/*@res "ˮƽ"*/);

		private String code;
		private String name;

		public String getCode() {
			return code;
		}

		public String getName() {
			return name;
		}

		private Align(String code, String name){
			this.code = code;
			this.name = name;
		}
	}
	
}