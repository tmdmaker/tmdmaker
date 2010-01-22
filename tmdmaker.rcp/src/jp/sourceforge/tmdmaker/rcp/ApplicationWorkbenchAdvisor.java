/*
 * Copyright 2009-2010 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package jp.sourceforge.tmdmaker.rcp;

import java.net.URL;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.ide.IDE;
import org.osgi.framework.Bundle;

/**
 * 
 * @author nakaG
 * 
 */
public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {

	private static final String PERSPECTIVE_ID = "tmdmaker.rcp.perspective";

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.application.WorkbenchAdvisor#initialize(org.eclipse.ui.application.IWorkbenchConfigurer)
	 */
	@Override
	public void initialize(IWorkbenchConfigurer configurer) {
		super.initialize(configurer);
		initializeWorkbenchImages(configurer);
		configurer.setSaveAndRestore(true);
	}

	private void initializeWorkbenchImages(IWorkbenchConfigurer configurer) {
		final String ICONS_PATH = "icons/full/";
		final String PATH_OBJECT = ICONS_PATH + "obj16/";
		final String IDE_WORKBENCH = "org.eclipse.ui.ide"; //$NON-NLS-1$

		Bundle ideBundle = Platform.getBundle(IDE_WORKBENCH);
		declareWorkbenchImage(configurer, ideBundle,
				IDE.SharedImages.IMG_OBJ_PROJECT, PATH_OBJECT + "prj_obj.gif",
				true);
		declareWorkbenchImage(configurer, ideBundle,
				IDE.SharedImages.IMG_OBJ_PROJECT_CLOSED, PATH_OBJECT
						+ "cprj_obj.gif", true);
	}

	private void declareWorkbenchImage(IWorkbenchConfigurer configurer_p,
			Bundle ideBundle, String symbolicName, String path, boolean shared) {
		URL url = ideBundle.getEntry(path);
		ImageDescriptor desc = ImageDescriptor.createFromURL(url);
		configurer_p.declareImage(symbolicName, desc, shared);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.application.WorkbenchAdvisor#preStartup()
	 */
	@Override
	public void preStartup() {
		super.preStartup();
		IDE.registerAdapters();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.application.WorkbenchAdvisor#createWorkbenchWindowAdvisor(org.eclipse.ui.application.IWorkbenchWindowConfigurer)
	 */
	public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(
			IWorkbenchWindowConfigurer configurer) {
		return new ApplicationWorkbenchWindowAdvisor(configurer);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.application.WorkbenchAdvisor#getInitialWindowPerspectiveId()
	 */
	public String getInitialWindowPerspectiveId() {
		return PERSPECTIVE_ID;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.application.WorkbenchAdvisor#getDefaultPageInput()
	 */
	@Override
	public IAdaptable getDefaultPageInput() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		return workspace.getRoot();
	}

}
