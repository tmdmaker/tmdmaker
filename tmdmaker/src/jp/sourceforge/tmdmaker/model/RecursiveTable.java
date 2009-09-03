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
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#createReusedIdentifier()
	 */
	@Override
	public ReusedIdentifier createReusedIdentifier() {
		ReusedIdentifier returnValue = new ReusedIdentifier();
		for (Map.Entry<AbstractEntityModel, ReusedIdentifier> rk : this.reusedIdentifieres.entrySet()) {
			returnValue.addAll(rk.getValue().getIdentifires());
		}
		return returnValue;
	}
	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#addReusedIdentifier(jp.sourceforge.tmdmaker.model.AbstractEntityModel)
	 */
	@Override
	public void addReusedIdentifier(AbstractEntityModel source) {
//		ReusedIdentifier added = source.createReusedIdentifier();
//		added.addAll(added.getIdentifires());
		ReusedIdentifier added = new ReusedIdentifier();
		added.addAll(source.createReusedIdentifier().getIdentifires());
		added.addAll(source.createReusedIdentifier().getIdentifires());
		this.reusedIdentifieres.put(source, added);
		firePropertyChange(PROPERTY_REUSED, null, added);
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
		return getModelTargetConnections().size() == 1 && getModelSourceConnections().size() == 0;
	}

}
