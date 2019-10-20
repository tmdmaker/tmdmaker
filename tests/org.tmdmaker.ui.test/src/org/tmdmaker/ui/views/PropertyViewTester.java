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
		testDiagramProperty();
		wait.waitDefault();

		botEditor.getEditPart("顧客.商品.対照表").select().click();
		testCombinationTableProperty();
		wait.waitDefault();

		botEditor.getEditPart("問合せ").select().click();
		testEntityProperty();
		wait.waitDefault();

		Window w = new Window(bot, botEditor);
		w.maximizeActiveEditor();
		botEditor.getEditPart("発送DTL").select().click();
		w.normalizeActiveEditor();
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
		testComboCell(item, 1);

	}

	private void testDiagramProperty() {
		SWTBotTree tree = getPropertyTree();
		SWTBotTreeItem item = tree.getTreeItem("Name");
		item.click(1);
		Text twidget = bot.widget(widgetOfType(Text.class));
		SWTBotText text = new SWTBotText(twidget, null);
		text.setText("testname");
		item.click(0);
		assertEquals("testname", item.cell(1));

		item = tree.getTreeItem("Database name");
		testComboCell(item, 1);

		item = tree.getTreeItem("Description");
		item.click(1);
		twidget = bot.widget(widgetOfType(Text.class));
		text = new SWTBotText(twidget, null);
		text.setText("testdesc");
		item.click(0);
		assertEquals("testdesc", item.cell(1));
	}

	private void testEntityProperty() {
		SWTBotTree tree = getPropertyTree();
		SWTBotTreeItem item = tree.getTreeItem("Name");
		item.click(1);
		Text twidget = bot.widget(widgetOfType(Text.class));
		SWTBotText text = new SWTBotText(twidget, null);
		text.setText("問合せ1");
		item.click(0);
		assertEquals("問合せ1", item.cell(1));

		item = tree.getTreeItem("Implementation name");
		item.click(1);
		twidget = bot.widget(widgetOfType(Text.class));
		text = new SWTBotText(twidget, null);
		text.setText("customer1");
		item.click(0);
		assertEquals("customer1", item.cell(1));

		item = tree.getTreeItem("Identifier");
		item.click(1);
		twidget = bot.widget(widgetOfType(Text.class));
		text = new SWTBotText(twidget, null);
		text.setText("問合せ1コード");
		item.click(0);
		assertEquals("問合せ1コード", item.cell(1));

		item = tree.getTreeItem("Entity type");
		item.click(1);
		CCombo cwidget = bot.widget(widgetOfType(CCombo.class));
		SWTBotCCombo ccombo = new SWTBotCCombo(cwidget, null);
		ccombo.setSelection(1);
		item.click(0);
		assertEquals("Event", item.cell(1));
		item.click(1);
		ccombo.setSelection(0);
		item.click(0);

		item = tree.getTreeItem("Implementation");
		item.click(1);
		cwidget = bot.widget(widgetOfType(CCombo.class));
		ccombo = new SWTBotCCombo(cwidget, null);
		ccombo.setSelection(1);
		item.click(0);
		assertEquals("Not to implement", item.cell(1));

	}

	private void testCombinationTableProperty() {
		SWTBotTree tree = getPropertyTree();
		SWTBotTreeItem item = tree.getTreeItem("Name");
		item.click(1);
		Text twidget = bot.widget(widgetOfType(Text.class));
		SWTBotText text = new SWTBotText(twidget, null);
		text.setText("テスト対照表");
		item.click(0);
		assertEquals("テスト対照表", item.cell(1));

		item = tree.getTreeItem("Implementation name");
		item.click(1);
		twidget = bot.widget(widgetOfType(Text.class));
		text = new SWTBotText(twidget, null);
		text.setText("combination1");
		item.click(0);
		assertEquals("combination1", item.cell(1));

		item = tree.getTreeItem("Implementation");
		item.click(1);
		CCombo cwidget = bot.widget(widgetOfType(CCombo.class));
		SWTBotCCombo ccombo = new SWTBotCCombo(cwidget, null);
		ccombo.setSelection(1);
		item.click(0);
		assertEquals("Not to implement", item.cell(1));

	}

	private void testDetailProperty() {
		SWTBotTree tree = getPropertyTree();
		SWTBotTreeItem item = tree.getTreeItem("Name");
		item.click(1);
		Text twidget = bot.widget(widgetOfType(Text.class));
		SWTBotText text = new SWTBotText(twidget, null);
		text.setText("発送1");
		item.click(0);
		assertEquals("発送1", item.cell(1));

		item = tree.getTreeItem("Implementation name");
		item.click(1);
		twidget = bot.widget(widgetOfType(Text.class));
		text = new SWTBotText(twidget, null);
		text.setText("ship1");
		item.click(0);
		assertEquals("ship1", item.cell(1));

		item = tree.getTreeItem("Identifier");
		item.click(1);
		twidget = bot.widget(widgetOfType(Text.class));
		text = new SWTBotText(twidget, null);
		text.setText("発送1コード");
		item.click(0);
		assertEquals("発送1コード", item.cell(1));

		item = tree.getTreeItem("Implementation");
		item.click(1);
		CCombo cwidget = bot.widget(widgetOfType(CCombo.class));
		SWTBotCCombo ccombo = new SWTBotCCombo(cwidget, null);
		ccombo.setSelection(1);
		item.click(0);
		assertEquals("Not to implement", item.cell(1));

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
		Text twidget = bot.widget(widgetOfType(Text.class));
		SWTBotText text = new SWTBotText(twidget, null);
		text.setText(testValue);
		item.click(0);
		assertEquals(testValue, item.cell(1));
	}

	private void testComboCell(SWTBotTreeItem item, int testIndex) {
		item.click(1);
		CCombo cwidget = bot.widget(widgetOfType(CCombo.class));
		SWTBotCCombo ccombo = new SWTBotCCombo(cwidget, null);
		ccombo.setSelection(testIndex);
		item.click(0);
		item.click(1);
		cwidget = bot.widget(widgetOfType(CCombo.class));
		ccombo = new SWTBotCCombo(cwidget, null);
		assertEquals(testIndex, ccombo.selectionIndex());
	}

}
