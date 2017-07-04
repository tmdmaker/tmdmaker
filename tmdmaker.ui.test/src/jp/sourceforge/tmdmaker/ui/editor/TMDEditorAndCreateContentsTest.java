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
package jp.sourceforge.tmdmaker.ui.editor;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.eclipse.gef.finder.SWTBotGefTestCase;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.keyboard.Keyboard;
import org.eclipse.swtbot.swt.finder.keyboard.KeyboardFactory;
import org.eclipse.swtbot.swt.finder.keyboard.Keystrokes;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
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
import jp.sourceforge.tmdmaker.ui.test.BotWait;
import jp.sourceforge.tmdmaker.ui.test.CreateDiagram;
import jp.sourceforge.tmdmaker.ui.test.NewEmptyProject;
import jp.sourceforge.tmdmaker.ui.test.View;

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
	private View propertiesView = new View("General", "Properties");
	private View outlineView = new View("General", "Outline");
	private SWTBotGefEditor botEditor;
	private TMDEditor tmdEditor;
	private BotWait wait = new BotWait();
	private static final String PROJECT_NAME = "test";
	private static final String FILE_NAME = "test.tmd";
	private static final int RADIO_INDEX_RESOURCE = 0;
	private static final int RADIO_INDEX_EVENT = 1;
	private static final int RADIO_INDEX_LAPUTA = 2;

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
		propertiesView.open();
		outlineView.open();
		maximizeActiveWindow();
		maximizeActiveEditor();

		createEntity(50, 50, "顧客番号", RADIO_INDEX_RESOURCE);

		createEntity(300, 50, "商品番号", RADIO_INDEX_RESOURCE);

		// R:R 対照表
		botEditor.activateTool("Relationship");
		botEditor.click(55, 55);
		botEditor.click(305, 55);
		sleep();

		createEntity(50, 250, "受注ID", RADIO_INDEX_EVENT);

		createEntity(300, 250, "請求ID", RADIO_INDEX_EVENT);

		createEntity(500, 250, "発送ID", RADIO_INDEX_EVENT);

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
		SWTBotShell shell = bot.shell("Edit relationship");
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

		Entity e2 = (Entity) diagram.getChildren().get(1);
		assertEquals("商品番号", e2.getIdentifier().getName());
		assertEquals("商品", e2.getName());

		CombinationTable t1 = (CombinationTable) diagram.getChildren().get(2);
		assertEquals("顧客.商品.対照表", t1.getName());
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

	@Test
	public void testCreateNotEntity() {
		propertiesView.open();
		outlineView.open();
		maximizeActiveWindow();
		maximizeActiveEditor();

		createEntity(50, 50, "顧客番号", RADIO_INDEX_RESOURCE);
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
		createEntity(150, 150, "", RADIO_INDEX_LAPUTA);
		createEntity(250, 250, "ラピュタ番号", RADIO_INDEX_LAPUTA);
	}

	@Test
	public void testAttributeDialog() {
		propertiesView.open();
		outlineView.open();

		botEditor.activateTool("Entity");
		botEditor.click(50, 50);
		SWTBotShell shell = bot.shell("Create a new entity");
		shell.activate();
		bot.text(0).setFocus();
		bot.text(0).setText("顧客番号");
		bot.radio(0).click();

		bot.button("Description").click();
		fillAttributeValues();

		bot.button("OK").click();
		sleep();
		botEditor.click(75, 95);

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
		bot.button("Description", 1).click();
		fillAttributeValues();

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
	}

	private void fillAttributeValues() {
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
	}

	@Test
	public void testMove() throws Exception {
		botEditor.setFocus();
		createEntity(50, 50, "顧客番号", RADIO_INDEX_RESOURCE);
		botEditor.activateTool("Select");

		botEditor.click(55, 55);

		botEditor.drag(55, 55, 100, 100);
		sleep();
		botEditor.getEditPart("顧客").select();
		botEditor.activateTool("Select");

		SWTBotPreferences.KEYBOARD_LAYOUT = "EN_US";
		Keyboard keyboard = KeyboardFactory.getSWTKeyboard();
		keyboard.pressShortcut(Keystrokes.LEFT);
		keyboard.pressShortcut(Keystrokes.LEFT);
		keyboard.pressShortcut(Keystrokes.DOWN);
		keyboard.pressShortcut(Keystrokes.DOWN);
		keyboard.pressShortcut(Keystrokes.RIGHT);
		keyboard.pressShortcut(Keystrokes.RIGHT);
		keyboard.pressShortcut(Keystrokes.UP);
		keyboard.pressShortcut(Keystrokes.UP);

		sleep();
	}

	@Test
	public void testPreferences() {
		botEditor.setFocus();
		createEntity(50, 50, "顧客番号", RADIO_INDEX_RESOURCE);
		sleep();
		botEditor.select("顧客");
		assertEquals(1, botEditor.getEditPart("顧客").children().size());

		try {
			bot.menu("Window").menu("Preferences").click();
		} catch (Exception e) {
			openPreferenceForMac();
		}
		bot.tree().getTreeItem("TMD-Maker").select();
		bot.tree().getTreeItem("TMD-Maker").expand();
		bot.tree().getTreeItem("TMD-Maker").getNode("Appearance").select();
		bot.tree().getTreeItem("TMD-Maker").getNode("Rule").select();
		bot.tree().getTreeItem("TMD-Maker").getNode("Appearance").select();
		SWTBotShell shell = bot.shell("Preferences");
		shell.activate();
		bot.checkBox(0).click();
		bot.tree().getTreeItem("TMD-Maker").getNode("Appearance").expand();
		bot.tree().getTreeItem("TMD-Maker").getNode("Appearance").getNode("Color Appearance")
				.select();
		bot.tree().getTreeItem("TMD-Maker").getNode("Appearance").getNode("Font Appearance")
				.select();
		try {
			bot.button("Apply and Close").click();
		} catch (WidgetNotFoundException e) {
			bot.button("OK").click();
		}
	}

	private void sleep() {
		wait.waitDefault();
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

	private void openPreferenceForMac() {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		workbench.getDisplay().asyncExec(new Runnable() {
			public void run() {
				IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
				if (window != null) {
					Menu appMenu = workbench.getDisplay().getSystemMenu();
					for (MenuItem item : appMenu.getItems()) {
						if (item.getText().startsWith("Preferences")) {
							Event event = new Event();
							event.time = (int) System.currentTimeMillis();
							event.widget = item;
							event.display = workbench.getDisplay();
							item.setSelection(true);
							item.notifyListeners(SWT.Selection, event);
							break;
						}
					}
				}
			}
		});
	}
}
