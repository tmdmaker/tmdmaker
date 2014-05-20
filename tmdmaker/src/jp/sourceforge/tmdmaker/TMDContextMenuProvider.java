/*
 * Copyright 2009-2012 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

import jp.sourceforge.tmdmaker.action.CommonAttributeSettingAction;
import jp.sourceforge.tmdmaker.action.DatabaseSelectAction;
import jp.sourceforge.tmdmaker.action.DiagramImageGenerateAction;
import jp.sourceforge.tmdmaker.action.ImplementInfoEditAction;
import jp.sourceforge.tmdmaker.action.MultivalueAndCreateAction;
import jp.sourceforge.tmdmaker.action.MultivalueOrCreateAction;
import jp.sourceforge.tmdmaker.action.SubsetCreateAction;
import jp.sourceforge.tmdmaker.action.SubsetTypeTurnAction;
import jp.sourceforge.tmdmaker.action.VirtualEntityCreateAction;
import jp.sourceforge.tmdmaker.action.VirtualSupersetCreateAction;
import jp.sourceforge.tmdmaker.generate.GeneratorProvider;
import jp.sourceforge.tmdmaker.importer.impl.AttributeFileImporter;
import jp.sourceforge.tmdmaker.importer.impl.EntityFileImporter;
import jp.sourceforge.tmdmaker.model.generate.Generator;

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
		ActionRegistry registry = getActionRegistry();
		MenuManager subsetMenu = new MenuManager("サブセット");
		subsetMenu.add(registry.getAction(SubsetCreateAction.ID));
		subsetMenu.add(registry.getAction(SubsetTypeTurnAction.ID));
		menu.add(subsetMenu);

		MenuManager multivalueMenu = new MenuManager("データ の多値");
		multivalueMenu.add(registry.getAction(MultivalueOrCreateAction.ID));
		multivalueMenu.add(registry.getAction(MultivalueAndCreateAction.ID));
		menu.add(multivalueMenu);

		MenuManager tmdashMenu = new MenuManager("みなし概念(TM')");
		tmdashMenu.add(registry.getAction(VirtualEntityCreateAction.ID));
		tmdashMenu.add(registry.getAction(VirtualSupersetCreateAction.ID));
		menu.add(tmdashMenu);

		menu.add(new Separator("implement"));
		menu.add(registry.getAction(DatabaseSelectAction.ID));
		menu.add(registry.getAction(ImplementInfoEditAction.ID));
		menu.add(registry.getAction(CommonAttributeSettingAction.ID));

		menu.add(new Separator("generate"));
		menu.add(registry.getAction(DiagramImageGenerateAction.ID));
		
		for (Generator generator : GeneratorProvider.getGenerators()) {
			menu.add(registry.getAction(generator.getClass().getName()));
		}

		menu.add(new Separator("importer"));
		menu.add(registry.getAction(EntityFileImporter.class.getName()));
		menu.add(registry.getAction(AttributeFileImporter.class.getName()));
	}

}
