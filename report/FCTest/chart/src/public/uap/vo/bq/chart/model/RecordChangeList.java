package uap.vo.bq.chart.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * 可以记录变更情况的List
 * 
 * @author zhanglld
 * 
 * @param <T>
 */
@SuppressWarnings("rawtypes")
public class RecordChangeList<T extends AbstractModelElement> extends
		AbstractModelElement implements List<T>, Serializable, IModelObject {

	private static final long serialVersionUID = 1L;

	/* 变更的属性组列表 */
	private List<T> changedPropertyGroupList = null;
	/* 增加的属性组列表 */
	private List<T> addedPropertyGroupList = null;
	/* 删除的属性组列表 */
	private List<T> deletedPropertyGroupList = null;
	/* 全部的属性组 */
	private List<T> originalList = null;
	/* 长度 */
	private Integer size = null;

	/**
	 * 构造指定大小的List
	 * @param size
	 */
	public RecordChangeList(int size) {
		this.size = size;
		buildAllList();

		setChange(true);
	}

	public RecordChangeList() {
		buildAllList();

		setChange(true);
	}
	
	@Override
	public void setChange(boolean changed) {
		super.setChange(changed);
		
		if(!changed){
			for(T t : originalList){
				t.setChange(changed);
			}
			
			changedPropertyGroupList.clear();
			addedPropertyGroupList.clear();
			deletedPropertyGroupList.clear();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object clone() {
		RecordChangeList newObj = (RecordChangeList) super.clone();
		List<T> newChangedPropertyGroupList = null;
		List<T> newAddedPropertyGroupList = null;
		List<T> newDdeletedPropertyGroupList = null;
		List<T> newOriginalList = null;

		newChangedPropertyGroupList = new ArrayList<T>();

		for (T t : changedPropertyGroupList) {
			T newT = (T) t.clone();
			newChangedPropertyGroupList.add(newT);
			newT.setSuperObject(newObj);
		}

		newAddedPropertyGroupList = new ArrayList<T>();

		for (T t : addedPropertyGroupList) {
			T newT = (T) t.clone();
			newAddedPropertyGroupList.add(newT);
			newT.setSuperObject(newObj);
		}

		newDdeletedPropertyGroupList = new ArrayList<T>();

		for (T t : deletedPropertyGroupList) {
			T newT = (T) t.clone();
			newDdeletedPropertyGroupList.add(newT);
			newT.setSuperObject(newObj);
		}

		newOriginalList = new ArrayList<T>();

		for (T t : originalList) {
			T newT = (T) t.clone();
			newOriginalList.add(newT);
			newT.setSuperObject(newObj);
		}

		newObj.addedPropertyGroupList = newAddedPropertyGroupList;
		newObj.changedPropertyGroupList = newChangedPropertyGroupList;
		newObj.deletedPropertyGroupList = newDdeletedPropertyGroupList;
		newObj.originalList = newOriginalList;
		newObj.size = this.size;

		return newObj;
	}

	/**
	 * 根据size是否设置构建相应List
	 */
	private void buildAllList() {
		if (size != null) {
			changedPropertyGroupList = new ArrayList<T>(size);
			addedPropertyGroupList = new ArrayList<T>(size);
			deletedPropertyGroupList = new ArrayList<T>(size);
			originalList = new ArrayList<T>(size);
		} else {
			changedPropertyGroupList = new ArrayList<T>();
			addedPropertyGroupList = new ArrayList<T>();
			deletedPropertyGroupList = new ArrayList<T>();
			originalList = new ArrayList<T>();
		}
	}

	public List<T> getChangedPropertyGroups() {
		return changedPropertyGroupList;
	}

	public List<T> getAddedPropertyGroups() {
		return addedPropertyGroupList;
	}

	public List<T> getDeletedPropertyGroups() {
		return deletedPropertyGroupList;
	}

	@Override
	public int size() {
		return originalList.size();
	}

	@Override
	public boolean isEmpty() {
		return originalList.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return originalList.contains(o);
	}

	@Override
	public Iterator<T> iterator() {
		return originalList.iterator();
	}

	@Override
	public Object[] toArray() {
		return originalList.toArray();
	}

	@SuppressWarnings("hiding")
	@Override
	public <T> T[] toArray(T[] a) {
		return originalList.toArray(a);
	}

	@Override
	public boolean add(T t) {
		t.setSuperObject(this);
		setChange(true);
		return originalList.add(t);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(Object o) {
		if (originalList.contains(o)){
			this.setChange(true);
			this.deletedPropertyGroupList.add((T) o);
			this.addedPropertyGroupList.remove(o);
			return originalList.remove(o);
		}
		return true;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return originalList.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		setChange(true);
		for(T t : c){
			t.setSuperObject(this);
		}
		this.addedPropertyGroupList.addAll(c);
		return originalList.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		setChange(true);
		for(T t : c){
			t.setSuperObject(this);
		}
		this.addedPropertyGroupList.addAll(c);
		return originalList.addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		setChange(true);
		this.deletedPropertyGroupList.removeAll(c);
		return originalList.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		setChange(true);
		return originalList.retainAll(c);
	}

	@Override
	public void clear() {
		this.deletedPropertyGroupList.addAll(originalList);
		originalList.clear();
		setChange(true);
	}

	@Override
	public T get(int index) {
		return originalList.get(index);
	}

	@Override
	public T set(int index, T element) {
		setChange(true);
		this.changedPropertyGroupList.add(element);
		return originalList.set(index, element);
	}

	@Override
	public void add(int index, T element) {
		setChange(true);
		element.setSuperObject(this);
		this.addedPropertyGroupList.add(element);
		originalList.add(index, element);
	}

	@Override
	public T remove(int index) {
		setChange(true);
		this.deletedPropertyGroupList.add(this.originalList.get(index));
		return originalList.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return originalList.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return originalList.lastIndexOf(o);
	}

	@Override
	public ListIterator<T> listIterator() {
		return originalList.listIterator();
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		return originalList.listIterator(index);
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		return originalList.subList(fromIndex, toIndex);
	}
}