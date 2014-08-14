package jp.sourceforge.tmdmaker.treeeditpart;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import jp.sourceforge.tmdmaker.TMDPlugin;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.ModelElement;

import org.eclipse.swt.graphics.Image;
import org.slf4j.LoggerFactory;
import org.eclipse.gef.editparts.AbstractTreeEditPart;

public class IdentifierTreeEditPart extends AbstractTreeEditPart implements PropertyChangeListener {
	
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(IdentifierTreeEditPart.class);
	
	@Override
	public Identifier getModel() {
		return (Identifier) super.getModel();
	}

	@Override
	protected String getText() {
		ModelElement model = getModel();
		if (model.getName() == null)
		{
			return "";
		}
		else{
			return model.getName();		
		}
	}

	@Override
	protected Image getImage() {
		return TMDPlugin.getImage("icons/outline/identifier.png");
	}

	@Override
	public void activate() {
		super.activate();
		getModel().addPropertyChangeListener(this);
	}

	@Override
	public void deactivate() {
		getModel().removePropertyChangeListener(this);
		super.deactivate();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		logger.debug(getClass() + "." + evt.getPropertyName());

		if (evt.getPropertyName().equals(Identifier.PROPERTY_NAME)) {
			refreshVisuals();
	    } else {
			logger.warn("Not Handle Event Occured.");
		}
	}
}
