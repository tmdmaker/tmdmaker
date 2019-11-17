/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org/>
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
package org.tmdmaker.ui.editor.workspace;

import java.io.File;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPart;

/**
 * Workspace operations.
 * 
 * @author nakag
 *
 */
public class Workspace {

	public IFile getEditFile(IWorkbenchPart part) {
		return ((IFileEditorInput) ((IEditorPart) part).getEditorInput()).getFile();
	}

	public void refreshGenerateResources(String path) throws Exception {
		IContainer[] container = ResourcesPlugin.getWorkspace().getRoot()
				.findContainersForLocationURI(new File(path).toURI());
		if (container != null) {
			for (IContainer c : container) {
				c.refreshLocal(IResource.DEPTH_INFINITE, null);
			}
		}
	}

	public void refreshGenerateResource(String path) throws Exception {
		IFile[] files = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(new File(path).toURI());
		if (files != null) {
			for (IFile f : files) {
				f.refreshLocal(IResource.DEPTH_INFINITE, null);
			}
		}
	}
}
