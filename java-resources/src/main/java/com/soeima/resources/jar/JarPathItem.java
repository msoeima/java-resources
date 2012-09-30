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

import com.soeima.resources.AbstractPathItem;
import com.soeima.resources.PathItem;
import com.soeima.resources.RecursionType;
import com.soeima.resources.Resource;
import com.soeima.resources.ResourceException;
import com.soeima.resources.util.CollectionUtil;
import com.soeima.resources.util.Paths;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Implements a {@link PathItem} for <tt>Jar</tt> and <tt>ZIP</tt> files.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2012/09/25
 */
public class JarPathItem extends AbstractPathItem {

    /** The backing <tt>Jar</tt> file. */
    private JarFile jar;

    /**
     * Creates a new {@link JarPathItem} object.
     *
     * @param   path  The path to the <tt>Jar</tt> or <tt>ZIP</tt> file.
     *
     * @throws  ResourceException  If <code>path</code> does not point to a valid <tt>Jar</tt> or <tt>ZIP</tt> file.
     */
    public JarPathItem(String path) {
        super(path);

        try {
            jar = new JarFile(path);
        }
        catch (IOException e) {
            throw new ResourceException(e);
        }
    }

    /**
     * @see  PathItem#getInputStream(String)
     */
    @Override public InputStream getInputStream(String name) {

        try {
            return jar.getInputStream(jar.getEntry(name));
        }
        catch (IOException e) {
            return null;
        }
    }

    /**
     * @see  PathItem#getURI()
     */
    @Override public URI getURI() {
        return new File(getPath()).toURI();
    }

    /**
     * @see  PathItem#findResourcesForExtension(String, RecursionType)
     */
    @Override public List<Resource> findResourcesForExtension(final String extension, RecursionType recursionType) {
        return findResources(new JarEntryFilter() {

                /**
                 * @see  JarEntryFilter#accept(String)
                 */
                @Override public boolean accept(JarEntry jarEntry, String entryName) {
                    return !jarEntry.isDirectory() && Paths.isExtension(entryName, extension);
                }
            }, recursionType, -1);
    }

    /**
     * @see  AbstractPathItem#findResources(String, RecursionType, int)
     */
    @Override protected List<Resource> findResources(final String name, RecursionType recursionType, int amount) {
        return findResources(new JarEntryFilter() {

                /**
                 * @see  JarEntryFilter#accept(String)
                 */
                @Override public boolean accept(JarEntry jarEntry, String entryName) {
                    return !jarEntry.isDirectory() && entryName.equals(name);
                }
            }, recursionType, amount);
    }

    /**
     * Returns all of the resources that match the given <code>filter</code>, The <code>filter</code> is passed entries
     * according to the given <code>recursionType</code>. If <code>amount</code> is a negative value, all matching
     * entries are returned, otherwise only the <code>amount</code> specified is returned.
     *
     * @param   filter         The filter criteria used to match the resources that are to be returned.
     * @param   recursionType  The type of recursion during processing.
     * @param   amount         The maximum number of resources to return or a negative value indicating all matching
     *                         resources.
     *
     * @return  A list of {@link Resource}s or an empty list if none are found.
     */
    private List<Resource> findResources(JarEntryFilter filter, RecursionType recursionType, int amount) {
        List<Resource> resources = new ArrayList<Resource>();

        for (Iterator<JarEntry> entryIt = CollectionUtil.asIterator(jar.entries()); entryIt.hasNext();) {
            JarEntry jarEntry = entryIt.next();
            String entryName =
                (recursionType == RecursionType.Recursive) ? Paths.getBaseName(jarEntry.getName()) : jarEntry.getName();

            if (filter.accept(jarEntry, entryName)) {
                resources.add(new JarResource(this, entryName));
            }

            if (resources.size() == amount) {
                break;
            }
        }

        return resources;
    }

    /**
     * Returns the underlying <tt>Jar</tt> file.
     *
     * @return  The underlying {@link JarFile}.
     */
    protected JarFile getJar() {
        return jar;
    }

    /**
     * Instances that implement this interface are used to filter <tt>Jar</tt> entries.
     *
     * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
     * @version  $Revision$, 2012/09/29
     */
    private static interface JarEntryFilter {

        /**
         * Returns <code>true</code> if the given <code>entryName</code> taken from the passed in<code>jarEntry</code>
         * is valid..
         *
         * <p>Note that the <code>entryName</code> may have been processed before calling this method and therefore may
         * not correspon exactually to the entry name returned by the <code>jarEntry</code>.</p>
         *
         * @param   jarEntry   The <tt>Jar</tt> entry being tested.
         * @param   entryName  The entry name corresponding to the passed in <code>jarEntry</code>.
         *
         * @return  <code>true</code> if this entry is valid; <code>false</code> otherwise.
         */
        boolean accept(JarEntry jarEntry, String entryName);
    }
} // end class JarPathItem
