/*
 * Copyright 2009-2017 TMD-Maker Project <http://tmdmaker.osdn.jp/>
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

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.eclipse.gef.finder.SWTBotGefTestCase;
import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import jp.sourceforge.tmdmaker.TMDEditor;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.CombinationTable;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.Identifier;

/**
 * TMD-MakerのUIテスト
 *
 * @author nakag
 *
 */
@RunWith(SWTBotJunit4ClassRunner.class)
public class TMDEditorAndCreateContentsTest extends SWTBotGefTestCase {
	private NewEmptyProject project = new NewEmptyProject();
	private CreateDiagram tmDiagram = new CreateDiagram();
	private SWTBotGefEditor botEditor;
	private TMDEditor tmdEditor;
	private static final String PROJECT_NAME = "test";
	private static final String FILE_NAME = "test.tmd";

	@BeforeClass
	public static void closeWelcomePage() {
		try {
			new SWTGefBot().viewByTitle("Welcome").close();

		} catch (WidgetNotFoundException e) {
			// do nothing
			System.out.println("Welcome view not found." + e.getMessage());
		}
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
		tmdEditor = (TMDEditor) botEditor.getReference().getEditor(false);
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
	public void activateTool() {
		botEditor.activateTool("Entity");
		assertEquals("Entity", getActiveToolLabel());

		botEditor.activateTool("Relationship");
		assertEquals("Relationship", getActiveToolLabel());

		botEditor.activateTool("Turbo file");
		assertEquals("Turbo file", getActiveToolLabel());

		botEditor.activateTool("Memo");
		assertEquals("Memo", getActiveToolLabel());
	}

	private String getActiveToolLabel() {
		return botEditor.getActiveTool().getLabel();
	}

	@Test
	public void testCreateEntity() {
		maximizeActiveWindow();
		maximizeActiveEditor();
		botEditor.activateTool("Entity");
		botEditor.click(50, 50);
		SWTBotShell shell = bot.shell("Create a new entity");
		shell.activate();
		bot.text(0).setFocus();
		bot.text(0).setText("顧客番号");
		bot.radio(0).click();
		bot.button("OK").click();
		sleep();

		botEditor.activateTool("Entity");
		botEditor.click(300, 50);
		shell = bot.shell("Create a new entity");
		shell.activate();
		bot.text(0).setFocus();
		bot.text(0).setText("商品番号");
		bot.radio(0).click();
		bot.button("OK").click();
		sleep();

		sleep();

		// R:R 対照表
		botEditor.activateTool("Relationship");
		botEditor.click(55, 55);
		botEditor.click(305, 55);
		sleep();

		botEditor.activateTool("Entity");
		botEditor.click(50, 250);
		shell = bot.shell("Create a new entity");
		shell.activate();
		bot.text(0).setFocus();
		bot.text(0).setText("受注ID");
		bot.radio(1).click();
		bot.button("OK").click();
		sleep();

		botEditor.activateTool("Entity");
		botEditor.click(300, 250);
		shell = bot.shell("Create a new entity");
		shell.activate();
		bot.text(0).setFocus();
		bot.text(0).setText("請求ID");
		bot.radio(1).click();
		bot.button("OK").click();
		sleep();

		botEditor.activateTool("Entity");
		botEditor.click(500, 250);
		shell = bot.shell("Create a new entity");
		shell.activate();
		bot.text(0).setFocus();
		bot.text(0).setText("発送ID");
		bot.radio(1).click();
		bot.button("OK").click();
		sleep();

		// R:E
		botEditor.activateTool("Relationship");
		botEditor.click(305, 55);
		botEditor.click(55, 255);
		sleep();

		// E:E 対応表
		botEditor.activateTool("Relationship");
		botEditor.click(55, 255);
		botEditor.click(305, 255);
		sleep();
		shell = bot.shell("Edit relationship");
		shell.activate();
		bot.radio("N", 0).click();
		bot.radio("N", 1).click();
		bot.button("OK").click();
		sleep();

		// E:E
		botEditor.activateTool("Relationship");
		botEditor.click(305, 255);
		botEditor.click(505, 255);
		sleep();
		shell = bot.shell("Edit relationship");
		shell.activate();
		bot.checkBox("No relationship", 0).select();
		bot.checkBox("No relationship", 1).select();
		bot.button("OK").click();
		sleep();

		// 再帰
		botEditor.activateTool("Relationship");
		botEditor.click(305, 255);
		botEditor.click(305, 255);
		sleep();

		// サブセット
		botEditor.activateTool("Select");
		botEditor.click(303, 55);
		// botEditor.clickContextMenu("Subset");
		botEditor.clickContextMenu("Create subset");
		shell = bot.shell("Edit subset");
		shell.activate();
		bot.radio("Hometype").click();
		bot.button("Add").click();
		bot.button("Add").click();
		bot.button("OK").click();
		sleep();

		// 多値のAND
		botEditor.activateTool("Select");
		botEditor.click(505, 255);
		botEditor.clickContextMenu("Multivalue").clickContextMenu("Create multivalue AND(HDR-DTL)");
		sleep();

		// 多値のOR
		botEditor.activateTool("Select");
		botEditor.click(305, 55);
		botEditor.clickContextMenu("Multivalue").clickContextMenu("Create multivalue OR");
		shell = bot.shell("Create multivalue OR");
		shell.activate();
		bot.text(0).setText("商品種別");
		bot.button("OK").click();
		sleep();

		// みなしエンティティ
		botEditor.click(55, 55);
		botEditor.clickContextMenu("Virtual entity(TM')").clickContextMenu("Create virtual entity");
		shell = bot.shell("Create virtual entity");
		shell.activate();
		bot.text(0).setText("顧客住所");
		bot.button("OK").click();
		sleep();

		/* みなしスーパーセット */
		botEditor.click(20, 205);
		botEditor.clickContextMenu("Edit virtual superset");
		shell = bot.shell("Edit virtual superset");
		shell.activate();
		sleep();
		bot.textWithLabel("Virtual superset name").setText("すーぱーセット");
		bot.list(1).select(0);
		bot.button("<").click();
		bot.list(1).select(0);
		bot.button("<").click();
		bot.button("OK").click();
		sleep();

		Diagram diagram = tmdEditor.getRootModel();
		Entity e1 = (Entity) diagram.getChildren().get(0);
		assertEquals("顧客番号", e1.getIdentifier().getName());
		assertEquals("顧客", e1.getName());
		System.out.println(e1.getConstraint());

		Entity e2 = (Entity) diagram.getChildren().get(1);
		assertEquals("商品番号", e2.getIdentifier().getName());
		assertEquals("商品", e2.getName());
		System.out.println(e2.getConstraint().toString());

		System.out.println(diagram.getChildren().size());
		CombinationTable t1 = (CombinationTable) diagram.getChildren().get(2);
		assertEquals("顧客.商品.対照表", t1.getName());
		botEditor.close();
	}

	@Test
	public void testCreateNotEntity() {
		maximizeActiveWindow();
		maximizeActiveEditor();
		botEditor.activateTool("Entity");
		botEditor.click(50, 50);
		SWTBotShell shell = bot.shell("Create a new entity");
		shell.activate();
		bot.text(0).setFocus();
		bot.text(0).setText("顧客番号");
		bot.radio(0).click();
		bot.button("OK").click();
		sleep();

		// ターボファイル
		botEditor.activateTool("Turbo file");
		botEditor.click(350, 100);
		sleep();
		botEditor.activateTool("Relationship");
		botEditor.click(55, 55);
		botEditor.click(355, 105);
		sleep();

		// メモ
		botEditor.activateTool("Memo");
		botEditor.click(600, 90);
		sleep();

		// ラピュタ
		botEditor.activateTool("Entity");
		botEditor.click(150, 150);
		shell = bot.shell("Create a new entity");
		shell.activate();
		bot.text(0).setFocus();
		bot.radio(2).click();
		bot.button("OK").click();
		sleep();

		botEditor.activateTool("Entity");
		botEditor.click(250, 250);
		shell = bot.shell("Create a new entity");
		shell.activate();
		bot.text(0).setFocus();
		bot.text(0).setText("ラピュタ番号");
		bot.radio(2).click();
		bot.button("OK").click();
		sleep();
		botEditor.close();
	}

	@Test
	public void testAttributeDialog() {
		maximizeActiveWindow();
		maximizeActiveEditor();
		botEditor.activateTool("Entity");
		botEditor.click(50, 50);
		SWTBotShell shell = bot.shell("Create a new entity");
		shell.activate();
		bot.text(0).setFocus();
		bot.text(0).setText("顧客番号");
		bot.radio(0).click();

		bot.button("Description").click();
		bot.text(1).setText("implementName");
		bot.text(2).setText("summary");
		// data type
		bot.comboBox(0).setSelection(7);
		bot.text(3).setText("10");
		bot.text(4).setText("1");
		// auto increment
		bot.checkBox(0).click();
		// default value
		bot.text(5).setText("10");
		// prerequisite
		bot.text(6).setText("prerequisite");
		// confidenciality
		bot.text(7).setText("confidenciality");
		// formula
		bot.text(8).setText("formula");
		bot.button("OK").click();
		sleep();
		bot.button("OK").click();
		sleep();

		botEditor.doubleClick(55, 55);
		shell = bot.shell("Edit entity");
		shell.activate();
		SWTBotTable table = bot.table();
		table.select(0);
		sleep();
		assertNotEnabled(shell.bot().button("Up"));

		bot.button("Add").click();
		sleep();

		bot.button("Add").click();
		sleep();
		assertNotEnabled(shell.bot().button("Down"));

		bot.button("Up").click();
		sleep();
		assertEnabled(shell.bot().button("Up"));
		assertEnabled(shell.bot().button("Down"));

		bot.button("Remove").click();
		sleep();

		bot.button("OK").click();
		sleep();

		Diagram diagram = tmdEditor.getRootModel();
		List<AbstractEntityModel> list = diagram.findModelByName("顧客");
		assertEquals(1, list.size());
		AbstractEntityModel m = list.get(0);
		assertEquals(2, m.getAttributes().size());

		Identifier i = ((Entity) m).getIdentifier();
		assertEquals("顧客番号", i.getName());
		assertEquals("formula", i.getDerivationRule());

		botEditor.close();
	}

	private void sleep() {
		bot.sleep(300);
	}

	private void maximizeActiveWindow() {
		final Shell activeShell = bot.activeShell().widget;
		VoidResult maximizeShell = new VoidResult() {
			@Override
			public void run() {
				activeShell.setMaximized(true);
			}
		};
		UIThreadRunnable.syncExec(maximizeShell);
	}

	private void maximizeActiveEditor() {
		VoidResult maximizeShell = new VoidResult() {
			@Override
			public void run() {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
						.setPartState(botEditor.getReference(), IWorkbenchPage.STATE_MAXIMIZED);
			}
		};
		UIThreadRunnable.syncExec(maximizeShell);
	}

}
