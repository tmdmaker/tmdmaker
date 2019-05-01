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

import java.io.File;

import org.eclipse.core.runtime.Platform;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.junit.Test;
import org.junit.runner.RunWith;

import jp.sourceforge.tmdmaker.generate.ddl.DdlUtilsDDLGenerator;

/**
 * TMD-MakerのDdlUtilsDDLGeneratorテスト.
 *
 * SWTBotがMessageDialogに対応していないので内部クラスを直接実行している.
 * 
 * @author nakag
 *
 */
@RunWith(SWTBotJunit4ClassRunner.class)
public class DDLGeneratorTest extends AbstractUITest {
	DdlUtilsDDLGenerator generator;
	String rootDir;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		createEntity(50, 50, "顧客番号", 0);
		createEntity(300, 50, "商品番号", 0);
		// R:R 対照表
		botEditor.activateTool("Relationship");
		botEditor.click(55, 55);
		botEditor.click(305, 55);
		wait.waitFor(10);
		generator = new DdlUtilsDDLGenerator();
		rootDir = Platform.getInstanceLocation().getURL().getPath() + projectName();
	}

	@Test
	public void notSelectDatabase() {
		try {
			generator.execute(rootDir, tmdEditor.getRootModel().query().listEntityModel());
		} catch (RuntimeException e) {
			return;
		}
		fail();
	}

	@Test
	public void generateTest() {
		botEditor.click(20, 20);
		botEditor.clickContextMenu("Select database");
		SWTBotShell shell = bot.shell("Select database");
		shell.activate();
		sleep();

		bot.comboBox(0).setSelection(7);
		bot.button("OK").click();
		sleep();

		generator.execute(rootDir, tmdEditor.getRootModel().query().listEntityModel());
		String docPath = rootDir + File.separator + "ddl.sql";
		assertEquals(true, new File(docPath).exists());
	}
}