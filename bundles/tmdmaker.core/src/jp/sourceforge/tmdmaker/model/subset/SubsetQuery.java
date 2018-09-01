/*
 * Copyright 2009-2018 TMD-Maker Project <https://tmdmaker.osdn.jp/>
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
package jp.sourceforge.tmdmaker.model.subset;

import java.util.ArrayList;
import java.util.List;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.ModelQuery;
import jp.sourceforge.tmdmaker.model.SubsetEntity;
import jp.sourceforge.tmdmaker.model.parts.ModelName;

/**
 * サブセットを検索するクラス.
 * 
 * @author nakag
 *
 */
public class SubsetQuery implements ModelQuery<SubsetEntity> {

	private AbstractEntityModel parent;

	/**
	 * コンストラクタは公開しない.
	 * 
	 * @param parent
	 *            サブセットのスーパークラス
	 */
	protected SubsetQuery(AbstractEntityModel parent) {
		this.parent = parent;
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.ModelQuery#findByName(jp.sourceforge.tmdmaker.model.parts.ModelName)
	 */
	@Override
	public List<SubsetEntity> findByName(ModelName name) {
		List<SubsetEntity> subsetList = parent.subsets().all();
		List<SubsetEntity> results = new ArrayList<SubsetEntity>();
		for (SubsetEntity s : subsetList) {
			if (name.getValue().equals(s.getName())) {
				results.add(s);
			}
		}
		return results;
	}
}
