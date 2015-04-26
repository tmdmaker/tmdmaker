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
package jp.sourceforge.tmdmaker.dialog;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import jp.sourceforge.tmdmaker.dialog.model.EditAttribute;
import jp.sourceforge.tmdmaker.dialog.model.EditTable;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * モデル編集ダイアログの基底クラス
 * 
 * @author tohosaku
 * 
 */
public abstract class ModelEditDialog<T extends AbstractEntityModel> extends Dialog implements PropertyChangeListener {

	public ModelEditDialog(Shell parentShell) {
		super(parentShell);
	}

	public ModelEditDialog(IShellProvider parentShell) {
		super(parentShell);
	}
	
	/** 編集元エンティティ */
	protected EditTable entity;
	
	/** 編集結果格納用 */
	protected T editedValue;

	protected abstract Control createDialogArea(Composite parent);

	public abstract void propertyChange(PropertyChangeEvent evt);

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed()
	{
		super.okPressed();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#close()
	 */
	@Override
	public boolean close() {
		entity.removePropertyChangeListener(this);
		return super.close();
	}
	
	protected EditTable getEditModel()
	{
		return entity;
	}

	/**
	 * @return the editAttributeList
	 */
	public List<EditAttribute> getEditAttributeList() {
		return entity.getAttributes();
	}

	/**
	 * @return the editedValueEntity
	 */
	public T getEditedValue() {
		return editedValue;
	}

}