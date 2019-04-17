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
package jp.sourceforge.tmdmaker.ui.actions;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.FreeformGraphicalRootEditPart;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import jp.sourceforge.tmdmaker.Messages;
import jp.sourceforge.tmdmaker.TMDPlugin;
import jp.sourceforge.tmdmaker.imagegenerator.Draw2dToImageConverter;

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
	public static final String ID = "DiagramImageGenerateAction"; //$NON-NLS-1$

	/**
	 * コンストラクタ
	 * 
	 * @param viewer
	 *            ビューワ
	 */
	public DiagramImageGenerateAction(GraphicalViewer viewer, IWorkbenchPart part) {
		this.viewer = viewer;
		this.part = part;
		setText(Messages.GenerateImage);
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

		FileDialog dialog = new FileDialog(viewer.getControl().getShell(), SWT.SAVE);
		IFile editfile = TMDPlugin.getEditFile(part);
		dialog.setFileName(editfile.getLocation().removeFileExtension().lastSegment());
		dialog.setFilterPath(buildFilterPath(editfile));
		final String[] extensions = converter.getExtensions();
		dialog.setFilterExtensions(extensions);

		final String file = dialog.open();
		if (file != null) {
			String selectedExtension = null;
			int filterIndex = dialog.getFilterIndex();
			// Linux Only #39124
			if (filterIndex == -1) {
				TMDPlugin.showMessageDialog(Messages.ExtensionNotSelected);
				selectedExtension = file.substring(file.lastIndexOf('.')); //$NON-NLS-1$
				if (!isSupported(extensions, selectedExtension)) {
					TMDPlugin.showErrorDialog(Messages.ExtensionNotSupported);
					return;
				}
			} else {
				selectedExtension = extensions[filterIndex];
			}
			final String extension = selectedExtension;
			final String fileFullPath = buildImageFileFullPath(file, extension);
			final FreeformGraphicalRootEditPart rootEditPart = (FreeformGraphicalRootEditPart) getViewer()
					.getRootEditPart();
			WorkspaceModifyOperation imageSaveOperation = new WorkspaceModifyOperation() {
				@Override
				public void execute(IProgressMonitor monitor) {
					monitor.beginTask(Messages.Generating, 1);
					converter.execute(rootEditPart.getLayer(LayerConstants.PRINTABLE_LAYERS),
							fileFullPath, extension);
					monitor.worked(1);
					monitor.done();
				}
			};
			try {
				new ProgressMonitorDialog(getViewer().getControl().getShell()).run(false, // don't
																							// fork
						false, // not cancelable
						imageSaveOperation);
				TMDPlugin.showMessageDialog(getText() + Messages.Completion);
			} catch (Exception e) {
				TMDPlugin.showErrorDialog(e);
			} finally {
				try {
					TMDPlugin.refreshGenerateResource(fileFullPath);
				} catch (Exception e) {
					// do nothing.
				}
			}
		}
	}

	private boolean isSupported(String[] extensions, String selectedExtension) {
		for (String ext : extensions) {
			if (ext.equals(selectedExtension)) {
				return true;
			}
		}
		return false;
	}

	private String buildFilterPath(IFile editfile) {
		String path = editfile.getLocation().removeLastSegments(1).toOSString();
		String separator = File.separator;
		if (!path.endsWith(separator)) {
			path = path + separator;
		}
		return path;
	}

	private String buildImageFileFullPath(String file, String extension) {
		StringBuilder filePath = new StringBuilder(file);
		if (!file.endsWith(extension)) {
			filePath.append("."); //$NON-NLS-1$
			filePath.append(extension);
		}
		return filePath.toString();
	}

	/**
	 * @return the viewer
	 */
	protected GraphicalViewer getViewer() {
		return viewer;
	}

}
