package jp.sourceforge.tmdmaker.model;

import java.util.Map;

/**
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class CombinationTable extends AbstractEntityModel {
	/** 対照表名のサフィックス */
	public static final String COMBINATION_TABLE_SUFFIX = ".対照表";

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#getMyReuseKey()
	 */
	@Override
	public ReUseKey getMyReuseKey() {
		ReUseKey returnValue = new ReUseKey();
		for (Map.Entry<AbstractEntityModel, ReUseKey> rk : this.reuseKey
				.entrySet()) {
			returnValue.addAll(rk.getValue().getIdentifires());
		}
		return returnValue;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#isEntityTypeEditable()
	 */
	@Override
	public boolean isEntityTypeEditable() {
		// return getModelTargetConnections().size() == 1
		// && getModelSourceConnections().size() == 0;
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#isDeletable()
	 */
	@Override
	public boolean isDeletable() {
		return getModelSourceConnections().size() == 0
				&& getModelTargetConnections().size() == 1;
	}
	/**
	 * 対象表作成時のリレーションシップを取得する
	 * @return 対象表作成時のリレーションシップ（リレーションシップへのリレーションシップ）
	 */
	public RelatedRelationship findCreationRelationship() {
		AbstractConnectionModel r = getModelTargetConnections().get(0);
		assert r instanceof RelatedRelationship;
		return (RelatedRelationship) r;
	}

	/**
	 * 
	 */
//	public void notifyReUseKeyChange(AbstractConnectionModel<?> callConnection) {
//		firePropertyChange(AbstractEntityModel.PROPERTY_REUSEKEY, null, null);
//		for (AbstractConnectionModel<?> con : getModelTargetConnections()) {
//
//			if (con instanceof ReUseKeyChangeListener && con != callConnection ) {
//				((ReUseKeyChangeListener) con).awareReUseKeysChanged();
//			}
//		}
//		for (AbstractConnectionModel<?> con : getModelSourceConnections()) {
//			if (con instanceof ReUseKeyChangeListener && con != callConnection ) {
//				((ReUseKeyChangeListener) con).awareReUseKeysChanged();
//			}
//		}
//
//	}
}
