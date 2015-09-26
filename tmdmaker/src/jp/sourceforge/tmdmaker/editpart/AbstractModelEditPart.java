/*
 * Copyright 2009-2015 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
import java.util.List;

import jp.sourceforge.tmdmaker.dialog.model.EditAttribute;
import jp.sourceforge.tmdmaker.figure.EntityFigure;
import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.ConnectableElement;
import jp.sourceforge.tmdmaker.model.Constraint;
import jp.sourceforge.tmdmaker.model.IAttribute;
import jp.sourceforge.tmdmaker.model.ModelElement;
import jp.sourceforge.tmdmaker.ui.command.AttributeEditCommand;
import jp.sourceforge.tmdmaker.ui.preferences.appearance.AppearanceSetting;
import jp.sourceforge.tmdmaker.ui.preferences.appearance.ModelAppearance;
import jp.sourceforge.tmdmaker.util.ConstraintConverter;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.swt.graphics.Color;

/**
 * エンティティ系モデルのコントローラの基底クラス
 * 
 * @author nakaG
 * 
 */
public abstract class AbstractModelEditPart<T extends ConnectableElement>
		extends AbstractTMDEditPart<T>implements NodeEditPart {

	/** このコントローラで利用するアンカー */
	private ConnectionAnchor anchor;

	/**
	 * コンストラクタ
	 */
	public AbstractModelEditPart() {
		super();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractTMDEditPart#activate()
	 */
	@Override
	public void activate() {
		logger.debug(getClass() + "#activate()");
		super.activate();
		getModel().addPropertyChangeListener(this);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractTMDEditPart#deactivate()
	 */
	@Override
	public void deactivate() {
		logger.debug(getClass() + "#deactivate()");
		super.deactivate();
		getModel().removePropertyChangeListener(this);
	}

	/**
	 * ConnectionAnchor を取得する。
	 * 
	 * @return ConnectionAnchor
	 */
	protected ConnectionAnchor getConnectionAnchor() {
		if (anchor == null) {
			anchor = new XYChopboxAnchor(getFigure());
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
	public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection) {
		// if (!(connection instanceof RelationshipEditPart)) {
		// return new ChopboxAnchor(this.getFigure());
		// }
		AbstractConnectionModel relationship = (AbstractConnectionModel) connection.getModel();

		XYChopboxAnchor anchor = new XYChopboxAnchor(this.getFigure(), relationship.getSourceXp(),
				relationship.getSourceYp());
		return anchor;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.NodeEditPart#getSourceConnectionAnchor(org.eclipse.gef.Request)
	 */
	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		if (request instanceof ReconnectRequest) {
			ReconnectRequest reconnectRequest = (ReconnectRequest) request;

			ConnectionEditPart connectionEditPart = reconnectRequest.getConnectionEditPart();

			// if (!(connectionEditPart instanceof RelationshipEditPart)) {
			// return new ChopboxAnchor(this.getFigure());
			// }
			AbstractConnectionModel relationship = (AbstractConnectionModel) connectionEditPart
					.getModel();
			if (relationship.getSource() == relationship.getTarget()) {
				return new XYChopboxAnchor(this.getFigure());
			}
			EditPart editPart = reconnectRequest.getTarget();
			if (editPart == null || !editPart.getModel().equals(relationship.getSource())) {
				return new XYChopboxAnchor(this.getFigure());
			}

			Point location = new Point(reconnectRequest.getLocation());
			this.getFigure().translateToRelative(location);
			IFigure sourceFigure = ((AbstractModelEditPart<?>) connectionEditPart.getSource())
					.getFigure();
			XYChopboxAnchor anchor = new XYChopboxAnchor(getFigure());

			Rectangle bounds = sourceFigure.getBounds();

			Rectangle centerRectangle = new Rectangle(bounds.x + (bounds.width / 4),
					bounds.y + (bounds.height / 4), bounds.width / 2, bounds.height / 2);

			if (!centerRectangle.contains(location)) {
				Point point = new XYChopboxAnchorHelper(bounds).getIntersectionPoint(location);
				int xp = 100 * (point.x - bounds.x) / bounds.width;
				int yp = 100 * (point.y - bounds.y) / bounds.height;
				anchor.setLocation(xp, yp);
			}
			return anchor;
		}

		return new XYChopboxAnchor(getFigure());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.NodeEditPart#getTargetConnectionAnchor(org.eclipse.gef.ConnectionEditPart)
	 */
	@Override
	public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) {
		// if (!(connection instanceof RelationshipEditPart)) {
		// return new ChopboxAnchor(this.getFigure());
		// }
		AbstractConnectionModel relationship = (AbstractConnectionModel) connection.getModel();
		XYChopboxAnchor anchor = new XYChopboxAnchor(this.getFigure(), relationship.getTargetXp(),
				relationship.getTargetYp());

		if (relationship.getTargetXp() != -1 && relationship.getTargetYp() != -1) {
		}

		return anchor;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.NodeEditPart#getTargetConnectionAnchor(org.eclipse.gef.Request)
	 */
	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		System.out.println("getTargetConnectionAnchor(Request request)");
		if (request instanceof ReconnectRequest) {
			ReconnectRequest reconnectRequest = (ReconnectRequest) request;
			ConnectionEditPart connectionEditPart = reconnectRequest.getConnectionEditPart();

			// if (!(connectionEditPart instanceof RelationshipEditPart)) {
			// return new ChopboxAnchor(this.getFigure());
			// }
			AbstractConnectionModel relationship = (AbstractConnectionModel) connectionEditPart
					.getModel();
			if (relationship.getSource() == relationship.getTarget()) {
				return new XYChopboxAnchor(this.getFigure());
			}
			EditPart editPart = reconnectRequest.getTarget();

			if (editPart == null || !editPart.getModel().equals(relationship.getTarget())) {
				return new XYChopboxAnchor(this.getFigure());
			}
			Point location = new Point(reconnectRequest.getLocation());
			this.getFigure().translateToRelative(location);
			IFigure targetFigure = ((AbstractModelEditPart<?>) connectionEditPart.getTarget())
					.getFigure();

			XYChopboxAnchor anchor = new XYChopboxAnchor(this.getFigure(),
					relationship.getTargetXp(), relationship.getTargetYp());

			Rectangle bounds = targetFigure.getBounds();

			Rectangle centerRectangle = new Rectangle(bounds.x + (bounds.width / 4),
					bounds.y + (bounds.height / 4), bounds.width / 2, bounds.height / 2);

			if (!centerRectangle.contains(location)) {
				Point point = new XYChopboxAnchorHelper(bounds).getIntersectionPoint(location);
				int xp = 100 * (point.x - bounds.x) / bounds.width;
				int yp = 100 * (point.y - bounds.y) / bounds.height;

				anchor.setLocation(xp, yp);
			}

			return anchor;
		}
		return new XYChopboxAnchor(getFigure());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#getModelSourceConnections()
	 */
	@Override
	protected List<AbstractConnectionModel> getModelSourceConnections() {
		return getModel().getModelSourceConnections();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#getModelTargetConnections()
	 */
	@Override
	protected List<AbstractConnectionModel> getModelTargetConnections() {
		return getModel().getModelTargetConnections();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		logger.debug(getClass() + "." + evt.getPropertyName());

		if (evt.getPropertyName().equals(ModelElement.PROPERTY_NAME)) {
			handleNameChange(evt);
		} else if (evt.getPropertyName().equals(ModelElement.PROPERTY_CONSTRAINT)) {
			handleConstraintChange(evt);
		} else if (evt.getPropertyName().equals(AbstractEntityModel.PROPERTY_ATTRIBUTE)) {
			handleAttributeChange(evt);
		} else if (evt.getPropertyName().equals(ConnectableElement.P_SOURCE_CONNECTION)) {
			handleSourceConnectionChange(evt);
		} else if (evt.getPropertyName().equals(ConnectableElement.P_TARGET_CONNECTION)) {
			handleTargetConnectionChange(evt);
		} else if (evt.getPropertyName().equals(AbstractEntityModel.PROPERTY_REUSED)) {
			handleReUseKeyChange(evt);
		} else if (evt.getPropertyName().equals(AbstractEntityModel.PROPERTY_IDENTIFIER)) {
			handleIdentifierChange(evt);
		} else if (evt.getPropertyName().equals(AbstractEntityModel.PROPERTY_ATTRIBUTE_REORDER)) {
			logger.warn("Handle Reorder Occured.");
			refreshChildren();
		} else {
			logger.warn("Not Handle Event Occured.");
		}
	}

	/**
	 * 名称変更イベント処理
	 * 
	 * @param evt
	 *            発生したイベント情報
	 */
	protected void handleNameChange(PropertyChangeEvent evt) {
		refreshVisuals();
	}

	/**
	 * 制約変更イベント処理
	 * 
	 * @param evt
	 *            発生したイベント情報
	 */
	protected void handleConstraintChange(PropertyChangeEvent evt) {
		refreshVisuals();
	}

	/**
	 * 属性変更イベント処理
	 * 
	 * @param evt
	 *            発生したイベント情報
	 */
	protected void handleAttributeChange(PropertyChangeEvent evt) {
		refreshVisuals();
		refreshChildren();
	}

	/**
	 * 個体指定子変更イベント処理
	 * 
	 * @param evt
	 *            発生したイベント情報
	 */
	protected void handleIdentifierChange(PropertyChangeEvent evt) {
		refreshVisuals();
	}

	/**
	 * 接続元コネクション変更イベント処理
	 * 
	 * @param evt
	 *            発生したイベント情報
	 */
	protected void handleSourceConnectionChange(PropertyChangeEvent evt) {
		refreshSourceConnections();
	}

	/**
	 * 接続先コネクション変更イベント処理
	 * 
	 * @param evt
	 *            発生したイベント情報
	 */
	protected void handleTargetConnectionChange(PropertyChangeEvent evt) {
		refreshTargetConnections();
	}

	/**
	 * 属性順序変更イベント処理
	 * 
	 * @param evt
	 *            発生したイベント情報
	 */
	protected void handleAttributeReorder(PropertyChangeEvent evt) {
		refreshVisuals();
	}

	/**
	 * ReUseKey変更イベント処理
	 * 
	 * @param evt
	 *            発生したイベント情報
	 */
	protected void handleReUseKeyChange(PropertyChangeEvent evt) {
		refreshVisuals();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#refresh()
	 */
	@Override
	public void refresh() {
		super.refresh();
		refleshConnections();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#refreshVisuals()
	 */
	@Override
	protected void refreshVisuals() {
		logger.debug(getClass().toString() + "#refreshVisuals()");
		super.refreshVisuals();
		Constraint constraint = getModel().getConstraint();
		Rectangle bounds = ConstraintConverter.toRectangleWithoutHeightWidth(constraint);
		((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), bounds);

		updateFigure(getFigure());
	}

	public void updateAppearance() {
		refreshVisuals();
	}

	protected void refleshConnections() {
		for (AbstractConnectionModel connection : getModelSourceConnections()) {
			connection.fireParentMoved();
		}
		for (AbstractConnectionModel connection : getModelTargetConnections()) {
			connection.fireParentMoved();
		}

	}

	/**
	 * Figureを更新する
	 * 
	 * @param figure
	 *            更新するFigure
	 */
	protected abstract void updateFigure(IFigure figure);

	/**
	 * アトリビュート編集コマンドを作成する
	 * 
	 * @param ccommand
	 *            コマンド
	 * @param entity
	 *            モデル
	 * @param editAttributeList
	 *            編集したアトリビュートリスト
	 */
	protected void addAttributeEditCommands(CompoundCommand ccommand, AbstractEntityModel entity,
			List<EditAttribute> editAttributeList) {
		for (EditAttribute ea : editAttributeList) {
			IAttribute original = ea.getOriginalAttribute();
			if (ea.isEdited() && !ea.isAdded()) {
				Attribute editedValueAttribute = new Attribute();
				ea.copyTo(editedValueAttribute);
				AttributeEditCommand editCommand = new AttributeEditCommand(original,
						editedValueAttribute, entity);
				ccommand.add(editCommand);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#getContentPane()
	 */
	@Override
	public IFigure getContentPane() {
		return ((EntityFigure) getFigure()).getAttributeCompartmentFigure();
	}

	/**
	 * モデルのサイズを自動調整可能か？
	 *
	 * @return 自動調整可能なモデルのコントローラはtrueを返す
	 */
	public abstract boolean canAutoSize();

	protected abstract ModelAppearance getAppearance();

	protected Color getForegroundColor() {
		return createForegroundColor(getAppearance());
	}

	protected Color getBackgroundColor() {
		return createBackgroundColor(getAppearance());
	}

	private Color createBackgroundColor(ModelAppearance appearance) {
		AppearanceSetting config = AppearanceSetting.getInstance();
		if (config.isColorEnabled() && appearance != null) {
			return new Color(null, config.getBackground(appearance));
		} else {
			return ColorConstants.white;
		}
	}

	private Color createForegroundColor(ModelAppearance appearance) {
		AppearanceSetting config = AppearanceSetting.getInstance();
		if (config.isColorEnabled() && appearance != null) {
			return new Color(null, config.getFont(appearance));
		} else {
			return ColorConstants.black;
		}
	}
}
