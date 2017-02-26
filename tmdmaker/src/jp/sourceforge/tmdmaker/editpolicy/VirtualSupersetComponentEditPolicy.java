/*
 * Copyright 2009-2017 TMD-Maker Project <http://tmdmaker.osdn.jp/>
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

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.GroupRequest;

import jp.sourceforge.tmdmaker.dialog.ModelEditDialog;
import jp.sourceforge.tmdmaker.model.VirtualSuperset;
import jp.sourceforge.tmdmaker.model.VirtualSupersetType;
import jp.sourceforge.tmdmaker.ui.editor.gef3.commands.ModelDeleteCommand;
import jp.sourceforge.tmdmaker.ui.editor.gef3.commands.VirtualSupersetTypeDeleteCommand;

/**
 * みなしスーパーセットのComponentEditPolicy
 * 
 * @author nakaG
 * 
 */
public class VirtualSupersetComponentEditPolicy extends AbstractEntityModelEditPolicy<VirtualSuperset> {
	
	@Override
	protected ModelEditDialog<VirtualSuperset> getDialog() {
		return null;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
	 */
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		VirtualSupersetType aggregator = getModel().getVirtualSupersetType();

		CompoundCommand ccommand = new CompoundCommand();
		ccommand.add(new VirtualSupersetTypeDeleteCommand(getDiagram(), aggregator));
		ccommand.add(new ModelDeleteCommand(getDiagram(), getModel()));
		return ccommand.unwrap();
	}
}