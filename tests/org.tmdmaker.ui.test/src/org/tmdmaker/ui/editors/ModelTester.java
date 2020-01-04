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

import static org.junit.Assert.assertEquals;

import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.tmdmaker.core.model.CombinationTable;
import org.tmdmaker.core.model.Diagram;
import org.tmdmaker.core.model.Entity;
import org.tmdmaker.ui.test.AbstractTester;

/**
 * Test for Model build by TMEditor.
 * 
 * @author nakag
 *
 */
public class ModelTester extends AbstractTester {

	public ModelTester(SWTGefBot bot, SWTBotGefEditor botEditor) {
		super(bot, botEditor);
	}

	@Override
	protected void doTest() {
		Diagram diagram = tmdEditor.getRootModel();
		Entity e1 = diagram.query().listEntityModel(Entity.class).get(0);
		assertEquals("顧客番号", e1.getIdentifier().getName());
		assertEquals("顧客", e1.getName());

		Entity e2 = diagram.query().listEntityModel(Entity.class).get(1);
		assertEquals("商品番号", e2.getIdentifier().getName());
		assertEquals("商品", e2.getName());

		CombinationTable t1 = diagram.query().listEntityModel(CombinationTable.class).get(0);
		assertEquals("顧客.商品.対照表", t1.getName());


	}

}
