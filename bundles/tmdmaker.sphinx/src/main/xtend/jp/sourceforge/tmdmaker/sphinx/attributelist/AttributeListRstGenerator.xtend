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
import static extension jp.sourceforge.tmdmaker.sphinx.utilities.SphinxUtils.*

/**
 * アトリビュートリストを生成する
 * 
 * @author tohosaku
 */
class AttributeListRstGenerator {
	
	def static void generateAttributeList(List<AbstractEntityModel> models, File outputdir) {
		// 出力ディレクトリを生成する
		val attributesDir = new File(outputdir, "attribute_list")
		attributesDir.mkdirs()
		
		models.attributeList
			  .writeTo(new File(outputdir, "attribute_list.rst"))
		
		models.forEach[model|
			model.attributes.forEach[attr|
				attr.generateRst(model)
				    .writeTo(new File(attributesDir, '''«attr.getFileName(model)».rst'''))
			]
		]
	}
	
	/**
	 * アトリビュートリストのトップページを生成する
	 */
	def static private attributeList(List<AbstractEntityModel> models) '''
		アトリビュートリスト
		=====================
		
		«FOR model : models»
		«FOR attr : model.attributes»
		«attr.name»
		-------------------------------------------------------
		
		.. toctree::
		   :maxdepth: 1
		
		    attribute_list/«attr.getFileName(model)»
		
		«ENDFOR»
		«ENDFOR»
	'''
	
	def static private getFileName(IAttribute attribute, AbstractEntityModel entity){
		'''«entity.implementName»_«attribute.implementName»'''
	}
	
	/**
	 * 各アトリビュートのページを生成する
	 */
	def static private generateRst(IAttribute attribute, AbstractEntityModel model) '''
		«attribute.name»
		«StringUtils.repeat("=", attribute.name.length * 2)»
		
		所属エンティティ
		----------------
		
		«model.name» «IF model instanceof Entity»(«model.entityType.typeName»)«ENDIF»
			
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
