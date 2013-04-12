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

import java.io.InputStream;

/**
 * Represents an entry in an {@link Archive}.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  $Revision$, 2013/04/12
 */
public interface ArchiveEntry {

    /**
     * Returns the name of the archive entry.
     *
     * @return  The name of the archive entry.
     */
    String getName();

    /**
     * Returns the size of the entry.
     *
     * @return  The size of the entry.
     */
    long size();

    /**
     * Returns an input stream for the entry.
     *
     * @return  An input stream or <code>null</code> if one cannot be created.
     */
    InputStream getInputStream();

    /**
     * Returns <code>true</code> if this entry represents a directory.
     *
     * @return  <code>true</code> if this entry represents a directory; <code>false</code> otherwise.
     */
    boolean isDirectory();
} // end interface ArchiveEntry
