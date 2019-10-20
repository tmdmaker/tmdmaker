package org.tmdmaker.ui.editors.generators;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.tmdmaker.ui.test.AbstractTester;

import jp.sourceforge.tmdmaker.generate.html.attributelist.AttributeListHtmlGenerator;
import jp.sourceforge.tmdmaker.generate.html.keydefinitionlist.KeyDefinitionListHtmlGenerator;
import jp.sourceforge.tmdmaker.generate.html.relationshiplist.RelationshipListHtmlGenerator;

public class HtmlGeneratorTester extends AbstractTester {

	public HtmlGeneratorTester(SWTGefBot bot, SWTBotGefEditor botEditor) {
		super(bot, botEditor);
	}

	@Override
	protected void doTest() {
		String rootDir = getProjectRootDir();
		attributeListTest(rootDir);
		keyListTest(rootDir);
		relationshipListTest(rootDir);

	}

	private void attributeListTest(String rootDir) {
		AttributeListHtmlGenerator generator = new AttributeListHtmlGenerator();
		generator.execute(rootDir, tmdEditor.getRootModel().query().listEntityModel());
		String docPath = rootDir + File.separator + "attribute_list.html";
		assertEquals(true, new File(docPath).exists());
	}

	private void keyListTest(String rootDir) {
		KeyDefinitionListHtmlGenerator generator = new KeyDefinitionListHtmlGenerator();
		generator.execute(rootDir, tmdEditor.getRootModel().query().listEntityModel());
		String docPath = rootDir + File.separator + "key_list.html";
		assertEquals(true, new File(docPath).exists());
	}

	private void relationshipListTest(String rootDir) {
		RelationshipListHtmlGenerator generator = new RelationshipListHtmlGenerator();
		generator.execute(rootDir, tmdEditor.getRootModel().query().listEntityModel());
		String docPath = rootDir + File.separator + "relationship_list.html";
		assertEquals(true, new File(docPath).exists());
	}
}
