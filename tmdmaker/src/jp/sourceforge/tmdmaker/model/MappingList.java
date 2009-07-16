package jp.sourceforge.tmdmaker.model;

import java.util.Map;

/**
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class MappingList extends AbstractEntityModel {
	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#getMyReuseKey()
	 */
	@Override
	public ReUseKeys getMyReuseKey() {
		ReUseKeys returnValue = new ReUseKeys();
		for (Map.Entry<AbstractEntityModel, ReUseKeys> rk : this.reuseKeys
				.entrySet()) {
			returnValue.addAll(rk.getValue().getIdentifires());
		}
		return returnValue;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#canEntityTypeEditable()
	 */
	@Override
	public boolean canEntityTypeEditable() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#canDeletable()
	 */
	@Override
	public boolean canDeletable() {
		return getModelSourceConnections().size() == 0
				&& getModelTargetConnections().size() == 1;
	}
	/**
	 * 対応表作成時のリレーションシップを取得する
	 * @return 対応表作成時のリレーションシップ（リレーションシップへのリレーションシップ）
	 */
	public RelatedRelationship findCreationRelationship() {
		AbstractConnectionModel<?> r = getModelTargetConnections().get(0);
		assert r instanceof RelatedRelationship;
		return (RelatedRelationship) r;
	}
}
