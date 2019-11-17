/*
 * Copyright 2009-2013 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package org.tmdmaker.rcp;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

/**
 * パースペクティブ
 * 
 * @author nakaG
 * 
 */
public class Perspective implements IPerspectiveFactory {

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IPerspectiveFactory#createInitialLayout(org.eclipse.ui.IPageLayout)
	 */
	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(true);

		layout.addView(IPageLayout.ID_PROJECT_EXPLORER, IPageLayout.LEFT, 0.3f,
				editorArea);
		layout.addView(IPageLayout.ID_OUTLINE, IPageLayout.RIGHT, 0.7f,
				editorArea);
		layout.addView(IPageLayout.ID_PROP_SHEET, IPageLayout.BOTTOM, 0.7f,
				editorArea);

	}
}
