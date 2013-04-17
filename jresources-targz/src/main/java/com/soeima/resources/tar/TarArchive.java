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

package com.soeima.resources.tar;

import com.soeima.resources.archive.cache.Archive;
import com.soeima.resources.compress.AbstractCompressArchive;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import java.io.InputStream;

/**
 * Implements a <tt>tar</tt> {@link Archive}.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2013/04/12
 */
public class TarArchive extends AbstractCompressArchive {

    /**
     * Creates a new {@link TarArchive} object.
     *
     * @param  path  The path to the <tt>tar</tt> archive.
     */
    public TarArchive(String path) {
        super(path);
    }

    /**
     * @see  AbstractCompressArchive#newArchiveInputStream(InputStream)
     */
    @Override protected ArchiveInputStream newArchiveInputStream(InputStream is) {
        return new TarArchiveInputStream(is);
    }
} // end class TarArchive
