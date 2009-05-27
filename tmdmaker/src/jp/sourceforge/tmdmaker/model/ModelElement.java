package jp.sourceforge.tmdmaker.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

import org.eclipse.draw2d.geometry.Rectangle;

/**
 * 
 * @author nakaG
 */
@SuppressWarnings("serial")
public class ModelElement implements Serializable {
	/** 名称プロパティ定数 */
	public static final String PROPERTY_NAME = "_property_name";
	/** 領域プロパティ定数 */
	public static final String PROPERTY_CONSTRAINT = "_property_constraint";
	/** オブジェクトの名称 */
	private String name;
	/** オブジェクトの領域 */
	private Rectangle constraint;
	/** プロパティ変更通知用 */
	private transient PropertyChangeSupport listeners = new PropertyChangeSupport(
			this);

	/**
	 * プロパティ変更通知先追加
	 * 
	 * @param listener
	 *            プロパティ変更通知先
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		listeners.addPropertyChangeListener(listener);
	}

	/**
	 * プロパティ変更通知
	 * 
	 * @param propName
	 *            変更したプロパティの名称
	 * @param oldValue
	 *            変更前の値
	 * @param newValue
	 *            変更後の値
	 */
	public void firePropertyChange(String propName, Object oldValue,
			Object newValue) {
		listeners.firePropertyChange(propName, oldValue, newValue);
	}

	/**
	 * プロパティ変更通知先削除
	 * 
	 * @param listener
	 *            プロパティ変更通知先
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		listeners.removePropertyChangeListener(listener);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
		firePropertyChange(PROPERTY_NAME, null, name);
	}

	/**
	 * @return the constraint
	 */
	public Rectangle getConstraint() {
		return constraint;
	}

	/**
	 * @param constraint
	 *            the constraint to set
	 */
	public void setConstraint(Rectangle constraint) {
		Rectangle oldValue = this.constraint;
		this.constraint = constraint;
		firePropertyChange(PROPERTY_CONSTRAINT, oldValue, constraint);
	}

}
