/**
 * 
 */
package jp.sourceforge.tmdmaker.imagegenerator;

import java.util.LinkedHashMap;
import java.util.Map;

import jp.sourceforge.tmdmaker.generate.ImageGenerator;

import org.eclipse.draw2d.IFigure;
import org.eclipse.swt.SWT;

/**
 * @author nakaG
 *
 */
public class ImageGeneratorImpl implements ImageGenerator {
	private Map<String, GeneratorWrapper> generatorMap = new LinkedHashMap<String, GeneratorWrapper>();
	private static final int IMAGE_SVG = -1;
	public ImageGeneratorImpl() {
		Generator rasterImageGenerator = new RasterImageGenerator();
		Generator svgImageGenerator = new SVGImageGenerator();
		generatorMap.put("jpg", new GeneratorWrapper(SWT.IMAGE_JPEG, rasterImageGenerator));
		generatorMap.put("png", new GeneratorWrapper(SWT.IMAGE_PNG, rasterImageGenerator));
		generatorMap.put("gif", new GeneratorWrapper(SWT.IMAGE_GIF, rasterImageGenerator));
		generatorMap.put("tiff", new GeneratorWrapper(SWT.IMAGE_TIFF, rasterImageGenerator));
		generatorMap.put("bmp", new GeneratorWrapper(SWT.IMAGE_BMP, rasterImageGenerator));
		generatorMap.put("svg", new GeneratorWrapper(IMAGE_SVG, svgImageGenerator));
		
	}
	public String[] getExtensions() {
		return generatorMap.keySet().toArray(new String[generatorMap.size()]);
	}
	/* (non-Javadoc)
	 * @see jp.sourceforge.tmdmaker.generate.ImageGenerator#execute(java.lang.String, org.eclipse.draw2d.IFigure, java.lang.String)
	 */
	@Override
	public void execute(String file, IFigure figure, String extention) {
		generatorMap.get(extention).execute(file, figure);
	}

}
class GeneratorWrapper {
	int imageType;
	Generator generator;
	public GeneratorWrapper(int imageType, Generator generator) {
		this.imageType = imageType;
		this.generator = generator;
	}
	public void execute(String file, IFigure rootFigure) {
		generator.execute(file, rootFigure, imageType);
	}
}
