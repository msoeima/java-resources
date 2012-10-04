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

import com.soeima.resources.util.IOUtil;
import com.soeima.resources.util.Paths;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * This class creates <tt>zip</tt> archives.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2012/09/30
 */
public class ZipArchiver {

    /** The name of the <tt>Zip</tt> file. */
    private String archivePath;

    /**
     * Creates a new {@link ZipArchiver} object.
     *
     * @param  name  The name of the <tt>Zip</tt> file.
     */
    public ZipArchiver(String name) {
        archivePath = name;
    }

    /**
     * Returns the path to the <tt>Zip</tt> file.
     *
     * @return  The path to the <tt>Zip</tt> file.
     */
    public String getPath() {
        return archivePath;
    }

    /**
     * Recursively archives the given <code>path</code>.
     *
     * @param  path  The path to the directory to archive.
     */
    public void archive(String path) {
        File file = new File(path);
        archive(file, Arrays.asList(file.listFiles()));
    }

    /**
     * Creates a new <tt>Zip</tt> file whose contents are the passed in <code>files</code>.
     *
     * @param  root   The base path of the file to be <tt>zip</tt>ped.
     * @param  files  The files to place in the <tt>zip</tt> file.
     */
    private void archive(File root, List<File> files) {
        ZipOutputStream os = null;

        try {
            os = new ZipOutputStream(new FileOutputStream(archivePath));
            zipEntry(os, root, files);
        }
        catch (IOException e) {
            return;
        }
        finally {
            IOUtil.close(os);
        }
    }

    /**
     * Adds a new <tt>Zip</tt> file entry to the <tt>zip</tt> input stream.
     *
     * <p>The caller is responsible for closing the {@link ZipOutputStream}, <code>zipStream</code>.</p>
     *
     * @param   zipStream  The <tt>zip</tt> input stream to add the new entry.
     * @param   root       The base path.
     * @param   files      The file to add.
     *
     * @throws  IOException  If an I/O error occurs.
     */
    private void zipEntry(ZipOutputStream zipStream, File root, List<File> files) throws IOException {
        byte[] buffer = new byte[8192];

        for (File file : files) {

            if (file.isDirectory()) {
                zipEntry(zipStream, root, Arrays.asList(file.listFiles()));
                continue;
            }

            zipStream.putNextEntry(new ZipEntry(Paths.normalize(Paths.stripParentPath(file.getPath(), root.getPath()),
                                                                '/')));
            FileInputStream is = null;

            try {
                is = new FileInputStream(file);
                int length = 0;

                while ((length = is.read(buffer)) > 0) {
                    zipStream.write(buffer, 0, length);
                }
            }
            finally {
                IOUtil.close(is);
            }
        } // end for
    } // end method zipEntry
} // end class ZipArchiver
