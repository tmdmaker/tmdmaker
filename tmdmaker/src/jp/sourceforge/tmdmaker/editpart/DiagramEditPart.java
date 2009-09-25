package jp.sourceforge.tmdmaker.editpart;

import java.beans.PropertyChangeEvent;
import java.util.List;

import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.ModelElement;
import jp.sourceforge.tmdmaker.model.command.EntityCreateCommand;
import jp.sourceforge.tmdmaker.model.command.ModelConstraintChangeCommand;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

/**
 * Diagramのコントローラ
 * 
 * @author nakaG
 * 
 */
public class DiagramEditPart extends AbstractTMDEditPart {
	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		Figure figure = new FreeformLayer();
		figure.setLayoutManager(new FreeformLayout());
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
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new DiagramEditPolicy());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(Diagram.PROPERTY_CHILDREN)) {
			refreshChildren();
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	@Override
	protected List<ModelElement> getModelChildren() {
		return ((Diagram) getModel()).getChildren();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractTMDEditPart#onDoubleClicked()
	 */
	@Override
	protected void onDoubleClicked() {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 * @author nakaG
	 * 
	 */
	private class DiagramEditPolicy extends XYLayoutEditPolicy {

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#createChildEditPolicy(org.eclipse.gef.EditPart)
		 */
		@Override
		protected EditPolicy createChildEditPolicy(EditPart child) {
			logger.debug(getClass() + "#createChildEditPolicy()");
			return new NonResizableEditPolicy();
		}

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#createChangeConstraintCommand(org.eclipse.gef.EditPart,
		 *      java.lang.Object)
		 */
		@Override
		protected Command createChangeConstraintCommand(EditPart child,
				Object constraint) {
			logger.debug(getClass() + "#createChangeConstraintCommand()");
			ModelConstraintChangeCommand command = new ModelConstraintChangeCommand(
					(ModelElement) child.getModel(), (Rectangle) constraint);
			return command;
		}

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.editpolicies.LayoutEditPolicy#getCreateCommand(org.eclipse.gef.requests.CreateRequest)
		 */
		@Override
		protected Command getCreateCommand(CreateRequest request) {
			logger.debug(getClass() + "#getCreateCommand()");
			Rectangle constraint = (Rectangle) getConstraintFor(request);
			constraint.width = -1;
			constraint.height = -1;
			Entity entity = (Entity) request
					.getNewObject();
			entity.setConstraint(constraint);
			return new EntityCreateCommand((Diagram) getModel(), entity);
		}
	}
}
