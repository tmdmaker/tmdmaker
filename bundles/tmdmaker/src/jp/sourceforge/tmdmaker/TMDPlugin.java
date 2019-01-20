/*
 * Copyright 2009-2019 TMD-Maker Project <https://tmdmaker.osdn.jp/>
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
package jp.sourceforge.tmdmaker;

import java.io.File;

import jp.sourceforge.tmdmaker.ui.preferences.IPreferenceListener;
import jp.sourceforge.tmdmaker.ui.preferences.appearance.AppearancePreferenceListener;
import jp.sourceforge.tmdmaker.ui.preferences.rule.RulePreferenceListener;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
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
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class TMDPlugin extends AbstractUIPlugin {

	/** The plug-in ID */
	public static final String PLUGIN_ID = "tmdmaker"; //$NON-NLS-1$
	public static final String IMPORTER_PLUGIN_ID = "tmdmaker.importers"; //$NON-NLS-1$

	/** The shared instance */
	private static TMDPlugin plugin;

	/** preferences変更リスナー */
	private IPropertyChangeListener[] listeners = {new AppearancePreferenceListener(), new RulePreferenceListener()};

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
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

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		IPreferenceStore store = plugin.getPreferenceStore();

		for (IPropertyChangeListener listener : listeners) {
			store.removePropertyChangeListener(listener);
		}

		super.stop(context);
		// imageRegistryの後片付けはsuper.stop()で実施
		// getImageRegistry().dispose();
		plugin = null;
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static TMDPlugin getDefault() {
		return plugin;
	}

	/**
	 * 
	 * @param path
	 *            画像へのパス
	 * @return ImageDescriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	/**
	 * 例外用のログ出力
	 * 
	 * @param t
	 *            発生した例外
	 */
	public static void log(Throwable t) {
		IStatus status = new Status(IStatus.ERROR, PLUGIN_ID, IStatus.ERROR,
				t.getMessage(), t);
		getDefault().getLog().log(status);
	}

	/**
	 * ログ出力
	 * 
	 * @param message ログメッセージ.
	 */
	public static void log(String message) {
		IStatus status = new Status(IStatus.ERROR, PLUGIN_ID, IStatus.ERROR,
				message, null);
		getDefault().getLog().log(status);
	}

	/**
	 * 情報ダイアログ表示
	 * 
	 * @param message
	 *            表示内容
	 */
	public static void showMessageDialog(String message) {
		MessageBox messageBox = new MessageBox(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell(), SWT.ICON_INFORMATION
				| SWT.OK);
		messageBox.setText(Messages.TMDPlugin_InformationTitle);
		messageBox.setMessage(message);
		messageBox.open();
	}

	/**
	 * エラーダイアログ表示
	 * 
	 * @param t
	 *            表示対象の例外
	 */
	public static void showErrorDialog(Throwable t) {
		showErrorDialog(Messages.TMDPlugin_ErrorMessage, t);
	}

	/**
	 * エラーダイアログ表示
	 * 
	 * @param message
	 *            ダイアログに表示するメッセージ
	 * @param t
	 *            表示対象の例外
	 */
	public static void showErrorDialog(String message, Throwable t) {
		IStatus status = new Status(IStatus.ERROR, PLUGIN_ID, 0,
				t.getMessage(), t);

		log(t);

		ErrorDialog.openError(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell(), Messages.TMDPlugin_ErrorTitle, message, status);
	}

	/**
	 * イメージを取得する
	 * 
	 * @param path
	 *            イメージファイルへのパス
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

	public void update() {
		for (IWorkbenchWindow w : PlatformUI.getWorkbench()
				.getWorkbenchWindows()) {

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

	/**
	 * 生成したリソース(ファイル)をワークスペース内に表示させる
	 * 
	 * @param path
	 *            リソースへのパス
	 * @throws Exception
	 *             refresh時の例外
	 */
	public static void refreshGenerateResource(String path) throws Exception {
		IFile[] files = ResourcesPlugin.getWorkspace().getRoot()
				.findFilesForLocationURI(new File(path).toURI());
		if (files != null) {
			for (IFile f : files) {
				f.refreshLocal(IResource.DEPTH_INFINITE, null);
			}
		}
	}

	/**
	 * 生成したリソース群（ディレクトリ）をワークスペース内に表示させる
	 * 
	 * @param path
	 *            リソースへのパス
	 * @throws Exception
	 *             refresh時の例外
	 */
	public static void refreshGenerateResources(String path) throws Exception {
		IContainer[] container = ResourcesPlugin.getWorkspace().getRoot()
				.findContainersForLocationURI(new File(path).toURI());
		if (container != null) {
			for (IContainer c : container) {
				c.refreshLocal(IResource.DEPTH_INFINITE, null);
			}
		}
	}

	/**
	 * TMDエディターで編集しているファイルを取得する
	 * 
	 * @param part
	 *            エディタ
	 * @return IFile
	 */
	public static IFile getEditFile(IWorkbenchPart part) {
		return ((IFileEditorInput) ((IEditorPart) part).getEditorInput())
				.getFile();
	}
}
