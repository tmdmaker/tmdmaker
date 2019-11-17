/*
 * Copyright 2009-2019 TMD-Maker Project <https://tmdmaker.osdn.jp/>
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
package org.tmdmaker.ui.editor;

import org.tmdmaker.ui.Activator;
import org.tmdmaker.ui.Messages;
import org.tmdmaker.ui.actions.gef3.CommonAttributeSettingAction;
import org.tmdmaker.ui.actions.gef3.DatabaseSelectAction;
import org.tmdmaker.ui.actions.gef3.DiagramImageGenerateAction;
import org.tmdmaker.ui.actions.gef3.ImplementInfoEditAction;
import org.tmdmaker.ui.actions.gef3.MultivalueAndCreateAction;
import org.tmdmaker.ui.actions.gef3.MultivalueAndSupersetHideAction;
import org.tmdmaker.ui.actions.gef3.MultivalueAndSupersetShowAction;
import org.tmdmaker.ui.actions.gef3.MultivalueOrCreateAction;
import org.tmdmaker.ui.actions.gef3.OpenDialogAction;
import org.tmdmaker.ui.actions.gef3.SubsetCreateAction;
import org.tmdmaker.ui.actions.gef3.SubsetTypeTurnAction;
import org.tmdmaker.ui.actions.gef3.VirtualEntityCreateAction;
import org.tmdmaker.ui.actions.gef3.VirtualSupersetCreateAction;

import java.util.List;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.actions.ActionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.SubsetType;
import org.tmdmaker.extension.GeneratorFactory;
import org.tmdmaker.extension.PluginExtensionPointFactory;
import org.tmdmaker.model.generate.Generator;
import org.tmdmaker.model.importer.FileImporter;

/**
 * コンテキストメニュー生成.
 *
 * @author nakaG
 *
 */
public class TMDContextMenuProvider extends ContextMenuProvider {
	/** actionRegistry */
	private ActionRegistry actionRegistry;
	private static Logger logger = LoggerFactory.getLogger(TMDContextMenuProvider.class);

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
	 * コンストラクタ.
	 *
	 * @param viewer
	 *            ビューア.
	 * @param registry
	 *            レジストリ.
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

		ActionRegistry registry = getActionRegistry();

		EditPartViewer viewer = getViewer();
		@SuppressWarnings("unchecked")
		List<EditPart> list = viewer.getSelectedEditParts();
		if (list != null) {
			printDebugLog(list);

			if (list.size() > 1) {
				addMenuWhenManyModelSelected(menu, registry);
			} else if (list.size() == 1) {
				if (list.get(0).getModel() instanceof AbstractEntityModel) {
					addMenuWhenSingleEntityModelSelected(menu, registry);
				} else if (list.get(0).getModel() instanceof SubsetType) {
					menu.add(registry.getAction(SubsetTypeTurnAction.ID));
				} else {
					menu.add(registry.getAction(OpenDialogAction.ID));
					menu.add(registry.getAction(ActionFactory.DELETE.getId()));
				}
			} else {
				// no model selected.
				menu.add(registry.getAction(DatabaseSelectAction.ID));
				menu.add(registry.getAction(VirtualSupersetCreateAction.ID));
				menu.add(new Separator("generate")); //$NON-NLS-1$
				menu.add(registry.getAction(DiagramImageGenerateAction.ID));

				addGeneratorMenus(menu, registry);

				menu.add(new Separator("importer")); //$NON-NLS-1$
				PluginExtensionPointFactory<FileImporter> fileImportFactory = new PluginExtensionPointFactory<FileImporter>(
						Activator.IMPORTER_PLUGIN_ID);
				for (FileImporter importer : fileImportFactory.getInstances()) {
					menu.add(registry.getAction(importer.getClass().getName()));
				}
			}
		}
	}

	private void addMenuWhenManyModelSelected(IMenuManager menu, ActionRegistry registry) {
		menu.add(registry.getAction(VirtualSupersetCreateAction.ID));
		menu.add(new Separator("generate")); //$NON-NLS-1$
		menu.add(registry.getAction(DiagramImageGenerateAction.ID));

		addGeneratorMenus(menu, registry);
	}

	private void addGeneratorMenus(IMenuManager menu, ActionRegistry registry) {
		for (Generator generator : GeneratorFactory.getGenerators()) {
			menu.add(registry.getAction(generator.getClass().getName()));
		}
	}

	private void addMenuWhenSingleEntityModelSelected(IMenuManager menu, ActionRegistry registry) {
		menu.add(registry.getAction(OpenDialogAction.ID));
		menu.add(registry.getAction(ActionFactory.DELETE.getId()));

		menu.add(registry.getAction(SubsetCreateAction.ID));

		MenuManager multivalueMenu = new MenuManager(Messages.Multivalue);
		multivalueMenu.add(registry.getAction(MultivalueOrCreateAction.ID));
		multivalueMenu.add(registry.getAction(MultivalueAndCreateAction.ID));
		multivalueMenu.add(registry.getAction(MultivalueAndSupersetHideAction.ID));
		multivalueMenu.add(registry.getAction(MultivalueAndSupersetShowAction.ID));
		menu.add(multivalueMenu);

		MenuManager tmdashMenu = new MenuManager(Messages.VirtualConcept);
		tmdashMenu.add(registry.getAction(VirtualEntityCreateAction.ID));
		tmdashMenu.add(registry.getAction(VirtualSupersetCreateAction.ID));
		menu.add(tmdashMenu);
		menu.add(new Separator("implement")); //$NON-NLS-1$
		menu.add(registry.getAction(ImplementInfoEditAction.ID));
		menu.add(registry.getAction(CommonAttributeSettingAction.ID));
		menu.add(new Separator("generate")); //$NON-NLS-1$

		addGeneratorMenus(menu, registry);
	}

	private void printDebugLog(List<EditPart> list) {
		if (!logger.isDebugEnabled()) {
			return;
		}

		for (EditPart e : list) {
			logger.debug("editpart:{}", e.getModel().getClass().getName());
		}
	}
}
