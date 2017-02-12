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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Version;
import jp.sourceforge.tmdmaker.persistence.handler.SerializerHandler;

/**
 * Handlerの抽象クラス.
 * 
 * @author nakag
 *
 */
public abstract class AbstractSerializerHandler implements SerializerHandler {
	protected static Logger logger;

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
	 * @see jp.sourceforge.tmdmaker.persistence.handler.SerializerHandler#handleBeforeDeserialize(java.lang.String)
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
	 * @see jp.sourceforge.tmdmaker.persistence.handler.SerializerHandler#handleAfterDeserialize(jp.sourceforge.tmdmaker.model.Diagram)
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
	 * @see jp.sourceforge.tmdmaker.persistence.handler.SerializerHandler#handleBeforeSerialize(jp.sourceforge.tmdmaker.model.Diagram)
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
	 * @see jp.sourceforge.tmdmaker.persistence.handler.SerializerHandler#handleAfterSerialize(java.lang.String)
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
		return versionUnderEqualByVersionString(in.getVersion(), major, minor, serviceNo);
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
			return versionUnderEqualByVersionString(versionString, major, minor, serviceNo);
		}
		return false;
	}

	private boolean versionUnderEqualByVersionString(String versionString, int major, int minor, int serviceNo) {
		Version version = new Version(versionString);
		logger.info("version = " + version.getValue());
		
		if (version.getMajorVersion() != major)
		{
			return version.getMajorVersion() < major;
		}
		else
		{
			if (version.getMinorVersion() != minor)
			{
				return version.getMinorVersion() < minor;
			}
			else
			{
				if (version.getServiceNo() != serviceNo)
				{
					return version.getServiceNo() < serviceNo;
				}
				else
				{
					return true;
				}
			}
		}
	}
}