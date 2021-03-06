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
package org.tmdmaker.ui.dialogs.model;

import org.tmdmaker.core.model.SubsetEntity;

/**
 * 編集用サブセット
 * 
 * @author nakaG
 * 
 */
public class EditSubsetEntity {
	private SubsetEntity original;
	private String name;

	public EditSubsetEntity() {
	}

	public EditSubsetEntity(SubsetEntity subsetEntity) {
		this.original = subsetEntity;
		this.name = subsetEntity.getName();
	}

	public boolean isAdded() {
		return original == null;
	}

	public boolean isNameChanged() {
		return original != null && !original.getName().equals(name);
	}

	/**
	 * @return the subsetEntity
	 */
	public SubsetEntity getOriginal() {
		return original;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}