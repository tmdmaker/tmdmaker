/*
 * Copyright 2009-2019 TMD-Maker Project <https://tmdmaker.osdn.jp/>
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

package org.tmdmaker.ui.editor.gef3.editparts;

import org.eclipse.gef.EditPart;
import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.AbstractRelationship;
import org.tmdmaker.core.model.Attribute;
import org.tmdmaker.core.model.CombinationTable;
import org.tmdmaker.core.model.Detail;
import org.tmdmaker.core.model.Diagram;
import org.tmdmaker.core.model.Entity;
import org.tmdmaker.core.model.Entity2SubsetTypeRelationship;
import org.tmdmaker.core.model.Entity2VirtualSupersetTypeRelationship;
import org.tmdmaker.core.model.Event2EventRelationship;
import org.tmdmaker.core.model.IVisitor;
import org.tmdmaker.core.model.Identifier;
import org.tmdmaker.core.model.IdentifierRef;
import org.tmdmaker.core.model.Laputa;
import org.tmdmaker.core.model.MappingList;
import org.tmdmaker.core.model.ModelElement;
import org.tmdmaker.core.model.MultivalueAndAggregator;
import org.tmdmaker.core.model.MultivalueAndSuperset;
import org.tmdmaker.core.model.MultivalueOrEntity;
import org.tmdmaker.core.model.RecursiveRelationship;
import org.tmdmaker.core.model.RecursiveTable;
import org.tmdmaker.core.model.RelatedRelationship;
import org.tmdmaker.core.model.SubsetEntity;
import org.tmdmaker.core.model.SubsetType;
import org.tmdmaker.core.model.TurboFileRelationship;
import org.tmdmaker.core.model.VirtualEntity;
import org.tmdmaker.core.model.VirtualSuperset;
import org.tmdmaker.core.model.VirtualSupersetType;
import org.tmdmaker.core.model.VirtualSupersetType2VirtualSupersetRelationship;
import org.tmdmaker.core.model.other.Memo;
import org.tmdmaker.core.model.other.TurboFile;
import org.tmdmaker.ui.editor.gef3.editparts.node.AttributeEditPart;
import org.tmdmaker.ui.editor.gef3.editparts.node.CombinationTableEditPart;
import org.tmdmaker.ui.editor.gef3.editparts.node.DetailEditPart;
import org.tmdmaker.ui.editor.gef3.editparts.node.EntityEditPart;
import org.tmdmaker.ui.editor.gef3.editparts.node.LaputaEditPart;
import org.tmdmaker.ui.editor.gef3.editparts.node.MappingListEditPart;
import org.tmdmaker.ui.editor.gef3.editparts.node.MemoEditPart;
import org.tmdmaker.ui.editor.gef3.editparts.node.MultivalueAndAggregatorEditPart;
import org.tmdmaker.ui.editor.gef3.editparts.node.MultivalueAndSupersetEditPart;
import org.tmdmaker.ui.editor.gef3.editparts.node.MultivalueOrEditPart;
import org.tmdmaker.ui.editor.gef3.editparts.node.RecursiveTableEditPart;
import org.tmdmaker.ui.editor.gef3.editparts.node.SubsetEntityEditPart;
import org.tmdmaker.ui.editor.gef3.editparts.node.SubsetTypeEditPart;
import org.tmdmaker.ui.editor.gef3.editparts.node.TurboFileEditPart;
import org.tmdmaker.ui.editor.gef3.editparts.node.VirtualEntityEditPart;
import org.tmdmaker.ui.editor.gef3.editparts.node.VirtualSupersetEditPart;
import org.tmdmaker.ui.editor.gef3.editparts.node.VirtualSupersetTypeEditPart;
import org.tmdmaker.ui.editor.gef3.editparts.relationship.Entity2SubsetTypeRelationshipEditPart;
import org.tmdmaker.ui.editor.gef3.editparts.relationship.RecursiveRelationshipEditPart;
import org.tmdmaker.ui.editor.gef3.editparts.relationship.RelatedRelationshipEditPart;
import org.tmdmaker.ui.editor.gef3.editparts.relationship.RelationshipEditPart;

/**
 * 
 * Visitor パターンの実装。IAcceptorを実装した各モデルに対し、visitメソッドで定義した処理を実行する。
 * ここでは、各モデルに対応したEditPartを生成する。
 * 
 * @author tohosaku
 *
 */
public class TMDEditPartVisitor implements IVisitor {

	private EditPart part = null;

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

	/**
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.IVisitor#visit(org.tmdmaker.core.model.other.Memo)
	 */
	@Override
	public void visit(Memo model) {
		part = new MemoEditPart(model);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.IVisitor#visit(org.tmdmaker.core.model.other.TurboFile)
	 */
	@Override
	public void visit(TurboFile entity) {
		part = new TurboFileEditPart(entity);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.IVisitor#visit(org.tmdmaker.core.model.TurboFileRelationship)
	 */
	@Override
	public void visit(TurboFileRelationship relationship) {
		part = new RelationshipEditPart(relationship);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.IVisitor#visit(org.tmdmaker.core.model.VirtualSupersetType2VirtualSupersetRelationship)
	 */
	@Override
	public void visit(VirtualSupersetType2VirtualSupersetRelationship relationship) {
		part = new RelatedRelationshipEditPart(relationship);
	}
}
