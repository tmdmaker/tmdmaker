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
package org.tmdmaker.model.persistence;

import java.io.InputStream;

import org.tmdmaker.core.model.Diagram;

/**
 * TMDのシリアライズクラスのインターフェース
 * 
 * @author nakaG
 * 
 */
public interface Serializer {
	/**
	 * TMDをシリアライズする
	 * 
	 * @param obj
	 *            TMD
	 * @return TMD入力ストリーム
	 */
	InputStream serialize(Diagram obj);

	/**
	 * TMDをデシリアライズする
	 * 
	 * @param in
	 *            TMD入力ストリーム
	 * @return TMD
	 */
	Diagram deserialize(InputStream in);
}
