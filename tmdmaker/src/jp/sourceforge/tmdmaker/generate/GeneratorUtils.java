/*
 * Copyright 2009 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * @author hiro
 *
 */
public class GeneratorUtils {
	public static void applyTemplate(String templateName, Class<?> clazz, File output,
			VelocityContext context) throws Exception {
		StringWriter writer = new StringWriter();

		InputStreamReader reader = new InputStreamReader(
				clazz.getResourceAsStream(templateName), "UTF-8");
		Velocity.evaluate(context, writer, templateName, reader);

		FileOutputStream out = new FileOutputStream(output);
		out.write(writer.getBuffer().toString().getBytes("UTF-8"));

		close(out);
		close(writer);
		close(reader);

	}

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
	public static void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (Exception ex) {
			}
		}
	}
}
