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
package org.tmdmaker.ui.test;

import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.tmdmaker.ui.editors.AttributeDialogTester;
import org.tmdmaker.ui.editors.ImplementInfoDialogTester;
import org.tmdmaker.ui.editors.ModelTester;
import org.tmdmaker.ui.editors.MoveTester;
import org.tmdmaker.ui.editors.ToolTester;
import org.tmdmaker.ui.editors.generators.CSVGeneratorTester;
import org.tmdmaker.ui.editors.generators.DDLGeneratorTester;
import org.tmdmaker.ui.editors.generators.HtmlGeneratorTester;
import org.tmdmaker.ui.editors.generators.ImageGeneratorTester;
import org.tmdmaker.ui.editors.generators.SphinxGeneratorTester;
import org.tmdmaker.ui.preferences.PreferencesTester;
import org.tmdmaker.ui.views.OutlineViewTester;
import org.tmdmaker.ui.views.PropertyViewTester;

@RunWith(SWTBotJunit4ClassRunner.class)
public class DiagramTest extends AbstractUITest {
	private static final int RADIO_INDEX_RESOURCE = 0;
	private static final int RADIO_INDEX_EVENT = 1;
	private static final int RADIO_INDEX_LAPUTA = 2;

	@Test
	public void testTMModels() {
		propertiesView.open();
		outlineView.open();
		maximizeActiveWindow();
		maximizeActiveEditor();

		editDiagrams();

		hookTMModelTests();
	}
	
	private void hookTMModelTests() {
		normalizeActiveEditor();

		ModelTester model = new ModelTester(bot, botEditor);
		model.test();

		ToolTester tool = new ToolTester(bot, botEditor);
		tool.test();

		PropertyViewTester propertyView = new PropertyViewTester(bot, botEditor);
		propertyView.test();

		OutlineViewTester outlineView = new OutlineViewTester(bot, botEditor);
		outlineView.test();

		AttributeDialogTester editAttributeDialog = new AttributeDialogTester(bot, botEditor);
		editAttributeDialog.test();

		ImplementInfoDialogTester implementInfoDialog = new ImplementInfoDialogTester(bot, botEditor);
		implementInfoDialog.test();

		PreferencesTester preferences = new PreferencesTester(bot, botEditor);
		preferences.test();
		
		MoveTester move = new MoveTester(bot, botEditor);
		move.test();
		
		CSVGeneratorTester csvGenerator = new CSVGeneratorTester(bot, botEditor);
		csvGenerator.test();
		
		DDLGeneratorTester ddlGenerator = new DDLGeneratorTester(bot, botEditor);
		ddlGenerator.test();
		
		HtmlGeneratorTester htmlGenerator = new HtmlGeneratorTester(bot, botEditor);
		htmlGenerator.test();
		
		ImageGeneratorTester imageGenerator = new ImageGeneratorTester(bot, botEditor);
		imageGenerator.test();
		
		SphinxGeneratorTester sphinxGenerator = new SphinxGeneratorTester(bot, botEditor);
		sphinxGenerator.test();
	}

	protected void editDiagrams() {
		botEditor.activateTool("Entity");
		botEditor.click(50, 50);
		SWTBotShell shell = bot.shell("Create a new entity");
		shell.activate();
		bot.text(0).setFocus();
		bot.text(0).setText("顧客番号");
		bot.radio(0).click();
		bot.button("Description").click();
		AttributeDialogTester.fillAttributeValues(bot);
		bot.checkBox(0).click();
		bot.checkBox(0).deselect();
		sleep();
		bot.button("OK").click();

		botEditor.getEditPart("顧客").select().click();
		botEditor.clickContextMenu("Edit implementation information");
		shell = bot.shell("Edit implementation information");
		shell.activate();
		bot.text(0).setText("impl");
		wait.waitDefault();
		bot.tabItem(1).activate();
		bot.button("Add").click();
		wait.waitDefault();		
		shell = bot.shell("Edit keys");
		shell.activate();
		bot.button("<<").click();
		bot.button("OK").click();
		bot.tabItem(0).activate();
		wait.waitDefault();
		bot.button("OK").click();
		wait.waitDefault();

		createEntity(300, 50, "商品番号", RADIO_INDEX_RESOURCE);

		// R:R 対照表
		botEditor.activateTool("Relationship");
		botEditor.click(55, 55);
		botEditor.click(305, 55);
		sleep();

		createEntity(50, 250, "受注ID", RADIO_INDEX_EVENT);
		botEditor.click(55, 255);

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

		createEntity(20, 410, "問合せ番号", RADIO_INDEX_RESOURCE);
		sleep();
	}


	@Test
	public void testNotTMModel() {
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

		normalizeActiveEditor();
		sleep();
	}

}
