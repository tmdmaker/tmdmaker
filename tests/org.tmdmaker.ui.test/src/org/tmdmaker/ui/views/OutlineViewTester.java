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
package org.tmdmaker.ui.views;

import static org.junit.Assert.assertEquals;

import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.tmdmaker.ui.test.AbstractTester;

/**
 * Test for OutlineView.
 * 
 * @author nakag
 *
 */
public class OutlineViewTester extends AbstractTester {

	public OutlineViewTester(SWTGefBot bot, SWTBotGefEditor botEditor) {
		super(bot, botEditor);
	}

	@Override
	protected void doTest() {
		SWTBotView outlineBot = bot.viewByTitle("Outline");
		SWTBotTree viewTree = outlineBot.bot().tree();
		SWTBotTreeItem[] identifierItems = viewTree.expandNode("顧客").expandNode("Identifier").getItems();
		assertEquals(identifierItems.length, 1);

		SWTBotTreeItem identifierTreeItem = viewTree.expandNode("顧客").expandNode("Identifier");
		bot.waitUntil(Conditions.treeItemHasNode(identifierTreeItem, "顧客番号"));
		assertEquals(identifierTreeItem.getNodes().size(), 1);
		
		identifierTreeItem.getNode(0).doubleClick();
		wait.waitDefault();

		SWTBotShell shell = bot.shell("Edit an attribute");
		shell.activate();
		wait.waitDefault();
		bot.text(1).setText("implementName");
		wait.waitDefault();
		bot.text(2).setText("summary");
		wait.waitDefault();
		bot.button("OK").click();
		wait.waitDefault();

		viewTree = outlineBot.bot().tree();
		SWTBotTreeItem[] attributeItems = viewTree.expandNode("顧客").expandNode("Attribute").getItems();
		assertEquals(attributeItems.length, 1);

		SWTBotTreeItem attributeTreeItem = viewTree.expandNode("顧客").expandNode("Attribute");
		bot.waitUntil(Conditions.treeItemHasNode(attributeTreeItem, "テスト名称"));
		assertEquals(attributeTreeItem.getNodes().size(), 1);

		attributeTreeItem.getNode(0).doubleClick();
		wait.waitDefault();
		
		shell = bot.shell("Edit an attribute");
		shell.activate();
		wait.waitDefault();
		bot.text(1).setText("implementName");
		wait.waitDefault();
		bot.text(2).setText("summary");
		bot.button("OK").click();
		wait.waitDefault();
		System.out.println("Attribute test ok");

		SWTBotTreeItem[] keyItems = viewTree.expandNode("顧客").expandNode("Key definitions").getItems();
		assertEquals(keyItems.length, 1);
		keyItems[0].select();
		viewTree.expandNode("顧客").expandNode("Key definitions").getNode(0).click();
		wait.waitDefault();

	}

}
