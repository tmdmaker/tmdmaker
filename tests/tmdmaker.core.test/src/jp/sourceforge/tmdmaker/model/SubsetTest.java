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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import jp.sourceforge.tmdmaker.model.SubsetType.SubsetTypeValue;
import jp.sourceforge.tmdmaker.model.parts.ModelName;
import jp.sourceforge.tmdmaker.model.subset.SubsetBuilder;

/**
 * Subsetのテストクラス
 * 
 * @author nakag
 *
 */
public class SubsetTest {
	private Diagram diagram = null;
	private Entity e1 = null;

	@Before
	public void setup() {
		diagram = new Diagram();
		e1 = Entity.ofEvent(new Identifier("テスト1番号")).withDefaultAttribute();
		diagram.addChild(e1);

		e1.subsets().builder()
			.same()
			.add(new ModelName("サブセット1"))
			.add(new ModelName("サブセット2"))
			.build();

	}

	@Test
	public void testCreateSubset() {
		SubsetType subsetType = e1.subsets().subsetType();
		assertEquals(SubsetTypeValue.SAME, subsetType.getSubsetType());
		assertEquals(true, subsetType.isSameType());
		assertEquals(true, subsetType.isInitialPosition());
		assertEquals(e1, subsetType.getSuperset());
		assertEquals(true, subsetType != null);
		assertEquals(true, subsetType.hasSubsetEntity());
		assertEquals(false, subsetType.isExceptNull());
		assertEquals(2, subsetType.getSubsetList().size());

		SubsetEntity s1 = subsetType.getSubsetList().get(0);
		SubsetEntity s2 = subsetType.getSubsetList().get(1);
		assertEquals("サブセット1", s1.getName());
		assertEquals("サブセット2", s2.getName());
		assertThat(((Entity2SubsetTypeRelationship) subsetType.getModelTargetConnections().get(0))
				.getSourceName(), equalTo(e1.getName()));
		assertThat(((Entity2SubsetTypeRelationship) subsetType.getModelTargetConnections().get(0))
				.getTargetName(), equalTo(s1.getName() + "," + s2.getName()));
		assertThat(subsetType.getModelSourceConnections().get(0),
				instanceOf(SubsetType2SubsetRelationship.class));
		assertThat(((SubsetType2SubsetRelationship) subsetType.getModelSourceConnections().get(0))
				.getSourceName(), equalTo(e1.getName()));
		assertThat(((SubsetType2SubsetRelationship) subsetType.getModelSourceConnections().get(0))
				.getTargetName(), equalTo(s1.getName()));
	}

	@Test
	public void testPartision() {
		SubsetType subsetType = e1.findSubsetType();
		Attribute a1 = new Attribute("テスト属性1");
		subsetType.setPartitionAttribute(a1);
		assertEquals(a1, subsetType.getPartitionAttribute());

		Attribute a2 = new Attribute("テスト属性2");
		subsetType.setPartitionAttribute(a2);
		assertEquals(a2, subsetType.getPartitionAttribute());

		Entity2SubsetTypeRelationship r1 = subsetType.getEntityRelationship();
		assertEquals("テスト属性2", r1.getPartitionAttributeName());
		subsetType.setExceptNull(true);
		assertEquals("NULL(テスト属性2)", r1.getPartitionAttributeName());
		assertThat(r1.isDeletable(), is(true));
		subsetType.setPartitionAttribute(null);
		assertEquals("", r1.getPartitionAttributeName());
	}

	@Test
	public void testRemoveSubset() {
		SubsetType subsetType = e1.findSubsetType();
		SubsetEntity s1 = subsetType.getSubsetList().get(0);
		SubsetEntity s2 = subsetType.getSubsetList().get(1);
		assertEquals(true, s1.isDeletable());
		assertEquals(true, s2.isDeletable());
		assertEquals(true, s1.getModelTargetConnections().get(0).isDeletable());
		assertEquals(true, s2.getModelTargetConnections().get(0).isDeletable());
		s2.getModelTargetConnections().get(0).isDeletable();
		s1.getModelTargetConnections().get(0).disconnect();
		s2.getModelTargetConnections().get(0).disconnect();
		assertEquals(false, subsetType.hasSubsetEntity());

		AbstractConnectionModel r1 = subsetType.getModelTargetConnections().get(0);
		assertEquals(true, r1 instanceof Entity2SubsetTypeRelationship);
		Entity2SubsetTypeRelationship r2 = (Entity2SubsetTypeRelationship) r1;
		assertEquals(true, r2.isDeletable());
		r2.disconnect();
		assertEquals(true, subsetType.isInitialPosition());
	}

	@Test
	public void testIdentifierChange() {
		SubsetType subsetType = e1.findSubsetType();
		SubsetEntity s1 = subsetType.getSubsetList().get(0);
		SubsetEntity s2 = subsetType.getSubsetList().get(1);
		e1.setIdentifierName("テスト1番号1");
		assertEquals("テスト1番号1", s1.getOriginalReusedIdentifier().getIdentifiers().get(0).getName());
		assertEquals("テスト1番号1", s2.getOriginalReusedIdentifier().getIdentifiers().get(0).getName());
	}

	@Test
	public void testAllRemove() {
		SubsetType subsetType = e1.findSubsetType();
		SubsetEntity s1 = subsetType.getSubsetList().get(0);
		SubsetEntity s2 = subsetType.getSubsetList().get(1);
		e1.subsets().builder()
			.remove(s1)
			.remove(s2)
			.build();
		assertEquals(false, subsetType.hasSubsetEntity());
		assertEquals(true, subsetType.isInitialPosition());
	}

	@Test
	public void testBuilder1() {
		SubsetBuilder builder = e1.subsets().builder();
		ModelName subsetName = new ModelName("サブセット3");
		builder
			.add(subsetName)
			.notExpectNull()
			.build();
		assertEquals(3, e1.subsets().all().size());
		builder.rollbackAdd();
		assertEquals(2, e1.subsets().all().size());
		builder.build();
	
		SubsetEntity s1 = e1.subsets().query().findByName(subsetName).get(0);
		builder = e1.subsets().builder();
		builder.remove(s1).build();
		assertEquals(2, e1.subsets().all().size());
		builder.rollbackRemove();
	}

	@Test
	public void testBuilder2() {
		Entity e2 = Entity.ofEvent(new Identifier("テスト1番号")).withDefaultAttribute();
		diagram.addChild(e2);
		Attribute atr = new Attribute("アトリビュート");
		e2.addAttribute(atr);

		SubsetBuilder builder = e2.subsets().builder();
		builder.different()
			.expectNull()
			.partition(atr)
			.add(new ModelName("サブセット1"))
			.add(new ModelName("サブセット2"))
			.build();
		builder.build();
		builder.rollbackAdd();
		assertEquals(0, e2.subsets().all().size());
	}

	@Test
	public void testBuilder3() {
		Entity e2 = Entity.ofEvent(new Identifier("テスト1番号")).withDefaultAttribute();
		diagram.addChild(e2);
		Attribute atr = new Attribute("アトリビュート");
		e2.addAttribute(atr);

		SubsetBuilder builder = e2.subsets().builder();
		builder.different()
			.expectNull()
			.partition(atr)
			.add(new ModelName("サブセット1"))
			.add(new ModelName("サブセット2"))
			.build();
		builder.build();
		assertEquals(2, e2.subsets().all().size());
		
		builder = e2.subsets().builder();
		builder
			.remove(e2.subsets().all().get(0))
			.remove(e2.subsets().all().get(1))
			.build();
		assertEquals(0, e2.subsets().all().size());
		
		builder.rollbackRemove();
		assertEquals(2, e2.subsets().all().size());
	}

}
