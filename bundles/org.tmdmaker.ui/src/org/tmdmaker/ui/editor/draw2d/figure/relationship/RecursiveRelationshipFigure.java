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
package org.tmdmaker.ui.editor.draw2d.figure.relationship;

import org.eclipse.draw2d.ConnectionEndpointLocator;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.RectangleFigure;

/**
 * 再帰表のリレーションシップのfigure.
 * 
 * @author nakag
 *
 */
public class RecursiveRelationshipFigure extends RelationshipFigure {

	public RecursiveRelationshipFigure() {
		createIntersectionCircle();
		createRecursiveDecoration();
	}

	private void createIntersectionCircle() {
		Ellipse ef = new Ellipse();
		ef.setOpaque(false);
		ef.setFill(false);
		ef.setSize(15, 15);

		ConnectionEndpointLocator locator2 = new ConnectionEndpointLocator(this, false);
		locator2.setUDistance(11);
		locator2.setVDistance(0);
		this.add(ef, locator2);
	}

	private void createRecursiveDecoration() {
		RectangleFigure rf = new RectangleFigure();
		rf.setSize(20, 20);
		rf.setOpaque(false);
		rf.setFill(false);

		ConnectionEndpointLocator locator = new ConnectionEndpointLocator(this, false);
		locator.setUDistance(-1);
		locator.setVDistance(1);
		this.add(rf, locator);
	}
}
