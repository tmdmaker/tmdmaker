/*
 * Copyright 2009-2019 TMD-Maker Project <https://tmdmaker.osdn.jp/>
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
package jp.sourceforge.tmdmaker.ui.views.properties;

import jp.sourceforge.tmdmaker.TMDEditor;

import org.eclipse.gef.commands.Command;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

public abstract class AbstractPropertySource implements IPropertySource {

	private TMDEditor editor;

	public AbstractPropertySource(TMDEditor editor) {
		this.editor = editor;
	}

	@Override
	public void resetPropertyValue(Object paramObject) {
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
	}

	protected abstract Command createSetPropertyCommand(Object id, Object value);

	@Override
	public boolean isPropertySet(Object id) {
		for (IPropertyDescriptor descriptor : getPropertyDescriptors()) {
			if (descriptor.equals(id)) {
				return true;
			}
		}
		return false;
	}

	protected String canonicalize(String property) {
		return property != null ? property : "";
	}

	protected TMDEditor getEditor() {
		return editor;
	}
}
