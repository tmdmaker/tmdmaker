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

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * diagramのテストクラス
 *
 * @author nakag
 *
 */
public class DiagramTest {

	@Test
	public void testCreateVirtualSuperset() {
		Diagram diagram = new Diagram();
		Entity e1 = diagram.createEntity("テスト1", "テスト1番号", EntityType.EVENT);
		Entity e2 = diagram.createEntity("テスト2", "テスト2番号", EntityType.EVENT);
		List<AbstractEntityModel> list = new ArrayList<AbstractEntityModel>();
		list.add(e1);
		list.add(e2);
		VirtualSuperset vsp = diagram.createVirtualSuperset("スーパーセット", list);
		RelatedRelationship t2v = (RelatedRelationship) vsp.getModelTargetConnections().get(0);
		VirtualSupersetType type = vsp.getVirtualSupersetType();
		Entity2VirtualSupersetTypeRelationship m2t1 = (Entity2VirtualSupersetTypeRelationship) type
				.getModelTargetConnections().get(0);

		assertEquals(2, vsp.getVirtualSubsetList().size());
		assertEquals(true, t2v.isDeletable());
		assertEquals("スーパーセット", t2v.getSourceName());
		assertEquals("テスト1,テスト2", t2v.getTargetName());

		assertEquals("スーパーセット", m2t1.getSourceName());
		assertEquals("テスト1", m2t1.getTargetName());
	}

	@Test
	public void testFindEntity() {
		Diagram diagram = new Diagram();
		assertEquals(0, diagram.findEntityModel().size());

		Entity e1 = diagram.createEntity("テスト1", "テスト1番号", EntityType.EVENT);
		assertEquals(1, diagram.findEntityModel().size());

		diagram.removeChild(e1);
		assertEquals(0, diagram.findEntityModel().size());

		diagram.addChild(e1);
		Entity e2 = diagram.createEntity("テスト2", "テスト2番号", EntityType.EVENT);
		List<AbstractEntityModel> list = new ArrayList<AbstractEntityModel>();
		list.add(e1);
		list.add(e2);
		VirtualSuperset vsp = diagram.createVirtualSuperset("スーパーセット", list);
		assertEquals(3, diagram.findEntityModel().size());

		VirtualSupersetType type = vsp.getVirtualSupersetType();
		diagram.addChild(e1);
		diagram.addChild(type);
		assertEquals(3, diagram.findEntityModel().size());

		List<AbstractEntityModel> excludes = new ArrayList<AbstractEntityModel>();
		excludes.add(e1);
		excludes.add(e2);
		excludes.add(vsp);
		Entity e3 = diagram.createEntity("テスト3", "テスト3番号", EntityType.EVENT);

		List<AbstractEntityModel> exlist = diagram.findEntityModelExcludeFor(excludes);
		assertEquals(1, exlist.size());
		assertEquals(e3, exlist.get(0));
	}	
}
