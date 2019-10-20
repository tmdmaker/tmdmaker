package org.tmdmaker.ui.test;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

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
