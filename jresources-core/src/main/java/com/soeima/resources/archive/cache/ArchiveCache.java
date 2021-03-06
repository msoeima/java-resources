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

import com.soeima.resources.util.IOUtil;
import com.soeima.resources.util.collections.ReferenceValueMap;
import com.soeima.resources.util.collections.ReferenceValueMap.ReferenceType;
import java.io.InputStream;
import java.util.Iterator;
import java.util.concurrent.ConcurrentMap;

/**
 * Implements an archive cache. The archive cache provides a cache for a single archive.
 *
 * <p>The archive cache is used to cache archive entries so that fetching the same entries multiple times does not
 * impose a significant performance penalty.</p>
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2013/04/11
 */
public class ArchiveCache {

    /** The archive being cached. */
    private Archive archive;

    /** Cache the archive entries according to their respective names. */
    private ConcurrentMap<String, ArchiveEntry> cache;

    /**
     * Creates a new {@link ArchiveCache} object.
     *
     * @param  archive  The archive to cache.
     */
    public ArchiveCache(Archive archive) {
        this.archive = archive;
        cache = new ReferenceValueMap<String, ArchiveEntry>(ReferenceType.Soft);
    }

    /**
     * Returns an iterator containing the {@link ArchiveEntry} objects for this cache's associated archive.
     *
     * @return  An iterator of {@link ArchiveEntry} objects.
     */
    public Iterator<ArchiveEntry> getEntries() {

        if (!archive.isOpen()) {
            archive.open();
        }

        synchronized (cache) {

            if (cache.isEmpty()) {

                for (Iterator<ArchiveEntry> entryIt = archive.getEntries(); entryIt.hasNext();) {
                    ArchiveEntry entry = entryIt.next();
                    cache.put(entry.getName(), entry);
                }
            }

            return cache.values().iterator();
        }
    }

    /**
     * Returns an input stream for the given entry <code>name</code>.
     *
     * <p>If the entry already exists in the cache, its input stream is returned. Otherwise, the entry is fetched from
     * the underlying archive and then stored in the cache.</p>
     *
     * @param   entryName  The name of the entry to return.
     *
     * @return  An {@link InputStream} for the given <code>entry</code> name or <code>null</code> if the entry does not
     *          exist or if the backing archive has been closed.
     */
    public InputStream getInputStream(String entryName) {
        ArchiveEntry entry = cache.get(entryName);
        return (archive.isOpen() && (entry != null)) ? archive.getInputStream(entry) : null;
    }

    /**
     * @see  Object#finalize()
     */
    @Override public void finalize() {
        IOUtil.close(archive);
        cache.clear();
    }
} // end class ArchiveCache
