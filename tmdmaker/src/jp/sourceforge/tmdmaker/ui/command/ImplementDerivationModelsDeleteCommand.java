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

import java.util.ArrayList;
import java.util.List;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;

import org.eclipse.gef.commands.Command;

/**
 * 派生元で実装するモデルの情報を削除するCommand
 * 
 * @author nakaG
 * 
 */
public class ImplementDerivationModelsDeleteCommand extends Command {
	protected List<AbstractEntityModel> oldImplementDerivationModels;
	protected List<AbstractEntityModel> newImplementDerivationModels;
	private AbstractEntityModel original;

	/**
	 * コンストラクタ
	 * 
	 * @param model
	 *            削除対象モデル
	 * @param original
	 *            派生元モデル
	 */
	public ImplementDerivationModelsDeleteCommand(AbstractEntityModel model,
			AbstractEntityModel original) {
		super();
		this.oldImplementDerivationModels = original
				.getImplementDerivationModels();
		this.newImplementDerivationModels = new ArrayList<AbstractEntityModel>(
				oldImplementDerivationModels);
		this.newImplementDerivationModels.remove(model);
		this.original = original;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		original.setImplementDerivationModels(newImplementDerivationModels);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		original.setImplementDerivationModels(oldImplementDerivationModels);
	}

}
