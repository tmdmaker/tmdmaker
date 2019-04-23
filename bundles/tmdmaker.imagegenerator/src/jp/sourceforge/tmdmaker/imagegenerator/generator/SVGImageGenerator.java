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

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.draw2d.ui.render.awt.internal.svg.export.GraphicsSVG;

import jp.sourceforge.tmdmaker.imagegenerator.generator.converter.ImageFormatConverterException;

/**
 * SVG画像生成クラス
 * 
 * @author nakaG
 * 
 */
public class SVGImageGenerator implements ImageGenerator {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(IFigure rootFigure, String rootDir, int imageType) {
		Rectangle rect = rootFigure.getBounds();
		GraphicsSVG graphics = GraphicsSVG.getInstance(rect.getTranslated(rect
				.getLocation().negate()));
		graphics.translate(rect.getLocation().negate());
		rootFigure.paint(graphics);
		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(rootDir);
			graphics.getSVGGraphics2D().stream(
					new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8")));
			outputStream.flush();
		} catch (Exception e) {
			throw new ImageFormatConverterException(e);
		} finally {
			try {
				if (outputStream != null)
					outputStream.close();
			} catch (IOException e) {
				// Do nothing.
			}
		}
	}

}
