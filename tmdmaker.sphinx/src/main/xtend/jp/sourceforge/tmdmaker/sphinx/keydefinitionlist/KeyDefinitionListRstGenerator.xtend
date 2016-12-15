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
import org.apache.commons.lang.StringUtils
import jp.sourceforge.tmdmaker.model.DataTypeDeclaration
import java.util.Map
import jp.sourceforge.tmdmaker.model.KeyModels

/**
 * キーの定義表を生成する。
 * 
 * @author tohosaku
 */
class KeyDefinitionListRstGenerator {
	
	def execute(File outputdir, List<AbstractEntityModel> models) {
		
		// 出力ディレクトリを生成する
		val keysDir = new File(outputdir, "keys")
		keysDir.mkdirs()

		SphinxUtils.writeFile(
			new File(outputdir, "key_list.rst"),
			key_list(models).toString
		)
		
		models.forEach[m|
			SphinxUtils.writeFile(
				new File(keysDir, m.implementName + ".rst"),
				keys(m,
					ImplementRule.findAllImplementAttributes(m).map[a|
						#{
						"name"          -> a.name,
						"implementName" -> a.implementName,
						"type"          -> datatype(a.dataTypeDeclaration).toString,
						"null"          -> nullable(a.nullable)}]
				).toString
			)
		]
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
	
	def key_list(List<AbstractEntityModel> entities) '''
		テーブル設計とキーの定義書
		=============================
		
		.. toctree::
		   :maxdepth: 1
		
		«FOR entity: entities»
		   keys/«entity.implementName»
		«ENDFOR»
	'''
	
	def keys(AbstractEntityModel m, List<Map<String,String>> columns)'''
		«m.name»
		«StringUtils.repeat("=", m.name.length * 2)»
		
		テーブル設計
		---------------
		
		.. list-table::
		   :header-rows: 1
		
		   * - 列名
		     - 実装名
		     - データ型
		     - Null
		
		   «FOR c: columns»
		   * - «c.get("name")»
		     - «c.get("implementName")»
		     - «c.get("type")»
		     - «c.get("null")»
		   «ENDFOR»
		
		キーの定義書
		---------------
		
		«IF m.keyModels.size > 0»
		.. list-table::
		   :header-rows: 1
		
		   * - データ
		     «FOR h: key_header(m.keyModels)»
		     - «h»
		     «ENDFOR»
		   «FOR mapping: createData(m).entrySet()»
		   * - «mapping.key.name»
		     «FOR rm: mapping.value»
		     - «rm.keyOrder»
			 «ENDFOR»
		   «ENDFOR»
		«ELSE»
		キーは定義されていません。
		«ENDIF»
	'''
	
	def private key_header(KeyModels keys){
		keys.indexed.map[item| if (item.value.masterKey == true) "N/M" else (item.key + 1).toString]
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