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
import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.Diagram;
import org.tmdmaker.core.model.MultivalueAndSuperset;

/**
 * @author ny@cosmichorror.org
 *
 */
public class DiagramTreeEditPart extends AbstractTreeEditPart implements PropertyChangeListener {

	/**
	 * コンストラクタ
	 * 
	 * @param diagram
	 */
	public DiagramTreeEditPart(Diagram diagram) {
		super();
		setModel(diagram);
	}

	@Override
	public Diagram getModel() {
		return (Diagram) super.getModel();
	}

	// 子要素があるときは、getModelChildren()で子要素の一覧を返す。無いときは空のリストを返す。
	@Override
	protected List<AbstractEntityModel> getModelChildren() {
		return getModel().query().excludeClass(MultivalueAndSuperset.class).listEntityModel();
	}

	@Override
	protected String getText() {
		return getModel().getName();
	}

	@Override
	protected Image getImage() {
		return super.getImage(); // TODO アイコン
	}

	@Override
	public void activate() {
		super.activate();
		getModel().addPropertyChangeListener(this);
	}

	@Override
	public void deactivate() {
		getModel().removePropertyChangeListener(this);
		super.deactivate();
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getPropertyName().equals(Diagram.PROPERTY_CHILDREN)) {
			refreshChildren();
		}
	}
}
