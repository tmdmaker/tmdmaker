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

import jp.sourceforge.tmdmaker.generate.Generator;
import jp.sourceforge.tmdmaker.generate.HtmlGeneratorRuntimeException;
import jp.sourceforge.tmdmaker.generate.HtmlGeneratorUtils;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.IAttribute;
import jp.sourceforge.tmdmaker.model.KeyModel;
import jp.sourceforge.tmdmaker.model.rule.ImplementRule;

import org.apache.velocity.VelocityContext;

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
		VelocityContext context = HtmlGeneratorUtils.getVecityContext();

		try {
			HtmlGeneratorUtils.outputCSS(rootDir);
			HtmlGeneratorUtils.copyStream(KeyDefinitionListHtmlGenerator.class
					.getResourceAsStream("index.html"), new FileOutputStream(
					new File(rootDir, "keys_index.html")));

			context.put("entities", models);
			HtmlGeneratorUtils.applyTemplate("summary.html", this.getClass(),
					new File(rootDir, "summary.html"), context);

			HtmlGeneratorUtils.applyTemplate("key_list.html", this.getClass(),
					new File(rootDir, "key_list.html"), context);

			File keysDir = new File(rootDir, "keys");
			keysDir.mkdir();

			for (AbstractEntityModel m : models) {
				context.put("keys", m.getKeyModels());
				Map<IAttribute, List<KeyDefinitionMapping>> attributeKeyMap = createData(m);

				context.put("attributes", attributeKeyMap.keySet());
				context.put("mappings", attributeKeyMap.entrySet());
				context.put("entity", m);
				HtmlGeneratorUtils.applyTemplate("keys.html", this.getClass(),
						new File(keysDir, m.getName() + ".html"), context);

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new HtmlGeneratorRuntimeException(e);
		}

	}

	private Map<IAttribute, List<KeyDefinitionMapping>> createData(
			AbstractEntityModel model) {
		List<IAttribute> attributes = ImplementRule
				.findAllImplementAttributes(model);
		Map<IAttribute, List<KeyDefinitionMapping>> data = new LinkedHashMap<IAttribute, List<KeyDefinitionMapping>>();
		for (IAttribute a : attributes) {
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
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.generate.Generator#isImplementModelOnly()
	 */
	@Override
	public boolean isImplementModelOnly() {
		return true;
	}

}
