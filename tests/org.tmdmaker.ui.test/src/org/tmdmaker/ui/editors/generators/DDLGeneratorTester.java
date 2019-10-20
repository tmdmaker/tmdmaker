package org.tmdmaker.ui.editors.generators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;

import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.tmdmaker.ui.test.AbstractTester;

import jp.sourceforge.tmdmaker.generate.ddl.DdlUtilsDDLGenerator;

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
