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
package jp.sourceforge.tmdmaker.ui.editor.draw2d;

import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.constraint.AnchorConstraint;

/**
 * リレーションシップモデルの接続先（アンカー）制約を管理するクラス.
 * 
 * @author nakag
 *
 */
public class AnchorConstraintManager {
	public static void setSourceAnchorConstraint(AbstractConnectionModel connection,
			AnchorConstraint sourceAnchorConstraint) {
		connection.setSourceAnchorConstraint(sourceAnchorConstraint);
	}

	public static void setTargetAnchorConstraint(AbstractConnectionModel connection,
			AnchorConstraint targetAnchorConstraint) {
		connection.setTargetAnchorConstraint(targetAnchorConstraint);
	}

	public static AnchorConstraint getSourceAnchorConstraint(AbstractConnectionModel connection) {
		return connection.getSourceAnchorConstraint();
	}

	public static AnchorConstraint getTargetAnchorConstraint(AbstractConnectionModel connection) {
		return connection.getTargetAnchorConstraint();
	}
}
