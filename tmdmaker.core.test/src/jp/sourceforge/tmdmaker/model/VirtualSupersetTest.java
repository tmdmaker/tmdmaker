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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * VirtualSupersetのテストクラス
 * 
 * @author nakag
 *
 */
public class VirtualSupersetTest {

	/**
	 * Test method for
	 * {@link jp.sourceforge.tmdmaker.model.VirtualSuperset#disconnectSubset(jp.sourceforge.tmdmaker.model.AbstractEntityModel)}
	 * .
	 */
	@Test
	public void testRemoveSubset() {
		Diagram diagram = new Diagram();
		Entity e1 = diagram.createEntity("テスト1", "テスト1番号", EntityType.EVENT);
		Entity e2 = diagram.createEntity("テスト2", "テスト2番号", EntityType.EVENT);
		List<AbstractEntityModel> list = new ArrayList<AbstractEntityModel>();
		list.add(e1);
		list.add(e2);
		VirtualSuperset vsp = diagram.createVirtualSuperset("スーパーセット", list);
		VirtualSupersetType type1 = vsp.getVirtualSupersetType();

		assertEquals(true, vsp.isDeletable());
		assertEquals(true, type1.isApplyAttribute());
		assertEquals(false, vsp.isEntityTypeEditable());
		assertEquals(true, vsp.hasSubset());
		assertEquals(2, vsp.getVirtualSubsetRelationshipList().size());
		
		vsp.disconnectSubset(e2);
		assertEquals(1, vsp.getVirtualSubsetList().size());
		vsp.disconnectSubset(e1);
		assertEquals(0, vsp.getVirtualSubsetList().size());
		assertEquals(false, vsp.hasSubset());
		vsp.disconnectSubset(e1);
		VirtualSupersetType type2 = vsp.getVirtualSupersetType();
		if (type2 == null) {
			return;
		}

		fail("VirtualSupersetType is not empty.");
	}

	@Test
	public void testSuper2Super() {
		Diagram diagram = new Diagram();
		Entity e1 = diagram.createEntity("テスト1", "テスト1番号", EntityType.EVENT);
		Entity e2 = diagram.createEntity("テスト2", "テスト2番号", EntityType.EVENT);
		Entity e3 = diagram.createEntity("テスト3", "テスト3番号", EntityType.EVENT);
		List<AbstractEntityModel> list = new ArrayList<AbstractEntityModel>();
		list.add(e1);
		list.add(e2);
		VirtualSuperset vsp1 = diagram.createVirtualSuperset("スーパーセット1", list);
		list = new ArrayList<AbstractEntityModel>();
		list.add(e3);
		list.add(vsp1);
		VirtualSuperset vsp2 = diagram.createVirtualSuperset("スーパーセット2", list);
		
		assertEquals(true, vsp2.isDeletable());
		assertEquals(false, vsp1.isDeletable());
	}
}
