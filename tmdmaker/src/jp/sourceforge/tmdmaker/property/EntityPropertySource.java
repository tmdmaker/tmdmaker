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
package jp.sourceforge.tmdmaker.property;

import jp.sourceforge.tmdmaker.Messages;
import jp.sourceforge.tmdmaker.TMDEditor;
import jp.sourceforge.tmdmaker.model.Entity;

/**
 * Entity property source.
 *
 */
public class EntityPropertySource extends AbstractEntityModelPropertySource {

	private Entity model;

	public EntityPropertySource(TMDEditor editor, Entity model) {
		super(editor, model);
		this.model = model;
	}

	@Override
	public Object getEditableValue() {
		return this.model;
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id.equals("Name")) { //$NON-NLS-1$
			return model.getName() != null ? model.getName() : ""; //$NON-NLS-1$
		}
		if (id.equals("ImplementName")) { //$NON-NLS-1$
			return model.getImplementName() != null ? model.getImplementName() : ""; //$NON-NLS-1$
		}
		if (id.equals("Implement")) { //$NON-NLS-1$
			return model.isNotImplement() ? Messages.EntityPropertySource_0
					: Messages.EntityPropertySource_1;
		}
		if (id.equals("EntityType")) { //$NON-NLS-1$
			return model.getEntityType().getTypeName();
		}
		return null;
	}
}
