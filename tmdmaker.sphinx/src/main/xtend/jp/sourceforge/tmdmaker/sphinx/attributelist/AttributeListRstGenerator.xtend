/*
 * Copyright 2016 TMD-Maker Project <http://tmdmaker.osdn.jp/>
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

package jp.sourceforge.tmdmaker.sphinx.attributelist

import java.io.File
import java.util.List
import java.util.Map
import java.util.TreeMap
import jp.sourceforge.tmdmaker.model.AbstractEntityModel
import jp.sourceforge.tmdmaker.model.Entity
import jp.sourceforge.tmdmaker.sphinx.utilities.SphinxUtils
import static extension jp.sourceforge.tmdmaker.sphinx.utilities.SphinxUtils.*
import org.apache.commons.lang.StringUtils

/**
 * アトリビュートリストを生成する
 * 
 * @author tohosaku
 */
public class AttributeListRstGenerator {
	
	def void execute(File outputdir, List<AbstractEntityModel> models) {
		
        val clazz = this.class

		val attributes   = findAllAttributes(models)
		var context      = SphinxUtils.getVecityContext()
		
		// 出力ディレクトリを生成する
		val attributesDir = new File(outputdir, "attribute_list")
		attributesDir.mkdirs()
		
		// トップページを生成する
		context => [
		    put("attributes", attributes)
		    applyTemplate("attribute_list.rst",
			              clazz,
			              new File(outputdir, "attribute_list.rst"))
        ]
		
		// アトリビュートごとのページを生成する
		for (e : attributes.entrySet()) {
			for (entry: e.value.entrySet()) {
				val attribute = entry.value.attribute
				val entity    = entry.value.model
			
				context => [
					put("attribute", attribute)
					put("delimiter", StringUtils.repeat("=", attribute.name.length * 2))
					put("entity",    entity)
					if (entity instanceof Entity) {
						put("entityType", '''(«(entity as Entity).entityType.typeName»)''')
					} else {
						remove("entityType");
					}
					applyTemplate("attribute.rst",
        	        	          clazz,
            	                  new File(attributesDir,
						                   entry.value.createAttributeFileKey() + ".rst"))
				]
			}
		}
	}
	
	def private findAllAttributes(List<AbstractEntityModel> models) {
		
		var attributes = new TreeMap<String, Map<String, EntityAttributePair>>
		
		for (m : models) {
			if (m.attributes.length > 0) {
				var attr = new TreeMap<String, EntityAttributePair>(
					[o1, o2| o1.compareTo(o2)]
					)
				for (a : m.attributes){
					var pair = new EntityAttributePair(m, a)
					attr.put(pair.createAttributeFileKey(), pair)
				}
				attributes.put(m.name, attr)
			}
		}
		attributes
	}
}
