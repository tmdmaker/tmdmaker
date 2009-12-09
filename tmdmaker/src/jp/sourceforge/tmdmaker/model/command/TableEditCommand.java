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
package jp.sourceforge.tmdmaker.model.command;

import java.util.List;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.Identifier;

import org.eclipse.gef.commands.Command;

/**
 * 表系モデル編集Command
 * 
 * @author nakaG
 * 
 * @param <T>
 *            AbstractEntityModelを継承したXX表のクラスを
 */
public class TableEditCommand<T extends AbstractEntityModel> extends Command {
	private String newName;
	// private List<Identifier> reusedIdentifieres;
	private List<Attribute> newAttributes;
	private boolean newNotImplement;

	protected T model;
	protected T newValue;

	private String oldName;
	// private List<Identifier> oldReuseKeys = new ArrayList<Identifier>();
	private List<Attribute> oldAttributes;
	private boolean oldNotImplement;

	/**
	 * コンストラクタ
	 * 
	 * @param model
	 * @param name
	 * @param reusedIdentifieres
	 * @param newAttributes
	 */
	public TableEditCommand(T model, String name, List<Identifier> reuseKey,
			List<Attribute> attributes) {
		this.newName = name;
		// this.reuseKey = reusedIdentifieres;
		this.newAttributes = attributes;
		this.oldName = model.getName();
		// this.oldReuseKeys = model.getReuseKeys();
		this.oldAttributes = model.getAttributes();
		this.model = model;
	}

	public TableEditCommand(T toBeEdit, T newValue) {
		this.model = toBeEdit;
		this.newValue = newValue;
		this.oldName = toBeEdit.getName();
		this.oldAttributes = toBeEdit.getAttributes();
		this.oldNotImplement = toBeEdit.isNotImplement();
		this.newName = this.newValue.getName();
		this.newAttributes = this.newValue.getAttributes();
		this.newNotImplement = this.newValue.isNotImplement();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		// TODO identifier更新伝播
		model.setAttributes(newAttributes);
		model.setNotImplement(newNotImplement);
		model.setName(newName);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		model.setAttributes(oldAttributes);
		model.setNotImplement(oldNotImplement);
		model.setName(oldName);
	}

}
