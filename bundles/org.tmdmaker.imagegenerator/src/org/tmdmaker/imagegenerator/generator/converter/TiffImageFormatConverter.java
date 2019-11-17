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
package org.tmdmaker.imagegenerator.generator.converter;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;

public class TiffImageFormatConverter implements ImageFormatConverter {

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.imagegenerator.generator.converter.ImageFormatConverter#convert(java.lang.String, int, org.eclipse.swt.graphics.Image)
	 */
	@Override
	public void convert(String file, int imageType, Image image) {
		BufferedImage bi = convertFromImageData(image.getImageData());
		try {
			OutputStream out = new FileOutputStream(file);
			ImageOutputStream io = ImageIO.createImageOutputStream(out);
			ImageIO.write(bi, "TIFF", io);
		} catch (IOException e) {
			throw new ImageFormatConverterException(e);
		}

	}
	private BufferedImage convertFromImageData(ImageData imageData) {
		int width = imageData.width;
		int height = imageData.height;
		ImageData maskData = null;
		int alpha[] = new int[1];

		if (imageData.alphaData == null)
			maskData = imageData.getTransparencyMask();

		// now we should have the image data for the bitmap, decompressed in
		// imageData[0].data.
		// Convert that to a Buffered Image.
		BufferedImage image = new BufferedImage(imageData.width, imageData.height, BufferedImage.TYPE_INT_ARGB);

		WritableRaster alphaRaster = image.getAlphaRaster();

		// loop over the imagedata and set each pixel in the BufferedImage to
		// the appropriate color.
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				RGB color = imageData.palette.getRGB(imageData.getPixel(x, y));
				image.setRGB(x, y, new java.awt.Color(color.red, color.green, color.blue).getRGB());

				// check for alpha channel
				if (alphaRaster != null) {
					if (imageData.alphaData != null) {
						alpha[0] = imageData.getAlpha(x, y);
						alphaRaster.setPixel(x, y, alpha);
					} else {
						// check for transparency mask
						if (maskData != null) {
							alpha[0] = maskData.getPixel(x, y) == 0 ? 0 : 255;
							alphaRaster.setPixel(x, y, alpha);
						}
					}
				}
			}
		}
		return image;
	}

}
