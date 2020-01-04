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

/**
 * モデルのバージョン0.3.0へのバージョンアップ
 * 
 * @author nakaG
 * 
 */
public class Patch030SerializerHandler extends AbstractSerializerHandler {

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.serializer.handler.patch.AbstractSerializerHandler#handleBeforeDeserialize(java.lang.String)
	 */
	@Override
	public String handleBeforeDeserialize(String in) {
		logger.info("handleBeforeDeserialize");

		if (versionUnderEqual(in, 0, 2, 10)) {
			return fixSurrogateKeySpell(in);
		}
		return in;
	}

	private String fixSurrogateKeySpell(String in) {
		String out1 = in.replace("SarogateKey", "SurrogateKey");
		String out2 = out1.replace("sarogateKey", "surrogateKey");
		return out2;
	}

}
