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

import org.junit.Test;
import org.tmdmaker.core.model.Diagram;
import org.tmdmaker.core.model.Entity;
import org.tmdmaker.core.model.Identifier;
import org.tmdmaker.core.model.multivalue.MultivalueOrBuilder;

/**
 * 多値のORのテストクラス
 * 
 * @author nakag
 *
 */
public class MultivalueOrTest {
	@Test
	public void testSuperset() {
		Diagram diagram = new Diagram();
		Entity e1 = Entity.ofEvent(new Identifier("テスト1番号")).withDefaultAttribute();
		diagram.addChild(e1);
		MultivalueOrBuilder builder = e1.multivalueOr().builder();
		builder.typeName("テスト種別").build();
		assertEquals(1, e1.multivalueOr().all().size());
		
		builder.rollback();
		assertEquals(0, e1.multivalueOr().all().size());		
	}
}
