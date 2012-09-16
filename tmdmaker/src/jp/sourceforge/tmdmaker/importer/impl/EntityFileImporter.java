/*
 * Copyright 2009-2012 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package jp.sourceforge.tmdmaker.importer.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.geometry.Rectangle;

import au.com.bytecode.opencsv.CSVReader;

import jp.sourceforge.tmdmaker.importer.FileImporter;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.EntityType;
import jp.sourceforge.tmdmaker.model.IAttribute;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.Laputa;
import jp.sourceforge.tmdmaker.model.rule.EntityRecognitionRule;
import jp.sourceforge.tmdmaker.model.rule.EntityTypeRule;

/**
 * エンティティをCSVファイルからインポートするクラス
 * 
 * @author nakaG
 * 
 */
public class EntityFileImporter implements FileImporter {
	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.importer.FileImporter#getImporterName()
	 */
	@Override
	public String getImporterName() {
		return "ファイルからエンティティをインポート";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.importer.FileImporter#importEntities(java.lang.String)
	 */
	@Override
	public List<AbstractEntityModel> importEntities(String filePath)
			throws FileNotFoundException, IOException {
		CSVReader reader = new CSVReader(new BufferedReader(new FileReader(
				filePath)));
		String[] nextLine;
		AbstractEntityModel l = null;
		Map<String, AbstractEntityModel> s = new HashMap<String, AbstractEntityModel>();
		while ((nextLine = reader.readNext()) != null) {
			String entityName = nextLine[0];
			String attributeName = nextLine[1];
			if (l == null) {
				System.out.println("l is null.");
				l = createLaputa(s, entityName);
			}
			if (!entityName.equals(l.getName())) {
				System.out.println("entityName not equals");
				l = s.get(entityName);
				if (l == null) {
					l = createLaputa(s, entityName);
				}
			} else {
				System.out.println("entityName equals");
				l = s.get(entityName);
			}
			l.addAttribute(new Attribute(attributeName));
		}
		reader.close();
		
		List<AbstractEntityModel> list = new ArrayList<AbstractEntityModel>();
		for (Map.Entry<String, AbstractEntityModel> entry : s.entrySet()) {
			list.add(convertEntityIfPossible(entry.getValue()));
		}
		return list;
	}

	private AbstractEntityModel convertEntityIfPossible(
			AbstractEntityModel model) {
		String entityName = model.getName();
		List<IAttribute> identifierCandidates = new ArrayList<IAttribute>();

		for (IAttribute a : model.getAttributes()) {
			String generateName = EntityRecognitionRule.getInstance()
					.generateEntityNameFromIdentifier(a.getName());
			if (entityName.equals(generateName)) {
				identifierCandidates.add(a);
			}
		}
		if (identifierCandidates.size() > 0) {
			Entity entity = new Entity();
			model.copyTo(entity);
			IAttribute attribute = identifierCandidates.get(0);
			entity.setIdentifier(new Identifier(attribute.getName()));
			entity.removeAttribute((Attribute) attribute);
			entity.setNotImplement(false);
			if (EntityTypeRule.hasEventAttribute(entity)) {
				entity.setEntityType(EntityType.EVENT);
			} else {
				entity.setEntityType(EntityType.RESOURCE);
			}
			return entity;
		}
		return model;
	}

	private Laputa createLaputa(Map<String, AbstractEntityModel> s,
			String entityName) {
		Laputa l = EntityRecognitionRule.getInstance().createLaputa(entityName);
		// EntityRecognitionRule.createEntity(entityName, identifier,
		// entityType)
		l.setConstraint(new Rectangle());
		s.put(l.getName(), l);
		return l;
	}
}
