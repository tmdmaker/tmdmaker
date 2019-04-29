package jp.sourceforge.tmdmaker.ui.editor;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.eclipse.gef.finder.SWTBotGefTestCase;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import jp.sourceforge.tmdmaker.TMDEditor;
import jp.sourceforge.tmdmaker.ui.test.BotWait;
import jp.sourceforge.tmdmaker.ui.test.CreateDiagram;
import jp.sourceforge.tmdmaker.ui.test.NewEmptyProject;
import jp.sourceforge.tmdmaker.ui.test.View;

public abstract class AbstractUITest extends SWTBotGefTestCase {

	private NewEmptyProject project = new NewEmptyProject();
	private CreateDiagram tmDiagram = new CreateDiagram();
	protected View propertiesView = new View("General", "Properties");
	protected View outlineView = new View("General", "Outline");
	protected SWTBotGefEditor botEditor;
	protected TMDEditor tmdEditor;
	protected BotWait wait = new BotWait();
	private static final String PROJECT_NAME = "test";
	private static final String FILE_NAME = "test.tmd";

	@BeforeClass
	public static void closeWelcomePage() {
		new View("Welcome").close();
	}

	public AbstractUITest() {
		super();
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		sleep();
		UIThreadRunnable.syncExec(new VoidResult() {
			public void run() {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().forceActive();
			}
		});
	
		project.createProject(PROJECT_NAME);
		sleep();
		tmDiagram.createFile(PROJECT_NAME, FILE_NAME);
		botEditor = bot.gefEditor(FILE_NAME);
		tmdEditor = (TMDEditor) botEditor.getReference().getEditor(false);
	}

	@After
	public void tearDown() throws Exception {
		if (botEditor != null)
			botEditor.close();
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJECT_NAME);
		project.delete(true, null);
		super.tearDown();
	}

	protected void sleep() {
		wait.waitDefault();
	}

	protected void maximizeActiveWindow() {
		final Shell activeShell = bot.activeShell().widget;
		VoidResult maximizeShell = new VoidResult() {
			@Override
			public void run() {
				activeShell.setMaximized(true);
			}
		};
		UIThreadRunnable.syncExec(maximizeShell);
	}

	protected void maximizeActiveEditor() {
		VoidResult maximizeShell = new VoidResult() {
			@Override
			public void run() {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
						.setPartState(botEditor.getReference(), IWorkbenchPage.STATE_MAXIMIZED);
			}
		};
		UIThreadRunnable.syncExec(maximizeShell);
	}

	protected void normalizeActiveEditor() {
		VoidResult maximizeShell = new VoidResult() {
			@Override
			public void run() {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
						.setPartState(botEditor.getReference(), IWorkbenchPage.STATE_RESTORED);
			}
		};
		UIThreadRunnable.syncExec(maximizeShell);
	}

	protected void openPreferenceForMac() {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		workbench.getDisplay().asyncExec(new Runnable() {
			public void run() {
				IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
				if (window != null) {
					Menu appMenu = workbench.getDisplay().getSystemMenu();
					for (MenuItem item : appMenu.getItems()) {
						if (item.getText().startsWith("Preferences")) {
							Event event = new Event();
							event.time = (int) System.currentTimeMillis();
							event.widget = item;
							event.display = workbench.getDisplay();
							item.setSelection(true);
							item.notifyListeners(SWT.Selection, event);
							break;
						}
					}
				}
			}
		});
	}

	protected void createEntity(int x, int y, String identifierName, int type) {
		botEditor.activateTool("Entity");
		botEditor.click(x, y);
		SWTBotShell shell = bot.shell("Create a new entity");
		shell.activate();
		bot.text(0).setFocus();
		bot.text(0).setText(identifierName);
		bot.radio(type).click();
		bot.button("OK").click();
		wait.waitDefault();
	}

	protected String projectName() {
		return PROJECT_NAME;
	}
}