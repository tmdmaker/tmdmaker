/*
 * Copyright 2009-2012 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package jp.sourceforge.tmdmaker.handler;

import java.util.Iterator;

import jp.sourceforge.tmdmaker.generate.Generator;
import jp.sourceforge.tmdmaker.generate.GeneratorUtils;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.persistence.SerializerFactory;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * ダイアグラムから別ファイルを生成するためのハンドラの基底クラス
 * 
 * @author nakaG
 * 
 */
public abstract class AbstractGeneratorHandler extends AbstractHandler {
	private Generator generator;

	/**
	 * コンストラクタ
	 * 
	 * @param generator
	 *            generator
	 */
	public AbstractGeneratorHandler(Generator generator) {
		super();
		this.generator = generator;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see AbstractGeneratorHandler#execute(ExecutionEvent)
	 * 
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection != null & selection instanceof IStructuredSelection) {
			IFile file = getSelectionFile((IStructuredSelection) selection);

			try {
				Diagram diagram = SerializerFactory.getInstance().deserialize(
						file.getContents());
				String path = getAbsolutePath(file);
				generator.execute(path, diagram.findEntityModel());
				GeneratorUtils.refreshGenerateResources(path);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	private IFile getSelectionFile(IStructuredSelection selection) {
		for (Iterator it = selection.iterator(); it.hasNext();) {
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

}