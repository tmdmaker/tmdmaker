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
package org.tmdmaker.core.model.generate.attributelist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;
import org.tmdmaker.core.model.Diagram;
import org.tmdmaker.core.model.Entity;
import org.tmdmaker.core.model.Header2DetailRelationship;
import org.tmdmaker.core.model.Identifier;
import org.tmdmaker.core.model.generate.attributelist.AttributeListModelBuilder;
import org.tmdmaker.core.model.generate.attributelist.EntityAttributePair;
import org.tmdmaker.core.model.parts.ModelName;

/**
 * アトリビュートリストのモデルテスト.
 * 
 * @author nakag
 *
 */
public class AttributeListModelBuilderTest {

	@Test
	public void test() {
		Diagram diagram = new Diagram();
		Entity e1 = Entity.ofEvent(new ModelName("テスト1"), new Identifier("テスト1番号"))
				.withDefaultAttribute();
		diagram.addChild(e1);

		Entity e2 = Entity.ofEvent(new ModelName("テスト2"), new Identifier("テスト2番号"))
				.withDefaultAttribute();
		diagram.addChild(e2);

		Map<String, EntityAttributePair> results = new AttributeListModelBuilder()
				.build(diagram.query().listEntityModel());

		assertEquals(results.keySet().size(), 4);
		assertTrue(results.containsKey("テスト1番号_テスト1"));
		EntityAttributePair pair = results.values().iterator().next();
		assertEquals(pair.getAttribute().getName(), "テスト1日");
		assertEquals("テスト1", pair.getModel().getName());
	}

	@Test
	public void testDetail() {
		Diagram diagram = new Diagram();
		Entity e1 = Entity.ofEvent(new Identifier("テスト1番号")).withDefaultAttribute();
		diagram.addChild(e1);
		diagram.addChild(Entity.ofResource(new Identifier("テスト2番号")).withDefaultAttribute());
		diagram.addChild(Entity.ofEvent(new Identifier("テスト3番号")).withDefaultAttribute());

		Header2DetailRelationship r1 = new Header2DetailRelationship(e1);
		r1.connect();

		Map<String, EntityAttributePair> results = new AttributeListModelBuilder()
				.build(diagram.query().listEntityModel());
		assertEquals(results.keySet().size(), 7);
	}
}
