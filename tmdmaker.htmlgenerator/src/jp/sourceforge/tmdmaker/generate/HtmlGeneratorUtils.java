/*
 * Copyright 2009-2010 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package jp.sourceforge.tmdmaker.generate;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.log.NullLogChute;

/**
 * HtmlGeneratorのUtilityクラス
 * 
 * @author nakaG
 * 
 */
public class HtmlGeneratorUtils {
	/**
	 * テンプレートを適用する。
	 * 
	 * @param templateName
	 *            Velocityのテンプレート名
	 * @param clazz
	 *            リソース取得先のクラス
	 * @param output
	 *            出力先ファイル
	 * @param context
	 *            VelocityContext
	 * @throws Exception
	 *             I/O系例外やVelocityの例外
	 */
	public static void applyTemplate(String templateName, Class<?> clazz,
			File output, VelocityContext context) throws Exception {
		StringWriter writer = new StringWriter();

		InputStreamReader reader = new InputStreamReader(clazz
				.getResourceAsStream(templateName), "UTF-8");
		Velocity.evaluate(context, writer, templateName, reader);

		FileOutputStream out = new FileOutputStream(output);
		out.write(writer.getBuffer().toString().getBytes("UTF-8"));

		close(out);
		close(writer);
		close(reader);

	}

	/**
	 * 入力ストリームの内容を出力ストリームへ流す。
	 * 
	 * @param in
	 *            入力ストリーム
	 * @param out
	 *            出力ストリーム
	 */
	public static void copyStream(InputStream in, OutputStream out) {
		try {
			byte[] buf = new byte[in.available()];
			in.read(buf);
			out.write(buf);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			close(in);
			close(out);
		}
	}

	/**
	 * ストリームのクローズ
	 * 
	 * @param closeable
	 *            主にストリーム
	 */
	public static void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (Exception ex) {
			}
		}
	}

	/**
	 * 初期化済みVelocityContextを取得する。
	 * 
	 * @return VelocityContext 初期化したVelocityContext
	 */
	public static VelocityContext getVecityContext() {
		Velocity.addProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,
				NullLogChute.class.getName());
		try {
			Velocity.init();
		} catch (Exception e) {
			e.printStackTrace();
			throw new HtmlGeneratorRuntimeException(e);
		}
		System.out.println("init");
		VelocityContext context = new VelocityContext();
		context.put("esc", new EscapeTool());
		return context;
	}

	/**
	 * 共通のCSSを出力する
	 * 
	 * @param rootDir
	 * @throws IOException
	 */
	public static void outputCSS(String rootDir) throws IOException {
		HtmlGeneratorUtils.copyStream(Activator.class
				.getResourceAsStream("stylesheet.css"), new FileOutputStream(
				new File(rootDir, "stylesheet.css")));
	}

}
