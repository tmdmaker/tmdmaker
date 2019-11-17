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
package org.tmdmaker.ui;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.tmdmaker.ui.editor.TMDEditor;
import org.tmdmaker.ui.preferences.IPreferenceListener;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.tmdmaker.ui"; //$NON-NLS-1$
	public static final String IMPORTER_PLUGIN_ID = "tmdmaker.importers"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	/** preferences変更リスナー */
	private IPropertyChangeListener[] listeners = {new org.tmdmaker.ui.preferences.appearance.AppearancePreferenceListener(), new org.tmdmaker.ui.preferences.rule.RulePreferenceListener()};

	/**
	 * The constructor
	 */
	public Activator() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

		IPreferenceStore store = plugin.getPreferenceStore();

		for (IPropertyChangeListener listener : listeners) {
			if (listener instanceof IPreferenceListener) {
				((IPreferenceListener) listener).preferenceStart(store);
			}
			store.addPropertyChangeListener(listener);
		}
		update();

	}

	@Override
	public void stop(BundleContext context) throws Exception {
		IPreferenceStore store = plugin.getPreferenceStore();

		for (IPropertyChangeListener listener : listeners) {
			store.removePropertyChangeListener(listener);
		}

		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Log for output exception.
	 * 
	 * @param t The Throwable raised.
	 */
	public static void log(Throwable t) {
		IStatus status = new Status(IStatus.ERROR, PLUGIN_ID, IStatus.ERROR, t.getMessage(), t);
		getDefault().getLog().log(status);
	}

	/**
	 * Log for output message.
	 * 
	 * @param message The message.
	 */
	public static void log(String message) {
		IStatus status = new Status(IStatus.ERROR, PLUGIN_ID, IStatus.ERROR, message, null);
		getDefault().getLog().log(status);
	}

	/**
	 * Show dialog for normal message.
	 * 
	 * @param message The message.
	 */
	public static void showMessageDialog(String message) {
		MessageBox messageBox = new MessageBox(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
				SWT.ICON_INFORMATION | SWT.OK);
		messageBox.setText(Messages.TMDPlugin_InformationTitle);
		messageBox.setMessage(message);
		messageBox.open();
	}

	/**
	 * Show dialog for error message.
	 * 
	 * @param message The message.
	 */
	public static void showErrorDialog(String message) {
		IStatus status = new Status(IStatus.ERROR, PLUGIN_ID, message);

		log(message);

		ErrorDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
				Messages.TMDPlugin_ErrorTitle, message, status);
	}

	/**
	 * Show dialog for exception.
	 * 
	 * @param t The Throwable raised.
	 */
	public static void showErrorDialog(Throwable t) {
		showErrorDialog(Messages.TMDPlugin_ErrorMessage, t);
	}

	/**
	 * Show dialog for message and exception.
	 * 
	 * @param message The message.
	 * @param t       The Throwable raised.
	 */
	public static void showErrorDialog(String message, Throwable t) {
		IStatus status = new Status(IStatus.ERROR, PLUGIN_ID, 0, t.getMessage(), t);

		log(t);

		ErrorDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
				Messages.TMDPlugin_ErrorTitle, message, status);
	}

	/**
	 * イメージを取得する
	 * 
	 * @param path イメージファイルへのパス
	 * @return イメージ
	 */
	public static Image getImage(String path) {
		ImageRegistry images = getDefault().getImageRegistry();
		Image image = images.get(path);
		if (image == null) {
			image = getImageDescriptor(path).createImage();
			images.put(path, image);
		}
		return image;
	}

	/**
	 * 
	 * @param path 画像へのパス
	 * @return ImageDescriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	public void update() {
		for (IWorkbenchWindow w : PlatformUI.getWorkbench().getWorkbenchWindows()) {

			for (IWorkbenchPage page : w.getPages()) {
				for (IEditorReference ref : page.getEditorReferences()) {
					IEditorPart part = ref.getEditor(false);
					if (part instanceof TMDEditor) {
						((TMDEditor) part).updateVisuals();
					}
				}
			}
		}
	}
}
