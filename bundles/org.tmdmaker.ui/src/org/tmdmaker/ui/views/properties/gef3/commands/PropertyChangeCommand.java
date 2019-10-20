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
package org.tmdmaker.ui.views.properties.gef3.commands;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.eclipse.gef.commands.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * PropertySheet's change command.
 * 
 * @author nakag
 *
 */
public class PropertyChangeCommand extends Command {
	private static Logger logger = LoggerFactory.getLogger(PropertyChangeCommand.class);
	private Object model;
	private String propertyName;
	private Object oldValue;
	private Object newValue;

	public PropertyChangeCommand(Object model, String propertyName, Object oldValue, Object newValue) {
		this.model = model;
		this.propertyName = propertyName;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	@Override
	public void execute() {
		this.doExecute(model, propertyName, newValue);
	}

	@Override
	public void undo() {
		this.doExecute(model, propertyName, oldValue);
	}

	private void doExecute(Object model, String propertyName, Object value) {
		try {
			BeanUtils.copyProperty(model, propertyName, value);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
	}
}
