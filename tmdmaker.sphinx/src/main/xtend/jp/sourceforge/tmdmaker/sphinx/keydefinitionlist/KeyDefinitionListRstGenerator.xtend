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
 
package jp.sourceforge.tmdmaker.sphinx.keydefinitionlist

import java.util.List
import jp.sourceforge.tmdmaker.model.AbstractEntityModel
import java.io.File
import jp.sourceforge.tmdmaker.sphinx.utilities.SphinxUtils
import jp.sourceforge.tmdmaker.model.IAttribute
import jp.sourceforge.tmdmaker.model.KeyModel
import java.util.ArrayList
import java.util.LinkedHashMap
import jp.sourceforge.tmdmaker.model.rule.ImplementRule
import static extension jp.sourceforge.tmdmaker.sphinx.utilities.SphinxUtils.*
import org.apache.commons.lang.StringUtils
import jp.sourceforge.tmdmaker.model.DataTypeDeclaration

/**
 * キーの定義表を生成する。
 * 
 * @author tohosaku
 */
class KeyDefinitionListRstGenerator {
	
	def execute(File outputdir, List<AbstractEntityModel> models) {
		
		var context   = SphinxUtils.getVecityContext()
        
		// 出力ディレクトリを生成する
		val keysDir = new File(outputdir, "keys")
		keysDir.mkdirs()

        val clazz = this.class

        context => [
	        put("entities", models)
	        applyTemplate("key_list.rst",
	        	    clazz,
					new File(outputdir, "key_list.rst"))
        ]

		for (AbstractEntityModel m : models) {
			
			context => [
				put("columns", ImplementRule.findAllImplementAttributes(m).map[a|
					#{
						"name"          -> a.name,
						"implementName" -> a.implementName,
						"type"          -> datatype(a.dataTypeDeclaration).toString,
						"null"          -> nullable(a.nullable)
				}])
				put("keys", m.keyModels)
				val attributeKeyMap = createData(m)

				put("entity", m)
				put("delimiter",  StringUtils.repeat("=", m.name.length * 2))
				put("attributes", attributeKeyMap.keySet())
				put("mappings",   attributeKeyMap.entrySet())
				applyTemplate("keys.rst",
					          clazz,
							  new File(keysDir, m.implementName + ".rst"))
			]
		}
	}
	
	def private datatype(DataTypeDeclaration t)
	{
		if (t.size == null){
			t.logicalType.getName()
		}
		else if (t.scale == null){
			'''«t.logicalType.getName()»(«t.size»)'''
		}
		else {
			'''«t.logicalType.getName()»(«t.size».«t.scale»)'''			
		}
	}
	
	def private nullable(boolean n){
		if (n) "許容" else  "禁止"
	}
	
	def private createData(AbstractEntityModel model) {
		var attributes = ImplementRule.findAllImplementAttributes(model)
		var data       = new LinkedHashMap<IAttribute, List<KeyDefinitionMapping>>()
		for (a : attributes) {
			var list = new ArrayList<KeyDefinitionMapping>();
			for (k : model.keyModels) {
				list.add(new KeyDefinitionMapping(a, k));
			}
			data.put(a, list);
		}
		data;
	}
}

class KeyDefinitionMapping {
	
	private IAttribute attribute
	private KeyModel   keyModel
	/** キーにおけるアトリビュートの順序 */
	private String keyOrder

	/**
	 * コンストラクタ
	 * 
	 * @param attribute 組み合わせ対象のアトリビュート
	 * @param keyModel 組み合わせ対象のキー
	 */
	new(IAttribute attribute, KeyModel keyModel) {
		this.attribute = attribute;
		this.keyModel  = keyModel;

		setup()
	}

	def setup() {
		if (this.keyModel.attributes.contains(this.attribute)) {
			keyOrder = String.valueOf(this.keyModel.attributes.indexOf(this.attribute) + 1)
		} else {
			keyOrder = "\\-"
		}
	}

	/**
	 * @return the keyOrder
	 */
	def getKeyOrder() {
		keyOrder
	}
}