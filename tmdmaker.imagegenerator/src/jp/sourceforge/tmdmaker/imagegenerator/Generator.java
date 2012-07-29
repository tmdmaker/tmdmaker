/**
 * 
 */
package jp.sourceforge.tmdmaker.imagegenerator;

import org.eclipse.draw2d.IFigure;

/**
 * @author nakaG
 * 
 */
public interface Generator {
	void execute(String file, IFigure rootFigure, int imageType);
}
