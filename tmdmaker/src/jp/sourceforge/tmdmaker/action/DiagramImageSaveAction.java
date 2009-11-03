package jp.sourceforge.tmdmaker.action;

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
import org.eclipse.ui.PlatformUI;

/**
 * 
 * @author nakaG
 *
 */
public class DiagramImageSaveAction extends Action {
	private GraphicalViewer viewer;
	public static final String ID = "DiagramImageSaveAction";
	public DiagramImageSaveAction(GraphicalViewer viewer) {
		this.viewer = viewer;
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
		FreeformGraphicalRootEditPart rootEditPart = (FreeformGraphicalRootEditPart) getViewer().getRootEditPart();

		FileDialog dialog = new FileDialog(viewer.getControl().getShell(), SWT.SAVE);
		dialog.setFileName(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor().getTitle() + ".jpg");
		String file = dialog.open();
		if (file != null) {
			IFigure figure = rootEditPart.getLayer(LayerConstants.PRINTABLE_LAYERS);
			Rectangle rectangle = figure.getBounds();
			
			Image image = new Image(Display.getDefault(), rectangle.width + 50, rectangle.height + 50);
			GC gc = new GC(image);
			SWTGraphics graphics = new SWTGraphics(gc);
			figure.paint(graphics);
			
			ImageLoader loader = new ImageLoader();
			loader.data = new ImageData[]{image.getImageData()};
			
			if(file.endsWith(".bmp")){
				loader.save(file, SWT.IMAGE_BMP);
			} else if(file.endsWith(".gif")){
				loader.save(file, SWT.IMAGE_GIF);
			} else if(file.endsWith(".jpg") || file.endsWith(".jpeg")){
				loader.save(file, SWT.IMAGE_JPEG);
			} else if(file.endsWith(".png")){
				loader.save(file, SWT.IMAGE_PNG);
			} else if(file.endsWith(".tiff")){
				loader.save(file, SWT.IMAGE_TIFF);
			} else {
				file = file + ".bmp";
				loader.save(file, SWT.IMAGE_BMP);
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
