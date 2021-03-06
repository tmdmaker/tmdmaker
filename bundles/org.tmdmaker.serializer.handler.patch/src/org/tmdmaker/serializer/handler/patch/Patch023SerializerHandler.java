/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org>
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

import java.util.List;

import org.tmdmaker.core.model.AbstractConnectionModel;
import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.Diagram;
import org.tmdmaker.core.model.Entity2SubsetTypeRelationship;
import org.tmdmaker.core.model.RelatedRelationship;

/**
 * モデルのバージョン0.2.3へのバージョンアップ
 * 
 * @author nakaG
 */
public class Patch023SerializerHandler extends AbstractSerializerHandler {

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.serializer.handler.patch.AbstractSerializerHandler#handleAfterDeserialize(org.tmdmaker.core.model.Diagram)
	 */
	@Override
	public Diagram handleAfterDeserialize(Diagram in) {
		logger.info("handleAfterDeserialize");

		if (versionUnderEqual(in, 0, 2, 2)) {
			logger.info("apply patch 0.2.3");
			for (AbstractEntityModel model : in.query().listEntityModel()) {
				convert(model);
			}
		}
		return in;
	}

	private void convert(AbstractEntityModel model) {
		convertRelatedRelationships(model.getModelSourceConnections());
		convertRelatedRelationships(model.getModelTargetConnections());
	}

	private void convertRelatedRelationships(List<AbstractConnectionModel> connections) {
		AnchorConstraintMigrator converter = new AnchorConstraintMigrator();
		for (AbstractConnectionModel c : connections) {
			if (c instanceof RelatedRelationship) {
				logger.debug("convertRelatedRelationships():{}", c);
				converter.convertNullLocationPoint(c);
			}
			if (c instanceof Entity2SubsetTypeRelationship) {
				logger.debug("convertEntity2SubsetTypeRelationships():{}", c);
				converter.convertNullLocationPoint(c);
			}
		}
	}
}
