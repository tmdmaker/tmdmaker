package jp.sourceforge.tmdmaker.editpolicy;

import jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart;
import jp.sourceforge.tmdmaker.editpart.AttributeEditPart;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.command.AttributeMoveCommand;
import jp.sourceforge.tmdmaker.model.command.AttributeTransferCommand;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;

/**
 * 
 * @author nakaG
 * 
 */
public class EntityLayoutEditPolicy extends ToolbarLayoutEditPolicy {
	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editpolicies.OrderedLayoutEditPolicy#createAddCommand(org.eclipse.gef.EditPart,
	 *      org.eclipse.gef.EditPart)
	 */
	@Override
	protected Command createAddCommand(EditPart child, EditPart after) {
		System.out.println(getClass() + "#createAddCommand()");
		if (!(child instanceof AttributeEditPart))
			return null;
		if (!(after instanceof AttributeEditPart))
			return null;

		Attribute toMove = (Attribute) child.getModel();

		AbstractEntityEditPart originalEntityEditPart = (AbstractEntityEditPart) child
				.getParent();
		AbstractEntityModel originalEntity = (AbstractEntityModel) originalEntityEditPart
				.getModel();
		AbstractEntityEditPart newEntityEditPart = (AbstractEntityEditPart) after
				.getParent();
		AbstractEntityModel newEntity = (AbstractEntityModel) newEntityEditPart
				.getModel();

		int oldIndex = originalEntityEditPart.getChildren().indexOf(child);
		int newIndex = newEntityEditPart.getChildren().indexOf(after);

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
		System.out.println(getClass() + "#createMoveChildCommand()");
		if (after != null) {
			AbstractEntityEditPart parent = (AbstractEntityEditPart) getHost();
			AbstractEntityModel model = (AbstractEntityModel) parent.getModel();
			Attribute attribute = (Attribute) child.getModel();
			int oldIndex = parent.getChildren().indexOf(child);
			int newIndex = parent.getChildren().indexOf(after);

			return new AttributeMoveCommand(attribute, model, oldIndex,
					newIndex);
		}
		return null;
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
