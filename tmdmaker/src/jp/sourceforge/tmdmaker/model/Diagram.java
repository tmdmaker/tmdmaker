package jp.sourceforge.tmdmaker.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class Diagram extends ModelElement {
	/** 子モデル */
	private List<ModelElement> children = new ArrayList<ModelElement>();
	/** 子モデルプロパティ定数 */
	public static final String PROPERTY_CHILDREN = "_property_children";

	/**
	 * 子モデル追加
	 * 
	 * @param child
	 *            エンティティまたは表
	 */
	public void addChild(AbstractEntityModel child) {
		if (!children.contains(child)) {
			children.add(child);
			child.setDiagram(this);
			firePropertyChange(PROPERTY_CHILDREN, null, child);
		}
	}

	/**
	 * 子モデル追加
	 * 
	 * @param child
	 *            エンティティや表以外の子モデル
	 */
	public void addChild(ConnectableElement child) {
		if (!children.contains(child)) {
			children.add(child);
			firePropertyChange(PROPERTY_CHILDREN, null, child);
		}
	}

	/**
	 * 子モデル削除
	 * 
	 * @param child
	 *            エンティティや表
	 */
	public void removeChild(AbstractEntityModel child) {
		child.setDiagram(null);
		children.remove(child);
		firePropertyChange(PROPERTY_CHILDREN, child, null);
	}

	/**
	 * 子モデル削除
	 * 
	 * @param child
	 *            エンティティや表以外の子モデル
	 */
	public void removeChild(ConnectableElement child) {
		children.remove(child);
		firePropertyChange(PROPERTY_CHILDREN, child, null);
	}

	/**
	 * @return the children
	 */
	public List<ModelElement> getChildren() {
		return children;
	}
}
