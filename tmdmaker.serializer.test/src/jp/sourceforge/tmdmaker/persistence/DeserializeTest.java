/**
 * 
 */
package jp.sourceforge.tmdmaker.persistence;

import static org.junit.Assert.assertNotNull;

import java.io.InputStream;

import org.junit.Test;

import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.ModelElement;

/**
 * @author nakag
 *
 */
public class DeserializeTest {

	@Test
	public void test040Deserialize() {
		InputStream in = getClass().getResourceAsStream("test040.tmd");
		XStreamSerializer serializer = new XStreamSerializer();
		Diagram diagram = serializer.deserialize(in);
		assertNotNull(diagram);
		for (ModelElement m : diagram.getChildren()) {
			
		}
	}
}
