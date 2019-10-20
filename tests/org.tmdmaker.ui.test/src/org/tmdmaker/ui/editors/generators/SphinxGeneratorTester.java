package org.tmdmaker.ui.editors.generators;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.tmdmaker.ui.test.AbstractTester;

import jp.sourceforge.tmdmaker.sphinx.generate.DocGenerator;

public class SphinxGeneratorTester extends AbstractTester {

	public SphinxGeneratorTester(SWTGefBot bot, SWTBotGefEditor botEditor) {
		super(bot, botEditor);
	}

	@Override
	protected void doTest() {
		DocGenerator generator = new DocGenerator();;
		String rootDir = getProjectRootDir();
		generator.execute(rootDir, tmdEditor.getRootModel().query().listEntityModel());
		String docPath = rootDir + File.separator + "doc";
		assertEquals(true, new File(docPath).exists());
	}

}
