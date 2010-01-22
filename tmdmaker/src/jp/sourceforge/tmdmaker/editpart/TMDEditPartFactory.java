/*
 * Copyright 2009-2010 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

import jp.sourceforge.tmdmaker.model.AbstractRelationship;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.CombinationTable;
import jp.sourceforge.tmdmaker.model.Detail;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.Entity2SubsetTypeRelationship;
import jp.sourceforge.tmdmaker.model.Event2EventRelationship;
import jp.sourceforge.tmdmaker.model.MappingList;
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
import jp.sourceforge.tmdmaker.model.VirtualSupersetAggregator;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

/**
 * TMDモデルのEditPartFactory
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
		} else if (model instanceof AbstractRelationship) {
			part = new RelationshipEditPart();
		} else if (model instanceof Entity2SubsetTypeRelationship) {
			part = new Entity2SubsetTypeRelationshipEditPart();
		} else if (model instanceof RelatedRelationship) {
			part = new RelatedRelationshipEditPart();
		} else if (model instanceof CombinationTable) {
			part = new CombinationTableEditPart();
		} else if (model instanceof MappingList) {
			part = new MappingListEditPart();
		} else if (model instanceof RecursiveTable) {
			part = new RecursiveTableEditPart();
		} else if (model instanceof Entity) {
			part = new EntityEditPart();
		} else if (model instanceof SubsetType) {
			part = new SubsetTypeEditPart();
		} else if (model instanceof SubsetEntity) {
			part = new SubsetEntityEditPart();
		} else if (model instanceof MultivalueOrEntity) {
			part = new MultivalueOrEditPart();
		} else if (model instanceof MultivalueAndAggregator) {
			part = new MultivalueAndAggregatorEditPart();
		} else if (model instanceof MultivalueAndSuperset) {
			part = new MultivalueAndSupersetEditPart();
		} else if (model instanceof Detail) {
			part = new DetailEditPart();
		} else if (model instanceof VirtualEntity) {
			part = new VirtualEntityEditPart();
		} else if (model instanceof VirtualSuperset) {
			part = new VirtualSupersetEditPart();
		} else if (model instanceof VirtualSupersetAggregator) {
			part = new VirtualSupersetAggregatorEditPart();
		} else if (model instanceof Attribute) {
			part = new AttributeEditPart();
		}
		if (part != null) {
			part.setModel(model);
		}
		return part;
	}
}
