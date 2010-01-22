/*
 * Copyright 2009-2010 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package jp.sourceforge.tmdmaker.figure;

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

	/**
	 * コンストラクタ
	 */
	public RelationshipFigure() {
		setConnectionRouter(new ManhattanConnectionRouter());
	}

	/**
	 * リレーションシップの中心のDecorationを作成する
	 */
	public void createCenterDecoration() {
		removeCenterDecoration();
		centerDecoration = new Ellipse();
		centerDecoration.setFill(false);
		centerDecoration.setBounds(new Rectangle(-1, -1, 15, 15));

		MidpointLocator locator = new MidpointLocator(this, 1);
		add(centerDecoration, locator);
	}

	/**
	 * リレーションシップの中心のDecorationを削除する
	 */
	public void removeCenterDecoration() {
		if (centerDecoration != null) {
			remove(centerDecoration);
			centerDecoration = null;
		}
	}

	public void createSourceDecoration(boolean souceCardinarityMany) {
		RotatableDecoration decoration = null;
		if (souceCardinarityMany) {
			decoration = createCardinarityManyDecoration();
		} else {
			decoration = createCardinalityOneDecoration();
		}
		setSourceDecoration(decoration);
	}

	public void createTargetDecoration(boolean targetCardinarityMany) {
		RotatableDecoration decoration = null;
		if (targetCardinarityMany) {
			decoration = createCardinarityManyDecoration();
		} else {
			decoration = createCardinalityOneDecoration();
		}
		setTargetDecoration(decoration);
	}

	public void createSourceZeroCardinalityDecoration() {
		removeSourceZeroCardinalityDecoration();
		this.sourceZeroCardinaliryFigure = createZeroCardinalityFigure();
		createZeroCardinaliryDecoration(this.sourceZeroCardinaliryFigure, false);
	}

	public void removeSourceZeroCardinalityDecoration() {
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
		ConnectionEndpointLocator locator = new ConnectionEndpointLocator(this,
				isEnd);
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

	public void createRecursiveDecoration() {
		Ellipse figure = new Ellipse();
		figure.setFill(false);
		figure.setBounds(new Rectangle(-1, -1, 8, 8));
		ConnectionEndpointLocator locator = new ConnectionEndpointLocator(this,
				false);
		locator.setUDistance(20);
		locator.setVDistance(0);
		add(figure, locator);
		PointList manyPointList = new PointList();
		manyPointList.addPoint(0, 2);
		manyPointList.addPoint(-24, 2);
		manyPointList.addPoint(-24, 0);
		PolylineDecoration decoration = new PolylineDecoration();
		decoration.setTemplate(manyPointList);
		setSourceDecoration(decoration);
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
