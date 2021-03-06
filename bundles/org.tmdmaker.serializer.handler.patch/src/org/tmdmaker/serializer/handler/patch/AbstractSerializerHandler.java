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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tmdmaker.core.model.Diagram;
import org.tmdmaker.core.model.Version;
import org.tmdmaker.persistence.handler.SerializerHandler;

/**
 * Handlerの抽象クラス.
 * 
 * @author nakag
 *
 */
public abstract class AbstractSerializerHandler implements SerializerHandler {
	protected Logger logger;

	/**
	 * the constructor.
	 */
	public AbstractSerializerHandler() {
		logger = LoggerFactory.getLogger(getClass());
		logger.debug("Handler Start.");
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.persistence.handler.SerializerHandler#handleBeforeDeserialize(java.lang.String)
	 */
	@Override
	public String handleBeforeDeserialize(String in) {
		logger.info("handleBeforeDeserialize");
		return in;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.persistence.handler.SerializerHandler#handleAfterDeserialize(org.tmdmaker.core.model.Diagram)
	 */
	@Override
	public Diagram handleAfterDeserialize(Diagram in) {
		logger.info("handleAfterDeserialize");
		return in;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.persistence.handler.SerializerHandler#handleBeforeSerialize(org.tmdmaker.core.model.Diagram)
	 */
	@Override
	public Diagram handleBeforeSerialize(Diagram diagram) {
		logger.info("handleBeforeSerialize");
		return diagram;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.persistence.handler.SerializerHandler#handleAfterSerialize(java.lang.String)
	 */
	@Override
	public String handleAfterSerialize(String in) {
		logger.info("handleAfterSerialize");
		return in;
	}

	/**
	 * モデルのバージョンが指定値以下か判定する
	 * 
	 * @param in
	 *            モデル
	 * @param major
	 * @param minor
	 * @param serviceNo
	 * @return 指定以下のバージョンの場合にtrueを返す
	 */
	protected boolean versionUnderEqual(Diagram in, int major, int minor, int serviceNo) {
		return new Version(in.getVersion()).versionUnderEqual(major, minor, serviceNo);
	}

	/**
	 * モデルのバージョンが指定値以下か判定する
	 * 
	 * @param in
	 *            モデルのシリアライズ
	 * @param major
	 * @param minor
	 * @param serviceNo
	 * @return 指定以下のバージョンの場合にtrueを返す
	 */
	protected boolean versionUnderEqual(String in, int major, int minor, int serviceNo) {
		Pattern pattern = Pattern.compile("(<version>)(.*?)(</version>)", Pattern.DOTALL);
		Matcher matcher = pattern.matcher(in);
		if (matcher.find()) {
			String versionString = matcher.group(2);
			return new Version(versionString).versionUnderEqual(major, minor, serviceNo);
		}
		return false;
	}
}