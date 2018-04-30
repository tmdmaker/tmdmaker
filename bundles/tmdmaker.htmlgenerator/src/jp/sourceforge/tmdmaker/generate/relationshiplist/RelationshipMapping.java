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
package jp.sourceforge.tmdmaker.generate.relationshiplist;

import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.RecursiveRelationship;
import jp.sourceforge.tmdmaker.model.RecursiveTable;

/**
 * 関係の検証データ
 * 
 * @author nakaG
 * 
 */
public class RelationshipMapping {
	/** 比較元 */
	private AbstractEntityModel source;
	/** 比較先 */
	private AbstractEntityModel target;
	/** リレーションシップ有無 */
	private boolean relationship = false;

	/**
	 * コンストラクタ
	 * 
	 * @param source
	 * @param target
	 */
	public RelationshipMapping(AbstractEntityModel source,
			AbstractEntityModel target) {
		this.source = source;
		this.target = target;

		if (hasRecursiveRelationship()) {
			relationship = true;
		} else if (hasRelationshipAsSource()) {
			relationship = true;
		} else if (hasRelationshipAsTarget()) {
			relationship = true;
		} else {
			relationship = false;
		}

		if (isRecursiveTablePair()) {
			relationship = false;
		}
	}

	/**
	 * 再帰表とそのリレーションシップ元との組合せか？
	 * 
	 * @return 比較元・先が再帰表とそのリレーションシップ元である場合にtrueを返す
	 */
	private boolean isRecursiveTablePair() {
		if (target instanceof RecursiveTable) {
			return ((RecursiveTable) target).isSource(source);
		} else if (source instanceof RecursiveTable) {
			return ((RecursiveTable) source).isSource(target);
		}
		return false;
	}

	/**
	 * 再帰表を作成しているか？
	 * 
	 * @return 再帰表を作成している場合にtrueを返す
	 */
	private boolean hasRecursiveRelationship() {
		if (source == target) {
			for (AbstractConnectionModel connection : source
					.getModelSourceConnections()) {
				if (connection instanceof RecursiveRelationship) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 接続元としてリレーションシップを作成しているか？
	 * 
	 * @return リレーションシップを作成している場合にtrueを返す
	 */
	private boolean hasRelationshipAsSource() {
		boolean hasRelationship = false;
		for (AbstractConnectionModel connection : source
				.getModelSourceConnections()) {
			if (connection.getTarget().equals(target)) {
				hasRelationship = true;
				break;
			}
		}
		return hasRelationship;
	}

	/**
	 * 接続先としてリレーションシップを作成しているか？
	 * 
	 * @return リレーションシップを作成している場合にtrueを返す
	 */
	private boolean hasRelationshipAsTarget() {
		boolean hasRelationship = false;
		for (AbstractConnectionModel connection : source
				.getModelTargetConnections()) {
			if (connection.getSource().equals(target)) {
				hasRelationship = true;
				break;
			}
		}
		return hasRelationship;
	}

	/**
	 * @return the relationship
	 */
	public boolean isRelationship() {
		return relationship;
	}

	/**
	 * @return the source
	 */
	public AbstractEntityModel getSource() {
		return source;
	}

	/**
	 * @return the target
	 */
	public AbstractEntityModel getTarget() {
		return target;
	}

}
