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
package jp.sourceforge.tmdmaker.ui.editor.gef3.commands;

import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.VirtualSupersetType;

/**
 * みなしスーパーセット種類モデルの削除Command
 * 
 * @author nakaG
 * 
 */
public class VirtualSupersetTypeDeleteCommand extends
		ConnectableElementDeleteCommand {
	private VirtualSupersetType model;

	/**
	 * コンストラクタ
	 * 
	 * @param diagram
	 *            ダイアグラム
	 * @param model
	 *            みなしスーパーセットとの接点
	 */
	public VirtualSupersetTypeDeleteCommand(Diagram diagram,
			VirtualSupersetType model) {
		this.diagram = diagram;
		this.model = model;
		sourceConnections.addAll(model.getModelSourceConnections());
		targetConnections.addAll(model.getModelTargetConnections());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		// Reusedの設定・解除があるため以下の順で接続を削除する

		// 先にみなしサブセットとみなしサブセット種類の接続を削除
		detachConnections(targetConnections);
		// 最後にみなしサブセット種類とみなしスーパーセットとの接続を削除
		detachConnections(sourceConnections);
		// みなしサブセット種類削除
		diagram.removeChild(model);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		// executeの逆順
		diagram.addChild(model);
		attathConnections(sourceConnections);
		attathConnections(targetConnections);
	}

}
