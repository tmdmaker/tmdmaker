package jp.sourceforge.tmdmaker.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

/**
 * 
 * @author nakaG
 */
public abstract class ModelElement implements Serializable  {
		public static final String PROPERTY_NAME = "_PROPERTY_NAME";
		private String name;
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

}
