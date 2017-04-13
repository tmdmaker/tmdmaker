package jp.sourceforge.tmdmaker.ui.editor.gef3.treeeditparts;

import java.beans.PropertyChangeListener;

import jp.sourceforge.tmdmaker.TMDEditor;
import jp.sourceforge.tmdmaker.TMDPlugin;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.EntityType;
import jp.sourceforge.tmdmaker.model.ModelElement;
import jp.sourceforge.tmdmaker.property.EntityPropertySource;
import jp.sourceforge.tmdmaker.property.IPropertyAvailable;

import org.eclipse.gef.EditPolicy;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * @author ny@cosmichorror.org
 *
 */
public class EntityTreeEditPart extends AbstractEntityModelTreeEditPart<Entity> implements
		PropertyChangeListener,IPropertyAvailable {
	
	/**
	 * コンストラクタ
	 * @param model
	 */
	public EntityTreeEditPart(Entity model, EditPolicy policy) {
		super(model, policy);
	}

	@Override
	protected void setIdentifiers(){
		identifiers.add(0, getModel().getIdentifier());
		super.setIdentifiers();
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
		if (getModel().getEntityType() == EntityType.RESOURCE){
			return TMDPlugin.getImage("icons/outline/resource.png"); //$NON-NLS-1$
		}else if(getModel().getEntityType() == EntityType.EVENT){
			return TMDPlugin.getImage("icons/outline/event.png"); //$NON-NLS-1$
		}else if(getModel().getEntityType() == EntityType.MO){
			return TMDPlugin.getImage("icons/outline/multi_or.png"); //$NON-NLS-1$
		}
		return super.getImage();
	}

	@Override
	public IPropertySource getPropertySource(TMDEditor editor) {
		return new EntityPropertySource(editor, this.getModel());
	}
}
