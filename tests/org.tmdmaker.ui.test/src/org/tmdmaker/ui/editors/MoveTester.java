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
package org.tmdmaker.ui.editors;

import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.keyboard.Keyboard;
import org.eclipse.swtbot.swt.finder.keyboard.KeyboardFactory;
import org.eclipse.swtbot.swt.finder.keyboard.Keystrokes;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.tmdmaker.ui.test.AbstractTester;

/**
 * Test for moving model.
 * 
 * @author nakag
 *
 */
public class MoveTester extends AbstractTester {

	public MoveTester(SWTGefBot bot, SWTBotGefEditor botEditor) {
		super(bot, botEditor);
	}

	@Override
	protected void doTest() {
		botEditor.activateTool("Select");

		botEditor.click(55, 55);

		botEditor.drag(55, 55, 100, 100);
		wait.waitDefault();
		botEditor.getEditPart("顧客").select();
		botEditor.activateTool("Select");

		SWTBotPreferences.KEYBOARD_LAYOUT = "EN_US";
		Keyboard keyboard = KeyboardFactory.getSWTKeyboard();
		keyboard.pressShortcut(Keystrokes.LEFT);
		keyboard.pressShortcut(Keystrokes.LEFT);
		keyboard.pressShortcut(Keystrokes.DOWN);
		keyboard.pressShortcut(Keystrokes.DOWN);
		keyboard.pressShortcut(Keystrokes.RIGHT);
		keyboard.pressShortcut(Keystrokes.RIGHT);
		keyboard.pressShortcut(Keystrokes.UP);
		keyboard.pressShortcut(Keystrokes.UP);
	}

}
