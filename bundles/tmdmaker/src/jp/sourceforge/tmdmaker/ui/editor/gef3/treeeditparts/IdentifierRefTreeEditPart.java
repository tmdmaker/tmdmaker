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

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertySource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tmdmaker.ui.views.properties.IPropertyAvailable;
import org.tmdmaker.ui.views.properties.gef3.IAttributePropertySource;

import jp.sourceforge.tmdmaker.TMDPlugin;
import jp.sourceforge.tmdmaker.model.IdentifierRef;
import jp.sourceforge.tmdmaker.model.ModelElement;

public class IdentifierRefTreeEditPart extends IdentifierTreeEditPart
		implements PropertyChangeListener, IPropertyAvailable {

	private static Logger logger = LoggerFactory
			.getLogger(IdentifierRefTreeEditPart.class);

	/**
	 * コンストラクタ
	 * 
	 * @param identifier
	 */
	public IdentifierRefTreeEditPart(IdentifierRef identifier) {
		super(identifier);
	}

	@Override
	public IdentifierRef getModel() {
		return (IdentifierRef) super.getModel();
	}

	@Override
	protected String getText() {
		ModelElement model = getModel();
		if (model.getName() == null) {
			return "";
		} else {
			return model.getName() + "(R)"; //$NON-NLS-1$
		}
	}

	@Override
	protected Image getImage() {
		return TMDPlugin.getImage("icons/outline/identifier.png"); //$NON-NLS-1$
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		logger.debug("{}.{}", getClass(), evt.getPropertyName());

		if (evt.getPropertyName().equals(IdentifierRef.PROPERTY_NAME)) {
			refreshVisuals();
		} else {
			logger.warn("Not Handle Event Occured.");
		}
	}

	@Override
	public IPropertySource getPropertySource(CommandStack commandStack) {
		return new IAttributePropertySource(commandStack, this.getModel());
	}
}
