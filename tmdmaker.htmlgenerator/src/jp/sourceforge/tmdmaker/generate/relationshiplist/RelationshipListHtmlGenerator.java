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
package jp.sourceforge.tmdmaker.generate.relationshiplist;

import java.io.File;
import java.io.FileNotFoundException;
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
import jp.sourceforge.tmdmaker.model.Diagram;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.log.NullLogChute;

/**
 * 関係の検証表をHTML形式で生成するクラス
 * 
 * @author hiro
 * 
 */
public class RelationshipListHtmlGenerator implements Generator {

	/**
	 * コンストラクタ
	 */
	public RelationshipListHtmlGenerator() {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.generate.Generator#getGeneratorName()
	 */
	@Override
	public String getGeneratorName() {
		return "関係の検証表をHTML形式で出力";
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
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.generate.Generator#execute(java.lang.String,
	 *      jp.sourceforge.tmdmaker.model.Diagram)
	 */
	@Override
	public void execute(String rootDir, Diagram diagram) {
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
		context.put("esc", new EscapeTool());

		Map<AbstractEntityModel, List<RelationshipMapping>> relationshipMappingMap = createData(diagram);

		context.put("entities", relationshipMappingMap.keySet());
		context.put("mappings", relationshipMappingMap.entrySet());
		try {
			GeneratorUtils.applyTemplate("relationship_list.html", this
					.getClass(), new File(rootDir, "relationship_list.html"),
					context);
		} catch (Exception e) {
			e.printStackTrace();
			throw new HtmlGeneratorRuntimeException(e);
		}
		try {
			GeneratorUtils.copyStream(Activator.class
					.getResourceAsStream("stylesheet.css"), new FileOutputStream(
					new File(rootDir, "stylesheet.css")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new HtmlGeneratorRuntimeException(e);
		}
	}

	/**
	 * 出力用データを作成する
	 * 
	 * @param diagram
	 *            ダイアグラム
	 * @return 出力用データ
	 */
	private Map<AbstractEntityModel, List<RelationshipMapping>> createData(
			Diagram diagram) {
		Map<AbstractEntityModel, List<RelationshipMapping>> relationshipMappingMap = new LinkedHashMap<AbstractEntityModel, List<RelationshipMapping>>();
		for (AbstractEntityModel source : diagram.findEntityModel()) {
			List<RelationshipMapping> relationshipMappingList = new ArrayList<RelationshipMapping>();
			for (AbstractEntityModel target : diagram.findEntityModel()) {
				relationshipMappingList.add(new RelationshipMapping(source,
						target));
			}
			relationshipMappingMap.put(source, relationshipMappingList);
		}
		return relationshipMappingMap;
	}
}
