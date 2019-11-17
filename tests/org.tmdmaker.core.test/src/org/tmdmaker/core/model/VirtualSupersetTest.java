/*
 * Copyright 2009-2019 TMD-Maker Project <https://tmdmaker.osdn.jp/>
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

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.Diagram;
import org.tmdmaker.core.model.Entity;
import org.tmdmaker.core.model.Identifier;
import org.tmdmaker.core.model.VirtualSuperset;
import org.tmdmaker.core.model.VirtualSupersetType;
import org.tmdmaker.core.model.parts.ModelName;
import org.tmdmaker.core.model.virtual.VirtualSubsetBuilder;

/**
 * VirtualSupersetのテストクラス
 * 
 * @author nakag
 *
 */
public class VirtualSupersetTest {

	@Test
	public void testRemoveSubset() {
		Diagram diagram = new Diagram();
		Entity e1 = Entity.ofResource(new Identifier("テスト1番号")).withDefaultAttribute();
		diagram.addChild(e1);
		Entity e2 = Entity.ofResource(new Identifier("テスト2番号")).withDefaultAttribute();
		diagram.addChild(e2);
		List<AbstractEntityModel> list = new ArrayList<AbstractEntityModel>();
		list.add(e1);
		list.add(e2);
		VirtualSuperset vsp = VirtualSuperset.of(new ModelName("スーパーセット"));
		VirtualSubsetBuilder builder = vsp.virtualSubsets().builder();
		builder.subsetList(list).build();
		assertTrue(vsp.hasSubset());
		VirtualSupersetType type1 = vsp.getVirtualSupersetType();

		assertEquals(true, vsp.isDeletable());
		assertEquals(true, type1.isApplyAttribute());
		assertEquals(false, vsp.isEntityTypeEditable());
		assertEquals(true, vsp.hasSubset());
		assertEquals(2, vsp.getVirtualSubsetRelationshipList().size());

		builder.rollback();
		assertTrue(!vsp.hasSubset());
		VirtualSupersetType type2 = vsp.getVirtualSupersetType();
		if (!diagram.contains(type2)) {
			return;
		}
	}

	@Test
	public void testSuper2Super() {
		Diagram diagram = new Diagram();
		Entity e1 = Entity.ofEvent(new Identifier("テスト1番号")).withDefaultAttribute();
		diagram.addChild(e1);
		Entity e2 = Entity.ofEvent(new Identifier("テスト2番号")).withDefaultAttribute();
		diagram.addChild(e2);
		Entity e3 = Entity.ofEvent(new Identifier("テスト2番号")).withDefaultAttribute();
		diagram.addChild(e3);
		List<AbstractEntityModel> list = new ArrayList<AbstractEntityModel>();
		list.add(e1);
		list.add(e2);
		VirtualSuperset vsp1 = VirtualSuperset.of(new ModelName("スーパーセット1"));
		vsp1.virtualSubsets().builder().subsetList(list).build();
		list = new ArrayList<AbstractEntityModel>();
		list.add(e3);
		list.add(vsp1);
		VirtualSuperset vsp2 = VirtualSuperset.of(new ModelName("スーパーセット2"));
		vsp2.virtualSubsets().builder().subsetList(list).build();

		assertEquals(true, vsp2.isDeletable());
		assertEquals(false, vsp1.isDeletable());

		VirtualSupersetType type1 = vsp1.getVirtualSupersetType();
		assertEquals(2, type1.getReusedIdentifiers().size());
		VirtualSupersetType type2 = vsp2.getVirtualSupersetType();
		assertEquals(2, type2.getReusedIdentifiers().size());
		assertEquals(false, type2.isVertical());
		type2.setVertical(true);
		assertEquals(true, type2.isVertical());
		assertEquals(type1.getSuperset(), vsp1);
	}

	@Test
	public void testSuper3Super() {
		List<AbstractEntityModel> list = new ArrayList<AbstractEntityModel>();
		Diagram diagram = new Diagram();
		Entity e1 = Entity.ofEvent(new Identifier("テスト1番号")).withDefaultAttribute();
		diagram.addChild(e1);
		Entity e2 = Entity.ofEvent(new Identifier("テスト2番号")).withDefaultAttribute();
		diagram.addChild(e2);
		Entity e3 = Entity.ofEvent(new Identifier("テスト2番号")).withDefaultAttribute();
		diagram.addChild(e3);
		VirtualSuperset vsp1 = VirtualSuperset.of(new ModelName("スーパーセット3"));
		VirtualSubsetBuilder builder = vsp1.virtualSubsets().builder();
		builder.subsetList(list).build();
		assertTrue(!vsp1.hasSubset());

		builder.rollback();
		list.add(e1);
		list.add(e2);
		list.add(e3);
		builder.subsetList(list).build();
		assertTrue(vsp1.hasSubset());
		assertEquals(diagram, vsp1.getDiagram());
	}

	@Test
	public void testEditSuperSuper1() {
		List<AbstractEntityModel> list = new ArrayList<AbstractEntityModel>();
		Diagram diagram = new Diagram();
		Entity e1 = Entity.ofEvent(new Identifier("テスト1番号")).withDefaultAttribute();
		diagram.addChild(e1);
		Entity e2 = Entity.ofEvent(new Identifier("テスト2番号")).withDefaultAttribute();
		diagram.addChild(e2);
		Entity e3 = Entity.ofEvent(new Identifier("テスト2番号")).withDefaultAttribute();
		diagram.addChild(e3);
		VirtualSuperset vsp1 = VirtualSuperset.of(new ModelName("スーパーセット"));
		VirtualSubsetBuilder builder = vsp1.virtualSubsets().builder();
		list.add(e1);
		list.add(e2);
		list.add(e3);
		builder.subsetList(list).build();
		assertEquals(3, vsp1.virtualSubsets().all().size());

		List<AbstractEntityModel> list2 = new ArrayList<AbstractEntityModel>();
		list2.add(e1);
		list2.add(e2);
		builder = vsp1.virtualSubsets().builder();
		builder.subsetList(list2).build();
		assertTrue(vsp1.hasSubset());
		assertEquals(vsp1.virtualSubsets().all().size(), 2);

		builder.rollback();
		assertTrue(vsp1.hasSubset());
		assertEquals(vsp1.virtualSubsets().all().size(), 3);
	}

	@Test
	public void testDeleteSuperSuper1() {
		List<AbstractEntityModel> list = new ArrayList<AbstractEntityModel>();
		Diagram diagram = new Diagram();
		Entity e1 = Entity.ofEvent(new Identifier("テスト1番号")).withDefaultAttribute();
		diagram.addChild(e1);
		Entity e2 = Entity.ofEvent(new Identifier("テスト2番号")).withDefaultAttribute();
		diagram.addChild(e2);
		Entity e3 = Entity.ofEvent(new Identifier("テスト2番号")).withDefaultAttribute();
		diagram.addChild(e3);
		VirtualSuperset vsp1 = VirtualSuperset.of(new ModelName("スーパーセット"));
		VirtualSubsetBuilder builder = vsp1.virtualSubsets().builder();
		list.add(e1);
		list.add(e2);
		list.add(e3);
		builder.subsetList(list).build();
		assertEquals(3, vsp1.virtualSubsets().all().size());

		List<AbstractEntityModel> list2 = new ArrayList<AbstractEntityModel>();
		list2.add(e1);
		list2.add(e2);
		builder = vsp1.virtualSubsets().builder();
		builder.subsetList(null).build();
		assertTrue(!vsp1.hasSubset());
		assertEquals(vsp1.virtualSubsets().all().size(), 0);

		builder.rollback();
		assertTrue(vsp1.hasSubset());
		assertEquals(vsp1.virtualSubsets().all().size(), 3);
	}

}
