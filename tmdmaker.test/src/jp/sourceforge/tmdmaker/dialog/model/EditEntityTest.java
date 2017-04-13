package jp.sourceforge.tmdmaker.dialog.model;

import static org.junit.Assert.*;

import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.EntityType;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.rule.EntityRecognitionRule;
import jp.sourceforge.tmdmaker.ui.dialogs.models.EditAttribute;
import jp.sourceforge.tmdmaker.ui.dialogs.models.EditEntity;

import org.junit.Test;

public class EditEntityTest {
	private EditEntity target;

	@Test
	public void testUpdateTypeAttribute() {
		Entity entity = EntityRecognitionRule.getInstance().createEntity("テスト",
				new Identifier("テスト番号"), EntityType.RESOURCE);
		target = new EditEntity(entity);

		target.updateTypeAttribute("テスト", "テスト１");
		EditAttribute ea = target.getAttributes().get(0);
		assertEquals("テスト１名称", ea.getName());

		entity = EntityRecognitionRule.getInstance().createEntity("テスト",
				new Identifier("テスト番号"), EntityType.EVENT);
		target = new EditEntity(entity);

		target.updateTypeAttribute("テスト", "テスト１");
		ea = target.getAttributes().get(0);
		assertEquals("テスト１日", ea.getName());

	}
}
