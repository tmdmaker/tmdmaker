package jp.sourceforge.tmdmaker.model.generate.attributelist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.EntityType;
import jp.sourceforge.tmdmaker.model.Header2DetailRelationship;

public class AttributeListModelBuilderTest {

	@Test
	public void test() {
		Diagram diagram = new Diagram();
		diagram.createEntity("テスト1", "テスト1番号", EntityType.EVENT);
		diagram.createEntity("テスト2", "テスト2番号", EntityType.EVENT);
		Map<String, EntityAttributePair> results = new AttributeListModelBuilder()
				.build(diagram.findEntityModel());

		assertEquals(results.keySet().size(), 4);
		assertTrue(results.containsKey("テスト1番号_テスト1"));
		EntityAttributePair pair = results.values().iterator().next();
		assertEquals(pair.getAttribute().getName(), "テスト1日");
		assertEquals("テスト1", pair.getModel().getName());
	}

	@Test
	public void testDetail() {
		Diagram diagram = new Diagram();
		Entity e1 = diagram.createEntity("テスト1", "テスト1番号", EntityType.EVENT);
		diagram.createEntity("テスト2", "テスト2番号", EntityType.RESOURCE);
		diagram.createEntity("テスト3", "テスト3番号", EntityType.EVENT);

		Header2DetailRelationship r1 = new Header2DetailRelationship(e1);
		r1.connect();

		Map<String, EntityAttributePair> results = new AttributeListModelBuilder()
				.build(diagram.findEntityModel());
		assertEquals(results.keySet().size(), 7);
	}
}
