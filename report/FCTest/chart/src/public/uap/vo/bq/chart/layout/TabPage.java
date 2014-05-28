package uap.vo.bq.chart.layout;

import nc.vo.pub.BusinessRuntimeException;

import com.ufida.iufo.pub.tools.AppDebug;
import com.ufida.iufo.pub.tools.DeepCopyUtilities;

/**
 * Tabҳ
 *
 * @author zll
 *
 */
public class TabPage implements LayoutElement{
	private static final long serialVersionUID = 1L;

	/* ��Ԫ�� */
	private LayoutElement[] children;
	/* ���� */
	private String name;
	/* ���� */
	private String code;
	/* Tab���뷽ʽ */
	private TabAlign tabAlign;
	/* ���õ������� */
	private String refPropertyGroup;

	public TabPage(TabPageInfo info) {
		this.name = info.name;
		this.code = info.code;
		this.tabAlign = info.tabAlign;
		this.children = info.children;
		this.refPropertyGroup = info.refPropertyGroup;
	}

	public LayoutElement[] getChildren() {
		return children;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public TabAlign getTabAlign() {
		return tabAlign;
	}

	public String getRefPropertyGroup() {
		return refPropertyGroup;
	}

	@Override
	public Object clone() {
		try {
			TabPage copy = (TabPage) super.clone();
			copy.children = (LayoutElement[]) DeepCopyUtilities.copy(this.children);
			copy.name = this.name;
			copy.code = this.code;
			copy.tabAlign = this.tabAlign;
			copy.children = this.children;
			copy.refPropertyGroup = this.refPropertyGroup;
			return copy;
		} catch (RuntimeException e) {
			AppDebug.error("TabPage clone error !");
			AppDebug.error(e);
			throw e;
		} catch (Exception e) {
			AppDebug.error("TabPage clone error");
			AppDebug.error(e);
			throw new RuntimeException("TabPage clone error", e);
		}
	}

	/**
	 * Tabҳ���뷽ʽ
	 *
	 * @author zhanglld
	 */
	public enum TabAlign {
		LEFT("left"), RIGHT("right"), TOP("top"), BOTTOM("bottom");

		private String value;

		private TabAlign(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		//		public boolean equals(String value){
		//			return this.value.equals(value);
		//		}

		public static TabAlign genTabAlign(String value) {
			if (LEFT.getValue().equals(value)) {
				return LEFT;
			} else if (RIGHT.getValue().equals(value)) {
				return RIGHT;
			} else if (TOP.getValue().equals(value)) {
				return TOP;
			} else if (BOTTOM.getValue().equals(value)) {
				return BOTTOM;
			} else {
				throw new BusinessRuntimeException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("0502004_0",
						"00502004-0082")/*@res "����ȷ�Ķ���ö��ֵ��"*/
						+ value);
			}
		}

		@Override
		public String toString() {
			return value;
		}
	}

	/**
	 * Tabҳ��Ϣ�����ڹ���TabPage����
	 * @author zhanglld
	 */
	public static class TabPageInfo {
		public LayoutElement[] children;
		public String name;
		public String code;
		public TabAlign tabAlign;
		public String refPropertyGroup;
	}
}