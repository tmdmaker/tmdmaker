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

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.gef.ui.actions.ActionBarContributor;
import org.eclipse.gef.ui.actions.AlignmentRetargetAction;
import org.eclipse.gef.ui.actions.DeleteRetargetAction;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.RedoRetargetAction;
import org.eclipse.gef.ui.actions.UndoRetargetAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.actions.ActionFactory;

/**
 * TMDiagramエディターのActionBarContributor
 * 
 * @author nakaG
 * 
 */
public class TMDEditorContributor extends ActionBarContributor {

	/**
	 * コンストラクタ
	 */
	public TMDEditorContributor() {
		super();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.ui.actions.ActionBarContributor#buildActions()
	 */
	@Override
	protected void buildActions() {
		addRetargetAction(new UndoRetargetAction());
		addRetargetAction(new RedoRetargetAction());
		addRetargetAction(new DeleteRetargetAction());

		addRetargetAction(new AlignmentRetargetAction(PositionConstants.LEFT));
		addRetargetAction(new AlignmentRetargetAction(PositionConstants.CENTER));
		addRetargetAction(new AlignmentRetargetAction(PositionConstants.RIGHT));
		addRetargetAction(new AlignmentRetargetAction(PositionConstants.TOP));
		addRetargetAction(new AlignmentRetargetAction(PositionConstants.MIDDLE));
		addRetargetAction(new AlignmentRetargetAction(PositionConstants.BOTTOM));
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.ui.actions.ActionBarContributor#declareGlobalActionKeys()
	 */
	@Override
	protected void declareGlobalActionKeys() {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.part.EditorActionBarContributor#contributeToToolBar(org.eclipse.jface.action.IToolBarManager)
	 */
	@Override
	public void contributeToToolBar(IToolBarManager toolBarManager) {
		toolBarManager.add(getActionRegistry().getAction(
				ActionFactory.DELETE.getId()));
		toolBarManager.add(getActionRegistry().getAction(
				ActionFactory.UNDO.getId()));
		toolBarManager.add(getActionRegistry().getAction(
				ActionFactory.REDO.getId()));

		toolBarManager.add(new Separator());
		// 水平方向の整列アクションの追加
		toolBarManager.add(getActionRegistry().getAction(
				GEFActionConstants.ALIGN_LEFT));
		toolBarManager.add(getActionRegistry().getAction(
				GEFActionConstants.ALIGN_CENTER));
		toolBarManager.add(getActionRegistry().getAction(
				GEFActionConstants.ALIGN_RIGHT));
		toolBarManager.add(new Separator());
		// 垂直方向の整列アクションの追加
		toolBarManager.add(getActionRegistry().getAction(
				GEFActionConstants.ALIGN_TOP));
		toolBarManager.add(getActionRegistry().getAction(
				GEFActionConstants.ALIGN_MIDDLE));
		toolBarManager.add(getActionRegistry().getAction(
				GEFActionConstants.ALIGN_BOTTOM));

	}
}
