/*
 * Copyright 2009-2019 TMD-Maker Project <https://tmdmaker.osdn.jp/>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.sourceforge.tmdmaker.ui.editor.gef3.treeeditparts;

import java.beans.PropertyChangeListener;

import jp.sourceforge.tmdmaker.TMDEditor;
import jp.sourceforge.tmdmaker.TMDPlugin;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.EntityType;
import jp.sourceforge.tmdmaker.model.ModelElement;
import jp.sourceforge.tmdmaker.ui.views.properties.EntityPropertySource;
import jp.sourceforge.tmdmaker.ui.views.properties.IPropertyAvailable;

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
