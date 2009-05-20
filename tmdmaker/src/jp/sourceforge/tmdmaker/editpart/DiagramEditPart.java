package jp.sourceforge.tmdmaker.editpart;

import java.beans.PropertyChangeEvent;
import java.util.List;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Diagram;
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
 * 
 * @author nakaG
 *
 */
public class DiagramEditPart extends AbstractTMDEditPart {

	@Override
	protected IFigure createFigure() {
		Figure figure = new FreeformLayer();
		figure.setLayoutManager(new FreeformLayout());
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new DiagramEditPolicy());
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(Diagram.P_CHILDREN)) {
			refreshChildren();
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	@Override
	protected List<ModelElement> getModelChildren() {
		return ((Diagram) getModel()).getChildren();
	}

	private class DiagramEditPolicy extends XYLayoutEditPolicy {

		/* (non-Javadoc)
		 * @see org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#createChildEditPolicy(org.eclipse.gef.EditPart)
		 */
		@Override
		protected EditPolicy createChildEditPolicy(EditPart child) {
			System.out.println(getClass().toString() + "#createChildEditPolicy()");
			return new NonResizableEditPolicy();
		}

		/* (non-Javadoc)
		 * @see org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#createChangeConstraintCommand(org.eclipse.gef.EditPart, java.lang.Object)
		 */
		@Override
		protected Command createChangeConstraintCommand(EditPart child,
				Object constraint) {
			System.out.println(getClass().toString() + "#createChangeConstraintCommand()");
			ModelConstraintChangeCommand command = new ModelConstraintChangeCommand();
			command.setModel((ModelElement)child.getModel());
			command.setConstraint((Rectangle) constraint);
			return command;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.gef.editpolicies.LayoutEditPolicy#getCreateCommand(org.eclipse.gef.requests.CreateRequest)
		 */
		@Override
		protected Command getCreateCommand(CreateRequest request) {
			System.out.println(getClass().toString() + "#getCreateCommand()");
			EntityCreateCommand command = new EntityCreateCommand();
			Rectangle constraint = (Rectangle) getConstraintFor(request);
			constraint.width = -1;
			constraint.height = -1;
			AbstractEntityModel entity = (AbstractEntityModel)request.getNewObject();
			entity.setConstraint(constraint);
			command.setDiagram((Diagram) getModel());
			command.setModel(entity);
			return command;
		}
	}
}
