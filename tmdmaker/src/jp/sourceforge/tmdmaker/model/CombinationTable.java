package jp.sourceforge.tmdmaker.model;

import java.util.Map;

/**
 * 
 * @author nakaG
 *
 */
public class CombinationTable extends AbstractEntityModel {
	public static final String COMBINATION_TABLE_SUFFIX = ".対照表";
	@Override
	public ReuseKey getMyReuseKey() {
		ReuseKey returnValue = new ReuseKey();
		for (Map.Entry<AbstractEntityModel, ReuseKey> rk :this.reuseKeys.entrySet()) {
			returnValue.addAll(rk.getValue().getIdentifires());
		}
		return returnValue;
	}
	
}
