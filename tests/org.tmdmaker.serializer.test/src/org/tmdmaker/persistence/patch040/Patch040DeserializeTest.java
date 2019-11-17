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
package org.tmdmaker.persistence.patch040;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;

import org.junit.Test;
import org.tmdmaker.core.model.AbstractConnectionModel;
import org.tmdmaker.core.model.Diagram;
import org.tmdmaker.core.model.VirtualSuperset;
import org.tmdmaker.core.model.VirtualSupersetType2VirtualSupersetRelationship;
import org.tmdmaker.persistence.XStreamSerializer;

/**
 * モデルのバージョン0.4.0へのバージョンアップのテスト.
 * 
 * @author nakag
 *
 */
public class Patch040DeserializeTest {

	@Test
	public void test040Deserialize() {
		InputStream in = getClass().getResourceAsStream("test040.tmd");
		XStreamSerializer serializer = new XStreamSerializer();
		Diagram diagram = serializer.deserialize(in);
		assertNotNull(diagram);
		for (VirtualSuperset superset : diagram.query().listEntityModel(VirtualSuperset.class)) {
			AbstractConnectionModel r = superset.getModelTargetConnections().get(0);
			assertTrue(r instanceof VirtualSupersetType2VirtualSupersetRelationship);
		}
	}
}
