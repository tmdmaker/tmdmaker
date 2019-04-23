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
import java.nio.file.Files;
import java.nio.file.Paths;

import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.FreeformGraphicalRootEditPart;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import jp.sourceforge.tmdmaker.imagegenerator.Draw2dToImageConverter;

/**
 * TMD-MakerのImageGeneratorテスト.
 *
 * SWTBotがFileDialogに対応していないので内部クラスを直接実行している.
 * 
 * @author nakag
 *
 */
@RunWith(SWTBotJunit4ClassRunner.class)
public class ImageGeneratorTest extends AbstractUITest {
	Draw2dToImageConverter converter;
	String tempDir = System.getProperty("java.io.tmpdir") + File.separator;
	String outputPath = null;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		createEntity(50, 50, "顧客番号", 0);
		converter = new Draw2dToImageConverter();
		wait.waitFor(10);
	}

	@Override
	public void tearDown() throws Exception {
		Files.deleteIfExists(Paths.get(outputPath));
		super.tearDown();
	}

	@Test
	public void jpegTest() throws Exception {
		execute(".jpg");
	}

	@Test
	public void pngTest() throws Exception {
		execute(".png");
	}

	@Test
	public void gifTest() throws Exception {
		execute(".gif");
	}

	@Test
	public void tiffTest() throws Exception {
		execute(".tiff");
	}

	@Test
	public void bmpTest() throws Exception {
		execute(".bmp");
	}

	@Test
	public void svgTest() throws Exception {
		execute(".svg");
	}

	private void execute(String extention) {
		this.outputPath = tempDir + "test" + extention;

		final FreeformGraphicalRootEditPart rootEditPart = (FreeformGraphicalRootEditPart) tmdEditor
				.getViewer().getRootEditPart();

		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				converter.execute(rootEditPart.getLayer(LayerConstants.PRINTABLE_LAYERS),
						outputPath, extention);
			}
		});

		assertEquals(true, new File(outputPath).exists());
	}
}
