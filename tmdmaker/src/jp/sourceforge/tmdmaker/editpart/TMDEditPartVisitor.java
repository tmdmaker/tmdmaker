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

public class TMDEditPartVisitor implements IVisitor{
	
	EditPart part = null;
	
	public EditPart getEditPart() {
		return part;
	}

	@Override
	public void visit(Attribute entity) {
		part = new AttributeEditPart();
		part.setModel(entity);
	}

	@Override
	public void visit(CombinationTable entity) {
		part = new CombinationTableEditPart();		
		part.setModel(entity);
	}

	@Override
	public void visit(Detail entity) {
		part = new DetailEditPart();
		part.setModel(entity);
	}

	@Override
	public void visit(Diagram diagram) {
		part = new DiagramEditPart();
		part.setModel(diagram);
	}

	@Override
	public void visit(Entity entity) {
		part = new EntityEditPart();
		part.setModel(entity);
	}

	@Override
	public void visit(Laputa entity) {
		part = new LaputaEditPart();
		part.setModel(entity);
	}

	@Override
	public void visit(MappingList entity) {
		part = new MappingListEditPart();
		part.setModel(entity);
	}

	@Override
	public void visit(AbstractEntityModel entity) {
	}

	@Override
	public void visit(MultivalueAndSuperset entity) {
		part = new MultivalueAndSupersetEditPart();
		part.setModel(entity);
	}

	@Override
	public void visit(MultivalueOrEntity entity) {
		part = new MultivalueOrEditPart();
	}

	@Override
	public void visit(RecursiveTable entity) {
		part = new RecursiveTableEditPart();
		part.setModel(entity);
	}

	@Override
	public void visit(SubsetEntity entity) {
		part = new SubsetEntityEditPart();
		part.setModel(entity);
	}

	@Override
	public void visit(VirtualEntity entity) {
		part = new VirtualEntityEditPart();
		part.setModel(entity);
	}

	@Override
	public void visit(VirtualSuperset entity) {
		part = new VirtualSupersetEditPart();
		part.setModel(entity);
	}

	@Override
	public void visit(RecursiveRelationship relationship) {
		part = new RecursiveRelationshipEditPart();
		part.setModel(relationship);
	}

	@Override
	public void visit(AbstractRelationship relationship) {
		part = new RelationshipEditPart();
		part.setModel(relationship);
	}

	@Override
	public void visit(Event2EventRelationship relationship) {
		part = new RelationshipEditPart();
		part.setModel(relationship);
	}

	@Override
	public void visit(Entity2SubsetTypeRelationship relationship) {
		part = new Entity2SubsetTypeRelationshipEditPart();
		part.setModel(relationship);
	}

	@Override
	public void visit(Entity2VirtualSupersetTypeRelationship relationship) {
		part = new RelatedRelationshipEditPart();
		part.setModel(relationship);
	}

	@Override
	public void visit(MultivalueAndAggregator aggregator) {
		part = new MultivalueAndAggregatorEditPart();
		part.setModel(aggregator);
	}

	@Override
	public void visit(RelatedRelationship relationship) {
		part = new RelatedRelationshipEditPart();
		part.setModel(relationship);
	}

	@Override
	public void visit(SubsetType type) {
		part = new SubsetTypeEditPart();
		part.setModel(type);
	}

	@Override
	public void visit(VirtualSupersetType type) {
		part = new VirtualSupersetTypeEditPart();
		part.setModel(type);
	}

	@Override
	public void visit(ModelElement model) {
		part = null;
	}
}
