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

import java.io.Closeable;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Provides an interface for all archive types. If an extension wishes to use the archive cache, it must implement this
 * interface.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2013/04/11
 */
public interface Archive extends Closeable {

    /**
     * Returns the name of the archive.
     *
     * @return  The name of the archive.
     */
    String getName();

    /**
     * Returns the path to the archive.
     *
     * @return  The path to the archive.
     */
    String getPath();

    /**
     * Returns the number of entries in this archive.
     *
     * @return  The number of entries in this archive or <code>-1</code> if this is unknown.
     */
    int size();

    /**
     * Opens the archive.
     *
     * @return  <code>true</code> if the archive was successfully opened; <code>false</code> otherwise.
     */
    boolean open();

    /**
     * Returns <code>true</code> if the archive is open.
     *
     * @return  <code>true</code> if the archive is open; <code>false</code> otherwise.
     */
    boolean isOpen();

    /**
     * Returns an iterator of the archive entries within this archive.
     *
     * @return  An {@link Iterator} of {@link ArchiveEntry} objects.
     */
    Iterator<ArchiveEntry> getEntries();

    /**
     * Returns an input stream for the given <code>entry</code>.
     *
     * @param   entry  The entry for which an input stream is to be returned.
     *
     * @return  An input stream for the given <code>entry</code> or <code>null</code> if the <code>entry</code> does not
     *          exist.
     */
    InputStream getInputStream(ArchiveEntry entry);
} // end interface Archive
