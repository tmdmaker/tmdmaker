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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertySource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.sourceforge.tmdmaker.TMDEditor;
import jp.sourceforge.tmdmaker.TMDPlugin;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.ModelElement;
import jp.sourceforge.tmdmaker.ui.views.properties.IAttributePropertySource;
import jp.sourceforge.tmdmaker.ui.views.properties.IPropertyAvailable;

public class IdentifierTreeEditPart extends AbstractTreeEditPart
		implements PropertyChangeListener, IPropertyAvailable {

	private static Logger logger = LoggerFactory.getLogger(IdentifierTreeEditPart.class);

	/**
	 * コンストラクタ
	 * 
	 * @param identifier
	 */
	public IdentifierTreeEditPart(Identifier identifier) {
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
		if (model.getName() == null) {
			return "";
		} else {
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
		logger.debug("{}.{}", getClass(), evt.getPropertyName());

		if (evt.getPropertyName().equals(Identifier.PROPERTY_NAME)) {
			refreshVisuals();
		} else {
			logger.warn("Not Handle Event Occured."); //$NON-NLS-1$
		}
	}

	@Override
	public IPropertySource getPropertySource(TMDEditor editor) {
		return new IAttributePropertySource(editor, this.getModel());
	}
}
