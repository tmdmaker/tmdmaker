/*
 * Copyright 2009-2014 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.sourceforge.tmdmaker.editpart;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import jp.sourceforge.tmdmaker.TMDEditor;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.ModelElement;
import jp.sourceforge.tmdmaker.property.DiagramPropertySource;
import jp.sourceforge.tmdmaker.property.IPropertyAvailable;
import jp.sourceforge.tmdmaker.ui.command.ModelAddCommand;
import jp.sourceforge.tmdmaker.ui.command.ModelConstraintChangeCommand;
import jp.sourceforge.tmdmaker.util.ConstraintConverter;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.CompoundSnapToHelper;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.SnapToGeometry;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ResizableEditPolicy;
import org.eclipse.gef.editpolicies.SnapFeedbackPolicy;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.rulers.RulerProvider;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * Diagramのコントローラ
 * 
 * @author nakaG
 * 
 */
public class DiagramEditPart extends AbstractTMDEditPart<Diagram> implements IPropertyAvailable {
	
	/**
	 * コンストラクタ
	 */
	public DiagramEditPart(Diagram diagram)
	{
		super();
		setModel(diagram);
	}

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
		// installEditPolicy(EditPolicy.LAYOUT_ROLE, new
		// DiagramEditPolicy((XYLayout) getContentPane().getLayoutManager()));
		installEditPolicy("Snap Feedback", new SnapFeedbackPolicy());
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
		return getModel().getChildren();
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

		// /**
		// *
		// */
		// public DiagramEditPolicy(XYLayout layout) {
		// super();
		// setXyLayout(layout);
		// }

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#createChildEditPolicy(org.eclipse.gef.EditPart)
		 */
		@Override
		protected EditPolicy createChildEditPolicy(EditPart child) {
			logger.debug(getClass() + "#createChildEditPolicy()");
			// return new NonResizableEditPolicy();
			return new ResizableEditPolicy();
		}

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#createChangeConstraintCommand(org.eclipse.gef.EditPart,
		 *      java.lang.Object)
		 */
		@Override
		protected Command createChangeConstraintCommand(EditPart child, Object constraint) {
			logger.debug(getClass() + "#createChangeConstraintCommand()");
			ModelConstraintChangeCommand command = new ModelConstraintChangeCommand(
					(ModelElement) child.getModel(),
					ConstraintConverter.toConstraint((Rectangle) constraint));
			return command;
		}

		@Override
		protected Command createChangeConstraintCommand(ChangeBoundsRequest request,
				EditPart child, Object constraint) {
			logger.debug("resizedirection:" + request.getResizeDirection());
			logger.debug("NORTH_SOUTH/EAST_WEST:" + PositionConstants.NORTH_SOUTH + "/"
					+ PositionConstants.EAST_WEST);
			return super.createChangeConstraintCommand(request, child, constraint);
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
			Entity entity = (Entity) request.getNewObject();
			entity.setConstraint(ConstraintConverter.toConstraint(constraint));
			return new ModelAddCommand(getModel(), constraint.x, constraint.y);
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#getAdapter(java.lang.Class)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class key) {
		if (key == SnapToHelper.class) {
			List<SnapToHelper> snapStrategies = new ArrayList<SnapToHelper>();
			Boolean val = (Boolean) getViewer().getProperty(SnapToGeometry.PROPERTY_SNAP_ENABLED);
			if (val != null && val.booleanValue())
				snapStrategies.add(new SnapToGeometry(this));
			val = (Boolean) getViewer().getProperty(SnapToGrid.PROPERTY_GRID_ENABLED);
			if (val != null && val.booleanValue())
				snapStrategies.add(new SnapToGrid(this));

			if (snapStrategies.size() == 0)
				return null;
			if (snapStrategies.size() == 1)
				return snapStrategies.get(0);

			SnapToHelper ss[] = new SnapToHelper[snapStrategies.size()];
			ss = snapStrategies.toArray(ss);
			return new CompoundSnapToHelper(ss);
		}
		return super.getAdapter(key);
	}

	@Override
	public IPropertySource getPropertySource(TMDEditor editor) {
		return new DiagramPropertySource(editor, this.getModel());
	}
}
