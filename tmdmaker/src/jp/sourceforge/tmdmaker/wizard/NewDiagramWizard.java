/*
 * Copyright 2009-2016 TMD-Maker Project <http://tmdmaker.osdn.jp/>
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
package jp.sourceforge.tmdmaker.wizard;

import java.io.InputStream;

import jp.sourceforge.tmdmaker.Messages;
import jp.sourceforge.tmdmaker.extension.SerializerFactory;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.persistence.SerializationException;
import jp.sourceforge.tmdmaker.model.persistence.Serializer;

import org.eclipse.core.resources.IFile;
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
 * 新規ダイアグラム作成Wizard
 * 
 * @author nakaG
 * 
 */
public class NewDiagramWizard extends Wizard implements INewWizard {
	/** logging */
	private static Logger logger = LoggerFactory
			.getLogger(NewDiagramWizard.class);

	private IWorkbench workbench;
	private IStructuredSelection selection;
	private NewDiagramCreationPage page;

	/**
	 * コンストラクタ
	 */
	public NewDiagramWizard() {
		super();
		setNeedsProgressMonitor(true);
		setWindowTitle(Messages.CreateNewDiagram);
	}

	@Override
	public boolean performFinish() {
		IFile file = page.createNewFile();
		if (file == null) {
			return false;
		}
		IWorkbenchPage page = workbench.getActiveWorkbenchWindow()
				.getActivePage();
		try {
			IDE.openEditor(page, file, true);
		} catch (PartInitException e) {
			logger.error("open error.", e); //$NON-NLS-1$
			return false;
		}
		return true;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
	 *      org.eclipse.jface.viewers.IStructuredSelection)
	 */
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
		page = new NewDiagramCreationPage(Messages.NewDiagram, selection);
		addPage(page);
		super.addPages();

	}

	private static class NewDiagramCreationPage extends
			WizardNewFileCreationPage {

		public NewDiagramCreationPage(String string,
				IStructuredSelection selection) {
			super(string, selection);
			setFileExtension("tmd"); //$NON-NLS-1$
			setFileName("diagram.tmd"); //$NON-NLS-1$
			setTitle("TMダイアグラム");
			setDescription("新規のTMダイアグラムを作成します。");
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
				Serializer serializer = SerializerFactory.getInstance();
				return serializer.serialize(diagram);
			} catch (SerializationException e) {
				e.printStackTrace();
				return null;
			}
		}
	}
}
