/*
 * Copyright 2009-2013 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.sourceforge.tmdmaker.generate.internal.HtmlGeneratorUtils;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.IAttribute;
import jp.sourceforge.tmdmaker.model.generate.Generator;
import jp.sourceforge.tmdmaker.model.generate.GeneratorRuntimeException;
import jp.sourceforge.tmdmaker.model.generate.attributelist.AttributeListModelBuilder;
import jp.sourceforge.tmdmaker.model.generate.attributelist.EntityAttributePair;

/**
 * アトリビュートリストをHTMLで出力するクラス
 * 
 * @author nakaG
 * 
 */
public class AttributeListHtmlGenerator implements Generator {
	/** logging */
	private static Logger logger = LoggerFactory
			.getLogger(AttributeListHtmlGenerator.class);

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.generate.Generator#getGeneratorName()
	 */
	@Override
	public String getGeneratorName() {
		return "アトリビュートリストをHTML形式で出力";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.generate.Generator#getGroupName()
	 */
	@Override
	public String getGroupName() {
		return "HTML";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.generate.Generator#execute(java.lang.String,
	 *      java.util.List)
	 */
	@Override
	public void execute(String rootDir, List<AbstractEntityModel> models) {
		logger.info("generate");
		VelocityContext context = HtmlGeneratorUtils.getVecityContext();

		try {
			HtmlGeneratorUtils.outputCSS(rootDir);
			HtmlGeneratorUtils.copyStream(AttributeListHtmlGenerator.class
					.getResourceAsStream("index.html"), new FileOutputStream(
					new File(rootDir, "attributes_index.html")));
			Map<String, EntityAttributePair> attributes = 
					new AttributeListModelBuilder().build(models);

			context.put("entities", models);
			HtmlGeneratorUtils.applyTemplate("summary.html", this.getClass(),
					new File(rootDir, "summary.html"), context);

			context.put("attributes", attributes.entrySet());

			HtmlGeneratorUtils.applyTemplate("attribute_list.html",
					this.getClass(), new File(rootDir, "attribute_list.html"),
					context);
			File attributesDir = new File(rootDir, "attributes");
			attributesDir.mkdir();
			for (Map.Entry<String, EntityAttributePair> entry : attributes
					.entrySet()) {
				IAttribute attribute = entry.getValue().getAttribute();
				AbstractEntityModel entity = entry.getValue().getModel();
				context.put("attribute", attribute);
				context.put("entity", entity);
				if (entity instanceof Entity) {
					context.put("entityType", ((Entity) entity).getEntityType()
							.getTypeName());
				} else {
					context.remove("entityType");
				}
				HtmlGeneratorUtils.applyTemplate("attribute.html",
						this.getClass(),
						new File(attributesDir, entry.getValue()
								.createAttributeFileKey() + ".html"), context);
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new GeneratorRuntimeException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.generate.Generator#isImplementModelOnly()
	 */
	@Override
	public boolean isImplementModelOnly() {
		return false;
	}
}
