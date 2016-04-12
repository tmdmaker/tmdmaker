/*
 * Copyright 2009,2014 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
import jp.sourceforge.tmdmaker.model.Constraint;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.Laputa;
import jp.sourceforge.tmdmaker.model.rule.EntityRecognitionRule;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.Clipboard;

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
		copyConstraint(copy, model.getConstraint());
		return copy;
	}

	/**
	 * モデルの位置をコピーする。位置は少しずらす。
	 * 
	 * @param model
	 * @param base
	 */
	private void copyConstraint(AbstractEntityModel model, Constraint base) {
		Constraint c = base.getCopy();
		c.x = c.x + 10;
		c.y = c.y + 10;
		model.setConstraint(c);
	}

	/**
	 * モデルをラピュタにコピーする。
	 * 
	 * @param model
	 *            コピー対象
	 * @return ラピュタ
	 */
	private AbstractEntityModel copyToLaputa(AbstractEntityModel model) {
		Laputa laputa = EntityRecognitionRule.getInstance().createLaputa(model.getName());
		model.copyWithAttributesTo(laputa);
		copyConstraint(laputa, model.getConstraint());
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
