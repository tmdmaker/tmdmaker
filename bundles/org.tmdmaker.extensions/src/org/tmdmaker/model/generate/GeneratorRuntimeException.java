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
package org.tmdmaker.model.generate;

/**
 * Generatorで発生した例外用
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class GeneratorRuntimeException extends RuntimeException {

	/**
	 * コンストラクタ
	 * 
	 * @param t
	 *            例外
	 */
	public GeneratorRuntimeException(Throwable t) {
		super(t);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param message
	 *            メッセージ
	 */
	public GeneratorRuntimeException(String message) {
		super(message);
	}
}
