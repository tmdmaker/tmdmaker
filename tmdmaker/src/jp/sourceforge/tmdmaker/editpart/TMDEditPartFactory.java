package jp.sourceforge.tmdmaker.editpart;

import jp.sourceforge.tmdmaker.model.CombinationTable;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.Event2EventRelationship;
import jp.sourceforge.tmdmaker.model.MappingList;
import jp.sourceforge.tmdmaker.model.RecursiveRelationship;
import jp.sourceforge.tmdmaker.model.RecursiveTable;
import jp.sourceforge.tmdmaker.model.RelatedRelationship;
import jp.sourceforge.tmdmaker.model.Relationship;
import jp.sourceforge.tmdmaker.model.Subset;
import jp.sourceforge.tmdmaker.model.SubsetEntity;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

/**
 * 
 * @author nakaG
 *
 */
public class TMDEditPartFactory implements EditPartFactory {

	/**
	 * @param context
	 *            コンテキスト
	 * @param model
	 *            TMのモデル
	 * @return EditPart modelに対応したEditPart
	 */
	@Override
	public final EditPart createEditPart(final EditPart context,
			final Object model) {
		EditPart part = null;
		if (model instanceof Diagram) {
			part = new DiagramEditPart();
		} else if (model instanceof RecursiveRelationship) {
			part = new RecursiveRelationshipEditPart();
		} else if (model instanceof Event2EventRelationship) {
			part = new RelationshipEditPart();
		} else if (model instanceof Relationship) {
			part = new RelationshipEditPart();
		} else if (model instanceof RelatedRelationship) {
			part = new RelatedRelationshipEditPart();
		} else if (model instanceof RecursiveMarkConnection) {
			part = new RecursiveConnectionEditPart();
		} else if (model instanceof CombinationTable) {
			part = new CombinationTableEditPart();
		} else if (model instanceof MappingList) {
			part = new MappingListEditPart();
		} else if (model instanceof RecursiveTable) {
			part = new RecursiveTableEditPart();
		} else if (model instanceof Entity) {
			part = new EntityEditPart();
		} else if (model instanceof Subset) {
			part = new SubsetEditPart();
		} else if (model instanceof SubsetEntity) {
			part = new SubsetEntityEditPart();
		}
		if (part != null) {
			part.setModel(model);
		}
		return part;
	}
}
