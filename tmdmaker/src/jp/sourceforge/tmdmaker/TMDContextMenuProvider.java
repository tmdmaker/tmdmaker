package jp.sourceforge.tmdmaker;

import jp.sourceforge.tmdmaker.action.MultivalueOrCreateAction;
import jp.sourceforge.tmdmaker.action.SubsetEditAction;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.jface.action.IMenuManager;

/**
 * 
 * @author nakaG
 * 
 */
public class TMDContextMenuProvider extends ContextMenuProvider {
	/** actionRegistry */
	private ActionRegistry actionRegistry;

	/**
	 * @return the actionRegistry
	 */
	public ActionRegistry getActionRegistry() {
		return actionRegistry;
	}

	/**
	 * @param actionRegistry
	 *            the actionRegistry to set
	 */
	public void setActionRegistry(ActionRegistry actionRegistry) {
		this.actionRegistry = actionRegistry;
	}

	/**
	 * Constractor
	 * 
	 * @param viewer
	 * @param registry
	 * @param editor
	 */
	public TMDContextMenuProvider(EditPartViewer viewer, ActionRegistry registry) {
		super(viewer);
		setActionRegistry(registry);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.ContextMenuProvider#buildContextMenu(org.eclipse.jface
	 * .action.IMenuManager)
	 */
	@Override
	public void buildContextMenu(IMenuManager menu) {
		System.out.println("buildContextMenu()");

		// GEFActionConstants.addStandardActionGroups(menu);
		menu.add(getActionRegistry().getAction(SubsetEditAction.SUBSET));
		menu.add(getActionRegistry().getAction(MultivalueOrCreateAction.MO));
	}

}
