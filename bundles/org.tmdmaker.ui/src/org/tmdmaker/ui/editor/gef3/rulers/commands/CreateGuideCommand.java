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
package org.tmdmaker.ui.editor.gef3.rulers.commands;

import org.eclipse.gef.commands.Command;
import org.tmdmaker.ui.editor.gef3.rulers.model.GuideModel;
import org.tmdmaker.ui.editor.gef3.rulers.model.RulerModel;

/**
 * ルーラーにガイドを作成するCommand
 * 
 * @author nakaG
 * 
 */
public class CreateGuideCommand extends Command {
	/** 作成するガイド */
	private GuideModel guide;
	/** ガイドを作成するルーラー */
	private RulerModel parent;
	/** ガイドの位置 */
	private int position;

	/**
	 * コンストラクタ
	 * 
	 * @param parent
	 *            ルーラーモデル
	 * @param position
	 *            ガイドの位置
	 */
	public CreateGuideCommand(RulerModel parent, int position) {
		this.parent = parent;
		this.position = position;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		if (guide == null) {
			guide = new GuideModel();
		}
		guide.setPosition(position);
		parent.addGuide(guide);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		parent.removeGuide(guide);
	}
}
