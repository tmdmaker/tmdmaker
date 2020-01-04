/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org>
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
package org.tmdmaker.ui.editors.generators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;

import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.tmdmaker.ui.test.AbstractTester;

import org.tmdmaker.generate.ddl.DdlUtilsDDLGenerator;

/**
 * Test for  DDLGenerator.
 * 
 * @author nakag
 *
 */
public class DDLGeneratorTester extends AbstractTester {

	public DDLGeneratorTester(SWTGefBot bot, SWTBotGefEditor botEditor) {
		super(bot, botEditor);
	}

	@Override
	protected void doTest() {
		DdlUtilsDDLGenerator generator = new DdlUtilsDDLGenerator();
		String rootDir = getProjectRootDir();

		boolean selectAlertOccur = false;
		selectDatabase(0);
		try {
			generator.execute(rootDir, tmdEditor.getRootModel().query().listEntityModel());
		} catch (RuntimeException e) {
			selectAlertOccur = true;
		}
		if (!selectAlertOccur) {
			fail();
		}

		selectDatabase(14);

		generator.execute(rootDir, tmdEditor.getRootModel().query().listEntityModel());
		String docPath = rootDir + File.separator + "ddl.sql";
		assertEquals(true, new File(docPath).exists());
	}

	protected void selectDatabase(int selectIndex) {
		botEditor.click(5, 5);
		botEditor.clickContextMenu("Select database");
		SWTBotShell shell = bot.shell("Select database");
		shell.activate();
		wait.waitDefault();

		bot.comboBox(0).setSelection(selectIndex);
		bot.button("OK").click();
		wait.waitDefault();
	}

}
