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
package org.tmdmaker.ui.editor.draw2d.figure.node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.Identifier;
import org.tmdmaker.core.model.ReusedIdentifier;
import org.tmdmaker.ui.preferences.appearance.ModelAppearance;

/**
 * TMのエンティティ系モデルを表現するFigureの親クラス.
 * 
 * T字の箱の中に何を描画するかは本クラスのサブクラスに実装する.
 * 
 * @author nakag
 *
 * @param <T>
 *            AbstractEntityModelを継承したエンティティ系クラス.
 */
public abstract class AbstractModelFigure<T extends AbstractEntityModel> extends TFormFigure {

	/**
	 * コンストラクタ.
	 */
	public AbstractModelFigure() {
		this(false);
	}

	/**
	 * コンストラクタ.
	 *
	 * @param notImplement
	 *            実装するか？
	 */
	public AbstractModelFigure(boolean notImplement) {
		super(notImplement);
	}

	/**
	 * モデルを元にFigureを（再）描画する.
	 * 
	 * @param model
	 */
	public void update(T model) {
		setEntityName(getName(model));
		setEntityType(getTypeLabel(model));
		setNotImplement(isNotImplement(model));
		removeAllRelationship();
		addIdentifier(setupIdentifierList(model));
		addRelationship(setupRelationshipList(model));
		setupColor(getAppearance(model));
	}

	/**
	 * T字に描画するモデルの名称を返す.
	 * 
	 * @param model
	 * @return デフォルトはmodelの名称.
	 */
	protected String getName(T model) {
		return model.getName();
	}

	/**
	 * T字に描画するモデルの種類を返す.
	 * 
	 * @param model
	 * @return デフォルトはmodelの種類のラベル.
	 */
	protected String getTypeLabel(T model) {
		return model.getEntityType().getLabel();
	}

	/**
	 * T字に描画する実装可否を返す.
	 * 
	 * @param model
	 * @return デフォルトはmodelの実装可否を返す.
	 */
	protected boolean isNotImplement(T model) {
		return model.isNotImplement();
	}

	/**
	 * T字に描画する個体指定子を返す.
	 * 
	 * @param model
	 * @return デフォルトは空.
	 */
	protected List<String> setupIdentifierList(T model) {
		return Collections.emptyList();
	}

	/**
	 * T字に描画する(R)を返す.
	 * 
	 * @param model
	 * @return デフォルトはモデルのreusedIdentifierのリストを返す.
	 */
	protected List<String> setupRelationshipList(T model) {
		List<String> relationship = new ArrayList<String>();
		for (Map.Entry<AbstractEntityModel, ReusedIdentifier> rk : model.getReusedIdentifiers()
				.entrySet()) {
			for (Identifier i : rk.getValue().getUniqueIdentifiers()) {
				relationship.add(i.getName());
			}
		}
		return relationship;
	}

	/**
	 * モデルの外観（色）を返す.
	 * 
	 * @param model
	 * @return デフォルトは未設定.
	 */
	protected ModelAppearance getAppearance(T model) {
		return null;
	}

	/**
	 * モデルのサイズを自動調整可能か？
	 *
	 * @return デフォルトはtrueを返す
	 */
	public boolean canAutoSize() {
		return true;
	}
}