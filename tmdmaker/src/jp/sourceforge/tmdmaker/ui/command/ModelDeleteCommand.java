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
package jp.sourceforge.tmdmaker.ui.command;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Diagram;

import org.eclipse.gef.commands.Command;

/**
 * モデル削除Command
 * 
 * @author nakaG
 * 
 */
public class ModelDeleteCommand extends Command {
	private Diagram diagram;
	private AbstractEntityModel model;

	/**
	 * コンストラクタ
	 * 
	 * @param diagram
	 *            ダイアグラム
	 * @param model
	 *            モデル
	 */
	public ModelDeleteCommand(Diagram diagram, AbstractEntityModel model) {
		this.diagram = diagram;
		this.model = model;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
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
		diagram.addChild(model);
	}

}
