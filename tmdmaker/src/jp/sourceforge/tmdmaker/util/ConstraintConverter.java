/*
 * Copyright 2014-2015 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package jp.sourceforge.tmdmaker.util;

import jp.sourceforge.tmdmaker.model.Constraint;

import org.eclipse.draw2d.geometry.Rectangle;

/**
 * TMD-MakerのモデルとGEFのモデルの制約の相互変用クラス
 * 
 * @author nakaG
 *
 */
public class ConstraintConverter {
	public static Rectangle toRectangle(Constraint constraint) {
		return new Rectangle(constraint.x, constraint.y, constraint.width, constraint.height);
	}

	public static Rectangle toRectangleWithoutHeightWidth(Constraint constraint) {
		return new Rectangle(constraint.x, constraint.y, -1, -1);
	}

	public static Constraint toConstraint(Rectangle rectangle) {
		return new Constraint(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
	}

	public static Constraint toConstraintWithoutHeightWidth(Rectangle rectangle) {
		return new Constraint(rectangle.x, rectangle.y, -1, -1);
	}

}
