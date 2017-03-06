/*
 * Copyright 2009-2017 TMD-Maker Project <http://tmdmaker.osdn.jp/>
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

import java.util.ArrayList;
import java.util.List;

import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Entity2VirtualSupersetTypeRelationship;
import jp.sourceforge.tmdmaker.model.ModelElement;
import jp.sourceforge.tmdmaker.model.RelatedRelationship;
import jp.sourceforge.tmdmaker.model.VirtualSuperset;
import jp.sourceforge.tmdmaker.model.VirtualSupersetType;
import jp.sourceforge.tmdmaker.model.VirtualSupersetType2VirtualSupersetRelationship;

/**
 * モデルのバージョン0.4.0へのバージョンアップ
 * 
 * @author nakaG
 * 
 */
public class Patch040SerializerHandler extends AbstractSerializerHandler {

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.serializer.handler.patch.AbstractSerializerHandler#handleAfterDeserialize(jp.sourceforge.tmdmaker.model.Diagram)
	 */
	@Override
	public Diagram handleAfterDeserialize(Diagram in) {
		if (versionUnderEqual(in, 0, 4, 0)) {
			logger.info("apply patch 0.4.0");
			for (ModelElement o : in.getChildren()) {
				if (o instanceof AbstractEntityModel) {
					AbstractEntityModel model = (AbstractEntityModel) o;
					convertRelatedRelationships(model);
				}
				if (o instanceof VirtualSuperset) {
					convertVirtualSupersetType2VirtualSupersetRelationship((VirtualSuperset)o);
				}
			}
		}

		return in;
	}

	private void convertRelatedRelationships(AbstractEntityModel model) {
		convertNullLocationPoint(model.getModelSourceConnections());
		convertNullLocationPoint(model.getModelTargetConnections());
	}

	private void convertNullLocationPoint(List<AbstractConnectionModel> connections) {
		AnchorConstraintMigrator converter = new AnchorConstraintMigrator();
		for (AbstractConnectionModel c : connections) {
			converter.convertNullLocationPoint(c);
		}
	}

	private void convertVirtualSupersetType2VirtualSupersetRelationship(VirtualSuperset superset) {
		AbstractConnectionModel acm = superset.getModelTargetConnections().get(0);
		if (acm instanceof VirtualSupersetType2VirtualSupersetRelationship) {
			return;
		}
		RelatedRelationship rr = (RelatedRelationship) acm;
		VirtualSupersetType type = (VirtualSupersetType) rr.getSource();
		VirtualSupersetType2VirtualSupersetRelationship newRelationship = new VirtualSupersetType2VirtualSupersetRelationship();
		List<Entity2VirtualSupersetTypeRelationship> newSubsetList = new ArrayList<Entity2VirtualSupersetTypeRelationship>();
		for (AbstractConnectionModel c : type.getModelTargetConnections()) {
			newSubsetList.add((Entity2VirtualSupersetTypeRelationship) c);
		}
		rr.disconnect();
		newRelationship.setSource(type);
		newRelationship.setTarget(superset);
		newRelationship.setSubset2typeRelationshipList(newSubsetList);
		newRelationship.connect();
	}
}
