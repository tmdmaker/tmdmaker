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
package jp.sourceforge.tmdmaker.model;

import java.util.ArrayList;
import java.util.List;

import jp.sourceforge.tmdmaker.model.parts.ModelName;

public class DiagramQuery {
	private final Diagram diagram;
	private boolean implementModelOnly = false;
	private ModelName name;
	private List<ModelElement> excludes = new ArrayList<ModelElement>();
	private List<Class<?>> excludeClasses = new ArrayList<Class<?>>();

	DiagramQuery(Diagram diagram) {
		this.diagram = diagram;
	}

	public DiagramQuery exclude(ModelElement m) {
		excludes.add(m);
		return this;
	}

	public DiagramQuery exclude(List<? extends ModelElement> modelList) {
		excludes.addAll(modelList);
		return this;
	}

	public DiagramQuery excludeClass(Class<?> clazz) {
		excludeClasses.add(clazz);
		return this;
	}

	public DiagramQuery implementModel() {
		implementModelOnly = true;
		return this;
	}

	public DiagramQuery name(ModelName name) {
		this.name = name;
		return this;
	}

	@SuppressWarnings("unchecked")
	private <T> List<T> listModel(Class<T> clazz) {
		List<T> results = new ArrayList<T>();
		for (ModelElement m : diagram.getChildren()) {
			ModelElement filteredModel = filterImplementModel(m);
			if (filteredModel == null) {
				continue;
			}
			if (isExclude(m)) {
				continue;
			}
			if (isExcludeClass(m)) {
				continue;
			}
			if (!isNameMatch(m)) {
				continue;
			}
			if (clazz.isAssignableFrom(m.getClass())) {
				results.add((T) m);
			}
		}
		return results;
	}

	private boolean isExcludeClass(ModelElement m) {
		if (excludeClasses.isEmpty()) {
			return false;
		}
		for (Class<?> clazz : excludeClasses) {
			if (clazz.isAssignableFrom(m.getClass())) {
				return true;
			}
		}
		return false;
	}

	private boolean isNameMatch(ModelElement m) {
		if (this.name == null) {
			return true;
		}
		return this.name.equals(new ModelName((m.getName())));
	}

	private ModelElement filterImplementModel(ModelElement m) {
		if (!implementModelOnly) {
			return m;
		}
		if (!(m instanceof AbstractEntityModel)) {
			return null;
		}
		AbstractEntityModel entityModel = (AbstractEntityModel) m;
		if (entityModel.isNotImplement()) {
			return null;
		}
		return entityModel;
	}

	public List<AbstractEntityModel> listEntityModel() {
		return listModel(AbstractEntityModel.class);
	}

	public <T extends ModelElement> List<T> listEntityModel(Class<T> clazz) {
		return listModel(clazz);
	}

	private boolean isExclude(ModelElement m) {
		if (excludes.isEmpty()) {
			return false;
		}
		return excludes.contains(m);
	}
}
