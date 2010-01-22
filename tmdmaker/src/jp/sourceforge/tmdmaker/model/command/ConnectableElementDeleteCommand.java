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
package jp.sourceforge.tmdmaker.model.command;

import java.util.ArrayList;
import java.util.List;

import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.Diagram;

import org.eclipse.gef.commands.Command;

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
			// model.detachSource();
			// model.detachTarget();
		}
	}

	protected void attathConnections(List<AbstractConnectionModel> connections) {
		for (AbstractConnectionModel model : connections) {
			model.connect();
			// model.attachSource();
			// model.attachTarget();
		}
	}

}