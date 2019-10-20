package org.tmdmaker.ui.editors.generators;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.tmdmaker.ui.test.AbstractTester;

import jp.sourceforge.tmdmaker.generate.csv.attributelist.AttributeListCsvGenerator;

public class CSVGeneratorTester extends AbstractTester {

	public CSVGeneratorTester(SWTGefBot bot, SWTBotGefEditor botEditor) {
		super(bot, botEditor);
	}

	@Override
	protected void doTest() {
		AttributeListCsvGenerator generator = new AttributeListCsvGenerator();
		String rootDir = getProjectRootDir();
		generator.execute(rootDir, tmdEditor.getRootModel().query().listEntityModel());
		String docPath = rootDir + File.separator + "attribute_list.csv";
		assertEquals(true, new File(docPath).exists());
	}

}
