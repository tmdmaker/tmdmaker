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
package org.tmdmaker.sphinx

import java.util.ResourceBundle
import java.util.MissingResourceException

class Messages {
	static val BUNDLE_NAME = "org.tmdmaker.sphinx.messages"
	static val RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME)

	def static getString(String key) {
		try {
			RESOURCE_BUNDLE.getString(key)
		} catch (MissingResourceException e) {
			'!' + key + '!'
		}
	}
}
