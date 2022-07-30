package org.tmdmaker.ui.editor.gef3.editparts.node;

import java.beans.PropertyChangeEvent;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.views.properties.IPropertySource;
import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.Identifier;
import org.tmdmaker.core.model.ModelElement;
import org.tmdmaker.ui.editor.draw2d.figure.node.IdentifierFigure;
import org.tmdmaker.ui.editor.gef3.editparts.IAttributeEditPart;
import org.tmdmaker.ui.views.properties.IPropertyAvailable;

public class IdentifierEditPart extends AbstractTMDEditPart<Identifier>
		implements IPropertyAvailable, IAttributeEditPart {

	public IdentifierEditPart(Identifier identifier) {
		super();
		setModel(identifier);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(ModelElement.PROPERTY_NAME)) {
			logger.debug("Handle Name Event.");
			handleNameChange(evt);
		} else {
			logger.warn("Not Handle Event Occured.");
		}
		
	}

	protected void handleNameChange(PropertyChangeEvent evt) {
		refreshVisuals();
	}

	@Override
	public AbstractEntityModel getParentModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPropertySource getPropertySource(CommandStack commandStack) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IFigure createFigure() {
		IdentifierFigure figure = new IdentifierFigure();
		figure.update(getModel());
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub
		
	}


}
