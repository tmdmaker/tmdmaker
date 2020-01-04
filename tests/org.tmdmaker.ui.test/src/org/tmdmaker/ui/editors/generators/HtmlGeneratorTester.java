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

import java.io.File;

import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.tmdmaker.ui.test.AbstractTester;

import org.tmdmaker.generate.html.attributelist.AttributeListHtmlGenerator;
import org.tmdmaker.generate.html.keydefinitionlist.KeyDefinitionListHtmlGenerator;
import org.tmdmaker.generate.html.relationshiplist.RelationshipListHtmlGenerator;

/**
 * Test for HtmlGenerator.
 * 
 * @author nakag
 *
 */
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
