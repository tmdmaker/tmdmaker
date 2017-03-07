/*
 * Copyright 2009-2017 TMD-Maker Project <http://tmdmaker.osdn.jp/>
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
package jp.sourceforge.tmdmaker.serializer.handler.patch;

import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.constraint.AnchorConstraint;

/**
 * モデルのバージョン0.4.0への変換.
 * 
 * @author nakag
 *
 */
public class AnchorConstraintMigrator {
	/**
	 * データ移行.
	 * 
	 * バージョン0.4.0より前の場合にデータを補正する。
	 */
	public void convertNullLocationPoint(AbstractConnectionModel connection) {
		if (connection.sourceXp == 0 && connection.sourceYp == 0
				|| connection.targetXp == 0 && connection.targetYp == 0) {
			connection.setSourceAnchorConstraint(new AnchorConstraint());
			connection.setTargetAnchorConstraint(new AnchorConstraint());
			return;
		}
		connection.setSourceAnchorConstraint(new AnchorConstraint(connection.sourceXp, connection.sourceYp));
		connection.setTargetAnchorConstraint(new AnchorConstraint(connection.targetXp, connection.targetYp));
	}
}
