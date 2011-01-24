/*
 * Copyright 2009-2011 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package jp.sourceforge.tmdmaker.persistent.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import jp.sourceforge.tmdmaker.persistent.SerializationException;
import jp.sourceforge.tmdmaker.persistent.Serializer;

import com.thoughtworks.xstream.XStream;

/**
 * XStreamを使ったダイアグラムのシリアライズ・デシリアライズ用クラス
 * 
 * @author nakaG
 * 
 */
public class XStreamSerializer implements Serializer {
	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.persistent.Serializer#serialize(java.lang.Object)
	 */
	@Override
	public String serialize(Object obj) {
		return serialize(obj, obj.getClass().getClassLoader());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @throws
	 * 
	 * @see jp.sourceforge.tmdmaker.persistent.Serializer#serializeStream(java.lang.Object)
	 */
	@Override
	public InputStream serializeStream(Object obj) {
		try {
			return serializeStream(obj, obj.getClass().getClassLoader());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new SerializationException(e);
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.persistent.Serializer#deserialize(java.io.InputStream)
	 */
	@Override
	public Object deserialize(InputStream in) {
		try {
			return deserialize(in, this.getClass().getClassLoader());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new SerializationException(e);
		}
	}

	private String serialize(Object obj, ClassLoader loader) {
		XStream xstream = new XStream();
		xstream.setClassLoader(loader);
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ xstream.toXML(obj);
	}

	/**
	 * ダイアグラムのシリアライズ
	 * 
	 * @param obj
	 *            ダイアグラム
	 * @param loader
	 *            クラスローダ
	 * @return ダイアグラムの入力ストリーム
	 * @throws UnsupportedEncodingException
	 */
	private InputStream serializeStream(Object obj, ClassLoader loader)
			throws UnsupportedEncodingException {
		String xml = serialize(obj, loader);
		return new ByteArrayInputStream(xml.getBytes("UTF-8"));
	}

	/**
	 * ダイアグラムのデシリアライズ
	 * 
	 * @param in
	 *            ダイアグラムの入力ストリーム
	 * @param loader
	 *            クラスローダ
	 * @return ダイアグラム
	 * @throws UnsupportedEncodingException
	 */
	private Object deserialize(InputStream in, ClassLoader loader)
			throws UnsupportedEncodingException {
		XStream xstream = new XStream();
		xstream.setClassLoader(loader);
		return xstream.fromXML(new InputStreamReader(in, "UTF-8"));
	}
}
