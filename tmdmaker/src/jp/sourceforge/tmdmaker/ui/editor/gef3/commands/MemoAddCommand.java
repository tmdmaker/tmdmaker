/*
 * Copyright 2009-2015 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

import org.eclipse.gef.commands.Command;

import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.other.Memo;

/**
 * メモ追加コマンド
 * 
 * @author nakag
 *
 */
public class MemoAddCommand extends Command {
	/** 親 */
	private Diagram diagram;
	/** 作成対象 */
	private Memo model;
	private int x;
	private int y;

	/**
	 * コンストラクタ
	 * 
	 * @param diagram
	 *            親
	 * @param model
	 *            メモ
	 * @param x
	 *            X座標
	 * @param y
	 *            Y座標
	 */
	public MemoAddCommand(Diagram diagram, Memo model, int x, int y) {
		super();
		this.diagram = diagram;
		this.model = model;
		this.x = x;
		this.y = y;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		if (model != null) {
			diagram.addChild(model);
			model.move(x, y);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		diagram.removeChild(model);
	}

}
