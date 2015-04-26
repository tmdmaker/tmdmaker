/*
 * Copyright 2009-2014 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.sourceforge.tmdmaker.model;

/**
 * 多値のANDのディテール
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class Detail extends AbstractEntityModel {
	/** HDRモデルのRe-used */
	private ReusedIdentifier originalReusedIdentifier;

	/** DTLの個体指定子 */
	private Identifier detailIdentifier = new Identifier();

	// public Detail() {
	// detailIdentifier.setParent(this);
	// }
	/**
	 * DTLの個体指定子名を設定する
	 * 
	 * @param name
	 */
	public void setDetailIdentifierName(String name) {
		String oldValue = detailIdentifier.getName();
		detailIdentifier.setName(name);
		if (oldValue == null || !oldValue.equals(name)) {
			firePropertyChange(PROPERTY_IDENTIFIER, oldValue, name);
			fireIdentifierChanged(null);
		}
	}

	/**
	 * @return the detailIdentifier
	 */
	public Identifier getDetailIdentifier() {
		return detailIdentifier;
	}

	/**
	 * @param detailIdentifier
	 *            the detailIdentifier to set
	 */
	public void setDetailIdentifier(Identifier detailIdentifier) {
		Identifier oldValue = this.detailIdentifier;
		// oldValue.setParent(null);
		this.detailIdentifier = detailIdentifier;
		// this.detailIdentifier.setParent(this);
		firePropertyChange(PROPERTY_IDENTIFIER, oldValue, detailIdentifier);
	}

	/**
	 * @return the originalReusedIdentifier
	 */
	public ReusedIdentifier getOriginalReusedIdentifier() {
		return originalReusedIdentifier;
	}

	/**
	 * @param originalReusedIdentifier
	 *            the originalReusedIdentifier to set
	 */
	public void setOriginalReusedIdentifier(ReusedIdentifier originalReusedIdentifier) {
		this.originalReusedIdentifier = originalReusedIdentifier;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#isEntityTypeEditable()
	 */
	@Override
	public boolean isEntityTypeEditable() {
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
		ReusedIdentifier returnValue = new ReusedIdentifier(keyModels.getSarogateKey());
		returnValue.addAll(this.originalReusedIdentifier.getIdentifires());
		returnValue.addIdentifier(detailIdentifier);
		return returnValue;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#isDeletable()
	 */
	@Override
	public boolean isDeletable() {
		return getModelSourceConnections().size() == 1 && getModelTargetConnections().size() == 1;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#copyTo(jp.sourceforge.tmdmaker.model.AbstractEntityModel)
	 */
	@Override
	public void copyTo(AbstractEntityModel to) {
		if (to instanceof Detail) {
			Detail toDetail = (Detail) to;
			toDetail.setDetailIdentifierName(getDetailIdentifier().getName());
			toDetail.getDetailIdentifier().copyFrom(getDetailIdentifier());
		}
		super.copyTo(to);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#getCopy()
	 */
	@Override
	public Detail getCopy() {
		Detail copy = new Detail();
		copyTo(copy);
		return copy;
	}

	@Override
	public int calcurateMaxIdentifierRefSize() {
		int di = detailIdentifier.getName().length();
		int oi = calcurateMaxOriginalIdentifierRefSize();
		int imax = Math.max(di, oi);
		return Math.max(imax, super.calcurateMaxIdentifierRefSize());
	}

	private int calcurateMaxOriginalIdentifierRefSize() {
		int i = 0;
		for (IdentifierRef ir : originalReusedIdentifier.getUniqueIdentifieres()) {
			i = Math.max(ir.getName().length(), i);
		}
		return i;
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}
