package jp.sourceforge.tmdmaker.model;

import java.util.Map;

/**
 * 
 * @author nakaG
 *
 */
public class RecursiveTable extends AbstractEntityModel {

	@Override
	public ReuseKey getMyReuseKey() {
		ReuseKey returnValue = new ReuseKey();
		for (Map.Entry<AbstractEntityModel, ReuseKey> rk : this.reuseKeys.entrySet()) {
			returnValue.addAll(rk.getValue().getIdentifires());
		}
		return returnValue;
	}

	@Override
	public void addReuseKey(AbstractEntityModel source) {
		ReuseKey added = source.getMyReuseKey();
		added.addAll(added.getIdentifires());
		this.reuseKeys.put(source, added);
		firePropertyChange(PROPERTY_REUSEKEY, null, added);
	}
	
}
