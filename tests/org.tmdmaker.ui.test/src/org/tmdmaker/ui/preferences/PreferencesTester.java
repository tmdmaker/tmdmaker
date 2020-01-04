/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org>
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
package org.tmdmaker.ui.preferences;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.tmdmaker.ui.test.AbstractTester;

/**
 * Test for Preferences settings.
 * 
 * @author nakag
 *
 */
public class PreferencesTester extends AbstractTester {

	public PreferencesTester(SWTGefBot bot, SWTBotGefEditor botEditor) {
		super(bot, botEditor);
	}

	@Override
	protected void doTest() {
		botEditor.setFocus();
		try {
			bot.menu("Window").menu("Preferences").click();
		} catch (Exception e) {
			openPreferenceForMac();
		}
		wait.waitDefault();
		SWTBotShell shell = bot.shell("Preferences");
		shell.activate();
		wait.waitDefault();
		bot.tree().expandNode("TMD-Maker").expandNode("Appearance").select();
		bot.tree().expandNode("TMD-Maker").expandNode("Rule").select();
		bot.tree().expandNode("TMD-Maker").expandNode("Appearance").select();

		shell = bot.shell("Preferences");
		shell.activate();
		bot.checkBox(0).click();
		bot.tree().expandNode("TMD-Maker").expandNode("Appearance").select();
		bot.tree().expandNode("TMD-Maker").expandNode("Appearance").expandNode("Color Appearance").select();
		bot.tree().expandNode("TMD-Maker").expandNode("Appearance").expandNode("Color Appearance").select();
		try {
			bot.button("Apply and Close").click();
		} catch (WidgetNotFoundException e) {
			bot.button("OK").click();
		}
	}

	private void openPreferenceForMac() {
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

}
