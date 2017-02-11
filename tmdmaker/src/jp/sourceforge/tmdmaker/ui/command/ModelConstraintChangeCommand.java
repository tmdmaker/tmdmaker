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

import jp.sourceforge.tmdmaker.model.Constraint;
import jp.sourceforge.tmdmaker.model.ModelElement;

import org.eclipse.gef.commands.Command;

/**
 * モデルの制約変更コマンド
 * 
 * @author nakaG
 * 
 */
public class ModelConstraintChangeCommand extends Command {
	/** 移動対象 */
	private ModelElement model;
	/** 移動後の座標 */
	private Constraint constraint;
	/** 移動前の座標 */
	private Constraint oldConstraint;

	/**
	 * コンストラクタ（新規作成時やマウス操作時）
	 * 
	 * @param model
	 *            移動対象
	 * @param constraint
	 *            移動後の座標
	 */
	public ModelConstraintChangeCommand(ModelElement model, Constraint constraint) {
		this.model = model;
		this.constraint = constraint;
		this.oldConstraint = model.getConstraint();
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
	public ModelConstraintChangeCommand(ModelElement model, int newX, int newY) {
		this.model = model;
		this.oldConstraint = model.getConstraint();
		this.constraint = model.getConstraint().getTranslated(newX, newY);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		model.setConstraint(constraint);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		model.setConstraint(oldConstraint);
	}
}
