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

/**
 * みなしスーパーセットの同一(=)/相違マーク(×)
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class VirtualSupersetAggregator extends ConnectableElement {
	public static final String PROPERTY_SUPERSET_TYPE = "_property_superset_type";
	
	/** アトリビュートに適用するか */
	private boolean applyAttribute = false;

	/**
	 * @return the applyAttribute
	 */
	public boolean isApplyAttribute() {
		return applyAttribute;
	}

	/**
	 * @param applyAttribute the applyAttribute to set
	 */
	public void setApplyAttribute(boolean applyAttribute) {
		boolean oldValue = this.applyAttribute;
		this.applyAttribute = applyAttribute;
		firePropertyChange(PROPERTY_SUPERSET_TYPE, oldValue, this.applyAttribute);
	}	
}
