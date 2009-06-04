package jp.sourceforge.tmdmaker.model;

import java.util.Map;

/**
 * 
 * @author nakaG
 *
 */
@SuppressWarnings("serial")
public class RecursiveTable extends AbstractEntityModel {
	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#getMyReuseKey()
	 */
	@Override
	public ReUseKeys getMyReuseKey() {
		ReUseKeys returnValue = new ReUseKeys();
		for (Map.Entry<AbstractEntityModel, ReUseKeys> rk : this.reuseKeys.entrySet()) {
			returnValue.addAll(rk.getValue().getIdentifires());
		}
		return returnValue;
	}
	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#addReuseKey(jp.sourceforge.tmdmaker.model.AbstractEntityModel)
	 */
	@Override
	public void addReuseKey(AbstractEntityModel source) {
		ReUseKeys added = source.getMyReuseKey();
		added.addAll(added.getIdentifires());
		this.reuseKeys.put(source, added);
		firePropertyChange(PROPERTY_REUSEKEY, null, added);
	}
	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#canEntityTypeEditable()
	 */
	@Override
	public boolean canEntityTypeEditable() {
		return false;
	}
}
