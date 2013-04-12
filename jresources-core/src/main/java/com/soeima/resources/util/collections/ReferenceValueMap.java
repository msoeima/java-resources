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

package com.soeima.resources.util.collections;

import com.google.common.collect.MapMaker;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/**
 * Implements a {@link ConcurrentMap} that stores keys as strong references and values as either weak or soft
 * references.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2013/04/12
 */
public class ReferenceValueMap<K, V> implements ConcurrentMap<K, V> {

    /**
     * Specifies the reference type used to store the value.
     *
     * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
     * @version  $Revision$, 2013/04/12
     */
    public enum ReferenceType {

        /** Specifies that the values should be stored as weak references. */
        Weak,

        /** Specifies that the values should be stored as soft references. */
        Soft;
    }

    /** The backing concurrent map. */
    private ConcurrentMap<K, V> map;

    /**
     * Creates a new {@link ReferenceValueMap} object.
     *
     * @param  referenceType  The reference type to use to store values.
     */
    public ReferenceValueMap(ReferenceType referenceType) {
        MapMaker mapMaker = new MapMaker();
        mapMaker = (referenceType == ReferenceType.Weak) ? mapMaker.weakValues() : mapMaker.softValues();
        map = mapMaker.makeMap();
    }

    /**
     * @see  Map#size()
     */
    @Override public int size() {
        return map.size();
    }

    /**
     * @see  Map#isEmpty()
     */
    @Override public boolean isEmpty() {
        return map.isEmpty();
    }

    /**
     * @see  Map#containsKey(Object)
     */
    @Override public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    /**
     * @see  Map#containsValue(Object)
     */
    @Override public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    /**
     * @see  Map#get(Object)
     */
    @Override public V get(Object key) {
        return map.get(key);
    }

    /**
     * @see  Map#put(Object, Object)
     */
    @Override public V put(K key, V value) {
        return map.put(key, value);
    }

    /**
     * @see  Map#remove(Object)
     */
    @Override public V remove(Object key) {
        return map.remove(key);
    }

    /**
     * @see  Map#putAll(Map)
     */
    @Override public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
    }

    /**
     * @see  Map#clear()
     */
    @Override public void clear() {
        map.clear();
    }

    /**
     * @see  Map#keySet()
     */
    @Override public Set<K> keySet() {
        return map.keySet();
    }

    /**
     * @see  Map#values()
     */
    @Override public Collection<V> values() {
        return map.values();
    }

    /**
     * @see  Map#entrySet()
     */
    @Override public Set<java.util.Map.Entry<K, V>> entrySet() {
        return map.entrySet();
    }

    /**
     * @see  ConcurrentMap#putIfAbsent(Object, Object)
     */
    @Override public V putIfAbsent(K key, V value) {
        return map.putIfAbsent(key, value);
    }

    /**
     * @see  ConcurrentMap#remove(Object, Object)
     */
    @Override public boolean remove(Object key, Object value) {
        return map.remove(key, value);
    }

    /**
     * @see  ConcurrentMap#replace(Object, Object, Object)
     */
    @Override public boolean replace(K key, V oldValue, V newValue) {
        return map.replace(key, oldValue, newValue);
    }

    /**
     * @see  ConcurrentMap#replace(Object, Object)
     */
    @Override public V replace(K key, V value) {
        return map.replace(key, value);
    }
} // end class ReferenceValueCache
