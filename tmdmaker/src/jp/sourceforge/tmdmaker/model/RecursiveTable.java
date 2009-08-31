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
	public ReusedIdentifier getMyReuseKey() {
		ReusedIdentifier returnValue = new ReusedIdentifier();
		for (Map.Entry<AbstractEntityModel, ReusedIdentifier> rk : this.reuseKey.entrySet()) {
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
		ReusedIdentifier added = source.getMyReuseKey();
		added.addAll(added.getIdentifires());
		this.reuseKey.put(source, added);
		firePropertyChange(PROPERTY_REUSEKEY, null, added);
	}
	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#isEntityTypeEditable()
	 */
	@Override
	public boolean isEntityTypeEditable() {
		return false;
	}
	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#isDeletable()
	 */
	@Override
	public boolean isDeletable() {
		// TODO Auto-generated method stub
		return false;
	}

}
