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

package jp.sourceforge.tmdmaker.ui.actions;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.AbstractEditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import jp.sourceforge.tmdmaker.Messages;

public class OpenDialogAction extends SelectionAction {
	public static final String ID = "_OPEN"; //$NON-NLS-1$

	public OpenDialogAction(IWorkbenchPart part) {
		super(part);
		setLazyEnablementCalculation(false);
		setText(Messages.Open);
		setId(ID);
	}

	@Override
	protected boolean calculateEnabled() {
		if (getSelectedObjects().isEmpty()) return false;
		
		return getSelectedObjects().get(0) instanceof EditPart;
	}
	
	@Override
	public void run()
	{
		EditPart part = (EditPart)getSelectedObjects().get(0);
		
		if (!(part instanceof AbstractEditPart)) return;
		
		((AbstractEditPart)part).performRequest(new Request(RequestConstants.REQ_OPEN));
	}
}
