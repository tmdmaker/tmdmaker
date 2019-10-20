/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org/>
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
package org.tmdmaker.ui.views;

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.widgetOfType;
import static org.junit.Assert.assertEquals;

import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCCombo;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.tmdmaker.ui.test.AbstractTester;
import org.tmdmaker.ui.test.Window;

/**
 * Test for TMD-Maker's PropertyView
 *
 * @author nakag
 *
 */
public class PropertyViewTester extends AbstractTester{

	public PropertyViewTester(SWTGefBot bot, SWTBotGefEditor botEditor) {
		super(bot, botEditor);
	}

	@Override
	protected void doTest() {
		botEditor.setFocus();
		botEditor.click(10, 10);
		wait.waitDefault();
		testDiagramProperty();
		wait.waitDefault();
		Window w = new Window(bot, botEditor);

		w.maximizeActiveEditor();
		wait.waitDefault();
		botEditor.getEditPart("顧客.商品.対照表").select().click();
		w.normalizeActiveEditor();
		wait.waitDefault();
		testCombinationTableProperty();
		wait.waitDefault();

		w.maximizeActiveEditor();
		wait.waitDefault();
		botEditor.getEditPart("問合せ").select().click();
		w.normalizeActiveEditor();
		wait.waitDefault();
		testEntityProperty();
		wait.waitDefault();

		w.maximizeActiveEditor();
		wait.waitDefault();
		botEditor.getEditPart("発送DTL").click();
		w.normalizeActiveEditor();
		wait.waitDefault();
		testDetailProperty();
		wait.waitDefault();

		botEditor.getEditPart("顧客名称").select().click();
		testAttributeProperty();

		SWTBotView outlineBot = bot.viewByTitle("Outline");
		SWTBotTree viewTree = outlineBot.bot().tree();
		SWTBotTreeItem[] keyItems = viewTree.expandNode("顧客").expandNode("Key definitions")
				.getItems();
		keyItems[0].select().click();
		testKeyProperty();
	}

	private void testKeyProperty() {
		SWTBotTree tree = getPropertyTree();
		SWTBotTreeItem item = tree.getTreeItem("Name");
		testTextCell(item, "testkey");

		item = tree.getTreeItem("Unique constraints");
		testComboCell(item, 1);
		testComboCell(item, 0);
		
		item = tree.getTreeItem("Master key");
		testComboCell(item, 1);
		item = tree.getTreeItem("Unique constraints");
		testComboCell(item, 0);

		testComboCell(tree.getTreeItem("Unique constraints"), 0);
	}

	private void testDiagramProperty() {
		SWTBotTree tree = getPropertyTree();
		SWTBotTreeItem item = tree.getTreeItem("Name");
		testTextCell(item, "testname");

		item = tree.getTreeItem("Database name");
		testComboCell(item, 1);

		item = tree.getTreeItem("Description");
		testTextCell(item, "testdesc");
	}

	private void testEntityProperty() {
		SWTBotTree tree = getPropertyTree();
		SWTBotTreeItem item = tree.getTreeItem("Name");
		testTextCell(item, "問合せ1");

		item = tree.getTreeItem("Implementation name");
		testTextCell(item, "customer1");

		item = tree.getTreeItem("Identifier");
		testTextCell(item, "問合せ1コード");

		item = tree.getTreeItem("Entity type");
		testComboCell(item, 1);
		testComboCell(item, 0);

		item = tree.getTreeItem("Implementation");
		testComboCell(item, 1);
	}

	private void testCombinationTableProperty() {
		SWTBotTree tree = getPropertyTree();
		SWTBotTreeItem item = tree.getTreeItem("Name");
		testTextCell(item, "テスト対照表");

		item = tree.getTreeItem("Implementation name");
		testTextCell(item, "combination1");

		item = tree.getTreeItem("Implementation");
		testComboCell(item, 1);
	}

	private void testDetailProperty() {
		SWTBotTree tree = getPropertyTree();
		SWTBotTreeItem item = tree.getTreeItem("Name");
		testTextCell(item, "発送DTL1");

		item = tree.getTreeItem("Implementation name");
		testTextCell(item, "ship1");
		wait.waitDefault();
		
		item = tree.getTreeItem("Identifier");
		testTextCell(item, "発送DTL1コード");
		wait.waitDefault();

		item = tree.getTreeItem("Implementation");
		testComboCell(item, 1);
	}

	protected SWTBotTree getPropertyTree() {
		SWTBotTree tree = bot.viewByTitle("Properties").bot().tree(0);
		return tree;
	}

	private void testAttributeProperty() {
		SWTBotTree tree = getPropertyTree();
		SWTBotTreeItem item = tree.getTreeItem("Name");
		testTextCell(item, "テスト名称");

		item = tree.getTreeItem("Implementation name");
		testTextCell(item, "test_name");

		item = tree.getTreeItem("Data type");
		testComboCell(item, 4);

		item = tree.getTreeItem("Size");
		testTextCell(item, "10");

		item = tree.getTreeItem("Scale");
		testTextCell(item, "10");
	}

	private void testTextCell(SWTBotTreeItem item, String testValue) {
		item.click(1);
		wait.waitDefault();
		Text twidget = bot.widget(widgetOfType(Text.class));
		SWTBotText text = new SWTBotText(twidget, null);
		text.setText(testValue);
		item.click(0);
		assertEquals(testValue, item.cell(1));
	}

	private void testComboCell(SWTBotTreeItem item, int testIndex) {
		item.click(1);
		wait.waitDefault();
		CCombo cwidget = bot.widget(widgetOfType(CCombo.class));
		SWTBotCCombo ccombo = new SWTBotCCombo(cwidget, null);
		ccombo.setSelection(testIndex);
		item.click(0);
		wait.waitDefault();
		item.click(1);
		wait.waitDefault();
		cwidget = bot.widget(widgetOfType(CCombo.class));
		ccombo = new SWTBotCCombo(cwidget, null);
		assertEquals(testIndex, ccombo.selectionIndex());
	}

}
