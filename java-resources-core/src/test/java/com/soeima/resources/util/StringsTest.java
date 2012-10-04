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
import org.junit.Test;
import java.util.Arrays;
import java.util.Collections;

/**
 * Provides unit tests for the {@link Strings} class.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2012/09/25
 */
public class StringsTest {

    /**
     * Creates a new {@link StringsTest} object.
     */
    public StringsTest() {
    }

    /**
     * Tests the {@link Strings#split(String)} and {@link Strings#split(String, String)} methods.
     */
    @Test public void split() {
        assertEquals(Collections.emptyList(), Strings.split(null, null));
        assertEquals(Collections.emptyList(), Strings.split("", null));
        assertEquals(Collections.emptyList(), Strings.split(null, ","));
        assertEquals(Collections.emptyList(), Strings.split(null));
        assertEquals(Arrays.asList(new String[] {
                                       "a",
                                       "b",
                                       "c"
                                   }), Strings.split("a,b,c"));
        assertEquals(Arrays.asList(new String[] {
                                       "a",
                                       "b",
                                       "c"
                                   }), Strings.split("a, b, c"));
        assertEquals(Arrays.asList(new String[] {
                                       "a",
                                       "b",
                                       "c"
                                   }),
                     Strings.split("a, b | c", ",|"));
        assertEquals(Arrays.asList(new String[] {
                                       "a",
                                       " b"
                                   }), Strings.split("a, b", ",", true));
    }
} // end class StringsTest
