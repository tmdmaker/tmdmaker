/*
 * Copyright 2009-2018 TMD-Maker Project <https://tmdmaker.osdn.jp/>
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
package jp.sourceforge.tmdmaker.dialog.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.ui.dialogs.models.EditAttribute;
import jp.sourceforge.tmdmaker.ui.dialogs.models.EditEntity;

/**
 * EditEntityのテスト.
 * 
 * @author nakag
 *
 */
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
