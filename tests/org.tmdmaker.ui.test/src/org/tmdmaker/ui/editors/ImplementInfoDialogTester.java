package org.tmdmaker.ui.editors;

import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.tmdmaker.ui.test.AbstractTester;

public class ImplementInfoDialogTester extends AbstractTester {

	public ImplementInfoDialogTester(SWTGefBot bot, SWTBotGefEditor botEditor) {
		super(bot, botEditor);
	}

	@Override
	protected void doTest() {
		botEditor.getEditPart("顧客").select().click();
		botEditor.clickContextMenu("Edit implementation information");
		SWTBotShell shell = bot.shell("Edit implementation information");
		shell.activate();
		bot.text(0).setText("impl");
		wait.waitDefault();
		bot.tabItem(1).activate();
		bot.button("Add").click();
		wait.waitDefault();		
		shell = bot.shell("Edit keys");
		shell.activate();
		bot.button("<<").click();
		bot.button("OK").click();
		bot.tabItem(0).activate();
		wait.waitDefault();
		bot.button("OK").click();
		wait.waitDefault();
	}

}
