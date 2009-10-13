package jp.sourceforge.tmdmaker.editpart;

import java.beans.PropertyChangeEvent;
import java.util.List;

import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.ConnectableElement;
import jp.sourceforge.tmdmaker.model.EditAttribute;
import jp.sourceforge.tmdmaker.model.ModelElement;
import jp.sourceforge.tmdmaker.model.command.AttributeEditCommand;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.CompoundCommand;

/**
 * 
 * @author nakaG
 * 
 */
public abstract class AbstractEntityEditPart extends AbstractTMDEditPart
		implements NodeEditPart {

	/** このコントローラで利用するアンカー */
	private ConnectionAnchor anchor;

	/**
	 * コンストラクタ
	 */
	public AbstractEntityEditPart() {
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
		((ModelElement) getModel()).addPropertyChangeListener(this);
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
		((ModelElement) getModel()).removePropertyChangeListener(this);
	}

	/**
	 * ConnectionAnchor を取得する。
	 * 
	 * @return ConnectionAnchor
	 */
	protected ConnectionAnchor getConnectionAnchor() {
		if (anchor == null) {
			anchor = new ChopboxAnchor(getFigure());
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
		} else if (evt.getPropertyName().equals(
				ModelElement.PROPERTY_CONSTRAINT)) {
			handleConstraintChange(evt);
		} else if (evt.getPropertyName().equals(
				AbstractEntityModel.PROPERTY_ATTRIBUTE)) {
			handleAttributeChange(evt);
		} else if (evt.getPropertyName().equals(
				ConnectableElement.P_SOURCE_CONNECTION)) {
			handleSourceConnectionChange(evt);
		} else if (evt.getPropertyName().equals(
				ConnectableElement.P_TARGET_CONNECTION)) {
			handleTargetConnectionChange(evt);
		} else if (evt.getPropertyName().equals(
				AbstractEntityModel.PROPERTY_REUSED)) {
			handleReUseKeyChange(evt);
		} else if (evt.getPropertyName().equals(
				AbstractEntityModel.PROPERTY_IDENTIFIER)) {
			handleIdentifierChange(evt);
		} else if (evt.getPropertyName().equals(
				AbstractEntityModel.PROPERTY_ATTRIBUTE_REORDER)) {
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
	 * 個体指示子変更イベント処理
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
	 * @see org.eclipse.gef.editparts.AbstractEditPart#refreshVisuals()
	 */
	@Override
	protected void refreshVisuals() {
		logger.debug(getClass().toString() + "#refreshVisuals()");
		super.refreshVisuals();
		Object model = getModel();
		Rectangle bounds = new Rectangle(((ModelElement) model).getConstraint());
		((GraphicalEditPart) getParent()).setLayoutConstraint(this,
				getFigure(), bounds);

		updateFigure(getFigure());
		refreshChildren();
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
	protected void addAttributeEditCommands(CompoundCommand ccommand,
			AbstractEntityModel entity, List<EditAttribute> editAttributeList) {
		for (EditAttribute ea : editAttributeList) {
			Attribute original = ea.getOriginalAttribute();
			if (ea.isEdited() && !ea.isAdded()) {
				Attribute editedValueAttribute = new Attribute();
				ea.copyTo(editedValueAttribute);
				AttributeEditCommand editCommand = new AttributeEditCommand(
						original, editedValueAttribute, entity);
				ccommand.add(editCommand);
			}
		}
	}
}
