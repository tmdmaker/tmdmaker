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
package jp.sourceforge.tmdmaker.model.persistence;

/**
 * シリアライズ時に発生した例外のラッパークラス
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class SerializationException extends RuntimeException {

	/**
	 * コンストラクタ
	 * 
	 * @param t
	 */
	public SerializationException(Throwable t) {
		super(t);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param message
	 */
	public SerializationException(String message) {
		super(message);
	}

}
