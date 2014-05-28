package uap.vo.bq.chart.layout;

import com.ufida.iufo.pub.tools.AppDebug;

/**
 * 可被布局的元素
 * 
 * @author zhanglld
 *
 */
public class Element implements LayoutElement{
	private static final long serialVersionUID = 1L;
	
	/* 引用属性组编码 */
	private String refPropertyGroupCode;
	/* 引用属性编码 */
	private String refPropertyCode;
	/* 名称 */
	private String name;
	/* 是控制者。在UI上体现为一个组的可用与否的CheckBox */
	private boolean isController = false;
	/*被控制者，在UI上体现为一个元素的可用与否收多个其他元素的空值*/
	private String mulController = null;
	private boolean isValidate = true;
	
	public boolean isValidate() {
		return isValidate;
	}

	public void setValidate(boolean isValidate) {
		this.isValidate = isValidate;
	}
	
	public String getMulController(){
		return mulController;
	}

	public Element(ElementInfo info){
		this.refPropertyGroupCode = info.refPropertyGroupCode;
		this.refPropertyCode = info.refPropertyCode;
		this.isController = info.isController;		
		this.mulController = info.mulController;
		this.name = info.name;
	}
	
	public String getRefPropertyGroupCode() {
		return refPropertyGroupCode;
	}
	public String getRefPropertyCode() {
		return refPropertyCode;
	}
	public String getName() {
		return name;
	}
	

	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isController() {
		return isController;
	}

	@Override
	public Object clone() {
		try {
			Element copy = (Element) super.clone();
			copy.refPropertyGroupCode = this.refPropertyGroupCode;
			copy.refPropertyCode = this.refPropertyCode;
			copy.isController = this.isController;
			copy.isValidate = this.isValidate;
			copy.name = this.name;
			copy.mulController = this.mulController;
			return copy;
		} catch (RuntimeException e) {
			AppDebug.error("Element clone error !");
			AppDebug.error(e);
			throw e;
		} catch (Exception e) {
			AppDebug.error("Element clone error");
			AppDebug.error(e);
			throw new RuntimeException("Element clone error", e);
		}
	}
	
	public static class ElementInfo{
		public String refPropertyGroupCode;
		public String refPropertyCode;
		public String name;
		public boolean isController = false;
		public String mulController = null;
	}
	

}
