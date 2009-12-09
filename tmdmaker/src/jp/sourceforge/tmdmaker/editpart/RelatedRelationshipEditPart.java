/*
 * Copyright 2009 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ManhattanConnectionRouter;
import org.eclipse.draw2d.PolylineConnection;


/**
 * リレーションシップを表すコネクションとのリレーションシップのコントローラ
 * 
 * @author nakaG
 * 
 */
public class RelatedRelationshipEditPart extends AbstractRelationshipEditPart {

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

		// Ellipse figure = new Ellipse();
		// figure.setFill(false);
		// figure.setBounds(new Rectangle(-1, -1, 16, 16));
		// ConnectionEndpointLocator locator = new
		// ConnectionEndpointLocator(connection,false);
		// locator.setUDistance(-8);
		// locator.setVDistance(0);
		// connection.add(figure, locator);
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
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractRelationshipEditPart#refreshVisuals()
	 */
	@Override
	protected void refreshVisuals() {
		IFigure figure = getFigure();
		updateFigure(figure);
		super.refreshVisuals();
	}

	/**
	 * 
	 * @param figure
	 */
	private void updateFigure(IFigure figure) {
		// TODO Auto-generated method stub

	}

}
