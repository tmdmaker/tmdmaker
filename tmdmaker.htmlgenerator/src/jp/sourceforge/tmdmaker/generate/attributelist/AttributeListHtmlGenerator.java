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
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import jp.sourceforge.tmdmaker.generate.Activator;
import jp.sourceforge.tmdmaker.generate.EscapeTool;
import jp.sourceforge.tmdmaker.generate.Generator;
import jp.sourceforge.tmdmaker.generate.GeneratorUtils;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.CombinationTable;
import jp.sourceforge.tmdmaker.model.Detail;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.MappingList;
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
public class AttributeListHtmlGenerator implements Generator {

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
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		System.out.println("init");
		VelocityContext context = new VelocityContext();

		try {
			GeneratorUtils.copyStream(AttributeListHtmlGenerator.class
					.getResourceAsStream("index.html"), new FileOutputStream(
					new File(rootDir, "index.html")));
			GeneratorUtils.copyStream(Activator.class
					.getResourceAsStream("stylesheet.css"), new FileOutputStream(
					new File(rootDir, "stylesheet.css")));
			// context.put("entities", findAvailableModel(model));
			context.put("esc", new EscapeTool());
			Map<String, EntityAttributePair> attributes = findAllAttributes(model);
	
			context.put("entities", model.findEntityModel());
			GeneratorUtils.applyTemplate("summary.html", this.getClass(), new File(
					rootDir, "summary.html"), context);
	
			context.put("attributes", attributes.entrySet());
	
			GeneratorUtils.applyTemplate("attribute_list.html", this.getClass(),
					new File(rootDir, "attribute_list.html"), context);
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
		}
	}

	private Map<String, EntityAttributePair> findAllAttributes(Diagram diagram) {

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

		for (AbstractEntityModel m : diagram.findEntityModel()) {
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

}
