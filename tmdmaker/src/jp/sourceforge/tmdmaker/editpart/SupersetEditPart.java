package jp.sourceforge.tmdmaker.editpart;

import java.util.List;

import jp.sourceforge.tmdmaker.dialog.MultivalueAndSupersetEditDialog;
import jp.sourceforge.tmdmaker.figure.EntityFigure;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.EntityType;
import jp.sourceforge.tmdmaker.model.IdentifierRef;
import jp.sourceforge.tmdmaker.model.MultivalueAndSuperset;
import jp.sourceforge.tmdmaker.model.command.TableDeleteCommand;
import jp.sourceforge.tmdmaker.model.command.VirtualSupersetEditCommand;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.jface.dialogs.Dialog;

/**
 * 
 * @author nakaG
 * 
 */
public class SupersetEditPart extends AbstractEntityEditPart {
	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart#onDoubleClicked()
	 */
	@Override
	protected void onDoubleClicked() {
		AbstractEntityModel entity = (AbstractEntityModel) getModel();
		MultivalueAndSupersetEditDialog dialog = new MultivalueAndSupersetEditDialog(getViewer().getControl().getShell(), entity.getName());
		if (dialog.open() == Dialog.OK) {
			getViewer().getEditDomain().getCommandStack().execute(new VirtualSupersetEditCommand(entity, dialog.getInputName()));
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
		logger.debug(getClass() + "#updateFigure()");
		EntityFigure entityFigure = (EntityFigure) figure;
		MultivalueAndSuperset entity = (MultivalueAndSuperset) getModel();

		List<Attribute> atts = entity.getAttributes();
		entityFigure.removeAllRelationship();
		entityFigure.removeAllAttributes();

		entityFigure.setEntityName(entity.getName());
		entityFigure.setEntityType(EntityType.MA.getLabel());
		// figure.setIdentifier(entity.getIdentifier().getName());
		IdentifierRef identifierRef = entity.getReusedIdentifieres().entrySet()
				.iterator().next().getValue().getIdentifires().get(0);
		entityFigure.setIdentifier(identifierRef.getName());
		// for (Map.Entry<AbstractEntityModel, ReusedIdentifier> rk : entity
		// .getReusedIdentifieres().entrySet()) {
		// for (Identifier i : rk.getValue().getIdentifires()) {
		// entityFigure.addRelationship(i.getName());
		// }
		// }
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
				new SupersetComponentEditPolicy());

	}

	/**
	 * 
	 * @author nakaG
	 * 
	 */
	private static class SupersetComponentEditPolicy extends
			ComponentEditPolicy {

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
		 */
		@Override
		protected Command createDeleteCommand(GroupRequest deleteRequest) {
			MultivalueAndSuperset model = (MultivalueAndSuperset) getHost().getModel();
			return new TableDeleteCommand(model, model.getDetail()
					.getModelTargetConnections().get(0));
		}

	}
}
