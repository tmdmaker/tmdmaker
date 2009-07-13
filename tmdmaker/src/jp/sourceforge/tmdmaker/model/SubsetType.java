package jp.sourceforge.tmdmaker.model;

import java.util.ArrayList;
import java.util.List;

/**
 * サブセット種類モデル
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class SubsetType extends ConnectableElement {

	/** サブセット種類（同一、相違） */
	public enum SubsetTypeValue {
		/** 同一 */
		SAME,
		/** 相違 */
		DIFFERENT
	};

	/** サブセット種類 */
	private SubsetTypeValue subsetType = SubsetTypeValue.SAME;
	/** サブセットタイプ */
	public static final String PROPERTY_TYPE = "_property_type";
	/** 区分コードの属性 */
//	private Attribute partitionAttribute;

	/**
	 * サブセットエンティティ取得
	 * 
	 * @return サブセットエンティティのリスト
	 */
	public List<SubsetEntity> findSubsetEntityList() {
		List<SubsetEntity> results = new ArrayList<SubsetEntity>();
		for (AbstractConnectionModel<?> c : getModelSourceConnections()) {
			if (c instanceof RelatedRelationship) {
				results.add((SubsetEntity) c.getTarget());
			}
		}
		return results;
	}

	/**
	 * @return the subsetType
	 */
	public SubsetTypeValue getSubsetType() {
		return subsetType;
	}

	/**
	 * @param subsetType
	 *            the subsetType to set
	 */
	public void setSubsetType(SubsetTypeValue subsetType) {
		SubsetTypeValue oldValue = this.subsetType;
		this.subsetType = subsetType;
		firePropertyChange(PROPERTY_TYPE, oldValue, this.subsetType);
	}

	/**
	 * オブジェクト破棄
	 */
	public void dispose() {
		// TODO
	}
	
}
