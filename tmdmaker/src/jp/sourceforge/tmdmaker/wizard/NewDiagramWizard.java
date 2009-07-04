package jp.sourceforge.tmdmaker.wizard;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import jp.sourceforge.tmdmaker.XStreamSerializer;
import jp.sourceforge.tmdmaker.model.Diagram;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.ide.IDE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author nakaG
 *
 */
public class NewDiagramWizard extends Wizard implements INewWizard {
	/** logging */
	protected static Logger logger = LoggerFactory
			.getLogger(NewDiagramWizard.class);

	private IWorkbench workbench;
	private IStructuredSelection selection;
	private NewDiagramCreationPage page;
	
	
	public NewDiagramWizard() {
		super();
		setNeedsProgressMonitor(true);
		setWindowTitle("新規作成");		
	}

	@Override
	public boolean performFinish() {
		IFile file = page.createNewFile();
		if (file == null) {
			return false;
		}
		IWorkbenchPage page = workbench.getActiveWorkbenchWindow().getActivePage();
		try {
			IDE.openEditor(page, file, true);
		} catch (PartInitException e) {
			logger.error("open error.", e);
			return false;
		}
		return true;
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
		this.selection = selection;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		page = new NewDiagramCreationPage("new TMD", selection);
		addPage(page);
		super.addPages();
		
	}
	private static class NewDiagramCreationPage extends WizardNewFileCreationPage {

		public NewDiagramCreationPage(String string,
				IStructuredSelection selection) {
			super(string, selection);
			setFileExtension("tmd");
			setFileName("diagram.tmd");
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#getInitialContents()
		 */
		@Override
		protected InputStream getInitialContents() {
			Diagram diagram = new Diagram();
			try {
				return XStreamSerializer.serializeStream(diagram, this.getClass().getClassLoader());
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}

//		/**
//		 * {@inheritDoc}
//		 *
//		 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#createLinkTarget()
//		 */
//		@Override
//		protected void createLinkTarget() {
//			// TODO Auto-generated method stub
//		}
//		
	}
}