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

import org.eclipse.core.runtime.Platform;
import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.tmdmaker.ui.editor.TMDEditor;

/**
 * Helper class for SWTBot UI test.
 * 
 * @author nakag
 *
 */
public abstract class AbstractTester {
	protected final SWTGefBot bot;
	protected final SWTBotGefEditor botEditor;
	protected TMDEditor tmdEditor;
	protected BotWait wait = new BotWait();

	public AbstractTester(SWTGefBot bot, SWTBotGefEditor botEditor) {
		this.bot = bot;
		this.botEditor = botEditor;
		this.tmdEditor = (TMDEditor) botEditor.getReference().getEditor(false);
	}

	protected void setUp() {}
	protected abstract void doTest();
	protected void tearDown() {}

	public void test() {
		try {
			setUp();
			doTest();
		} catch (Error e) {
			throw e;
		} finally {
			tearDown();
		}
	}

	protected String getProjectRootDir() {
		return Platform.getInstanceLocation().getURL().getPath() + AbstractUITest.PROJECT_NAME;
	}
}
