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
package org.tmdmaker.sphinx.utilities

import java.io.Closeable
import java.io.File
import java.io.InputStream
import org.tmdmaker.model.generate.GeneratorRuntimeException
//import org.slf4j.Logger
//import org.slf4j.LoggerFactory
import org.tmdmaker.core.model.AbstractEntityModel
import java.io.FileWriter
import java.io.IOException
import java.io.FileOutputStream

/** 
 * Utilityクラス
 * @author tohosaku
 */
class SphinxUtils {
	/** 
	 * logging 
	 */
	//static Logger logger = LoggerFactory.getLogger(typeof(SphinxUtils))

	/** 
	 * 入力ストリームの内容を出力ストリームへ流す。
	 * @param in 入力ストリーム
	 * @param file 出力ファイル
	 */
	def static void copyTo(InputStream in, File file) {
		var FileOutputStream out = null
		try {
			out = new FileOutputStream(file)
			var byte[] buf = newByteArrayOfSize(in.available())
			in.read(buf)
			out.write(buf)
		} catch (Exception e) {
			//logger.error(e.getMessage())
			throw new GeneratorRuntimeException(e)
		} finally {
			close(in)
			close(out)
		}
	}

	/** 
	 * ストリームのクローズ
	 * @param closeable 主にストリーム
	 */
	def static void close(Closeable closeable) {
		try {
			closeable?.close()
		} catch (Exception e) {
			//logger.warn(e.getMessage())
		}
	}
	
	/**
	 * ER図のファイル名から拡張子を除いて返す
	 * @param model モデル
	 */
	def static diagram_name(AbstractEntityModel model) {
		val name = model.diagram.name
		name.substring(0,name.lastIndexOf('.'))
	}
	
	/**
	 * テキストを指定したファイルに書き込む
	 * @param content 出力内容
	 * @param file 出力先
	 */
	def static writeTo(CharSequence content, File file){
		var FileWriter filewriter = null
		
		try {
			filewriter = new FileWriter(file)
			filewriter.write(content.toString)
		} catch (IOException e) {
			println(e);
		} finally {
			try {
				filewriter?.close()
			} catch (IOException e) {
				println(e)
			}
		}
	}
}
