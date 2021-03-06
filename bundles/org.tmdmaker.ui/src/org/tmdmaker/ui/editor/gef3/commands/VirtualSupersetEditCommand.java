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

import java.util.List;

import org.eclipse.gef.commands.CompoundCommand;
import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.VirtualSuperset;


/**
 * みなしスーパーセット編集Command.
 * 
 * @author nakag
 *
 */
public class VirtualSupersetEditCommand extends CompoundCommand {

	public VirtualSupersetEditCommand(VirtualSuperset original, VirtualSuperset edited,
			List<AbstractEntityModel> selection, boolean applyAttribute) {
		if (selection.isEmpty()) {
			add(new ConnectionDeleteCommand(original.getCreationRelationship()));
		} else {
			// みなしスーパーセット編集
			add(new ModelEditCommand(original, edited));

			// 接点との接続
			add(new VirtualSupersetCreateCommand(original, selection));

			// 接点編集
			add(new VirtualSupersetTypeChangeCommand(original, applyAttribute));
		}
	}
}
