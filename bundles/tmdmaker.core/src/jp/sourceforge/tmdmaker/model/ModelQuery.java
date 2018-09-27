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
package jp.sourceforge.tmdmaker.model;

import java.util.List;

import jp.sourceforge.tmdmaker.model.parts.ModelName;

/**
 * TMモデル検索用インターフェース.
 * 
 * @author nakag
 *
 * @param <T>
 *            検索対象のクラス
 */
public interface ModelQuery<T> {
	/**
	 * 名前を元にモデルを抽出する.
	 * 
	 * @param name
	 *            モデル名
	 * @return
	 */
	List<T> findByName(ModelName name);
}