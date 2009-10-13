package jp.sourceforge.tmdmaker.editpart;

import java.util.List;
import java.util.Map;

import jp.sourceforge.tmdmaker.dialog.TableEditDialog;
import jp.sourceforge.tmdmaker.editpolicy.EntityLayoutEditPolicy;
import jp.sourceforge.tmdmaker.figure.EntityFigure;
import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.EditAttribute;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.MappingList;
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
 * 対応表のコントローラ
 * 
 * @author nakaG
 * 
 */
public class MappingListEditPart extends AbstractEntityEditPart {
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
		MappingList table = (MappingList) getModel();
		entityFigure.setNotImplement(table.isNotImplement());
		// List<Identifier> ids = table.getReuseKeys();
//		List<Attribute> atts = table.getAttributes();
		entityFigure.removeAllRelationship();
//		entityFigure.removeAllAttributes();

		entityFigure.setEntityName(table.getName());
		for (Map.Entry<AbstractEntityModel, ReusedIdentifier> rk : table
				.getReusedIdentifieres().entrySet()) {
			for (Identifier i : rk.getValue().getIdentifires()) {
				entityFigure.addRelationship(i.getName());
			}
		}
//		for (Attribute a : atts) {
//			entityFigure.addAttribute(a.getName());
//		}
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
				new MappingListComponentEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new EntityLayoutEditPolicy());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart#onDoubleClicked()
	 */
	@Override
	protected void onDoubleClicked() {
		logger.debug(getClass() + "#onDoubleClicked()");
		MappingList table = (MappingList) getModel();
		// TableEditDialog dialog = new TableEditDialog(getViewer().getControl()
		// .getShell(), table.getName(), table.getReuseKeys(), table
		// .getAttributes());
		TableEditDialog dialog = new TableEditDialog(getViewer().getControl()
				.getShell(), "対応表編集", table);
		if (dialog.open() == Dialog.OK) {
			CompoundCommand ccommand = new CompoundCommand();

			List<EditAttribute> editAttributeList = dialog
					.getEditAttributeList();
			addAttributeEditCommands(ccommand, table, editAttributeList);

			TableEditCommand<MappingList> command = new TableEditCommand<MappingList>(
					table, (MappingList) dialog.getEditedValue());
			ccommand.add(command);
			getViewer().getEditDomain().getCommandStack().execute(ccommand);
			// TableEditCommand<MappingList> command = new
			// TableEditCommand<MappingList>(
			// table, dialog.getEntityName(), dialog.getReuseKeys(),
			// dialog.getAttributes());
			// // MappingListEditCommand command = new MappingListEditCommand(
			// // table, dialog.getEntityName(), dialog.getReuseKeys(),
			// // dialog.getAttributes());
			// getViewer().getEditDomain().getCommandStack().execute(command);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#getContentPane()
	 */
	@Override
	public IFigure getContentPane() {
		return ((EntityFigure) getFigure()).getAttributeCompartmentFigure();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected List getModelChildren() {
		return 
		((AbstractEntityModel) getModel()).getAttributes();
	}

	/**
	 * 
	 * @author nakaG
	 * 
	 */
	private static class MappingListComponentEditPolicy extends
			ComponentEditPolicy {
		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
		 */
		@Override
		protected Command createDeleteCommand(GroupRequest deleteRequest) {
			// MappingListDeleteCommand command = new MappingListDeleteCommand(
			// (MappingList) getHost().getModel());
			// return command;
			MappingList model = (MappingList) getHost().getModel();
			AbstractConnectionModel creationRelationship = (AbstractConnectionModel) model
					.findCreationRelationship().getSource();
			return new TableDeleteCommand(model, creationRelationship);
		}

	}
	// /**
	// *
	// * @author nakaG
	// *
	// */
	// private static class MappingListDeleteCommand extends Command {
	// // private Diagram diagram;
	// /** 削除対象の対応表 */
	// private MappingList model;
	// /** 対応表とリレーションシップ間のコネクション */
	// private RelatedRelationship relatedRelationship;
	// /** 対応表を作成する契機となったリレーションシップ */
	// private AbstractRelationship relationship;
	//
	// /**
	// * コンストラクタ
	// *
	// * @param model
	// * 削除対象モデル
	// */
	// public MappingListDeleteCommand(MappingList model) {
	// this.model = model;
	// // this.diagram = model.getDiagram();
	// this.relatedRelationship = model.findCreationRelationship();
	// this.relationship = (AbstractRelationship) relatedRelationship
	// .getSource();
	// }
	//
	// /**
	// * {@inheritDoc}
	// *
	// * @see org.eclipse.gef.commands.Command#canExecute()
	// */
	// @Override
	// public boolean canExecute() {
	// return model.isDeletable();
	// }
	//
	// /**
	// *
	// * {@inheritDoc}
	// *
	// * @see org.eclipse.gef.commands.Command#execute()
	// */
	// @Override
	// public void execute() {
	// // diagram.removeChild(model);
	// // model.setDiagram(null);
	// relationship.disconnect();
	// }
	//
	//
	// /**
	// *
	// * {@inheritDoc}
	// *
	// * @see org.eclipse.gef.commands.Command#undo()
	// */
	// @Override
	// public void undo() {
	// // diagram.addChild(model);
	// // model.setDiagram(diagram);
	// relationship.connect();
	// }
	// }
}
