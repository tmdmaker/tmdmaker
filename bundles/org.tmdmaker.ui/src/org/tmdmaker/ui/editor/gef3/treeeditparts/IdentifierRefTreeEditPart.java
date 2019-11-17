/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org/>
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
package org.tmdmaker.ui.editor.gef3.treeeditparts;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.views.properties.IPropertySource;
import org.tmdmaker.core.model.IdentifierRef;
import org.tmdmaker.core.model.ModelElement;
import org.tmdmaker.ui.views.properties.gef3.IdentifierRefPropertySource;

public class IdentifierRefTreeEditPart extends IdentifierTreeEditPart {

	/**
	 * コンストラクタ
	 * 
	 * @param identifier
	 */
	public IdentifierRefTreeEditPart(IdentifierRef identifier) {
		super(identifier, null);
	}

	@Override
	public IdentifierRef getModel() {
		return (IdentifierRef) super.getModel();
	}

	@Override
	protected String getText() {
		ModelElement model = getModel();
		if (model.getName() == null) {
			return ""; //$NON-NLS-1$
		} else {
			return model.getName() + "(R)"; //$NON-NLS-1$
		}
	}

	@Override
	public IPropertySource getPropertySource(CommandStack commandStack) {
		return new IdentifierRefPropertySource(commandStack, this.getModel());
	}
}
