package jp.sourceforge.tmdmaker.editpart;

import java.util.List;
import java.util.Map;

import jp.sourceforge.tmdmaker.dialog.EditAttribute;
import jp.sourceforge.tmdmaker.dialog.TableEditDialog2;
import jp.sourceforge.tmdmaker.editpolicy.AbstractEntityGraphicalNodeEditPolicy;
import jp.sourceforge.tmdmaker.figure.EntityFigure;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.AbstractRelationship;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.EntityType;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.MultivalueOrEntity;
import jp.sourceforge.tmdmaker.model.ReUseKeys;
import jp.sourceforge.tmdmaker.model.command.TableEditCommand;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.jface.dialogs.Dialog;

/**
 * 
 * @author nakaG
 * 
 */
public class MultivalueOrEditPart extends AbstractEntityEditPart {

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart#onDoubleClicked()
	 */
	@Override
	protected void onDoubleClicked() {
		logger.debug(getClass() + "#onDoubleClicked()");
		MultivalueOrEntity table = (MultivalueOrEntity) getModel();
		// TableEditDialog dialog = new TableEditDialog(getViewer().getControl()
		// .getShell(), table.getName(), table.getReuseKeys(), table
		// .getAttributes());
		TableEditDialog2 dialog = new TableEditDialog2(getViewer().getControl()
				.getShell(), "多値のOR表編集", table);
		if (dialog.open() == Dialog.OK) {
			CompoundCommand ccommand = new CompoundCommand();

			List<EditAttribute> editAttributeList = dialog
					.getEditAttributeList();
			addAttributeEditCommands(ccommand, table, editAttributeList);

			TableEditCommand<MultivalueOrEntity> command = new TableEditCommand<MultivalueOrEntity>(
					table, (MultivalueOrEntity) dialog.getEditedValue());
			ccommand.add(command);
			getViewer().getEditDomain().getCommandStack().execute(ccommand);
			// TableEditCommand<MultivalueOrEntity> command = new
			// TableEditCommand<MultivalueOrEntity>(
			// table, dialog.getEntityName(), dialog.getReuseKeys(),
			// dialog.getAttributes());
			// getViewer().getEditDomain().getCommandStack().execute(command);
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart#updateFigure(org.eclipse.draw2d.IFigure)
	 */
	@Override
	protected void updateFigure(IFigure figure) {
		EntityFigure entityFigure = (EntityFigure) figure;
		MultivalueOrEntity entity = (MultivalueOrEntity) getModel();
		entityFigure.setNotImplement(entity.isNotImplement());

		List<Attribute> atts = entity.getAttributes();
		entityFigure.removeAllRelationship();
		entityFigure.removeAllAttributes();

		entityFigure.setEntityName(entity.getName());
		entityFigure.setEntityType(EntityType.MO.getLabel());
		for (Map.Entry<AbstractEntityModel, ReUseKeys> rk : entity
				.getReuseKeys().entrySet()) {
			for (Identifier i : rk.getValue().getIdentifires()) {
				entityFigure.addRelationship(i.getName());
			}
		}
		for (Attribute a : atts) {
			entityFigure.addAttribute(a.getName());
		}

	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		EntityFigure figure = new EntityFigure();
		updateFigure(figure);
		return figure;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new MultivalueOrEntityComponentEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
				new AbstractEntityGraphicalNodeEditPolicy());
	}

	/**
	 * 
	 * @author nakaG
	 * 
	 */
	private static class MultivalueOrEntityComponentEditPolicy extends
			ComponentEditPolicy {

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
		 */
		@Override
		protected Command createDeleteCommand(GroupRequest deleteRequest) {
			MultivalueOrEntity model = (MultivalueOrEntity) getHost()
					.getModel();
			MultivalueOrEntityDeleteCommand command = new MultivalueOrEntityDeleteCommand(
					model);
			return command;
		}

	}

	/**
	 * 
	 * @author nakaG
	 * 
	 */
	private static class MultivalueOrEntityDeleteCommand extends Command {
		/** 削除対象 */
		private MultivalueOrEntity model;
		/** 元エンティティと多値のORを接続するリレーションシップ */
		private AbstractRelationship relationship;

		/**
		 * コンストラクタ
		 * 
		 * @param model
		 *            削除対象
		 */
		public MultivalueOrEntityDeleteCommand(MultivalueOrEntity model) {
			this.model = model;
			this.relationship = (AbstractRelationship) model
					.getModelTargetConnections().get(0);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#canExecute()
		 */
		@Override
		public boolean canExecute() {
			return model.canDeletable();
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		@Override
		public void execute() {
			relationship.disconnect();
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		@Override
		public void undo() {
			relationship.connect();
		}

	}
}
