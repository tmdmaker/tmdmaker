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

import jp.sourceforge.tmdmaker.model.filter.ExcludeClassFilter;
import jp.sourceforge.tmdmaker.model.filter.ExcludeModelFilter;
import jp.sourceforge.tmdmaker.model.filter.ImplementModelFilter;
import jp.sourceforge.tmdmaker.model.filter.NameMatchFilter;
import jp.sourceforge.tmdmaker.model.parts.ModelName;

/**
 * Diagramを検索するクエリクラス.
 * 
 * @author nakag
 *
 */
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
			ModelElement filteredModel = applyFilteres(m);
			if (filteredModel == null) {
				continue;
			}

			if (clazz.isAssignableFrom(m.getClass())) {
				results.add((T) m);
			}
		}
		return results;
	}

	private ModelElement applyFilteres(ModelElement model) {
		for (ModelFilter filter : createFilteres()) {
			ModelElement filteredModel = filter.filter(model);
			if (filteredModel == null) {
				return null;
			}
		}
		return model;
	}

	private ModelFilter[] createFilteres() {
		List<ModelFilter> filteres = new ArrayList<ModelFilter>();
		filteres.add(new ImplementModelFilter(implementModelOnly));
		filteres.add(new ExcludeModelFilter(excludes));
		filteres.add(new ExcludeClassFilter(excludeClasses));
		filteres.add(new NameMatchFilter(name));
		return filteres.toArray(new ModelFilter[filteres.size()]);
	}

	public List<AbstractEntityModel> listEntityModel() {
		return listModel(AbstractEntityModel.class);
	}

	public <T extends ModelElement> List<T> listEntityModel(Class<T> clazz) {
		return listModel(clazz);
	}
}
