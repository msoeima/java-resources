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

package com.soeima.resources.file;

import com.soeima.resources.AbstractPathItem;
import com.soeima.resources.PathItem;
import com.soeima.resources.RecursionType;
import com.soeima.resources.Resource;
import com.soeima.resources.util.CollectionUtil;
import com.soeima.resources.util.Paths;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * A {@link PathItem} suitable for working with file-system directories.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2012/09/25
 */
public class FilePathItem extends AbstractPathItem {

    /** The directory. */
    private File directory;

    /**
     * Creates a new {@link FilePathItem} object.
     *
     * @param  directory  The directory.
     */
    public FilePathItem(File directory) {
        super(directory.getAbsolutePath());
        this.directory = directory;
    }

    /**
     * Returns the file to the underlying path.
     *
     * @return  The {@link File} to the underlying path.
     */
    protected File getFile() {
        return directory;
    }

    /**
     * @see  PathItem#findResourcesForExtension(String, RecursionType)
     */
    @Override public List<Resource> findResourcesForExtension(String extension, RecursionType recursionType) {
        return findResources(getPath(), new FileExtensionFilter(extension, recursionType), -1);
    }

    /**
     * @see  AbstractPathItem#findResources(String, RecursionType, int)
     */
    @Override protected List<Resource> findResources(String name, RecursionType recursionType, int amount) {
        return findResources(Paths.join(getPath(), Paths.getParentPath(name)),
                             new FileNameFilter(Paths.getBaseName(name), recursionType),
                             amount);
    }

    /**
     * Returns a list of file resources that match the <code>filter</code> criteria. If <code>amount</code> is a
     * negative value, all matching resources are returned, otherwise only the <code>amount</code> specified is
     * returned.
     *
     * @param   path    The path where the search is to be performed.
     * @param   filter  The filter criteria used to match the resources that are to be returned.
     * @param   amount  The maximum number of resources to return or a negative value indicating all matching resources.
     *
     * @return  A list of {@link Resource}s or an empty list if none are found.
     */
    private List<Resource> findResources(String path, FileFilter filter, int amount) {
        List<Resource> resources = new ArrayList<Resource>();

        for (File file : FileRetriever.getFiles(path, filter)) {
            resources.add(new FileResource(this, Paths.stripParentPath(file.getAbsolutePath(), getPath())));

            if (amount == resources.size()) {
                break;
            }
        }

        return resources;
    }

    /**
     * @see  PathItem#getInputStream(String)
     */
    @Override public InputStream getInputStream(String name) {
        InputStream is = null;

        try {
            is = new FileInputStream(new File(Paths.join(getPath(), name)));
        }
        catch (FileNotFoundException e) {
        }

        return is;
    }

    /**
     * Implements a {@link FilterFilter} suitable for the purposes of the {@link FilePathItemFactory}.
     *
     * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
     * @version  2012/09/29
     */
    private static class FileNameFilter implements FileFilter {

        /** The relative path for which we are searching. */
        private String relativePath;

        /** The type of recursion to use. */
        private RecursionType recursionType;

        /**
         * Creates a new {@link FileFilter} object.
         *
         * @param  relativePath   The relative path for which we are searching.
         * @param  recursionType  The type of recursion to use.
         */
        public FileNameFilter(String relativePath, RecursionType recursionType) {
            this.relativePath = relativePath;
            this.recursionType = recursionType;
        }

        /**
         * @see  FileFilter#accept(File)
         */
        @Override public boolean accept(File file) {

            if (file.isDirectory()) {
                return recursionType == RecursionType.Recursive;
            }

            return relativePath.equals(file.getName());
        }
    } // end class FileNameFilter

    /**
     * Implements a {@link FilterFilter} suitable for the purposes of the {@link FilePathItemFactory}.
     *
     * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
     * @version  2012/09/29
     */
    private static class FileExtensionFilter implements FileFilter {

        /** The file extension. */
        private String extension;

        /** The type of recursion to use. */
        private RecursionType recursionType;

        /**
         * Creates a new {@link FileFilter} object.
         *
         * @param  extension      The file extension.
         * @param  recursionType  The type of recursion to use.
         */
        public FileExtensionFilter(String extension, RecursionType recursionType) {
            this.extension = extension;
            this.recursionType = recursionType;
        }

        /**
         * @see  FileFilter#accept(File)
         */
        @Override public boolean accept(File file) {

            if (file.isDirectory()) {
                return recursionType == RecursionType.Recursive;
            }

            return Paths.isExtension(file.getName(), extension);
        }
    } // end class FileExtensionFilter

    /**
     * Provides a simple mechanism to retrieve files.
     *
     * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
     * @version  2012/09/29
     */
    private static class FileRetriever {

        /**
         * Creates a new {@link FileRetriever} object.
         */
        private FileRetriever() {
        }

        /**
         * Returns all of the files from the given <code>path</code> that match the given <code>filter</code>.
         *
         * <p>If the <code>filter</code> returns directories, these will automatically be recursively searched.</p>
         *
         * @param   path    The path to begin the search.
         * @param   filter  The filter used to return the desired files.
         *
         * @return  A list of files or an empty list if none are found.
         */
        public static List<File> getFiles(String path, FileFilter filter) {
            File dir = new File(path);
            return dir.isDirectory() ? getFiles(dir, filter) : Collections.<File>emptyList();
        }

        /**
         * Returns all of the files from the given <code>path</code> that match the given <code>filter</code>.
         *
         * <p>If the <code>filter</code> returns directories, these will automatically be recursively searched.</p>
         *
         * @param   path    The path to begin the search.
         * @param   filter  The filter used to return the desired files.
         *
         * @return  A list of files or an empty list if none are found.
         */
        public static List<File> getFiles(File path, FileFilter filter) {
            List<File> files = new ArrayList<File>();
            Stack<File> dirs = new Stack<File>();
            dirs.add(path);

            while (!dirs.isEmpty()) {
                File dir = dirs.pop();

                for (File file : CollectionUtil.nonNullCollection(dir.listFiles(filter))) {

                    if (file.isDirectory()) {
                        dirs.add(file);
                    }
                    else {
                        files.add(file);
                    }
                }
            }

            return files;
        } // end method getFiles
    } // end class FileRetriever
} // end class FilePathItem
