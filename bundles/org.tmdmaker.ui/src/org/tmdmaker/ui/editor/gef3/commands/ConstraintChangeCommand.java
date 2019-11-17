/*
 * Copyright 2009-2017 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.tmdmaker.core.model.ModelElement;
import org.tmdmaker.ui.editor.draw2d.ConstraintConverter;

/**
 * モデルの制約変更コマンド
 * 
 * @author nakaG
 * 
 */
public class ConstraintChangeCommand extends Command {
	/** 移動対象 */
	private ModelElement model;
	/** 移動後の座標 */
	private Rectangle rectangle;
	/** 移動前の座標 */
	private Rectangle oldRectangle;

	/**
	 * コンストラクタ（新規作成時やマウス操作時）
	 * 
	 * @param model
	 *            移動対象
	 * @param rectangle
	 *            移動後の座標
	 */
	public ConstraintChangeCommand(ModelElement model, Rectangle rectangle) {
		this.model = model;
		this.rectangle = rectangle;
		this.oldRectangle = ConstraintConverter.getRectangle(model);
	}

	/**
	 * コンストラクタ（テンキー操作時）
	 * 
	 * @param model
	 *            移動対象
	 * @param newX
	 *            移動後のX座標
	 * @param newY
	 *            移動後のY座標
	 */
	public ConstraintChangeCommand(ModelElement model, int newX, int newY) {
		this.model = model;
		this.oldRectangle = ConstraintConverter.getRectangle(model);
		this.rectangle = ConstraintConverter.getTranslatedRectangle(model, newX, newY);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		ConstraintConverter.setConstraint(model, rectangle);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		ConstraintConverter.setConstraint(model, oldRectangle);
	}
}
