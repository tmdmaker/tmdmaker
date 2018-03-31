package jp.sourceforge.tmdmaker.model.generate.attributelist;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Detail;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.IAttribute;
import jp.sourceforge.tmdmaker.model.Identifier;

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
