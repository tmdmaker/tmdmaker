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
package jp.sourceforge.tmdmaker.model.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.ConnectableElement;
import jp.sourceforge.tmdmaker.model.Detail;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.IdentifierRef;
import jp.sourceforge.tmdmaker.model.ReusedIdentifier;
import jp.sourceforge.tmdmaker.model.SubsetEntity;
import jp.sourceforge.tmdmaker.model.SubsetType;
import jp.sourceforge.tmdmaker.model.VirtualEntity;

/**
 * 実装に関するルールをまとめたクラス
 * 
 * @author nakaG
 * 
 */
public class ImplementRule {
	/**
	 * 実装しない派生モデルを取得する。
	 * 
	 * @param model
	 *            派生元
	 * @return 実装しない派生モデルのリスト。対象が存在しない場合は空リストを返す。
	 */
	public static List<AbstractEntityModel> findNotImplementModel(
			AbstractEntityModel model) {
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

	private static void findNotImplementSubset(
			List<AbstractEntityModel> results, AbstractEntityModel model) {
		SubsetType type = model.findSubsetType();
		if (type == null) {
			return;
		}

		for (AbstractConnectionModel connection : type
				.getModelSourceConnections()) {
			SubsetEntity subset = (SubsetEntity) connection.getTarget();
			if (subset.isNotImplement()) {
				results.add(subset);
			}
			findNotImplementSubset(results, subset);
		}
	}

	private static void findNotImplementVirtualEntity(
			List<AbstractEntityModel> results, AbstractEntityModel model) {
		for (AbstractConnectionModel connection : model
				.getModelSourceConnections()) {
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
	public static List<Attribute> findAllImplementAttributes(
			AbstractEntityModel model) {
		List<Attribute> attributes = new ArrayList<Attribute>();
		// 個体指定子を追加
		if (model instanceof Entity) {
			attributes.add(((Entity) model).getIdentifier());
		}
		if (model instanceof Detail) {
			attributes.add(((Detail) model).getDetailIdentifier());
		}
		// re-usedを追加
		Map<AbstractEntityModel, ReusedIdentifier> reused = model
				.getReusedIdentifieres();
		for (Entry<AbstractEntityModel, ReusedIdentifier> entry : reused
				.entrySet()) {
			for (IdentifierRef ref : entry.getValue().getIdentifires()) {
				attributes.add(ref);
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
}
