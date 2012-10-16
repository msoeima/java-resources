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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Provides static convenience methods for manipulating strings.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2012/09/25
 */
public class Strings {

    /**
     * Creates a new Strings object.
     */
    private Strings() {
    }

    /**
     * Splits the given <code>string</code> according by ",".
     *
     * <p>Calling this method is equivalent to calling <code>Strings.split(string, ",")</code>.</p>
     *
     * @param   string  The string to split.
     *
     * @return  A list of strings split according to the "," or an empty list if <code>string</code> is <code>
     *          null</code>.
     */
    public static List<String> split(String string) {
        return split(string, ",", false);
    }

    /**
     * Splits the given <code>string</code> according to the characters defined in the <code>sepChars</code> strings.
     *
     * <p>Calling this method is equivalent to calling <code>Strings.split(string, sepChars, false)</code>.</p>
     *
     * @param   string    The string to split.
     * @param   sepChars  The string containing the separator chars.
     *
     * @return  A list of strings split according to the separator chars or an empty list if either <code>string</code>
     *          or <code>sepChars</code> is <code>null</code>.
     */
    public static List<String> split(String string, String sepChars) {
        return split(string, sepChars, false);
    }

    /**
     * Splits the given <code>string</code> according to the characters defined in the <code>sepChars</code> strings.
     *
     * @param   string      The string to split.
     * @param   sepChars    The string containing the separator chars.
     * @param   preserveWS  If <code>false</code>, whitespace will be removed from the split strings. Trimming white
     *                      space may result in empty strings which will not be added to the result.
     *
     * @return  A list of strings split according to the separator chars or an empty list if either <code>string</code>
     *          or <code>sepChars</code> is <code>null</code>.
     */
    public static List<String> split(String string, String sepChars, boolean preserveWS) {

        if ((string == null) || (sepChars == null)) {
            return Collections.emptyList();
        }

        List<String> strings = new ArrayList<String>();

        for (StringTokenizer st = new StringTokenizer(string, sepChars); st.hasMoreElements();) {
            String s = st.nextToken();

            if (!preserveWS) {
                s = s.trim();
            }

            if (!s.isEmpty()) {
                strings.add(s);
            }
        }

        return strings;
    }

    /**
     * Returns a single where all of the <code>strings</code> are separated by ",".
     *
     * <p>Calling this method is equivalent to calling: <code>Strings.join(strings, ",")</code></p>
     *
     * @param   strings  The strings to join.
     *
     * @return  A single joined string.
     */
    public static String join(List<String> strings) {
        return join(strings, ",");
    }

    /**
     * Returns a single where all of the <code>strings</code> are separated by ",".
     *
     * <p>Calling this method is equivalent to calling: <code>Strings.join(strings, ",")</code></p>
     *
     * @param   strings  The strings to join.
     *
     * @return  A single joined string.
     */
    public static String join(String[] strings) {
        return join(strings, ",");
    }

    /**
     * Returns a single where all of the <code>strings</code> are separated by the given <code>separator</code> string.
     *
     * @param   strings    The strings to join.
     * @param   separator  The separator used to join the strings.
     *
     * @return  A single joined string.
     */
    public static String join(String[] strings, String separator) {
        return join(Arrays.asList(strings), separator);
    }

    /**
     * Returns a single where all of the <code>strings</code> are separated by the given <code>separator</code> string.
     *
     * @param   strings    The strings to join.
     * @param   separator  The separator used to join the strings.
     *
     * @return  A single joined string.
     */
    public static String join(List<String> strings, String separator) {

        if ((strings == null) || (separator == null)) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        String sep = "";

        for (String string : strings) {

            if (string == null) {
                continue;
            }

            sb.append(sep);
            sb.append(string);
            sep = separator;
        }

        return sb.toString();
    }

    /**
     * *
     *
     * @param   string
     * @param   separator
     *
     * @return  String
     */
    public static String substringAfter(String string, String separator) {

        if ((string == null) || (separator == null)) {
            return string;
        }

        int index = string.indexOf(separator);
        return (index >= 0) ? string.substring(index + separator.length()) : string;
    }

    /**
     * *
     *
     * @param   string
     * @param   separator
     *
     * @return  String
     */
    public static String substringBefore(String string, String separator) {

        if ((string == null) || (separator == null)) {
            return string;
        }

        int index = string.indexOf(separator);
        return (index >= 0) ? string.substring(0, index) : string;
    }

    /**
     * *
     *
     * @param   string
     * @param   beginSep
     * @param   endSep
     *
     * @return  String
     */
    public static String substringBetween(String string, String beginSep, String endSep) {
        return substringBefore(substringAfter(string, beginSep), endSep);
    }
} // end class Strings
