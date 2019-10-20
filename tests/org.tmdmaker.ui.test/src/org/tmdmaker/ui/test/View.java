/*
 * Copyright 2009-2017 TMD-Maker Project <http://tmdmaker.osdn.jp/>
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

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;

/**
 * EclipseのViewを操作するテスト用クラス.
 * 
 * @author nakag
 *
 */
public class View {
	private SWTWorkbenchBot bot = new SWTWorkbenchBot();
	private final String node;
	private final String viewName;

	public View(String viewName) {
		this("", viewName);
	}

	public View(String node, String viewName) {
		this.node = node;
		this.viewName = viewName;
	}

	public void open() {
		BotWait wait = new BotWait();
		try {
			bot.menu("Window").menu("Show View").menu("Other...").click();
		} catch (WidgetNotFoundException e) {
			bot.menu("Window").menu("Show View").click();
		}
		bot.waitUntil(Conditions.shellIsActive("Show View"));
		SWTBotShell shell = bot.shell("Show View");
		shell.activate();

		SWTBotTree viewTree = bot.tree();
		viewTree.expandNode(node).select(viewName);
		wait.waitDefault();
		try {
			bot.button("Open").click();
		} catch (WidgetNotFoundException e) {
			bot.button("OK").click();
		}
	}

	public void close() {
		try {
			bot.viewByTitle(viewName).close();

		} catch (WidgetNotFoundException e) {
			// do nothing
			System.out.println(viewName + " view not found." + e.getMessage());
		}
	}
}
