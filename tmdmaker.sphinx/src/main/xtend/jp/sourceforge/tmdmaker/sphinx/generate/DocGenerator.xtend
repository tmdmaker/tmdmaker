package jp.sourceforge.tmdmaker.sphinx.generate

import jp.sourceforge.tmdmaker.model.generate.Generator
import java.util.List
import jp.sourceforge.tmdmaker.model.AbstractEntityModel
import jp.sourceforge.tmdmaker.sphinx.attributelist.AttributeListRstGenerator
import jp.sourceforge.tmdmaker.sphinx.keydefinitionlist.KeyDefinitionListRstGenerator
import jp.sourceforge.tmdmaker.sphinx.relationshiplist.RelationshipListRstGenerator
import java.io.File
import java.io.FileOutputStream
import jp.sourceforge.tmdmaker.sphinx.utilities.SphinxUtils

class DocGenerator implements Generator {
	
	override execute(String rootDir, List<AbstractEntityModel> models) {
		
		val outputdir    = new File(rootDir, "doc")
		outputdir.mkdirs()

		SphinxUtils.copyStream(this.class.getResourceAsStream("index.rst")
							, new FileOutputStream(new File(outputdir, "index.rst")))

		(new AttributeListRstGenerator).execute(outputdir,     models)
		(new KeyDefinitionListRstGenerator).execute(outputdir, models.filter[m| m.isNotImplement == false].toList)
		(new RelationshipListRstGenerator).execute(outputdir,  models)
		
	}
	
	override getGeneratorName() {
		"Sphinx形式でドキュメントを生成する"
	}
	
	override getGroupName() {
		"sphinx"
	}
	
	override isImplementModelOnly() {
		false
	}
	
}