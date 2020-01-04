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

import java.util.List;

import org.tmdmaker.core.model.ModelElement;
import org.tmdmaker.core.model.ModelFilter;

/**
 * 指定したクラスに該当するモデルを除外するフィルター.
 * 
 * @author nakag
 *
 */
public class ExcludeClassFilter implements ModelFilter {
	private List<Class<?>> excludeClasses;

	public ExcludeClassFilter(List<Class<?>> excludeClasses) {
		this.excludeClasses = excludeClasses;
	}

	@Override
	public <T extends ModelElement> T filter(T model) {
		if (excludeClasses.isEmpty()) {
			return model;
		}

		for (Class<?> clazz : excludeClasses) {
			if (clazz.isAssignableFrom(model.getClass())) {
				return null;
			}
		}
		return model;
	}

}
