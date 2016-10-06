/*
 * Copyright 2009-2015 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

package jp.sourceforge.tmdmaker.editpart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.sourceforge.tmdmaker.TMDEditor;
import jp.sourceforge.tmdmaker.dialog.ModelEditDialog;
import jp.sourceforge.tmdmaker.dialog.model.EditAttribute;
import jp.sourceforge.tmdmaker.figure.EntityFigure;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Constraint;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.IdentifierRef;
import jp.sourceforge.tmdmaker.model.ReusedIdentifier;
import jp.sourceforge.tmdmaker.model.rule.ImplementRule;
import jp.sourceforge.tmdmaker.property.AbstractEntityModelPropertySource;
import jp.sourceforge.tmdmaker.property.IPropertyAvailable;
import jp.sourceforge.tmdmaker.ui.command.ImplementDerivationModelsDeleteCommand;
import jp.sourceforge.tmdmaker.ui.command.ModelEditCommand;
import jp.sourceforge.tmdmaker.util.ConstraintConverter;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * Entity系のeditpartの基底クラス
 *
 * @author tohosaku
 *
 */
public abstract class AbstractEntityModelEditPart<T extends AbstractEntityModel>
		extends AbstractModelEditPart<T>implements IPropertyAvailable {
	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		EntityFigure figure = new EntityFigure();
		updateFigure(figure);

		return figure;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractModelEditPart#updateFigure(org.eclipse.draw2d.IFigure)
	 */
	@Override
	abstract protected void updateFigure(IFigure figure);

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractModelEditPart#onDoubleClicked()
	 */
	@Override
	protected void onDoubleClicked() {
		ModelEditDialog<T> dialog = getDialog();
		if (dialog.open() != Dialog.OK)
			return;
		CompoundCommand ccommand = createEditCommand(dialog.getEditAttributeList(),
				dialog.getEditedValue());
		executeEditCommand(ccommand);
	}

	/**
	 * 編集用ダイアログを取得する。
	 *
	 * @return ダイアログボックス
	 */
	protected abstract ModelEditDialog<T> getDialog();

	/**
	 * 編集用コマンドを生成する
	 *
	 * @param editAttributeList
	 * @param editedValue
	 * @return 編集用コマンド
	 */
	protected CompoundCommand createEditCommand(List<EditAttribute> editAttributeList,
			AbstractEntityModel editedValue) {
		CompoundCommand ccommand = new CompoundCommand();
		addAttributeEditCommands(ccommand, getModel(), editAttributeList);
		ModelEditCommand command = new ModelEditCommand(getModel(), editedValue);
		ccommand.add(command);
		return ccommand;
	}

	/**
	 * 編集コマンドを実行する。
	 *
	 * @param command
	 */
	protected void executeEditCommand(Command command) {
		getViewer().getEditDomain().getCommandStack().execute(command);
	}

	/**
	 * 自分自身が実装対象でない場合に実行するコマンドを生成する。
	 * 
	 * @param editedValue
	 * @return
	 */
	protected Command getDeleteCommand(AbstractEntityModel editedValue) {
		AbstractEntityModel table = getModel();
		if (table.isNotImplement() && !editedValue.isNotImplement()) {
			AbstractEntityModel original = ImplementRule.findOriginalImplementModel(table);
			return new ImplementDerivationModelsDeleteCommand(table, original);
		}
		return null;
	}

	/**
	 * ダイアログ表示のためのShellを返す。
	 *
	 * @return ParentShell
	 */
	protected Shell getControllShell() {
		return getViewer().getControl().getShell();
	}

	/**
	 * @param table
	 */
	protected List<String> extractRelationship(T table) {
		List<String> relationship = new ArrayList<String>();
		for (Map.Entry<AbstractEntityModel, ReusedIdentifier> rk : table.getReusedIdentifiers()
				.entrySet()) {
			for (Identifier i : rk.getValue().getUniqueIdentifiers()) {
				relationship.add(i.getName());
			}
		}
		return relationship;
	}

	/**
	 * @param table
	 * @param original
	 */
	protected List<String> extractRelationship(T table, IdentifierRef original) {
		List<String> relationship = new ArrayList<String>();
		for (Map.Entry<AbstractEntityModel, ReusedIdentifier> rk : table.getReusedIdentifiers()
				.entrySet()) {
			for (IdentifierRef i : rk.getValue().getUniqueIdentifiers()) {
				if (i.isSame(original)) {
					// nothing
				} else {
					relationship.add(i.getName());
				}
			}
		}
		return relationship;
	}

	@Override
	abstract protected void createEditPolicies();

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	protected List getModelChildren() {
		return getModel().getAttributes();
	}

	public IPropertySource getPropertySource(TMDEditor editor) {
		return new AbstractEntityModelPropertySource(editor, this.getModel());
	}
	
	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractModelEditPart#convert(jp.sourceforge.tmdmaker.model.Constraint)
	 */
	@Override
	protected Rectangle convert(Constraint constraint)
	{
		return ConstraintConverter.toRectangle(constraint);
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractModelEditPart#canAutoSize()
	 */
	@Override
	public boolean canAutoSize() {
		return true;
	}
}
