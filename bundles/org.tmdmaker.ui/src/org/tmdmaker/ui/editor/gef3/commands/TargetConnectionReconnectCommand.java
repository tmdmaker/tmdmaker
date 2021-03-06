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

import org.eclipse.gef.commands.Command;
import org.tmdmaker.core.model.AbstractConnectionModel;
import org.tmdmaker.core.model.constraint.AnchorConstraint;
import org.tmdmaker.ui.editor.draw2d.AnchorConstraintManager;;

/**
 * リレーションシップ等のコネクションのターゲットを再接続するCommand
 * 
 * @author nakaG
 * 
 */
public class TargetConnectionReconnectCommand extends Command {
	/** 再接続対象 */
	private AbstractConnectionModel relationship;
	/** 再接続位置 */
	private AnchorConstraint newAnchorConstraint;
	/** 変更前位置 */
	private AnchorConstraint oldAnchorConstraint;

	/**
	 * コンストラクタ
	 * 
	 * @param relationship
	 *            再接続対象
	 * @param newAnchorConstraint
	 *            再接続位置
	 */
	public TargetConnectionReconnectCommand(AbstractConnectionModel relationship,
			AnchorConstraint newAnchorConstraint) {
		this.relationship = relationship;
		this.newAnchorConstraint = newAnchorConstraint;
		this.oldAnchorConstraint = AnchorConstraintManager
				.getTargetAnchorConstraint(relationship);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		AnchorConstraintManager.setTargetAnchorConstraint(relationship, newAnchorConstraint);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		AnchorConstraintManager.setTargetAnchorConstraint(relationship, oldAnchorConstraint);
	}

}
