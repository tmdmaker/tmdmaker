/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org>
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
package org.tmdmaker.core.model;

import java.util.Map.Entry;

import org.tmdmaker.core.model.rule.ImplementRule;

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

	/** DTLの個体指定子を使用するか？ */
	private boolean isDetailIdentifierEnabled;

	/**
	 * コンストラクタは非公開.
	 */
	protected Detail() {
		isDetailIdentifierEnabled = true;
	}

	/**
	 * 多値のANDのDetailを作成する。
	 * 
	 * @param header
	 *            派生元のモデル
	 * @return 多値のANDのDetail
	 */
	protected static Detail build(AbstractEntityModel header) {
		Detail detail = new Detail();
		detail.setName(header.getName() + "DTL");
		detail.setEntityType(header.getEntityType());
		detail.setOriginalReusedIdentifier(header.createReusedIdentifier());
		detail.getDetailIdentifier().copyFrom(createDetailIdentifier(header.getName()));
		ImplementRule.setModelDefaultValue(detail);
		return detail;
	}

	/**
	 * Detailの個体指定子を作成する
	 * 
	 * @param headerName
	 *            派生元のモデル名
	 * @return Detailの個体指定子
	 */
	private static Identifier createDetailIdentifier(String headerName) {
		Identifier id = new Identifier(headerName + "明細番号");
		ImplementRule.setIdentifierDefaultValue(id);

		return id;
	}

	/**
	 * DTLの個体指定子名(明細番号)を設定する
	 * 
	 * @param name
	 */
	public void setDetailIdentifierName(String name) {
		this.detailIdentifier.setName(name);
	}

	/**
	 * DTLの個体指定子名(明細番号)を返す
	 * 
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
		oldValue.setParent(null);
		this.detailIdentifier = detailIdentifier;
		this.detailIdentifier.setParent(this);
		firePropertyChange(PROPERTY_IDENTIFIER, oldValue, detailIdentifier);
	}

	/**
	 * DTLの個体指定子の使用有無を設定する。
	 * 
	 * HDR以外のRe-UsedのIdentifierがある場合以外はfalseに設定できない。
	 * 変更になった場合は、他のEntityに変化を波及させる必要がある。
	 * 
	 * @param enabled
	 */
	public void setDetailIdentifierEnabled(boolean enabled) {
		if (isDetailIdentifierEnabled() == enabled)
			return;
		// canDisableDetailIdentifierEnabled() で不用意な書き換えを制御したいがダイアログ書き換えのタイミングの
		// 問題で難しい。
		isDetailIdentifierEnabled = enabled;
		fireIdentifierChanged();
	}

	@Override
	protected void fireIdentifierChanged() {
		firePropertyChange(PROPERTY_IDENTIFIER, null, detailIdentifier);
		super.fireIdentifierChanged();
	}

	/**
	 * DTLの個体指定子(明細番号)が使用されているかを返す。
	 * 
	 * @return
	 */
	public boolean isDetailIdentifierEnabled() {
		return isDetailIdentifierEnabled;
	}

	/**
	 * DTLの個体指定子(明細番号)を使用できないようにできるかどうかを返す。
	 * HDR以外のRe-UsedのIdentifierがある場合はtrueを返す。
	 * 
	 * @return
	 */
	public boolean canDisableDetailIdentifierEnabled() {
		return getReusedIdentifiers().size() > 1;
	}

	/**
	 * @return the originalReusedIdentifier
	 */
	public ReusedIdentifier getOriginalReusedIdentifier() {
		return originalReusedIdentifier;
	}

	/**
	 * 
	 * @return the IdentifierRef
	 */
	public IdentifierRef getOriginalUniqueIdentifierRef() {
		return getOriginalReusedIdentifier().getUniqueIdentifiers().get(0);
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
	 * @see org.tmdmaker.core.model.AbstractEntityModel#isEntityTypeEditable()
	 */
	@Override
	public boolean isEntityTypeEditable() {
		return false;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.AbstractEntityModel#createReusedIdentifier()
	 */
	@Override
	public ReusedIdentifier createReusedIdentifier() {
		ReusedIdentifier returnValue = new ReusedIdentifier(keyModels.getSurrogateKey());
		if (isDetailIdentifierEnabled) {
			if (originalReusedIdentifier != null) {
				returnValue.addAll(this.originalReusedIdentifier.getIdentifiers());
			}
			returnValue.addIdentifier(detailIdentifier);
		} else {
			for (Entry<AbstractEntityModel, ReusedIdentifier> ref : this.reusedIdentifiers
					.entrySet()) {
				returnValue.addAll(ref.getValue().getIdentifiers());
			}
		}
		return returnValue;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.AbstractEntityModel#isDeletable()
	 */
	@Override
	public boolean isDeletable() {
		return getModelSourceConnections().size() == 1 && getModelTargetConnections().size() == 1;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.AbstractEntityModel#copyTo(org.tmdmaker.core.model.AbstractEntityModel)
	 */
	@Override
	public void copyTo(AbstractEntityModel to) {
		Detail toDetail = (Detail) to;
		toDetail.setDetailIdentifierEnabled(isDetailIdentifierEnabled());
		toDetail.setDetailIdentifierName(getDetailIdentifier().getName());
		toDetail.getDetailIdentifier().copyFrom(getDetailIdentifier());
		super.copyTo(to);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.AbstractEntityModel#getCopy()
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
		for (IdentifierRef ir : originalReusedIdentifier.getUniqueIdentifiers()) {
			i = Math.max(ir.getName().length(), i);
		}
		return i;
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}
