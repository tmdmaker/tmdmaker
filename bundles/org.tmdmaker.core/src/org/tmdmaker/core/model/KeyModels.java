/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org>
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
package org.tmdmaker.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * キーモデルのコンテナ
 * 
 * @author nakaG
 * 
 */
public class KeyModels implements Iterable<KeyModel>, Serializable {
	private static final long serialVersionUID = 1L;
	/** キーモデルのリスト */
	private List<KeyModel> keys = new ArrayList<KeyModel>();
	/** マスターキーとして指定したキーモデル */
	private KeyModel masterKey;
	/** サロゲートキー */
	private SurrogateKey surrogateKey;

	/**
	 * @return the masterKey
	 */
	public KeyModel getMasterKey() {
		return masterKey;
	}

	/**
	 * キーモデルを追加する
	 * 
	 * @param keyModel
	 *            追加するキーモデル
	 */
	public void add(KeyModel keyModel) {
		if (keyModel.isMasterKey()) {
			add(0, keyModel);
		} else {
			keys.add(keyModel);
		}
	}

	private void add(int index, KeyModel keyModel) {
		if (keyModel.isMasterKey()) {
			setMasterKey(keyModel);
			keys.add(0, keyModel);
		} else {
			keys.add(index, keyModel);
		}
	}

	private void setMasterKey(KeyModel keyModel) {
		if (masterKey != null) {
			masterKey.setMasterKey(false);
		}
		masterKey = keyModel;
	}

	/**
	 * キーモデルを置き換える
	 * 
	 * @param index
	 *            置き換えるモデルのindex
	 * @param keyModel
	 *            置き換えるモデル
	 */
	public void replaceKeyModel(int index, KeyModel keyModel) {
		remove(index);
		add(index, keyModel);
	}

	/**
	 * キーモデルを削除する
	 * 
	 * @param index
	 *            削除するモデルのindex
	 */
	public void remove(int index) {
		KeyModel model = keys.get(index);
		if (model == null) {
			return;
		}
		if (model.isMasterKey()) {
			setMasterKey(null);
		}
		keys.remove(index);
	}

	/**
	 * キーモデルを削除する
	 * 
	 * @param model
	 *            削除するモデル
	 */
	public void remove(KeyModel model) {
		if (model.isMasterKey()) {
			setMasterKey(null);
		}
		keys.remove(model);
	}

	/**
	 * キーモデルを取得する
	 * 
	 * @param index
	 *            取得するモデルのindex
	 * @return キーモデル
	 */
	public KeyModel get(int index) {
		return keys.get(index);
	}

	/**
	 * キーモデルの要素数を取得する
	 * 
	 * @return キーモデルの要素数
	 */
	public int size() {
		return keys.size();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<KeyModel> iterator() {
		return new KeyModelIterator(keys);
	}

	/**
	 * @return the surrogateKey
	 */
	public SurrogateKey getSurrogateKey() {
		if (surrogateKey == null) {
			surrogateKey = new SurrogateKey();
		}
		return surrogateKey;
	}

	/**
	 * @param surrogateKey
	 *            the surrogateKey to set
	 */
	protected void setSurrogateKey(SurrogateKey surrogateKey) {
		this.surrogateKey = surrogateKey;
	}

	/**
	 * 自身の値をtoへコピーする
	 * 
	 * @param to
	 *            コピー先
	 */
	public void copyTo(KeyModels to) {
		to.setSurrogateKey(getSurrogateKey());
		for (KeyModel k : this) {
			to.add(k.getCopy());
		}
	}

	/*
	 * キーモデルのイテレーター
	 */
	static class KeyModelIterator implements Iterator<KeyModel> {
		private List<KeyModel> keyModelList;
		private int index = 0;

		/**
		 * コンストラクタ
		 * 
		 * @param keyModelList
		 *            キーモデルのリスト
		 */
		public KeyModelIterator(List<KeyModel> keyModelList) {
			this.keyModelList = keyModelList;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
		public boolean hasNext() {
			return keyModelList.size() > index;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.util.Iterator#next()
		 */
		@Override
		public KeyModel next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return keyModelList.get(index++);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.util.Iterator#remove()
		 */
		@Override
		public void remove() {
			keyModelList.remove(index);
		}
	}
}
