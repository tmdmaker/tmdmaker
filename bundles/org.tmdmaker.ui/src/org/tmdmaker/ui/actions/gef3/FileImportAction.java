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
package org.tmdmaker.ui.actions.gef3;

import java.util.List;

import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editparts.FreeformGraphicalRootEditPart;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.FileDialog;
import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.Diagram;
import org.tmdmaker.model.importer.FileImporter;
import org.tmdmaker.ui.Activator;
import org.tmdmaker.ui.editor.gef3.commands.EntityModelAddCommand;

/**
 * ファイルからエンティティをインポートするAction
 * 
 * @author nakaG
 * 
 */
public class FileImportAction extends Action {
	/** ビューワ */
	private GraphicalViewer viewer;
	/** importer */
	private FileImporter importer;

	public FileImportAction(GraphicalViewer viewer, FileImporter importer) {
		super();
		this.viewer = viewer;
		this.importer = importer;
		setText(this.importer.getImporterName());
		setId(this.importer.getClass().getName());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		Viewport viewport = (Viewport) ((FreeformGraphicalRootEditPart) viewer.getRootEditPart())
				.getFigure();
		Point p = viewport.getViewLocation();
		FileDialog dialog = new FileDialog(viewer.getControl().getShell());
		dialog.setFilterExtensions(importer.getAvailableExtensions());
		String filePath = dialog.open();
		if (filePath != null) {
			try {
				List<AbstractEntityModel> l = importer.importEntities(filePath);

				viewer.getEditDomain().getCommandStack().execute(getCreateCommands(l, p));
			} catch (Throwable t) {
				Activator.showErrorDialog(t);
			}
		}
	}

	private Command getCreateCommands(List<AbstractEntityModel> list, Point p) {
		CompoundCommand ccommand = new CompoundCommand();
		Diagram diagram = (Diagram) viewer.getContents().getModel();
		int i = 0;
		for (AbstractEntityModel model : list) {
			EntityModelAddCommand c = new EntityModelAddCommand(diagram, p.x + i, p.y + i);
			i += 5;
			c.setModel(model);
			ccommand.add(c);
		}
		return ccommand.unwrap();
	}
}
