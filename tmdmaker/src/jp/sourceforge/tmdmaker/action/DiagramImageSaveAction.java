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
import jp.sourceforge.tmdmaker.generate.GeneratorUtils;

import org.eclipse.core.resources.IFile;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.FreeformGraphicalRootEditPart;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbenchPart;

/**
 * ダイアグラムを画像として保存するAction
 * 
 * @author nakaG
 * 
 */
public class DiagramImageSaveAction extends Action {
	/** ビューワ */
	private GraphicalViewer viewer;
	private IWorkbenchPart part;
	/** ID */
	public static final String ID = "DiagramImageSaveAction";
	
	/**
	 * コンストラクタ
	 * 
	 * @param viewer
	 *            ビューワ
	 */
	public DiagramImageSaveAction(GraphicalViewer viewer,IWorkbenchPart part) {
		this.viewer = viewer;
		this.part = part;
		setText("画像として保存");
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

		FileDialog dialog = new FileDialog(viewer.getControl().getShell(),
				SWT.SAVE);
		IFile editfile = GeneratorUtils.getEditFile(part);
		dialog.setFileName(editfile.getLocation().toOSString() + ".jpg");
		dialog.setFilterPath(editfile.getLocation().removeFirstSegments(1).toOSString());
		String file = dialog.open();
		if (file != null) {
			IFigure figure = rootEditPart
					.getLayer(LayerConstants.PRINTABLE_LAYERS);
			Rectangle rectangle = figure.getBounds();

			Image image = new Image(Display.getDefault(), rectangle.width + 50,
					rectangle.height + 50);
			GC gc = new GC(image);
			SWTGraphics graphics = new SWTGraphics(gc);
			// 一部画像が表示されないための対策。何故この処理なのかは良く分かっていない・・・
			// 参照URL
			// http://www.eclipse.org/forums/index.php?t=msg&th=168154&start=0&
			graphics.translate(rectangle.x * -1, rectangle.y * -1);

			figure.paint(graphics);

			ImageLoader loader = new ImageLoader();
			loader.data = new ImageData[] { image.getImageData() };

			if (file.endsWith(".bmp")) {
				loader.save(file, SWT.IMAGE_BMP);
			} else if (file.endsWith(".gif")) {
				loader.save(file, SWT.IMAGE_GIF);
			} else if (file.endsWith(".jpg") || file.endsWith(".jpeg")) {
				loader.save(file, SWT.IMAGE_JPEG);
			} else if (file.endsWith(".png")) {
				loader.save(file, SWT.IMAGE_PNG);
			} else if (file.endsWith(".tiff")) {
				loader.save(file, SWT.IMAGE_TIFF);
			} else {
				file = file + ".bmp";
				loader.save(file, SWT.IMAGE_BMP);
			}
			TMDPlugin.showMessageDialog(getText() + " 完了");

			try {
				GeneratorUtils.refreshGenerateResource(file);
			} catch (Exception e) {
				TMDPlugin.showErrorDialog(e);
			}
			
			image.dispose();
			gc.dispose();


		}
	}

	/**
	 * @return the viewer
	 */
	protected GraphicalViewer getViewer() {
		return viewer;
	}

}
