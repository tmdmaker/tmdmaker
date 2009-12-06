/*
 * Copyright 2009 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

import jp.sourceforge.tmdmaker.generate.attributelist.AttributeListHtmlGenerator;
import jp.sourceforge.tmdmaker.model.Diagram;

import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;

/**
 * アトリビュートリスト出力アクション
 * 
 * @author nakaG
 * 
 */
public class AttributeListSaveAction extends Action {
	private AttributeListHtmlGenerator generator;
	private GraphicalViewer viewer;
	public static final String ID = "AttributeListSaveAction";

	/**
	 * コンストラクタ
	 * 
	 * @param viewer
	 *            ビューワ
	 */
	public AttributeListSaveAction(GraphicalViewer viewer) {
		super();
		this.viewer = viewer;
		this.generator = new AttributeListHtmlGenerator();
		setId(ID);
		setText("アトリビュートリストを出力");
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		DirectoryDialog dialog = new DirectoryDialog(viewer.getControl()
				.getShell(), SWT.SAVE);
		String rootDir = dialog.open();
		if (rootDir != null) {
			try {
				Diagram diagram = (Diagram) viewer.getContents().getModel();
				this.generator.generate(rootDir, diagram);
			} catch (Throwable t) {
				t.printStackTrace();
				throw new RuntimeException(t);
			}
		}
	}
}
