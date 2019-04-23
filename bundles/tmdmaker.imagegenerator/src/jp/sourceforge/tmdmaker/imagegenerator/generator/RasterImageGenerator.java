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
package jp.sourceforge.tmdmaker.imagegenerator.generator;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import jp.sourceforge.tmdmaker.imagegenerator.generator.converter.DefaultImageFormatConverer;
import jp.sourceforge.tmdmaker.imagegenerator.generator.converter.ImageFormatConverter;

/**
 * ラスターイメージ生成クラス
 * 
 * @author nakaG
 * 
 */
public class RasterImageGenerator implements ImageGenerator {
	private final ImageFormatConverter imageDataConverter;

	public RasterImageGenerator() {
		this.imageDataConverter = new DefaultImageFormatConverer();
	}

	public RasterImageGenerator(ImageFormatConverter imageDataConverter) {
		this.imageDataConverter = imageDataConverter;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(IFigure rootFigure, String file, int imageType) {
		Rectangle rectangle = rootFigure.getBounds();

		Image image = new Image(Display.getDefault(), rectangle.width + 50, rectangle.height + 50);

		GC gc = new GC(image);
		SWTGraphics graphics = new SWTGraphics(gc);
		graphics.translate(rectangle.getLocation().negate());

		rootFigure.paint(graphics);

		this.imageDataConverter.convert(file, imageType, image);
		gc.dispose();
		image.dispose();
	}
}