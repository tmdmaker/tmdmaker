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
package org.tmdmaker.core.model.generate.attributelist;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.Detail;
import org.tmdmaker.core.model.Entity;
import org.tmdmaker.core.model.IAttribute;
import org.tmdmaker.core.model.Identifier;

public class AttributeListModelBuilder {

	public Map<String, EntityAttributePair> build(List<AbstractEntityModel> models) {

			Map<String, EntityAttributePair> attributes = new TreeMap<String, EntityAttributePair>(
					new Comparator<String>() {

						/**
						 * {@inheritDoc}
						 * 
						 * @see java.util.Comparator#compare(java.lang.Object,
						 *      java.lang.Object)
						 */
						@Override
						public int compare(String o1, String o2) {
							return o1.compareTo(o2);

						}

					});

			for (AbstractEntityModel m : models) {
				if (m instanceof Entity) {
					Entity e = (Entity) m;
					Identifier i = e.getIdentifier();
					EntityAttributePair pair = new EntityAttributePair(e, i);
					attributes.put(pair.createAttributeFileKey(), pair);
				}
				if (m instanceof Detail) {
					Detail d = (Detail) m;
					if (d.isDetailIdentifierEnabled()) {
						Identifier i = d.getDetailIdentifier();
						EntityAttributePair pair = new EntityAttributePair(d, i);
						attributes.put(pair.createAttributeFileKey(), pair);
					}
				}
				for (IAttribute a : m.getAttributes()) {
					EntityAttributePair pair = new EntityAttributePair(m, a);
					attributes.put(pair.createAttributeFileKey(), pair);
				}
			}
			return attributes;
	}
}
