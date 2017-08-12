/*
 * Copyright 2009-2011 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package jp.sourceforge.tmdmaker.model.persistence;

import java.io.InputStream;

import jp.sourceforge.tmdmaker.model.Diagram;

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
