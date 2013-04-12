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

package com.soeima.resources.archive.cache;

import com.soeima.resources.AbstractPathItem;
import com.soeima.resources.PathItem;
import com.soeima.resources.RecursionType;
import com.soeima.resources.Resource;
import com.soeima.resources.ResourceException;
import com.soeima.resources.util.Paths;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Provides an abstract base class for path items that work with {@link Archive}s.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2013/04/15
 */
public abstract class AbstractArchivePathItem extends AbstractPathItem {

    /** The path or <tt>URL</tt> to the <tt>Jar</tt>. */
    private String url;

    /** The backing <tt>Jar</tt> archive cache. */
    private ArchiveCache cache;

    /**
     * Creates a new {@link AbstractArchivePathItem} object.
     *
     * @param  path  The backing path.
     */
    public AbstractArchivePathItem(String path) {
        super(path);
        cache = new ArchiveCache(newArchive(path));
        url = toURL(path);
    }

    /**
     * Creates a new archive for the given <code>path</code>.
     *
     * @param   path  The path to the archive.
     *
     * @return  A new archive for the given <code>path</code>.
     */
    public abstract Archive newArchive(String path);

    /**
     * Converts the given <code>path</code> to a proper <tt>URL</tt> string.
     *
     * @param   path  The path to convert to a proper<tt>URL</tt> string.
     *
     * @return  A proper <tt>URL</tt> string for the given <code>path</code>.
     */
    protected abstract String toURL(String path);

    /**
     * Creates a new {@link Resource} for this path item at the given <code>relativePath</code>.
     *
     * @param   relativePath  The relative path, within the path item, to the resource.
     *
     * @return  A new {@link Resource}.
     */
    public abstract Resource newResource(String relativePath);

    /**
     * @see  PathItem#findResourcesForExtension(String, RecursionType)
     */
    @Override public List<Resource> findResourcesForExtension(final String extension,
                                                              final RecursionType recursionType) {
        return findResources(new ArchiveEntryFilter() {

                /**
                 * @see  ArchiveEntryFilter#accept(String)
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
     * @see  PathItem#getInputStream(String)
     */
    @Override public InputStream getInputStream(String name) {
        return cache.getInputStream(name);
    }

    /**
     * @see  PathItem#getURI()
     */
    @Override public URI getURI() {

        try {
            return new URI(url);
        }
        catch (URISyntaxException e) {
            throw new ResourceException(e);
        }
    }

    /**
     * @see  PathItem#findResources(String, RecursionType)
     */
    @Override protected List<Resource> findResources(final String name, final RecursionType recursionType, int amount) {
        return findResources(new ArchiveEntryFilter() {

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
     * various archive entries. If <code>amount</code> is a negative value, all matching entries are returned, otherwise
     * only the <code>amount</code> specified is returned.
     *
     * @param   filter  The filter criteria used to match the resources that are to be returned.
     * @param   amount  The maximum number of resources to return or a negative value indicating all matching resources.
     *
     * @return  A list of {@link Resource}s or an empty list if none are found.
     */
    private List<Resource> findResources(ArchiveEntryFilter filter, int amount) {
        List<Resource> resources = new ArrayList<Resource>();

        for (Iterator<ArchiveEntry> entryIt = cache.getEntries(); entryIt.hasNext();) {
            ArchiveEntry entry = entryIt.next();

            if (entry.isDirectory()) {
                continue;
            }

            String entryName = entry.getName();

            if (filter.accept(entryName)) {
                resources.add(newResource(entryName));
            }

            if (resources.size() == amount) {
                break;
            }
        }

        return resources;
    } // end method findResources

    /**
     * Instances that implement this interface are used to filter archive entries.
     *
     * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
     * @version  $Revision$, 2012/09/29
     */
    private static interface ArchiveEntryFilter {

        /**
         * Returns <code>true</code> if the given <code>entryName</code> is valid.
         *
         * @param   entryName  The entry name to check.
         *
         * @return  <code>true</code> if the <code>entryName</code> is valid; <code>false</code> otherwise.
         */
        boolean accept(String entryName);
    }
} // end class AbstractArchivePathItem
