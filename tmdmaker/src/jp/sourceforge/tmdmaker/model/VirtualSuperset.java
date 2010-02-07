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
package jp.sourceforge.tmdmaker.model;

import java.util.ArrayList;
import java.util.List;

/**
 * みなしスーパーセット
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class VirtualSuperset extends AbstractEntityModel {
	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#createReusedIdentifier()
	 */
	@Override
	public ReusedIdentifier createReusedIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#isDeletable()
	 */
	@Override
	public boolean isDeletable() {
		return true;
	}
	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#isEntityTypeEditable()
	 */
	@Override
	public boolean isEntityTypeEditable() {
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * 
	 * @return
	 */
	public List<AbstractEntityModel> getVirtualSubsetList() {
		List<AbstractEntityModel> results = new ArrayList<AbstractEntityModel>();
		for (AbstractConnectionModel con : getVirtualSupersetAggregator()
				.getModelSourceConnections()) {
			results.add((AbstractEntityModel) con.getTarget());
		}
		return results;
	}
	/**
	 * 
	 * @return
	 */
	public List<AbstractConnectionModel> getVirtualSubsetRelationshipList() {
		List<AbstractConnectionModel> results = new ArrayList<AbstractConnectionModel>();
		for (AbstractConnectionModel con : getVirtualSupersetAggregator()
				.getModelSourceConnections()) {
			results.add(con);
		}
		return results;
	}
	/**
	 * 
	 * @return
	 */
	public VirtualSupersetAggregator getVirtualSupersetAggregator() {
		return (VirtualSupersetAggregator) getModelSourceConnections().get(0)
				.getTarget();
	}
	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#getCopy()
	 */
	@Override
	public VirtualSuperset getCopy() {
		VirtualSuperset copy = new VirtualSuperset();
		copyTo(copy);
		return copy;
	}
	
}
