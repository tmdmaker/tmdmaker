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
package jp.sourceforge.tmdmaker.action;

import jp.sourceforge.tmdmaker.TMDPlugin;
import jp.sourceforge.tmdmaker.generate.Generator;
import jp.sourceforge.tmdmaker.model.Diagram;

import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;

/**
 * 設定されたGeneratorを実行するActoin
 * 
 * @author hiro
 * 
 */
public class GenerateAction extends Action {

	private Generator generator;
	private GraphicalViewer viewer;

	/**
	 * コンストラクタ
	 * 
	 * @param viewer
	 * @param generator
	 */
	public GenerateAction(GraphicalViewer viewer, Generator generator) {
		super();
		this.viewer = viewer;
		this.generator = generator;
		setId(this.generator.getClass().getName());
		setText(this.generator.getGeneratorName());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		Diagram diagram = (Diagram) viewer.getContents().getModel();
		DirectoryDialog dialog = new DirectoryDialog(viewer.getControl()
				.getShell(), SWT.SAVE);
		String rootDir = dialog.open();
		try {
			generator.execute(rootDir, diagram);
		} catch (Throwable t) {
			TMDPlugin.log(t);
		}
	}

}
