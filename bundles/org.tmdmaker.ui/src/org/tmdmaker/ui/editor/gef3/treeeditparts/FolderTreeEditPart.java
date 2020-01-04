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
package org.tmdmaker.ui.editor.gef3.treeeditparts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.swt.graphics.Image;
import org.slf4j.LoggerFactory;
import org.tmdmaker.ui.Activator;

public class FolderTreeEditPart<T> extends AbstractTreeEditPart implements PropertyChangeListener {
	
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(FolderTreeEditPart.class);
	
	private String title;
	
	public FolderTreeEditPart(String text){
	    this.title = text;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getModel() {
		return (List<T>) super.getModel();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<T> getModelChildren() {
		return (List<T>)super.getModel();
	}
	
	@Override
	protected String getText() {
		return title;
	}
	
	@Override
	protected Image getImage() {
		return Activator.getImage("icons/outline/folder.png"); //$NON-NLS-1$
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		logger.debug("{}.{}", getClass(), evt.getPropertyName());
	}
}
