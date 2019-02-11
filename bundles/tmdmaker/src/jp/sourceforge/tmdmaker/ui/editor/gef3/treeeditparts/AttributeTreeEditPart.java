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

import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.gef.tools.SelectEditPartTracker;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertySource;
import org.slf4j.LoggerFactory;

import jp.sourceforge.tmdmaker.TMDEditor;
import jp.sourceforge.tmdmaker.TMDPlugin;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.ModelElement;
import jp.sourceforge.tmdmaker.ui.editor.gef3.editparts.IAttributeEditPart;
import jp.sourceforge.tmdmaker.ui.views.properties.AttributePropertySource;
import jp.sourceforge.tmdmaker.ui.views.properties.IPropertyAvailable;

public class AttributeTreeEditPart extends AbstractTreeEditPart implements PropertyChangeListener,IPropertyAvailable, IAttributeEditPart {
	
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(AttributeTreeEditPart.class);
	
	protected EditPolicy componentPolicy;
	
	/**
	 * コンストラクタ
	 * @param attribute
	 */
	public AttributeTreeEditPart(Attribute attribute, EditPolicy policy)
	{
		super();
		setModel(attribute);
		this.componentPolicy = policy;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, this.componentPolicy);
	}
	
	@Override
	public Attribute getModel() {
		return (Attribute) super.getModel();
	}
	
	@Override
	public DragTracker getDragTracker(Request request) {
		return new SelectEditPartTracker(this);
	}
	
	@Override
	public void performRequest(Request request) {
		if (request.getType() == RequestConstants.REQ_OPEN) {
			Command ccommand = getCommand(request);
			executeEditCommand(ccommand);
		}
	}
	
	/**
	 * 編集コマンドを実行する。
	 *
	 * @param command
	 */
	protected void executeEditCommand(Command command) {
		getViewer().getEditDomain().getCommandStack().execute(command);
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
		return TMDPlugin.getImage("icons/outline/attribute.png"); //$NON-NLS-1$
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

	@Override
	public AbstractEntityModel getParentModel() {
		EditPart part = getParent().getParent();
		if (part instanceof EntityTreeEditPart) {
			return (AbstractEntityModel) part.getModel();
		}
		// part is a key definition
		return (AbstractEntityModel) part.getParent().getModel();
	}
}
