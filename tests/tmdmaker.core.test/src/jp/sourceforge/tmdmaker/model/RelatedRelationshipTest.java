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
package jp.sourceforge.tmdmaker.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import jp.sourceforge.tmdmaker.model.relationship.Relationship;
import jp.sourceforge.tmdmaker.model.rule.VirtualEntityRule;

/**
 * RelatedRelationshipのテスト
 * 
 * @author nakag
 *
 */
public class RelatedRelationshipTest {

	/**
	 * Test method for {@link jp.sourceforge.tmdmaker.model.RelatedRelationship}
	 * .
	 */
	@Test
	public void testVirtualSupersetHelper() {
		Diagram diagram = new Diagram();
		Entity e1 = Entity.ofEvent(new Identifier("テスト1番号")).withDefaultAttribute();
		diagram.addChild(e1);
		Entity e2 = Entity.ofEvent(new Identifier("テスト2番号")).withDefaultAttribute();
		diagram.addChild(e2);

		VirtualSuperset vsp = VirtualEntityRule.createVirtualSuperset("スーパーセット");
		VirtualSupersetType type = new VirtualSupersetType();
		diagram.addChild(vsp);
		diagram.addChild(type);
		RelatedRelationship t2v = new RelatedRelationship(type, vsp);
		Entity2VirtualSupersetTypeRelationship m2t1 = new Entity2VirtualSupersetTypeRelationship(e1,
				type);
		Entity2VirtualSupersetTypeRelationship m2t2 = new Entity2VirtualSupersetTypeRelationship(e2,
				type);
		t2v.connect();
		m2t1.connect();
		m2t2.connect();

		assertEquals(true, t2v.isDeletable());
		assertEquals("スーパーセット", t2v.getSourceName());
		assertEquals("テスト1,テスト2", t2v.getTargetName());

		assertEquals("スーパーセット", m2t1.getSourceName());
		assertEquals("テスト1", m2t1.getTargetName());
	}

	/**
	 * Test method for {@link jp.sourceforge.tmdmaker.model.RelatedRelationship}
	 * .
	 */
	@Test
	public void testMultivalueAndHelper() {
		Diagram diagram = new Diagram();
		Entity e = Entity.ofEvent(new Identifier("テスト番号")).withDefaultAttribute();
		diagram.addChild(e);

		Header2DetailRelationship r = new Header2DetailRelationship(e);
		r.connect();
		MultivalueAndAggregator ag = r.getAggregator();
		AbstractConnectionModel c0 = ag.getModelTargetConnections().get(0);
		AbstractConnectionModel c1 = ag.getModelTargetConnections().get(1);
		AbstractConnectionModel c2 = ag.getModelTargetConnections().get(2);

		assertEquals("テスト", c0.getSourceName());
		assertEquals("テストHDR,テストDTL", c0.getTargetName());

		assertEquals("テスト", c1.getSourceName());
		assertEquals("テストHDR", c1.getTargetName());

		assertEquals("テスト", c2.getSourceName());
		assertEquals("テストDTL", c2.getTargetName());
	}

	/**
	 * Test method for {@link jp.sourceforge.tmdmaker.model.RelatedRelationship}
	 * .
	 */
	@Test
	public void testTableHelper() {
		Diagram diagram = new Diagram();
		Entity e1 = Entity.ofResource(new Identifier("テスト1番号")).withDefaultAttribute();
		diagram.addChild(e1);
		Entity e2 = Entity.ofResource(new Identifier("テスト2番号")).withDefaultAttribute();
		diagram.addChild(e2);

		AbstractRelationship r = Relationship.of(e1, e2);
		r.connect();
		CombinationTable t1 = (CombinationTable) r.getTable();
		RelatedRelationship rr = (RelatedRelationship) t1.getModelTargetConnections().get(0);

		assertEquals("テスト1", rr.getSourceName());
		assertEquals("テスト2", rr.getTargetName());
	}
}
