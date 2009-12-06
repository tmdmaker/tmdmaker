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
package jp.sourceforge.tmdmaker.generate.attributelist;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.CombinationTable;
import jp.sourceforge.tmdmaker.model.Detail;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.MappingList;
import jp.sourceforge.tmdmaker.model.ModelElement;
import jp.sourceforge.tmdmaker.model.MultivalueAndSuperset;
import jp.sourceforge.tmdmaker.model.MultivalueOrEntity;
import jp.sourceforge.tmdmaker.model.RecursiveTable;
import jp.sourceforge.tmdmaker.model.SubsetEntity;
import jp.sourceforge.tmdmaker.model.VirtualEntity;
import jp.sourceforge.tmdmaker.model.VirtualSuperset;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.log.NullLogChute;

/**
 * アトリビュートリストをHTMLで出力するクラス
 * 
 * @author nakaG
 * 
 */
public class AttributeListHtmlGenerator {
	// 未使用。別機能で使うかも
	private Map<Class<? extends AbstractEntityModel>, String> displayEntityType = new HashMap<Class<? extends AbstractEntityModel>, String>() {
		{
			put(CombinationTable.class, "対照表");
			put(Detail.class, "明細");
			put(MappingList.class, "対応表");
			put(MultivalueAndSuperset.class, "概念的スーパーセット");
			put(MultivalueOrEntity.class, "多値のOR");
			put(RecursiveTable.class, "再帰表");
			put(SubsetEntity.class, "サブセット");
			put(VirtualEntity.class, "みなしエンティティ");
			put(VirtualSuperset.class, "みなしスーパーセット");
		}
	};

	/**
	 * HTML生成
	 * 
	 * @param rootDir
	 *            出力先ディレクトリ
	 * @param model
	 *            対象ダイアグラム
	 * @throws Exception
	 *             I/O系例外
	 */
	public void generate(String rootDir, Diagram model) throws Exception {
		assert model != null;
		System.out.println("generate");
		Velocity.addProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,
				NullLogChute.class.getName());
		Velocity.init();
		System.out.println("init");
		VelocityContext context = new VelocityContext();

		copyStream(AttributeListHtmlGenerator.class
				.getResourceAsStream("index.html"), new FileOutputStream(
				new File(rootDir, "index.html")));
		// context.put("entities", findAvailableModel(model));
		context.put("esc", new EscapeTool());
		Map<Attribute, AbstractEntityModel> attributes = findAllAttributes(model);

		context.put("entities", findAvailableModel(model));
		applyTemplate("summary.html", new File(rootDir, "summary.html"),
				context);

		context.put("attributes", attributes.keySet());

		applyTemplate("attribute_list.html", new File(rootDir,
				"attribute_list.html"), context);
		File attributesDir = new File(rootDir, "attributes");
		attributesDir.mkdir();
		for (Map.Entry<Attribute, AbstractEntityModel> entry : attributes
				.entrySet()) {
			Attribute attribute = entry.getKey();
			AbstractEntityModel entity = entry.getValue();
			context.put("attribute", attribute);
			context.put("entity", entity);
			if (entity instanceof Entity) {
				context.put("entityType", ((Entity) entity).getEntityType()
						.getTypeName());
			} else {
				context.remove("entityType");
			}
			applyTemplate("attribute.html", new File(attributesDir, attribute
					.getName()
					+ ".html"), context);
		}
	}

	private void applyTemplate(String templateName, File output,
			VelocityContext context) throws Exception {
		StringWriter writer = new StringWriter();

		InputStreamReader reader = new InputStreamReader(
				AttributeListHtmlGenerator.class
						.getResourceAsStream(templateName), "UTF-8");
		Velocity.evaluate(context, writer, templateName, reader);

		FileOutputStream out = new FileOutputStream(output);
		out.write(writer.getBuffer().toString().getBytes("UTF-8"));

		close(out);
		close(writer);
		close(reader);

	}

	private List<AbstractEntityModel> findAvailableModel(Diagram diagram) {
		List<AbstractEntityModel> entities = new ArrayList<AbstractEntityModel>();

		for (ModelElement m : diagram.getChildren()) {
			if (m instanceof AbstractEntityModel) {
				entities.add((AbstractEntityModel) m);
			}
		}
		return entities;
	}

	private Map<Attribute, AbstractEntityModel> findAllAttributes(
			Diagram diagram) {
		Map<Attribute, AbstractEntityModel> attributes = new TreeMap<Attribute, AbstractEntityModel>(
				new Comparator<Attribute>() {

					/**
					 * {@inheritDoc}
					 * 
					 * @see java.util.Comparator#compare(java.lang.Object,
					 *      java.lang.Object)
					 */
					@Override
					public int compare(Attribute o1, Attribute o2) {
						return o1.getName().compareTo(o2.getName());

					}
				});

		for (AbstractEntityModel m : findAvailableModel(diagram)) {
			if (m instanceof Entity) {
				Entity e = (Entity) m;
				attributes.put(e.getIdentifier(), e);
			}
			if (m instanceof Detail) {
				Detail d = (Detail) m;
				attributes.put(d.getDetailIdentifier(), d);
			}
			for (Attribute a : m.getAttributes()) {
				attributes.put(a, m);
			}
		}
		return attributes;
	}

	private void copyStream(InputStream in, OutputStream out) {
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

	public void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (Exception ex) {
			}
		}
	}
}
