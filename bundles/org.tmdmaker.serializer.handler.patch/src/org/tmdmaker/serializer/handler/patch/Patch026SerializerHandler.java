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
package org.tmdmaker.serializer.handler.patch;

import org.tmdmaker.core.model.Diagram;
import org.tmdmaker.core.model.VirtualEntity;
import org.tmdmaker.core.model.VirtualEntityType;

/**
 * モデルのバージョン0.2.6へのバージョンアップ
 * 
 * @author nakaG
 * 
 */
public class Patch026SerializerHandler extends AbstractSerializerHandler {

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.serializer.handler.patch.AbstractSerializerHandler#handleAfterDeserialize(org.tmdmaker.core.model.Diagram)
	 */
	@Override
	public Diagram handleAfterDeserialize(Diagram in) {
		logger.info("handleAfterDeserialize");
		if (versionUnderEqual(in, 0, 2, 5)) {
			logger.info("apply patch 0.2.6");
			for (VirtualEntity model : in.query().listEntityModel(VirtualEntity.class)) {
				if (model.getVirtualEntityType() == null) {
					model.setVirtualEntityType(VirtualEntityType.NORMAL);
				}
			}
		}
		return in;
	}
}
