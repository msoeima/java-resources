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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Provides static convenience methods for working with <tt>Java Collections</tt>.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2012/09/25
 */
public class CollectionUtil {

    /**
     * Creates a new {@link CollectionUtil} object.
     */
    private CollectionUtil() {
    }

    /**
     * Wraps the given <code>enumeration</code> in an {@link Iterator}.
     *
     * @param   <E>
     * @param   enumeration  The {@link Enumeration} to wrap inside of an {@link Iterator}.
     *
     * @return  An {@link Iterator} for the given <code>enumeration</code>.
     *
     * @throws  UnsupportedOperationException  If the {@link Iterator#remove()} method is invoked.
     */
    public static <E> Iterator<E> asIterator(final Enumeration<E> enumeration) {
        return new Iterator<E>() {

            /**
             * @see  Iterator#hasNext()
             */
            @Override public boolean hasNext() {
                return enumeration.hasMoreElements();
            }

            /**
             * @see  Iterator#next()
             */
            @Override public E next() {
                return enumeration.nextElement();
            }

            /**
             * @see  Iterator#remove()
             */
            @Override public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    /**
     * Checks if <code>collections</code> is <code>null</code> in which case an empty {@link Collection} is returned,
     * otherwise if <code>collection</code> is not <code>null</code> it is returned.
     *
     * @param   <E>
     * @param   collection  The collection to check.
     *
     * @return  An allocated {@link Collection}.
     */
    public static <E> Collection<E> nonNullCollection(Collection<E> collection) {
        return (collection == null) ? Collections.<E>emptyList() : collection;
    }

    /**
     * Checks if <code>array</code> is <code>null</code> in which case an empty {@link Collection} is returned,
     * otherwise if <code>array</code> is not <code>null</code> it is returned.
     *
     * @param   <E>
     * @param   array  The array to check.
     *
     * @return  An allocated {@link Collection}.
     */
    public static <E> Collection<E> nonNullCollection(E[] array) {
        return (array == null) ? Collections.<E>emptyList() : Arrays.asList(array);
    }

    /**
     * Creates and returns a new map from the two-dimensional <code>array</code>.
     *
     * @param   <K>
     * @param   <V>
     * @param   array  The two-dimensional array from which a map is created.
     *
     * @return  A new map.
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> asMap(Object[][] array) {
        Map<K, V> map = new HashMap<K, V>();

        for (int i = 0; i < array.length; ++i) {
            map.put((K)array[i][0], (V)array[i][1]);
        }

        return map;
    }
} // end class CollectionUtil
