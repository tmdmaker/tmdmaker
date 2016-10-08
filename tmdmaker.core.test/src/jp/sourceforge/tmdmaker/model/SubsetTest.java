/*
 * Copyright 2009-2015 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
import jp.sourceforge.tmdmaker.model.SubsetType.SubsetTypeValue;
import jp.sourceforge.tmdmaker.model.rule.SubsetRule;

import org.junit.Before;
import org.junit.Test;

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
		e1 = diagram.createEntity("テスト1", "テスト1番号", EntityType.EVENT);
		SubsetType subsetType = new SubsetType();
		subsetType.setExceptNull(false);
		subsetType.setSubsetType(SubsetTypeValue.SAME);
		Entity2SubsetTypeRelationship r1 = new Entity2SubsetTypeRelationship(e1, subsetType);
		diagram.addChild(subsetType);
		r1.connect();

		SubsetEntity s1 = SubsetRule.createSubsetEntity(e1, "サブセット1");
		SubsetEntity s2 = SubsetRule.createSubsetEntity(e1, "サブセット2");
		SubsetType2SubsetRelationship r2 = new SubsetType2SubsetRelationship(subsetType, s1);
		SubsetType2SubsetRelationship r3 = new SubsetType2SubsetRelationship(subsetType, s2);
		r2.connect();
		r3.connect();

	}

	@Test
	public void testCreateSubset() {
		SubsetType subsetType = e1.findSubsetType();
		assertEquals(SubsetTypeValue.SAME, subsetType.getSubsetType());
		assertEquals(true, subsetType.isSameType());
		assertEquals(false, subsetType.isNew());
		assertEquals(e1, subsetType.getSuperset());
		assertEquals(true, subsetType != null);
		assertEquals(true, subsetType.hasSubsetEntity());
		assertEquals(false, subsetType.isExceptNull());
		assertEquals(2, subsetType.getSubsetList().size());

		SubsetEntity s1 = subsetType.getSubsetList().get(0);
		SubsetEntity s2 = subsetType.getSubsetList().get(1);
		assertEquals("サブセット1", s1.getName());
		assertEquals("サブセット2", s2.getName());
		assertThat(
				((Entity2SubsetTypeRelationship) subsetType.getModelTargetConnections().get(0))
						.getSourceName(),
				equalTo(e1.getName()));
		assertThat(
				((Entity2SubsetTypeRelationship) subsetType.getModelTargetConnections().get(0))
						.getTargetName(),
				equalTo(s1.getName() + "," + s2.getName()));
		assertThat(subsetType.getModelSourceConnections().get(0),
				instanceOf(SubsetType2SubsetRelationship.class));
		assertThat(
				((SubsetType2SubsetRelationship) subsetType.getModelSourceConnections().get(0))
						.getSourceName(),
				equalTo(e1.getName()));
		assertThat(
				((SubsetType2SubsetRelationship) subsetType.getModelSourceConnections().get(0))
						.getTargetName(),
				equalTo(s1.getName()));
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

		Entity2SubsetTypeRelationship r1 = (Entity2SubsetTypeRelationship) subsetType
				.getModelTargetConnections().get(0);
		assertEquals(a2, r1.getPartitionAttribute());
		assertEquals(false, r1.isExceptNull());
		assertThat(r1.isDeletable(), is(true));
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
		assertEquals(true, subsetType.isNew());
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
}
