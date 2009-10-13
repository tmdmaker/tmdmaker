package jp.sourceforge.tmdmaker.editpart;

import java.util.List;
import java.util.Map;

import jp.sourceforge.tmdmaker.dialog.DetailEditDialog;
import jp.sourceforge.tmdmaker.editpolicy.TMDModelGraphicalNodeEditPolicy;
import jp.sourceforge.tmdmaker.editpolicy.EntityLayoutEditPolicy;
import jp.sourceforge.tmdmaker.figure.EntityFigure;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Detail;
import jp.sourceforge.tmdmaker.model.EditAttribute;
import jp.sourceforge.tmdmaker.model.IdentifierRef;
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
 * ディテールのコントローラ
 * 
 * @author nakaG
 * 
 */
public class DetailEditPart extends AbstractEntityEditPart {
	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart#onDoubleClicked()
	 */
	@Override
	protected void onDoubleClicked() {
		logger.debug(getClass() + "#onDoubleClicked()");
		Detail table = (Detail) getModel();
		// TableEditDialog dialog = new TableEditDialog(getViewer().getControl()
		// .getShell(), table.getName(), table.getReusedIdentifieres(), table
		// .getAttributes());
		DetailEditDialog dialog = new DetailEditDialog(getViewer().getControl()
				.getShell(), table);
		if (dialog.open() == Dialog.OK) {
			CompoundCommand ccommand = new CompoundCommand();

			List<EditAttribute> editAttributeList = dialog
					.getEditAttributeList();
			addAttributeEditCommands(ccommand, table, editAttributeList);

			DetailEditCommand command = new DetailEditCommand(table,
					(Detail) dialog.getEditedValue());
			ccommand.add(command);
			getViewer().getEditDomain().getCommandStack().execute(ccommand);
			// TableEditCommand<Detail> command = new TableEditCommand<Detail>(
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
		Detail entity = (Detail) getModel();

		entityFigure.setNotImplement(entity.isNotImplement());
		// List<Attribute> atts = entity.getAttributes();
		entityFigure.removeAllRelationship();
		// entityFigure.removeAllAttributes();

		entityFigure.setEntityName(entity.getName());
		// entityFigure.setEntityType(entity.getEntityType().toString());
		// figure.setIdentifier(entity.getIdentifier().getName());
		IdentifierRef original = entity.getOriginalReusedIdentifier()
				.getIdentifires().get(0);
		entityFigure.setIdentifier(original.getName());
		entityFigure.setIdentifier(entity.getDetailIdentifier().getName());
		for (Map.Entry<AbstractEntityModel, ReusedIdentifier> rk : entity
				.getReusedIdentifieres().entrySet()) {

			for (IdentifierRef i : rk.getValue().getIdentifires()) {
				if (i.isSame(original)) {
					// nothing
				} else {
					entityFigure.addRelationship(i.getName());
				}
			}
		}
		// for (Attribute a : atts) {
		// entityFigure.addAttribute(a.getName());
		// }
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
				new DetailComponentEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
				new TMDModelGraphicalNodeEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new EntityLayoutEditPolicy());
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
		return ((AbstractEntityModel) getModel()).getAttributes();
	}

	/**
	 * 
	 * @author nakaG
	 * 
	 */
	private static class DetailComponentEditPolicy extends ComponentEditPolicy {

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
		 */
		@Override
		protected Command createDeleteCommand(GroupRequest deleteRequest) {
			Detail model = (Detail) getHost().getModel();
			return new TableDeleteCommand(model, model
					.getModelTargetConnections().get(0));

		}
	}

	private static class DetailEditCommand extends TableEditCommand<Detail> {
		private String oldDetailIdentifierName;

		/**
		 * 
		 * @param toBeEdit
		 * @param newValue
		 */
		public DetailEditCommand(Detail toBeEdit, Detail newValue) {
			super(toBeEdit, newValue);
			oldDetailIdentifierName = toBeEdit.getDetailIdentifier().getName();
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see jp.sourceforge.tmdmaker.model.command.TableEditCommand#execute()
		 */
		@Override
		public void execute() {
			super.execute();
			model.setDetailIdeitifierName(newValue.getDetailIdentifier()
					.getName());
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see jp.sourceforge.tmdmaker.model.command.TableEditCommand#undo()
		 */
		@Override
		public void undo() {
			super.undo();
			model.setDetailIdeitifierName(oldDetailIdentifierName);
		}

	}
}
