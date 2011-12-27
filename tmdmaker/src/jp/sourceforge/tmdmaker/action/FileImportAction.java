/*
 * Copyright 2009-2011 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package jp.sourceforge.tmdmaker.action;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.sourceforge.tmdmaker.TMDPlugin;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.EntityType;
import jp.sourceforge.tmdmaker.model.IAttribute;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.Laputa;
import jp.sourceforge.tmdmaker.model.command.ModelAddCommand;
import jp.sourceforge.tmdmaker.model.rule.EntityRecognitionRule;
import jp.sourceforge.tmdmaker.model.rule.EntityTypeRule;

import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editparts.FreeformGraphicalRootEditPart;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.FileDialog;

import au.com.bytecode.opencsv.CSVReader;

/**
 * ファイルからエンティティをインポートするAction
 * 
 * @author nakaG
 * 
 */
public class FileImportAction extends Action {
	/** ビューワ */
	private GraphicalViewer viewer;
	/** ID */
	public static final String ID = "FileImportAction";

	public FileImportAction(GraphicalViewer viewer) {
		super();
		this.viewer = viewer;
		setText("ファイルからエンティティをインポート");
		setId(ID);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		Viewport viewport = (Viewport) ((FreeformGraphicalRootEditPart) viewer
				.getRootEditPart()).getFigure();
		Point p = viewport.getViewLocation();
		FileDialog dialog = new FileDialog(viewer.getControl().getShell());

		String filePath = dialog.open();
		if (filePath != null) {
			try {
				Map<String, AbstractEntityModel> s = createEntitiesFromCSVFile(filePath);

				viewer.getEditDomain().getCommandStack()
						.execute(getCreateCommands(s, p));
			} catch (Throwable t) {
				TMDPlugin.showErrorDialog(t);
			}
		}
	}

	private Map<String, AbstractEntityModel> createEntitiesFromCSVFile(
			String filePath) throws FileNotFoundException, IOException {
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
		Map<String, AbstractEntityModel> map = new HashMap<String, AbstractEntityModel>();
		for (Map.Entry<String, AbstractEntityModel> entry : s.entrySet()) {
			map.put(entry.getKey(), convertEntityIfPossible(entry.getValue()));
		}
		return map;
	}

	private AbstractEntityModel convertEntityIfPossible(AbstractEntityModel model) {
		String entityName = model.getName();
		List<IAttribute> identifierCandidates = new ArrayList<IAttribute>();

		for (IAttribute a : model.getAttributes()) {
			String generateName = EntityRecognitionRule
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
		Laputa l = EntityRecognitionRule.createLaputa(entityName);
		// EntityRecognitionRule.createEntity(entityName, identifier,
		// entityType)
		l.setConstraint(new Rectangle());
		s.put(l.getName(), l);
		return l;
	}

	private Command getCreateCommands(Map<String, AbstractEntityModel> list,
			Point p) {
		CompoundCommand ccommand = new CompoundCommand();
		Diagram diagram = (Diagram) viewer.getContents().getModel();
		int i = 0;
		for (Map.Entry<String, AbstractEntityModel> entry : list.entrySet()) {
			ModelAddCommand c = new ModelAddCommand(diagram, p.x + i, p.y + i);
			i += 5;
			c.setModel(entry.getValue());
			ccommand.add(c);
		}
		return ccommand.unwrap();
	}
}
