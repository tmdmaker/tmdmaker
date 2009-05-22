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
public class ModelElement implements Serializable  {
		public static final String PROPERTY_NAME = "_PROPERTY_NAME";
		public static final String PROPERTY_CONSTRAINT = "p_constraint";
		private String name;
		private Rectangle constraint;
		private transient PropertyChangeSupport listeners = new PropertyChangeSupport(this);
		
		public void addPropertyChangeListener(PropertyChangeListener listener) {
			listeners.addPropertyChangeListener(listener);
		}
		
		public void firePropertyChange(String propName, Object oldValue, Object newValue) {
			listeners.firePropertyChange(propName, oldValue, newValue);
		}

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
		 * @param name the name to set
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
		 * @param constraint the constraint to set
		 */
		public void setConstraint(Rectangle constraint) {
			Rectangle oldValue = this.constraint;
			this.constraint = constraint;
			firePropertyChange(PROPERTY_CONSTRAINT, oldValue, constraint);
		}

}
