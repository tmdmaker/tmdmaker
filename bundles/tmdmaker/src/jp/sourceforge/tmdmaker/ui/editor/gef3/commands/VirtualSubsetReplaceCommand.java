/*
 * Copyright 2009-2017 TMD-Maker Project <http://tmdmaker.osdn.jp/>
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
package jp.sourceforge.tmdmaker.ui.editor.gef3.commands;

import java.util.List;

import org.eclipse.gef.commands.Command;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Entity2VirtualSupersetTypeRelationship;
import jp.sourceforge.tmdmaker.model.VirtualSuperset;
import jp.sourceforge.tmdmaker.model.VirtualSupersetType2VirtualSupersetRelationship;

/**
 * みなしスーパセットに接続しているサブセットを置き換えるCommand.
 * 
 * @author nakag
 *
 */
public class VirtualSubsetReplaceCommand extends Command {
	private VirtualSupersetType2VirtualSupersetRelationship relationship;
	private List<Entity2VirtualSupersetTypeRelationship> oldSubset2typeRelationshipList;
	private List<AbstractEntityModel> newSubsetList;

	public VirtualSubsetReplaceCommand(VirtualSuperset superset,
			List<AbstractEntityModel> newSubsetList) {
		this.relationship = superset.getCreationRelationship();
		this.newSubsetList = newSubsetList;
		this.oldSubset2typeRelationshipList = relationship.getSubset2typeRelationshipList();
	}

	@Override
	public void execute() {
		relationship.reconnect(newSubsetList);
	}

	@Override
	public void undo() {
		relationship.disconnect();
		relationship.setSubset2typeRelationshipList(oldSubset2typeRelationshipList);
		relationship.connect();
	}

}
