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

import org.tmdmaker.core.model.Detail;
import org.tmdmaker.core.model.Diagram;

/**
 * モデルのバージョン0.3.1へのバージョンアップ
 * 
 * @author nakaG
 * 
 */
public class Patch031SerializerHandler extends AbstractSerializerHandler {

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.serializer.handler.patch.AbstractSerializerHandler#handleBeforeDeserialize(java.lang.String)
	 */
	@Override
	public String handleBeforeDeserialize(String in) {
		logger.info("handleBeforeDeserialize");

		if (versionUnderEqual(in, 0, 3, 0)) {
			return fixIdentifierSpell(in);
		}
		return in;
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.serializer.handler.patch.AbstractSerializerHandler#handleAfterDeserialize(org.tmdmaker.core.model.Diagram)
	 */
	@Override
	public Diagram handleAfterDeserialize(Diagram in) {
		if (versionUnderEqual(in, 0, 3, 0)) {
			logger.info("apply patch 0.3.1");
			for (Detail model : in.query().listEntityModel(Detail.class)) {
				model.setDetailIdentifierEnabled(true);
			}
		}

		return in;
	}

	private String fixIdentifierSpell(String in) {
		String out1 = in.replace("Identifires", "Identifiers");
		String out2 = out1.replace("identifires", "identifiers");
		String out3 = out2.replace("Identifieres", "Identifiers");
		String out4 = out3.replace("identifieres", "identifiers");
		return out4;
	}

}
