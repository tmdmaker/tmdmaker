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
package jp.sourceforge.tmdmaker.generate;

import org.eclipse.draw2d.IFigure;

/**
 * @author nakaG
 *
 */
public interface ImageGenerator {
	/**
	 * 生成処理実行
	 * 
	 * @param rootDir
	 *            生成先ディレクトリ
	 * @param figure
	 *            対象モデル
	 */
	void execute(String file, IFigure figure, String extention);
	String[] getExtensions();
}
