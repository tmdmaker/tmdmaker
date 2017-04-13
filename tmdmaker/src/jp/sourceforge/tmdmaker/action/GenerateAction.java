/*
 * Copyright 2009-2017 TMD-Maker Project <http://tmdmaker.osdn.jp/>
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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.dialogs.Dialog;

import jp.sourceforge.tmdmaker.Messages;
import jp.sourceforge.tmdmaker.TMDEditor;
import jp.sourceforge.tmdmaker.TMDPlugin;
import jp.sourceforge.tmdmaker.dialog.GeneratorDialog;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.generate.Generator;
import jp.sourceforge.tmdmaker.ui.editor.gef3.editparts.node.AbstractModelEditPart;

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
				// generator.execute(dialog.getSavePath(), diagram);
				TMDPlugin.showMessageDialog(generator.getGeneratorName() + Messages.Completion);
				TMDPlugin.refreshGenerateResources(savePath);
			} catch (Throwable t) {
				TMDPlugin.showErrorDialog(t);
			}
		}
	}

	private String getSavePath() {
		IFile file = TMDPlugin.getEditFile(getWorkbenchPart());
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
		List<AbstractEntityModel> target = diagram.findEntityModel();
		list.removeAll(selectedModels);
		if (generator.isImplementModelOnly()) {
			for (AbstractEntityModel m : target) {
				if (!m.isNotImplement()) {
					list.add(m);
				}
			}
		} else {
			list.addAll(target);
		}
		list.removeAll(selectedModels);
		return list;
	}
}
