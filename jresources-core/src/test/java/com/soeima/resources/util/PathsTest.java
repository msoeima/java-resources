/*
 * Copyright 2012 Marco Soeima
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
 *
 */

package com.soeima.resources.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Provides unit tests for the {@link Paths} class.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2012/10/18
 */
public class PathsTest {

    /**
     * Creates a new {@link PathsTest} object.
     */
    public PathsTest() {
    }

    /**
     * Tests the {@link Paths#leadingSlash(String)} and {@link Paths#leadingSlash(String, char)} methods.
     */
    @Test public void leadingSlash() {
        assertNull(Paths.leadingSlash(null));
        assertEquals("/", Paths.leadingSlash("", '/'));
    }

    /**
     * Tests the {@link Paths#equals(Object)} method.
     */
    @Test public void equals() {
        assertFalse(Paths.equals(null, ""));
        assertFalse(Paths.equals("", null));
        assertTrue(Paths.equals(null, null));
        assertTrue(Paths.equals("/", "\\"));
        assertFalse(Paths.equals("/dir", "\\dir/"));
        assertTrue(Paths.equals("/dir\\", "\\dir/"));
        assertFalse(Paths.equals("/dir\\", "dir/"));
        assertFalse(Paths.equals("/dir\\a", "dir/"));
        assertTrue(Paths.equals("/dir\\a", "/dir/a"));
    }
} // end class PathsTest
