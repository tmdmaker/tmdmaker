package org.tmdmaker.ui.editors.generators;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.FreeformGraphicalRootEditPart;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.tmdmaker.ui.test.AbstractTester;

import jp.sourceforge.tmdmaker.imagegenerator.Draw2dToImageConverter;

public class ImageGeneratorTester extends AbstractTester {

	public ImageGeneratorTester(SWTGefBot bot, SWTBotGefEditor botEditor) {
		super(bot, botEditor);
	}

	@Override
	protected void doTest() {
		Draw2dToImageConverter converter = new Draw2dToImageConverter();
		String tempDir = System.getProperty("java.io.tmpdir") + File.separator;
		execute(converter, tempDir, ".jpg");
		execute(converter, tempDir, ".png");
		execute(converter, tempDir, ".gif");
		execute(converter, tempDir, ".tiff");
		execute(converter, tempDir, ".bmp");
		execute(converter, tempDir, ".svg");
	}

	private void execute(Draw2dToImageConverter converter, String tempDir, String extention) {
		String outputPath = tempDir + "test" + extention;

		final FreeformGraphicalRootEditPart rootEditPart = (FreeformGraphicalRootEditPart) tmdEditor.getViewer()
				.getRootEditPart();

		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				converter.execute(rootEditPart.getLayer(LayerConstants.PRINTABLE_LAYERS), outputPath, extention);
			}
		});

		assertEquals(true, new File(outputPath).exists());
	}
}
