/*
 * Copyright 2009-2014 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

package jp.sourceforge.tmdmaker.treeeditpart;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import jp.sourceforge.tmdmaker.TMDEditor;
import jp.sourceforge.tmdmaker.TMDPlugin;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.ModelElement;
import jp.sourceforge.tmdmaker.property.AttributePropertySource;
import jp.sourceforge.tmdmaker.property.IPropertyAvailable;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertySource;
import org.slf4j.LoggerFactory;
import org.eclipse.gef.editparts.AbstractTreeEditPart;

public class AttributeTreeEditPart extends AbstractTreeEditPart implements PropertyChangeListener,IPropertyAvailable {
	
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(AttributeTreeEditPart.class);
	
	/**
	 * コンストラクタ
	 * @param attribute
	 */
	public AttributeTreeEditPart(Attribute attribute)
	{
		super();
		setModel(attribute);
	}
	
	@Override
	public Attribute getModel() {
		return (Attribute) super.getModel();
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
		return TMDPlugin.getImage("icons/outline/attribute.png");
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

		if (evt.getPropertyName().equals(Attribute.PROPERTY_NAME)) {
			refreshVisuals();
	    } else {
			logger.warn("Not Handle Event Occured.");
		}
	}

	@Override
	public IPropertySource getPropertySource(TMDEditor editor) {
		return new AttributePropertySource(editor, this.getModel());
	}
}
