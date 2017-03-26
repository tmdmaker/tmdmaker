package jp.sourceforge.tmdmaker.treeeditpart;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import jp.sourceforge.tmdmaker.TMDEditor;
import jp.sourceforge.tmdmaker.TMDPlugin;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.ModelElement;
import jp.sourceforge.tmdmaker.property.IPropertyAvailable;
import jp.sourceforge.tmdmaker.property.IdentifierPropertySource;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertySource;
import org.slf4j.LoggerFactory;
import org.eclipse.gef.editparts.AbstractTreeEditPart;

public class IdentifierTreeEditPart extends AbstractTreeEditPart implements PropertyChangeListener,IPropertyAvailable {
	
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(IdentifierTreeEditPart.class);
	
	/**
	 * コンストラクタ
	 * @param identifier
	 */
	public IdentifierTreeEditPart(Identifier identifier)
	{
		super();
		setModel(identifier);
	}
	
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
		return TMDPlugin.getImage("icons/outline/identifier.png"); //$NON-NLS-1$
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
			logger.warn("Not Handle Event Occured."); //$NON-NLS-1$
		}
	}

	@Override
	public IPropertySource getPropertySource(TMDEditor editor) {
		return new IdentifierPropertySource(editor, this.getModel());
	}
}
