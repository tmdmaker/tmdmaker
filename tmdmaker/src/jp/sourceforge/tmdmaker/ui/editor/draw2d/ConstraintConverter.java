/*
 * Copyright 2014-2017 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

import org.eclipse.draw2d.geometry.Rectangle;

import jp.sourceforge.tmdmaker.model.ModelElement;
import jp.sourceforge.tmdmaker.model.constraint.Constraint;

/**
 * TMD-MakerのモデルとGEFのモデルの制約の変換用クラス
 * 
 * @author nakaG
 *
 */
public class ConstraintConverter {

	/**
	 * TMDモデルからGEFの制約を取得する.
	 * 
	 * @param model
	 * @return
	 */
	public static Rectangle getRectangle(ModelElement model) {
		if (model == null) {
			return null;
		}
		Constraint constraint = model.getConstraint();
		if (constraint == null) {
			return null;
		}
		return new Rectangle(constraint.x, constraint.y, constraint.width, constraint.height);
	}

	/**
	 * TMDモデルから幅と高さをリセットしたGEFの制約を取得する.
	 * 
	 * @param model
	 * @return
	 */
	public static Rectangle getResetRectangle(ModelElement model) {
		Constraint constraint = model.getConstraint();
		return new Rectangle(constraint.x, constraint.y, -1, -1);
	}

	/**
	 * TMDモデルの位置を移動させたGEFの制約を取得する.
	 * 
	 * @param model
	 * @return
	 */
	public static Rectangle getTranslatedRectangle(ModelElement model, int x, int y) {
		Constraint constraint = model.getConstraint().getTranslated(x, y);
		return new Rectangle(constraint.x, constraint.y, constraint.width, constraint.height);
	}

	/**
	 * GEFの制約を元にTMDモデルの位置を設定する.
	 * 
	 * @param model
	 * @param rectangle
	 */
	public static void setConstraint(ModelElement model, Rectangle rectangle) {
		model.setConstraint(
				new Constraint(rectangle.x, rectangle.y, rectangle.width, rectangle.height));
	}

	/**
	 * TMDモデルを起点モデルから所定の位置に設定する.
	 * 
	 * @param model
	 * @param base
	 * @param x
	 * @param y
	 */
	public static void setTranslatedConstraint(ModelElement model, ModelElement base, int x,
			int y) {
		model.setConstraint(base.getConstraint().getTranslated(x, y));
	}

	/**
	 * モデルの位置をコピーする。位置は少しずらす。
	 * 
	 * @param model
	 * @param base
	 */
	public static void copyConstraint(ModelElement model, ModelElement base) {
		Constraint c = base.getConstraint().getCopy();
		c.x = c.x + 10;
		c.y = c.y + 10;
		model.setConstraint(c);
	}

	/**
	 * モデルの位置が初期値か？.
	 * 
	 * @param model
	 * @return 初期値の場合にtrueを返す.
	 */
	public static boolean isInitialPosition(ModelElement model) {
		Constraint c = model.getConstraint();
		return c.x == 0 && c.y == 0;
	}
}
