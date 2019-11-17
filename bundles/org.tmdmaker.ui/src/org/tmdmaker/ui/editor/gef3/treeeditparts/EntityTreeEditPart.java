/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org/>
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
package org.tmdmaker.ui.editor.gef3.treeeditparts;

import java.beans.PropertyChangeListener;

import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertySource;
import org.tmdmaker.core.model.Entity;
import org.tmdmaker.core.model.EntityType;
import org.tmdmaker.ui.Activator;
import org.tmdmaker.ui.util.ModelEditUtils;
import org.tmdmaker.ui.views.properties.IPropertyAvailable;
import org.tmdmaker.ui.views.properties.gef3.EntityPropertySource;

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
		return ModelEditUtils.toBlankStringIfNull(getModel().getName());
	}
	
	@Override
	protected Image getImage() {
		if (getModel().getEntityType() == EntityType.RESOURCE){
			return Activator.getImage("icons/outline/resource.png"); //$NON-NLS-1$
		}else if(getModel().getEntityType() == EntityType.EVENT){
			return Activator.getImage("icons/outline/event.png"); //$NON-NLS-1$
		}else if(getModel().getEntityType() == EntityType.MO){
			return Activator.getImage("icons/outline/multi_or.png"); //$NON-NLS-1$
		}
		return super.getImage();
	}

	@Override
	public IPropertySource getPropertySource(CommandStack commandStack) {
		return new EntityPropertySource(commandStack, this.getModel());
	}
}
