/*
 * Copyright 2009-2014 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package jp.sourceforge.tmdmaker.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Re-usedのモデル
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class ReusedIdentifier implements Serializable {
	/** 参照元の個体指定子（またはRe-usedキー） */
	private List<IdentifierRef> identifieres = new ArrayList<IdentifierRef>();
	/** 参照元のサロゲートキー */
	private List<SarogateKeyRef> sarogateKeys = new ArrayList<SarogateKeyRef>();

	/**
	 * デフォルトコンストラクタ
	 */
	public ReusedIdentifier() {
	}

	/**
	 * コンストラクタ
	 * 
	 * @param identifier
	 *            参照元の個体指定子
	 * @param sarogateKey
	 *            参照元のサロゲートキー
	 */
	public ReusedIdentifier(Identifier identifier, SarogateKey sarogateKey) {
		addIdentifier(identifier);
		this.sarogateKeys.add(new SarogateKeyRef(sarogateKey));
	}

	/**
	 * コンストラクタ
	 * 
	 * @param sarogateKey
	 *            参照元のサロゲートキー
	 */
	public ReusedIdentifier(SarogateKey sarogateKey) {
		this.sarogateKeys.add(new SarogateKeyRef(sarogateKey));
	}

	/**
	 * コンストラクタ. 再帰表が作成される時に利用する
	 * 
	 * @param sarogateKey
	 *            参照元のサロゲートキー
	 * @param sarogateKey
	 *            参照元のサロゲートキー
	 */
	public ReusedIdentifier(SarogateKey sarogateKey1, SarogateKey sarogateKey2) {
		this.sarogateKeys.add(new SarogateKeyRef(sarogateKey1));
		this.sarogateKeys.add(new SarogateKeyRef(sarogateKey2));
	}

	/**
	 * 参照元の個体指定子（またはRe-usedキー）を返す
	 * 
	 * @return 参照元の個体指定子（またはRe-usedキー）
	 */
	public List<IdentifierRef> getIdentifires() {
		return Collections.unmodifiableList(identifieres);
	}

	/**
	 * 参照元の個体指定子（またはRe-usedキー）を返す。
	 * sourceとtargetで重複する個体指定子は１つにまとめる。
	 * @return 重複を排除した参照元の個体指定子（またはRe-usedキー）。
	 */
	public List<IdentifierRef> getUniqueIdentifieres() {
		List<IdentifierRef> list = new ArrayList<IdentifierRef>(identifieres.size());
		for(IdentifierRef i : identifieres) {
			if (!i.isDuplicate()) {
				list.add(i);
			}
		}
		return Collections.unmodifiableList(list);
	}
	
	/**
	 * @return the sarogateKey
	 */
	public List<SarogateKeyRef> getSarogateKeys() {
		return Collections.unmodifiableList(sarogateKeys);
	}

	/**
	 * 参照元の個体指定子（またはRe-usedキー）を追加する
	 * 
	 * @param identifieres
	 *            参照元の個体指定子（またはRe-usedキー）
	 */
	public void addAll(List<IdentifierRef> identifieres) {
		for (IdentifierRef i : identifieres) {
			this.identifieres.add(new IdentifierRef(i));
		}
	}

	public void addIdentifier(Identifier identifier) {
		IdentifierRef identifierRef = new IdentifierRef(identifier);
		this.identifieres.add(identifierRef);
	}

	/**
	 * オブジェクトを破棄する
	 */
	public void dispose() {
		identifieres.clear();
	}

	public boolean isSarogateKeyEnabled() {
		if (sarogateKeys != null && sarogateKeys.size() > 0) {
			SarogateKeyRef sarogateKey = sarogateKeys.get(0);
			return sarogateKey != null && sarogateKey.isEnabled();
		} else {
			return false;
		}
	}
}
