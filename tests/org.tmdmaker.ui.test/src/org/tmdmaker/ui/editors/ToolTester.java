package org.tmdmaker.ui.editors;

import static org.junit.Assert.assertEquals;

import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.tmdmaker.ui.test.AbstractTester;

public class ToolTester extends AbstractTester {

	public ToolTester(SWTGefBot bot, SWTBotGefEditor botEditor) {
		super(bot, botEditor);
	}

	@Override
	protected void doTest() {
		botEditor.activateTool("Entity");
		assertEquals("Entity", getActiveToolLabel());

		botEditor.activateTool("Relationship");
		assertEquals("Relationship", getActiveToolLabel());

		botEditor.activateTool("Turbo file");
		assertEquals("Turbo file", getActiveToolLabel());

		botEditor.activateTool("Memo");
		assertEquals("Memo", getActiveToolLabel());

		botEditor.activateTool("Select");
		assertEquals("Select", getActiveToolLabel());
	}

	private String getActiveToolLabel() {
		return botEditor.getActiveTool().getLabel();
	}
}
