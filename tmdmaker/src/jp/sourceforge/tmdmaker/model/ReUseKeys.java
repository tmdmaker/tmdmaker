package jp.sourceforge.tmdmaker.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Re-usedキーモデル
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class ReUseKeys implements Serializable {
	/** 参照元の個体指示子（またはRe-usedキー） */
	private List<Identifier> identifieres = new ArrayList<Identifier>();

	/**
	 * デフォルトコンストラクタ
	 */
	public ReUseKeys() {
	}

	/**
	 * コンストラクタ
	 * 
	 * @param identifier
	 *            参照元の固体指示子
	 */
	public ReUseKeys(Identifier identifier) {
		this.identifieres.add(identifier);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param identifieres
	 *            Re-usedキー
	 */
	public ReUseKeys(List<Identifier> identifieres) {
		identifieres.addAll(identifieres);
	}

	/**
	 * 参照元の個体指示子（またはRe-usedキー）を返す
	 * 
	 * @return 参照元の個体指示子（またはRe-usedキー）
	 */
	public List<Identifier> getIdentifires() {
		return Collections.unmodifiableList(identifieres);
	}

	/**
	 * 参照元の個体指示子（またはRe-usedキー）を追加する
	 * 
	 * @param identifieres
	 *            参照元の個体指示子（またはRe-usedキー）
	 */
	public void addAll(List<Identifier> identifieres) {
		this.identifieres.addAll(identifieres);
	}

	/**
	 * オブジェクトを破棄する
	 */
	public void dispose() {
		identifieres.clear();
	}
}
