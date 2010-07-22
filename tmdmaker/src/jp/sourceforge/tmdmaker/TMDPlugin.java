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
package jp.sourceforge.tmdmaker;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class TMDPlugin extends AbstractUIPlugin {

	/** The plug-in ID */
	public static final String PLUGIN_ID = "tmdmaker";

	/** The shared instance */
	private static TMDPlugin plugin;

	/**
	 * The constructor
	 */
	public TMDPlugin() {
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
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
		return AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	/**
	 * 例外用のログ出力
	 * 
	 * @param t
	 *            発生した例外
	 */
	public static void log(Throwable t) {
		IStatus status = new Status(IStatus.ERROR, PLUGIN_ID, IStatus.ERROR, t
				.getMessage(), t);
		getDefault().getLog().log(status);
		t.printStackTrace();
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
		messageBox.setText("情報");
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
		showErrorDialog("エラーが発生しました", t);
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
				.getActiveWorkbenchWindow().getShell(), "エラー", message, status);
	}

}
