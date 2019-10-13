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

package org.tmdmaker.ui.views.properties.gef3;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;
import org.tmdmaker.ui.views.properties.IPropertyAvailable;

/**
 * PropertySourceProvider for TMD-Maker
 * 
 * @author nakag
 *
 */
public class TMDEditorPropertySourceProvider implements IPropertySourceProvider {

	private final CommandStack commandStack;

	public TMDEditorPropertySourceProvider(CommandStack commandStack) {
		this.commandStack = commandStack;
	}

	@Override
	public IPropertySource getPropertySource(Object object) {
		if (object instanceof IPropertyAvailable) {
			return ((IPropertyAvailable) object).getPropertySource(this.commandStack);
		}
		return null;
	}
}
