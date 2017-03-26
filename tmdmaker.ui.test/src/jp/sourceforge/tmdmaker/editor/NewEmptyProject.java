package jp.sourceforge.tmdmaker.editor;

import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;

public class NewEmptyProject {
	private SWTBot bot = new SWTBot();

	public void createProject(String projectName) throws Exception {
		bot.menu("File").menu("New").menu("Project...").click();
		bot.sleep(300);
		SWTBotShell shell = bot.shell("New Project");
		shell.activate();
		bot.sleep(300);
		SWTBotTree projectSelectionTree = bot.tree();
		projectSelectionTree.expandNode("General").expandNode("Project")
				.select();
		bot.waitUntil(new DefaultCondition() {
			public String getFailureMessage() {
				return "unable to select";
			}

			public boolean test() throws Exception {
				return bot.button("Next >").isEnabled();
			}

		});
		bot.button("Next >").click();
		bot.sleep(300);
		bot.textWithLabel("Project name:").setText(projectName);
		bot.button("Finish").click();
		bot.waitUntil(Conditions.shellCloses(shell));
	}
}
