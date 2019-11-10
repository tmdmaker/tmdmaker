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

import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.tmdmaker.ui.test.AbstractTester;
import org.tmdmaker.ui.test.AbstractUITest;

/**
 * Test for Project Explorer. Especially context menu.
 * 
 * @author nakag
 *
 */
public class ProjectExplorerTester extends AbstractTester {

	public ProjectExplorerTester(SWTGefBot bot, SWTBotGefEditor botEditor) {
		super(bot, botEditor);
	}

	@Override
	protected void doTest() {
		SWTBotView explorerBot = bot.viewByTitle("Project Explorer");
		explorerBot.show();

		SWTBotTree projectTree = explorerBot.bot().tree();
		projectTree.setFocus();

		SWTBotTreeItem projectTreeItem = projectTree.expandNode(AbstractUITest.PROJECT_NAME);
		projectTreeItem.expandNode(AbstractUITest.FILE_NAME).select().contextMenu("TMD-Maker")
				.contextMenu("Output AttributeList in CSV format").click();
		wait.waitDefault();
	}

}
