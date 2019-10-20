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

import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;

/**
 * 空のプロジェクトを作成するテスト用クラス.
 * 
 * @author nakag
 *
 */
public class NewEmptyProject {
	private SWTBot bot = new SWTBot();

	public void create(String projectName) throws Exception {
		BotWait wait = new BotWait();
		bot.menu("File").menu("New").menu("Project...").click();
		wait.waitDefault();wait.waitDefault();
		SWTBotShell shell = bot.shell("New Project");
		shell.activate();
		wait.waitDefault();wait.waitDefault();
		SWTBotTree projectSelectionTree = bot.tree();
		projectSelectionTree.expandNode("General").expandNode("Project").select();
		bot.waitUntil(new DefaultCondition() {
			public String getFailureMessage() {
				return "unable to select";
			}

			public boolean test() throws Exception {
				return bot.button("Next >").isEnabled();
			}

		});
		bot.button("Next >").click();
		wait.waitDefault();
		bot.textWithLabel("Project name:").setText(projectName);
		bot.button("Finish").click();
		bot.waitUntil(Conditions.shellCloses(shell));
	}
}
