/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org/>
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
import org.tmdmaker.ui.editor.gef3.editpolicies.AttributeComponentEditPolicy;
import org.tmdmaker.ui.editor.gef3.editpolicies.CombinationTableComponentEditPolicy;
import org.tmdmaker.ui.editor.gef3.editpolicies.DetailComponentEditPolicy;
import org.tmdmaker.ui.editor.gef3.editpolicies.EntityEditComponentPolicy;
import org.tmdmaker.ui.editor.gef3.editpolicies.LaputaComponentEditPolicy;
import org.tmdmaker.ui.editor.gef3.editpolicies.MappingListComponentEditPolicy;
import org.tmdmaker.ui.editor.gef3.editpolicies.MultivalueOrEntityComponentEditPolicy;
import org.tmdmaker.ui.editor.gef3.editpolicies.RecursiveTableComponentEditPolicy;
import org.tmdmaker.ui.editor.gef3.editpolicies.SubsetEntityComponentEditPolicy;
import org.tmdmaker.ui.editor.gef3.editpolicies.TurboFileComponentEditPolicy;
import org.tmdmaker.ui.editor.gef3.editpolicies.VirtualEntityComponentEditPolicy;
import org.tmdmaker.ui.editor.gef3.editpolicies.VirtualSupersetComponentEditPolicy;

/**
 * 
 * Visitor パターンの実装。IAcceptorを実装した各モデルに対し、visitメソッドで定義した処理を実行する。
 * ここでは、各モデルに対応したtreeview用のEditPartを生成する。
 * 
 * @author tohosaku
 *
 */
public class TMDOutlineTreeEditPartVisitor implements IVisitor {

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
		part = new AttributeTreeEditPart(attribute, new AttributeComponentEditPolicy());
	}

	@Override
	public void visit(CombinationTable entity) {
		part = new AbstractEntityModelTreeEditPart<CombinationTable>(entity, new CombinationTableComponentEditPolicy());
	}

	@Override
	public void visit(Detail entity) {
		part = new DetailTreeEditPart(entity, new DetailComponentEditPolicy());
	}

	@Override
	public void visit(Diagram diagram) {
		part = new DiagramTreeEditPart(diagram);
	}

	@Override
	public void visit(Entity entity) {
		part = new EntityTreeEditPart(entity, new EntityEditComponentPolicy());
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
		part = new AbstractEntityModelTreeEditPart<Laputa>(entity, new LaputaComponentEditPolicy());
	}

	@Override
	public void visit(MappingList entity) {
		part = new AbstractEntityModelTreeEditPart<MappingList>(entity, new MappingListComponentEditPolicy());
	}

	@Override
	public void visit(AbstractEntityModel entity) {
		part = new AbstractEntityModelTreeEditPart<AbstractEntityModel>(entity, null);
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
		part = new AbstractEntityModelTreeEditPart<MultivalueOrEntity>(entity, new MultivalueOrEntityComponentEditPolicy());
	}

	@Override
	public void visit(RecursiveRelationship relationship) {
		part = null;
	}

	@Override
	public void visit(RecursiveTable entity) {
		part = new AbstractEntityModelTreeEditPart<RecursiveTable>(entity, new RecursiveTableComponentEditPolicy());
	}

	@Override
	public void visit(RelatedRelationship relationship) {
		part = null;
	}

	@Override
	public void visit(SubsetEntity entity) {
		part = new AbstractEntityModelTreeEditPart<SubsetEntity>(entity, new SubsetEntityComponentEditPolicy());
	}

	@Override
	public void visit(SubsetType type) {
		part = null;
	}

	@Override
	public void visit(VirtualEntity entity) {
		part = new AbstractEntityModelTreeEditPart<VirtualEntity>(entity, new VirtualEntityComponentEditPolicy());
	}

	@Override
	public void visit(VirtualSuperset entity) {
		part = new AbstractEntityModelTreeEditPart<VirtualSuperset>(entity, new VirtualSupersetComponentEditPolicy());
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
		part = new IdentifierTreeEditPart(identifier, new AttributeComponentEditPolicy());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.IVisitor#visit(org.tmdmaker.core.model.other.Memo)
	 */
	@Override
	public void visit(Memo type) {
		part = null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.IVisitor#visit(org.tmdmaker.core.model.other.TurboFile)
	 */
	@Override
	public void visit(TurboFile entity) {
		part = new AbstractEntityModelTreeEditPart<TurboFile>(entity,new TurboFileComponentEditPolicy());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.IVisitor#visit(org.tmdmaker.core.model.TurboFileRelationship)
	 */
	@Override
	public void visit(TurboFileRelationship relationship) {
		part = null;
	}

	@Override
	public void visit(VirtualSupersetType2VirtualSupersetRelationship relationship) {
		part = null;
	}
}
