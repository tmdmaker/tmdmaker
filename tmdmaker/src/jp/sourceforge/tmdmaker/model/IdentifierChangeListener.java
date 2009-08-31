package jp.sourceforge.tmdmaker.model;

/**
 * 個体指示子変更リスナー
 * 
 * @author nakaG
 * 
 */
public interface IdentifierChangeListener {
	/**
	 * 個体指示子（Re-used）が変更された場合の処理を記述する
	 */
	abstract void identifierChanged();

}