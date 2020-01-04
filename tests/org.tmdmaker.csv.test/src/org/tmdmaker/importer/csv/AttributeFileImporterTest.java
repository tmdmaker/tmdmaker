/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org>
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
package org.tmdmaker.importer.csv;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.importer.csv.AttributeFileImporter;

/**
 * AttributeFileImporterのテストクラス.
 * 
 * @author nakag
 *
 */
public class AttributeFileImporterTest {
	private File testFile;
	private AttributeFileImporter importer;

	@Before
	public void setUp() throws Throwable {
		importer = new AttributeFileImporter();
		testFile = File.createTempFile("test", "csv");
		try (PrintWriter w = new PrintWriter(new FileWriter(testFile))) {
			w.println("テスト１");
			w.println("テスト２");
			w.println("テスト３");
			w.flush();
		}
	}

	@After
	public void tearDown() {
		if (testFile != null) {
			testFile.delete();
		}
	}

	@Test
	public void test() throws Throwable {
		List<AbstractEntityModel> entities = importer.importEntities(testFile.getAbsolutePath());
		assertEquals(1, entities.size());

		AbstractEntityModel model = entities.get(0);
		assertEquals(3, model.getAttributes().size());
	}

	@Test
	public void testExtension() {
		assertEquals("csv", importer.getAvailableExtensions()[0]);
	}

	@Test
	public void testName() {
		assertEquals("Import Attributes from the file", importer.getImporterName());
	}
}
