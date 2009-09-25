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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
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

		AbstractEntityEditPart originalEntityEditPart = (AbstractEntityEditPart) child
				.getParent();
		AbstractEntityModel originalEntity = (AbstractEntityModel) originalEntityEditPart
				.getModel();
		int oldIndex = originalEntityEditPart.getChildren().indexOf(child);

		AbstractEntityEditPart newEntityEditPart = null;
		int newIndex = 0;
		// アトリビュートが0件か最終行を指定した場合はnull
		if (after == null) {
			newEntityEditPart = (AbstractEntityEditPart) getHost();
			newIndex = newEntityEditPart.getChildren().size();
		} else if (after instanceof AttributeEditPart) {
			newEntityEditPart = (AbstractEntityEditPart) getHost();
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
		AbstractEntityEditPart parent = (AbstractEntityEditPart) getHost();
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
