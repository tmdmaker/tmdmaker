/*
 * Copyright 2009-2010 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package jp.sourceforge.tmdmaker.editpolicy;

import jp.sourceforge.tmdmaker.editpart.AbstractModelEditPart;
import jp.sourceforge.tmdmaker.editpart.AttributeEditPart;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.ui.command.AttributeMoveCommand;
import jp.sourceforge.tmdmaker.ui.command.AttributeTransferCommand;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * エンティティ系モデルへのアトリビュート操作系のEditPolicy
 * 
 * @author nakaG
 * 
 */
public class EntityLayoutEditPolicy extends ToolbarLayoutEditPolicy {
	/** logging */
	private static Logger logger = LoggerFactory
			.getLogger(EntityLayoutEditPolicy.class);

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editpolicies.OrderedLayoutEditPolicy#createAddCommand(org.eclipse.gef.EditPart,
	 *      org.eclipse.gef.EditPart)
	 */
	@Override
	protected Command createAddCommand(EditPart child, EditPart after) {
		logger.debug(getClass() + "#createAddCommand()");
		if (!(child instanceof AttributeEditPart)) {
			logger.debug("child is not AttributeEditPart." + child);
			return null;
		}
		Attribute toMove = (Attribute) child.getModel();

		AbstractModelEditPart<?> originalEntityEditPart = (AbstractModelEditPart<?>) child
				.getParent();
		AbstractEntityModel originalEntity = (AbstractEntityModel) originalEntityEditPart
				.getModel();
		int oldIndex = originalEntityEditPart.getChildren().indexOf(child);

		AbstractModelEditPart<?> newEntityEditPart = null;
		int newIndex = 0;
		// アトリビュートが0件か最終行を指定した場合はnull
		if (after == null) {
			newEntityEditPart = (AbstractModelEditPart<?>) getHost();
			newIndex = newEntityEditPart.getChildren().size();
		} else if (after instanceof AttributeEditPart) {
			newEntityEditPart = (AbstractModelEditPart<?>) getHost();
			newIndex = newEntityEditPart.getChildren().indexOf(after);
		} else {
			logger.debug("after is null or not AttributeEditPart." + after);
			return null;
		}
		AbstractEntityModel newEntity = (AbstractEntityModel) newEntityEditPart
				.getModel();

		AttributeTransferCommand command = new AttributeTransferCommand(toMove,
				originalEntity, oldIndex, newEntity, newIndex);

		return command;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editpolicies.OrderedLayoutEditPolicy#createMoveChildCommand(org.eclipse.gef.EditPart,
	 *      org.eclipse.gef.EditPart)
	 */
	@Override
	protected Command createMoveChildCommand(EditPart child, EditPart after) {
		logger.debug(getClass() + "#createMoveChildCommand()");
		AbstractModelEditPart<?> parent = (AbstractModelEditPart<?>) getHost();
		AbstractEntityModel model = (AbstractEntityModel) parent.getModel();
		Attribute attribute = (Attribute) child.getModel();
		int oldIndex = parent.getChildren().indexOf(child);
		int newIndex = 0;
		if (after != null) {
			newIndex = parent.getChildren().indexOf(after);
		} else {
			newIndex = parent.getChildren().size();
		}
		return new AttributeMoveCommand(attribute, model, oldIndex, newIndex);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editpolicies.LayoutEditPolicy#getCreateCommand(org.eclipse.gef.requests.CreateRequest)
	 */
	@Override
	protected Command getCreateCommand(CreateRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
