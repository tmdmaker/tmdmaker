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
package jp.sourceforge.tmdmaker.ui.editor.gef3.treeeditparts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertySource;
import org.slf4j.LoggerFactory;
import org.tmdmaker.ui.views.properties.IPropertyAvailable;
import org.tmdmaker.ui.views.properties.gef3.KeyModelPropertySource;

import jp.sourceforge.tmdmaker.TMDPlugin;
import jp.sourceforge.tmdmaker.model.IAttribute;
import jp.sourceforge.tmdmaker.model.KeyModel;
import jp.sourceforge.tmdmaker.ui.util.ModelEditUtils;

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
		return ModelEditUtils.toBlankStringIfNull(getModel().getName());
	}

	@Override
	protected Image getImage() {
		return TMDPlugin.getImage("icons/outline/key.png"); //$NON-NLS-1$
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		logger.debug("{}.{}", getClass(), evt.getPropertyName());
	}

	@Override
	public IPropertySource getPropertySource(CommandStack commandStack) {
		return new KeyModelPropertySource(commandStack, this.getModel());
	}
}
