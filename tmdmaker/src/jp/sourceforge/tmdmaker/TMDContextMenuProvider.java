package jp.sourceforge.tmdmaker;

import jp.sourceforge.tmdmaker.action.MultivalueAndCreateAction;
import jp.sourceforge.tmdmaker.action.MultivalueOrCreateAction;
import jp.sourceforge.tmdmaker.action.SubsetEditAction;
import jp.sourceforge.tmdmaker.action.VirtualEntityCreateAction;
import jp.sourceforge.tmdmaker.action.VirtualSupersetCreateAction;

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
	 * コンストラクタ
	 * 
	 * @param viewer
	 *            ビューア
	 * @param registry
	 *            レジストリ
	 */
	public TMDContextMenuProvider(EditPartViewer viewer, ActionRegistry registry) {
		super(viewer);
		setActionRegistry(registry);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.ContextMenuProvider#buildContextMenu(org.eclipse.jface.action.IMenuManager)
	 */
	@Override
	public void buildContextMenu(IMenuManager menu) {
		System.out.println("buildContextMenu()");

		// GEFActionConstants.addStandardActionGroups(menu);
		menu.add(getActionRegistry().getAction(SubsetEditAction.ID));
		menu.add(getActionRegistry().getAction(MultivalueOrCreateAction.ID));
		menu.add(getActionRegistry().getAction(MultivalueAndCreateAction.ID));
		menu.add(getActionRegistry().getAction(VirtualEntityCreateAction.ID));
		menu.add(getActionRegistry().getAction(VirtualSupersetCreateAction.ID));
	}

}
