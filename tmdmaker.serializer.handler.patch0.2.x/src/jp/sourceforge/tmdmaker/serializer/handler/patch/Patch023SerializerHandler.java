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

import java.util.List;

import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Entity2SubsetTypeRelationship;
import jp.sourceforge.tmdmaker.model.ModelElement;
import jp.sourceforge.tmdmaker.model.RelatedRelationship;
import jp.sourceforge.tmdmaker.model.Version;
import jp.sourceforge.tmdmaker.persistence.handler.SerializerHandler;

/**
 * モデルのバージョン0.2.3へのバージョンアップ
 * 
 * @author nakaG
 */
public class Patch023SerializerHandler implements SerializerHandler {

	/**
	 * コンストラクタ
	 */
	public Patch023SerializerHandler() {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.persistence.handler.SerializerHandler#
	 *      handleBeforeDeserialize(java.lang.String)
	 */
	@Override
	public String handleBeforeDeserialize(String in) {
		System.out.println(getClass() + "#handleBeforeDeserialize");
		return in;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.persistence.handler.SerializerHandler#
	 *      handleAfterDeserialize(jp.sourceforge.tmdmaker.model.Diagram)
	 */
	@Override
	public Diagram handleAfterDeserialize(Diagram in) {
		System.out.println(getClass() + "#handleAfterDeserialize");

		Version version = new Version(in.getVersion());
		// 0.2.xのバージョン
		if (version.getMajorVersion() == 0 && version.getMinorVersion() == 2
				&& version.getServiceNo() <= 2) {
			System.out.println("need to convert model.");
			for (ModelElement o : in.getChildren()) {
				if (o instanceof AbstractEntityModel) {
					AbstractEntityModel model = (AbstractEntityModel) o;
					convert(model);
				}
			}
		} else {
			System.out.println("no need to convert.");
		}
		return in;
	}

	private void convert(AbstractEntityModel model) {
		convertRelatedRelationships(model.getModelSourceConnections());
		convertRelatedRelationships(model.getModelTargetConnections());
	}

	private void convertRelatedRelationships(
			List<AbstractConnectionModel> connections) {
		for (AbstractConnectionModel c : connections) {
			if (c instanceof RelatedRelationship) {
				System.out.println("convertRelatedRelationships():"
						+ c.toString());
				convertLocationIfNeeds(c);
			}
			if (c instanceof Entity2SubsetTypeRelationship) {
				System.out.println("convertRelatedRelationships():"
						+ c.toString());
				convertLocationIfNeeds(c);
			}
		}
	}

	private void convertLocationIfNeeds(AbstractConnectionModel connection) {
		if (connection.getSourceXp() == 0 && connection.getSourceYp() == 0
				|| connection.getTargetXp() == 0
				&& connection.getTargetYp() == 0) {
			connection.setSourceLocationp(-1, -1);
			connection.setTargetLocationp(-1, -1);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.persistence.handler.SerializerHandler#
	 *      handleBeforeSerialize(jp.sourceforge.tmdmaker.model.Diagram)
	 */
	@Override
	public Diagram handleBeforeSerialize(Diagram diagram) {
		System.out.println(getClass() + "#handleBeforeSerialize");
		return diagram;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.persistence.handler.SerializerHandler#
	 *      handleAfterSerialize(java.lang.String)
	 */
	@Override
	public String handleAfterSerialize(String in) {
		System.out.println(getClass() + "#handleAfterSerialize");
		return in;
	}

}
