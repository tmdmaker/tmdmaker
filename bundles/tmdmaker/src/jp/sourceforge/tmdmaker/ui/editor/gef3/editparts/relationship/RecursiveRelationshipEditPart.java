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
package jp.sourceforge.tmdmaker.ui.editor.gef3.editparts.relationship;

import org.eclipse.draw2d.IFigure;

import jp.sourceforge.tmdmaker.model.RecursiveRelationship;
import jp.sourceforge.tmdmaker.ui.editor.draw2d.figure.relationship.RecursiveRelationshipFigure;

/**
 * 再帰を表すコネクションのコントローラ
 * 
 * @author nakaG
 * 
 */
public class RecursiveRelationshipEditPart extends RelationshipEditPart {

	/**
	 * コンストラクタ
	 */
	public RecursiveRelationshipEditPart(RecursiveRelationship relationship) {
		super(relationship);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.ui.editor.gef3.editparts.relationship.RelationshipEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		RecursiveRelationshipFigure connection = new RecursiveRelationshipFigure();

		return connection;
	}
}
