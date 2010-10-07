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
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.Detail;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.IdentifierRef;
import jp.sourceforge.tmdmaker.model.KeyModel;
import jp.sourceforge.tmdmaker.model.KeyModels;
import jp.sourceforge.tmdmaker.model.ReusedIdentifier;
import jp.sourceforge.tmdmaker.model.SarogateKey;
import jp.sourceforge.tmdmaker.model.rule.ImplementRule;

/**
 * @author nakaG
 *
 */
public class EditImplementEntity {
	/** プロパティ変更通知用 */
	private PropertyChangeSupport listeners = new PropertyChangeSupport(this);
	public static final String PROPERTY_ATTRIBUTES = "attributes";
	public static final String PROPERTY_SAROGATE = "sarogate";
	private AbstractEntityModel model;
	private EditSarogateKey sarogateKey;
	private List<EditImplementAttribute> attributes = new ArrayList<EditImplementAttribute>();
	private KeyModels keyModels = new KeyModels();
	private Map<AbstractEntityModel, List<EditImplementAttribute>> otherModelAttributesMap = new HashMap<AbstractEntityModel, List<EditImplementAttribute>>();

	public EditImplementEntity(AbstractEntityModel model) {
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

		if (model instanceof Entity) {
			attributes.add(new EditImplementAttribute(model,((Entity) model)
					.getIdentifier()));
		} else if (model instanceof Detail) {
			attributes.add(new EditImplementAttribute(model, ((Detail) model)
					.getDetailIdentifier()));
		}
		// Re-usedをカラムとして追加
		Map<AbstractEntityModel, ReusedIdentifier> reused = model
				.getReusedIdentifieres();
		for (Entry<AbstractEntityModel, ReusedIdentifier> entry : reused
				.entrySet()) {
			for (IdentifierRef ref : entry.getValue().getIdentifires()) {
				attributes.add(new EditImplementAttribute(model, ref));
			}
		}
		// attributeをカラムとして追加
		for (Attribute a : model.getAttributes()) {
			attributes.add(new EditImplementAttribute(model, a));
		}
		// 対象モデルを元とした実装しないモデル（サブセット、みなしエンティティ）のattributeを抽出
		for (AbstractEntityModel m : ImplementRule.findNotImplementModel(model)) {
			List<EditImplementAttribute> list = new ArrayList<EditImplementAttribute>();
			for (Attribute a : m.getAttributes()) {
				list.add(new EditImplementAttribute(m, a));
			}
			otherModelAttributesMap.put(m, list);			
		}
		// 対象モデルに戻して実装するモデルが保持するattributeを抽出
		if (model.getImplementDerivationModels() != null) {
			for (AbstractEntityModel m : model.getImplementDerivationModels()) {
				List<EditImplementAttribute> list = otherModelAttributesMap.get(m);
				if (list != null) {
					attributes.addAll(list);
				}
			}
		}
	}
	public boolean hasSarogateKey() {
		return sarogateKey.isEnabled();
	}
	public void setSarogateKeyEnabled(boolean enabled) {
		sarogateKey.setEnabled(enabled);
		if (enabled) {
			addAttribute(0, sarogateKey);
		} else {
			attributes.remove(sarogateKey);
		}
		firePropertyChange(PROPERTY_ATTRIBUTES, null, sarogateKey);
	}
	public boolean isSarogateKeyEnabled() {
		return sarogateKey.isEnabled();
	}
	public String getSarogateKeyName() {
		return sarogateKey.getName();
	}
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
	public void addAttribute(EditImplementAttribute attribute) {
		attributes.add(attribute);
		for (KeyModel ek : keyModels) {
			attribute.addKeyModel(ek);
		}

		firePropertyChange(PROPERTY_ATTRIBUTES, null, attribute);
	}
	public void addAttribute(int index, EditImplementAttribute attribute) {
		attributes.add(index, attribute);
		firePropertyChange(PROPERTY_ATTRIBUTES, null, attribute);
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
