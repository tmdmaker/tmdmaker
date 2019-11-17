/*
 * Copyright 2009-2018 TMD-Maker Project <https://tmdmaker.osdn.jp/>
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
import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.VirtualEntityType;
import org.tmdmaker.core.model.parts.ModelName;
import org.tmdmaker.core.model.virtual.VirtualEntityBuilder;
import org.tmdmaker.ui.editor.draw2d.adjuster.ModelConstraintAdjuster;

/**
 * みなしエンティティを作成するCommand.
 * 
 * @author nakag
 *
 */
public class VirtualEntityCreateCommand extends Command {
	private AbstractEntityModel model;
	private VirtualEntityBuilder builder;

	public VirtualEntityCreateCommand(AbstractEntityModel model, ModelName virtualEntityName,
			VirtualEntityType type) {
		this.model = model;
		this.builder = this.model.virtualEntities().builder().virtualEntityName(virtualEntityName)
				.virtualEntityType(type);
	}

	@Override
	public void execute() {
		this.builder.build();
		new ModelConstraintAdjuster(this.builder.getVirtualEntity(), this.model, 150, 0).adjust();
	}

	@Override
	public void undo() {
		this.builder.rollback();
	}

	@Override
	public void redo() {
		this.builder.build();
	}

}
