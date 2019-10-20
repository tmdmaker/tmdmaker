package org.tmdmaker.ui.test;

import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;

import jp.sourceforge.tmdmaker.TMDEditor;

public abstract class AbstractTester {
	protected final SWTGefBot bot;
	protected final SWTBotGefEditor botEditor;
	protected TMDEditor tmdEditor;
	protected BotWait wait = new BotWait();

	public AbstractTester(SWTGefBot bot, SWTBotGefEditor botEditor) {
		this.bot = bot;
		this.botEditor = botEditor;
		this.tmdEditor = (TMDEditor) botEditor.getReference().getEditor(false);
	}

	protected void setUp() {}
	protected abstract void doTest();
	protected void tearDown() {}

	public void test() {
		try {
			setUp();
			doTest();
		} catch (Error e) {
			throw e;
		} finally {
			tearDown();
		}
	}

}
