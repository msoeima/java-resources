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

package com.soeima.resources.jar;

import com.soeima.resources.archive.cache.Archive;
import com.soeima.resources.compress.AbstractCompressArchive;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.jar.JarArchiveInputStream;
import java.io.InputStream;

/**
 * Implements an {@link Archive} for a <tt>Jar</tt>.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2013/04/11
 */
public class JarArchive extends AbstractCompressArchive {

    /**
     * Creates a new {@link JarArchive} object.
     *
     * @param  path  The path to the <tt>Jar</tt> file.
     */
    public JarArchive(String path) {
        super(path);
    }

    /**
     * Creates a new {@link JarArchive} object.
     *
     * @param  path  The path to the <tt>Jar</tt> archive.
     * @param  is    The backing input stream to the <tt>Jar</tt> archive.
     */
    public JarArchive(String path, InputStream is) {
        super(path, is);
    }

    /**
     * @see  AbstractCompressArchive#newArchiveInputStream(InputStream)
     */
    @Override protected ArchiveInputStream newArchiveInputStream(InputStream is) {
        return new JarArchiveInputStream(is);
    }
} // end class JarArchive
