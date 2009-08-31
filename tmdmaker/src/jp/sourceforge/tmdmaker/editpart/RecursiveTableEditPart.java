package jp.sourceforge.tmdmaker.editpart;

import java.util.List;
import java.util.Map;

import jp.sourceforge.tmdmaker.dialog.EditAttribute;
import jp.sourceforge.tmdmaker.dialog.TableEditDialog2;
import jp.sourceforge.tmdmaker.editpolicy.AbstractEntityGraphicalNodeEditPolicy;
import jp.sourceforge.tmdmaker.figure.EntityFigure;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.ReusedIdentifier;
import jp.sourceforge.tmdmaker.model.RecursiveTable;
import jp.sourceforge.tmdmaker.model.command.ConnectableElementDeleteCommand;
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
public class RecursiveTableEditPart extends AbstractEntityEditPart {

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
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart#updateFigure(org.eclipse.draw2d.IFigure)
	 */
	@Override
	protected void updateFigure(IFigure figure) {
		EntityFigure entityFigure = (EntityFigure) figure;
		RecursiveTable table = (RecursiveTable) getModel();
		entityFigure.setNotImplement(table.isNotImplement());
		// List<Identifier> ids = table.getReuseKeys();
		List<Attribute> atts = table.getAttributes();
		entityFigure.removeAllRelationship();
		entityFigure.removeAllAttributes();

		entityFigure.setEntityName(table.getName());
		for (Map.Entry<AbstractEntityModel, ReusedIdentifier> rk : table.getReuseKey()
				.entrySet()) {
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
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new RecursiveTableComponentEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
				new AbstractEntityGraphicalNodeEditPolicy());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart#onDoubleClicked()
	 */
	@Override
	protected void onDoubleClicked() {
		logger.debug(getClass() + "#onDoubleClicked()");
		RecursiveTable table = (RecursiveTable) getModel();
//		TableEditDialog dialog = new TableEditDialog(getViewer().getControl()
//				.getShell(), table.getName(), table.getReuseKeys(), table
//				.getAttributes());
		TableEditDialog2 dialog = new TableEditDialog2(getViewer().getControl()
				.getShell(), "再帰表編集", table);
		if (dialog.open() == Dialog.OK) {
			CompoundCommand ccommand = new CompoundCommand();

			List<EditAttribute> editAttributeList = dialog
					.getEditAttributeList();
			addAttributeEditCommands(ccommand, table, editAttributeList);

			TableEditCommand<RecursiveTable> command = new TableEditCommand<RecursiveTable>(
					table, (RecursiveTable) dialog.getEditedValue());
			ccommand.add(command);
			getViewer().getEditDomain().getCommandStack().execute(ccommand);

//			TableEditCommand<RecursiveTable> command = new TableEditCommand<RecursiveTable>(
//					table, dialog.getEntityName(), dialog.getReuseKeys(),
//					dialog.getAttributes());
//			getViewer().getEditDomain().getCommandStack().execute(command);
		}

	}

	/**
	 * 
	 * @author nakaG
	 * 
	 */
	private static class RecursiveTableComponentEditPolicy extends
			ComponentEditPolicy {

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
		 */
		@Override
		protected Command createDeleteCommand(GroupRequest deleteRequest) {
			RecursiveTableDeleteCommand command = new RecursiveTableDeleteCommand(
					(Diagram) getHost().getParent().getModel(),
					(RecursiveTable) getHost().getModel());
			return command;
		}

	}

	/**
	 * 
	 * @author nakaG
	 * 
	 */
	private static class RecursiveTableDeleteCommand extends
			ConnectableElementDeleteCommand {
		/** 再帰表 */
		private RecursiveTable model;

		/**
		 * コンストラクタ
		 * 
		 * @param diagram
		 *            親
		 * @param model
		 *            再帰表
		 */
		public RecursiveTableDeleteCommand(Diagram diagram, RecursiveTable model) {
			super();
			this.diagram = diagram;
			this.model = model;
			this.sourceConnections.addAll(model.getModelSourceConnections());
			this.targetConnections.addAll(model.getModelTargetConnections());
		}

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		@Override
		public void execute() {

			diagram.removeChild(model);

			detachConnections(sourceConnections);
			detachConnections(targetConnections);
		}

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		@Override
		public void undo() {
			diagram.addChild(model);
			attathConnections(sourceConnections);
			attathConnections(targetConnections);
		}
	}
}
