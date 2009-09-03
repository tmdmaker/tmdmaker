package jp.sourceforge.tmdmaker.editpart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.sourceforge.tmdmaker.dialog.EditAttribute;
import jp.sourceforge.tmdmaker.dialog.TableEditDialog2;
import jp.sourceforge.tmdmaker.editpolicy.AbstractEntityGraphicalNodeEditPolicy;
import jp.sourceforge.tmdmaker.figure.EntityFigure;
import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.CombinationTable;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.RecursiveTable;
import jp.sourceforge.tmdmaker.model.ReusedIdentifier;
import jp.sourceforge.tmdmaker.model.command.TableDeleteCommand;
import jp.sourceforge.tmdmaker.model.command.TableEditCommand;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.jface.dialogs.Dialog;

/**
 * 対照表のコントローラ
 * 
 * @author nakaG
 * 
 */
public class CombinationTableEditPart extends AbstractEntityEditPart {

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
		logger.debug(getClass() + "#updateFigure()");
		EntityFigure entityFigure = (EntityFigure) figure;
		CombinationTable table = (CombinationTable) getModel();

		entityFigure.setNotImplement(table.isNotImplement());
		List<Attribute> atts = table.getAttributes();
		entityFigure.removeAllRelationship();
		entityFigure.removeAllAttributes();

		entityFigure.setEntityName(table.getName());
		List<String> reusedIdentifierNames = new ArrayList<String>();
		for (Map.Entry<AbstractEntityModel, ReusedIdentifier> rk : table
				.getReusedIdentifieres().entrySet()) {
			for (Identifier i : rk.getValue().getIdentifires()) {
				if (!reusedIdentifierNames.contains(i.getName())
						|| rk.getKey() instanceof RecursiveTable) {
					reusedIdentifierNames.add(i.getName());
				}
			}
		}
		for (String name : reusedIdentifierNames) {
			entityFigure.addRelationship(name);
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
				new CombinationTableComponentEditPolicy());
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
		CombinationTable table = (CombinationTable) getModel();
		// TableEditDialog dialog = new TableEditDialog(getViewer().getControl()
		// .getShell(), table.getName(), table.getReuseKeys(), table
		// .getAttributes());
		TableEditDialog2 dialog = new TableEditDialog2(getViewer().getControl()
				.getShell(), "対照表編集", table);
		if (dialog.open() == Dialog.OK) {
			CompoundCommand ccommand = new CompoundCommand();

			List<EditAttribute> editAttributeList = dialog
					.getEditAttributeList();
			addAttributeEditCommands(ccommand, table, editAttributeList);

			TableEditCommand<CombinationTable> command = new TableEditCommand<CombinationTable>(
					table, (CombinationTable) dialog.getEditedValue());
			ccommand.add(command);
			getViewer().getEditDomain().getCommandStack().execute(ccommand);
			// TableEditCommand<CombinationTable> command = new
			// TableEditCommand<CombinationTable>(
			// table, dialog.getEntityName(), dialog.getReuseKeys(),
			// dialog.getAttributes());
			// getViewer().getEditDomain().getCommandStack().execute(command);
		}

	}

	/**
	 * 
	 * @author nakaG
	 * 
	 */
	private static class CombinationTableComponentEditPolicy extends
			ComponentEditPolicy {
		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
		 */
		@Override
		protected Command createDeleteCommand(GroupRequest deleteRequest) {
//			CombinationTableDeleteCommand command = new CombinationTableDeleteCommand(
//					(CombinationTable) getHost().getModel());
//			return command;
			CombinationTable model = (CombinationTable) getHost().getModel();
			AbstractConnectionModel creationRelationship = (AbstractConnectionModel) model.findCreationRelationship().getSource();
			return new TableDeleteCommand(model, creationRelationship); 
		}
	}

//	/**
//	 * 
//	 * @author nakaG
//	 * 
//	 */
//	private static class CombinationTableDeleteCommand extends Command {
//
//		/** 削除対象の対照表 */
//		private CombinationTable model;
//		/** 対照表とリレーションシップ間のコネクション */
//		private RelatedRelationship relatedRelationship;
//		/** 対照表を作成する契機となったリレーションシップ */
//		private AbstractRelationship relationship;
//
//		// private List<AbstractConnectionModel> sourceConnections = new
//		// ArrayList<AbstractConnectionModel>();
//		// private List<AbstractConnectionModel> targetConnections = new
//		// ArrayList<AbstractConnectionModel>();
//
//		/**
//		 * コンストラクタ
//		 * 
//		 * @param model
//		 *            削除対象モデル
//		 */
//		public CombinationTableDeleteCommand(CombinationTable model) {
//			// this.diagram = diagram;
//			this.model = model;
//			this.relatedRelationship = model.findCreationRelationship();
//			relationship = (AbstractRelationship) relatedRelationship
//					.getSource();
//		}
//
//		/**
//		 * 
//		 * {@inheritDoc}
//		 * 
//		 * @see org.eclipse.gef.commands.Command#canExecute()
//		 */
//		@Override
//		public boolean canExecute() {
//			return model.isDeletable();
//		}
//
//		/**
//		 * 
//		 * {@inheritDoc}
//		 * 
//		 * @see org.eclipse.gef.commands.Command#execute()
//		 */
//		@Override
//		public void execute() {
//			relationship.disconnect();
//		}
//
//		/**
//		 * 
//		 * {@inheritDoc}
//		 * 
//		 * @see org.eclipse.gef.commands.Command#undo()
//		 */
//		@Override
//		public void undo() {
//			relationship.connect();
//		}
//
//	}
}
