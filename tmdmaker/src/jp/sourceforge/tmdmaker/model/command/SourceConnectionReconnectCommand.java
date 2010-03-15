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
package jp.sourceforge.tmdmaker.model.command;

import jp.sourceforge.tmdmaker.model.AbstractRelationship;

import org.eclipse.gef.commands.Command;

/**
 * リレーションシップ等のコネクションのソースを再接続するCommand
 * 
 * @author nakaG
 * 
 */
public class SourceConnectionReconnectCommand extends Command {
	/** 再接続対象 */
	private AbstractRelationship relationship;
	/** x位置 */
	private int xp;
	/** y位置 */
	private int yp;
	/** 変更前x */
	private int oldXp;
	/** 変更前y */
	private int oldYp;

	/**
	 * コンストラクタ
	 * 
	 * @param relationship
	 *            再接続対象
	 * @param xp
	 *            x位置
	 * @param yp
	 *            y位置
	 */
	public SourceConnectionReconnectCommand(AbstractRelationship relationship,
			int xp, int yp) {
		this.relationship = relationship;
		this.xp = xp;
		this.yp = yp;
		this.oldXp = relationship.getSourceXp();
		this.oldYp = relationship.getSourceYp();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		relationship.setSourceLocationp(xp, yp);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		relationship.setSourceLocationp(oldXp, oldYp);
	}
}
