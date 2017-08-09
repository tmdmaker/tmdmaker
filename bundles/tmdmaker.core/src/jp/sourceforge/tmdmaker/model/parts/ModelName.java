/*
 * Copyright 2009-2017 TMD-Maker Project <http://tmdmaker.osdn.jp/>
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
package jp.sourceforge.tmdmaker.model.parts;

/**
 * モデルの名前を表現するオブジェクト.
 *
 * @author nakag
 *
 */
public class ModelName {
	private final String name;

	public ModelName() {
		this.name = "";
	}

	public ModelName(String name) {
		this.name = name;
	}

	public String getValue() {
		return name;
	}

	public boolean isEmpty() {
		return this.name.length() == 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ModelName) {
			ModelName other = (ModelName) obj;
			return this.name.equals(other.name);
		}
		return false;
	}

}
