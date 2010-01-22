/*
 * Copyright 2009-2010 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
	 */
	public ReusedIdentifier(Identifier identifier) {
		addIdentifier(identifier);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param identifieres
	 *            Re-usedキー
	 */
	public ReusedIdentifier(List<Identifier> identifieres) {
		for (Identifier i : identifieres) {
			IdentifierRef identifierRef = new IdentifierRef(i);
			identifieres.add(identifierRef);
		}
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
	 * 参照元の個体指定子（またはRe-usedキー）を追加する
	 * 
	 * @param identifieres
	 *            参照元の個体指定子（またはRe-usedキー）
	 */
	public void addAll(List<IdentifierRef> identifieres) {
		for (IdentifierRef i : identifieres) {
			this.identifieres.add(i);
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
}
