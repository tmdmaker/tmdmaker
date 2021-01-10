/*
 * Copyright 2009-2021 TMD-Maker Project <https://www.tmdmaker.org>
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
package org.tmdmaker.core.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Versionのテストクラス
 * 
 * @author nakag
 *
 */
public class VersionTest {
	private Version version;

	@Before
	public void setUp() {
		version = new Version("1.1.1.qualifier");
	}

	@Test
	public void testEqualVersion() {
		assertEquals(true, version.versionUnderEqual(1, 1, 1));
	}

	@Test
	public void testUnderServiceVersion() {
		assertEquals(true, version.versionUnderEqual(1, 1, 2));
	}

	@Test
	public void testUnderMinorVersion() {
		assertEquals(true, version.versionUnderEqual(1, 2, 1));
	}

	@Test
	public void testUnderMajorVersion() {
		assertEquals(true, version.versionUnderEqual(2, 1, 1));
	}

	@Test
	public void testQualifierVersion() {
		assertEquals("qualifier", version.getQualifier());
	}

	@Test
	public void testNonQualifierVersion() {
		version = new Version("2.1.1");
		assertEquals(true, version.versionUnderEqual(2, 1, 1));
		assertEquals("", version.getQualifier());
	}

	@Test
	public void testOverMajorVersion() {
		assertEquals(false, version.versionUnderEqual(0, 1, 1));
	}

	@Test
	public void testOverMinorVersion() {
		assertEquals(false, version.versionUnderEqual(1, 0, 1));
	}

	@Test
	public void testOverServiceVersion() {
		assertEquals(false, version.versionUnderEqual(1, 1, 0));
	}

}
