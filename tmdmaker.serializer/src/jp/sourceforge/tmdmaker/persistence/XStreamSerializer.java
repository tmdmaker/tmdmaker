/*
 * Copyright 2009-2015 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package jp.sourceforge.tmdmaker.persistence;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import jp.sourceforge.tmdmaker.extension.PluginExtensionPointFactory;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.persistence.SerializationException;
import jp.sourceforge.tmdmaker.model.persistence.Serializer;
import jp.sourceforge.tmdmaker.persistence.handler.SerializerHandler;

import com.thoughtworks.xstream.XStream;

/**
 * XStreamを使ったTMDシリアライズ用クラス
 * 
 * @author nakaG
 * 
 */
public class XStreamSerializer implements Serializer {
	/** ファイルのエンコーディング */
	private static final String ENCODING = "UTF-8";

	/** handlerを取得するためのfactory */
	private PluginExtensionPointFactory<SerializerHandler> factory = new PluginExtensionPointFactory<SerializerHandler>(
			Activator.PLUGIN_ID + ".handler");

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.persistence.Serializer#serialize(jp.sourceforge.tmdmaker.model.Diagram)
	 */
	@Override
	public InputStream serialize(Diagram obj) {
		try {
			Diagram before = fireBeforeSerialize(obj);
			return serializeAsStream(before, before.getClass().getClassLoader());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new SerializationException(e);
		}
	}

	/**
	 * TMDシリアライズ
	 * 
	 * @param obj
	 *            TMダイアグラム
	 * @param loader
	 *            クラスローダー
	 * @return TMD入力ストリーム
	 * @throws UnsupportedEncodingException
	 */
	private InputStream serializeAsStream(Object obj, ClassLoader loader)
			throws UnsupportedEncodingException {
		String xml = serializeAsString(obj, loader);
		return new ByteArrayInputStream(xml.getBytes(ENCODING));
	}

	/**
	 * TMDシリアライズ
	 * 
	 * @param obj
	 *            TMダイアグラム
	 * @param loader
	 *            クラスローダー
	 * @return TMダイアグラム文字列
	 * @throws UnsupportedEncodingException
	 */
	private String serializeAsString(Object obj, ClassLoader loader) {
		XStream xstream = new XStream();
		xstream.setClassLoader(loader);
		String result = "<?xml version=\"1.0\" encoding=\"" + ENCODING
				+ "\"?>\n" + xstream.toXML(obj);

		return fireAfterSerialize(result);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.persistence.Serializer#deserialize(java.io.InputStream)
	 */
	@Override
	public Diagram deserialize(InputStream in) {
		try {
			String xml = loadStream(in, ENCODING);
			System.out.println(getVersionFromXml(xml));

			String converted = fireBeforeDeserialize(xml);
			Diagram diagram = (Diagram) deserialize(new ByteArrayInputStream(
					converted.getBytes(ENCODING)), this.getClass()
					.getClassLoader());
			fireAfterDeserialize(diagram);

			return diagram;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new SerializationException(e);
		}
	}

	private Diagram fireBeforeSerialize(Diagram diagram) {
		for (SerializerHandler c : factory.getInstances()) {
			c.handleBeforeSerialize(diagram);
		}
		return diagram;
	}

	private String fireAfterSerialize(String in) {
		String converted = in;
		for (SerializerHandler c : factory.getInstances()) {
			converted = c.handleAfterSerialize(converted);
		}
		return converted;

	}

	private String fireBeforeDeserialize(String xml) {
		String converted = xml;
		for (SerializerHandler c : factory.getInstances()) {
			converted = c.handleBeforeDeserialize(converted);
		}
		return converted;
	}

	private void fireAfterDeserialize(Diagram diagram) {
		for (SerializerHandler c : factory.getInstances()) {
			c.handleAfterDeserialize(diagram);
		}
	}

	private String getVersionFromXml(String xml) {
		final String VERSION_START_TAG = "<version>";
		final String VERSION_END_TAG = "</version>";

		if (xml == null || xml.length() == 0) {
			return "";
		}
		if (xml.indexOf(VERSION_START_TAG) == -1) {
			return "";
		}
		return xml.substring(
				xml.indexOf(VERSION_START_TAG) + VERSION_START_TAG.length(),
				xml.indexOf(VERSION_END_TAG));
	}

	/**
	 * TMDデシリアライズ
	 * 
	 * @param in
	 *            TMDの入力ストリーム
	 * @param loader
	 *            クラスローダー
	 * @return TMD
	 * @throws UnsupportedEncodingException
	 */
	private Object deserialize(InputStream in, ClassLoader loader)
			throws UnsupportedEncodingException {
		XStream xstream = new XStream();
		xstream.setClassLoader(loader);

		return xstream.fromXML(new InputStreamReader(in, ENCODING));
	}

	private String loadStream(InputStream in, String encoding) {
		try {
			byte[] buf;
			buf = new byte[in.available()];
			in.read(buf);
			return new String(buf, encoding);
		} catch (IOException e) {
			e.printStackTrace();
			throw new SerializationException(e);
		} finally {
			try {
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
