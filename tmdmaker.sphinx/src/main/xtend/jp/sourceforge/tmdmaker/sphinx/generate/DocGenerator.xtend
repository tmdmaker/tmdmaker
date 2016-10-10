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
import static extension jp.sourceforge.tmdmaker.sphinx.utilities.SphinxUtils.*
import java.util.Calendar
import org.apache.commons.lang.StringUtils

class DocGenerator implements Generator {
	
	override execute(String rootDir, List<AbstractEntityModel> models) {
		
		val outputdir    = new File(rootDir, "doc")
		outputdir.mkdirs()
		
		outputConfig(outputdir, models)
		outputIndex(outputdir, models)

		SphinxUtils.copyStream(this.class.getResourceAsStream("make.bat")
							, new FileOutputStream(new File(outputdir, "make.bat")))
		SphinxUtils.copyStream(this.class.getResourceAsStream("Makefile")
							, new FileOutputStream(new File(outputdir, "Makefile")))

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

	def private void outputIndex(File outputDir, List<AbstractEntityModel> models){
		val diagram = models.get(0).diagram
		var context = SphinxUtils.getVecityContext()
		context => [
		    put("diagram_name", diagram.name)
			put("delimiter", StringUtils.repeat("=", diagram.name.length * 2))
		    put("description",  diagram.description)
		    
		    applyTemplate("index.rst",
			              this.class,
			              new File(outputDir, "index.rst"))
        ]
    }
	
	def private void outputConfig(File outputDir, List<AbstractEntityModel> models){
		val diagram = models.get(0).diagram
		var context = SphinxUtils.getVecityContext()
		context => [
		    put("diagram_name", diagram.name)
		    put("author",       System.getProperty("user.name"))
		    
		    var cal = Calendar.instance
		    put("year",         cal.get(Calendar.YEAR))
		    
		    applyTemplate("conf.py",
			              this.class,
			              new File(outputDir, "conf.py"))
        ]
	}
}