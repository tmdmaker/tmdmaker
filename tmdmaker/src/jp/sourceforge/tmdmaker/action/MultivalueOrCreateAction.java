package jp.sourceforge.tmdmaker.action;

import jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart;
import jp.sourceforge.tmdmaker.editpart.SubsetTypeEditPart;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

/**
 * 
 * @author nakaG
 *
 */
public class MultivalueOrCreateAction extends SelectionAction {
	/** 多値のOR作成アクションを表す定数 */
	public static final String MO = "_MO";

	/**
	 * コンストラクタ
	 * 
	 * @param part
	 *            エディター
	 */
	public MultivalueOrCreateAction(IWorkbenchPart part) {
		super(part);
		setText("多値のOR作成");
		setId(MO);
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
					&& (selection instanceof SubsetTypeEditPart) == false) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		AbstractEntityEditPart part = getPart(); 
		AbstractEntityModel model = getModel();
		
		MultivalueOrCreateCommand command = new MultivalueOrCreateCommand();
		
		execute(command);
	}

	protected AbstractEntityEditPart getPart() {
		return (AbstractEntityEditPart) getSelectedObjects().get(0);
	}
	
	protected AbstractEntityModel getModel() {
		return (AbstractEntityModel) getPart().getModel();
	}
	
	private static class MultivalueOrCreateCommand extends Command {
		
	}
}
