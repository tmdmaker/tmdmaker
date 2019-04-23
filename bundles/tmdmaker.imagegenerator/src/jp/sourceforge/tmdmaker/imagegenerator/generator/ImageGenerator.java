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

/**
 * 画像生成用インターフェース
 * 
 * @author nakaG
 * 
 */
public interface ImageGenerator {
	/**
	 * 画像生成実行
	 * 
	 * @param rootFigure
	 *            draw2dのfigure
	 * @param file
	 *            出力先
	 * @param imageType
	 *            　画像種類の定数
	 */
	void execute(IFigure rootFigure, String file, int imageType);
}
