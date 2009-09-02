package jp.sourceforge.tmdmaker.editpart;

import java.util.List;
import java.util.Map;

import jp.sourceforge.tmdmaker.dialog.EditAttribute;
import jp.sourceforge.tmdmaker.dialog.TableEditDialog2;
import jp.sourceforge.tmdmaker.editpolicy.AbstractEntityGraphicalNodeEditPolicy;
import jp.sourceforge.tmdmaker.figure.EntityFigure;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.Detail;
import jp.sourceforge.tmdmaker.model.IdentifierRef;
import jp.sourceforge.tmdmaker.model.ReusedIdentifier;
import jp.sourceforge.tmdmaker.model.command.TableEditCommand;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.CompoundCommand;
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
		TableEditDialog2 dialog = new TableEditDialog2(getViewer().getControl()
				.getShell(), "DTL表編集", table);
		if (dialog.open() == Dialog.OK) {
			CompoundCommand ccommand = new CompoundCommand();

			List<EditAttribute> editAttributeList = dialog
					.getEditAttributeList();
			addAttributeEditCommands(ccommand, table, editAttributeList);

			TableEditCommand<Detail> command = new TableEditCommand<Detail>(
					table, (Detail) dialog.getEditedValue());
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
		System.out.println("Detail updateFigure()");
		EntityFigure entityFigure = (EntityFigure) figure;
		Detail entity = (Detail) getModel();

		entityFigure.setNotImplement(entity.isNotImplement());
		List<Attribute> atts = entity.getAttributes();
		entityFigure.removeAllRelationship();
		entityFigure.removeAllAttributes();

		entityFigure.setEntityName(entity.getName());
		// entityFigure.setEntityType(entity.getEntityType().toString());
		// figure.setIdentifier(entity.getIdentifier().getName());
		IdentifierRef original = entity.getOriginalReusedIdentifier().getIdentifires().get(0);
		entityFigure.setIdentifier(original.getName());
		for (Map.Entry<AbstractEntityModel, ReusedIdentifier> rk : entity
				.getReusedIdentifieres().entrySet()) {
			System.out.println(rk.getKey().getName());

			for (IdentifierRef i : rk.getValue().getIdentifires()) {
				if (i.isSame(original)) {
					// nothing
				} else {
					entityFigure.addRelationship(i.getName());
				}
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
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
				new AbstractEntityGraphicalNodeEditPolicy());
	}

}
