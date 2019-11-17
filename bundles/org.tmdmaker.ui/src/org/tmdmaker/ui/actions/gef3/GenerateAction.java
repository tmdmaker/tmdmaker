/*
 * Copyright 2009-2019 TMD-Maker Project <https://tmdmaker.osdn.jp/>
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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.dialogs.Dialog;
import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.Diagram;
import org.tmdmaker.model.generate.Generator;
import org.tmdmaker.ui.Activator;
import org.tmdmaker.ui.Messages;
import org.tmdmaker.ui.dialogs.GeneratorDialog;
import org.tmdmaker.ui.editor.TMDEditor;
import org.tmdmaker.ui.editor.gef3.editparts.node.AbstractModelEditPart;
import org.tmdmaker.ui.editor.workspace.Workspace;

/**
 * 設定されたGeneratorを実行するAction
 * 
 * @author nakaG
 * 
 */
public class GenerateAction extends SelectionAction {
	private Generator generator;
	private GraphicalViewer viewer;

	/**
	 * コンストラクタ
	 * 
	 * @param editor
	 *            TMDエディタ
	 * @param viewer
	 *            ビューワ
	 * @param generator
	 *            generator
	 */
	public GenerateAction(TMDEditor editor, GraphicalViewer viewer, Generator generator) {
		super(editor);
		this.generator = generator;
		this.viewer = viewer;
		setId(generator.getClass().getName());
		setText(generator.getGeneratorName());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		return true;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		Diagram diagram = (Diagram) viewer.getContents().getModel();
		List<AbstractEntityModel> selectedModels = getSelectedModelList();

		GeneratorDialog dialog = new GeneratorDialog(getWorkbenchPart().getSite().getShell(),
				getSavePath(), generator.getGeneratorName(), selectedModels,
				getNotSelectedModelList(diagram, selectedModels));
		if (dialog.open() == Dialog.OK) {
			try {
				String savePath = dialog.getSavePath();
				generator.execute(savePath, dialog.getSelectedModels());
				Activator.showMessageDialog(generator.getGeneratorName() + Messages.Completion);
				new Workspace().refreshGenerateResources(savePath);
			} catch (Throwable t) {
				Activator.showErrorDialog(t);
			}
		}
	}

	private String getSavePath() {
		IFile file = new Workspace().getEditFile (getWorkbenchPart());
		return file.getLocation().removeLastSegments(1).toOSString();
	}

	private List<AbstractEntityModel> getSelectedModelList() {
		List<AbstractEntityModel> list = new ArrayList<AbstractEntityModel>();
		for (Object selection : getSelectedObjects()) {
			if (selection instanceof AbstractModelEditPart) {
				Object obj = ((AbstractModelEditPart<?>) selection).getModel();
				if (!(obj instanceof AbstractEntityModel)) {
					continue;
				}
				AbstractEntityModel model = (AbstractEntityModel) obj;
				if (generator.isImplementModelOnly()) {
					if (!model.isNotImplement()) {
						list.add(model);
					}
				} else {
					list.add(model);
				}
			}
		}
		return list;
	}

	private List<AbstractEntityModel> getNotSelectedModelList(Diagram diagram,
			List<AbstractEntityModel> selectedModels) {
		List<AbstractEntityModel> list = new ArrayList<AbstractEntityModel>();
		list.removeAll(selectedModels);
		if (generator.isImplementModelOnly()) {
			list.addAll(diagram.query().implementModel().listEntityModel());
		} else {
			list.addAll(diagram.query().listEntityModel());
		}
		list.removeAll(selectedModels);
		return list;
	}
}
