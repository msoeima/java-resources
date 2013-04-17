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

package com.soeima.resources.compress;

import com.soeima.resources.archive.cache.Archive;
import com.soeima.resources.archive.cache.ArchiveEntry;
import com.soeima.resources.util.IOUtil;
import com.soeima.resources.util.Paths;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Provides common base functionality for archives that use the <tt>Apache commons-compress</tt> library.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2013/04/11
 */
public abstract class AbstractCompressArchive implements Archive {

    /** The path to the archive. */
    private String path;

    /** The archive stream. */
    private ArchiveInputStream stream;

    /**
     * The backing input stream. This may not necessarily always be valid, although when it is used to fetch the backing
     * archive.
     */
    private InputStream is;

    /**
     * Creates a new {@link AbstractCompressArchive} object.
     *
     * @param  path  The path to the archive file.
     */
    public AbstractCompressArchive(String path) {
        this.path = path;
    }

    /**
     * Creates a new {@link AbstractCompressArchive} object.
     *
     * @param  path  The path to the archive file.
     * @param  is    The backing input stream.
     */
    public AbstractCompressArchive(String path, InputStream is) {
        this.path = path;
        this.is = is;
    }

    /**
     * @see  Closeable#close()
     */
    @Override public void close() throws IOException {
        IOUtil.close(stream);
        stream = null;
    }

    /**
     * @see  Archive#getName()
     */
    @Override public String getName() {

        // XXX: this won't work all the time... will it?
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
     * @see  Archive#isOpen()
     */
    @Override public boolean isOpen() {
        return stream != null;
    }

    /**
     * @see  Archive#open()
     */
    @Override public boolean open() {

        if (isOpen()) {
            return true;
        }

        try {
            stream = newArchiveInputStream((is != null) ? is : new FileInputStream(path));
        }
        catch (FileNotFoundException e) {
            return false;
        }

        return true;
    }

    /**
     * Returns a new archive input stream which wraps the given <code>is</code>.
     *
     * @param   is  The input stream to wrap.
     *
     * @return  A new {@link ArchiveInputStream}.
     */
    protected abstract ArchiveInputStream newArchiveInputStream(InputStream is);

    /**
     * @see  Archive#getEntries()
     */
    @Override public Iterator<ArchiveEntry> getEntries() {
        return new CompressEntryIterator(stream);
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
                stream.getNextEntry();
            }

            return stream.getBytesRead();
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
        IOUtil.close(stream);
        stream = null;

        if (!open()) {
            return false;
        }

        return skip(index) >= 0;
    }

    /**
     * @see  Archive#getInputStream(ArchiveEntry)
     */
    @Override public InputStream getInputStream(ArchiveEntry entry) {
        CompressArchiveEntry archiveEntry = (CompressArchiveEntry)entry;
        setAt(archiveEntry.getIndex());
        return new CompressEntryInputStream(stream);
    }

    /**
     * Implements an iterator that wraps {@link org.apache.commons.compress.archivers.ArchiveEntry} objects into {@link
     * CompressArchiveEntry} objects.
     *
     * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
     * @version  2013/04/12
     */
    private class CompressEntryIterator implements Iterator<ArchiveEntry> {

        /** The backing archive file. */
        private ArchiveInputStream archiveStream;

        /** The backing archive entry. */
        private org.apache.commons.compress.archivers.ArchiveEntry archiveEntry;

        /** The {@link #tarEntry}'s index in the {@link #stream}. */
        private int index;

        /**
         * Creates a new {@link CompressEntryIterator} object.
         *
         * @param  jarStream  The backing archive file.
         */
        public CompressEntryIterator(ArchiveInputStream jarStream) {
            this.archiveStream = jarStream;
        }

        /**
         * @see  Iterator#hasNext()
         */
        @Override public boolean hasNext() {

            if (archiveEntry != null) {
                return true;
            }

            try {
                archiveEntry = archiveStream.getNextEntry();
            }
            catch (IOException e) {
                return false;
            }

            if (archiveEntry != null) {
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

            CompressArchiveEntry entry = new CompressArchiveEntry(archiveStream, archiveEntry, index);
            archiveEntry = null;
            return entry;
        }

        /**
         * @see  Iterator#remove()
         */
        @Override public void remove() {
            throw new UnsupportedOperationException("#remove is not supported.");
        }
    } // end class CompressEntryIterator

    /**
     * Wraps a {@link ArchiveEntry} within an {@link ArchiveEntry}.
     *
     * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
     * @version  2013/04/15
     */
    private class CompressArchiveEntry implements ArchiveEntry {

        /** The backing archive input stream. */
        private ArchiveInputStream archiveStream;

        /** The backing archive entry. */
        private org.apache.commons.compress.archivers.ArchiveEntry archiveEntry;

        /** The {@link #archiveEntry}'s index in the {@link #archiveStream}. */
        private int index;

        /**
         * Creates a new {@link TarResourceArchiveEntry} object.
         *
         * @param  archiveStream  The backing archive input stream.
         * @param  archiveEntry   The backing archive entry.
         * @param  index          The <code>archiveEntry</code>'s index within the <code>tar</code> archive.
         */
        public CompressArchiveEntry(ArchiveInputStream archiveStream,
                                    org.apache.commons.compress.archivers.ArchiveEntry archiveEntry, int index) {
            this.archiveStream = archiveStream;
            this.archiveEntry = archiveEntry;
            this.index = index;
        }

        /**
         * @see  ArchiveEntry#getName()
         */
        @Override public String getName() {
            return archiveEntry.getName();
        }

        /**
         * @see  ArchiveEntry#size()
         */
        @Override public long size() {
            return archiveEntry.getSize();
        }

        /**
         * @see  ArchiveEntry#getInputStream()
         */
        @Override public InputStream getInputStream() {
            return new CompressEntryInputStream(archiveStream);
        }

        /**
         * @see  ArchiveEntry#isDirectory()
         */
        @Override public boolean isDirectory() {
            return archiveEntry.isDirectory();
        }

        /**
         * Returns this archive entry's index within its backing input stream.
         *
         * @return  The archive entry index within its backing input stream.
         */
        public int getIndex() {
            return index;
        }
    } // end class CompressArchiveEntry

    /**
     * Wraps a {@link ArchiveInputStream} stream so that it behaves as though it was a {@link CompressEntryInputStream}.
     *
     * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
     * @version  2013/04/15
     */
    private class CompressEntryInputStream extends InputStream {

        /** The backing archive input stream. */
        private ArchiveInputStream archiveStream;

        /**
         * Creates a new {@link CompressEntryInputStream} object.
         *
         * @param  tarStream  The archive input stream to wrap.
         */
        public CompressEntryInputStream(ArchiveInputStream tarStream) {
            this.archiveStream = tarStream;
        }

        /**
         * @see  InputStream#read()
         */
        @Override public int read() throws IOException {
            return archiveStream.read();
        }
    }
} // end class AbstractCompressArchive
