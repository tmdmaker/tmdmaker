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

public class TMDOutlineTreeEditPartVisitor  implements IVisitor{

	EditPart part = null;
	
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
	public void visit(Attribute entity) {
		part = new AttributeTreeEditPart();
		part.setModel(entity);
	}

	@Override
	public void visit(CombinationTable entity) {
		part = new AbstractEntityModelTreeEditPart<CombinationTable>();
		part.setModel(entity);
	}

	@Override
	public void visit(Detail entity) {
		part = new DetailTreeEditPart();
		part.setModel(entity);
	}

	@Override
	public void visit(Diagram diagram) {
		part = new DiagramTreeEditPart();
		part.setModel(diagram);
	}

	@Override
	public void visit(Entity entity) {
		part = new EntityTreeEditPart();
		part.setModel(entity);
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
		part = new AbstractEntityModelTreeEditPart<Laputa>();
		part.setModel(entity);
	}

	@Override
	public void visit(MappingList entity) {
		part = new AbstractEntityModelTreeEditPart<MappingList>();
		part.setModel(entity);
	}

	@Override
	public void visit(AbstractEntityModel entity) {
		part = new AbstractEntityModelTreeEditPart<AbstractEntityModel>();
		part.setModel(entity);
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
		part = new AbstractEntityModelTreeEditPart<MultivalueOrEntity>();
		part.setModel(entity);
	}

	@Override
	public void visit(RecursiveRelationship relationship) {
		part = null;
	}

	@Override
	public void visit(RecursiveTable entity) {
		part = new AbstractEntityModelTreeEditPart<RecursiveTable>();
		part.setModel(entity);
	}

	@Override
	public void visit(RelatedRelationship relationship) {
		part = null;
	}

	@Override
	public void visit(SubsetEntity entity) {
		part = new AbstractEntityModelTreeEditPart<SubsetEntity>();
		part.setModel(entity);
	}

	@Override
	public void visit(SubsetType type) {
		part = null;
	}

	@Override
	public void visit(VirtualEntity entity) {
		part = new AbstractEntityModelTreeEditPart<VirtualEntity>();
		part.setModel(entity);
	}

	@Override
	public void visit(VirtualSuperset entity) {
		part = new AbstractEntityModelTreeEditPart<VirtualSuperset>();
		part.setModel(entity);
	}

	@Override
	public void visit(VirtualSupersetType type) {
		part = null;
	}

	@Override
	public void visit(IdentifierRef identifier) {
		part = new IdentifierRefTreeEditPart();
		part.setModel(identifier);
	}

	@Override
	public void visit(Identifier identifier) {
		part = new IdentifierTreeEditPart();
		part.setModel(identifier);
	}

}
