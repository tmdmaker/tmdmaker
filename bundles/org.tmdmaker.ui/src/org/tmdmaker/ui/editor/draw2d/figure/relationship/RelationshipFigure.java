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
package org.tmdmaker.ui.editor.draw2d.figure.relationship;

import org.eclipse.draw2d.ConnectionEndpointLocator;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.ManhattanConnectionRouter;
import org.eclipse.draw2d.MidpointLocator;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.draw2d.RotatableDecoration;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.tmdmaker.core.model.AbstractRelationship;
import org.tmdmaker.core.model.Cardinality;

/**
 * リレーションシップのFigure
 * 
 * @author nakaG
 * 
 */
public class RelationshipFigure extends PolylineConnection {
	private Figure sourceZeroCardinaliryFigure;
	private Figure targetZeroCardinaliryFigure;
	private Ellipse centerDecoration;
	private static final int LINE_WIDTH = 1;

	/**
	 * コンストラクタ
	 */
	public RelationshipFigure() {
		setConnectionRouter(new ManhattanConnectionRouter());
		setLineWidth(LINE_WIDTH);
	}

	/**
	 * figureを更新する.
	 * 
	 * @param model
	 */
	public void update(AbstractRelationship model) {
		this.createSourceDecoration(model.getSourceCardinality().equals(Cardinality.MANY));
		if (model.isSourceNoInstance()) {
			this.createSourceZeroCardinalityDecoration();
		} else {
			this.removeSourceZeroCardinalityDecoration();
		}
		this.createTargetDecoration(model.getTargetCardinality().equals(Cardinality.MANY));
		if (model.isTargetNoInstance()) {
			this.createTargetZeroCardinalityDecoration();
		} else {
			this.removeTargetZeroCardinalityDecoration();
		}

		if (model.isCenterMark()) {
			this.createCenterDecoration();
		} else {
			this.removeCenterDecoration();
		}

	}

	/**
	 * リレーションシップの中心のDecorationを作成する
	 */
	private void createCenterDecoration() {
		removeCenterDecoration();
		centerDecoration = new Ellipse();
		centerDecoration.setFill(false);
		centerDecoration.setBounds(new Rectangle(-1, -1, 15, 15));
		centerDecoration.setLineWidth(LINE_WIDTH);
		MidpointLocator locator = new MidpointLocator(this, 1);
		add(centerDecoration, locator);
	}

	/**
	 * リレーションシップの中心のDecorationを削除する
	 */
	private void removeCenterDecoration() {
		if (centerDecoration != null) {
			remove(centerDecoration);
			centerDecoration = null;
		}
	}

	private void createSourceDecoration(boolean souceCardinarityMany) {
		RotatableDecoration decoration = null;
		if (souceCardinarityMany) {
			decoration = createCardinarityManyDecoration();
		} else {
			decoration = createCardinalityOneDecoration();
		}
		setSourceDecoration(decoration);
	}

	private void createTargetDecoration(boolean targetCardinarityMany) {
		RotatableDecoration decoration = null;
		if (targetCardinarityMany) {
			decoration = createCardinarityManyDecoration();
		} else {
			decoration = createCardinalityOneDecoration();
		}
		setTargetDecoration(decoration);
	}

	private void createSourceZeroCardinalityDecoration() {
		removeSourceZeroCardinalityDecoration();
		this.sourceZeroCardinaliryFigure = createZeroCardinalityFigure();
		createZeroCardinaliryDecoration(this.sourceZeroCardinaliryFigure, false);
	}

	private void removeSourceZeroCardinalityDecoration() {
		if (this.sourceZeroCardinaliryFigure != null) {
			remove(this.sourceZeroCardinaliryFigure);
			this.sourceZeroCardinaliryFigure = null;
		}
	}

	public void createTargetZeroCardinalityDecoration() {
		removeTargetZeroCardinalityDecoration();
		this.targetZeroCardinaliryFigure = createZeroCardinalityFigure();
		createZeroCardinaliryDecoration(this.targetZeroCardinaliryFigure, true);
	}

	public void removeTargetZeroCardinalityDecoration() {
		if (this.targetZeroCardinaliryFigure != null) {
			remove(this.targetZeroCardinaliryFigure);
			this.targetZeroCardinaliryFigure = null;
		}
	}

	private void createZeroCardinaliryDecoration(Figure figure, boolean isEnd) {
		ConnectionEndpointLocator locator = new ConnectionEndpointLocator(this, isEnd);
		locator.setUDistance(8);
		locator.setVDistance(0);
		add(figure, locator);
	}

	private RotatableDecoration createCardinarityManyDecoration() {
		PointList manyPointList = new PointList();
		manyPointList.addPoint(0, 2);
		manyPointList.addPoint(-1, 0);
		manyPointList.addPoint(0, -2);

		PolylineDecoration decoration = new PolylineDecoration();
		decoration.setTemplate(manyPointList);
		return decoration;
	}

	private RotatableDecoration createCardinalityOneDecoration() {
		PointList onePointList = new PointList();
		onePointList.addPoint(-1, 2);
		onePointList.addPoint(-1, -2);

		PolylineDecoration decoration = new PolylineDecoration();
		decoration.setTemplate(onePointList);
		return decoration;
	}

	private Figure createZeroCardinalityFigure() {
		Ellipse figure = new Ellipse();
		figure.setFill(false);
		figure.setBounds(new Rectangle(-1, -1, 8, 8));
		return figure;
	}

}
