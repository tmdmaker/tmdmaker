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
	/** 区分コードプロパティ定数 */
	public static final String PROPERTY_PARTITION = "_property_partition";
	/** サブセットタイプ */
	public static final String PROPERTY_TYPE = "_property_type";
	/** 区分コードの属性 */
	private Attribute partitionAttribute;
	/** NULLを排除（形式的サブセット）するか？ */
	private boolean exceptNull;

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
	 * @return the partitionAttribute
	 */
	public Attribute getPartitionAttribute() {
		return partitionAttribute;
	}

	/**
	 * @param partitionAttribute the partitionAttribute to set
	 */
	public void setPartitionAttribute(Attribute partitionAttribute) {
		Attribute oldValue = this.partitionAttribute;
		this.partitionAttribute = partitionAttribute;
		firePropertyChange(PROPERTY_PARTITION, oldValue, partitionAttribute);
	}

	/**
	 * @return the exceptNull
	 */
	public boolean isExceptNull() {
		return exceptNull;
	}

	/**
	 * @param exceptNull the exceptNull to set
	 */
	public void setExceptNull(boolean exceptNull) {
		this.exceptNull = exceptNull;
	}

	/**
	 * オブジェクト破棄
	 */
	public void dispose() {
		// TODO
	}
	
}
