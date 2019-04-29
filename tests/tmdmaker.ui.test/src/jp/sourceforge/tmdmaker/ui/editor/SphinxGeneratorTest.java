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
package jp.sourceforge.tmdmaker.ui.editor;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.Platform;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.sphinx.generate.DocGenerator;

/**
 * TMD-MakerのDocGeneratorテスト.
 *
 * SWTBotがFileDialogに対応していないので内部クラスを直接実行している.
 * 
 * @author nakag
 *
 */
@RunWith(SWTBotJunit4ClassRunner.class)
public class SphinxGeneratorTest extends AbstractUITest {
	DocGenerator generator;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		createEntity(50, 50, "顧客番号", 0);
		wait.waitFor(10);
		generator = new DocGenerator();
	}

	@Test
	public void generateTest() {
		Entity e = (Entity) tmdEditor.getRootModel().getChildren().get(0);
		List<AbstractEntityModel> list = Arrays.asList(e);
		String rootDir = Platform.getInstanceLocation().getURL().getPath() + projectName();
		generator.execute(rootDir, list);
		String docPath = Platform.getInstanceLocation().getURL().getPath() + projectName()
				+ File.separator + "doc";
		assertEquals(true, new File(docPath).exists());
	}
}
