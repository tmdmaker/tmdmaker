/*
 * Copyright 2009-2010 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
import jp.sourceforge.tmdmaker.extension.PluginExtensionPointFactory;
import jp.sourceforge.tmdmaker.generate.GeneratorUtils;
import jp.sourceforge.tmdmaker.generate.ImageGenerator;

import org.eclipse.core.resources.IFile;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.FreeformGraphicalRootEditPart;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbenchPart;

/**
 * ダイアグラムを画像として保存するAction
 * 
 * @author nakaG
 * 
 */
public class DiagramSVGImageGenerateAction extends Action {
	/** ビューワ */
	private GraphicalViewer viewer;
	private IWorkbenchPart part;
	ImageGenerator generator;
	/** ID */
	public static final String ID = "DiagramSVGImageGenerateAction";

	/**
	 * コンストラクタ
	 * 
	 * @param viewer
	 *            ビューワ
	 */
	public DiagramSVGImageGenerateAction(GraphicalViewer viewer,
			IWorkbenchPart part) {
		this.viewer = viewer;
		this.part = part;
		setText("SVG形式で保存");
		setId(ID);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		FreeformGraphicalRootEditPart rootEditPart = (FreeformGraphicalRootEditPart) getViewer()
				.getRootEditPart();
		PluginExtensionPointFactory<ImageGenerator> factory = new PluginExtensionPointFactory<ImageGenerator>(
				TMDPlugin.PLUGIN_ID + ".image.generators");
		ImageGenerator generator = factory.getInstance();

		FileDialog dialog = new FileDialog(viewer.getControl().getShell(),
				SWT.SAVE);
		IFile editfile = GeneratorUtils.getEditFile(part);
		dialog.setFileName(editfile.getLocation().removeFileExtension().toOSString());
		dialog.setFilterPath(editfile.getLocation().removeFirstSegments(1)
				.toOSString());
		String[] extensions = generator.getExtensions();
		dialog.setFilterExtensions(extensions);
		
		String file = dialog.open();
		if (file != null) {
			StringBuffer filePath = new StringBuffer(file);
			String extension = extensions[dialog.getFilterIndex()];
			if (!file.endsWith(extension)) {
				filePath.append(".");
				filePath.append(extension);
			}
			IFigure figure = rootEditPart
					.getLayer(LayerConstants.PRINTABLE_LAYERS);

			generator.execute(filePath.toString(), figure, extension);

			TMDPlugin.showMessageDialog(getText() + " 完了");

			try {
				GeneratorUtils.refreshGenerateResource(file);
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
