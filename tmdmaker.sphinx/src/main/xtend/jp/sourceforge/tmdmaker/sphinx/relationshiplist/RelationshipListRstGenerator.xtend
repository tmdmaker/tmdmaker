package jp.sourceforge.tmdmaker.sphinx.relationshiplist

import java.io.File
import java.util.List
import java.util.LinkedHashMap
import java.util.ArrayList
import jp.sourceforge.tmdmaker.model.AbstractEntityModel
import jp.sourceforge.tmdmaker.model.generate.GeneratorRuntimeException
import jp.sourceforge.tmdmaker.model.RecursiveTable
import jp.sourceforge.tmdmaker.model.RecursiveRelationship
import jp.sourceforge.tmdmaker.sphinx.utilities.SphinxUtils
import static extension jp.sourceforge.tmdmaker.sphinx.utilities.SphinxUtils.*

class RelationshipListRstGenerator {
	
	def execute(File outputdir, List<AbstractEntityModel> models) {
		
		var context   = SphinxUtils.getVecityContext()
        
        outputdir.mkdirs()
        
		val relationshipMappingMap = createData(models)

		context => [
			put("entities", relationshipMappingMap.keySet())
			put("mappings", relationshipMappingMap.entrySet())
			try {
				applyTemplate("relationship_list.rst",
					this.class,
					new File(outputdir, "relationship_list.rst"))
			} catch (Exception e) {
				throw new GeneratorRuntimeException(e)
			}
		]
	}
	
	def private createData(List<AbstractEntityModel> models) {
		var relationshipMappingMap = new LinkedHashMap<AbstractEntityModel, List<RelationshipMapping>>()
		for (source : models) {
			var relationshipMappingList = new ArrayList<RelationshipMapping>()
			for (target : models) {
				relationshipMappingList.add(new RelationshipMapping(source,target))
			}
			relationshipMappingMap.put(source, relationshipMappingList)
		}
		relationshipMappingMap
	}
}

class RelationshipMapping {
	
	/** 比較元 */
	private AbstractEntityModel source
	/** 比較先 */
	private AbstractEntityModel target
	/** リレーションシップ有無 */
	private boolean relationship = false
	
	/**
	 * コンストラクタ
	 * 
	 * @param source
	 * @param target
	 */
	new(AbstractEntityModel source, AbstractEntityModel target) {
		this.source = source
		this.target = target

		if (hasRecursiveRelationship()) {
			relationship = true
		} else if (hasRelationshipAsSource()) {
			relationship = true
		} else if (hasRelationshipAsTarget()) {
			relationship = true
		} else {
			relationship = false
		}

		if (isRecursiveTablePair()) {
			relationship = false
		}
	}
	/**
	 * 再帰表とそのリレーションシップ元との組合せか？
	 * 
	 * @return 比較元・先が再帰表とそのリレーションシップ元である場合にtrueを返す
	 */
	def boolean isRecursiveTablePair() {
		if (target instanceof RecursiveTable) {
			return (target as RecursiveTable).isSource(source)
		} else if (source instanceof RecursiveTable) {
			return (source as RecursiveTable).isSource(target)
		}
		return false;
	}

	/**
	 * 再帰表を作成しているか？
	 * 
	 * @return 再帰表を作成している場合にtrueを返す
	 */
	def hasRecursiveRelationship() {
		if (source == target) {
			for (connection : source.modelSourceConnections) {
				if (connection instanceof RecursiveRelationship) {
					return true
				}
			}
		}
		false
	}

	/**
	 * 接続元としてリレーションシップを作成しているか？
	 * 
	 * @return リレーションシップを作成している場合にtrueを返す
	 */
	def private boolean hasRelationshipAsSource() {
		var hasRelationship = false;
		for (connection : source.modelSourceConnections) {
			if (connection.target.equals(target)) {
				hasRelationship = true
			}
		}
		hasRelationship
	}

	/**
	 * 接続先としてリレーションシップを作成しているか？
	 * 
	 * @return リレーションシップを作成している場合にtrueを返す
	 */
	def private hasRelationshipAsTarget() {
		var hasRelationship = false
		for (connection : source.modelTargetConnections) {
			if (connection.source.equals(target)) {
				hasRelationship = true
			}
		}
		hasRelationship
	}

	/**
	 * @return the relationship
	 */
	def isRelationship() {
		relationship
	}

	/**
	 * @return the source
	 */
	def getSource() {
		source
	}

	/**
	 * @return the target
	 */
	def getTarget() {
		target
	}
}