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
package jp.sourceforge.tmdmaker.action;

import java.util.List;

import jp.sourceforge.tmdmaker.TMDPlugin;
import jp.sourceforge.tmdmaker.importer.FileImporter;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.command.ModelAddCommand;

import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editparts.FreeformGraphicalRootEditPart;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.FileDialog;

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
		Viewport viewport = (Viewport) ((FreeformGraphicalRootEditPart) viewer
				.getRootEditPart()).getFigure();
		Point p = viewport.getViewLocation();
		FileDialog dialog = new FileDialog(viewer.getControl().getShell());

		String filePath = dialog.open();
		if (filePath != null) {
			try {
				List<AbstractEntityModel> l = importer.importEntities(filePath);

				viewer.getEditDomain().getCommandStack()
						.execute(getCreateCommands(l, p));
			} catch (Throwable t) {
				TMDPlugin.showErrorDialog(t);
			}
		}
	}


	private Command getCreateCommands(List<AbstractEntityModel> list, Point p) {
		CompoundCommand ccommand = new CompoundCommand();
		Diagram diagram = (Diagram) viewer.getContents().getModel();
		int i = 0;
		for (AbstractEntityModel model : list) {
			ModelAddCommand c = new ModelAddCommand(diagram, p.x + i, p.y + i);
			i += 5;
			c.setModel(model);
			ccommand.add(c);
		}
		return ccommand.unwrap();
	}
}
