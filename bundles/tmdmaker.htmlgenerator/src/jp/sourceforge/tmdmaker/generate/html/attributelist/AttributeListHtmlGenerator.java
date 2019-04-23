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
package jp.sourceforge.tmdmaker.generate.html.attributelist;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.sourceforge.tmdmaker.generate.html.Messages;
import jp.sourceforge.tmdmaker.generate.html.internal.HtmlGeneratorUtils;
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
		return Messages.AttributeListHtmlGenerator_GeneratorName;
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
	 * @see jp.sourceforge.tmdmaker.model.generate.Generator#execute(java.lang.String,
	 *      java.util.List)
	 */
	@Override
	public void execute(String rootDir, List<AbstractEntityModel> models) {
		logger.info("generate"); //$NON-NLS-1$
		VelocityContext context = HtmlGeneratorUtils.getVecityContext();

		try {
			HtmlGeneratorUtils.outputCSS(rootDir);
			HtmlGeneratorUtils.copyStream(AttributeListHtmlGenerator.class
					.getResourceAsStream("index.html"), new FileOutputStream( //$NON-NLS-1$
					new File(rootDir, "attributes_index.html"))); //$NON-NLS-1$
			Map<String, EntityAttributePair> attributes = 
					new AttributeListModelBuilder().build(models);

			context.put("entities", models); //$NON-NLS-1$
			HtmlGeneratorUtils.applyTemplate("summary.html", this.getClass(), //$NON-NLS-1$
					new File(rootDir, "summary.html"), context); //$NON-NLS-1$

			context.put("attributes", attributes.entrySet()); //$NON-NLS-1$

			HtmlGeneratorUtils.applyTemplate("attribute_list.html", //$NON-NLS-1$
					this.getClass(), new File(rootDir, "attribute_list.html"), //$NON-NLS-1$
					context);
			File attributesDir = new File(rootDir, "attributes"); //$NON-NLS-1$
			attributesDir.mkdir();
			for (Map.Entry<String, EntityAttributePair> entry : attributes
					.entrySet()) {
				IAttribute attribute = entry.getValue().getAttribute();
				AbstractEntityModel entity = entry.getValue().getModel();
				context.put("attribute", attribute); //$NON-NLS-1$
				context.put("entity", entity); //$NON-NLS-1$
				if (entity instanceof Entity) {
					context.put("entityType", ((Entity) entity).getEntityType() //$NON-NLS-1$
							.getTypeName());
				} else {
					context.remove("entityType"); //$NON-NLS-1$
				}
				HtmlGeneratorUtils.applyTemplate("attribute.html", //$NON-NLS-1$
						this.getClass(),
						new File(attributesDir, entry.getValue()
								.createAttributeFileKey() + ".html"), context); //$NON-NLS-1$
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
