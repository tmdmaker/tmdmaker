/*
 * Copyright 2009-2013 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package jp.sourceforge.tmdmaker.rcp;

import org.eclipse.e4.ui.model.application.ui.SideValue;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimmedWindow;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.model.application.ui.menu.MToolControl;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.internal.WorkbenchWindow;

/**
 * 
 * @author nakaG
 * 
 */
public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {
	/**
	 * 
	 * @param configurer
	 */
	public ApplicationWorkbenchWindowAdvisor(
			IWorkbenchWindowConfigurer configurer) {
		super(configurer);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.application.WorkbenchWindowAdvisor#createActionBarAdvisor(org.eclipse.ui.application.IActionBarConfigurer)
	 */
	public ActionBarAdvisor createActionBarAdvisor(
			IActionBarConfigurer configurer) {
		return new ApplicationActionBarAdvisor(configurer);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.application.WorkbenchWindowAdvisor#preWindowOpen()
	 */
	public void preWindowOpen() {
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		configurer.setInitialSize(new Point(640, 480));
		configurer.setShowCoolBar(true);
		configurer.setShowMenuBar(true);
		configurer.setShowStatusLine(false);
		configurer.setTitle("TMD-Maker for RCP");
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.application.WorkbenchWindowAdvisor#postWindowOpen()
	 */
	@Override
	public void postWindowOpen() {
		super.postWindowOpen();
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		Shell shell = configurer.getWindow().getShell();
		if (configurer.getInitialSize().equals(shell.getSize())) {
			shell.setMaximized(true);
			shell.setMinimumSize(640, 480);
		}
	}

	@Override
	public void openIntro() {
		super.openIntro();
		trimWindow();
	}

	private void trimWindow() {
		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		if (window instanceof WorkbenchWindow) {
			MWindow model = ((WorkbenchWindow) window).getModel();
			EModelService modelService = model.getContext().get(
					EModelService.class);
			hideQuickAccess(modelService, model);
		}
	}

	private void hideQuickAccess(EModelService modelService, MWindow model) {
		MToolControl searchField = (MToolControl) modelService.find(
				"SearchField", model);
		if (searchField != null) {
			searchField.setToBeRendered(false);
			MTrimBar trimBar = modelService.getTrim((MTrimmedWindow) model,
					SideValue.TOP);
			trimBar.getChildren().remove(searchField);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.ui.application.WorkbenchWindowAdvisor#preWindowShellClose()
	 */
	@Override
	public boolean preWindowShellClose() {
		Shell shell = getWindowConfigurer().getWindow().getShell();
		return MessageDialog.openConfirm(shell, "Confirm Exit", "Exit TMD-Maker?");
	}

}
