/*
 * Copyright 2009-2014 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package jp.sourceforge.tmdmaker.treeeditpart;

import org.eclipse.gef.EditPart;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.AbstractRelationship;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.CombinationTable;
import jp.sourceforge.tmdmaker.model.Detail;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.Entity2SubsetTypeRelationship;
import jp.sourceforge.tmdmaker.model.Entity2VirtualSupersetTypeRelationship;
import jp.sourceforge.tmdmaker.model.Event2EventRelationship;
import jp.sourceforge.tmdmaker.model.IVisitor;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.IdentifierRef;
import jp.sourceforge.tmdmaker.model.Laputa;
import jp.sourceforge.tmdmaker.model.MappingList;
import jp.sourceforge.tmdmaker.model.ModelElement;
import jp.sourceforge.tmdmaker.model.MultivalueAndAggregator;
import jp.sourceforge.tmdmaker.model.MultivalueAndSuperset;
import jp.sourceforge.tmdmaker.model.MultivalueOrEntity;
import jp.sourceforge.tmdmaker.model.RecursiveRelationship;
import jp.sourceforge.tmdmaker.model.RecursiveTable;
import jp.sourceforge.tmdmaker.model.RelatedRelationship;
import jp.sourceforge.tmdmaker.model.SubsetEntity;
import jp.sourceforge.tmdmaker.model.SubsetType;
import jp.sourceforge.tmdmaker.model.VirtualEntity;
import jp.sourceforge.tmdmaker.model.VirtualSuperset;
import jp.sourceforge.tmdmaker.model.VirtualSupersetType;
import jp.sourceforge.tmdmaker.model.other.Memo;

/**
 * 
 * Visitor パターンの実装。IAcceptorを実装した各モデルに対し、visitメソッドで定義した処理を実行する。
 * ここでは、各モデルに対応したtreeview用のEditPartを生成する。
 * 
 * @author tohosaku
 *
 */
public class TMDOutlineTreeEditPartVisitor  implements IVisitor{

	private EditPart part = null;
	
	public EditPart getEditPart() {
		return part;
	}

	@Override
	public void visit(ModelElement model) {
		part = null;
	}

	@Override
	public void visit(AbstractRelationship relationship) {
		part = null;
	}

	@Override
	public void visit(Attribute attribute) {
		part = new AttributeTreeEditPart(attribute);
	}

	@Override
	public void visit(CombinationTable entity) {
		part = new AbstractEntityModelTreeEditPart<CombinationTable>(entity);
	}

	@Override
	public void visit(Detail entity) {
		part = new DetailTreeEditPart(entity);
	}

	@Override
	public void visit(Diagram diagram) {
		part = new DiagramTreeEditPart(diagram);
	}

	@Override
	public void visit(Entity entity) {
		part = new EntityTreeEditPart(entity);
	}

	@Override
	public void visit(Event2EventRelationship relationship) {
		part = null;
	}

	@Override
	public void visit(Entity2SubsetTypeRelationship relationship) {
		part = null;
	}

	@Override
	public void visit(Entity2VirtualSupersetTypeRelationship relationship) {
		part = null;
	}

	@Override
	public void visit(Laputa entity) {
		part = new AbstractEntityModelTreeEditPart<Laputa>(entity);
	}

	@Override
	public void visit(MappingList entity) {
		part = new AbstractEntityModelTreeEditPart<MappingList>(entity);
	}

	@Override
	public void visit(AbstractEntityModel entity) {
		part = new AbstractEntityModelTreeEditPart<AbstractEntityModel>(entity);
	}

	@Override
	public void visit(MultivalueAndSuperset entity) {
		part = null;
	}

	@Override
	public void visit(MultivalueAndAggregator aggregator) {
		part = null;
	}

	@Override
	public void visit(MultivalueOrEntity entity) {
		part = new AbstractEntityModelTreeEditPart<MultivalueOrEntity>(entity);
	}

	@Override
	public void visit(RecursiveRelationship relationship) {
		part = null;
	}

	@Override
	public void visit(RecursiveTable entity) {
		part = new AbstractEntityModelTreeEditPart<RecursiveTable>(entity);
	}

	@Override
	public void visit(RelatedRelationship relationship) {
		part = null;
	}

	@Override
	public void visit(SubsetEntity entity) {
		part = new AbstractEntityModelTreeEditPart<SubsetEntity>(entity);
	}

	@Override
	public void visit(SubsetType type) {
		part = null;
	}

	@Override
	public void visit(VirtualEntity entity) {
		part = new AbstractEntityModelTreeEditPart<VirtualEntity>(entity);
	}

	@Override
	public void visit(VirtualSuperset entity) {
		part = new AbstractEntityModelTreeEditPart<VirtualSuperset>(entity);
	}

	@Override
	public void visit(VirtualSupersetType type) {
		part = null;
	}

	@Override
	public void visit(IdentifierRef identifier) {
		part = new IdentifierRefTreeEditPart(identifier);
	}

	@Override
	public void visit(Identifier identifier) {
		part = new IdentifierTreeEditPart(identifier);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.IVisitor#visit(jp.sourceforge.tmdmaker.model.other.Memo)
	 */
	@Override
	public void visit(Memo type) {
		part = null;
	}
}
