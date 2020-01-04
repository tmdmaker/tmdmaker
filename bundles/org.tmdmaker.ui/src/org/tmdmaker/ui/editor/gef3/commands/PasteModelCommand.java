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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.Clipboard;
import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.Diagram;
import org.tmdmaker.core.model.Entity;
import org.tmdmaker.core.model.Laputa;
import org.tmdmaker.core.model.parts.ModelName;
import org.tmdmaker.ui.editor.draw2d.ConstraintConverter;

/**
 * コピーしたモデルをペーストするCommand
 * 
 * @author nakaG
 *
 */
public class PasteModelCommand extends Command {
	Diagram diagram;
	List<AbstractEntityModel> copyList = new ArrayList<AbstractEntityModel>();

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		Object obj = Clipboard.getDefault().getContents();
		if (!(obj instanceof List<?>)) {
			return false;
		}
		// CopyModelActionでAbstractEntityModelのListであることを保証
		@SuppressWarnings("unchecked")
		List<AbstractEntityModel> list = (List<AbstractEntityModel>) obj;
		copyList.clear();
		for (AbstractEntityModel m : list) {
			if (diagram == null) {
				diagram = m.getDiagram();
			}
			add(m);
		}
		return copyList != null && !copyList.isEmpty();
	}

	/**
	 * ペースト対象を変換してリストに追加
	 * 
	 * @param model
	 *            コピーされたモデル
	 */
	private void add(AbstractEntityModel model) {
		if (model instanceof Entity) {
			copyList.add(createCopy(model));
		} else {
			// TODO 関係の文法等で作成されたモデルについて関係毎コピーするか？
			copyList.add(copyToLaputa(model));
		}
	}

	/**
	 * モデルのコピーを作成する
	 * 
	 * @param model
	 *            コピー対象
	 * @return コピーしたモデル
	 */
	private AbstractEntityModel createCopy(AbstractEntityModel model) {
		AbstractEntityModel copy = model.getCopyWithAttributes();
		ConstraintConverter.copyConstraint(copy, model);
		return copy;
	}

	/**
	 * モデルをラピュタにコピーする。
	 * 
	 * @param model
	 *            コピー対象
	 * @return ラピュタ
	 */
	private AbstractEntityModel copyToLaputa(AbstractEntityModel model) {
		Laputa laputa = Laputa.of(new ModelName(model.getName()));
		model.copyWithAttributesTo(laputa);
		ConstraintConverter.copyConstraint(laputa, model);
		return laputa;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		for (AbstractEntityModel m : copyList) {
			diagram.addChild(m);
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		for (AbstractEntityModel m : copyList) {
			diagram.removeChild(m);
		}
	}
}
