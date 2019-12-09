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
import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.multivalue.MultivalueOrBuilder;
import org.tmdmaker.ui.editor.draw2d.adjuster.ModelConstraintAdjuster;

/**
 * 多値のORを作成するCommand.
 * 
 * @author nakag
 *
 */
public class MultivalueOrCreateCommand extends Command {
	private AbstractEntityModel model;
	private MultivalueOrBuilder builder;

	public MultivalueOrCreateCommand(AbstractEntityModel model, String typeName) {
		this.model = model;
		this.builder = this.model.multivalueOr().builder().typeName(typeName);
	}

	@Override
	public void execute() {
		this.builder.build();
		new ModelConstraintAdjuster(this.builder.getMultivalueOrEntity(), this.model, 150, 0)
				.adjust();
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
