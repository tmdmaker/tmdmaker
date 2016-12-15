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
import jp.sourceforge.tmdmaker.model.AbstractEntityModel
import jp.sourceforge.tmdmaker.model.Entity
import org.apache.commons.lang.StringUtils
import jp.sourceforge.tmdmaker.model.IAttribute
import java.util.Map
import jp.sourceforge.tmdmaker.sphinx.utilities.SphinxUtils

/**
 * アトリビュートリストを生成する
 * 
 * @author tohosaku
 */
public class AttributeListRstGenerator {
	
	def static void execute(File outputdir, List<AbstractEntityModel> models) {
		val attributes = findAllAttributes(models)
		
		// 出力ディレクトリを生成する
		val attributesDir = new File(outputdir, "attribute_list")
		attributesDir.mkdirs()
		
		SphinxUtils.writeFile(new File(outputdir, "attribute_list.rst"),
				  attribute_list(attributes).toString
		)
		
		attributes.mapValues[v|v.entrySet()]
				  .values
				  .flatten
				  .forEach[e| SphinxUtils.writeFile(new File(attributesDir, e.key + ".rst"),
				  						attribute(e.value.attribute,e.value.model).toString
				  )]
	}
	
	def static private findAllAttributes(List<AbstractEntityModel> models) {
		models.filter[it.attributes.length > 0]
			  .toMap[it.name]
			  .mapValues[m| m.attributes.map[new EntityAttributePair(m, it)]
						 			 	.toMap[it.createAttributeFileKey()]]
	}
	
	/**
	 * アトリビュートリストのトップページを生成する
	 */
	def static private attribute_list(Map<String,Map<String,EntityAttributePair>> attributes) '''
		アトリビュートリスト
		=====================
		
		«FOR attr : attributes.entrySet()»
		«attr.key»
		-------------------------------------------------------
		
		.. toctree::
		   :maxdepth: 1
		
		    «FOR entry : attr.value.entrySet()»
		    attribute_list/«entry.value.createAttributeFileKey()»
		    «ENDFOR»
		
		«ENDFOR»
	'''
	
	/**
	 * 各アトリビュートのページを生成する
	 */
	def static private attribute(IAttribute attribute, AbstractEntityModel entity) '''
		«attribute.name»
		«StringUtils.repeat("=", attribute.name.length * 2)»
		
		所属エンティティ
		----------------
		
		«entity.name» «IF entity instanceof Entity»(«(entity as Entity).entityType.typeName»)«ENDIF»
			
		摘要
		----
		
		«attribute.description»
		
		前提
		----
		
		«attribute.validationRule»
		
		機密性
		------
		
		«attribute.lock»
		
		計算式
		------
		
		«attribute.derivationRule»
	'''
}
