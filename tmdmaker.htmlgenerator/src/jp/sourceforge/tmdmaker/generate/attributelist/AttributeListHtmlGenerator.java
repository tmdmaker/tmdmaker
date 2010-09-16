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
package jp.sourceforge.tmdmaker.generate.attributelist;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jp.sourceforge.tmdmaker.generate.Generator;
import jp.sourceforge.tmdmaker.generate.GeneratorUtils;
import jp.sourceforge.tmdmaker.generate.HtmlGeneratorRuntimeException;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.Detail;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.Identifier;

import org.apache.velocity.VelocityContext;

/**
 * アトリビュートリストをHTMLで出力するクラス
 * 
 * @author nakaG
 * 
 */
public class AttributeListHtmlGenerator implements Generator {

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.generate.Generator#getGeneratorName()
	 */
	@Override
	public String getGeneratorName() {
		return "アトリビュートリストをHTML形式で出力";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.generate.Generator#getGroupName()
	 */
	@Override
	public String getGroupName() {
		return "HTML";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.generate.Generator#execute(java.lang.String,
	 *      java.util.List)
	 */
	@Override
	public void execute(String rootDir, List<AbstractEntityModel> models) {
		System.out.println("generate");

		VelocityContext context = GeneratorUtils.getVecityContext();

		try {
			GeneratorUtils.outputCSS(rootDir);
			GeneratorUtils.copyStream(AttributeListHtmlGenerator.class
					.getResourceAsStream("index.html"), new FileOutputStream(
					new File(rootDir, "index.html")));
			Map<String, EntityAttributePair> attributes = findAllAttributes(models);

			context.put("entities", models);
			GeneratorUtils.applyTemplate("summary.html", this.getClass(),
					new File(rootDir, "summary.html"), context);

			context.put("attributes", attributes.entrySet());

			GeneratorUtils.applyTemplate("attribute_list.html",
					this.getClass(), new File(rootDir, "attribute_list.html"),
					context);
			File attributesDir = new File(rootDir, "attributes");
			attributesDir.mkdir();
			for (Map.Entry<String, EntityAttributePair> entry : attributes
					.entrySet()) {
				Attribute attribute = entry.getValue().getAttribute();
				AbstractEntityModel entity = entry.getValue().getModel();
				context.put("attribute", attribute);
				context.put("entity", entity);
				if (entity instanceof Entity) {
					context.put("entityType", ((Entity) entity).getEntityType()
							.getTypeName());
				} else {
					context.remove("entityType");
				}
				GeneratorUtils.applyTemplate("attribute.html", this.getClass(),
						new File(attributesDir, entry.getValue()
								.createAttributeFileKey()
								+ ".html"), context);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new HtmlGeneratorRuntimeException(e);
		}
	}

	private Map<String, EntityAttributePair> findAllAttributes(
			List<AbstractEntityModel> models) {

		Map<String, EntityAttributePair> attributes = new TreeMap<String, EntityAttributePair>(
				new Comparator<String>() {

					/**
					 * {@inheritDoc}
					 * 
					 * @see java.util.Comparator#compare(java.lang.Object,
					 *      java.lang.Object)
					 */
					@Override
					public int compare(String o1, String o2) {
						return o1.compareTo(o2);

					}

				});

		for (AbstractEntityModel m : models) {
			if (m instanceof Entity) {
				Entity e = (Entity) m;
				Identifier i = e.getIdentifier();
				EntityAttributePair pair = new EntityAttributePair(e, i);
				attributes.put(pair.createAttributeFileKey(), pair);
			}
			if (m instanceof Detail) {
				Detail d = (Detail) m;
				Identifier i = d.getDetailIdentifier();
				EntityAttributePair pair = new EntityAttributePair(d, i);
				attributes.put(pair.createAttributeFileKey(), pair);
			}
			for (Attribute a : m.getAttributes()) {
				EntityAttributePair pair = new EntityAttributePair(m, a);
				attributes.put(pair.createAttributeFileKey(), pair);
			}
		}
		return attributes;
	}

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
	public void execute(String rootDir, Diagram model) {
		// assert model != null;
		// System.out.println("generate");
		//
		// VelocityContext context = initialize();
		//
		// try {
		// outputCSS(rootDir);
		//
		// GeneratorUtils.copyStream(AttributeListHtmlGenerator.class
		// .getResourceAsStream("index.html"), new FileOutputStream(
		// new File(rootDir, "index.html")));
		// // context.put("entities", findAvailableModel(model));
		//
		// Map<String, EntityAttributePair> attributes =
		// findAllAttributes(model);
		//
		// context.put("entities", model.findEntityModel());
		// GeneratorUtils.applyTemplate("summary.html", this.getClass(),
		// new File(rootDir, "summary.html"), context);
		//
		// context.put("attributes", attributes.entrySet());
		//
		// GeneratorUtils.applyTemplate("attribute_list.html",
		// this.getClass(), new File(rootDir, "attribute_list.html"),
		// context);
		// File attributesDir = new File(rootDir, "attributes");
		// attributesDir.mkdir();
		// for (Map.Entry<String, EntityAttributePair> entry : attributes
		// .entrySet()) {
		// Attribute attribute = entry.getValue().getAttribute();
		// AbstractEntityModel entity = entry.getValue().getModel();
		// context.put("attribute", attribute);
		// context.put("entity", entity);
		// if (entity instanceof Entity) {
		// context.put("entityType", ((Entity) entity).getEntityType()
		// .getTypeName());
		// } else {
		// context.remove("entityType");
		// }
		// GeneratorUtils.applyTemplate("attribute.html", this.getClass(),
		// new File(attributesDir, entry.getValue()
		// .createAttributeFileKey()
		// + ".html"), context);
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// throw new HtmlGeneratorRuntimeException(e);
		// }
	}

	// private Map<String, EntityAttributePair> findAllAttributes(Diagram
	// diagram) {
	//
	// Map<String, EntityAttributePair> attributes = new TreeMap<String,
	// EntityAttributePair>(
	// new Comparator<String>() {
	//
	// /**
	// * {@inheritDoc}
	// *
	// * @see java.util.Comparator#compare(java.lang.Object,
	// * java.lang.Object)
	// */
	// @Override
	// public int compare(String o1, String o2) {
	// return o1.compareTo(o2);
	//
	// }
	//
	// });
	//
	// for (AbstractEntityModel m : diagram.findEntityModel()) {
	// if (m instanceof Entity) {
	// Entity e = (Entity) m;
	// Identifier i = e.getIdentifier();
	// EntityAttributePair pair = new EntityAttributePair(e, i);
	// attributes.put(pair.createAttributeFileKey(), pair);
	// }
	// if (m instanceof Detail) {
	// Detail d = (Detail) m;
	// Identifier i = d.getDetailIdentifier();
	// EntityAttributePair pair = new EntityAttributePair(d, i);
	// attributes.put(pair.createAttributeFileKey(), pair);
	// }
	// for (Attribute a : m.getAttributes()) {
	// EntityAttributePair pair = new EntityAttributePair(m, a);
	// attributes.put(pair.createAttributeFileKey(), pair);
	// }
	// }
	// return attributes;
	// }

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.generate.Generator#isImplementModelOnly()
	 */
	@Override
	public boolean isImplementModelOnly() {
		return false;
	}
}
