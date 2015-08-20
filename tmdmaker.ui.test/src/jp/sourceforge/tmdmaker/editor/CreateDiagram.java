/*
 * Copyright 2009-2015 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package jp.sourceforge.tmdmaker.editor;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;

/**
 * @author nakag
 *
 */
public class CreateDiagram {
	private SWTWorkbenchBot	bot	= new SWTWorkbenchBot();
	public void createFile(final String projectName, final String fileName) throws Exception {
		bot.menu("File").menu("New").menu("Other...").click();
	    bot.waitUntil(Conditions.shellIsActive("New"));
		SWTBotShell shell = bot.shell("New");
		shell.activate();
		
		
	    SWTBotTree wizardTree = bot.tree();
	    wizardTree.expandNode("TMD-Maker").select("TM Diagram");
	    bot.button("Next >").click();
		
	    SWTBotTree projectSelectionTree = bot.tree();
	    projectSelectionTree.select(projectName); 
	    
	    bot.textWithLabel("File name:").setText(fileName);
	    bot.button("Finish").click();
		bot.waitUntil(Conditions.shellCloses(shell));
	}
}