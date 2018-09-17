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

import jp.sourceforge.tmdmaker.core.Configuration;
import jp.sourceforge.tmdmaker.model.parts.ModelName;

/**
 * エンティティ
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class Entity extends AbstractEntityModel {

	/** 物理名 */
	private String physicalName = "";
	/** 個体指定子 */
	private Identifier identifier = new Identifier();

	/**
	 * コンストラクタは非公開.
	 */
	protected Entity() {
		super();
	}

	/**
	 * リソースエンティティ生成.
	 * 
	 * @param identifier
	 *            個体指定子
	 * @return リソースエンティティ
	 */
	public static Entity ofResource(final Identifier identifier) {
		return ofResource(null, identifier);
	}

	/**
	 * リソースエンティティ生成.
	 * 
	 * @param entityName
	 *            エンティティ名
	 * @param identifier
	 *            個体指定子
	 * @return リソースエンティティ
	 */
	public static Entity ofResource(final ModelName entityName, final Identifier identifier) {
		Entity resource = of(entityName, identifier);
		resource.setEntityType(EntityType.RESOURCE);
		return resource;
	}

	/**
	 * イベントエンティティを生成する.
	 * 
	 * @param identifier
	 *            個体指定子
	 * @return イベントエンティティ
	 */
	public static Entity ofEvent(final Identifier identifier) {
		return ofEvent(null, identifier);
	}

	/**
	 * イベントエンティティを生成する.
	 * 
	 * @param entityName
	 *            エンティティ名
	 * @param identifier
	 *            個体指定子
	 * @return イベントエンティティ
	 */
	public static Entity ofEvent(final ModelName entityName, final Identifier identifier) {
		Entity event = of(entityName, identifier);
		event.setEntityType(EntityType.EVENT);
		return event;
	}

	/**
	 * エンティティを生成する.
	 * 
	 * 種別は未設定.
	 * 
	 * @param modelName
	 *            エンティティ名（候補）
	 * @param identifier
	 *            個体指定子
	 * @return エンティティ
	 */
	private static Entity of(final ModelName modelName, final Identifier identifier) {
		ModelName entityName = null;
		if (modelName == null || modelName.isEmpty()) {
			entityName = identifier.createEntityName();
		} else {
			entityName = modelName;
		}
		Entity entity = new Entity();
		entity.setName(entityName.getValue());
		entity.setIdentifier(identifier);
		return entity;
	}

	/**
	 * エンティティ種別に合わせた初期アトリビュートを追加する.
	 * 
	 * @return
	 */
	public Entity withDefaultAttribute() {
		addDefaultAttribute();
		return this;
	}

	/**
	 * 初期アトリビュートを設定する.
	 */
	private void addDefaultAttribute() {
		String attributeName = null;
		DataTypeDeclaration dataType = null;
		if (isEvent()) {
			attributeName = getDefaultEventAttributeName(getName());
			dataType = new DataTypeDeclaration(StandardSQLDataType.DATE, null, null);
		} else {
			attributeName = getDefaultResourceAttributeName(getName());
			dataType = new DataTypeDeclaration(StandardSQLDataType.CHARACTER_VARYING, 10, null);
		}

		if (hasAttribute(attributeName)) {
			return;
		}
		Attribute attribute = new Attribute(attributeName);
		attribute.setImplementName(attributeName);
		attribute.setDataTypeDeclaration(dataType);
		addAttribute(attribute);
	}

	/**
	 * リソースエンティティの初期アトリビュート名を返す.
	 * 
	 * @param entityName
	 *            エンティティ名
	 * @return 初期アトリビュート名
	 */
	public static String getDefaultResourceAttributeName(String entityName) {
		String format = Configuration.getDefault().getResourceAttributeFormat();
		return String.format(format, entityName);
	}

	/**
	 * イベントエンティティの初期アトリビュート名を返す.
	 * 
	 * @param entityName
	 *            エンティティ名
	 * @return 初期アトリビュート名
	 */
	public static String getDefaultEventAttributeName(String entityName) {
		String format = Configuration.getDefault().getEventAttributeFormat();
		return String.format(format, entityName);
	}

	/**
	 * @return the physicalName
	 */
	public String getPhysicalName() {
		return physicalName;
	}

	/**
	 * @param physicalName
	 *            the physicalName to set
	 */
	public void setPhysicalName(String physicalName) {
		this.physicalName = physicalName;
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
		Identifier oldValue = this.identifier;
		// oldValue.setParent(null);
		this.identifier = identifier;
		// this.identifier.setParent(this);
		firePropertyChange(PROPERTY_IDENTIFIER, oldValue, identifier);
	}

	/**
	 * 
	 * @param identifierName
	 *            個体指定子名称
	 */
	public void setIdentifierName(String identifierName) {
		String oldValue = this.identifier.getName();
		this.identifier.setName(identifierName);
		if (oldValue == null || !oldValue.equals(identifierName)) {
			firePropertyChange(PROPERTY_IDENTIFIER, oldValue, identifier);
			fireIdentifierChanged(null);
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#createReusedIdentifier()
	 */
	@Override
	public ReusedIdentifier createReusedIdentifier() {
		return new ReusedIdentifier(this.identifier, keyModels.getSurrogateKey());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#isEntityTypeEditable()
	 */
	@Override
	public boolean isEntityTypeEditable() {
		return getModelSourceConnections().size() == 0 && getModelTargetConnections().size() == 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#isDeletable()
	 */
	@Override
	public boolean isDeletable() {
		if (getEntityType() == EntityType.EVENT) {
			return getModelSourceConnections().size() == 0;
		}
		return getModelSourceConnections().size() == 0 && getModelTargetConnections().size() == 0;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#copyTo(jp.sourceforge.tmdmaker.model.AbstractEntityModel)
	 */
	@Override
	public void copyTo(AbstractEntityModel to) {
		if (to instanceof Entity) {
			Entity toEntity = (Entity) to;
			toEntity.setIdentifierName(getIdentifier().getName());
			toEntity.getIdentifier().copyFrom(getIdentifier());
		}
		super.copyTo(to);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#getCopy()
	 */
	@Override
	public Entity getCopy() {
		Entity copy = new Entity();
		copyTo(copy);
		return copy;
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#calcurateMaxIdentifierRefSize()
	 */
	@Override
	public int calcurateMaxIdentifierRefSize() {
		int i = getIdentifier().getName().length();
		return Math.max(super.calcurateMaxIdentifierRefSize(), i);
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
	 * 
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.ModelElement#canCreateMultivalueAnd()
	 */
	@Override
	public boolean canCreateMultivalueAnd() {
		return true;
	}

}
