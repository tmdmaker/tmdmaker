package org.tmdmaker.ui.views;

import static org.junit.Assert.assertEquals;

import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.tmdmaker.ui.test.AbstractTester;

public class OutlineViewTester extends AbstractTester {

	public OutlineViewTester(SWTGefBot bot, SWTBotGefEditor botEditor) {
		super(bot, botEditor);
	}

	@Override
	protected void doTest() {
		SWTBotView outlineBot = bot.viewByTitle("Outline");
		SWTBotTree viewTree = outlineBot.bot().tree();
		SWTBotTreeItem[] identifierItems = viewTree.expandNode("顧客").expandNode("Identifier")
				.getItems();
		assertEquals(identifierItems.length, 1);
		identifierItems[0].doubleClick();
		wait.waitDefault();
		bot.text(1).setText("implementName");
		bot.text(2).setText("summary");
		bot.button("OK").click();
		wait.waitDefault();
		viewTree = outlineBot.bot().tree();
		SWTBotTreeItem[] attributeItems = viewTree.expandNode("顧客").expandNode("Attribute")
				.getItems();
		assertEquals(attributeItems.length, 1);
		attributeItems[0].doubleClick();
		wait.waitDefault();
		bot.text(1).setText("implementName");
		bot.text(2).setText("summary");
		bot.button("OK").click();
		wait.waitDefault();

		SWTBotTreeItem[] keyItems = viewTree.expandNode("顧客").expandNode("Key definitions")
				.getItems();
		assertEquals(keyItems.length, 1);
		keyItems[0].select();

	}

}
