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
package org.tmdmaker.core.model.filter;

import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.ModelElement;
import org.tmdmaker.core.model.ModelFilter;

/**
 * 実装するモデルを抽出するフィルター.
 * 
 * @author nakag
 *
 */
public class ImplementModelFilter implements ModelFilter {
	private boolean implementModelOnly;

	public ImplementModelFilter(boolean implementModelOnly) {
		this.implementModelOnly = implementModelOnly;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ModelElement> T filter(T model) {
		if (!implementModelOnly) {
			return model;
		}
		if (!(model instanceof AbstractEntityModel)) {
			return null;
		}
		AbstractEntityModel entityModel = (AbstractEntityModel) model;
		if (entityModel.isNotImplement()) {
			return null;
		}
		return (T) entityModel;
	}

}
