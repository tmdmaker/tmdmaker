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
package jp.sourceforge.tmdmaker.ui.command;

import org.eclipse.gef.commands.Command;

import jp.sourceforge.tmdmaker.model.other.Memo;

/**
 * メモ変更コマンド
 *
 * @author nakag
 *
 */
public class MemoChangeCommand extends Command {
	private Memo memo;
	private String oldValue;
	private String newValue;

	/**
	 * 
	 */
	public MemoChangeCommand(Memo memo, String newValue) {
		this.memo = memo;
		this.oldValue = memo.getMemo();
		this.newValue = newValue;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		memo.setMemo(newValue);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		memo.setMemo(oldValue);
	}

}