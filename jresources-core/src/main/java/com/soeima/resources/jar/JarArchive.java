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

import com.soeima.resources.ResourceException;
import com.soeima.resources.archive.cache.Archive;
import com.soeima.resources.archive.cache.ArchiveEntry;
import com.soeima.resources.util.Paths;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Implements an {@link Archive} for a <tt>Jar</tt>.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2013/04/11
 */
public class JarArchive implements Archive {

    /** The underlying <tt>Jar</tt> file. */
    private JarFile jar;

    /**
     * Creates a new {@link JarArchive} object.
     *
     * @param   path  The path to the <tt>Jar</tt> file.
     *
     * @throws  ResourceException  If <code>path</code> does not represent a valid <tt>Jar</tt> file.
     */
    public JarArchive(String path) {

        try {
            jar = new JarFile(path);
        }
        catch (IOException e) {
            throw new ResourceException(e);
        }
    }

    /**
     * Creates a new {@link JarArchive} object.
     *
     * @param  jarFile  The backing <tt>Jar</tt> file.
     */
    public JarArchive(JarFile jarFile) {
        jar = jarFile;
    }

    /**
     * @see  Closeable#close()
     */
    @Override public void close() throws IOException {

        if (jar != null) {
            jar.close();
        }

        jar = null;
    }

    /**
     * @see  Archive#getName()
     */
    @Override public String getName() {
        return Paths.getBaseName(jar.getName());
    }

    /**
     * @see  Archive#getPath()
     */
    @Override public String getPath() {
        return jar.getName();
    }

    /**
     * @see  Archive#size()
     */
    @Override public int size() {
        return jar.size();
    }

    /**
     * @see  Archive#isOpen()
     */
    @Override public boolean isOpen() {
        return (jar != null);
    }

    /**
     * @see  Archive#open()
     */
    @Override public boolean open() {
        return isOpen();
    }

    /**
     * @see  Archive#getEntries()
     */
    @Override public Iterator<ArchiveEntry> getEntries() {
        return new JarEntryIterator(jar);
    }

    /**
     * @see  Archive#getInputStream(ArchiveEntry)
     */
    @Override public InputStream getInputStream(ArchiveEntry entry) {
        return entry.getInputStream();
    }

    /**
     * Implements an iterator that wraps {@link JarEntry} objects into {@link JarArchiveEntry} objects.
     *
     * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
     * @version  2013/04/12
     */
    private class JarEntryIterator implements Iterator<ArchiveEntry> {

        /** The backing <tt>Jar</tt> file. */
        private JarFile jar;

        /** The backing {@link Enumeration} of <tt>Jar</tt> entries. */
        private Enumeration<JarEntry> jarEnumeration;

        /**
         * Creates a new {@link JarEntryIterator} object.
         *
         * @param  jarFile  The backing <tt>Jar</tt> file.
         */
        public JarEntryIterator(JarFile jarFile) {
            this.jar = jarFile;
            this.jarEnumeration = jarFile.entries();
        }

        /**
         * @see  Iterator#hasNext()
         */
        @Override public boolean hasNext() {
            return jarEnumeration.hasMoreElements();
        }

        /**
         * @see  Iterator#next()
         */
        @Override public ArchiveEntry next() {
            return new JarArchiveEntry(jar, jarEnumeration.nextElement());
        }

        /**
         * @see  Iterator#remove()
         */
        @Override public void remove() {
            throw new UnsupportedOperationException("#remove is not supported.");
        }
    } // end class JarEntryIterator

    /**
     * Wraps a regular <tt>Jar</tt> entry so that it acts as an {@link ArchiveEntry}.
     *
     * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
     * @version  2013/04/12
     */
    private class JarArchiveEntry implements ArchiveEntry {

        /** The backing <tt>Jar</tt> file. */
        private JarFile jar;

        /** The backing <tt>Jar</tt> entry. */
        private JarEntry jarEntry;

        /**
         * Creates a new {@link JarArchiveEntry} object.
         *
         * @param  jarFile   The backing <tt>Jar</tt> file.
         * @param  jarEntry  The backing <tt>Jar</tt> entry.
         */
        public JarArchiveEntry(JarFile jarFile, JarEntry jarEntry) {
            this.jar = jarFile;
            this.jarEntry = jarEntry;
        }

        /**
         * @see  ArchiveEntry#getName()
         */
        @Override public String getName() {
            return jarEntry.getName();
        }

        /**
         * @see  ArchiveEntry#size()
         */
        @Override public long size() {
            return jarEntry.getSize();
        }

        /**
         * @see  ArchiveEntry#getInputStream()
         */
        @Override public InputStream getInputStream() {

            try {
                return jar.getInputStream(jarEntry);
            }
            catch (IOException e) {
                return null;
            }
        }

        /**
         * @see  ArchiveEntry#isDirectory()
         */
        @Override public boolean isDirectory() {
            return jarEntry.isDirectory();
        }
    } // end class JarArchiveEntry
} // end class JarArchive
