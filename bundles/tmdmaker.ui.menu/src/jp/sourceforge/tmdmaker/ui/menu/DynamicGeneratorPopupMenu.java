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
package jp.sourceforge.tmdmaker.ui.menu;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.Command;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;

import jp.sourceforge.tmdmaker.extension.GeneratorFactory;
import jp.sourceforge.tmdmaker.model.generate.Generator;
import jp.sourceforge.tmdmaker.ui.handler.GeneratorHandler;

/**
 * エクスプローラ系のポップアップに登録されているGeneratorを表示するクラス
 * 
 * @author nakaG
 * 
 */
public class DynamicGeneratorPopupMenu extends CompoundContributionItem {

	@Override
	protected IContributionItem[] getContributionItems() {
		if (!isTMDFileSelected()) {
			return new IContributionItem[0];
		}
		IWorkbench workbench = PlatformUI.getWorkbench();
		ICommandService commandService = workbench.getService(ICommandService.class);
		IHandlerService handlerService = workbench.getService(IHandlerService.class);
		IWorkbenchWindow win = workbench.getActiveWorkbenchWindow();
		List<Generator> generagors = GeneratorFactory.getGenerators();

		List<IContributionItem> items = new ArrayList<IContributionItem>();
		MenuManager menu = new MenuManager("TMD-Maker");
		items.add(menu);
		Generator prev = null;
		for (Generator g : generagors) {
			if (prev != null && !prev.getGroupName().equals(g.getGroupName())) {
				menu.add(new Separator(g.getGroupName()));
			}
			prev = g;

			Command command = commandService.getCommand(g.getClass().getName());
			if (!command.isDefined()) {
				command.define(g.getGeneratorName(), "", commandService.getCategory("tmdmaker.ui.menu.category"));
			}
			handlerService.activateHandler(command.getId(), new GeneratorHandler(g) {
			});

			CommandContributionItemParameter p = new CommandContributionItemParameter(win, "", command.getId(),
					CommandContributionItem.STYLE_PUSH);
			CommandContributionItem item = new CommandContributionItem(p);
			item.setVisible(true);
			menu.add(item);
		}
		return items.toArray(new IContributionItem[0]);
	}

	private boolean isTMDFileSelected() {
		ISelection selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService()
				.getSelection();
		if (selection == null) {
			return false;
		}
		if (!(selection instanceof TreeSelection)) {
			return false;
		}
		TreeSelection treeSelection = (TreeSelection) selection;
		if (treeSelection.size() != 1) {
			return false;
		}
		Object selectedObject = treeSelection.getFirstElement();
		if (!(selectedObject instanceof IFile)) {
			return false;
		}
		IFile selectedFile = (IFile) selectedObject;
		return selectedFile.getFileExtension().equals("tmd");
	}
}
