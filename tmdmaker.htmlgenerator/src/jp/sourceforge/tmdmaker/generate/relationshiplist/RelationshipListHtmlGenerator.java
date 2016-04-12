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
package jp.sourceforge.tmdmaker.generate.relationshiplist;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.sourceforge.tmdmaker.generate.Activator;
import jp.sourceforge.tmdmaker.generate.internal.HtmlGeneratorUtils;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.generate.Generator;
import jp.sourceforge.tmdmaker.model.generate.GeneratorRuntimeException;

import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	 * コンストラクタ
	 */
	public RelationshipListHtmlGenerator() {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.generate.Generator#getGeneratorName()
	 */
	@Override
	public String getGeneratorName() {
		return "関係の検証表をHTML形式で出力";
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
	 * {inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.generate.Generator#execute(java.lang.String,
	 *      java.util.List)
	 */
	@Override
	public void execute(String rootDir, List<AbstractEntityModel> models) {
		logger.info("generate");
		VelocityContext context = HtmlGeneratorUtils.getVecityContext();

		Map<AbstractEntityModel, List<RelationshipMapping>> relationshipMappingMap = createData(models);

		context.put("entities", relationshipMappingMap.keySet());
		context.put("mappings", relationshipMappingMap.entrySet());
		try {
			HtmlGeneratorUtils.outputCSS(rootDir);
			HtmlGeneratorUtils.applyTemplate("relationship_list.html",
					this.getClass(),
					new File(rootDir, "relationship_list.html"), context);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new GeneratorRuntimeException(e);
		}
		try {
			HtmlGeneratorUtils.copyStream(
					Activator.class.getResourceAsStream("stylesheet.css"),
					new FileOutputStream(new File(rootDir, "stylesheet.css")));
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
	 * @see jp.sourceforge.tmdmaker.model.generate.Generator#isImplementModelOnly()
	 */
	@Override
	public boolean isImplementModelOnly() {
		return false;
	}

}
