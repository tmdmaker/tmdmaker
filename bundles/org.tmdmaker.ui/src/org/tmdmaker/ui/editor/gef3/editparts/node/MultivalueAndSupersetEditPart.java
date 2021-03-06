/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org>
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
package org.tmdmaker.ui.editor.gef3.editparts.node;

import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.jface.dialogs.Dialog;
import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.MultivalueAndSuperset;
import org.tmdmaker.ui.dialogs.ModelEditDialog;
import org.tmdmaker.ui.dialogs.MultivalueAndSupersetEditDialog;
import org.tmdmaker.ui.editor.draw2d.figure.node.AbstractModelFigure;
import org.tmdmaker.ui.editor.draw2d.figure.node.MultivalueAndSupersetFigure;
import org.tmdmaker.ui.editor.gef3.commands.ModelEditCommand;
import org.tmdmaker.ui.editor.gef3.commands.TableDeleteCommand;
import org.tmdmaker.ui.editor.gef3.editpolicies.AbstractEntityModelEditPolicy;
import org.tmdmaker.ui.editor.gef3.editpolicies.ReconnectableNodeEditPolicy;

/**
 * 多値のANDの概念的スーパーセットのコントローラ
 * 
 * @author nakaG
 * 
 */
public class MultivalueAndSupersetEditPart
		extends AbstractEntityModelEditPart<MultivalueAndSuperset> {

	/**
	 * コンストラクタ
	 */
	public MultivalueAndSupersetEditPart(MultivalueAndSuperset entity) {
		super();
		setModel(entity);
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.ui.editor.gef3.editparts.node.AbstractEntityModelEditPart#createTMDFigure()
	 */
	@Override
	protected AbstractModelFigure<MultivalueAndSuperset> createTMDFigure() {
		return new MultivalueAndSupersetFigure();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new SupersetComponentEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new ReconnectableNodeEditPolicy());
	}

	/**
	 * 
	 * @author nakaG
	 * 
	 */
	private static class SupersetComponentEditPolicy
			extends AbstractEntityModelEditPolicy<MultivalueAndSuperset> {
		@Override
		protected ModelEditDialog<MultivalueAndSuperset> getDialog() {
			return new MultivalueAndSupersetEditDialog(getControllShell(), getModel());
		}

		/**
		 * 
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.gef.editparts.AbstractEditPart#getCommand(org.eclipse.gef.Request)
		 */
		@Override
		public Command getCommand(Request request) {
			if (REQ_OPEN.equals(request.getType())) {
				AbstractEntityModel entity = getModel();
				ModelEditDialog<MultivalueAndSuperset> dialog = getDialog();

				if (dialog.open() == Dialog.OK) {
					return new ModelEditCommand(entity, dialog.getEditedValue());
				}
			}
			return null;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
		 */
		@Override
		protected Command createDeleteCommand(GroupRequest deleteRequest) {
			return new TableDeleteCommand(getModel(),
					getModel().getDetail().getModelTargetConnections().get(0));
		}
	}
}
