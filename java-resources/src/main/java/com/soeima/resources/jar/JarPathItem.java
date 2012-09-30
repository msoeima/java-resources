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
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
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
     * Creates a new {@link JarPathItem} object.
     *
     * @param  path  The path to the <tt>Jar</tt> or <tt>ZIP</tt> file.
     * @param  jar   The backing <tt>Jar</tt> file.
     */
    protected JarPathItem(String path, JarFile jar) {
        super(path);
        this.jar = jar;
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

        try {
            return new URI(getPath());
        }
        catch (URISyntaxException e) {
            throw new ResourceException(e);
        }
    }

    /**
     * @see  PathItem#findResourcesForExtension(String, RecursionType)
     */
    @Override public List<Resource> findResourcesForExtension(final String extension,
                                                              final RecursionType recursionType) {
        return findResources(new JarEntryFilter() {

                /**
                 * @see  JarEntryFilter#accept(String)
                 */
                @Override public boolean accept(String entryName) {

                    if (recursionType == RecursionType.NonRecursive) {

                        if (!Paths.getParentPath(entryName).isEmpty()) {
                            return false;
                        }
                    }

                    return Paths.isExtension(entryName, extension);
                }
            }, -1);
    }

    /**
     * @see  AbstractPathItem#findResources(String, RecursionType, int)
     */
    @Override protected List<Resource> findResources(final String name, final RecursionType recursionType, int amount) {
        return findResources(new JarEntryFilter() {

                /**
                 * @see  JarEntryFilter#accept(String)
                 */
                @Override public boolean accept(String entryName) {
                    return (recursionType == RecursionType.Recursive) ? Paths.endsWithNormalized(entryName, name)
                                                                      : Paths.equalsNormalized(entryName, name);
                }
            }, amount);
    }

    /**
     * Returns all of the resources that match the given <code>filter</code>. The <code>filter</code> is passed the
     * various <tt>Jar</tt> entries. If <code>amount</code> is a negative value, all matching entries are returned,
     * otherwise only the <code>amount</code> specified is returned.
     *
     * @param   filter  The filter criteria used to match the resources that are to be returned.
     * @param   amount  The maximum number of resources to return or a negative value indicating all matching resources.
     *
     * @return  A list of {@link Resource}s or an empty list if none are found.
     */
    private List<Resource> findResources(JarEntryFilter filter, int amount) {
        List<Resource> resources = new ArrayList<Resource>();

        for (Iterator<JarEntry> entryIt = CollectionUtil.asIterator(jar.entries()); entryIt.hasNext();) {
            JarEntry jarEntry = entryIt.next();

            if (jarEntry.isDirectory()) {
                continue;
            }

            String entryName = jarEntry.getName();

            if (filter.accept(entryName)) {
                resources.add(new JarResource(this, entryName));
            }

            if (resources.size() == amount) {
                break;
            }
        }

        return resources;
    } // end method findResources

    /**
     * Sets the backing <code>jar</code> file.
     *
     * @param  jar  The backing <tt>Jar</tt> file.
     */
    protected void setJar(JarFile jar) {
        this.jar = jar;
    }

    /**
     * Returns the backing <tt>Jar</tt> file.
     *
     * @return  The backing {@link JarFile}.
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
         * Returns <code>true</code> if the given <code>entryName</code> is valid.
         *
         * @param   entryName  The entry name to check.
         *
         * @return  <code>true</code> if the <code>entryName</code> is valid; <code>false</code> otherwise.
         */
        boolean accept(String entryName);
    }
} // end class JarPathItem
