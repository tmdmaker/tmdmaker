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
package jp.sourceforge.tmdmaker.model;

import jp.sourceforge.tmdmaker.model.other.TurboFile;

/**
 * ソースのRe-usedをターゲットのターボファイルへ移送するリレーションシップ
 *
 * @author nakag
 * 
 */
@SuppressWarnings("serial")
public class TurboFileRelationship extends TransfarReuseKeysToTargetRelationship {

	/**
	 * デフォルトコンストラクタ
	 */
	public TurboFileRelationship() {
	}

	/**
	 * コンストラクタ
	 * 
	 * @param source
	 *            from
	 * @param target
	 *            to
	 */
	public TurboFileRelationship(AbstractEntityModel source, AbstractEntityModel target) {
		AbstractEntityModel from = null;
		AbstractEntityModel to = null;
		if (target instanceof TurboFile) {
			from = source;
			to = target;
		} else {
			from = target;
			to = source;
		}
		setSource(from);
		setTarget(to);
	}
}
