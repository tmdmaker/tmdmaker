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
import org.eclipse.swtbot.eclipse.gef.finder.SWTBotGefTestCase;
import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import jp.sourceforge.tmdmaker.TMDEditor;
import jp.sourceforge.tmdmaker.model.CombinationTable;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Entity;

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
			e.printStackTrace();
		}
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		project.createProject(PROJECT_NAME);
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
		botEditor.activateTool("エンティティ");
		assertEquals("エンティティ", getActiveToolLabel());

		botEditor.activateTool("リレーションシップ");
		assertEquals("リレーションシップ", getActiveToolLabel());

	}

	private String getActiveToolLabel() {
		return botEditor.getActiveTool().getLabel();
	}

	@Test
	public void testCreateResourceEntity() {
		botEditor.activateTool("エンティティ");
		botEditor.click(50, 50);
		SWTBotShell shell = bot.shell("エンティティ新規作成");
		shell.activate();
		bot.text(0).setFocus();
		bot.text(0).setText("顧客番号");
		bot.comboBox(0).setSelection(0);
		bot.button("OK").click();
		sleep();

		botEditor.activateTool("エンティティ");
		botEditor.click(300, 50);
		shell = bot.shell("エンティティ新規作成");
		shell.activate();
		bot.text(0).setFocus();
		bot.text(0).setText("商品番号");
		bot.comboBox(0).setSelection(0);
		bot.button("OK").click();
		sleep();

		sleep();

		// R:R 対照表
		botEditor.activateTool("リレーションシップ");
		botEditor.click(55, 55);
		botEditor.click(305, 55);
		sleep();

		botEditor.activateTool("エンティティ");
		botEditor.click(50, 250);
		shell = bot.shell("エンティティ新規作成");
		shell.activate();
		bot.text(0).setFocus();
		bot.text(0).setText("受注ID");
		bot.comboBox(0).setSelection(1);
		bot.button("OK").click();
		sleep();

		botEditor.activateTool("エンティティ");
		botEditor.click(300, 250);
		shell = bot.shell("エンティティ新規作成");
		shell.activate();
		bot.text(0).setFocus();
		bot.text(0).setText("請求ID");
		bot.comboBox(0).setSelection(1);
		bot.button("OK").click();
		sleep();

		botEditor.activateTool("エンティティ");
		botEditor.click(500, 250);
		shell = bot.shell("エンティティ新規作成");
		shell.activate();
		bot.text(0).setFocus();
		bot.text(0).setText("発送ID");
		bot.comboBox(0).setSelection(1);
		bot.button("OK").click();
		sleep();

		// R:E
		botEditor.activateTool("リレーションシップ");
		botEditor.click(305, 55);
		botEditor.click(55, 255);
		sleep();

		// E:E 対応表
		botEditor.activateTool("リレーションシップ");
		botEditor.click(55, 255);
		botEditor.click(305, 255);
		sleep();
		shell = bot.shell("Relationship編集");
		shell.activate();
		bot.comboBox(0).setSelection(1);
		bot.button("OK").click();
		sleep();

		// E:E
		botEditor.activateTool("リレーションシップ");
		botEditor.click(305, 255);
		botEditor.click(505, 255);
		sleep();
		shell = bot.shell("Relationship編集");
		shell.activate();
		bot.button("OK").click();
		sleep();

		// 再帰
		botEditor.activateTool("リレーションシップ");
		botEditor.click(305, 255);
		botEditor.click(305, 255);
		sleep();

		// サブセット
		botEditor.activateTool("Select");
		botEditor.click(303, 55);
		botEditor.clickContextMenu("サブセット");
		botEditor.clickContextMenu("サブセット作成");
		shell = bot.shell("サブセット編集");
		shell.activate();
		bot.radio("同一").click();
		bot.button("新規").click();
		bot.button("新規").click();
		bot.button("OK").click();
		sleep();

		// 多値のAND
		botEditor.activateTool("Select");
		botEditor.click(505, 255);
		botEditor.clickContextMenu("データ の多値").clickContextMenu("多値のAND(HDR-DTL)作成");
		sleep();

		// 多値のOR
		botEditor.activateTool("Select");
		botEditor.click(305, 55);
		botEditor.clickContextMenu("データ の多値").clickContextMenu("多値のOR作成");
		shell = bot.shell("MO作成");
		shell.activate();
		bot.text(0).setText("商品種別");
		bot.button("OK").click();
		sleep();

		// みなしエンティティ
		botEditor.click(55, 55);
		botEditor.clickContextMenu("みなし概念(TM')").clickContextMenu("みなしEntity作成");
		shell = bot.shell("みなしエンティティ作成");
		shell.activate();
		bot.text(0).setText("顧客住所");
		bot.button("OK").click();
		sleep();

		// みなしスーパーセット
		botEditor.click(20, 205);
		botEditor.clickContextMenu("みなし概念(TM')").clickContextMenu("みなしSuperset編集");
		shell = bot.shell("スーパーセット編集");
		shell.activate();
		sleep();
		bot.textWithLabel("みなしスーパーセット名").setText("すーぱーセット");
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

	private void sleep() {
		bot.sleep(300);
	}
}
