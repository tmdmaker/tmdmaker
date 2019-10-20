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
