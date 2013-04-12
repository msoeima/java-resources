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

package com.soeima.resources.archive.cache;

import com.soeima.resources.util.collections.ReferenceValueMap;
import com.soeima.resources.util.collections.ReferenceValueMap.ReferenceType;
import java.util.concurrent.ConcurrentMap;

/**
 * Provides a pool of {@link ArchiveCache}s. The {@link ArchiveCache}s are automatically cleared when they are no longer
 * referenced.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2013/04/12
 */
public class ArchiveCachePool {

    /** The archive cache pool. Each {@link ArchiveCache} is associated to its respective archive path. */
    private static ConcurrentMap<String, ArchiveCache> pool =
        new ReferenceValueMap<String, ArchiveCache>(ReferenceType.Weak);

    /**
     * Creates a new {@link ArchiveCachePool} object.
     */
    private ArchiveCachePool() {
    }

    /**
     * Returns an {@link ArchiveCache} for the given <code>archive</code>.
     *
     * @param   archive  The archive to cache.
     *
     * @return  An {@link ArchiveCache} for the given cache.
     */
    public static ArchiveCache getCache(Archive archive) {

        if (archive == null) {
            return null;
        }

        String path = archive.getPath();
        ArchiveCache cache = pool.get(path);

        if (cache == null) {
            cache = new ArchiveCache(archive);
            pool.putIfAbsent(path, cache);
        }

        return cache;
    }
} // end class ArchiveCachePool
