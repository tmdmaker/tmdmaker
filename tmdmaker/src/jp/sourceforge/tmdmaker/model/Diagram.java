package jp.sourceforge.tmdmaker.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author nakaG
 *
 */
public class Diagram extends ModelElement {
	private List<ModelElement> children = new ArrayList<ModelElement>();
//	private String dialectName = "";
	public static final String P_CHILDREN = "p_children";

	public <T extends ModelElement>void addChild(T child) {
		if (children.contains(child) == false) {
			children.add(child);
			firePropertyChange(P_CHILDREN, null, child);
		}
	}
	public <T extends ModelElement>void removeChild(T child) {
		children.remove(child);
		firePropertyChange(P_CHILDREN, child, null);
	}
	/**
	 * @return the children
	 */
	public List<ModelElement> getChildren() {
		return children;
	}
}
