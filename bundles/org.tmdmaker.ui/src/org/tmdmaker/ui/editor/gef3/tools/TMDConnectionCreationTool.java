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
package org.tmdmaker.ui.editor.gef3.tools;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.tools.ConnectionCreationTool;
import org.eclipse.jface.dialogs.Dialog;
import org.tmdmaker.core.model.AbstractConnectionModel;
import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.Event2EventRelationship;
import org.tmdmaker.ui.dialogs.RelationshipEditDialog;
import org.tmdmaker.ui.editor.gef3.commands.ConnectionCreateCommand;

/**
 * リレーションシップ作成ツール
 * 
 * @author nakaG
 * 
 */
public class TMDConnectionCreationTool extends ConnectionCreationTool {
	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.tools.AbstractConnectionCreationTool#handleCreateConnection()
	 */
	@Override
	protected boolean handleCreateConnection() {

		eraseSourceFeedback();
		Command endCommand = getCommand();

		ConnectionCreateCommand command = (ConnectionCreateCommand) endCommand;
		if (command == null) {
			return false;
		}
		AbstractConnectionModel connection = command.getConnection();
		if (connection instanceof Event2EventRelationship) {
			Event2EventRelationship relationship = (Event2EventRelationship) connection;
			AbstractEntityModel source = relationship.getSource();
			AbstractEntityModel target = relationship.getTarget();

			RelationshipEditDialog dialog = new RelationshipEditDialog(
					getCurrentViewer().getControl().getShell(), source.getName(), target.getName());
			if (dialog.open() == Dialog.OK) {
				relationship.setSourceCardinality(dialog.getSourceCardinality());
				relationship.setSourceNoInstance(dialog.isSourceNoInstance());
				relationship.setTargetCardinality(dialog.getTargetCardinality());
				relationship.setTargetNoInstance(dialog.isTargetNoInstance());

			} else {
				return false;
			}
		}
		setCurrentCommand(endCommand);
		executeCurrentCommand();
		return true;

	}
}
