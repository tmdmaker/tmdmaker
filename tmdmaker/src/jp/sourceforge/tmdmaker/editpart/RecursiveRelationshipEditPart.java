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

import jp.sourceforge.tmdmaker.figure.RelationshipFigure;

import org.eclipse.draw2d.ConnectionEndpointLocator;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * 再帰を表すコネクションのコントローラ
 * 
 * @author nakaG
 * 
 */
public class RecursiveRelationshipEditPart extends RelationshipEditPart {
	// 未使用
	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.RelationshipEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		RelationshipFigure connection = new RelationshipFigure();

		RectangleFigure rf = new RectangleFigure();
		rf.setSize(20, 20);
		rf.setOpaque(false);
		rf.setFill(false);

		Ellipse ef = new Ellipse();
		ef.setOpaque(false);
		ef.setFill(false);
		ef.setSize(15, 15);
		Figure l = new Figure();
		l.setOpaque(false);
		l.setBorder(new LineBorder());
		Rectangle rect = new Rectangle(-1, -1, 20, 20);
		l.setBounds(rect);

		ConnectionEndpointLocator locator = new ConnectionEndpointLocator(
				connection, false);
		locator.setUDistance(-1);
		locator.setVDistance(1);
		connection.add(rf, locator);

		ConnectionEndpointLocator locator2 = new ConnectionEndpointLocator(
				connection, false);
		locator2.setUDistance(11);
		locator2.setVDistance(0);
		connection.add(ef, locator2);

		return connection;
	}
}
