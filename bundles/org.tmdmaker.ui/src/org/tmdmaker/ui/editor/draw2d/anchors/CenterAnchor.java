/*
 * Copyright 2009-2010 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package org.tmdmaker.ui.editor.draw2d.anchors;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;

/**
 * モデルの中心に接続するアンカー
 * 
 * @author nakaG
 * 
 */
public class CenterAnchor extends ChopboxAnchor {
		/**
		 * コンストラクタ
		 * 
		 * @param owner
		 *            親Figure
		 */
		public CenterAnchor(IFigure owner) {
			super(owner);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.draw2d.ChopboxAnchor#getLocation(org.eclipse.draw2d.geometry.Point)
		 */
		@Override
		public Point getLocation(Point reference) {
			Point loc = getBox().getCenter();
			getOwner().translateToAbsolute(loc);
			return loc;
		}
}
