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
package org.tmdmaker.ui.editor.gef3.commands;

import org.eclipse.gef.commands.Command;
import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.Diagram;

/**
 * エンティティ系モデル追加Command
 * 
 * @author nakaG
 * 
 */
public class EntityModelAddCommand extends Command {
	/** 親 */
	private Diagram diagram;
	/** 作成対象 */
	private AbstractEntityModel model;
	private int x;
	private int y;

	/**
	 * コンストラクタ
	 *
	 * @param diagram
	 *            親
	 * @param x
	 *            X座標
	 * @param y
	 *            Y座標
	 */
	public EntityModelAddCommand(Diagram diagram, AbstractEntityModel model, int x, int y) {
		this.diagram = diagram;
		this.model = model;
		this.x = x;
		this.y = y;
	}

	/**
	 * コンストラクタ
	 *
	 * @param diagram
	 *            親
	 * @param x
	 *            X座標
	 * @param y
	 *            Y座標
	 */
	public EntityModelAddCommand(Diagram diagram, int x, int y) {
		this.diagram = diagram;
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

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(AbstractEntityModel model) {
		this.model = model;
	}

	public boolean isModelAdded() {
		return model != null;
	}
}
