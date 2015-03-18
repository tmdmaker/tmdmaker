/*
 * Copyright 2009-2015 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package jp.sourceforge.tmdmaker.editor;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.gef.finder.SWTBotGefTestCase;
import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author nakag
 *
 */
@RunWith(SWTBotJunit4ClassRunner.class)
public class TMDEditorAndCreateContentsTest extends SWTBotGefTestCase {
	private NewEmptyProject project = new NewEmptyProject();
	private CreateDiagram tmDiagram = new CreateDiagram();
	private SWTBotGefEditor editor;
	private static final String PROJECT_NAME = "test";
	private static final String FILE_NAME = "test.tmd";

	@BeforeClass
	public static void closeWelcomePage() {
		try {
			// bot.viewByTitle("Welcome").close();
			new SWTGefBot().viewByTitle("Welcome").close();

		} catch (WidgetNotFoundException e) {
			// do nothing
			e.printStackTrace();
		}
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
//		bot.menu("File").menu("New").menu("Project...").click();
//
//		SWTBotShell shell = bot.shell("New Project");
//		shell.activate();
//		SWTBotTree projectSelectionTree = bot.tree();
//		projectSelectionTree.expandNode("General").expandNode("Project")
//				.select();
//		bot.waitUntil(new DefaultCondition() {
//			public String getFailureMessage() {
//				return "unable to select";
//			}
//
//			public boolean test() throws Exception {
//				return bot.button("Next >").isEnabled();
//			}
//
//		});
//		bot.button("Next >").click();
//		bot.textWithLabel("Project name:").setText(PROJECT_NAME);
//		bot.button("Finish").click();
//		bot.waitUntil(Conditions.shellCloses(shell));
		 project.createProject(PROJECT_NAME);
		tmDiagram.createFile(PROJECT_NAME, FILE_NAME);
		editor = bot.gefEditor(FILE_NAME);
	}

	@After
	public void tearDown() throws Exception {
		if (editor != null)
			editor.close();
		IProject project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(PROJECT_NAME);
		project.delete(true, null);
		super.tearDown();
	}

	@Test
	public void activateTool() {
		editor.activateTool("エンティティ");
		assertEquals("エンティティ", getActiveToolLabel());

		editor.activateTool("リレーションシップ");
		assertEquals("リレーションシップ", getActiveToolLabel());

	}

	private String getActiveToolLabel() {
		return editor.getActiveTool().getLabel();
	}
}
