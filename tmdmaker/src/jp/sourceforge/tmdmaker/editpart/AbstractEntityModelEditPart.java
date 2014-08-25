package jp.sourceforge.tmdmaker.editpart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.sourceforge.tmdmaker.TMDEditor;
import jp.sourceforge.tmdmaker.dialog.ModelEditDialog;
import jp.sourceforge.tmdmaker.dialog.model.EditAttribute;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.IdentifierRef;
import jp.sourceforge.tmdmaker.model.ReusedIdentifier;
import jp.sourceforge.tmdmaker.model.rule.ImplementRule;
import jp.sourceforge.tmdmaker.property.AbstractEntityModelPropertySource;
import jp.sourceforge.tmdmaker.property.IPropertyAvailable;
import jp.sourceforge.tmdmaker.ui.command.ImplementDerivationModelsDeleteCommand;
import jp.sourceforge.tmdmaker.ui.command.ModelEditCommand;
import jp.sourceforge.tmdmaker.ui.preferences.appearance.AppearanceSetting;
import jp.sourceforge.tmdmaker.ui.preferences.appearance.ModelAppearance;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.views.properties.IPropertySource;

public abstract class AbstractEntityModelEditPart<T extends AbstractEntityModel> extends AbstractModelEditPart<T> implements IPropertyAvailable {

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
		if (dialog.open() != Dialog.OK) return;		
		CompoundCommand ccommand = createEditCommand(dialog.getEditAttributeList(), dialog.getEditedValue());
		executeEditCommand(ccommand);
	}
	
	/**
	 * 編集用ダイアログを取得する。
	 * @return ダイアログボックス
	 */
	protected abstract ModelEditDialog<T> getDialog();

	/**
	 * 編集用コマンドを生成する
	 * @param editAttributeList
	 * @param editedValue
	 * @return 編集用コマンド
	 */
	protected CompoundCommand createEditCommand(List<EditAttribute> editAttributeList, AbstractEntityModel editedValue)
	{
		CompoundCommand ccommand = new CompoundCommand();
		addAttributeEditCommands(ccommand, getModel(), editAttributeList);
		ModelEditCommand command = new ModelEditCommand(getModel(), editedValue);
		ccommand.add(command);
		return ccommand;
	}
	
	/**
	 * 編集コマンドを実行する。
	 * @param command
	 */
	protected void executeEditCommand(Command command)
	{
		getViewer().getEditDomain().getCommandStack().execute(command);
	}
	
	/**
	 * 自分自身が実装対象でない場合に実行するコマンドを生成する。
	 * @param editedValue
	 * @return
	 */
	protected Command getDeleteCommand(AbstractEntityModel editedValue)
	{
		AbstractEntityModel table = getModel();
		if (table.isNotImplement() && !editedValue.isNotImplement()) {
			AbstractEntityModel original = ImplementRule.findOriginalImplementModel(table);
			return new ImplementDerivationModelsDeleteCommand(table, original);
		}
		return null;
	}
	
	/**
	 * ダイアログ表示のためのShellを返す。
	 * @return ParentShell
	 */
	protected Shell getControllShell()
	{
		return getViewer().getControl().getShell();
	}

	/**
	 * @param table
	 */
	protected List<String> extractRelationship(T table) {
		List<String> relationship = new ArrayList<String>();
		for (Map.Entry<AbstractEntityModel, ReusedIdentifier> rk : table.getReusedIdentifieres().entrySet()) {
			for (Identifier i : rk.getValue().getUniqueIdentifieres()) {
				relationship.add(i.getName());
			}
		}
		return relationship;
	}

	/**
	 * @param entityFigure
	 * @param entity
	 * @param original
	 */
	protected List<String> extractRelationship(T table, IdentifierRef original) {
		List<String> relationship = new ArrayList<String>();
		for (Map.Entry<AbstractEntityModel, ReusedIdentifier> rk : table.getReusedIdentifieres().entrySet()) {
			for (IdentifierRef i : rk.getValue().getUniqueIdentifieres()) {
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
	 * モデルの色を設定する
	 * 
	 * @param entityFigure
	 *            モデル
	 * @param appearance
	 *            モデル外観
	 */
	protected void setupColor(IFigure entityFigure) {
		entityFigure.setBackgroundColor(createBackgroundColor(getAppearance()));
		entityFigure.setForegroundColor(createForegroundColor(getAppearance()));
	}

	abstract protected ModelAppearance getAppearance();
	
	private Color createBackgroundColor(ModelAppearance appearance) {
		AppearanceSetting config = AppearanceSetting.getInstance();
		if (config.isColorEnabled()) {
			return new Color(null, config.getBackground(appearance));
		} else {
			return ColorConstants.white;
		}
	}

	private Color createForegroundColor(ModelAppearance appearance) {
		AppearanceSetting config = AppearanceSetting.getInstance();
		if (config.isColorEnabled()) {
			return new Color(null, config.getFont(appearance));
		} else {
			return ColorConstants.black;
		}
	}
	
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
}
