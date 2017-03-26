package jp.sourceforge.tmdmaker.treeeditpart;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import jp.sourceforge.tmdmaker.TMDEditor;
import jp.sourceforge.tmdmaker.TMDPlugin;
import jp.sourceforge.tmdmaker.model.IdentifierRef;
import jp.sourceforge.tmdmaker.model.ModelElement;
import jp.sourceforge.tmdmaker.property.IPropertyAvailable;
import jp.sourceforge.tmdmaker.property.IdentifierPropertySource;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertySource;
import org.slf4j.LoggerFactory;

public class IdentifierRefTreeEditPart extends IdentifierTreeEditPart implements PropertyChangeListener,IPropertyAvailable {
	
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(IdentifierRefTreeEditPart.class);
	
	/**
	 * コンストラクタ
	 * @param identifier
	 */
	public IdentifierRefTreeEditPart(IdentifierRef identifier)
	{
		super(identifier);
	}
	
	@Override
	public IdentifierRef getModel() {
		return (IdentifierRef) super.getModel();
	}

	@Override
	protected String getText() {
		ModelElement model = getModel();
		if (model.getName() == null)
		{
			return "";
		}
		else{
			return model.getName() + "(R)"; //$NON-NLS-1$
		}
	}

	@Override
	protected Image getImage() {
		return TMDPlugin.getImage("icons/outline/identifier.png"); //$NON-NLS-1$
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		logger.debug(getClass() + "." + evt.getPropertyName());

		if (evt.getPropertyName().equals(IdentifierRef.PROPERTY_NAME)) {
			refreshVisuals();
	    } else {
			logger.warn("Not Handle Event Occured.");
		}
	}

	@Override
	public IPropertySource getPropertySource(TMDEditor editor) {
		return new IdentifierPropertySource(editor, this.getModel());
	}
}
