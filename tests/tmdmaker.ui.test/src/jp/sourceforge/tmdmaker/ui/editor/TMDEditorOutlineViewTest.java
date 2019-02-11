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
package jp.sourceforge.tmdmaker.ui.editor;

import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * TMD-MakerのOutlineビューのUIテスト
 * 
 * @author nakag
 *
 */
@RunWith(SWTBotJunit4ClassRunner.class)
public class TMDEditorOutlineViewTest extends AbstractUITest {
	private static final int RADIO_INDEX_RESOURCE = 0;

	@Test
	public void testTree() throws Exception {
		outlineView.open();
		botEditor.setFocus();
		createEntity(50, 50, "顧客番号", RADIO_INDEX_RESOURCE);
		SWTBotView outlineBot = bot.viewByTitle("Outline");

		SWTBotTree viewTree = outlineBot.bot().tree();
		SWTBotTreeItem[] identifierItems = viewTree.expandNode("顧客").expandNode("Identifier")
				.getItems();
		assertEquals(identifierItems.length, 1);
		identifierItems[0].doubleClick();
		sleep();
		bot.text(1).setText("implementName");
		bot.text(2).setText("summary");
		bot.button("OK").click();
		sleep();
		viewTree = outlineBot.bot().tree();
		SWTBotTreeItem[] attributeItems = viewTree.expandNode("顧客").expandNode("Attribute")
				.getItems();
		assertEquals(attributeItems.length, 1);
		attributeItems[0].doubleClick();
		sleep();
		bot.text(1).setText("implementName");
		bot.text(2).setText("summary");
		bot.button("OK").click();
		sleep();
	}
}
