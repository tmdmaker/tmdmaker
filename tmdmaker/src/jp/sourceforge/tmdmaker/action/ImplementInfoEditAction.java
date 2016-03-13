/*
 * Copyright 2009-2016 TMD-Maker Project <http://tmdmaker.osdn.jp/>
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

import jp.sourceforge.tmdmaker.Messages;
import jp.sourceforge.tmdmaker.dialog.ImplementInfoEditDialog;
import jp.sourceforge.tmdmaker.dialog.model.EditImplementAttribute;
import jp.sourceforge.tmdmaker.dialog.model.EditSarogateKey;
import jp.sourceforge.tmdmaker.model.IAttribute;
import jp.sourceforge.tmdmaker.model.SarogateKey;
import jp.sourceforge.tmdmaker.ui.command.AttributeEditCommand;
import jp.sourceforge.tmdmaker.ui.command.ModelEditCommand;

import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.ui.IWorkbenchPart;

/**
 * 実装情報編集Action
 * 
 * @author nakaG
 * 
 */
public class ImplementInfoEditAction extends AbstractEntitySelectionAction {
	public static final String ID = "ImplementInfoEditAction"; //$NON-NLS-1$
	/**
	 * コンストラクタ
	 * @param part エディター
	 */
	public ImplementInfoEditAction(IWorkbenchPart part) {
		super(part);
		setText(Messages.ImplementInfoEditAction_0);
		setId(ID);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		ImplementInfoEditDialog dialog = new ImplementInfoEditDialog(getPart()
				.getViewer().getControl().getShell(), getModel());
		if (dialog.open() == Dialog.OK) {

			CompoundCommand ccommand = new CompoundCommand();

			ccommand.add(new ModelEditCommand(getModel(), dialog
					.getEditedValueEntity()));
//			for (EditImplementAttribute ei : dialog
//					.getEditedValueIdentifieres()) {
//				Identifier newIdentifier = new Identifier();
//				Identifier original = (Identifier) ei.getOriginalAttribute();
//				ei.copyTo(newIdentifier);
//				ccommand.add(new AttributeEditCommand(original, newIdentifier,
//						ei.getContainerModel()));
//			}

			for (EditImplementAttribute ea : dialog.getEditedValueAttributes()) {
				IAttribute original = ea.getOriginalAttribute();
				IAttribute newAttribute = original.getCopy();
				ea.copyTo(newAttribute);
				ccommand.add(new AttributeEditCommand(original, newAttribute,
						ea.getContainerModel()));
			}
			SarogateKey newSarogateKey = new SarogateKey();
			EditSarogateKey edited = dialog.getEditedSarogateKey();
			edited.copyTo(newSarogateKey);
			SarogateKey original = (SarogateKey) edited.getOriginalAttribute();
			ccommand.add(new AttributeEditCommand(original, newSarogateKey, edited.getContainerModel()));
			execute(ccommand);
		}
	}

}
