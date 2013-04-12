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
import com.soeima.resources.archive.cache.ArchiveEntry;
import com.soeima.resources.util.IOUtil;
import com.soeima.resources.util.Paths;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implements a <tt>tar</tt> {@link Archive}.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2013/04/12
 */
public class TarArchive implements Archive {

    /** The backing <tt>tar</tt> input stream. */
    private TarArchiveInputStream is;

    /** The path to the <tt>tar</tt> archive. */
    private String path;

    /**
     * Creates a new {@link TarArchive} object.
     *
     * @param  path  The path to the <tt>tar</tt> archive.
     */
    public TarArchive(String path) {
        this.path = path;
    }

    /**
     * @see  Closeable#close()
     */
    @Override public void close() throws IOException {
        IOUtil.close(is);
        is = null;
    }

    /**
     * @see  Archive#getName()
     */
    @Override public String getName() {
        return Paths.getBaseName(path);
    }

    /**
     * @see  Archive#getPath()
     */
    @Override public String getPath() {
        return path;
    }

    /**
     * @see  Archive#size()
     */
    @Override public int size() {
        return -1;
    }

    /**
     * Skips ahead <code>count</code> number of entries in the {@link #is}.
     *
     * @param   count  The number of entries to skip.
     *
     * @return  The actual number of bytes skipped or <code>-1</code> if an error occurred..
     */
    private long skip(int count) {

        try {

            while (count-- > 0) {
                is.getNextEntry();
            }

            return is.getBytesRead();
        }
        catch (IOException e) {
            return -1;
        }
    }

    /**
     * Sets the {@link #is} to the entry at the given <code>index</code>.
     *
     * @param   index  The index to set the stream at.
     *
     * @return  <code>true</code> if the {@link #is} was properly reopened; <code>false</code> otherwise.
     */
    private boolean setAt(int index) {
        IOUtil.close(is);
        is = null;

        if (!open()) {
            return false;
        }

        return skip(index) >= 0;
    }

    /**
     * @see  Archive#open()
     */
    @Override public boolean open() {

        if (isOpen()) {
            return true;
        }

        try {
            is = new TarArchiveInputStream(new FileInputStream(getPath()));
        }
        catch (FileNotFoundException e) {
            return false;
        }

        return true;
    }

    /**
     * @see  Archive#isOpen()
     */
    @Override public boolean isOpen() {
        return (is != null);
    }

    /**
     * @see  Archive#getEntries()
     */
    @Override public Iterator<ArchiveEntry> getEntries() {
        return new TarEntryIterator(is);
    }

    /**
     * @see  Archive#getInputStream(ArchiveEntry)
     */
    @Override public InputStream getInputStream(ArchiveEntry entry) {
        TarResourceArchiveEntry tarEntry = (TarResourceArchiveEntry)entry;
        setAt(tarEntry.getIndex());
        return new TarEntryInputStream(is);
    }

    /**
     * Wraps a {@link TarArchiveEntry} within an {@link ArchiveEntry}.
     *
     * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
     * @version  2013/04/15
     */
    private class TarResourceArchiveEntry implements ArchiveEntry {

        /** The backing <tt>tar</tt> input stream. */
        private TarArchiveInputStream tarStream;

        /** The backing <tt>tar</tt> entry. */
        private TarArchiveEntry tarEntry;

        /** The {@link #tarEntry}'s index in the {@link #tarStream}. */
        private int index;

        /**
         * Creates a new {@link TarResourceArchiveEntry} object.
         *
         * @param  tarStream  The backing <tt>tar</tt> input stream.
         * @param  tarEntry   The backing <tt>tar</tt> entry.
         * @param  index      The <code>tarEntry</code>'s index within the <code>tar</code> archive.
         */
        public TarResourceArchiveEntry(TarArchiveInputStream tarStream, TarArchiveEntry tarEntry, int index) {
            this.tarStream = tarStream;
            this.tarEntry = tarEntry;
            this.index = index;
        }

        /**
         * @see  ArchiveEntry#getName()
         */
        @Override public String getName() {
            return tarEntry.getName();
        }

        /**
         * @see  ArchiveEntry#size()
         */
        @Override public long size() {
            return tarEntry.getSize();
        }

        /**
         * @see  ArchiveEntry#getInputStream()
         */
        @Override public InputStream getInputStream() {
            return new TarEntryInputStream(tarStream);
        }

        /**
         * @see  ArchiveEntry#isDirectory()
         */
        @Override public boolean isDirectory() {
            return tarEntry.isDirectory();
        }

        /**
         * Returns this <tt>tar</tt> entry's index within its backing input stream.
         *
         * @return  The <tt>tar</tt> entry index within its backing input stream.
         */
        public int getIndex() {
            return index;
        }
    } // end class TarResourceArchiveEntry

    /**
     * Wraps a {@link TarArchiveInputStream} stream so that it behaves as though it was a {@link TarEntryInputStream}.
     *
     * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
     * @version  2013/04/15
     */
    private class TarEntryInputStream extends InputStream {

        /** The backing <tt>tar</tt> input stream. */
        private TarArchiveInputStream tarStream;

        /**
         * Creates a new {@link TarEntryInputStream} object.
         *
         * @param  tarStream  The <tt>tar</tt> input stream to wrap.
         */
        public TarEntryInputStream(TarArchiveInputStream tarStream) {
            this.tarStream = tarStream;
        }

        /**
         * @see  InputStream#read()
         */
        @Override public int read() throws IOException {
            return tarStream.read();
        }
    }

    /**
     * Implements an {@link Iterator} for <tt>tar</tt> entries.
     *
     * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
     * @version  2013/04/15
     */
    private class TarEntryIterator implements Iterator<ArchiveEntry> {

        /** The backing <tt>tar</tt> input stream. */
        private TarArchiveInputStream tarStream;

        /** The next <tt>tar</tt> entry in the {@link #tarStream}. */
        private TarArchiveEntry tarEntry;

        /** The index of the <tt>tar</tt> entry within the {@link #tarStream}. */
        private int index;

        /**
         * Creates a new {@link TarEntryIterator} object.
         *
         * @param  tarStream  The backing <tt>tar</tt> input stream.
         */
        public TarEntryIterator(TarArchiveInputStream tarStream) {
            this.tarStream = tarStream;
        }

        /**
         * @see  Iterator#hasNext()
         */
        @Override public boolean hasNext() {

            if (tarEntry != null) {
                return true;
            }

            try {
                tarEntry = tarStream.getNextTarEntry();
            }
            catch (IOException e) {
                return false;
            }

            if (tarEntry != null) {
                ++index;
                return true;
            }

            return false;
        }

        /**
         * @see  Iterator#next()
         */
        @Override public ArchiveEntry next() {

            if (!hasNext()) {
                throw new NoSuchElementException("Tar entry does not exist.");
            }

            TarResourceArchiveEntry entry = new TarResourceArchiveEntry(tarStream, tarEntry, index);
            tarEntry = null;
            return entry;
        }

        /**
         * @see  Iterator#remove()
         */
        @Override public void remove() {
            throw new UnsupportedOperationException("#remove is not supported.");
        }
    } // end class TarEntryIterator
} // end class TarArchive
