/*
 * Copyright 2009-2019 TMD-Maker Project <https://tmdmaker.osdn.jp/>
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
package org.tmdmaker.ui.editor.gef3.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.commands.Command;
import org.tmdmaker.core.model.AbstractConnectionModel;
import org.tmdmaker.core.model.Diagram;

/**
 * コネクションと接続可能なモデルを削除するCommandの基底クラス
 * 
 * @author nakaG
 * 
 */
public abstract class ConnectableElementDeleteCommand extends Command {

	protected Diagram diagram;
	protected List<AbstractConnectionModel> sourceConnections = new ArrayList<AbstractConnectionModel>();
	protected List<AbstractConnectionModel> targetConnections = new ArrayList<AbstractConnectionModel>();

	public ConnectableElementDeleteCommand() {
		super();
	}

	public ConnectableElementDeleteCommand(String label) {
		super(label);
	}

	public void setDiagram(Object diagram) {
		this.diagram = (Diagram) diagram;
	}

	protected void detachConnections(List<AbstractConnectionModel> connections) {
		for (AbstractConnectionModel model : connections) {
			model.disconnect();
		}
	}

	protected void attathConnections(List<AbstractConnectionModel> connections) {
		for (AbstractConnectionModel model : connections) {
			model.connect();
		}
	}

}