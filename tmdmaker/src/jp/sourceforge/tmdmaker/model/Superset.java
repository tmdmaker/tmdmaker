package jp.sourceforge.tmdmaker.model;

/**
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class Superset extends AbstractEntityModel {
	/** DTL */
	private Detail detail;

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#isEntityTypeEditable()
	 */
	@Override
	public boolean isEntityTypeEditable() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#createReusedIdentifier()
	 */
	@Override
	public ReusedIdentifier createReusedIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#isDeletable()
	 */
	@Override
	public boolean isDeletable() {
		return detail.isDeletable();
	}

	/**
	 * @return the detail
	 */
	public Detail getDetail() {
		return detail;
	}

	/**
	 * @param detail the detail to set
	 */
	public void setDetail(Detail detail) {
		this.detail = detail;
	}

}
