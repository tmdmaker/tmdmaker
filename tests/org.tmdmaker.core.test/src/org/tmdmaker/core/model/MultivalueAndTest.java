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
package org.tmdmaker.core.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;
import org.tmdmaker.core.model.AbstractConnectionModel;
import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.AbstractRelationship;
import org.tmdmaker.core.model.Cardinality;
import org.tmdmaker.core.model.Detail;
import org.tmdmaker.core.model.Diagram;
import org.tmdmaker.core.model.Entity;
import org.tmdmaker.core.model.Header2DetailRelationship;
import org.tmdmaker.core.model.Identifier;
import org.tmdmaker.core.model.MultivalueAndAggregator;
import org.tmdmaker.core.model.MultivalueAndSuperset;
import org.tmdmaker.core.model.ReusedIdentifier;
import org.tmdmaker.core.model.TransfarReuseKeysToTargetRelationship;
import org.tmdmaker.core.model.multivalue.MultivalueAndBuilder;
import org.tmdmaker.core.model.relationship.Relationship;

/**
 * 多値のANDのテストクラス
 * 
 * @author nakag
 *
 */
public class MultivalueAndTest {

	@Test
	public void testSuperset() {
		Diagram diagram = new Diagram();
		Entity e1 = Entity.ofEvent(new Identifier("テスト1番号")).withDefaultAttribute();
		diagram.addChild(e1);
		Entity e2 = Entity.ofResource(new Identifier("テスト2番号")).withDefaultAttribute();
		diagram.addChild(e2);

		Header2DetailRelationship r = new Header2DetailRelationship(e1);
		assertEquals(false, r.isConnected());
		assertEquals(false, r.isSupersetConnected());
		r.connect();
		assertEquals(true, r.isConnected());
		assertEquals(true, r.isSupersetConnected());
		MultivalueAndSuperset sp = r.getMultivalueAndSuperset();
		assertEquals(true, sp.isDeletable());
		assertEquals(false, sp.isEntityTypeEditable());
		assertEquals(true, sp.isNotImplement());
		assertEquals(true, sp.createReusedIdentifier() == null);
		assertEquals(true, r.isDeletable());

		Detail d1 = sp.getDetail();
		TransfarReuseKeysToTargetRelationship r2 = new TransfarReuseKeysToTargetRelationship(e2,
				d1);
		r2.connect();

		assertEquals(false, sp.isDeletable());
		assertEquals(false, r.isDeletable());
		assertEquals(r.getAggregator().getSuperset(), sp);
		MultivalueAndAggregator a = r.getAggregator();
		assertEquals(a.getSubsetList().get(0), e1);
		assertEquals(a.getSubsetList().get(1), r.getTarget());

		r.disconnect();
		assertEquals(false, r.isConnected());
		assertEquals(false, r.isSupersetConnected());
	}

	@Test
	public void testDetail() {
		Diagram diagram = new Diagram();
		Entity e1 = Entity.ofEvent(new Identifier("テスト1番号")).withDefaultAttribute();
		diagram.addChild(e1);
		Entity e2 = Entity.ofResource(new Identifier("テスト2番号")).withDefaultAttribute();
		diagram.addChild(e2);
		Entity e3 = Entity.ofEvent(new Identifier("テスト3番号")).withDefaultAttribute();
		diagram.addChild(e1);

		Header2DetailRelationship r1 = new Header2DetailRelationship(e1);
		r1.connect();
		MultivalueAndSuperset sp = r1.getMultivalueAndSuperset();
		assertEquals(true, sp != null);
		Detail dtl = (Detail) r1.getTarget();
		assertEquals(true, dtl.isDeletable());
		assertEquals(e1.getIdentifier(),
				dtl.getOriginalReusedIdentifier().getIdentifiers().get(0).getOriginal());
		assertEquals(false, dtl.isEntityTypeEditable());

		AbstractConnectionModel r2 = Relationship.of(dtl, e2);
		r2.connect();
		assertEquals(false, dtl.isDeletable());

		AbstractConnectionModel r3 = Relationship.of(dtl, e3);
		r3.connect();

		// Detailへの個体指定子変更伝播
		e1.setIdentifierName("テスト1番号変更");
		Map<AbstractEntityModel, ReusedIdentifier> reused1 = dtl.getReusedIdentifiers();
		assertEquals(2, reused1.size());
		ReusedIdentifier ri1 = reused1.get(e1);
		assertEquals("テスト1番号変更", ri1.getIdentifiers().get(0).getName());
		Map<AbstractEntityModel, ReusedIdentifier> reused2 = e3.getReusedIdentifiers();
		assertEquals(1, reused2.size());
		ReusedIdentifier dri1 = reused2.get(dtl);
		assertEquals(2, dri1.getIdentifiers().size());
		assertEquals("テスト1番号変更", dri1.getIdentifiers().get(0).getName());
		assertEquals("テスト1明細番号", dri1.getIdentifiers().get(1).getName());

		// Detailからの個体指定子変更伝播
		dtl.setDetailIdentifierName("テスト1明細番号変更");
		Map<AbstractEntityModel, ReusedIdentifier> reused3 = e3.getReusedIdentifiers();
		assertEquals(1, reused3.size());
		ReusedIdentifier dri2 = reused3.get(dtl);
		assertEquals(2, dri2.getIdentifiers().size());
		assertEquals("テスト1番号変更", dri2.getIdentifiers().get(0).getName());
		assertEquals("テスト1明細番号変更", dri2.getIdentifiers().get(1).getName());

		dtl.setDetailIdentifier(new Identifier("テスト1明細番号変更2"));
		Map<AbstractEntityModel, ReusedIdentifier> reused4 = e3.getReusedIdentifiers();
		assertEquals(1, reused4.size());
		ReusedIdentifier dri3 = reused4.get(dtl);
		assertEquals(2, dri3.getIdentifiers().size());
		assertEquals("テスト1番号変更", dri3.getIdentifiers().get(0).getName());
		assertEquals("テスト1明細番号変更", dri3.getIdentifiers().get(1).getName()); // 伝播しない

	}

	@Test
	public void testBuilder() {
		Diagram diagram = new Diagram();
		Entity e1 = Entity.ofEvent(new Identifier("テスト1番号")).withDefaultAttribute();
		diagram.addChild(e1);
		Entity e2 = Entity.ofResource(new Identifier("テスト2番号")).withDefaultAttribute();
		diagram.addChild(e2);
		Entity e3 = Entity.ofEvent(new Identifier("テスト3番号")).withDefaultAttribute();
		diagram.addChild(e3);
		AbstractRelationship r = Relationship.of(e2, e1);
		r.connect();
		r.setSourceCardinality(Cardinality.MANY);
		r.setTargetCardinality(Cardinality.MANY);

		MultivalueAndBuilder builder = e1.multivalueAnd().builder();
		builder.build();
		assertTrue(e1.isHeaderDetail());
		assertTrue(e1.multivalueAnd().aggregator() != null);
		assertTrue(e1.multivalueAnd().superset() != null);
		assertTrue(e1.multivalueAnd().detail() != null);

		builder.rollback();
		assertTrue(!e1.isHeaderDetail());
		assertTrue(e1.multivalueAnd().aggregator() == null);
		assertTrue(e1.multivalueAnd().superset() == null);
		assertTrue(e1.multivalueAnd().detail() == null);
	}
}
