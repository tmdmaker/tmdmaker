/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org>
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
package org.tmdmaker.ui.editor.gef3.treeeditparts;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.slf4j.LoggerFactory;
import org.tmdmaker.core.model.Attribute;
import org.tmdmaker.core.model.Identifier;
import org.tmdmaker.core.model.KeyModel;
import org.tmdmaker.core.model.ModelElement;
import org.tmdmaker.ui.Messages;

/*
 * TMDモデルのツリービュー用のEditPart
 * 
 * ツリービューでは、 entity、対照表、対応表を一覧することができる。
 * 各entityを展開すると、個体指定子、属性がぶらさがっている。 
 */
public class TMDEditorOutlineTreePartFactory implements EditPartFactory {
	
	/** logging */
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(TMDEditorOutlineTreePartFactory.class);
	
	/**
	 * @param context
	 *            コンテキスト
	 * @param model
	 *            TMのモデル
	 * @return EditPart modelに対応したEditPart
	 */
	@Override
	public final EditPart createEditPart(final EditPart context, final Object model) {
		
		EditPart part = null;
		if (model instanceof ModelElement) {
			ModelElement element = (ModelElement)model;
			TMDOutlineTreeEditPartVisitor visitor = new TMDOutlineTreeEditPartVisitor();
			element.accept(visitor);
			return visitor.getEditPart();
		}else if (model instanceof KeyModel) {
			logger.debug("KeyModel用の EditPart を返しました");
			logger.debug(((KeyModel)model).getName());
			part = new KeyModelTreeEditPart();
		}else if (model instanceof List<?>){
			logger.debug("フォルダー用EditPartを準備します。");
			List<?> list = (List<?>)model;
			if (list.size() > 0){
				if (list.get(0) instanceof Identifier){				
					logger.debug("個体指定子のフォルダー用EditPartを返しました。");
				    part = new FolderTreeEditPart<Identifier>(Messages.Identifier);
				} else if (list.get(0) instanceof Attribute){
					logger.debug("属性のフォルダー用EditPartを返しました。");
				    part = new FolderTreeEditPart<Attribute>(Messages.Attribute);
				} else if (list.get(0) instanceof KeyModel){
					logger.debug("キーのフォルダー用EditPartを返しました。");
				    part = new FolderTreeEditPart<KeyModel>(Messages.KeyDefinitions);					
				}
			}
		}
		if (part != null) {
			part.setModel(model);
		}
		return part;
	}
}
