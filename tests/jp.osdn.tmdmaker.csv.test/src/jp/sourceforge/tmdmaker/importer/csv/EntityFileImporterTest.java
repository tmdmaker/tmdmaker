/*
 * Copyright 2009-2019 TMD-Maker Project <https://tmdmaker.osdn.jp/>
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
package jp.sourceforge.tmdmaker.importer.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Entity;

/**
 * EntityFileImporterのテストクラス.
 * 
 * @author nakag
 *
 */
public class EntityFileImporterTest {
	private File testFile;
	private EntityFileImporter importer;

	@Before
	public void setUp() throws Exception {
		importer = new EntityFileImporter();
		testFile = File.createTempFile("test", "csv");
		try (PrintWriter w = new PrintWriter(new FileWriter(testFile))) {
			w.println("テスト１,アトリビュート1");
			w.println("テスト１,アトリビュート2");
			w.println("テスト２,テスト２番号");
			w.println("テスト２,テスト２名称");
			w.println("テスト３,テスト３ID");
			w.println("テスト３,テスト３日");
			w.flush();
		}
	}

	@After
	public void tearDown() throws Exception {
		if (testFile != null) {
			testFile.delete();
		}
	}

	@Test
	public void test() throws Throwable {
		List<AbstractEntityModel> entities = importer.importEntities(testFile.getAbsolutePath());
		assertEquals(3, entities.size());

		assertEquals(2, entities.get(0).getAttributes().size());

		assertEquals(1, entities.get(1).getAttributes().size());
		assertTrue(((Entity) entities.get(1)).isResource());

		assertEquals(1, entities.get(2).getAttributes().size());
		assertTrue(((Entity) entities.get(2)).isEvent());
	}

	@Test
	public void testExtension() {
		assertEquals("csv", importer.getAvailableExtensions()[0]);
	}

	@Test
	public void testName() {
		assertEquals("Import Enities from the file", importer.getImporterName());
	}

}
