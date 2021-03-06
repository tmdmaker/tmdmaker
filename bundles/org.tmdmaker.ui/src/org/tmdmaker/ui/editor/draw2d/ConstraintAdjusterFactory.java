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
package org.tmdmaker.ui.editor.draw2d;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tmdmaker.core.model.AbstractConnectionModel;
import org.tmdmaker.core.model.AbstractRelationship;
import org.tmdmaker.core.model.Event2EventRelationship;
import org.tmdmaker.core.model.RecursiveRelationship;
import org.tmdmaker.core.model.Resource2ResourceRelationship;
import org.tmdmaker.ui.editor.draw2d.adjuster.CombinationTableAdjuster;
import org.tmdmaker.ui.editor.draw2d.adjuster.MappingListAdjuster;
import org.tmdmaker.ui.editor.draw2d.adjuster.NullConstraintAdjuster;
import org.tmdmaker.ui.editor.draw2d.adjuster.RecursiveTableAdjuster;

/**
 * ConstraintAdjusterのFactoryクラス.
 * 
 * @author nakag
 *
 */
public class ConstraintAdjusterFactory {
	/** logging */
	protected static Logger logger = LoggerFactory.getLogger(ConstraintAdjusterFactory.class);

	private static Map<Class<? extends AbstractRelationship>, Class<? extends ConstraintAdjuster>> adjusterMap = new HashMap<Class<? extends AbstractRelationship>, Class<? extends ConstraintAdjuster>>() {
		private static final long serialVersionUID = 1L;

		{
			put(RecursiveRelationship.class, RecursiveTableAdjuster.class);
			put(Event2EventRelationship.class, MappingListAdjuster.class);
			put(Resource2ResourceRelationship.class, CombinationTableAdjuster.class);
		}
	};

	public static ConstraintAdjuster getAdjuster(AbstractConnectionModel relationship) {
		Class<? extends ConstraintAdjuster> adjuster = adjusterMap.get(relationship.getClass());
		if (adjuster == null) {
			return new NullConstraintAdjuster();
		}
		try {
			return adjuster.getConstructor(relationship.getClass()).newInstance(relationship);
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
		return new NullConstraintAdjuster();
	}
}
