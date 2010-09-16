/**
 * 
 */
package jp.sourceforge.tmdmaker.generate.keydefinitionlist;

import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.KeyModel;

/**
 * キー定義のデータ
 * 
 * @author nakaG
 * 
 */
public class KeyDefinitionMapping {
	private Attribute attribute;
	private KeyModel keyModel;
	private String keyOrder;
	public KeyDefinitionMapping(Attribute attribute, KeyModel keyModel) {
		this.attribute = attribute;
		this.keyModel = keyModel;
		
		if (this.keyModel.getAttributes().contains(this.attribute)) {
			keyOrder = String.valueOf(this.keyModel.getAttributes().indexOf(this.attribute) + 1);
		} else {
			keyOrder = "-";
		}
	}
	/**
	 * @return the keyOrder
	 */
	public String getKeyOrder() {
		return keyOrder;
	}
	
}
