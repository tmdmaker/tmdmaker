/*
 * Copyright 2009 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
 * エンティティ
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class Entity extends AbstractEntityModel {
	/** 物理名 */
	private String physicalName = "";
	/** 摘要 */
	private String description = "";
	/** 個体指定子 */
	private Identifier identifier = new Identifier();

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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
		if (!oldValue.equals(identifierName)) {
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
		return new ReusedIdentifier(this.identifier);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#isEntityTypeEditable()
	 */
	@Override
	public boolean isEntityTypeEditable() {
		return getModelSourceConnections().size() == 0
				&& getModelTargetConnections().size() == 0;
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
		return getModelSourceConnections().size() == 0
				&& getModelTargetConnections().size() == 0;

	}

}
