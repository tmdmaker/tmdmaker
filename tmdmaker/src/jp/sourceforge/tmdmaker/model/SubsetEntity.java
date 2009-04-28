package jp.sourceforge.tmdmaker.model;

public class SubsetEntity extends AbstractEntityModel {
	public enum SubsetType {SAME, DIFFERENT};
	public SubsetType subsettype = SubsetType.SAME;

	private AbstractEntityModel original;

	@Override
	public ReuseKey getMyReuseKey() {
		return original.getMyReuseKey();
	}

	/**
	 * @param original the original to set
	 */
	public void setOriginal(AbstractEntityModel original) {
		this.original = original;
	}

	/**
	 * @return the original
	 */
	public AbstractEntityModel getOriginal() {
		return original;
	}
}
