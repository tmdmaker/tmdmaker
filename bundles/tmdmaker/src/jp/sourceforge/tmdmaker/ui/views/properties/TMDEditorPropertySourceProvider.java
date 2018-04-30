/*
 * Copyright 2009-2013 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;

public class TMDEditorPropertySourceProvider implements IPropertySourceProvider {
	
	TMDEditor editor;
	
	public TMDEditorPropertySourceProvider(TMDEditor editor){	
		this.editor = editor;
	}

	@Override
	public IPropertySource getPropertySource(Object object) {
		if (object instanceof IPropertyAvailable) {
			return ((IPropertyAvailable) object).getPropertySource(this.editor);
		}
		return null;
	}
}
