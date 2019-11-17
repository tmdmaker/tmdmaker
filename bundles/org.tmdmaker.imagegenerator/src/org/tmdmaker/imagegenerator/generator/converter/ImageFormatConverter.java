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

import org.eclipse.swt.graphics.Image;

/**
 * 画像フォーマット変換.
 * 
 * @author nakag
 *
 */
public interface ImageFormatConverter {

	/**
	 * 変換処理.
	 * 
	 * @param file
	 * @param imageType
	 * @param image
	 * 
	 * @throws ImageFormatConverterException.
	 */
	void convert(String file, int imageType, Image image);

}