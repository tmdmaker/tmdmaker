package jp.sourceforge.tmdmaker.treeeditpart;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import jp.sourceforge.tmdmaker.TMDEditor;
import jp.sourceforge.tmdmaker.TMDPlugin;
import jp.sourceforge.tmdmaker.model.IAttribute;
import jp.sourceforge.tmdmaker.model.KeyModel;
import jp.sourceforge.tmdmaker.property.IPropertyAvailable;
import jp.sourceforge.tmdmaker.property.KeyModelPropertySource;

import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertySource;
import org.slf4j.LoggerFactory;

public class KeyModelTreeEditPart extends AbstractTreeEditPart implements PropertyChangeListener,IPropertyAvailable {
	
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(KeyModelTreeEditPart.class);
	
	@Override
	public KeyModel getModel() {
		return (KeyModel) super.getModel();
	}
	
	@Override
	protected List<IAttribute> getModelChildren() {
		return getModel().getAttributes();
	}
	
	@Override
	protected String getText() {
		KeyModel model = getModel();
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
		return TMDPlugin.getImage("icons/outline/key.png");
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		logger.debug(getClass() + "." + evt.getPropertyName());
	}

	@Override
	public IPropertySource getPropertySource(TMDEditor editor) {
		return new KeyModelPropertySource(editor, this.getModel());
	}
}
