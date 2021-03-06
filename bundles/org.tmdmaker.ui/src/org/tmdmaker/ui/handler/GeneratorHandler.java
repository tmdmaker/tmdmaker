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
package org.tmdmaker.ui.handler;

import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.tmdmaker.core.model.Diagram;
import org.tmdmaker.extension.SerializerFactory;
import org.tmdmaker.model.generate.Generator;
import org.tmdmaker.ui.Activator;
import org.tmdmaker.ui.editor.workspace.Workspace;

/**
 * ダイアグラムから別ファイルを生成するためのハンドラクラス
 * 
 * @author nakaG
 * 
 */
public abstract class GeneratorHandler extends AbstractHandler {
	private Generator generator;

	/**
	 * コンストラクタ
	 * 
	 * @param generator generator
	 */
	public GeneratorHandler(Generator generator) {
		super();
		this.generator = generator;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see GeneratorHandler#execute(ExecutionEvent)
	 * 
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof IStructuredSelection) {
			IFile file = getSelectionFile((IStructuredSelection) selection);
			if (file == null) {
				return null;
			}
			try {
				Diagram diagram = SerializerFactory.getInstance().deserialize(file.getContents());
				String path = getAbsolutePath(file);
				generator.execute(path, diagram.query().listEntityModel());
				new Workspace().refreshGenerateResources(path);
			} catch (CoreException e) {
				Activator.showErrorDialog(e);
			} catch (Exception e) {
				Activator.showErrorDialog(e);
			}
		}
		return null;
	}

	private IFile getSelectionFile(IStructuredSelection selection) {
		if (selection == null) {
			return null;
		}
		for (Iterator<?> it = selection.iterator(); it.hasNext();) {
			Object obj = it.next();
			if (obj instanceof IFile) {
				return (IFile) obj;
			}
		}
		return null;
	}

	private String getAbsolutePath(IFile file) {
		return file.getProject().getLocation().toString();
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}