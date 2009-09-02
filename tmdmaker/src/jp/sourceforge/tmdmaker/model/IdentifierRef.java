package jp.sourceforge.tmdmaker.model;

/**
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class IdentifierRef extends Identifier {
	/** 元の個体指示子 */
	private Identifier original;

	/**
	 * コンストラクタ
	 * 
	 * @param identifier
	 *            移送元の個体指示子
	 */
	public IdentifierRef(Identifier identifier) {
		this.original = identifier;
	}

	/**
	 * @return the original
	 */
	public Identifier getOriginal() {
		return original;
	}

	/**
	 * @param original
	 *            the original to set
	 */
	public void setOriginal(Identifier original) {
		this.original = original;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.ModelElement#getName()
	 */
	@Override
	public String getName() {
		String returnName = super.getName();
		if (returnName == null) {
			returnName = original.getName();
		}
		return returnName;
	}
	public boolean isSame(IdentifierRef identifierRef) {
		return this.original.equals(identifierRef.getOriginal());
	}
}
