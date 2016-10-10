/*
 * Copyright 2016 TMD-Maker Project <http://tmdmaker.osdn.jp/>
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
 
package jp.sourceforge.tmdmaker.sphinx.utilities

import java.io.Closeable
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import jp.sourceforge.tmdmaker.model.generate.GeneratorRuntimeException
import org.apache.velocity.VelocityContext
import org.apache.velocity.app.Velocity
import org.apache.velocity.runtime.RuntimeConstants
import org.apache.velocity.runtime.log.NullLogChute
//import org.slf4j.Logger
//import org.slf4j.LoggerFactory
import java.io.StringWriter
import java.io.InputStreamReader
import jp.sourceforge.tmdmaker.model.AbstractEntityModel

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
	 * @param in入力ストリーム
	 * @param out出力ストリーム
	 */
	def static void copyStream(InputStream in, OutputStream out) {
		try {
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
	 * 初期化済みVelocityContextを取得する。
	 * @return VelocityContext 初期化したVelocityContext
	 */
	def static VelocityContext getVecityContext() {
		Velocity.addProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, typeof(NullLogChute).name)
		try {
			Velocity.init()
		} catch (Exception e) {
			//logger.error(e.getMessage())
			throw new GeneratorRuntimeException(e)
		}

		var context = new VelocityContext
		return context
	}
	
	/** 
	 * テンプレートを適用する。
	 * @param templateNameVelocityのテンプレート名
	 * @param clazzリソース取得先のクラス
	 * @param output出力先ファイル
	 * @param contextVelocityContext
	 * @throws ExceptionI/O系例外やVelocityの例外
	 */
	def static applyTemplate(VelocityContext context, String templateName, Class<?> clazz, File output) throws Exception {
		var writer = new StringWriter()
		var reader = new InputStreamReader(clazz.getResourceAsStream(templateName), "UTF-8")
		Velocity.evaluate(context, writer, templateName, reader)
		var out = new FileOutputStream(output)
		out.write(writer.getBuffer().toString().getBytes("UTF-8"))
		close(out)
		close(writer)
		close(reader)
	}
	
	/** 
	 * ストリームのクローズ
	 * @param closeable主にストリーム
	 */
	def static void close(Closeable closeable) {
		if (closeable !== null) {
			try {
				closeable.close()
			} catch (Exception e) {
				//logger.warn(e.getMessage())
			}
		}
	}
	
	/**
	 * ER図のファイル名から拡張子を除いて返す
	 */
	def static diagram_name(AbstractEntityModel model) {
		val name = model.diagram.name
		name.substring(0,name.lastIndexOf('.'))
	}
}
