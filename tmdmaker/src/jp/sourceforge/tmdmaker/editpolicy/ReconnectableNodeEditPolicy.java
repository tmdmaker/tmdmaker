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
package jp.sourceforge.tmdmaker.editpolicy;

import jp.sourceforge.tmdmaker.editpart.AbstractModelEditPart;
import jp.sourceforge.tmdmaker.editpart.XYChopboxAnchorHelper;
import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.ui.command.SourceConnectionReconnectCommand;
import jp.sourceforge.tmdmaker.ui.command.TargetConnectionReconnectCommand;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * リレーションの接続位置を変更可能なノード（エンティティ系モデル）用のEditPolicy
 * 
 * @author nakaG
 * 
 */
public class ReconnectableNodeEditPolicy extends GraphicalNodeEditPolicy {

	/** logging */
	protected static Logger logger = LoggerFactory
			.getLogger(TMDModelGraphicalNodeEditPolicy.class);

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getReconnectSourceCommand(org.eclipse.gef.requests.ReconnectRequest)
	 */
	@Override
	protected Command getReconnectSourceCommand(ReconnectRequest request) {
		AbstractConnectionModel connection = (AbstractConnectionModel) request
				.getConnectionEditPart().getModel();
		if (connection.getSource() == connection.getTarget()) {
			System.out.println("source == target");
			return null;
		}
		AbstractEntityModel newSource = (AbstractEntityModel) request
				.getTarget().getModel();
		if (!connection.getSource().equals(newSource)) {
			System.out.println("source not equals newSource");
			return null;
		}
		Point location = new Point(request.getLocation());
		AbstractModelEditPart<?> sourceEditPart = (AbstractModelEditPart<?>) request
				.getConnectionEditPart().getSource();
		IFigure sourceFigure = sourceEditPart.getFigure();
		sourceFigure.translateToRelative(location);

		int xp = -1;
		int yp = -1;

		Rectangle bounds = sourceFigure.getBounds();
		Rectangle centerRectangle = new Rectangle(
				bounds.x + (bounds.width / 4), bounds.y + (bounds.height / 4),
				bounds.width / 2, bounds.height / 2);
		if (!centerRectangle.contains(location)) {
			System.out.println("not center");
			Point point = new XYChopboxAnchorHelper(bounds)
					.getIntersectionPoint(location);
			xp = 100 * (point.x - bounds.x) / bounds.width;
			yp = 100 * (point.y - bounds.y) / bounds.height;
		}
		return new SourceConnectionReconnectCommand(connection, xp, yp);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getReconnectTargetCommand(org.eclipse.gef.requests.ReconnectRequest)
	 */
	@Override
	protected Command getReconnectTargetCommand(ReconnectRequest request) {
		AbstractConnectionModel connection = (AbstractConnectionModel) request
				.getConnectionEditPart().getModel();
		if (connection.getSource() == connection.getTarget()) {
			System.out.println("source == target");
			return null;
		}
		AbstractEntityModel newTarget = (AbstractEntityModel) request
				.getTarget().getModel();
		if (!connection.getTarget().equals(newTarget)) {
			System.out.println("target not equals newTarget");
			return null;
		}
		Point location = new Point(request.getLocation());
		AbstractModelEditPart<?> targetEditPart = (AbstractModelEditPart<?>) request
				.getConnectionEditPart().getTarget();
		IFigure targetFigure = targetEditPart.getFigure();
		targetFigure.translateToRelative(location);

		int xp = -1;
		int yp = -1;

		Rectangle bounds = targetFigure.getBounds();
		Rectangle centerRectangle = new Rectangle(
				bounds.x + (bounds.width / 4), bounds.y + (bounds.height / 4),
				bounds.width / 2, bounds.height / 2);

		if (!centerRectangle.contains(location)) {
			System.out.println("not center");
			Point point = new XYChopboxAnchorHelper(bounds)
					.getIntersectionPoint(location);
			xp = 100 * (point.x - bounds.x) / bounds.width;
			yp = 100 * (point.y - bounds.y) / bounds.height;
		}
		return new TargetConnectionReconnectCommand(connection, xp, yp);
	}

	/**
	 * エンティティモデルを取得する。
	 * 
	 * @return モデル
	 */
	protected AbstractEntityModel getAbstractEntityModel() {
		return (AbstractEntityModel) getHost().getModel();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getConnectionCompleteCommand(org.eclipse.gef.requests.CreateConnectionRequest)
	 */
	@Override
	protected Command getConnectionCompleteCommand(
			CreateConnectionRequest request) {
		return null;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getConnectionCreateCommand(org.eclipse.gef.requests.CreateConnectionRequest)
	 */
	@Override
	protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
		return null;
	}
}