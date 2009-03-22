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

	public void addChild(ModelElement child) {
		children.add(child);
		firePropertyChange(P_CHILDREN, null, child);
	}
	public void removeChild(ModelElement child) {
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
