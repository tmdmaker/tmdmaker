/*
 * Copyright 2009-2012 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package jp.sourceforge.tmdmaker.serializer.handler.patch;

import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.ModelElement;
import jp.sourceforge.tmdmaker.model.VirtualEntity;
import jp.sourceforge.tmdmaker.model.VirtualEntityType;

/**
 * モデルのバージョン0.2.6へのバージョンアップ
 * 
 * @author nakaG
 * 
 */
public class Patch026SerializerHandler extends AbstractSerializerHandler {
	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.persistence.handler.SerializerHandler#
	 *      handleAfterDeserialize(jp.sourceforge.tmdmaker.model.Diagram)
	 */
	@Override
	public Diagram handleAfterDeserialize(Diagram in) {
		logger.info("handleAfterDeserialize");
		if (versionUnderEqual(in, 0, 2, 5)) {
			logger.info("apply patch 0.2.6");
			for (ModelElement model : in.getChildren()) {
				if (model instanceof VirtualEntity) {
					VirtualEntity entity = (VirtualEntity) model;
					if (entity.getVirtualEntityType() == null) {
						entity.setVirtualEntityType(VirtualEntityType.NORMAL);
					}
				}
			}
		}
		return in;
	}
}
