/*
 * Copyright 2009-2015 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package jp.sourceforge.tmdmaker.ui.editor.gef3.editparts;

import jp.sourceforge.tmdmaker.model.ModelElement;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

/**
 * TMDモデルのEditPartFactory
 * 
 * @author nakaG
 * 
 */
public class TMDEditPartFactory implements EditPartFactory {

	/**
	 * @param context
	 *            コンテキスト
	 * @param obj
	 *            TMのモデル
	 * @return EditPart modelに対応したEditPart
	 */
	@Override
	public final EditPart createEditPart(final EditPart context, final Object obj) {
		ModelElement model = (ModelElement)obj;
		TMDEditPartVisitor visitor = new TMDEditPartVisitor();
		model.accept(visitor);
		return visitor.getEditPart();
	}
}
