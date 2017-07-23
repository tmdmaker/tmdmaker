/*
 * Copyright 2009-2017 TMD-Maker Project <http://tmdmaker.osdn.jp/>
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
package jp.sourceforge.tmdmaker.ui.editor.draw2d.figure.relationship;

import org.eclipse.draw2d.ConnectionEndpointLocator;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.ManhattanConnectionRouter;
import org.eclipse.draw2d.PolylineConnection;

import jp.sourceforge.tmdmaker.model.Entity2SubsetTypeRelationship;

/**
 * エンティティ系モデルとサブセット種類とのリレーションシップのFigure
 * 
 * @author nakaG
 * 
 */
public class Entity2SubsetTypeRelationshipFigure extends PolylineConnection {
	/** 区分コードのアトリビュート名のDecoration用Figure */
	private Figure partitionAttributeNameFigure;

	/**
	 * コンストラクタ
	 */
	public Entity2SubsetTypeRelationshipFigure() {
		setConnectionRouter(new ManhattanConnectionRouter());
	}

	/**
	 * figureの状態を更新する.
	 * 
	 * @param relationship
	 */
	public void update(Entity2SubsetTypeRelationship relationship) {
		this.createPartitionAttributeNameDecoration(relationship.getPartitionAttributeName());
	}

	/**
	 * 区分コードのアトリビュート名を表示するDecorationを作成する
	 * 
	 * @param partitionAttributeName
	 *            区分コードのアトリビュート名
	 */
	private void createPartitionAttributeNameDecoration(String partitionAttributeName) {
		if (partitionAttributeName == null) {
			return;
		}
		if (partitionAttributeNameFigure != null) {
			remove(partitionAttributeNameFigure);
		}
		partitionAttributeNameFigure = new Label(partitionAttributeName);
		ConnectionEndpointLocator locator = new ConnectionEndpointLocator(this, true);
		locator.setUDistance(-10);
		locator.setVDistance(20);
		add(partitionAttributeNameFigure, locator);
	}
}
