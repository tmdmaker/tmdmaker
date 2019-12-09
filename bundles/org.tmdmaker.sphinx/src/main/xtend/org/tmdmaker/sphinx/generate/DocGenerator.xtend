/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org>
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
package org.tmdmaker.sphinx.generate

import java.io.File
import java.util.Calendar
import java.util.List
import org.tmdmaker.core.model.AbstractEntityModel
import org.tmdmaker.core.model.Diagram
import org.tmdmaker.model.generate.Generator
import org.apache.commons.lang.StringUtils
import org.tmdmaker.sphinx.Messages
import static extension org.tmdmaker.sphinx.attributelist.AttributeListRstGenerator.*
import static extension org.tmdmaker.sphinx.keydefinitionlist.KeyDefinitionListRstGenerator.*
import static extension org.tmdmaker.sphinx.relationshiplist.RelationshipListRstGenerator.*
import static extension org.tmdmaker.sphinx.utilities.SphinxUtils.*

/**
 * ドキュメント全体を生成する
 * 
 * @author tohosaku
 */
class DocGenerator implements Generator {
	
	Diagram diagram
	
	override execute(String rootDir, List<AbstractEntityModel> models) {
		
		val outputdir    = new File(rootDir, "doc")
		outputdir.mkdirs()
		
		this.diagram = models.head.diagram
		
		outputConfig(outputdir)
		outputIndex(outputdir)

		this.class.getResourceAsStream("make.bat").copyTo(new File(outputdir, "make.bat"))
		this.class.getResourceAsStream("Makefile").copyTo(new File(outputdir, "Makefile"))

		models => [
			generateAttributeList(outputdir)
			filter[m| m.isNotImplement == false].toList.generateKeyDefinitionList(outputdir)
			generateRelationshipList(outputdir)
		]
	}
	
	override getGeneratorName() {
		Messages.getString("DocGenerator_GeneratorName")
	}
	
	override getGroupName() {
		"sphinx"
	}
	
	override isImplementModelOnly() {
		false
	}

	def private void outputIndex(File outputDir){
		index(diagram.name, diagram.description).writeTo(new File(outputDir, "index.rst"))
	}
	
	def private void outputConfig(File outputDir){
		val year   = Calendar.instance.get(Calendar.YEAR)
		val author = System.getProperty("user.name")
		
		confPy(diagram.name,year,author).writeTo(new File(outputDir, "conf.py"))
	}
	
	def private index(String diagram_name, String description) '''
		«IF diagram_name !== null»「«diagram_name»」«ENDIF»設計文書
		«IF diagram_name !== null»«StringUtils.repeat("=", diagram_name.length * 2)»«ENDIF»============
		
		«description»
		
		.. toctree::
		   :maxdepth: 1
		
		   attribute_list
		   key_list
		   relationship_list
	'''
	
	def private confPy(String diagram_name, int year, String author) '''
		#!/usr/bin/env python3
		# -*- coding: utf-8 -*-
		extensions = []
		
		# Add any paths that contain templates here, relative to this directory.
		templates_path = ['_templates']
		
		source_suffix = '.rst'
		
		# The master toctree document.
		master_doc = 'index'
		
		# General information about the project.
		project = '«diagram_name»'
		copyright = '«year», «author»'
		author = '«author»'
		
		# The version info for the project you're documenting, acts as replacement for
		# |version| and |release|, also used in various other places throughout the
		# built documents.
		#
		# The short X.Y version.
		version = '1.0'
		# The full version, including alpha/beta/rc tags.
		release = '1.0'
		
		# The language for content autogenerated by Sphinx. Refer to documentation
		# for a list of supported languages.
		#
		# This is also used if you do content translation via gettext catalogs.
		# Usually you set "language" from the command line for these cases.
		language = 'ja'
		
		# List of patterns, relative to source directory, that match files and
		# directories to ignore when looking for source files.
		# This patterns also effect to html_static_path and html_extra_path
		exclude_patterns = ['_build', 'Thumbs.db', '.DS_Store']
		
		# The name of the Pygments (syntax highlighting) style to use.
		pygments_style = 'sphinx'
		
		# If true, `todo` and `todoList` produce output, else they produce nothing.
		todo_include_todos = False
		
		
		# -- Options for HTML output ----------------------------------------------
		
		# The theme to use for HTML and HTML Help pages.  See the documentation for
		# a list of builtin themes.
		#
		html_theme = 'bizstyle'
		
		# Add any paths that contain custom static files (such as style sheets) here,
		# relative to this directory. They are copied after the builtin static files,
		# so a file named "default.css" will overwrite the builtin "default.css".
		html_static_path = ['_static']
		
		# Output file base name for HTML help builder.
		htmlhelp_basename = 'sphinxdoc'
		
		# -- Options for LaTeX output ---------------------------------------------
		
		latex_elements = {
		}
		
		# Grouping the document tree into LaTeX files. List of tuples
		# (source start file, target name, title,
		#  author, documentclass [howto, manual, or own class]).
		latex_documents = [
		    (master_doc, 'sphinx.tex', '«diagram_name» Documentation',
		     '«author»', 'manual'),
		]
		
		# -- Options for manual page output ---------------------------------------
		
		# One entry per manual page. List of tuples
		# (source start file, name, description, authors, manual section).
		man_pages = [
		    (master_doc, 'sphinx', '«diagram_name» Documentation',
		     [author], 1)
		]
		
		# -- Options for Texinfo output -------------------------------------------
		
		# Grouping the document tree into Texinfo files. List of tuples
		# (source start file, target name, title, author,
		#  dir menu entry, description, category)
		texinfo_documents = [
		    (master_doc, 'sphinx', '«diagram_name» Documentation',
		     author, 'sphinx', 'One line description of project.',
		     'Miscellaneous'),
		]
	'''
}