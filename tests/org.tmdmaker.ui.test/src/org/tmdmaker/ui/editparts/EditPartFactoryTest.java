/*
 * Copyright 2009-2021 TMD-Maker Project <https://www.tmdmaker.org>
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
package org.tmdmaker.ui.editparts;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.EditPart;
import org.junit.Before;
import org.junit.Test;
import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.AbstractRelationship;
import org.tmdmaker.core.model.Attribute;
import org.tmdmaker.core.model.Cardinality;
import org.tmdmaker.core.model.Diagram;
import org.tmdmaker.core.model.Entity;
import org.tmdmaker.core.model.Entity2SubsetTypeRelationship;
import org.tmdmaker.core.model.Entity2VirtualSupersetTypeRelationship;
import org.tmdmaker.core.model.Event2EventRelationship;
import org.tmdmaker.core.model.Identifier;
import org.tmdmaker.core.model.Laputa;
import org.tmdmaker.core.model.MappingList;
import org.tmdmaker.core.model.ModelElement;
import org.tmdmaker.core.model.RecursiveRelationship;
import org.tmdmaker.core.model.RecursiveTable;
import org.tmdmaker.core.model.RelatedRelationship;
import org.tmdmaker.core.model.VirtualSuperset;
import org.tmdmaker.core.model.VirtualSupersetType;
import org.tmdmaker.core.model.parts.ModelName;
import org.tmdmaker.core.model.relationship.Relationship;
import org.tmdmaker.ui.editor.gef3.editparts.DiagramEditPart;
import org.tmdmaker.ui.editor.gef3.editparts.TMDEditPartFactory;
import org.tmdmaker.ui.editor.gef3.editparts.node.AttributeEditPart;
import org.tmdmaker.ui.editor.gef3.editparts.node.CombinationTableEditPart;
import org.tmdmaker.ui.editor.gef3.editparts.node.DetailEditPart;
import org.tmdmaker.ui.editor.gef3.editparts.node.EntityEditPart;
import org.tmdmaker.ui.editor.gef3.editparts.node.LaputaEditPart;
import org.tmdmaker.ui.editor.gef3.editparts.node.MappingListEditPart;
import org.tmdmaker.ui.editor.gef3.editparts.node.MultivalueAndAggregatorEditPart;
import org.tmdmaker.ui.editor.gef3.editparts.node.MultivalueAndSupersetEditPart;
import org.tmdmaker.ui.editor.gef3.editparts.node.MultivalueOrEditPart;
import org.tmdmaker.ui.editor.gef3.editparts.node.RecursiveTableEditPart;
import org.tmdmaker.ui.editor.gef3.editparts.node.SubsetEntityEditPart;
import org.tmdmaker.ui.editor.gef3.editparts.node.SubsetTypeEditPart;
import org.tmdmaker.ui.editor.gef3.editparts.node.VirtualEntityEditPart;
import org.tmdmaker.ui.editor.gef3.editparts.node.VirtualSupersetEditPart;
import org.tmdmaker.ui.editor.gef3.editparts.node.VirtualSupersetTypeEditPart;
import org.tmdmaker.ui.editor.gef3.editparts.relationship.Entity2SubsetTypeRelationshipEditPart;
import org.tmdmaker.ui.editor.gef3.editparts.relationship.RecursiveRelationshipEditPart;
import org.tmdmaker.ui.editor.gef3.editparts.relationship.RelatedRelationshipEditPart;
import org.tmdmaker.ui.editor.gef3.editparts.relationship.RelationshipEditPart;

/**
 * Test for EditPartFactory.
 * 
 * @author nakag
 *
 */
public class EditPartFactoryTest {
	private TMDEditPartFactory factory;
	private Diagram diagram;
	private Entity e1;
	private Entity e2;
	private Entity r1;
	private Entity r2;
	private VirtualSuperset vsp;
	private VirtualSupersetType vtype;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		diagram = new Diagram();
		e1 = Entity.ofEvent(new Identifier("テスト1番号")).withDefaultAttribute();
		diagram.addChild(e1);

		e2 = Entity.ofEvent(new Identifier("テスト2番号")).withDefaultAttribute();
		diagram.addChild(e2);

		r1 = Entity.ofResource(new Identifier("テスト１No")).withDefaultAttribute();
		diagram.addChild(r1);
		
		r2 = Entity.ofResource(new Identifier("テスト２No")).withDefaultAttribute();
		diagram.addChild(r2);

		factory = new TMDEditPartFactory();
//		subsetType = new SubsetType();
//		subsetType.setExceptNull(false);
//		subsetType.setSubsetType(SubsetTypeValue.SAME);
//		diagram.addChild(subsetType);

		List<AbstractEntityModel> list = new ArrayList<AbstractEntityModel>();
		list.add(e1);
		list.add(e2);
		vsp = VirtualSuperset.of(new ModelName("スーパーセット"));
		vsp.virtualSubsets().builder().subsetList(list).build();
		vtype = vsp.getVirtualSupersetType();
	}

	/**
	 * Test method for
	 * {@link org.tmdmaker.ui.editor.gef3.editparts.TMDEditPartFactory#createEditPart(org.eclipse.gef.EditPart, java.lang.Object)}
	 * .
	 */
	@Test
	public void testCreateEditPart() {
		Object o = new Attribute();
		EditPart editPart = factory.createEditPart(null, o);
		assertThat(editPart, instanceOf(AttributeEditPart.class));

		AbstractRelationship r = Relationship.of(r1, r2);
		r.connect();
		o = r.getTable();
		editPart = factory.createEditPart(null, o);
		assertThat(editPart, instanceOf(CombinationTableEditPart.class));

		Entity res1 = Entity.ofResource(new Identifier("リソース番号"));
		Entity ev1 = Entity.ofEvent(new Identifier("イベント番号"));
		AbstractRelationship rel = Relationship.of(res1, ev1);
		rel.connect();
		rel.setSourceCardinality(Cardinality.MANY);
		rel.setTargetCardinality(Cardinality.MANY);
		ev1.multivalueAnd().builder().build();

		o = ev1.multivalueAnd().detail();
		editPart = factory.createEditPart(null, o);
		assertThat(editPart, instanceOf(DetailEditPart.class));

		o = new Diagram();
		editPart = factory.createEditPart(null, o);
		assertThat(editPart, instanceOf(DiagramEditPart.class));

		o = Entity.ofResource(new ModelName("個体"), new Identifier("番号"));
		editPart = factory.createEditPart(null, o);
		assertThat(editPart, instanceOf(EntityEditPart.class));

		o = Laputa.of();
		editPart = factory.createEditPart(null, o);
		assertThat(editPart, instanceOf(LaputaEditPart.class));

		o = new MappingList();
		editPart = factory.createEditPart(null, o);
		assertThat(editPart, instanceOf(MappingListEditPart.class));

		o = ev1.multivalueAnd().superset();
		editPart = factory.createEditPart(null, o);
		assertThat(editPart, instanceOf(MultivalueAndSupersetEditPart.class));

		Entity ev2 = Entity.ofEvent(new Identifier("イベント番号"));
		ev2.multivalueOr().builder().typeName("テスト種別").build();
		o = ev2.multivalueOr().query().findByName(new ModelName("イベント.テスト種別")).get(0);
		editPart = factory.createEditPart(null, o);
		assertThat(editPart, instanceOf(MultivalueOrEditPart.class));

		o = new RecursiveTable();
		editPart = factory.createEditPart(null, o);
		assertThat(editPart, instanceOf(RecursiveTableEditPart.class));

		
		Entity e= Entity.ofResource(new Identifier("親ID"));
		e.subsets().builder().add(new ModelName("サブセット")).build();
		o = e.subsets().query().findByName(new ModelName("サブセット")).get(0);
		editPart = factory.createEditPart(null, o);
		assertThat(editPart, instanceOf(SubsetEntityEditPart.class));

		ModelName searchName = new ModelName("みなし");
		e.virtualEntities().builder().virtualEntityName(searchName).build();
		o = e.virtualEntities().query().findByName(searchName).get(0);
		editPart = factory.createEditPart(null, o);
		assertThat(editPart, instanceOf(VirtualEntityEditPart.class));

		o = VirtualSuperset.of(new ModelName("スーパーセット"));
		editPart = factory.createEditPart(null, o);
		assertThat(editPart, instanceOf(VirtualSupersetEditPart.class));

		o = new RecursiveRelationship(e1);
		editPart = factory.createEditPart(null, o);
		assertThat(editPart, instanceOf(RecursiveRelationshipEditPart.class));

		o = new Event2EventRelationship(e1, e2);
		editPart = factory.createEditPart(null, o);
		assertThat(editPart, instanceOf(RelationshipEditPart.class));

		Entity2SubsetTypeRelationship r1 = new Entity2SubsetTypeRelationship(e1);
		r1.connect();
		editPart = factory.createEditPart(null, r1);
		assertThat(editPart, instanceOf(Entity2SubsetTypeRelationshipEditPart.class));

		o = (Entity2VirtualSupersetTypeRelationship) vtype.getModelTargetConnections().get(0);
		editPart = factory.createEditPart(null, o);
		assertThat(editPart, instanceOf(RelatedRelationshipEditPart.class));

		o = ev1.multivalueAnd().aggregator();
		editPart = factory.createEditPart(null, o);
		assertThat(editPart, instanceOf(MultivalueAndAggregatorEditPart.class));

		o = new RelatedRelationship(e1, e2);
		editPart = factory.createEditPart(null, o);
		assertThat(editPart, instanceOf(RelatedRelationshipEditPart.class));

		Entity2SubsetTypeRelationship r2 = new Entity2SubsetTypeRelationship(null);
		editPart = factory.createEditPart(null, r2.getSubsetType());
		assertThat(editPart, instanceOf(SubsetTypeEditPart.class));

		o = vtype;
		editPart = factory.createEditPart(null, o);
		assertThat(editPart, instanceOf(VirtualSupersetTypeEditPart.class));

		o = new ModelElement();
		editPart = factory.createEditPart(null, o);
		assertThat(editPart, nullValue());

		// TODO 必要になったらaccept(IVisitor)メソッドをIdentifierRefでoverrideする
		// o = new IdentifierRef(e1.getIdentifier());
		// editPart = factory.createEditPart(null, o);
		// assertThat(editPart, nullValue());

		// TODO 必要になったらaccept(IVisitor)メソッドをIdentifierでoverrideする
		// Identifier i = e1.getIdentifier();
		// editPart = factory.createEditPart(null, i);
		// assertThat(editPart, nullValue());
	}
}
