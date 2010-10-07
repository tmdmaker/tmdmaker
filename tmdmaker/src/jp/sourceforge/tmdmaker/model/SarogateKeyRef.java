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
 * @author nakaG
 *
 */
@SuppressWarnings("serial")
public class SarogateKeyRef extends SarogateKey {
	private SarogateKey original;

	
	/**
	 * @param original
	 */
	public SarogateKeyRef(SarogateKey original) {
		this.original = original;
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.Attribute#getImplementName()
	 */
	@Override
	public String getImplementName() {
		String returnName = super.getImplementName();
		if (returnName == null) {
			returnName = original.getImplementName();
		}
		return returnName;
	}

	/**
	 * @return the original
	 */
	public SarogateKey getOriginal() {
		return original;
	}

	/**
	 * @param original the original to set
	 */
	public void setOriginal(SarogateKey original) {
		this.original = original;
	}
}
