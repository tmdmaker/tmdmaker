/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org/>
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
package org.tmdmaker.ui.views.properties.gef3;

import org.apache.commons.beanutils.BeanUtils;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class AbstractPropertySource implements IPropertySource {

	private CommandStack commandStack;

	protected Logger logger;

	public AbstractPropertySource(CommandStack commandStack) {
		this.commandStack = commandStack;
		this.logger = LoggerFactory.getLogger(getClass());
	}

	@Override
	public void resetPropertyValue(Object paramObject) {
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		Command command = createSetPropertyCommand(id, value);
		if (command != null) {
			this.getCommandStack().execute(command);
		}
	}

	protected abstract Command createSetPropertyCommand(Object id, Object value);

	@Override
	public boolean isPropertySet(Object id) {
		for (IPropertyDescriptor descriptor : getPropertyDescriptors()) {
			if (descriptor.equals(id)) {
				return true;
			}
		}
		return false;
	}

	protected String canonicalize(String property) {
		return property != null ? property : "";
	}

	protected CommandStack getCommandStack() {
		return this.commandStack;
	}

	protected Object getPropertyValue(Object model, String propertyName) {
		try {
			return BeanUtils.getProperty(model, propertyName);
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
		return "";
	}
}
