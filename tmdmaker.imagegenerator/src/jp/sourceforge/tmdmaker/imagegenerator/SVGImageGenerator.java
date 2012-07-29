/**
 * 
 */
package jp.sourceforge.tmdmaker.imagegenerator;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.draw2d.ui.render.awt.internal.svg.export.GraphicsSVG;

/**
 * @author nakaG
 * 
 */
public class SVGImageGenerator implements Generator {

	@Override
	public void execute(String rootDir, IFigure rootFigure, int imageType) {
		Rectangle rect = rootFigure.getBounds();
		GraphicsSVG graphics = GraphicsSVG.getInstance(rect.getTranslated(rect
				.getLocation().negate()));
		graphics.translate(rect.getLocation().negate());
		rootFigure.paint(graphics);
		OutputStream outputStream;
		try {
			outputStream = new FileOutputStream(rootDir);
			graphics.getSVGGraphics2D().stream(
					new BufferedWriter(new OutputStreamWriter(outputStream)));
			outputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
