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

import org.junit.Test;

import jp.sourceforge.tmdmaker.model.other.Memo;

/**
 * モデル毎の特徴の差異のテスト
 *
 * @author nakag
 *
 */
public class ModelFeatureTest {
	/**
	 * モデル毎のサブセットの作成可否をテスト
	 */
	@Test
	public void testCanCreateSubset() {
		CombinationTable t = new CombinationTable();
		assertEquals(true, t.canCreateSubset());

		Entity e = new Entity();
		assertEquals(true, e.canCreateSubset());

		Detail d = new Detail();
		assertEquals(true, d.canCreateSubset());

		Laputa l = new Laputa();
		assertEquals(false, l.canCreateSubset());

		MappingList m = new MappingList();
		assertEquals(true, m.canCreateSubset());

		MultivalueAndSuperset ms = new MultivalueAndSuperset();
		assertEquals(false, ms.canCreateSubset());

		MultivalueOrEntity mo = new MultivalueOrEntity();
		assertEquals(true, mo.canCreateSubset());

		RecursiveTable r = new RecursiveTable();
		assertEquals(true, r.canCreateSubset());

		SubsetEntity s = new SubsetEntity();
		assertEquals(true, s.canCreateSubset());

		VirtualEntity v = new VirtualEntity();
		assertEquals(false, v.canCreateSubset());

		VirtualSuperset vs = new VirtualSuperset();
		assertEquals(false, vs.canCreateSubset());

		Memo mm = new Memo();
		assertEquals(false, mm.canCreateSubset());

	}

	/**
	 * モデル毎の多値のOR作成可否をテスト
	 */
	@Test
	public void testCanCreateMultivalueOr() {
		CombinationTable t = new CombinationTable();
		assertEquals(true, t.canCreateMultivalueOr());

		Entity e = new Entity();
		assertEquals(true, e.canCreateMultivalueOr());

		Detail d = new Detail();
		assertEquals(true, d.canCreateMultivalueOr());

		Laputa l = new Laputa();
		assertEquals(false, l.canCreateMultivalueOr());

		MappingList m = new MappingList();
		assertEquals(true, m.canCreateMultivalueOr());

		MultivalueAndSuperset ms = new MultivalueAndSuperset();
		assertEquals(false, ms.canCreateMultivalueOr());

		MultivalueOrEntity mo = new MultivalueOrEntity();
		assertEquals(true, mo.canCreateMultivalueOr());

		RecursiveTable r = new RecursiveTable();
		assertEquals(true, r.canCreateMultivalueOr());

		SubsetEntity s = new SubsetEntity();
		assertEquals(true, s.canCreateMultivalueOr());

		VirtualEntity v = new VirtualEntity();
		assertEquals(false, v.canCreateMultivalueOr());

		VirtualSuperset vs = new VirtualSuperset();
		assertEquals(false, vs.canCreateMultivalueOr());

		Memo mm = new Memo();
		assertEquals(false, mm.canCreateMultivalueOr());

	}

	/**
	 * モデル毎のみなしエンティティ作成可否をテスト
	 */
	@Test
	public void testCanCreateVirtualEntity() {
		CombinationTable t = new CombinationTable();
		assertEquals(true, t.canCreateVirtualEntity());

		Entity e = new Entity();
		assertEquals(true, e.canCreateVirtualEntity());

		Detail d = new Detail();
		assertEquals(true, d.canCreateVirtualEntity());

		Laputa l = new Laputa();
		assertEquals(false, l.canCreateVirtualEntity());

		MappingList m = new MappingList();
		assertEquals(true, m.canCreateVirtualEntity());

		MultivalueAndSuperset ms = new MultivalueAndSuperset();
		assertEquals(false, ms.canCreateVirtualEntity());

		MultivalueOrEntity mo = new MultivalueOrEntity();
		assertEquals(true, mo.canCreateVirtualEntity());

		RecursiveTable r = new RecursiveTable();
		assertEquals(true, r.canCreateVirtualEntity());

		SubsetEntity s = new SubsetEntity();
		assertEquals(true, s.canCreateVirtualEntity());

		VirtualEntity v = new VirtualEntity();
		assertEquals(false, v.canCreateVirtualEntity());

		VirtualSuperset vs = new VirtualSuperset();
		assertEquals(false, vs.canCreateVirtualEntity());

		Memo mm = new Memo();
		assertEquals(false, mm.canCreateVirtualEntity());

	}

}
