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
public class ReUseKey implements Serializable {
	/** 参照元の個体指示子（またはRe-usedキー） */
	private List<Identifier> identifieres = new ArrayList<Identifier>();

	/**
	 * デフォルトコンストラクタ
	 */
	public ReUseKey() {
	}

	/**
	 * コンストラクタ
	 * 
	 * @param identifier
	 *            参照元の個体指示子
	 */
	public ReUseKey(Identifier identifier) {
		IdentifierRef identifierRef = new IdentifierRef(identifier);
		this.identifieres.add(identifierRef);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param identifieres
	 *            Re-usedキー
	 */
	public ReUseKey(List<Identifier> identifieres) {
		for (Identifier i : identifieres) {
			IdentifierRef identifierRef = new IdentifierRef(i);
			identifieres.add(identifierRef);
		}
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
