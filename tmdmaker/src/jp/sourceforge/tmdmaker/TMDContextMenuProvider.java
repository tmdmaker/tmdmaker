/*
 * Copyright 2009-2017 TMD-Maker Project <http://tmdmaker.osdn.jp/>
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
import jp.sourceforge.tmdmaker.action.MultivalueAndSupersetHideAction;
import jp.sourceforge.tmdmaker.action.MultivalueAndSupersetShowAction;
import jp.sourceforge.tmdmaker.action.MultivalueOrCreateAction;
import jp.sourceforge.tmdmaker.action.OpenDialogAction;
import jp.sourceforge.tmdmaker.action.SubsetCreateAction;
import jp.sourceforge.tmdmaker.action.SubsetTypeTurnAction;
import jp.sourceforge.tmdmaker.action.VirtualEntityCreateAction;
import jp.sourceforge.tmdmaker.action.VirtualSupersetCreateAction;
import jp.sourceforge.tmdmaker.extension.GeneratorFactory;
import jp.sourceforge.tmdmaker.extension.PluginExtensionPointFactory;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.SubsetType;
import jp.sourceforge.tmdmaker.model.generate.Generator;
import jp.sourceforge.tmdmaker.model.importer.FileImporter;

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

		// GEFActionConstants.addStandardActionGroups(menu);
		ActionRegistry registry = getActionRegistry();
		
		EditPartViewer viewer = getViewer();
		@SuppressWarnings("unchecked")
		List<EditPart> list = viewer.getSelectedEditParts();
		if (list != null)
		{
			for (EditPart e: list){
				logger.debug("editpart:" + e.getModel().getClass().getName());
			}
			
			if (list.size() > 1){
					menu.add(registry.getAction(VirtualSupersetCreateAction.ID));
					menu.add(new Separator("generate")); //$NON-NLS-1$
					menu.add(registry.getAction(DiagramImageGenerateAction.ID));

					for (Generator generator : GeneratorFactory.getGenerators()) {
						menu.add(registry.getAction(generator.getClass().getName()));
					}
			}
			else if (list.size() == 1)
			{
				if (list.get(0).getModel() instanceof AbstractEntityModel)
				{
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

					for (Generator generator : GeneratorFactory.getGenerators()) {
						menu.add(registry.getAction(generator.getClass().getName()));
					}
					
				}
				else if(list.get(0).getModel() instanceof SubsetType){
					
					menu.add(registry.getAction(SubsetTypeTurnAction.ID));
					
				}
				else	
				{
					menu.add(registry.getAction(OpenDialogAction.ID));
					menu.add(registry.getAction(ActionFactory.DELETE.getId()));
				}
			}
			else {
				menu.add(registry.getAction(DatabaseSelectAction.ID));
				menu.add(registry.getAction(VirtualSupersetCreateAction.ID));				
				menu.add(new Separator("generate")); //$NON-NLS-1$
				menu.add(registry.getAction(DiagramImageGenerateAction.ID));

				for (Generator generator : GeneratorFactory.getGenerators()) {
					menu.add(registry.getAction(generator.getClass().getName()));
				}
				
				menu.add(new Separator("importer")); //$NON-NLS-1$
				PluginExtensionPointFactory<FileImporter> fileImportFactory = new PluginExtensionPointFactory<FileImporter>(
						TMDPlugin.IMPORTER_PLUGIN_ID);
				for (FileImporter importer : fileImportFactory.getInstances()) {
					menu.add(registry.getAction(importer.getClass().getName()));
				}
			}
		}
	}
}
