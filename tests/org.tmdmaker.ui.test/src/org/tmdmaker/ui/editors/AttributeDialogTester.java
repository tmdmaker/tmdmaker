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
package org.tmdmaker.ui.editors;

import static org.eclipse.swtbot.swt.finder.SWTBotAssert.assertEnabled;
import static org.eclipse.swtbot.swt.finder.SWTBotAssert.assertNotEnabled;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.Diagram;
import org.tmdmaker.core.model.Entity;
import org.tmdmaker.core.model.Identifier;
import org.tmdmaker.core.model.parts.ModelName;
import org.tmdmaker.ui.test.AbstractTester;

/**
 * Test for Attribute Dialog.
 * 
 * @author nakag
 *
 */
public class AttributeDialogTester extends AbstractTester {

	public AttributeDialogTester(SWTGefBot bot, SWTBotGefEditor botEditor) {
		super(bot, botEditor);
	}

	@Override
	protected void doTest() {
		botEditor.doubleClick(55, 55);
		SWTBotShell shell = bot.shell("Edit entity");
		shell.activate();
		SWTBotTable table = bot.table();
		table.select(0);
		wait.waitDefault();
		assertNotEnabled(shell.bot().button("Up"));

		bot.button("Add").click();
		wait.waitDefault();

		bot.button("Add").click();
		wait.waitDefault();

		assertNotEnabled(shell.bot().button("Down"));

		bot.button("Up").click();
		wait.waitDefault();
		assertEnabled(shell.bot().button("Up"));
		assertEnabled(shell.bot().button("Down"));

		bot.button("Remove").click();
		wait.waitDefault();
		bot.button("Description", 1).click();
		fillAttributeValues(bot);
		wait.waitDefault();

		bot.button("OK").click();
		wait.waitDefault();

		Diagram diagram = tmdEditor.getRootModel();
		List<AbstractEntityModel> list = diagram.query().name(new ModelName("顧客")).listEntityModel();
		assertEquals(1, list.size());
		AbstractEntityModel m = list.get(0);
		assertEquals(2, m.getAttributes().size());

		Identifier i = ((Entity) m).getIdentifier();
		assertEquals("顧客番号", i.getName());
		assertEquals("formula", i.getDerivationRule());

	}

	public static void fillAttributeValues(SWTGefBot bot) {
		bot.text(1).setText("implementName1");
		bot.text(2).setText("summary");
		// data type
		bot.comboBox(0).setSelection(7);
		bot.text(3).setText("10");
		bot.text(4).setText("1");
		// auto increment
		bot.checkBox(0).click();
		bot.checkBox(0).deselect();
		// default value
		bot.text(5).setText("10");
		// prerequisite
		bot.text(6).setText("prerequisite");
		// confidenciality
		bot.text(7).setText("confidenciality");
		// formula
		bot.text(8).setText("formula");
		bot.button("OK").click();
	}
}
