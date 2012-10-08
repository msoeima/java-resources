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

/**
 * Static convenience methods for working with <tt>Java</tt> reflection.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2012/09/29
 */
public class ReflectionUtil {

    /**
     * Creates a new {@link ReflectionUtil} object.
     */
    private ReflectionUtil() {
    }

    /**
     * Instantiates a new instance of the class represented by the given <code>className</code>.
     *
     * @param   <E>
     * @param   className  The name of the class to instantiate.
     *
     * @return  A new instance of the requested class.
     */
    @SuppressWarnings("unchecked")
    public static <E> E newInstance(String className) {

        if (className != null) {

            try {
                return (E)Class.forName(className).newInstance();
            }
            catch (InstantiationException e) {
            }
            catch (IllegalAccessException e) {
            }
            catch (ClassNotFoundException e) {
            }
        }

        return null;
    }
} // end class ReflectionUtil
