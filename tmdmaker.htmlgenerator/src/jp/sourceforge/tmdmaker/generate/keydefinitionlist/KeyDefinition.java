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

	public Map<Attribute, List<KeyDefinitionMapping>> createDate(AbstractEntityModel model) {
		List<Attribute> attributes = new ArrayList<Attribute>();
		if (model instanceof Entity) {
			attributes.add(((Entity)model).getIdentifier());
		}
		if (model instanceof Detail) {
			attributes.add(((Detail)model).getDetailIdentifier());
		}
		attributes.addAll(model.getAttributes());
		// 派生元に戻して実装するモデルのアトリビュートを取得
		for (AbstractEntityModel m : model.getImplementDerivationModels()) {
			attributes.addAll(m.getAttributes());
		}
		Map<Attribute, List<KeyDefinitionMapping>> data = new LinkedHashMap<Attribute, List<KeyDefinitionMapping>>();
		for (Attribute a : attributes) {
			List<KeyDefinitionMapping> list = new ArrayList<KeyDefinitionMapping>();
			for (KeyModel k : model.getKeyModels()) {
				list.add(new KeyDefinitionMapping(a, k));
			}
			data.put(a, list);
		}
		return data;
	}

}
