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
package jp.sourceforge.tmdmaker.imagegenerator.generator;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Display;

/**
 * ラスターイメージ生成クラス
 * 
 * @author nakaG
 * 
 */
public class RasterImageGenerator implements ImageGenerator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(IFigure rootFigure, String file, int imageType) {
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
