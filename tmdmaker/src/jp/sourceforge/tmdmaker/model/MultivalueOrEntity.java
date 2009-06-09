package jp.sourceforge.tmdmaker.model;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author nakaG
 *
 */
@SuppressWarnings("serial")
public class MultivalueOrEntity extends AbstractEntityModel {
	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#canEntityTypeEditable()
	 */
	@Override
	public boolean canEntityTypeEditable() {
		return false;
	}
	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#getMyReuseKey()
	 */
	@Override
	public ReUseKeys getMyReuseKey() {
		ReUseKeys returnValue = new ReUseKeys();
		for (Map.Entry<AbstractEntityModel, ReUseKeys> rk : this.reuseKeys
				.entrySet()) {
			returnValue.addAll(rk.getValue().getIdentifires());
		}
		return returnValue;
	}

}
