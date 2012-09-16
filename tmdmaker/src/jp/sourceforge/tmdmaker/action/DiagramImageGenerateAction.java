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

import jp.sourceforge.tmdmaker.TMDPlugin;
import jp.sourceforge.tmdmaker.generate.GeneratorUtils;
import jp.sourceforge.tmdmaker.imagegenerator.Draw2dToImageConverter;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.FreeformGraphicalRootEditPart;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

/**
 * ダイアグラムから画像を出力して保存するAction
 * 
 * @author nakaG
 * 
 */
public class DiagramImageGenerateAction extends Action {
	/** ビューワ */
	private GraphicalViewer viewer;
	private IWorkbenchPart part;
	private Draw2dToImageConverter converter;
	/** ID */
	public static final String ID = "DiagramImageGenerateAction";

	/**
	 * コンストラクタ
	 * 
	 * @param viewer
	 *            ビューワ
	 */
	public DiagramImageGenerateAction(GraphicalViewer viewer,
			IWorkbenchPart part) {
		this.viewer = viewer;
		this.part = part;
		setText("ダイアグラムから画像を生成");
		setId(ID);
		converter = new Draw2dToImageConverter();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {

		FileDialog dialog = new FileDialog(viewer.getControl().getShell(),
				SWT.SAVE);
		IFile editfile = GeneratorUtils.getEditFile(part);
		dialog.setFileName(editfile.getLocation().removeFileExtension()
				.toOSString());
		dialog.setFilterPath(editfile.getLocation().removeFirstSegments(1)
				.toOSString());
		String[] extensions = converter.getExtensions();
		dialog.setFilterExtensions(extensions);

		String file = dialog.open();
		if (file != null) {
			final StringBuffer filePath = new StringBuffer(file);
			final String extension = extensions[dialog.getFilterIndex()];
			if (!file.endsWith(extension)) {
				filePath.append(".");
				filePath.append(extension);
			}
			FreeformGraphicalRootEditPart rootEditPart = (FreeformGraphicalRootEditPart) getViewer()
					.getRootEditPart();
			final IFigure figure = rootEditPart
					.getLayer(LayerConstants.PRINTABLE_LAYERS);
			try {
				new ProgressMonitorDialog(getViewer().getControl().getShell())
						.run(false, // don't fork
								false, // not cancelable
								new WorkspaceModifyOperation() { // run this
									// operation

									@Override
									public void execute(IProgressMonitor monitor) {
										monitor.beginTask("生成", 1);
										converter.execute(figure,
												filePath.toString(), extension);
										monitor.worked(1);
										monitor.done();
									}
								});
			} catch (Exception e) {
				TMDPlugin.showErrorDialog(e);
			}

			TMDPlugin.showMessageDialog(getText() + " 完了");

			try {
				GeneratorUtils.refreshGenerateResource(filePath.toString());
			} catch (Exception e) {
				TMDPlugin.showErrorDialog(e);
			}
		}
	}

	/**
	 * @return the viewer
	 */
	protected GraphicalViewer getViewer() {
		return viewer;
	}

}
