/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org/>
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
package org.tmdmaker.ui.test;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swtbot.eclipse.gef.finder.SWTBotGefTestCase;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import jp.sourceforge.tmdmaker.TMDEditor;


/**
 * Helper class for SWTBot UI test.
 * 
 * @author nakag
 *
 */
public abstract class AbstractUITest extends SWTBotGefTestCase {

	private NewEmptyProject project = new NewEmptyProject();
	private NewDiagramFile tmDiagram = new NewDiagramFile();
	protected View propertiesView = new View("General", "Properties");
	protected View outlineView = new View("General", "Outline");
	protected SWTBotGefEditor botEditor;
	protected TMDEditor tmdEditor;
	protected BotWait wait = new BotWait();
	protected Window window = null;
	public static final String PROJECT_NAME = "test";
	public static final String FILE_NAME = "test.tmd";

	@BeforeClass
	public static void closeWelcomePage() {
		new View("Welcome").close();
	}

	public AbstractUITest() {
		super();
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		sleep();
		UIThreadRunnable.syncExec(new VoidResult() {
			public void run() {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().forceActive();
			}
		});
	
		project.create(PROJECT_NAME);
		sleep();
		tmDiagram.create(PROJECT_NAME, FILE_NAME);
		botEditor = bot.gefEditor(FILE_NAME);
		tmdEditor = (TMDEditor) botEditor.getReference().getEditor(false);
		window = new Window(bot, botEditor);
	}

	@After
	public void tearDown() throws Exception {
		if (botEditor != null)
			botEditor.close();
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJECT_NAME);
		project.delete(true, null);
		super.tearDown();
	}

	protected void sleep() {
		wait.waitDefault();
	}

	protected void maximizeActiveWindow() {
		window.maximizeActiveWindow();
	}

	protected void maximizeActiveEditor() {
		window.maximizeActiveEditor();
	}

	protected void normalizeActiveEditor() {
		window.normalizeActiveEditor();
	}

	protected void openPreferenceForMac() {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		workbench.getDisplay().asyncExec(new Runnable() {
			public void run() {
				IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
				if (window != null) {
					Menu appMenu = workbench.getDisplay().getSystemMenu();
					for (MenuItem item : appMenu.getItems()) {
						if (item.getText().startsWith("Preferences")) {
							Event event = new Event();
							event.time = (int) System.currentTimeMillis();
							event.widget = item;
							event.display = workbench.getDisplay();
							item.setSelection(true);
							item.notifyListeners(SWT.Selection, event);
							break;
						}
					}
				}
			}
		});
	}

	protected void createEntity(int x, int y, String identifierName, int type) {
		botEditor.activateTool("Entity");
		botEditor.click(x, y);
		SWTBotShell shell = bot.shell("Create a new entity");
		shell.activate();
		bot.text(0).setFocus();
		bot.text(0).setText(identifierName);
		bot.radio(type).click();
		bot.button("OK").click();
		wait.waitDefault();
	}

	protected String projectName() {
		return PROJECT_NAME;
	}
}