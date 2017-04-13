/*
 * Copyright 2009-2014 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

import org.eclipse.ui.views.properties.IPropertySource;
import jp.sourceforge.tmdmaker.TMDEditor;

/**
 * PropertySource を返す editpart は、このinterface を実装すること。
 * 
 * @author tohosaku
 * 
 */
public interface IPropertyAvailable {
	IPropertySource getPropertySource(TMDEditor editor);
}