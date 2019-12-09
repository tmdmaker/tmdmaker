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
package org.tmdmaker.ui.dialogs.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.Detail;
import org.tmdmaker.core.model.Entity;
import org.tmdmaker.core.model.IAttribute;
import org.tmdmaker.core.model.IdentifierRef;
import org.tmdmaker.core.model.KeyModel;
import org.tmdmaker.core.model.KeyModels;
import org.tmdmaker.core.model.ReusedIdentifier;
import org.tmdmaker.core.model.SubsetEntity;
import org.tmdmaker.core.model.SurrogateKey;
import org.tmdmaker.core.model.SurrogateKeyRef;
import org.tmdmaker.core.model.rule.ImplementRule;
import org.tmdmaker.ui.util.ModelEditUtils;

/**
 * エンティティ実装情報の編集用
 * 
 * @author nakaG
 * 
 */
public class EditImplementEntity {
	/** プロパティ変更通知用 */
	private PropertyChangeSupport listeners = new PropertyChangeSupport(this);
	public static final String PROPERTY_ATTRIBUTES = "attributes"; //$NON-NLS-1$
	public static final String PROPERTY_SURROGATE = "surrogate"; //$NON-NLS-1$
	public static final String PROPERTY_KEYMODELS = "keymodels"; //$NON-NLS-1$
	private EditSurrogateKey surrogateKey;
	private List<EditImplementAttribute> attributes = new ArrayList<EditImplementAttribute>();
	private KeyModels keyModels = new KeyModels();
	private Map<AbstractEntityModel, List<EditImplementAttribute>> otherModelAttributesMap = new HashMap<AbstractEntityModel, List<EditImplementAttribute>>();
	private String implementName;

	/**
	 * コンストラクタ
	 * 
	 * @param model
	 *            実装情報編集対象モデル
	 */
	public EditImplementEntity(AbstractEntityModel model) {
		implementName = ModelEditUtils.toBlankStringIfNull(model.getImplementName());

		addSurrogateKey(model);

		addIdentifire(model);

		addReusedIdentifiers(model);

		addAttributes(model);

		// 対象モデルを元とした実装しないモデル（サブセット、みなしエンティティ）のattributeを抽出
		addOtherModelAttributes(model);

		// 対象モデルに戻して実装するモデルが保持するattributeを抽出
		addDerivationModelAttributes(model);

		updateEditImplementAttributes();
	}

	private void addDerivationModelAttributes(AbstractEntityModel model) {
		if (model.getImplementDerivationModels() != null) {
			for (AbstractEntityModel m : model.getImplementDerivationModels()) {
				List<EditImplementAttribute> list = otherModelAttributesMap.get(m);
				if (list != null) {
					attributes.addAll(list);
				}
			}
		}
	}

	private void addOtherModelAttributes(AbstractEntityModel model) {
		for (AbstractEntityModel m : ImplementRule.findNotImplementModel(model)) {
			List<EditImplementAttribute> list = new ArrayList<EditImplementAttribute>();
			for (IAttribute a : m.getAttributes()) {
				list.add(new EditImplementAttribute(m, a));
			}
			otherModelAttributesMap.put(m, list);
		}
	}

	private void addAttributes(AbstractEntityModel model) {
		for (IAttribute a : model.getAttributes()) {
			attributes.add(new EditImplementAttribute(model, a));
		}
	}

	private void addSurrogateKey(AbstractEntityModel model) {
		KeyModels originalKeyModels = model.getKeyModels();
		if (originalKeyModels == null) {
			return;
		}
		originalKeyModels.copyTo(keyModels);

		SurrogateKey key = originalKeyModels.getSurrogateKey();
		surrogateKey = new EditSurrogateKey(model, key);
		if (surrogateKey.isEnabled()) {
			attributes.add(surrogateKey);
		}
	}

	private void addReusedIdentifiers(AbstractEntityModel model) {
		Map<AbstractEntityModel, ReusedIdentifier> reused = model.getReusedIdentifiers();
		for (Entry<AbstractEntityModel, ReusedIdentifier> entry : reused.entrySet()) {
			ReusedIdentifier ri = entry.getValue();
			if (ri.isSurrogateKeyEnabled()) {
				// サロゲートキーをカラムとして使用
				for (SurrogateKeyRef s : ri.getSurrogateKeys()) {
					attributes.add(new EditImplementAttribute(model, s));
				}
			} else {
				// 個体指定子の参照をカラムとして使用
				for (IdentifierRef ref : ri.getUniqueIdentifiers()) {
					attributes.add(new EditImplementAttribute(model, ref));
				}
			}
		}
	}

	private void addIdentifire(AbstractEntityModel model) {
		if (model instanceof Entity) {
			attributes.add(new EditImplementAttribute(model, ((Entity) model).getIdentifier()));
		}
		if (model instanceof Detail && ((Detail) model).isDetailIdentifierEnabled()) {
			attributes
					.add(new EditImplementAttribute(model, ((Detail) model).getDetailIdentifier()));
		}

		if (model instanceof SubsetEntity) {
			ReusedIdentifier reused = ((SubsetEntity) model).getOriginalReusedIdentifier();
			for (IdentifierRef ref : reused.getUniqueIdentifiers()) {
				attributes.add(new EditImplementAttribute(model, ref));
			}

		}
	}

	/**
	 * サロゲートキーのenabledプロパティを設定する
	 * 
	 * @param enabled
	 *            サロゲートキーへ設定するプロパティ値
	 */
	public void setSurrogateKeyEnabled(boolean enabled) {
		surrogateKey.setEnabled(enabled);
		if (enabled) {
			addAttribute(0, surrogateKey);
		} else {
			removeAttribute(surrogateKey);
		}
		firePropertyChange(PROPERTY_ATTRIBUTES, null, surrogateKey);
	}

	/**
	 * サロゲートキーのenabledプロパティを返す
	 * 
	 * @return サロゲートキーのenabledプロパティ
	 */
	public boolean isSurrogateKeyEnabled() {
		return surrogateKey.isEnabled();
	}

	/**
	 * サロゲートキー名を返す
	 * 
	 * @return サロゲートキー名
	 */
	public String getSurrogateKeyName() {
		return surrogateKey.getName();
	}

	/**
	 * サロゲートキー名を設定する
	 * 
	 * @param name
	 *            サロゲートキー名
	 */
	public void setSurrogateKeyName(String name) {
		surrogateKey.setName(name);
		firePropertyChange(PROPERTY_SURROGATE, null, surrogateKey);
	}

	/**
	 * @return the surrogateKey
	 */
	public EditSurrogateKey getSurrogateKey() {
		return surrogateKey;
	}

	/**
	 * アトリビュートを追加する
	 * 
	 * @param attribute
	 *            追加するアトリビュート
	 */
	public void addAttribute(EditImplementAttribute attribute) {
		attributes.add(attribute);
		for (KeyModel ek : keyModels) {
			attribute.addKeyModel(ek);
		}
		firePropertyChange(PROPERTY_ATTRIBUTES, null, attribute);
	}

	/**
	 * アトリビュートを追加する
	 * 
	 * @param index
	 *            追加場所
	 * @param attribute
	 *            追加するアトリビュート
	 */
	public void addAttribute(int index, EditImplementAttribute attribute) {
		attributes.add(index, attribute);
		for (KeyModel ek : keyModels) {
			attribute.addKeyModel(ek);
		}
		firePropertyChange(PROPERTY_ATTRIBUTES, null, attribute);
	}

	/**
	 * アトリビュートを削除する
	 * 
	 * @param attribute
	 *            削除するアトリビュート
	 * @return 削除したアトリビュート。アトリビュートが存在しなかった場合はnullを返す。
	 */
	public EditImplementAttribute removeAttribute(EditImplementAttribute attribute) {
		int index = attributes.indexOf(attribute);
		if (index >= 0) {
			EditImplementAttribute removed = attributes.remove(index);
			List<KeyModel> toBeRemoved = new ArrayList<KeyModel>();
			for (KeyModel ek : keyModels) {
				ek.getAttributes().remove(removed.getOriginalAttribute());
				// アトリビュートが存在しないキーモデルは削除対象とする
				if (ek.getAttributes().isEmpty()) {
					toBeRemoved.add(ek);
				}
			}
			for (KeyModel ek : toBeRemoved) {
				keyModels.remove(ek);
				for (EditImplementAttribute a : attributes) {
					a.removeKeyModel(ek);
				}
			}
			return removed;
		} else {
			return null;
		}
	}

	/**
	 * キーモデルを返す
	 * 
	 * @param index
	 *            キーモデルのインデックス
	 * @return キーモデル
	 */
	public KeyModel getKeyModel(int index) {
		if (index <= keyModels.size() && index >= 0) {
			return keyModels.get(index);
		} else {
			return null;
		}
	}

	/**
	 * キーモデルを追加する
	 * 
	 * @param keyModel
	 *            追加するキーモデル
	 */
	public void addKeyModel(KeyModel keyModel) {
		keyModels.add(keyModel);
		updateEditImplementAttributes();
		firePropertyChange(PROPERTY_KEYMODELS, null, keyModel);
	}

	/**
	 * キーモデルを入れ替える
	 * 
	 * @param index
	 *            入れ替え場所
	 * @param keyModel
	 *            入れ換えるモデル
	 */
	public void replaceKeyModel(int index, KeyModel keyModel) {
		keyModels.replaceKeyModel(index, keyModel);
		updateEditImplementAttributes();
		firePropertyChange(PROPERTY_KEYMODELS, null, keyModel);
	}

	/**
	 * キーモデルを削除する
	 * 
	 * @param index
	 *            削除対象のインデックス
	 */
	public void removeKeyModel(int index) {
		keyModels.remove(index);
		updateEditImplementAttributes();
		firePropertyChange(PROPERTY_KEYMODELS, null, null);
	}

	private void updateEditImplementAttributes() {
		for (EditImplementAttribute ea : attributes) {
			ea.removeAllKeyModel();
			for (KeyModel ek : keyModels) {
				ea.addKeyModel(ek);
			}
		}
	}

	/**
	 * @return the attributes
	 */
	public List<EditImplementAttribute> getAttributes() {
		return attributes;
	}

	/**
	 * @return the keyModels
	 */
	public KeyModels getKeyModels() {
		return keyModels;
	}

	/**
	 * @return the otherModelAttributesMap
	 */
	public Map<AbstractEntityModel, List<EditImplementAttribute>> getOtherModelAttributesMap() {
		return otherModelAttributesMap;
	}

	/**
	 * @return the implementName
	 */
	public String getImplementName() {
		return implementName;
	}

	/**
	 * @param implementName
	 *            the implementName to set
	 */
	public void setImplementName(String implementName) {
		this.implementName = implementName;
	}

	/**
	 * プロパティ変更通知先追加
	 * 
	 * @param listener
	 *            プロパティ変更通知先
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		listeners.addPropertyChangeListener(listener);
	}

	/**
	 * プロパティ変更通知
	 * 
	 * @param propName
	 *            変更したプロパティの名称
	 * @param oldValue
	 *            変更前の値
	 * @param newValue
	 *            変更後の値
	 */
	public void firePropertyChange(String propName, Object oldValue, Object newValue) {
		listeners.firePropertyChange(propName, oldValue, newValue);
	}

	/**
	 * プロパティ変更通知先削除
	 * 
	 * @param listener
	 *            プロパティ変更通知先
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		listeners.removePropertyChangeListener(listener);
	}

}
