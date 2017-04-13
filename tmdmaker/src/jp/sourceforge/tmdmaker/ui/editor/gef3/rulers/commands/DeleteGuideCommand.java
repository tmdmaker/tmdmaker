/*
 * Copyright 2009-2011 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package jp.sourceforge.tmdmaker.ui.editor.gef3.rulers.commands;

import org.eclipse.gef.commands.Command;

import jp.sourceforge.tmdmaker.ui.editor.gef3.rulers.models.GuideModel;
import jp.sourceforge.tmdmaker.ui.editor.gef3.rulers.models.RulerModel;

/**
 * ルーラーからガイドを削除するCommand
 * 
 * @author nakaG
 * 
 */
public class DeleteGuideCommand extends Command {
	/** ガイドを削除するルーラー */
	private RulerModel parent;
	/** 削除するガイド */
	private GuideModel guide;

	/**
	 * コンストラクタ
	 * 
	 * @param parent
	 *            ルーラーモデル
	 * @param guide
	 *            削除するガイド
	 */
	public DeleteGuideCommand(RulerModel parent, GuideModel guide) {
		this.parent = parent;
		this.guide = guide;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		parent.removeGuide(guide);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		parent.addGuide(guide);
	}
}