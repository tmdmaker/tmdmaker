/**
 * 
 */
package jp.sourceforge.tmdmaker.generate.keydefinitionlist;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.Detail;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.KeyModel;

/**
 * @author nakaG
 *
 */
public class KeyDefinition {
	private AbstractEntityModel model;
	private Map<Attribute,List<Boolean>> attributeMap = new LinkedHashMap<Attribute, List<Boolean>>();
	public KeyDefinition(AbstractEntityModel model) {
		this.model = model;
		List<Attribute> attributes = new ArrayList<Attribute>();
		if (model instanceof Entity) {
			attributes.add(((Entity)model).getIdentifier());
		}
		if (model instanceof Detail) {
			attributes.add(((Detail)model).getDetailIdentifier());
		}
		attributes.addAll(model.getAttributes());
		
		for (Attribute a : attributes) {
			List<Boolean> list = new ArrayList<Boolean>();
			for (KeyModel k : model.getKeyModels()) {
				list.add(k.getAttributes().contains(a));
			}
		}
	}

}
