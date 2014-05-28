package uap.bq.chart.generator.dragnode;

import java.util.List;

public abstract class DragNodeTree {

	public abstract boolean hasChildTrees();

	public abstract List<DragNodeTree> getChildTrees();

	public abstract SetNode getRootNode();

	public abstract int getHeightUnitInterval();

	public abstract int getWidthUnitInterval();
	
	public abstract int getXTopRightCorner();
	
	public abstract int getYTopRightCorner();

	public int getSetNodeHeightUnit() {
		return getRootNode().getHeightUnit();
	}

	public int getSetNodeWidthUnit() {
		return getRootNode().getWidthUnit();
	}

	private int heightUnit;

	public int getHeightUnit() {
		calculateDimension();
		return heightUnit;
	}

	private int widthUnit;

	public int getWidthUnit() {
		calculateDimension();
		return widthUnit;
	}

	private boolean calculated = false;

	protected void calculateDimension() {
		if (calculated)
			return;
		int heightUnitTemp = 0;
		int widthUnitTemp = 0;
		if (hasChildTrees()) {
			for (DragNodeTree childTree : getChildTrees()) {
				widthUnitTemp = widthUnitTemp + getWidthUnitInterval()
						+ childTree.getWidthUnit();
				heightUnitTemp = (heightUnitTemp > childTree.getHeightUnit()) ? heightUnitTemp
						: childTree.getHeightUnit();
			}
			widthUnit = widthUnitTemp > getSetNodeWidthUnit() ? widthUnitTemp
					: getSetNodeWidthUnit();
			widthUnit += getWidthUnitInterval();
			heightUnit = heightUnitTemp + 2 * getHeightUnitInterval()
					+ getSetNodeHeightUnit();
		} else {
			widthUnit  = getSetNodeWidthUnit();
			heightUnit = getSetNodeHeightUnit();
		}
		calculated = true;
	}
	
	private int xPosition;
	private int yPosition;
	
	protected void calculatePosition(){
		xPosition = getXTopRightCorner() - getWidthUnit()/2;
		yPosition = getYTopRightCorner() - getSetNodeHeightUnit()/2 - getHeightUnitInterval();
	}

}
