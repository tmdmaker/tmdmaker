package jp.sourceforge.tmdmaker.model;

public interface IVisitor {
	void visit(ModelElement model);
	void visit(AbstractRelationship relationship);
	void visit(Attribute entity);
	void visit(CombinationTable entity);
	void visit(Detail entity);
	void visit(Diagram diagram);
	void visit(Entity entity);
	void visit(Event2EventRelationship relationship);
	void visit(Entity2SubsetTypeRelationship relationship);
	void visit(Entity2VirtualSupersetTypeRelationship relationship);
	void visit(IdentifierRef identifier);
	void visit(Identifier identifier);
	void visit(Laputa entity);
	void visit(MappingList entity);
	void visit(AbstractEntityModel entity);
	void visit(MultivalueAndSuperset entity);
	void visit(MultivalueAndAggregator aggregator);
	void visit(MultivalueOrEntity entity);
	void visit(RecursiveRelationship relationship);
	void visit(RecursiveTable entity);
	void visit(RelatedRelationship relationship);
	void visit(SubsetEntity entity);
	void visit(SubsetType type);
	void visit(VirtualEntity entity);
	void visit(VirtualSuperset entity);
	void visit(VirtualSupersetType type);
}