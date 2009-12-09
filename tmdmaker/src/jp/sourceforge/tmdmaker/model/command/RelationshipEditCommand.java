/*
 * Copyright 2009 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package jp.sourceforge.tmdmaker.model.command;

import jp.sourceforge.tmdmaker.model.AbstractRelationship;
import jp.sourceforge.tmdmaker.model.Cardinality;

import org.eclipse.gef.commands.Command;

/**
 * リレーションシップ編集Command
 * 
 * @author nakaG
 * 
 */
public class RelationshipEditCommand extends Command {
	private AbstractRelationship model;
	private Cardinality sourceCardinality = Cardinality.ONE;
	private Cardinality targetCardinality = Cardinality.ONE;
	private boolean sourceNoInstance = false;
	private boolean targetNoInstance = false;

	private Cardinality oldSourceCardinality = null;
	private Cardinality oldTargetCardinality = null;
	private boolean oldSourceNoInstance = false;
	private boolean oldTargetNoInstance = false;

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		this.model.setSourceNoInstance(this.sourceNoInstance);
		this.model.setSourceCardinality(this.sourceCardinality);
		this.model.setTargetNoInstance(this.targetNoInstance);
		this.model.setTargetCardinality(this.targetCardinality);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		this.model.setSourceNoInstance(this.oldSourceNoInstance);
		this.model.setSourceCardinality(this.oldSourceCardinality);
		this.model.setTargetNoInstance(this.oldTargetNoInstance);
		this.model.setTargetCardinality(this.oldTargetCardinality);
	}

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(AbstractRelationship model) {
		this.model = model;
		this.oldSourceCardinality = model.getSourceCardinality();
		this.oldSourceNoInstance = model.isSourceNoInstance();
		this.oldTargetCardinality = model.getTargetCardinality();
		this.oldTargetNoInstance = model.isTargetNoInstance();
	}

	/**
	 * @param sourceCardinality
	 *            the sourceCardinality to set
	 */
	public void setSourceCardinality(Cardinality sourceCardinality) {
		this.sourceCardinality = sourceCardinality;
	}

	/**
	 * @param targetCardinality
	 *            the targetCardinality to set
	 */
	public void setTargetCardinality(Cardinality targetCardinality) {
		this.targetCardinality = targetCardinality;
	}

	/**
	 * @param sourceNoInstance
	 *            the sourceNoInstance to set
	 */
	public void setSourceNoInstance(boolean sourceNoInstance) {
		this.sourceNoInstance = sourceNoInstance;
	}

	/**
	 * @param targetNoInstance
	 *            the targetNoInstance to set
	 */
	public void setTargetNoInstance(boolean targetNoInstance) {
		this.targetNoInstance = targetNoInstance;
	}
}
