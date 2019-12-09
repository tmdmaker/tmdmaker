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
package org.tmdmaker.importer.csv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.Attribute;
import org.tmdmaker.core.model.Entity;
import org.tmdmaker.core.model.EntityType;
import org.tmdmaker.core.model.IAttribute;
import org.tmdmaker.core.model.Identifier;
import org.tmdmaker.core.model.Laputa;
import org.tmdmaker.core.model.parts.ModelName;
import org.tmdmaker.csv.Messages;
import org.tmdmaker.model.importer.FileImporter;

import au.com.bytecode.opencsv.CSVReader;

/**
 * エンティティをCSVファイルからインポートするクラス
 * 
 * @author nakaG
 * 
 */
public class EntityFileImporter implements FileImporter {
	private static final String[] EXTENSION = new String[] { "csv" }; //$NON-NLS-1$

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.model.importer.FileImporter#getImporterName()
	 */
	@Override
	public String getImporterName() {
		return Messages.getString("EntityFileImporter.ImporterName"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.model.importer.FileImporter#importEntities(java.lang.String)
	 */
	@Override
	public List<AbstractEntityModel> importEntities(String filePath)
			throws FileNotFoundException, IOException {
		CSVReader reader = new CSVReader(new BufferedReader(new FileReader(filePath)));
		String[] nextLine;
		AbstractEntityModel l = null;
		Map<String, AbstractEntityModel> s = new LinkedHashMap<String, AbstractEntityModel>();
		while ((nextLine = reader.readNext()) != null) {
			String entityName = nextLine[0];
			String attributeName = ""; //$NON-NLS-1$
			if (nextLine.length >= 2) {
				attributeName = nextLine[1];
			}
			if (l == null) {
				l = createLaputa(s, entityName);
			}
			if (!entityName.equals(l.getName())) {
				l = s.get(entityName);
				if (l == null) {
					l = createLaputa(s, entityName);
				}
			} else {
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

	private AbstractEntityModel convertEntityIfPossible(AbstractEntityModel model) {
		String entityName = model.getName();
		List<IAttribute> identifierCandidates = new ArrayList<IAttribute>();

		for (IAttribute a : model.getAttributes()) {
			String generateName = new Identifier(a.getName()).createEntityName().getValue();
			if (entityName.equals(generateName)) {
				identifierCandidates.add(a);
			}
		}
		if (!identifierCandidates.isEmpty()) {
			IAttribute attribute = identifierCandidates.get(0);
			Entity entity = null;
			Identifier identifier = new Identifier(attribute.getName());
			ModelName name = new ModelName(model.getName());

			if (isEvent(model)) {
				entity = Entity.ofEvent(name, identifier);
			} else {
				entity = Entity.ofResource(name, identifier);
			}
			EntityType type = entity.getEntityType();
			model.copyTo(entity);
			entity.setEntityType(type);
			entity.removeAttribute((Attribute) attribute);
			entity.setNotImplement(false);

			return entity;
		}
		return model;
	}

	private boolean isEvent(AbstractEntityModel model) {
		String eventAttributeName = Entity.getDefaultEventAttributeName(model.getName());
		for (IAttribute a : model.getAttributes()) {
			if (eventAttributeName.equals(a.getName())) {
				return true;
			}
		}
		return false;
	}

	private Laputa createLaputa(Map<String, AbstractEntityModel> s, String entityName) {
		Laputa l = Laputa.of(new ModelName(entityName));
		s.put(l.getName(), l);
		return l;
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.model.importer.FileImporter#getAvailableExtensions()
	 */
	@Override
	public String[] getAvailableExtensions() {
		return EXTENSION;
	}
}
