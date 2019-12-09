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
package org.tmdmaker.core.model;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.tmdmaker.core.model.Diagram;
import org.tmdmaker.core.model.Entity;
import org.tmdmaker.core.model.Identifier;
import org.tmdmaker.core.model.VirtualEntity;
import org.tmdmaker.core.model.parts.ModelName;
import org.tmdmaker.core.model.virtual.VirtualEntityBuilder;

/**
 * みなしエンティティのテストクラス
 * 
 * @author nakag
 *
 */
public class VirtualEntityTest {
	@Test
	public void testCreateVE() {
		Diagram diagram = new Diagram();
		Entity e1 = Entity.ofEvent(new Identifier("テスト1番号")).withDefaultAttribute();
		diagram.addChild(e1);

		VirtualEntityBuilder builder = e1.virtualEntities().builder();
		ModelName veName = new ModelName("テストVE");
		builder.virtualEntityName(veName);
		builder.build();
		List<VirtualEntity> results = e1.virtualEntities().query().findByName(veName);
		assertEquals(1, results.size());

		builder.rollback();
		results = e1.virtualEntities().query().findByName(veName);
		assertEquals(0, results.size());
	}
}
