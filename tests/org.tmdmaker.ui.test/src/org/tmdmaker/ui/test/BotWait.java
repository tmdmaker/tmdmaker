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
package org.tmdmaker.ui.test;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;

/**
 * Bot操作中のwaitを指定するクラス.
 * 
 * @author nakag
 *
 */
public class BotWait {
	private SWTWorkbenchBot bot = new SWTWorkbenchBot();

	public void waitDefault() {
		bot.sleep(300);
	}

	public void waitFor(long millis) {
		bot.sleep(millis);
	}
}
