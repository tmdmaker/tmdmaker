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
package jp.sourceforge.tmdmaker.dialog.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Detail;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.IAttribute;
import jp.sourceforge.tmdmaker.model.IdentifierRef;
import jp.sourceforge.tmdmaker.model.KeyModel;
import jp.sourceforge.tmdmaker.model.KeyModels;
import jp.sourceforge.tmdmaker.model.ReusedIdentifier;
import jp.sourceforge.tmdmaker.model.SarogateKey;
import jp.sourceforge.tmdmaker.model.SarogateKeyRef;
import jp.sourceforge.tmdmaker.model.SubsetEntity;
import jp.sourceforge.tmdmaker.model.rule.ImplementRule;
import jp.sourceforge.tmdmaker.model.util.ModelEditUtils;

/**
 * エンティティ実装情報の編集用
 * 
 * @author nakaG
 * 
 */
public class EditImplementEntity {
	/** プロパティ変更通知用 */
	private PropertyChangeSupport listeners = new PropertyChangeSupport(this);
	public static final String PROPERTY_ATTRIBUTES = "attributes";
	public static final String PROPERTY_SAROGATE = "sarogate";
	public static final String PROPERTY_KEYMODELS = "keymodels";
	// private AbstractEntityModel model;
	private EditSarogateKey sarogateKey;
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
		// 対象モデルのキーを抽出
		KeyModels originalKeyModels = model.getKeyModels();
		if (originalKeyModels != null) {
			originalKeyModels.copyTo(keyModels);

			SarogateKey key = originalKeyModels.getSarogateKey();
			sarogateKey = new EditSarogateKey(model, key);
			if (sarogateKey.isEnabled()) {
				attributes.add(sarogateKey);
			}
		}
		// 個体指定子をカラムとして追加
		if (model instanceof Entity) {
			attributes.add(new EditImplementAttribute(model, ((Entity) model)
					.getIdentifier()));
		}
		if (model instanceof Detail) {
			attributes.add(new EditImplementAttribute(model, ((Detail) model)
					.getDetailIdentifier()));
		}
		if (model instanceof SubsetEntity) {
			ReusedIdentifier reused = ((SubsetEntity) model).getOriginalReusedIdentifier();
			for (IdentifierRef ref : reused.getIdentifires()) {
				attributes.add(new EditImplementAttribute(model, ref));
			}

		}
		// Re-usedをカラムとして追加
		Map<AbstractEntityModel, ReusedIdentifier> reused = model
				.getReusedIdentifieres();
		for (Entry<AbstractEntityModel, ReusedIdentifier> entry : reused
				.entrySet()) {
			ReusedIdentifier ri = entry.getValue();
			if (ri.isSarogateKeyEnabled()) {
				// サロゲートキーをカラムとして使用
				System.out.println("ri.isSarogateKeyEnabled()");
				for (SarogateKeyRef s : ri.getSarogateKeys()) {
					attributes.add(new EditImplementAttribute(model, s));
				}
			} else {
				System.out.println("not ri.isSarogateKeyEnabled()");
				// 個体指定子の参照をカラムとして使用
				for (IdentifierRef ref : ri.getIdentifires()) {
					attributes.add(new EditImplementAttribute(model, ref));
				}
			}
		}
		// attributeをカラムとして追加
		for (IAttribute a : model.getAttributes()) {
			attributes.add(new EditImplementAttribute(model, a));
		}
		// 対象モデルを元とした実装しないモデル（サブセット、みなしエンティティ）のattributeを抽出
		for (AbstractEntityModel m : ImplementRule.findNotImplementModel(model)) {
			List<EditImplementAttribute> list = new ArrayList<EditImplementAttribute>();
			for (IAttribute a : m.getAttributes()) {
				list.add(new EditImplementAttribute(m, a));
			}
			otherModelAttributesMap.put(m, list);
		}
		// 対象モデルに戻して実装するモデルが保持するattributeを抽出
		if (model.getImplementDerivationModels() != null) {
			for (AbstractEntityModel m : model.getImplementDerivationModels()) {
				List<EditImplementAttribute> list = otherModelAttributesMap
						.get(m);
				if (list != null) {
					attributes.addAll(list);
				}
			}
		}
		updateEditImplementAttributes();
	}

	/**
	 * サロゲートキーのenabledプロパティを設定する
	 * 
	 * @param enabled
	 *            サロゲートキーへ設定するプロパティ値
	 */
	public void setSarogateKeyEnabled(boolean enabled) {
		sarogateKey.setEnabled(enabled);
		if (enabled) {
			addAttribute(0, sarogateKey);
		} else {
			removeAttribute(sarogateKey);
		}
		firePropertyChange(PROPERTY_ATTRIBUTES, null, sarogateKey);
	}

	/**
	 * サロゲートキーのenabledプロパティを返す
	 * 
	 * @return サロゲートキーのenabledプロパティ
	 */
	public boolean isSarogateKeyEnabled() {
		return sarogateKey.isEnabled();
	}

	/**
	 * サロゲートキー名を返す
	 * 
	 * @return サロゲートキー名
	 */
	public String getSarogateKeyName() {
		return sarogateKey.getName();
	}

	/**
	 * サロゲートキー名を設定する
	 * 
	 * @param name
	 *            サロゲートキー名
	 */
	public void setSarogateKeyName(String name) {
		sarogateKey.setName(name);
		firePropertyChange(PROPERTY_SAROGATE, null, sarogateKey);
	}

	/**
	 * @return the sarogateKey
	 */
	public EditSarogateKey getSarogateKey() {
		return sarogateKey;
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
	public EditImplementAttribute removeAttribute(
			EditImplementAttribute attribute) {
		int index = attributes.indexOf(attribute);
		if (index >= 0) {
			EditImplementAttribute removed = attributes.remove(index);
			List<KeyModel> toBeRemoved = new ArrayList<KeyModel>();
			for (KeyModel ek : keyModels) {
				ek.getAttributes().remove(removed.getOriginalAttribute());
				// アトリビュートが存在しないキーモデルは削除対象とする
				if (ek.getAttributes().size() == 0) {
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
	 * @param implementName the implementName to set
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
	public void firePropertyChange(String propName, Object oldValue,
			Object newValue) {
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
