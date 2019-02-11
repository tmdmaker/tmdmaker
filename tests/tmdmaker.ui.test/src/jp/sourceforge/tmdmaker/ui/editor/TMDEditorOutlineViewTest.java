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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.eclipse.gef.finder.SWTBotGefTestCase;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.eclipse.ui.PlatformUI;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import jp.sourceforge.tmdmaker.ui.test.BotWait;
import jp.sourceforge.tmdmaker.ui.test.CreateDiagram;
import jp.sourceforge.tmdmaker.ui.test.NewEmptyProject;
import jp.sourceforge.tmdmaker.ui.test.View;

/**
 * TMD-MakerのOutlineビューのUIテスト
 * 
 * @author nakag
 *
 */
@RunWith(SWTBotJunit4ClassRunner.class)
public class TMDEditorOutlineViewTest extends SWTBotGefTestCase {
	private NewEmptyProject project = new NewEmptyProject();
	private CreateDiagram tmDiagram = new CreateDiagram();
	private View outlineView = new View("General", "Outline");
	private SWTBotGefEditor botEditor;
	private BotWait wait = new BotWait();
	private static final String PROJECT_NAME = "test";
	private static final String FILE_NAME = "test.tmd";
	private static final int RADIO_INDEX_RESOURCE = 0;

	@BeforeClass
	public static void closeWelcomePage() {
		new View("Welcome").close();
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		sleep();
		UIThreadRunnable.syncExec(new VoidResult() {
			public void run() {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().forceActive();
			}
		});

		project.createProject(PROJECT_NAME);
		sleep();
		tmDiagram.createFile(PROJECT_NAME, FILE_NAME);
		botEditor = bot.gefEditor(FILE_NAME);
	}

	@After
	public void tearDown() throws Exception {
		if (botEditor != null)
			botEditor.close();
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJECT_NAME);
		project.delete(true, null);
		super.tearDown();
	}

	@Test
	public void testMove() throws Exception {
		outlineView.open();
		botEditor.setFocus();
		createEntity(50, 50, "顧客番号", RADIO_INDEX_RESOURCE);
		SWTBotView outlineBot = bot.viewByTitle("Outline");

		SWTBotTree viewTree = outlineBot.bot().tree();
		SWTBotTreeItem[] identifierItems = viewTree.expandNode("顧客").expandNode("Identifier")
				.getItems();
		assertEquals(identifierItems.length, 1);
		identifierItems[0].doubleClick();
		sleep();
		bot.text(1).setText("implementName");
		bot.text(2).setText("summary");
		bot.button("OK").click();
		sleep();
		viewTree = outlineBot.bot().tree();
		SWTBotTreeItem[] attributeItems = viewTree.expandNode("顧客").expandNode("Attribute")
				.getItems();
		assertEquals(attributeItems.length, 1);
		attributeItems[0].doubleClick();
		sleep();
		bot.text(1).setText("implementName");
		bot.text(2).setText("summary");
		bot.button("OK").click();
		sleep();
	}

	private void createEntity(int x, int y, String identifierName, int type) {
		botEditor.activateTool("Entity");
		botEditor.click(x, y);
		SWTBotShell shell = bot.shell("Create a new entity");
		shell.activate();
		bot.text(0).setFocus();
		bot.text(0).setText(identifierName);
		bot.radio(type).click();
		bot.button("OK").click();
		wait.waitDefault();
	}

	private void sleep() {
		wait.waitDefault();
	}
}
