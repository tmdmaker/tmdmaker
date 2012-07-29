/**
 * 
 */
package jp.sourceforge.tmdmaker.imagegenerator;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Display;

/**
 * @author nakaG
 * 
 */
public class RasterImageGenerator implements Generator {
	@Override
	public void execute(String file, IFigure rootFigure, int imageType) {
		Rectangle rectangle = rootFigure.getBounds();

		Image image = new Image(Display.getDefault(), rectangle.width + 50,
				rectangle.height + 50);
		GC gc = new GC(image);
		SWTGraphics graphics = new SWTGraphics(gc);
		graphics.translate(rectangle.getLocation().negate());

		rootFigure.paint(graphics);

		ImageLoader loader = new ImageLoader();
		loader.data = new ImageData[] { image.getImageData() };

		loader.save(file, imageType);
		
		gc.dispose();
		image.dispose();
	}
}
