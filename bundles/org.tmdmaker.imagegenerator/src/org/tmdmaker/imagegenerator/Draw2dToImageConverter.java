/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org>
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
package org.tmdmaker.imagegenerator;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.draw2d.IFigure;
import org.eclipse.swt.SWT;
import org.tmdmaker.imagegenerator.generator.ImageGenerator;
import org.tmdmaker.imagegenerator.generator.RasterImageGenerator;
import org.tmdmaker.imagegenerator.generator.SVGImageGenerator;
import org.tmdmaker.imagegenerator.generator.converter.GifImageFormatConverter;
import org.tmdmaker.imagegenerator.generator.converter.TiffImageFormatConverter;

/**
 * Draw2dのfigureを画像に変換する
 * 
 * @author nakaG
 * 
 */
public class Draw2dToImageConverter {
	private Map<String, GeneratorWrapper> generatorMap = new LinkedHashMap<String, GeneratorWrapper>();
	private static final int IMAGE_SVG = -1;

	/**
	 * コンストラクタ
	 */
	public Draw2dToImageConverter() {
		ImageGenerator rasterImageGenerator = new RasterImageGenerator();
		ImageGenerator gifImageGenerator = new RasterImageGenerator(new GifImageFormatConverter());
		ImageGenerator tiffImageGenerator = new RasterImageGenerator(new TiffImageFormatConverter());
		ImageGenerator svgImageGenerator = new SVGImageGenerator();
		generatorMap.put(".jpg", new GeneratorWrapper(SWT.IMAGE_JPEG, rasterImageGenerator));
		generatorMap.put(".png", new GeneratorWrapper(SWT.IMAGE_PNG, rasterImageGenerator));
		generatorMap.put(".gif", new GeneratorWrapper(SWT.IMAGE_GIF, gifImageGenerator));
		generatorMap.put(".tiff", new GeneratorWrapper(SWT.IMAGE_TIFF, tiffImageGenerator));
		generatorMap.put(".bmp", new GeneratorWrapper(SWT.IMAGE_BMP, rasterImageGenerator));
		generatorMap.put(".svg", new GeneratorWrapper(IMAGE_SVG, svgImageGenerator));
	}

	/**
	 * サポートしている拡張子を返す
	 * 
	 * @return 拡張子の配列
	 */
	public String[] getExtensions() {
		return generatorMap.keySet().toArray(new String[generatorMap.size()]);
	}

	/**
	 * 変換を実行する
	 * 
	 * @param figure    draw2dのfigure
	 * @param file      画像出力先
	 * @param extention 出力する画像の種類（拡張子で選択）
	 */
	public void execute(IFigure figure, String file, String extention) {
		generatorMap.get(extention).execute(file, figure);
	}

}

/**
 * imageTypeとgeneratorの組み合わせ
 * 
 * @author nakaG
 * 
 */
class GeneratorWrapper {
	int imageType;
	ImageGenerator generator;

	public GeneratorWrapper(int imageType, ImageGenerator generator) {
		this.imageType = imageType;
		this.generator = generator;
	}

	public void execute(String file, IFigure rootFigure) {
		generator.execute(rootFigure, file, imageType);
	}
}
