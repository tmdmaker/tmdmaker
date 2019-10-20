package org.tmdmaker.ui.editors;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.ui.dialogs.models.EditAttribute;
import jp.sourceforge.tmdmaker.ui.dialogs.models.EditEntity;

public class EditEntityTest {
	private EditEntity target;

	@Test
	public void testUpdateTypeAttribute() {
		Entity entity = Entity.ofResource(new Identifier("テスト番号")).withDefaultAttribute();
		target = new EditEntity(entity);

		target.updateTypeAttribute("テスト", "テスト１");
		EditAttribute ea = target.getAttributes().get(0);
		assertEquals("テスト１名称", ea.getName());

		entity = Entity.ofEvent(new Identifier("テスト番号")).withDefaultAttribute();

		target = new EditEntity(entity);

		target.updateTypeAttribute("テスト", "テスト１");
		ea = target.getAttributes().get(0);
		assertEquals("テスト１日", ea.getName());

	}
}
