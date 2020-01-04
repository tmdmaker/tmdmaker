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
package org.tmdmaker.imagegenerator.generator.converter;

import java.util.Arrays;
import java.util.HashMap;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;

public class GifImageFormatConverter implements ImageFormatConverter {
	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.imagegenerator.generator.converter.ImageFormatConverter#convert(java.lang.String, int, org.eclipse.swt.graphics.Image)
	 */
	@Override
	public void convert(String file, int imageType, Image image) {
		ImageLoader loader = new ImageLoader();
		loader.data = new ImageData[] { convertImageData(image) };
		loader.save(file, imageType);
	}

	private ImageData convertImageData(Image image) {
		ImageData data = image.getImageData();
		if (!data.palette.isDirect && data.depth <= 8)
			return data;

		// compute a histogram of color frequencies
		HashMap<RGB, ColorCounter> freq = new HashMap<RGB, ColorCounter>();
		int width = data.width;
		int[] pixels = new int[width];
		int[] maskPixels = new int[width];
		for (int y = 0, height = data.height; y < height; ++y) {
			data.getPixels(0, y, width, pixels, 0);
			for (int x = 0; x < width; ++x) {
				RGB rgb = data.palette.getRGB(pixels[x]);
				ColorCounter counter = freq.get(rgb);
				if (counter == null) {
					counter = new ColorCounter();
					counter.rgb = rgb;
					freq.put(rgb, counter);
				}
				counter.count++;
			}
		}

		// sort colors by most frequently used
		ColorCounter[] counters = new ColorCounter[freq.size()];
		freq.values().toArray(counters);
		Arrays.sort(counters);

		// pick the most frequently used 256 (or fewer), and make a palette
		ImageData mask = null;
		if (data.transparentPixel != -1 || data.maskData != null) {
			mask = data.getTransparencyMask();
		}
		int n = Math.min(256, freq.size());
		RGB[] rgbs = new RGB[n + (mask != null ? 1 : 0)];
		for (int i = 0; i < n; ++i)
			rgbs[i] = counters[i].rgb;
		if (mask != null) {
			rgbs[rgbs.length - 1] = data.transparentPixel != -1 ? data.palette.getRGB(data.transparentPixel)
					: new RGB(255, 255, 255);
		}
		PaletteData palette = new PaletteData(rgbs);

		ImageData newData = new ImageData(width, data.height, 8, palette);
		if (mask != null)
			newData.transparentPixel = rgbs.length - 1;
		for (int y = 0, height = data.height; y < height; ++y) {
			data.getPixels(0, y, width, pixels, 0);
			if (mask != null)
				mask.getPixels(0, y, width, maskPixels, 0);
			for (int x = 0; x < width; ++x) {
				if (mask != null && maskPixels[x] == 0) {
					pixels[x] = rgbs.length - 1;
				} else {
					RGB rgb = data.palette.getRGB(pixels[x]);
					pixels[x] = closest(rgbs, n, rgb);
				}
			}
			newData.setPixels(0, y, width, pixels, 0);
		}
		return newData;

	}

	private int closest(RGB[] rgbs, int n, RGB rgb) {
		int minDist = 256 * 256 * 3;
		int minIndex = 0;
		for (int i = 0; i < n; ++i) {
			RGB rgb2 = rgbs[i];
			int da = rgb2.red - rgb.red;
			int dg = rgb2.green - rgb.green;
			int db = rgb2.blue - rgb.blue;
			int dist = da * da + dg * dg + db * db;
			if (dist < minDist) {
				minDist = dist;
				minIndex = i;
			}
		}
		return minIndex;
	}

	static class ColorCounter implements Comparable<ColorCounter> {
		RGB rgb;
		int count;

		@Override
		public int compareTo(ColorCounter o) {
			return o.count - count;
		}

	}
}
