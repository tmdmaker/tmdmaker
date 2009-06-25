package jp.sourceforge.tmdmaker.action;

import jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart;
import jp.sourceforge.tmdmaker.editpart.MultivalueAndAggregatorEditPart;
import jp.sourceforge.tmdmaker.editpart.SubsetTypeEditPart;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

/**
 * 
 * @author nakaG
 * 
 */
public abstract class AbstractEntitySelectionAction extends SelectionAction {

	/**
	 * コンストラクタ
	 * 
	 * @param part
	 *            エディター
	 */
	public AbstractEntitySelectionAction(IWorkbenchPart part) {
		super(part);
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		if (getSelectedObjects().size() == 1) {
			Object selection = getSelectedObjects().get(0);
			if (selection instanceof AbstractEntityEditPart
					&& (selection instanceof SubsetTypeEditPart) == false
					&& (selection instanceof MultivalueAndAggregatorEditPart == false)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @return コントローラ(EditPart)
	 */
	protected AbstractEntityEditPart getPart() {
		return (AbstractEntityEditPart) getSelectedObjects().get(0);
	}

	/**
	 * 
	 * @return モデル
	 */
	protected AbstractEntityModel getModel() {
		return (AbstractEntityModel) getPart().getModel();
	}
}
