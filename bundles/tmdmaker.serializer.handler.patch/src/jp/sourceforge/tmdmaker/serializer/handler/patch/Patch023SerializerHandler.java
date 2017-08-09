/*
 * Copyright 2009-2017 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

import java.util.List;

import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Entity2SubsetTypeRelationship;
import jp.sourceforge.tmdmaker.model.ModelElement;
import jp.sourceforge.tmdmaker.model.RelatedRelationship;

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
	 * @see jp.sourceforge.tmdmaker.serializer.handler.patch.AbstractSerializerHandler#handleAfterDeserialize(jp.sourceforge.tmdmaker.model.Diagram)
	 */
	@Override
	public Diagram handleAfterDeserialize(Diagram in) {
		logger.info("handleAfterDeserialize");

		if (versionUnderEqual(in, 0, 2, 2)) {
			logger.info("apply patch 0.2.3");
			for (ModelElement o : in.getChildren()) {
				if (o instanceof AbstractEntityModel) {
					AbstractEntityModel model = (AbstractEntityModel) o;
					convert(model);
				}
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
				logger.debug("convertRelatedRelationships():" + c.toString());
				converter.convertNullLocationPoint(c);
			}
			if (c instanceof Entity2SubsetTypeRelationship) {
				logger.debug("convertEntity2SubsetTypeRelationships():" + c.toString());
				converter.convertNullLocationPoint(c);
			}
		}
	}
}
