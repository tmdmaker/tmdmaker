/*
 * Copyright 2009-2015 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.CombinationTable;
import jp.sourceforge.tmdmaker.model.Detail;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.Entity2SubsetTypeRelationship;
import jp.sourceforge.tmdmaker.model.Entity2VirtualSupersetTypeRelationship;
import jp.sourceforge.tmdmaker.model.EntityType;
import jp.sourceforge.tmdmaker.model.Event2EventRelationship;
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
import jp.sourceforge.tmdmaker.model.SubsetType.SubsetTypeValue;
import jp.sourceforge.tmdmaker.model.VirtualEntity;
import jp.sourceforge.tmdmaker.model.VirtualSuperset;
import jp.sourceforge.tmdmaker.model.VirtualSupersetType;

import org.eclipse.gef.EditPart;
import org.junit.Before;
import org.junit.Test;

/**
 * TMDEditPartFactoryのテストクラス.
 *
 * @author nakag
 *
 */
public class TMDEditPartFactoryTest {
	private TMDEditPartFactory factory;
	private Diagram diagram;
	private Entity e1;
	private Entity e2;
	private SubsetType subsetType;
	private VirtualSuperset vsp;
	private VirtualSupersetType vtype;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		diagram = new Diagram();
		e1 = diagram.createEntity("テスト1", "テスト1番号", EntityType.EVENT);
		e2 = diagram.createEntity("テスト2", "テスト2番号", EntityType.EVENT);

		factory = new TMDEditPartFactory();
		subsetType = new SubsetType();
		subsetType.setExceptNull(false);
		subsetType.setSubsetType(SubsetTypeValue.SAME);
		diagram.addChild(subsetType);

		List<AbstractEntityModel> list = new ArrayList<AbstractEntityModel>();
		list.add(e1);
		list.add(e2);
		vsp = diagram.createVirtualSuperset("スーパーセット", list);
		vtype = vsp.getVirtualSupersetType();
	}

	/**
	 * Test method for
	 * {@link jp.sourceforge.tmdmaker.editpart.TMDEditPartFactory#createEditPart(org.eclipse.gef.EditPart, java.lang.Object)}
	 * .
	 */
	@Test
	public void testCreateEditPart() {
		Object o = new Attribute();
		EditPart editPart = factory.createEditPart(null, o);
		assertThat(editPart, instanceOf(AttributeEditPart.class));

		o = new CombinationTable();
		editPart = factory.createEditPart(null, o);
		assertThat(editPart, instanceOf(CombinationTableEditPart.class));

		o = new Detail();
		editPart = factory.createEditPart(null, o);
		assertThat(editPart, instanceOf(DetailEditPart.class));

		o = new Diagram();
		editPart = factory.createEditPart(null, o);
		assertThat(editPart, instanceOf(DiagramEditPart.class));

		o = new Entity();
		editPart = factory.createEditPart(null, o);
		assertThat(editPart, instanceOf(EntityEditPart.class));

		o = new Laputa();
		editPart = factory.createEditPart(null, o);
		assertThat(editPart, instanceOf(LaputaEditPart.class));

		o = new Laputa();
		editPart = factory.createEditPart(null, o);
		assertThat(editPart, instanceOf(LaputaEditPart.class));

		o = new MappingList();
		editPart = factory.createEditPart(null, o);
		assertThat(editPart, instanceOf(MappingListEditPart.class));

		o = new MultivalueAndSuperset();
		editPart = factory.createEditPart(null, o);
		assertThat(editPart, instanceOf(MultivalueAndSupersetEditPart.class));

		o = new MultivalueOrEntity();
		editPart = factory.createEditPart(null, o);
		assertThat(editPart, instanceOf(MultivalueOrEditPart.class));

		o = new RecursiveTable();
		editPart = factory.createEditPart(null, o);
		assertThat(editPart, instanceOf(RecursiveTableEditPart.class));

		o = new SubsetEntity();
		editPart = factory.createEditPart(null, o);
		assertThat(editPart, instanceOf(SubsetEntityEditPart.class));

		o = new VirtualEntity();
		editPart = factory.createEditPart(null, o);
		assertThat(editPart, instanceOf(VirtualEntityEditPart.class));

		o = new VirtualSuperset();
		editPart = factory.createEditPart(null, o);
		assertThat(editPart, instanceOf(VirtualSupersetEditPart.class));

		o = new RecursiveRelationship(e1);
		editPart = factory.createEditPart(null, o);
		assertThat(editPart, instanceOf(RecursiveRelationshipEditPart.class));

		o = new Event2EventRelationship(e1, e2);
		editPart = factory.createEditPart(null, o);
		assertThat(editPart, instanceOf(RelationshipEditPart.class));

		Entity2SubsetTypeRelationship r1 = new Entity2SubsetTypeRelationship(
				e1, subsetType);
		r1.connect();
		editPart = factory.createEditPart(null, r1);
		assertThat(editPart,
				instanceOf(Entity2SubsetTypeRelationshipEditPart.class));

		o = (Entity2VirtualSupersetTypeRelationship) vtype
				.getModelTargetConnections().get(0);
		editPart = factory.createEditPart(null, o);
		assertThat(editPart, instanceOf(RelatedRelationshipEditPart.class));

		o = new MultivalueAndAggregator();
		editPart = factory.createEditPart(null, o);
		assertThat(editPart, instanceOf(MultivalueAndAggregatorEditPart.class));

		o = new RelatedRelationship(e1, e2);
		editPart = factory.createEditPart(null, o);
		assertThat(editPart, instanceOf(RelatedRelationshipEditPart.class));

		o = new SubsetType();
		editPart = factory.createEditPart(null, o);
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
