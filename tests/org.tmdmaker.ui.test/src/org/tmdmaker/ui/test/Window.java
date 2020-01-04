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
package org.tmdmaker.ui.test;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

/**
 * Helper class for manipulating Window size.
 * 
 * @author nakag
 *
 */
public class Window {
	private final SWTGefBot bot;
	private final SWTBotGefEditor botEditor;

	public Window(SWTGefBot bot, SWTBotGefEditor botEditor) {
		this.bot = bot;
		this.botEditor = botEditor;
	}

	public void maximizeActiveWindow() {
		final Shell activeShell = bot.activeShell().widget;
		VoidResult maximizeShell = new VoidResult() {
			@Override
			public void run() {
				activeShell.setMaximized(true);
			}
		};
		UIThreadRunnable.syncExec(maximizeShell);
	}

	public void maximizeActiveEditor() {
		VoidResult maximizeShell = new VoidResult() {
			@Override
			public void run() {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
						.setPartState(botEditor.getReference(), IWorkbenchPage.STATE_MAXIMIZED);
			}
		};
		UIThreadRunnable.syncExec(maximizeShell);
	}

	public void normalizeActiveEditor() {
		VoidResult maximizeShell = new VoidResult() {
			@Override
			public void run() {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
						.setPartState(botEditor.getReference(), IWorkbenchPage.STATE_RESTORED);
			}
		};
		UIThreadRunnable.syncExec(maximizeShell);
	}

}
