/*
 * Copyright 2009-2011 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

import jp.sourceforge.tmdmaker.editpolicy.RelationshipEditPolicy;
import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.RelatedRelationship;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ManhattanConnectionRouter;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;

/**
 * リレーションシップを表すコネクションとのリレーションシップのコントローラ
 * 
 * @author nakaG
 * 
 */
public class RelatedRelationshipEditPart extends AbstractRelationshipEditPart {

	/**
	 * コンストラクタ
	 */
	public RelatedRelationshipEditPart(RelatedRelationship relationship)
	{
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
		PolylineConnection connection = new PolylineConnection();
		ManhattanConnectionRouter router = new ManhattanConnectionRouter();
		connection.setConnectionRouter(router);
		return connection;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE,
				new ConnectionEndpointEditPolicy());
		installEditPolicy(EditPolicy.CONNECTION_ROLE,
				new RelationshipEditPolicy());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractRelationshipEditPart#refreshVisuals()
	 */
	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		calculateAnchorLocation();
		updateFigure(getFigure());
	}

	/**
	 * 
	 * @param figure
	 */
	private void updateFigure(IFigure figure) {
		// TODO Auto-generated method stub

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(
				AbstractConnectionModel.PROPERTY_CONNECTION)) {
			refreshVisuals();
		} else {
			super.propertyChange(evt);
		}
	}

}
