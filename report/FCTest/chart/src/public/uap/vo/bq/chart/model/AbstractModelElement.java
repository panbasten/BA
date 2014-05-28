package uap.vo.bq.chart.model;

/**
 * ����ģ�Ͷ��� �����ø����󡢿����ñ仯
 * 
 * @author zhanglld
 * 
 */
public abstract class AbstractModelElement<T> implements IModelElement<T> {

	private static final long serialVersionUID = 1L;
	/* ���� */
	protected String code;
	/* �ϼ����� */
	private IModelObject superObject;
	/* �Ƿ��� */
	private boolean isChanged = false;

	public void setCode(String code) {
		if (code != null) {
			this.code = code;
			setChange(true); 
		}
	}

	public String getCode() {
		return code;
	}

	public void setSuperObject(IModelObject superObject) {
		this.superObject = superObject;
	}

	public IModelObject getSuperObject() {
		return superObject;
	}

	@Override
	public boolean isChanged() {
		return isChanged;
	}

	@Override
	public void setChange(boolean isChanged) {
		this.isChanged = isChanged;
		if (isChanged) {
			if (superObject != null) {
				superObject.setChange(true);
			}
		}
	}

	@Override
	public Object clone(){
		AbstractModelElement<?> o = null;
		try {
			o = (AbstractModelElement<?>)super.clone();
			o.code = this.code;
			o.isChanged = this.isChanged;
			o.superObject = this.superObject;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
		
		return o;
	}
}
