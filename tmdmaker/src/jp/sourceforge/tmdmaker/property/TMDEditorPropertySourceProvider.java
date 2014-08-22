/*
 * Copyright 2009-2013 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

package jp.sourceforge.tmdmaker.property;

import jp.sourceforge.tmdmaker.TMDEditor;
import jp.sourceforge.tmdmaker.editpart.AbstractModelEditPart;
import jp.sourceforge.tmdmaker.editpart.AttributeEditPart;
import jp.sourceforge.tmdmaker.editpart.DiagramEditPart;
import jp.sourceforge.tmdmaker.editpart.EntityEditPart;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.IdentifierRef;
import jp.sourceforge.tmdmaker.model.KeyModel;
import jp.sourceforge.tmdmaker.treeeditpart.AbstractEntityModelTreeEditPart;
import jp.sourceforge.tmdmaker.treeeditpart.AttributeTreeEditPart;
import jp.sourceforge.tmdmaker.treeeditpart.EntityTreeEditPart;
import jp.sourceforge.tmdmaker.treeeditpart.IdentifierRefTreeEditPart;
import jp.sourceforge.tmdmaker.treeeditpart.IdentifierTreeEditPart;
import jp.sourceforge.tmdmaker.treeeditpart.KeyModelTreeEditPart;

import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;

public class TMDEditorPropertySourceProvider implements IPropertySourceProvider {
	
	TMDEditor editor;
	
	public TMDEditorPropertySourceProvider(TMDEditor editor){	
		this.editor = editor;
	}

	@Override
	public IPropertySource getPropertySource(Object object) {
		if (object instanceof DiagramEditPart) {
			Diagram diagram = (Diagram) ((DiagramEditPart) object).getModel();
			return new DiagramPropertySource(this.editor, diagram);
		}
		if (object instanceof EntityEditPart) {
			Entity entity = (Entity) ((EntityEditPart) object).getModel();
			return new EntityPropertySource(this.editor, entity);
		}
		if (object instanceof EntityTreeEditPart) {
			Entity entity = (Entity) ((EntityTreeEditPart) object).getModel();
			return new EntityPropertySource(this.editor, entity);
		}
		if (object instanceof AbstractModelEditPart) {
			AbstractEntityModel model = (AbstractEntityModel) ((AbstractModelEditPart<?>) object).getModel();
			return new AbstractEntityModelPropertySource(this.editor, model);
		}
		if (object instanceof AbstractEntityModelTreeEditPart) {
			AbstractEntityModel model = (AbstractEntityModel) ((AbstractEntityModelTreeEditPart<AbstractEntityModel>) object).getModel();
			return new AbstractEntityModelPropertySource(this.editor, model);
		}
		if (object instanceof IdentifierRefTreeEditPart) {
			IdentifierRef attribute = (IdentifierRef) ((IdentifierRefTreeEditPart) object).getModel();
			return new IdentifierPropertySource(this.editor, attribute);
		}
		if (object instanceof IdentifierTreeEditPart) {
			Identifier attribute = (Identifier) ((IdentifierTreeEditPart) object).getModel();
			return new IdentifierPropertySource(this.editor, attribute);
		}
		if (object instanceof AttributeEditPart) {
			Attribute attribute = (Attribute) ((AttributeEditPart) object).getModel();
			return new AttributePropertySource(this.editor, attribute);
		}
		if (object instanceof AttributeTreeEditPart) {
			Attribute attribute = (Attribute) ((AttributeTreeEditPart) object).getModel();
			return new AttributePropertySource(this.editor, attribute);
		}
		if (object instanceof KeyModelTreeEditPart) {
			KeyModel keymodel = (KeyModel) ((KeyModelTreeEditPart) object).getModel();
			return new KeyModelPropertySource(this.editor, keymodel);
		}
		return null;
	}
}
