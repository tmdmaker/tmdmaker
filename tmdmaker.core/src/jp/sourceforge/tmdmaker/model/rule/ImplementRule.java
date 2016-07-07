/*
 * Copyright 2009-2016 TMD-Maker Project <http://tmdmaker.osdn.jp/>
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
package jp.sourceforge.tmdmaker.model.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.ConnectableElement;
import jp.sourceforge.tmdmaker.model.DataTypeDeclaration;
import jp.sourceforge.tmdmaker.model.Detail;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.IAttribute;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.IdentifierRef;
import jp.sourceforge.tmdmaker.model.ReusedIdentifier;
import jp.sourceforge.tmdmaker.model.SurrogateKey;
import jp.sourceforge.tmdmaker.model.SurrogateKeyRef;
import jp.sourceforge.tmdmaker.model.StandardSQLDataType;
import jp.sourceforge.tmdmaker.model.SubsetEntity;
import jp.sourceforge.tmdmaker.model.SubsetType;
import jp.sourceforge.tmdmaker.model.VirtualEntity;
import jp.sourceforge.tmdmaker.model.util.ModelEditUtils;

/**
 * 実装に関するルールをまとめたクラス
 * 
 * @author nakaG
 * 
 */
public class ImplementRule {
	private static boolean foreignKeyEnabled;

	/**
	 * 実装しない派生モデルを取得する。
	 * 
	 * @param model
	 *            派生元
	 * @return 実装しない派生モデルのリスト。対象が存在しない場合は空リストを返す。
	 */
	public static List<AbstractEntityModel> findNotImplementModel(AbstractEntityModel model) {
		List<AbstractEntityModel> subsets = new ArrayList<AbstractEntityModel>();
		List<AbstractEntityModel> ves = new ArrayList<AbstractEntityModel>();
		List<AbstractEntityModel> results = new ArrayList<AbstractEntityModel>();
		// 実装しない設定になっているサブセットを抽出
		findNotImplementSubset(subsets, model);

		// 実装しない設定になっているVEを抽出
		findNotImplementVirtualEntity(ves, model);

		// サブセットにVEが存在する場合を考慮
		for (AbstractEntityModel m : subsets) {
			findNotImplementVirtualEntity(ves, m);
		}
		results.addAll(subsets);
		results.addAll(ves);

		return results;
	}

	private static void findNotImplementSubset(List<AbstractEntityModel> results,
			AbstractEntityModel model) {
		SubsetType type = model.findSubsetType();
		if (type == null) {
			return;
		}

		for (AbstractConnectionModel connection : type.getModelSourceConnections()) {
			SubsetEntity subset = (SubsetEntity) connection.getTarget();
			if (subset.isNotImplement()) {
				results.add(subset);
			}
			findNotImplementSubset(results, subset);
		}
	}

	private static void findNotImplementVirtualEntity(List<AbstractEntityModel> results,
			AbstractEntityModel model) {
		for (AbstractConnectionModel connection : model.getModelSourceConnections()) {
			ConnectableElement e = connection.getTarget();
			if (e instanceof VirtualEntity) {
				VirtualEntity ve = (VirtualEntity) e;
				if (ve.isNotImplement()) {
					results.add(ve);
				}
				findNotImplementVirtualEntity(results, ve);
			}
		}
	}

	/**
	 * モデルの実装対象アトリビュートを取得する
	 * 
	 * @param model
	 *            対象モデル
	 * @return アトリビュートのリスト
	 */
	public static List<IAttribute> findAllImplementAttributes(AbstractEntityModel model) {
		List<IAttribute> attributes = new ArrayList<IAttribute>();
		SurrogateKey surrogateKey = model.getKeyModels().getSurrogateKey();
		if (surrogateKey.isEnabled()) {
			attributes.add(surrogateKey);
		}
		// 個体指定子を追加
		if (model instanceof Entity) {
			attributes.add(((Entity) model).getIdentifier());
		}
		if (model instanceof Detail) {
			if (((Detail) model).isDetailIdentifierEnabled()){
				attributes.add(((Detail) model).getDetailIdentifier());
			}
		}
		if (model instanceof SubsetEntity) {
			ReusedIdentifier reused = ((SubsetEntity) model).getOriginalReusedIdentifier();
			for (IdentifierRef ref : reused.getUniqueIdentifieres()) {
				attributes.add(ref);
			}
		}
		// re-usedを追加
		Map<AbstractEntityModel, ReusedIdentifier> reused = model.getReusedIdentifieres();
		for (Entry<AbstractEntityModel, ReusedIdentifier> entry : reused.entrySet()) {
			ReusedIdentifier ri = entry.getValue();
			if (ri == null) {
				continue;
			}
			if (ri.isSurrogateKeyEnabled()) {
				for (SurrogateKeyRef s : ri.getSurrogateKeys()) {
					attributes.add(s);
				}
			} else {
				for (IdentifierRef ref : ri.getUniqueIdentifieres()) {
					attributes.add(ref);
				}
			}
		}
		// モデルのアトリビュートを追加
		attributes.addAll(model.getAttributes());

		// 派生元に戻して実装するモデルのアトリビュートを追加
		for (AbstractEntityModel m : model.getImplementDerivationModels()) {
			attributes.addAll(m.getAttributes());
		}

		return attributes;
	}

	/**
	 * 実装対象でないサブセットとみなしエンティティの実装元モデル（候補含む）を取得する。
	 * 
	 * @param model
	 *            対象モデル（実装対象でないサブセットかみなしエンティティ）
	 * @return 実装元モデル（候補含む）
	 */
	public static AbstractEntityModel findOriginalImplementModel(AbstractEntityModel model) {
		if (model != null && model.isNotImplement()) {
			if (model instanceof SubsetEntity) {
				return findOriginalImplementModel(((SubsetEntity) model).getSuperset());
			} else if (model instanceof VirtualEntity) {
				return findOriginalImplementModel(((VirtualEntity) model).getRealEntity());
			} else {
				return model;
			}
		} else {
			return model;
		}
	}

	/**
	 * エンティティ系モデルの実装情報の初期値を設定する。
	 * 
	 * @param model
	 *            エンティティ系モデル
	 */
	public static void setModelDefaultValue(AbstractEntityModel model) {
		if (ModelEditUtils.isEmpty(model.getImplementName())) {
			model.setImplementName(model.getName());
		}
	}

	/**
	 * 個体指定子の実装情報の初期値を設定する。
	 * 
	 * @param identifier
	 *            個体指定子
	 */
	public static void setIdentifierDefaultValue(Identifier identifier) {
		if (ModelEditUtils.isEmpty(identifier.getImplementName())) {
			identifier.setImplementName(identifier.getName());
		}
		if (identifier.getDataTypeDeclaration() == null) {
			identifier.setDataTypeDeclaration(
					new DataTypeDeclaration(StandardSQLDataType.NUMERIC, 10, 0));
		}
	}

	/**
	 * イベントのアトリビュートの初期値を設定する。
	 * 
	 * @param attribute
	 *            アトリビュート
	 */
	public static void setEventDefaultAttributeValue(Attribute attribute) {
		attribute.setImplementName(attribute.getName());
		attribute.setDataTypeDeclaration(
				new DataTypeDeclaration(StandardSQLDataType.DATE, null, null));

	}

	/**
	 * リソースのアトリビュートの初期値を設定する。
	 * 
	 * @param attribute
	 *            アトリビュート
	 */
	public static void setResourceDefaultAttributeValue(Attribute attribute) {
		attribute.setImplementName(attribute.getName());
		attribute.setDataTypeDeclaration(
				new DataTypeDeclaration(StandardSQLDataType.CHARACTER_VARYING, 10, null));

	}

	/**
	 * サロゲートキーの初期値を設定する。
	 * 
	 * @param surrogateKey
	 *            サロゲートキー
	 */
	public static void setSurrogateKeyDefaultValue(SurrogateKey surrogateKey) {
		String implementName = surrogateKey.getImplementName();
		if (ModelEditUtils.isEmpty(implementName)) {
			surrogateKey.setImplementName(surrogateKey.getName());
		}
		DataTypeDeclaration dtd = surrogateKey.getDataTypeDeclaration();
		if (dtd == null) {
			surrogateKey.setDataTypeDeclaration(
					new DataTypeDeclaration(StandardSQLDataType.NUMERIC, 10, 0));
		}
	}

	public static boolean isForeignKeyEnabled() {
		return foreignKeyEnabled;
	}

	public static void setForeignKeyEnabled(boolean foreignKeyEnabled) {
		ImplementRule.foreignKeyEnabled = foreignKeyEnabled;
	}

}
