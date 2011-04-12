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
import java.beans.PropertyChangeListener;
import java.util.List;

import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.AbstractRelationship;
import jp.sourceforge.tmdmaker.model.ConnectableElement;
import jp.sourceforge.tmdmaker.model.ModelElement;

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * リレーションシップのコントローラの基底クラス
 * 
 * @author nakaG
 * 
 */
public abstract class AbstractRelationshipEditPart extends
		AbstractConnectionEditPart implements NodeEditPart,
		PropertyChangeListener {
	/** logging */
	protected static Logger logger = LoggerFactory
			.getLogger(AbstractEntityEditPart.class);
	/** リレーションシップのアンカー */
	private ConnectionAnchor anchor;

	/**
	 * コンストラクタ
	 */
	public AbstractRelationshipEditPart() {
		super();
	}

	/**
	 * コネクションアンカーを取得する
	 * 
	 * @return anchor
	 */
	private ConnectionAnchor getConnectionAnchor() {
		if (anchor == null) {

			// anchor = new ChopboxAnchor(getFigure());
			anchor = new PolylineConnectionAnchor(
					(PolylineConnection) getFigure());
		}
		return anchor;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.NodeEditPart#getSourceConnectionAnchor(org.eclipse.gef.ConnectionEditPart)
	 */
	@Override
	public ConnectionAnchor getSourceConnectionAnchor(
			ConnectionEditPart connection) {
		return getConnectionAnchor();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.NodeEditPart#getSourceConnectionAnchor(org.eclipse.gef.Request)
	 */
	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		return getConnectionAnchor();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.NodeEditPart#getTargetConnectionAnchor(org.eclipse.gef.ConnectionEditPart)
	 */
	@Override
	public ConnectionAnchor getTargetConnectionAnchor(
			ConnectionEditPart connection) {
		return getConnectionAnchor();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.NodeEditPart#getTargetConnectionAnchor(org.eclipse.gef.Request)
	 */
	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		return getConnectionAnchor();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(
				AbstractEntityModel.PROPERTY_CONSTRAINT)) {
			logger.debug("Connection AbstractEntityModel.P_CONSTRAINT");
			refreshVisuals();
		} else if (evt.getPropertyName().equals(
				ConnectableElement.P_SOURCE_CONNECTION)) {
			logger.debug("Connection AbstractEntityModel.P_SOURCE_CONNECTION");
			refreshSourceConnections();
		} else if (evt.getPropertyName().equals(
				ConnectableElement.P_TARGET_CONNECTION)) {
			logger.debug("Connection AbstractEntityModel.P_TARGET_CONNECTION");
			refreshTargetConnections();
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#activate()
	 */
	@Override
	public void activate() {
		super.activate();
		((ModelElement) getModel()).addPropertyChangeListener(this);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#deactivate()
	 */
	@Override
	public void deactivate() {
		super.deactivate();
		((ModelElement) getModel()).removePropertyChangeListener(this);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#getModelSourceConnections()
	 */
	@Override
	protected List<AbstractConnectionModel> getModelSourceConnections() {
		return ((ConnectableElement) getModel()).getModelSourceConnections();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#getModelTargetConnections()
	 */
	@Override
	protected List<AbstractConnectionModel> getModelTargetConnections() {
		return ((ConnectableElement) getModel()).getModelTargetConnections();
	}

	protected void calculateAnchorLocation() {
		AbstractRelationship relationship = (AbstractRelationship) getModel();

		ConnectionAnchor sourceAnchor = this.getConnectionFigure()
				.getSourceAnchor();

		if (sourceAnchor instanceof XYChopboxAnchor) {
			((XYChopboxAnchor) sourceAnchor).setLocation(
					relationship.getSourceXp(), relationship.getSourceYp());
		}

		ConnectionAnchor targetAnchor = this.getConnectionFigure()
				.getTargetAnchor();

		if (targetAnchor instanceof XYChopboxAnchor) {
			((XYChopboxAnchor) targetAnchor).setLocation(
					relationship.getTargetXp(), relationship.getTargetYp());
		}

	}

	/**
	 * 
	 * @author nakaG
	 * 
	 */
	protected static class PolylineConnectionAnchor extends
			AbstractConnectionAnchor {
		/** コネクション */
		private PolylineConnection owner;

		/**
		 * コンストラクタ
		 * 
		 * @param owner
		 *            コネクション
		 */
		public PolylineConnectionAnchor(PolylineConnection owner) {
			this.owner = owner;
		}

		/**
		 * Returns the midpoint of the owner connection.
		 */
		public Point getLocation(Point reference) {
			Point midpoint = owner.getPoints().getMidpoint();
			owner.translateToAbsolute(midpoint);
			return midpoint;
		}

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.draw2d.AbstractConnectionAnchor#getOwner()
		 */
		@Override
		public IFigure getOwner() {
			return owner;
		}

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.draw2d.AbstractConnectionAnchor#getReferencePoint()
		 */
		@Override
		public Point getReferencePoint() {
			if (owner == null) {
				return null;
			} else {
				return getLocation(null);
			}
		}

	}

}