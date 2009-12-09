/*
 * Copyright 2009 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package jp.sourceforge.tmdmaker.model.command.strategy;

import jp.sourceforge.tmdmaker.model.ConnectableElement;
import jp.sourceforge.tmdmaker.model.command.ConnectingModelSwitchStrategy;

/**
 * ソースとターゲットを入れ替えない
 * 
 * @author nakaG
 * 
 */
public class DefaultConnectingModelSwitchStrategy implements
		ConnectingModelSwitchStrategy {

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.command.ConnectingModelSwitchStrategy#switchModel(jp.sourceforge.tmdmaker.model.ConnectableElement,
	 *      jp.sourceforge.tmdmaker.model.ConnectableElement)
	 */
	@Override
	public ModelPair switchModel(ConnectableElement tempSource,
			ConnectableElement tempTarget) {
		ModelPair pair = new ModelPair();
		pair.source = tempSource;
		pair.target = tempTarget;
		return pair;
	}
}
