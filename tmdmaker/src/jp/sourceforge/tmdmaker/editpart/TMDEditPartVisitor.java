/*
 * Copyright 2009-2011 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

package jp.sourceforge.tmdmaker.editpart;

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

/**
 * 
 * Visitor パターンの実装。IAcceptorを実装した各モデルに対し、visitメソッドで定義した処理を実行する。
 * ここでは、各モデルに対応したEditPartを生成する。
 * 
 * @author tohosaku
 *
 */
public class TMDEditPartVisitor implements IVisitor{
	
	EditPart part = null;
	
	public EditPart getEditPart() {
		return part;
	}

	@Override
	public void visit(Attribute attribute) {
		part = new AttributeEditPart(attribute);
	}

	@Override
	public void visit(CombinationTable table) {
		part = new CombinationTableEditPart(table);
	}

	@Override
	public void visit(Detail entity) {
		part = new DetailEditPart(entity);
	}

	@Override
	public void visit(Diagram diagram) {
		part = new DiagramEditPart(diagram);
	}

	@Override
	public void visit(Entity entity) {
		part = new EntityEditPart(entity);
	}

	@Override
	public void visit(Laputa entity) {
		part = new LaputaEditPart(entity);
	}

	@Override
	public void visit(MappingList table) {
		part = new MappingListEditPart(table);
	}

	@Override
	public void visit(AbstractEntityModel entity) {
		part = null;
	}

	@Override
	public void visit(MultivalueAndSuperset entity) {
		part = new MultivalueAndSupersetEditPart(entity);
	}

	@Override
	public void visit(MultivalueOrEntity entity) {
		part = new MultivalueOrEditPart(entity);
	}

	@Override
	public void visit(RecursiveTable table) {
		part = new RecursiveTableEditPart(table);
	}

	@Override
	public void visit(SubsetEntity entity) {
		part = new SubsetEntityEditPart(entity);
	}

	@Override
	public void visit(VirtualEntity entity) {
		part = new VirtualEntityEditPart(entity);
	}

	@Override
	public void visit(VirtualSuperset entity) {
		part = new VirtualSupersetEditPart(entity);
	}

	@Override
	public void visit(RecursiveRelationship relationship) {
		part = new RecursiveRelationshipEditPart(relationship);
	}

	@Override
	public void visit(AbstractRelationship relationship) {
		part = new RelationshipEditPart(relationship);
	}

	@Override
	public void visit(Event2EventRelationship relationship) {
		part = new RelationshipEditPart(relationship);
	}

	@Override
	public void visit(Entity2SubsetTypeRelationship relationship) {
		part = new Entity2SubsetTypeRelationshipEditPart(relationship);
	}

	@Override
	public void visit(Entity2VirtualSupersetTypeRelationship relationship) {
		part = new RelatedRelationshipEditPart(relationship);
	}

	@Override
	public void visit(MultivalueAndAggregator aggregator) {
		part = new MultivalueAndAggregatorEditPart(aggregator);
	}

	@Override
	public void visit(RelatedRelationship relationship) {
		part = new RelatedRelationshipEditPart(relationship);
	}

	@Override
	public void visit(SubsetType type) {
		part = new SubsetTypeEditPart(type);
	}

	@Override
	public void visit(VirtualSupersetType type) {
		part = new VirtualSupersetTypeEditPart(type);
	}

	@Override
	public void visit(ModelElement model) {
		part = null;
	}

	@Override
	public void visit(IdentifierRef identifier) {
		part = null;
	}

	@Override
	public void visit(Identifier identifier) {
		part = null;
	}
}
