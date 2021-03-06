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
package org.tmdmaker.ui.editor.gef3.editpolicies;

import java.util.List;

import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Shell;
import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.Attribute;
import org.tmdmaker.core.model.Diagram;
import org.tmdmaker.core.model.IAttribute;
import org.tmdmaker.ui.dialogs.ModelEditDialog;
import org.tmdmaker.ui.dialogs.model.EditAttribute;
import org.tmdmaker.ui.editor.gef3.commands.AttributeEditCommand;
import org.tmdmaker.ui.editor.gef3.commands.ModelEditCommand;

/**
 * 
 * @author tohosaku
 *
 */
public abstract class AbstractEntityModelEditPolicy<T extends AbstractEntityModel> extends ComponentEditPolicy {
	
	@Override
	public Command getCommand(Request request) {
		if (REQ_OPEN.equals(request.getType()))
		{
			ModelEditDialog<T> dialog = getDialog();
			if (dialog.open() != Dialog.OK) return null;
			Command command = createEditCommand(dialog.getEditAttributeList(), dialog.getEditedValue());
			return command;
		}
		else{
			return super.getCommand(request);
		}
	}
	
	/**
	 * 編集用コマンドを生成する
	 *
	 * @param editAttributeList
	 * @param editedValue
	 * @return 編集用コマンド
	 */
	protected Command createEditCommand(List<EditAttribute> editAttributeList, AbstractEntityModel editedValue) {
		CompoundCommand ccommand = new CompoundCommand();
		addAttributeEditCommands(ccommand, getModel(), editAttributeList);
		ModelEditCommand command = new ModelEditCommand(getModel(), editedValue);
		ccommand.add(command);
		return ccommand.unwrap();
	}
	
	/**
	 * アトリビュート編集コマンドを作成する
	 * 
	 * @param ccommand
	 *            コマンド
	 * @param entity
	 *            モデル
	 * @param editAttributeList
	 *            編集したアトリビュートリスト
	 */
	protected void addAttributeEditCommands(CompoundCommand ccommand, T entity,
			List<EditAttribute> editAttributeList) {
		for (EditAttribute ea : editAttributeList) {
			IAttribute original = ea.getOriginalAttribute();
			if (ea.isEdited() && !ea.isAdded()) {
				Attribute editedValueAttribute = new Attribute();
				ea.copyTo(editedValueAttribute);
				AttributeEditCommand editCommand = new AttributeEditCommand(original,
						editedValueAttribute, entity);
				ccommand.add(editCommand);
			}
		}
	}
	
	/**
	 * 
	 * @return モデル
	 */
	@SuppressWarnings("unchecked")
	protected T getModel()
	{
		return (T)getHost().getModel();
	}
	
	/**
	 * 編集用ダイアログを表示する。
	 * 
	 * @return ダイアログ
	 */
	protected abstract ModelEditDialog<T> getDialog();
	
	/**
	 * ダイアログ表示のためのShellを返す。
	 *
	 * @return ParentShell
	 */
	protected Shell getControllShell() {
		return getHost().getViewer().getControl().getShell();
	}
	
	/**
	 * 
	 * @return ダイアグラム
	 */
	protected Diagram getDiagram()
	{
		return (Diagram) getHost().getParent().getModel();
	}
}
