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
package jp.sourceforge.tmdmaker.editpolicy;

import java.util.ArrayList;
import java.util.List;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.rule.ImplementRule;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.GroupRequest;

/**
 * @author nakaG
 *
 */
public class DerivationEntityComponentEditPolicy extends EntityComponentEditPolicy {

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpolicy.EntityComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
	 */
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		CompoundCommand ccommand = new CompoundCommand();
		AbstractEntityModel model =(AbstractEntityModel)getHost().getModel();
		if (model.isNotImplement()) {
			AbstractEntityModel original = ImplementRule.findOriginal(model);
			ccommand.add(new ImplementDerivationModelsDeleteCommand(model, original));
		}
		ccommand.add(super.createDeleteCommand(deleteRequest));
		return ccommand;
	}
	class ImplementDerivationModelsDeleteCommand extends Command {
		protected List<AbstractEntityModel> oldImplementDerivationModels;
		protected List<AbstractEntityModel> newImplementDerivationModels;
		private AbstractEntityModel original;

		
		/**
		 * @param model
		 * @param original
		 */
		public ImplementDerivationModelsDeleteCommand(
				AbstractEntityModel model,
				AbstractEntityModel original) {
			super();
			this.oldImplementDerivationModels = original.getImplementDerivationModels();
			this.newImplementDerivationModels = new ArrayList<AbstractEntityModel>(oldImplementDerivationModels);
			this.newImplementDerivationModels.remove(model);
			this.original = original;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		@Override
		public void execute() {
			original.setImplementDerivationModels(newImplementDerivationModels);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		@Override
		public void undo() {
			original.setImplementDerivationModels(oldImplementDerivationModels);
		}
	}

}
