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

import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * TMD-Makerの実装情報のテスト
 *
 * @author nakag
 *
 */
@RunWith(SWTBotJunit4ClassRunner.class)
public class ImplementTest extends AbstractUITest {

	@Test
	public void testOpenImplementInfoDialog() {
		createEntity(50, 50, "顧客番号", 0);
		
		botEditor.click(55, 55);
		botEditor.clickContextMenu("Edit implementation information");
		SWTBotShell shell = bot.shell("Edit implementation information");
		shell.activate();
		bot.text(0).setText("impl");
		sleep();
		bot.tabItem(1).activate();
		bot.button("Add").click();
		sleep();
		shell = bot.shell("Edit keys");
		shell.activate();
		bot.button("<<").click();
		bot.button("OK").click();
		bot.tabItem(0).activate();
		sleep();
		bot.button("OK").click();
		sleep();
	}
}
