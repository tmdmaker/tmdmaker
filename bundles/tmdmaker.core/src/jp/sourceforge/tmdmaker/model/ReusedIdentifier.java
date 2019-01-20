/*
 * Copyright 2009-2019 TMD-Maker Project <https://tmdmaker.osdn.jp/>
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
	private List<IdentifierRef> identifiers = new ArrayList<IdentifierRef>();
	/** 参照元のサロゲートキー */
	private List<SurrogateKeyRef> surrogateKeys = new ArrayList<SurrogateKeyRef>();

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
	 * @param surrogateKey
	 *            参照元のサロゲートキー
	 */
	public ReusedIdentifier(Identifier identifier, SurrogateKey surrogateKey) {
		addIdentifier(identifier);
		this.surrogateKeys.add(new SurrogateKeyRef(surrogateKey));
	}

	/**
	 * コンストラクタ
	 * 
	 * @param surrogateKey
	 *            参照元のサロゲートキー
	 */
	public ReusedIdentifier(SurrogateKey surrogateKey) {
		this.surrogateKeys.add(new SurrogateKeyRef(surrogateKey));
	}

	/**
	 * コンストラクタ. 再帰表が作成される時に利用する
	 * 
	 * @param surrogateKey1
	 *            参照元のサロゲートキー
	 * @param surrogateKey2
	 *            参照元のサロゲートキー
	 */
	public ReusedIdentifier(SurrogateKey surrogateKey1, SurrogateKey surrogateKey2) {
		this.surrogateKeys.add(new SurrogateKeyRef(surrogateKey1));
		this.surrogateKeys.add(new SurrogateKeyRef(surrogateKey2));
	}

	/**
	 * 参照元の個体指定子（またはRe-usedキー）を返す
	 * 
	 * @return 参照元の個体指定子（またはRe-usedキー）
	 */
	public List<IdentifierRef> getIdentifiers() {
		return Collections.unmodifiableList(identifiers);
	}

	/**
	 * 参照元の個体指定子（またはRe-usedキー）を返す。 sourceとtargetで重複する個体指定子は１つにまとめる。
	 * 
	 * @return 重複を排除した参照元の個体指定子（またはRe-usedキー）。
	 */
	public List<IdentifierRef> getUniqueIdentifiers() {
		List<IdentifierRef> list = new ArrayList<IdentifierRef>(identifiers.size());
		for (IdentifierRef i : identifiers) {
			if (!i.isDuplicate()) {
				list.add(i);
			}
		}
		return Collections.unmodifiableList(list);
	}

	/**
	 * @return the surrogateKeys
	 */
	public List<SurrogateKeyRef> getSurrogateKeys() {
		return Collections.unmodifiableList(surrogateKeys);
	}

	/**
	 * 参照元の個体指定子（またはRe-usedキー）を追加する
	 * 
	 * @param identifiers
	 *            参照元の個体指定子（またはRe-usedキー）
	 */
	public void addAll(List<IdentifierRef> identifiers) {
		for (IdentifierRef i : identifiers) {
			this.identifiers.add(new IdentifierRef(i));
		}
	}

	public void addIdentifier(Identifier identifier) {
		IdentifierRef identifierRef = new IdentifierRef(identifier);
		this.identifiers.add(identifierRef);
	}

	/**
	 * オブジェクトを破棄する
	 */
	public void dispose() {
		identifiers.clear();
	}

	public boolean isSurrogateKeyEnabled() {
		if (surrogateKeys != null && !surrogateKeys.isEmpty()) {
			SurrogateKeyRef surrogateKey = surrogateKeys.get(0);
			return surrogateKey != null && surrogateKey.isEnabled();
		} else {
			return false;
		}
	}
}
