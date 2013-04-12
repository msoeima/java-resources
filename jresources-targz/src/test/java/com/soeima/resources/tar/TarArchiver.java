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

import com.soeima.resources.Archiver;
import com.soeima.resources.util.IOUtil;
import com.soeima.resources.util.Paths;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class creates <tt>tar</tt> archives.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2012/10/05
 */
public class TarArchiver implements Archiver {

    /** The path to the <tt>tar</tt> archive. */
    private String archivePath;

    /**
     * Creates a new {@link TarArchiver} object.
     */
    public TarArchiver() {
    }

    /**
     * @see  Archiver#setPath(String)
     */
    @Override public void setPath(String path) {
        archivePath = path;
    }

    /**
     * @see  Archiver#getPath()
     */
    @Override public String getPath() {
        return archivePath;
    }

    /**
     * @see  Archiver#archive(String)
     */
    @Override public void archive(String path) {
        archive(path, archivePath);
    }

    /**
     * Creates a <code>tar</code> file at the specified <code>archivePath</code> with the contents of the specified
     * <code>path</code>.
     *
     * @param  path         The path to the directory to archive.
     * @param  archivePath  The path to the archive to create.
     */
    private void archive(String path, String archivePath) {
        TarArchiveOutputStream taos = null;

        try {
            taos = new TarArchiveOutputStream(new BufferedOutputStream(new FileOutputStream(new File(archivePath))));
            taos.setLongFileMode(TarArchiveOutputStream.LONGFILE_GNU);

            for (File file : new File(path).listFiles()) {
                addFile(taos, file.getAbsolutePath(), "");
            }
        }
        catch (IOException e) {
        }
        finally {
            IOUtil.close(taos);
        }
    }

    /**
     * Adds the given <code>path</code> to the output stream, <code>os</code>.
     *
     * @param   taos  The tar file's output stream
     * @param   path  The file system path of the file/directory being added
     * @param   base  The base prefix to for the name of the tar file entry
     *
     * @throws  IOException  If an I/O error occurs.
     */
    private void addFile(TarArchiveOutputStream taos, String path, String base) throws IOException {
        File file = new File(path);
        String entryName = Paths.stripLeadingSlash(Paths.normalize(Paths.join(base, file.getName()), '/'));

        if (file.isFile()) {

            try {
                TarArchiveEntry tarEntry = new TarArchiveEntry(file, entryName);
                taos.putArchiveEntry(tarEntry);
                taos.write(IOUtil.toByteArray(new FileInputStream(file)));
            }
            finally {
                taos.closeArchiveEntry();
            }
        }
        else {
            File[] files = file.listFiles();

            if (files != null) {

                for (File f : files) {
                    addFile(taos, f.getAbsolutePath(), Paths.leadingSlash(entryName, '/'));
                }
            }
        }
    } // end method addFile
} // end class TarArchiver
