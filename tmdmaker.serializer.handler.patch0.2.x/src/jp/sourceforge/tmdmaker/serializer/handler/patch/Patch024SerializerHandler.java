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

import java.util.ArrayList;

import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.IAttribute;
import jp.sourceforge.tmdmaker.persistence.handler.SerializerHandler;

/**
 * モデルのバージョン0.2.3へのバージョンアップ
 * 
 * @author nakaG
 */
public class Patch024SerializerHandler implements SerializerHandler {

	/**
	 * コンストラクタ
	 */
	public Patch024SerializerHandler() {
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
		if (in.getCommonAttributes() == null) {
			in.setCommonAttributes(new ArrayList<IAttribute>());
		}
		return in;
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
