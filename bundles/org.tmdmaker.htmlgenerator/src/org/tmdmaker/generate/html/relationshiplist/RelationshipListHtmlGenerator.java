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
package org.tmdmaker.generate.html.relationshiplist;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.generate.html.Messages;
import org.tmdmaker.generate.html.internal.HtmlGeneratorUtils;
import org.tmdmaker.model.generate.Generator;
import org.tmdmaker.model.generate.GeneratorRuntimeException;

/**
 * 関係の検証表をHTML形式で生成するクラス
 * 
 * @author nakaG
 * 
 */
public class RelationshipListHtmlGenerator implements Generator {
	/** logging */
	private static Logger logger = LoggerFactory
			.getLogger(RelationshipListHtmlGenerator.class);

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.model.generate.Generator#getGeneratorName()
	 */
	@Override
	public String getGeneratorName() {
		return Messages.RelationshipListHtmlGenerator_GeneratorName;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.model.generate.Generator#getGroupName()
	 */
	@Override
	public String getGroupName() {
		return "HTML"; //$NON-NLS-1$
	}

	/**
	 * {inheritDoc}
	 * 
	 * @see org.tmdmaker.model.generate.Generator#execute(java.lang.String,
	 *      java.util.List)
	 */
	@Override
	public void execute(String rootDir, List<AbstractEntityModel> models) {
		logger.info("generate"); //$NON-NLS-1$
		VelocityContext context = HtmlGeneratorUtils.getVecityContext();

		Map<AbstractEntityModel, List<RelationshipMapping>> relationshipMappingMap = createData(models);

		context.put("entities", relationshipMappingMap.keySet()); //$NON-NLS-1$
		context.put("mappings", relationshipMappingMap.entrySet()); //$NON-NLS-1$
		try {
			HtmlGeneratorUtils.outputCSS(rootDir);
			HtmlGeneratorUtils.applyTemplate("relationship_list.html", //$NON-NLS-1$
					this.getClass(),
					new File(rootDir, "relationship_list.html"), context); //$NON-NLS-1$
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new GeneratorRuntimeException(e);
		}
		try {
			HtmlGeneratorUtils.copyStream(
					HtmlGeneratorUtils.class.getResourceAsStream("stylesheet.css"), //$NON-NLS-1$
					new FileOutputStream(new File(rootDir, "stylesheet.css"))); //$NON-NLS-1$
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
			throw new GeneratorRuntimeException(e);
		}

	}

	/**
	 * 出力用データを作成する
	 * 
	 * @param models
	 *            出力対象モデル
	 * @return 出力用データ
	 */
	private Map<AbstractEntityModel, List<RelationshipMapping>> createData(
			List<AbstractEntityModel> models) {
		Map<AbstractEntityModel, List<RelationshipMapping>> relationshipMappingMap = new LinkedHashMap<AbstractEntityModel, List<RelationshipMapping>>();
		for (AbstractEntityModel source : models) {
			List<RelationshipMapping> relationshipMappingList = new ArrayList<RelationshipMapping>();
			for (AbstractEntityModel target : models) {
				relationshipMappingList.add(new RelationshipMapping(source,
						target));
			}
			relationshipMappingMap.put(source, relationshipMappingList);
		}
		return relationshipMappingMap;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.model.generate.Generator#isImplementModelOnly()
	 */
	@Override
	public boolean isImplementModelOnly() {
		return false;
	}

}
