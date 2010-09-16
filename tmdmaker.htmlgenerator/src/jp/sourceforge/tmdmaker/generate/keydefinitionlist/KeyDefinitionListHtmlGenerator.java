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
package jp.sourceforge.tmdmaker.generate.keydefinitionlist;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.sourceforge.tmdmaker.generate.Activator;
import jp.sourceforge.tmdmaker.generate.EscapeTool;
import jp.sourceforge.tmdmaker.generate.Generator;
import jp.sourceforge.tmdmaker.generate.GeneratorUtils;
import jp.sourceforge.tmdmaker.generate.HtmlGeneratorRuntimeException;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.KeyModel;
import jp.sourceforge.tmdmaker.model.rule.ImplementRule;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.log.NullLogChute;

/**
 * キー定義書をHTMLで出力するクラス
 * 
 * @author nakaG
 * 
 */
public class KeyDefinitionListHtmlGenerator implements Generator {

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
			GeneratorUtils.copyStream(KeyDefinitionListHtmlGenerator.class
					.getResourceAsStream("index.html"), new FileOutputStream(
					new File(rootDir, "index.html")));

			context.put("entities", models);
			GeneratorUtils.applyTemplate("summary.html", this.getClass(),
					new File(rootDir, "summary.html"), context);

			GeneratorUtils.applyTemplate("key_list.html", this.getClass(),
					new File(rootDir, "key_list.html"), context);

			File keysDir = new File(rootDir, "keys");
			keysDir.mkdir();

			for (AbstractEntityModel m : models) {
				context.put("keys", m.getKeyModels());
				Map<Attribute, List<KeyDefinitionMapping>> attributeKeyMap = createData(m);

				context.put("attributes", attributeKeyMap.keySet());
				context.put("mappings", attributeKeyMap.entrySet());
				context.put("entity", m);
				GeneratorUtils.applyTemplate("keys.html", this.getClass(),
						new File(keysDir, m.getName() + "_keys.html"), context);

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new HtmlGeneratorRuntimeException(e);
		}

	}

//	// TODO DDLでカラムを生成する時に共通で利用できるように要修正
//	private List<Attribute> findAllAttributes(AbstractEntityModel model) {
//		List<Attribute> attributes = new ArrayList<Attribute>();
//		// 個体指定子を追加
//		if (model instanceof Entity) {
//			attributes.add(((Entity) model).getIdentifier());
//		}
//		if (model instanceof Detail) {
//			attributes.add(((Detail) model).getDetailIdentifier());
//		}
//		// re-usedを追加
//		Map<AbstractEntityModel, ReusedIdentifier> reused = model
//				.getReusedIdentifieres();
//		for (Entry<AbstractEntityModel, ReusedIdentifier> entry : reused
//				.entrySet()) {
//			for (IdentifierRef ref : entry.getValue().getIdentifires()) {
//				attributes.add(ref);
//			}
//		}
//		// モデルのアトリビュートを追加
//		attributes.addAll(model.getAttributes());
//
//		// 派生元に戻して実装するモデルのアトリビュートを追加
//		for (AbstractEntityModel m : model.getImplementDerivationModels()) {
//			attributes.addAll(m.getAttributes());
//		}
//
//		return attributes;
//	}

	public Map<Attribute, List<KeyDefinitionMapping>> createData(
			AbstractEntityModel model) {
		List<Attribute> attributes = ImplementRule.findAllImplementAttributes(model);
		Map<Attribute, List<KeyDefinitionMapping>> data = new LinkedHashMap<Attribute, List<KeyDefinitionMapping>>();
		for (Attribute a : attributes) {
			List<KeyDefinitionMapping> list = new ArrayList<KeyDefinitionMapping>();
			for (KeyModel k : model.getKeyModels()) {
				list.add(new KeyDefinitionMapping(a, k));
			}
			data.put(a, list);
		}
		return data;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.generate.Generator#getGeneratorName()
	 */
	@Override
	public String getGeneratorName() {
		return "キー定義書をHTML形式で出力";
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
		assert model != null;
		System.out.println("generate");
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

		try {
			GeneratorUtils.copyStream(KeyDefinitionListHtmlGenerator.class
					.getResourceAsStream("index.html"), new FileOutputStream(
					new File(rootDir, "index.html")));
			GeneratorUtils.copyStream(Activator.class
					.getResourceAsStream("stylesheet.css"),
					new FileOutputStream(new File(rootDir, "stylesheet.css")));
			// context.put("entities", findAvailableModel(model));
			context.put("esc", new EscapeTool());
			// Map<String, EntityAttributePair> attributes =
			// findAllAttributes(model);
			//	
			// context.put("entities", model.findEntityModel());
			// GeneratorUtils.applyTemplate("summary.html", this.getClass(), new
			// File(
			// rootDir, "summary.html"), context);
			//	
			// context.put("attributes", attributes.entrySet());

			GeneratorUtils.applyTemplate("attribute_list.html",
					this.getClass(), new File(rootDir, "attribute_list.html"),
					context);
			File attributesDir = new File(rootDir, "attributes");
			attributesDir.mkdir();
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
		} catch (Exception e) {
			e.printStackTrace();
			throw new HtmlGeneratorRuntimeException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.generate.Generator#isImplementModelOnly()
	 */
	@Override
	public boolean isImplementModelOnly() {
		return true;
	}

}
