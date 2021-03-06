/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org>
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
package org.tmdmaker.ui.editor.gef3.editparts;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

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
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.ResizableEditPolicy;
import org.eclipse.gef.editpolicies.SnapFeedbackPolicy;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.ui.views.properties.IPropertySource;
import org.tmdmaker.core.model.ConnectableElement;
import org.tmdmaker.core.model.Diagram;
import org.tmdmaker.core.model.Entity;
import org.tmdmaker.core.model.Laputa;
import org.tmdmaker.core.model.ModelElement;
import org.tmdmaker.core.model.other.Memo;
import org.tmdmaker.core.model.other.TurboFile;
import org.tmdmaker.ui.Messages;
import org.tmdmaker.ui.editor.draw2d.ConstraintConverter;
import org.tmdmaker.ui.editor.gef3.commands.ConstraintChangeCommand;
import org.tmdmaker.ui.editor.gef3.commands.EntityModelAddCommand;
import org.tmdmaker.ui.editor.gef3.commands.MemoAddCommand;
import org.tmdmaker.ui.editor.gef3.commands.MemoChangeCommand;
import org.tmdmaker.ui.editor.gef3.editparts.node.AbstractTMDEditPart;
import org.tmdmaker.ui.views.properties.IPropertyAvailable;
import org.tmdmaker.ui.views.properties.gef3.DiagramPropertySource;

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
	public DiagramEditPart(Diagram diagram) {
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
		installEditPolicy("Snap Feedback", new SnapFeedbackPolicy()); //$NON-NLS-1$
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
			logger.debug("{}#createChildEditPolicy()", getClass());
			return new ResizableEditPolicy();
		}

		@Override
		protected Command createChangeConstraintCommand(ChangeBoundsRequest request, EditPart child,
				Object rectangle) {
			logger.debug("resizedirection:{}", request.getResizeDirection());
			logger.debug("NORTH_SOUTH/EAST_WEST:" + PositionConstants.NORTH_SOUTH + "/"
					+ PositionConstants.EAST_WEST);
			return new ConstraintChangeCommand((ModelElement) child.getModel(),
					(Rectangle) rectangle);
		}

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.editpolicies.LayoutEditPolicy#getCreateCommand(org.eclipse.gef.requests.CreateRequest)
		 */
		@Override
		protected Command getCreateCommand(CreateRequest request) {
			Object objectType = request.getNewObjectType();
			logger.debug("{} #getCreateCommand() {}", getClass(), objectType);
			Rectangle rectangle = getTrimmedRectangle(request);
			if (objectType.equals(TurboFile.class)) {
				TurboFile model = (TurboFile) request.getNewObject();
				ConstraintConverter.setConstraint(model, rectangle);
				return createTurboFileCommand(rectangle, model);
			}
			// Memo
			if (objectType.equals(Memo.class)) {
				ConnectableElement model = (ConnectableElement) request.getNewObject();
				ConstraintConverter.setConstraint(model, rectangle);
				return createMemoCommand(rectangle, model);
			}

			// EntityまたはLaputa
			if (objectType.equals(Entity.class) || objectType.equals(Laputa.class)) {
				return new EntityModelAddCommand(getModel(), rectangle.x, rectangle.y);
			}
			return null;
		}

		private Rectangle getTrimmedRectangle(CreateRequest request) {
			Rectangle rectangle = (Rectangle) getConstraintFor(request);
			rectangle.height = -1;
			rectangle.width = -1;
			return rectangle;
		}

		private EntityModelAddCommand createTurboFileCommand(Rectangle rectangle, TurboFile turbo) {
			turbo.setName(Messages.TurboFile);
			return new EntityModelAddCommand(getModel(), turbo, rectangle.x, rectangle.y);
		}

		private Command createMemoCommand(Rectangle rectangle, ConnectableElement model) {
			Memo memo = (Memo) model;
			CompoundCommand command = new CompoundCommand();
			command.add(new MemoAddCommand(getModel(), memo, rectangle.x, rectangle.y));
			command.add(new MemoChangeCommand(memo, Messages.Memo));
			return command;
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

			if (snapStrategies.isEmpty())
				return null;
			if (snapStrategies.size() == 1)
				return snapStrategies.get(0);

			SnapToHelper[] ss = new SnapToHelper[snapStrategies.size()];
			ss = snapStrategies.toArray(ss);
			return new CompoundSnapToHelper(ss);
		}
		return super.getAdapter(key);
	}

	@Override
	public IPropertySource getPropertySource(CommandStack commandStack) {
		return new DiagramPropertySource(commandStack, this.getModel());
	}
}
