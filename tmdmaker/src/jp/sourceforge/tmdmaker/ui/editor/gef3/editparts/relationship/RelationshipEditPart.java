/*
 * Copyright 2009-2017 TMD-Maker Project <http://tmdmaker.osdn.jp/>
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
package jp.sourceforge.tmdmaker.ui.editor.gef3.editparts.relationship;

import java.beans.PropertyChangeEvent;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.jface.dialogs.Dialog;

import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.AbstractRelationship;
import jp.sourceforge.tmdmaker.ui.dialogs.RelationshipEditDialog;
import jp.sourceforge.tmdmaker.ui.editor.draw2d.figure.relationship.RelationshipFigure;
import jp.sourceforge.tmdmaker.ui.editor.gef3.commands.ConnectionDeleteCommand;
import jp.sourceforge.tmdmaker.ui.editor.gef3.commands.RelationshipEditCommand;
import jp.sourceforge.tmdmaker.ui.editor.gef3.editpolicies.RelationshipEditPolicy;

/**
 * リレーションシップのコントローラ
 * 
 * @author nakaG
 * 
 */
public class RelationshipEditPart extends AbstractRelationshipEditPart {

	/**
	 * コンストラクタ
	 */
	public RelationshipEditPart(AbstractRelationship relationship) {
		super();
		setModel(relationship);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractConnectionEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		RelationshipFigure connection = new RelationshipFigure();
		updateFigure(connection);

		return connection;
	}

	/**
	 * 
	 * @param connection
	 *            figure
	 */
	protected void updateFigure(RelationshipFigure connection) {
		AbstractRelationship model = (AbstractRelationship) getModel();
		connection.update(model);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.ui.editor.gef3.editparts.relationship.AbstractRelationshipEditPart#refreshVisuals()
	 */
	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		calculateAnchorLocation();
		updateFigure((RelationshipFigure) getFigure());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new ConnectionEndpointEditPolicy());
		installEditPolicy(EditPolicy.CONNECTION_ROLE, new RelationshipEditPolicy() {
			protected Command getDeleteCommand(GroupRequest request) {
				ConnectionDeleteCommand cmd = new ConnectionDeleteCommand(
						(AbstractConnectionModel) getModel());
				return cmd;
			}
		});
	}

	/**
	 * ダブルクリック時の処理（リレーションシップ編集)
	 */
	protected void onDoubleClicked() {
		AbstractRelationship model = (AbstractRelationship) getModel();
		AbstractEntityModel source = model.getSource();
		AbstractEntityModel target = model.getTarget();
		RelationshipEditDialog dialog = new RelationshipEditDialog(
				getViewer().getControl().getShell(), source.getName(), target.getName(),
				model.getSourceCardinality(), model.getTargetCardinality(),
				model.isSourceNoInstance(), model.isTargetNoInstance());
		if (dialog.open() == Dialog.OK) {
			RelationshipEditCommand command = new RelationshipEditCommand();
			command.setModel(model);
			command.setSourceCardinality(dialog.getSourceCardinality());
			command.setSourceNoInstance(dialog.isSourceNoInstance());
			command.setTargetCardinality(dialog.getTargetCardinality());
			command.setTargetNoInstance(dialog.isTargetNoInstance());
			GraphicalViewer viewer = (GraphicalViewer) getViewer();
			viewer.getEditDomain().getCommandStack().execute(command);
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.ui.editor.gef3.editparts.relationship.AbstractRelationshipEditPart#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(AbstractConnectionModel.PROPERTY_SOURCE_CARDINALITY)) {
			refreshVisuals();
		} else if (evt.getPropertyName()
				.equals(AbstractConnectionModel.PROPERTY_TARGET_CARDINALITY)) {
			refreshVisuals();
		} else if (evt.getPropertyName().equals(AbstractConnectionModel.PROPERTY_CONNECTION)) {
			refreshVisuals();
		} else {
			super.propertyChange(evt);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#performRequest(org.eclipse.gef.Request)
	 */
	@Override
	public void performRequest(Request req) {
		logger.debug(getClass().toString() + req.getType());
		if (req.getType().equals(RequestConstants.REQ_OPEN)) {
			onDoubleClicked();
		} else {
			super.performRequest(req);
		}
	}
}
