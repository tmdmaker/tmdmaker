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
package org.tmdmaker.ui.editor;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.gef.ui.actions.ActionBarContributor;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.AlignmentRetargetAction;
import org.eclipse.gef.ui.actions.DeleteRetargetAction;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.RedoRetargetAction;
import org.eclipse.gef.ui.actions.UndoRetargetAction;
import org.eclipse.gef.ui.actions.ZoomComboContributionItem;
import org.eclipse.gef.ui.actions.ZoomInRetargetAction;
import org.eclipse.gef.ui.actions.ZoomOutRetargetAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.RetargetAction;

import org.tmdmaker.ui.Activator;
import org.tmdmaker.ui.actions.gef3.AutoSizeSettingAction;

/**
 * TMDiagramエディターのActionBarContributor
 * 
 * @author nakaG
 * 
 */
public class TMDEditorActionBarContributor extends ActionBarContributor {

	/**
	 * コンストラクタ
	 */
	public TMDEditorActionBarContributor() {
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
		RetargetAction gridAction = new RetargetAction(GEFActionConstants.TOGGLE_GRID_VISIBILITY,
				"&Grid", IAction.AS_CHECK_BOX);
		gridAction.setImageDescriptor(Activator.getImageDescriptor("icons/grid.gif")); //$NON-NLS-1$
		addRetargetAction(gridAction);

		RetargetAction rulerAction = new RetargetAction(GEFActionConstants.TOGGLE_RULER_VISIBILITY,
				"&Rulers", IAction.AS_CHECK_BOX);
		rulerAction.setImageDescriptor(Activator.getImageDescriptor("icons/ruler.gif")); //$NON-NLS-1$
		addRetargetAction(rulerAction);

		RetargetAction snapAction = new RetargetAction(GEFActionConstants.TOGGLE_SNAP_TO_GEOMETRY,
				"Snap to Geo&metry", IAction.AS_CHECK_BOX);
		snapAction.setImageDescriptor(Activator.getImageDescriptor("icons/snap.gif")); //$NON-NLS-1$
		addRetargetAction(snapAction);

		RetargetAction autoSizeAction = new RetargetAction(AutoSizeSettingAction.ID, "&AutoSize");
		autoSizeAction.setImageDescriptor(Activator.getImageDescriptor("icons/autosize.gif")); //$NON-NLS-1$
		addRetargetAction(autoSizeAction);

		addRetargetAction(new ZoomInRetargetAction());
		addRetargetAction(new ZoomOutRetargetAction());

		IWorkbenchWindow iww = getPage().getWorkbenchWindow();
		addRetargetAction((RetargetAction) ActionFactory.COPY.create(iww));
		addRetargetAction((RetargetAction) ActionFactory.PASTE.create(iww));
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.ui.actions.ActionBarContributor#declareGlobalActionKeys()
	 */
	@Override
	protected void declareGlobalActionKeys() {
		// do nothing

	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.part.EditorActionBarContributor#contributeToToolBar(org.eclipse.jface.action.IToolBarManager)
	 */
	@Override
	public void contributeToToolBar(IToolBarManager toolBarManager) {
		ActionRegistry registry = getActionRegistry();
		toolBarManager.add(registry.getAction(ActionFactory.DELETE.getId()));
		toolBarManager.add(registry.getAction(ActionFactory.UNDO.getId()));
		toolBarManager.add(registry.getAction(ActionFactory.REDO.getId()));

		toolBarManager.add(new Separator());
		// 水平方向の整列アクションの追加
		toolBarManager.add(registry.getAction(GEFActionConstants.ALIGN_LEFT));
		toolBarManager.add(registry.getAction(GEFActionConstants.ALIGN_CENTER));
		toolBarManager.add(registry.getAction(GEFActionConstants.ALIGN_RIGHT));
		toolBarManager.add(new Separator());
		// 垂直方向の整列アクションの追加
		toolBarManager.add(registry.getAction(GEFActionConstants.ALIGN_TOP));
		toolBarManager.add(registry.getAction(GEFActionConstants.ALIGN_MIDDLE));
		toolBarManager.add(registry.getAction(GEFActionConstants.ALIGN_BOTTOM));

		toolBarManager.add(new Separator());
		toolBarManager.add(registry.getAction(GEFActionConstants.TOGGLE_GRID_VISIBILITY));

		toolBarManager.add(registry.getAction(GEFActionConstants.TOGGLE_RULER_VISIBILITY));

		toolBarManager.add(registry.getAction(GEFActionConstants.TOGGLE_SNAP_TO_GEOMETRY));

		toolBarManager.add(registry.getAction(AutoSizeSettingAction.ID));

		toolBarManager.add(new ZoomComboContributionItem(getPage()));

		toolBarManager.add(getAction(GEFActionConstants.ZOOM_IN));
		toolBarManager.add(getAction(GEFActionConstants.ZOOM_OUT));
		
		toolBarManager.add(getAction(ActionFactory.COPY.getId()));
		toolBarManager.add(getAction(ActionFactory.PASTE.getId()));
	}

}
