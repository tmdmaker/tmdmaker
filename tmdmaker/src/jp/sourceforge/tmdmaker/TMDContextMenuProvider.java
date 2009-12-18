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
package jp.sourceforge.tmdmaker;

import jp.sourceforge.tmdmaker.action.AttributeListSaveAction;
import jp.sourceforge.tmdmaker.action.DiagramImageSaveAction;
import jp.sourceforge.tmdmaker.action.MultivalueAndCreateAction;
import jp.sourceforge.tmdmaker.action.MultivalueOrCreateAction;
import jp.sourceforge.tmdmaker.action.SubsetCreateAction;
import jp.sourceforge.tmdmaker.action.VirtualEntityCreateAction;
import jp.sourceforge.tmdmaker.action.VirtualSupersetCreateAction;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;

/**
 * コンテキストメニュー生成
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

		// GEFActionConstants.addStandardActionGroups(menu);
		menu.add(getActionRegistry().getAction(SubsetCreateAction.ID));

		MenuManager multivalueMenu = new MenuManager("データ の多値");
		multivalueMenu.add(getActionRegistry().getAction(
				MultivalueOrCreateAction.ID));
		multivalueMenu.add(getActionRegistry().getAction(
				MultivalueAndCreateAction.ID));
		menu.add(multivalueMenu);

		MenuManager tmdashMenu = new MenuManager("みなし概念(TM')");
		tmdashMenu.add(getActionRegistry().getAction(
				VirtualEntityCreateAction.ID));
		tmdashMenu.add(getActionRegistry().getAction(
				VirtualSupersetCreateAction.ID));
		menu.add(tmdashMenu);

		menu.add(new Separator("print"));
		menu.add(getActionRegistry().getAction(DiagramImageSaveAction.ID));

		menu.add(getActionRegistry().getAction(AttributeListSaveAction.ID));
	}

}
