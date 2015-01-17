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

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * MultivalueAndSupersetのテストクラス
 * 
 * @author nakag
 *
 */
public class MultivalueAndSupersetTest {

	@Test
	public void test() {
		Diagram diagram = new Diagram();
		Entity e1 = diagram.createEntity("テスト1", "テスト1番号", EntityType.EVENT);
		Entity e2 = diagram.createEntity("テスト2", "テスト2番号", EntityType.RESOURCE);
		
		Header2DetailRelationship r = new Header2DetailRelationship(e1);
		r.connect();
		MultivalueAndSuperset sp = r.getMultivalueAndSuperset();
		assertEquals(true, sp.isDeletable());
		assertEquals(false, sp.isEntityTypeEditable());
		assertEquals(true, sp.isNotImplement());
		assertEquals(true, sp.createReusedIdentifier() == null);
		
		Detail d1 = sp.getDetail();
		TransfarReuseKeysToTargetRelationship r2 = new TransfarReuseKeysToTargetRelationship(e2, d1);
		r2.connect();
		
		assertEquals(false, sp.isDeletable());
	}

}
