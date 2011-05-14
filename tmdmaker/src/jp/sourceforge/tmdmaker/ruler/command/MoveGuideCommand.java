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
package jp.sourceforge.tmdmaker.ruler.command;

import jp.sourceforge.tmdmaker.ruler.model.GuideModel;

import org.eclipse.gef.commands.Command;

/**
 * ルーラーのガイドを移動するCommand
 * 
 * @author nakaG
 * 
 */
public class MoveGuideCommand extends Command {
	/** 移動するガイド */
	private GuideModel guide;
	/** 移動先の位置 */
	private int positionDelta;

	/**
	 * コンストラクタ
	 * 
	 * @param guide
	 *            移動するガイド
	 * @param positionDelta
	 *            移動先の位置
	 */
	public MoveGuideCommand(GuideModel guide, int positionDelta) {
		this.guide = guide;
		this.positionDelta = positionDelta;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		guide.setPosition(guide.getPosition() + positionDelta);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		guide.setPosition(guide.getPosition() - positionDelta);
	}
}
