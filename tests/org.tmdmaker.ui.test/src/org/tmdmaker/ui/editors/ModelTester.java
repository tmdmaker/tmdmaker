package org.tmdmaker.ui.editors;

import static org.junit.Assert.assertEquals;

import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.tmdmaker.ui.test.AbstractTester;

import jp.sourceforge.tmdmaker.model.CombinationTable;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Entity;

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
