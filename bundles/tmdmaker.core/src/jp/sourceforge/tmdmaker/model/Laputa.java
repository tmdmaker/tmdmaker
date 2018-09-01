/*
 * Copyright 2009-2018 TMD-Maker Project <https://tmdmaker.osdn.jp/>
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

import jp.sourceforge.tmdmaker.model.parts.ModelName;

/**
 * ラピュタ
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class Laputa extends AbstractEntityModel {
	/** 個体指定子 */
	private Identifier identifier;

	/**
	 * コンストラクタは非公開.
	 */
	protected Laputa() {
		super();
		setNotImplement(true);
		setEntityType(EntityType.LAPUTA);
	}

	/**
	 * コンストラクタは非公開.
	 * 
	 * @param modelName
	 *            ラピュタ名
	 * @param identifier
	 *            個体指定子
	 */
	protected Laputa(final ModelName modelName, final Identifier identifier) {
		this();
		setName(modelName.getValue());
		setIdentifier(identifier);
	}

	/**
	 * ラピュタを生成する.
	 * 
	 * @return ラピュタ
	 */
	public static Laputa of() {
		return of(null);
	}

	/**
	 * ラピュタを生成する.
	 * 
	 * @param modelName ラピュタ名
	 * @param identifier 個体指定子
	 * @return ラピュタ
	 */
	public static Laputa of(final String modelName, final Identifier identifier) {
		return of(new ModelName(modelName), identifier);
	}

	/**
	 * ラピュタを生成する.
	 * @param modelName ラピュタ名
	 * @return ラピュタ
	 */
	public static Laputa of(final ModelName modelName) {
		return of(modelName, null);
	}

	/**
	 * ラピュタを生成する.
	 * 
	 * @param modelName ラピュタ名
	 * @param identifier 個体指定子
	 * 
	 * @return ラピュタ
	 */
	public static Laputa of(final ModelName modelName, final Identifier identifier) {
		ModelName newModelName = modelName;
		if (modelName == null || modelName.isEmpty()) {
			newModelName = new ModelName("ラピュタ");
		}
		Identifier newIdentifier = identifier;
		if (identifier == null) {
			newIdentifier = new Identifier(newModelName.getValue());
		}
		return new Laputa(newModelName, newIdentifier);
	}

	/**
	 * @return the identifier
	 */
	public Identifier getIdentifier() {
		return identifier;
	}

	/**
	 * @param identifier
	 *            the identifier to set
	 */
	public void setIdentifier(Identifier identifier) {
		this.identifier = identifier;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#createReusedIdentifier()
	 */
	@Override
	public ReusedIdentifier createReusedIdentifier() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#getCopy()
	 */
	@Override
	public AbstractEntityModel getCopy() {
		Laputa laputa = new Laputa();
		copyTo(laputa);
		return laputa;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#isDeletable()
	 */
	@Override
	public boolean isDeletable() {
		return true;
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
	 * 
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#accept(jp.sourceforge.tmdmaker.model.IVisitor)
	 */
	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#isNotImplement()
	 */
	@Override
	public boolean isNotImplement() {
		// Laputaは実装しない
		return true;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#canCreateSubset()
	 */
	@Override
	public boolean canCreateSubset() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#canCreateMultivalueOr()
	 */
	@Override
	public boolean canCreateMultivalueOr() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#canCreateVirtualEntity()
	 */
	@Override
	public boolean canCreateVirtualEntity() {
		return false;
	}

}
