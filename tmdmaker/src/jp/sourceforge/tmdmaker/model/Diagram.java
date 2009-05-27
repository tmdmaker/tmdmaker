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
	 * @param <T>
	 *            モデル型を継承しているクラスの型
	 * @param child
	 *            子モデル
	 */
	public <T extends ModelElement> void addChild(T child) {
		if (children.contains(child) == false) {
			children.add(child);
			firePropertyChange(PROPERTY_CHILDREN, null, child);
		}
	}
	/**
	 * 子モデル削除
	 * @param <T> モデル型を継承しているクラスの型
	 * @param child 子モデル
	 */
	public <T extends ModelElement> void removeChild(T child) {
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
