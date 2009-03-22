package jp.sourceforge.tmdmaker;

import org.eclipse.gef.ui.actions.ActionBarContributor;
import org.eclipse.gef.ui.actions.DeleteRetargetAction;
import org.eclipse.gef.ui.actions.RedoRetargetAction;
import org.eclipse.gef.ui.actions.UndoRetargetAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.actions.ActionFactory;

/**
 * 
 * @author nakaG
 *
 */
public class TMDEditorContributor extends ActionBarContributor {

	/**
	 * 
	 */
	public TMDEditorContributor() {
		super();
	}

	@Override
	protected void buildActions() {
	    addRetargetAction(new UndoRetargetAction());
	    addRetargetAction(new RedoRetargetAction());
	    addRetargetAction(new DeleteRetargetAction());
	}

	@Override
	protected void declareGlobalActionKeys() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorActionBarContributor#contributeToToolBar(org.eclipse.jface.action.IToolBarManager)
	 */
	@Override
	public void contributeToToolBar(IToolBarManager toolBarManager) {
		toolBarManager.add(getActionRegistry().getAction(
				ActionFactory.DELETE.getId()));
		toolBarManager.add(getActionRegistry().getAction(
				ActionFactory.UNDO.getId()));
		toolBarManager.add(getActionRegistry().getAction(
				ActionFactory.REDO.getId()));
	}
}
