/*
 * Copyright 2009-2019 TMD-Maker Project <https://tmdmaker.osdn.jp/>
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
package jp.sourceforge.tmdmaker.generate.html.keydefinitionlist;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.sourceforge.tmdmaker.generate.html.Messages;
import jp.sourceforge.tmdmaker.generate.html.internal.HtmlGeneratorUtils;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.IAttribute;
import jp.sourceforge.tmdmaker.model.KeyModel;
import jp.sourceforge.tmdmaker.model.generate.Generator;
import jp.sourceforge.tmdmaker.model.generate.GeneratorRuntimeException;
import jp.sourceforge.tmdmaker.model.rule.ImplementRule;

import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * キー定義書をHTMLで出力するクラス
 * 
 * @author nakaG
 * 
 */
public class KeyDefinitionListHtmlGenerator implements Generator {
	/** logging */
	private static Logger logger = LoggerFactory
			.getLogger(KeyDefinitionListHtmlGenerator.class);

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.generate.Generator#execute(java.lang.String,
	 *      java.util.List)
	 */
	@Override
	public void execute(String rootDir, List<AbstractEntityModel> models) {
		logger.info("generate"); //$NON-NLS-1$
		VelocityContext context = HtmlGeneratorUtils.getVecityContext();

		try {
			HtmlGeneratorUtils.outputCSS(rootDir);
			HtmlGeneratorUtils.copyStream(KeyDefinitionListHtmlGenerator.class
					.getResourceAsStream("index.html"), new FileOutputStream( //$NON-NLS-1$
					new File(rootDir, "keys_index.html"))); //$NON-NLS-1$

			context.put("entities", models); //$NON-NLS-1$
			HtmlGeneratorUtils.applyTemplate("summary.html", this.getClass(), //$NON-NLS-1$
					new File(rootDir, "summary.html"), context); //$NON-NLS-1$

			HtmlGeneratorUtils.applyTemplate("key_list.html", this.getClass(), //$NON-NLS-1$
					new File(rootDir, "key_list.html"), context); //$NON-NLS-1$

			File keysDir = new File(rootDir, "keys"); //$NON-NLS-1$
			keysDir.mkdir();

			for (AbstractEntityModel m : models) {
				context.put("keys", m.getKeyModels()); //$NON-NLS-1$
				Map<IAttribute, List<KeyDefinitionMapping>> attributeKeyMap = createData(m);

				context.put("attributes", attributeKeyMap.keySet()); //$NON-NLS-1$
				context.put("mappings", attributeKeyMap.entrySet()); //$NON-NLS-1$
				context.put("entity", m); //$NON-NLS-1$
				HtmlGeneratorUtils.applyTemplate("keys.html", this.getClass(), //$NON-NLS-1$
						new File(keysDir, m.getName() + ".html"), context); //$NON-NLS-1$

			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new GeneratorRuntimeException(e);
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
	 * @see jp.sourceforge.tmdmaker.model.generate.Generator#getGeneratorName()
	 */
	@Override
	public String getGeneratorName() {
		return Messages.KeyDefinitionListHtmlGenerator_GeneratorName;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.generate.Generator#getGroupName()
	 */
	@Override
	public String getGroupName() {
		return "HTML"; //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.generate.Generator#isImplementModelOnly()
	 */
	@Override
	public boolean isImplementModelOnly() {
		return true;
	}

}
