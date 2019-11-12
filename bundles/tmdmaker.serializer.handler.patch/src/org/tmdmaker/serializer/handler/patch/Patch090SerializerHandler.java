/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org/>
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
package org.tmdmaker.serializer.handler.patch;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Detail;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.serializer.handler.patch.AbstractSerializerHandler;

public class Patch090SerializerHandler extends AbstractSerializerHandler {

	@Override
	public Diagram handleAfterDeserialize(Diagram in) {
		logger.info("handleAfterDeserialize");

		if (versionUnderEqual(in, 0, 9, 0)) {
			logger.info("apply patch 0.9.0");
			for (AbstractEntityModel model : in.query().listEntityModel()) {
				if (model instanceof Entity) {
					logger.info("Entity found. {}", model.getName());
					Entity entity = (Entity) model;
					entity.setIdentifier(entity.getIdentifier());
				}
				if (model instanceof Detail) {
					logger.info("Detail found. {}", model.getName());
					Detail detail = (Detail) model;
					detail.setDetailIdentifier(detail.getDetailIdentifier());
				}
			}
		}
		return in;
	}

}
