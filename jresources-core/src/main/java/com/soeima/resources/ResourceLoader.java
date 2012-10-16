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

package com.soeima.resources;

import java.util.ArrayList;
import java.util.List;

/**
 * The resource loader loads {@link Resource}s.
 *
 * <p>This class is not thread-safe.</p>
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2012/09/25
 */
public class ResourceLoader {

    /** Performs the actual resource search. */
    private ResourceFinder finder;

    /**
     * Creates a new {@link ResourceLoader} object.
     */
    public ResourceLoader() {
        finder = new ResourceFinder();
    }

    /**
     * Adds a path to this resource loader's search paths.
     *
     * @param  path  The path to add.
     */
    public void addPath(String path) {
        finder.addPath(PathItems.newPathItem(path));
    }

    /**
     * Removes a path from this resource loader's search paths.
     *
     * @param  path  The path to remove.
     */
    public void removePath(String path) {
        finder.removePath(PathItems.newPathItem(path));
    }

    /**
     * Sets this resource loader's search paths to <code>paths</code>.
     *
     * @param  paths  The new search paths.
     */
    public void setPaths(List<String> paths) {
        List<PathItem> pathItems = new ArrayList<PathItem>();

        for (String path : paths) {
            pathItems.add(PathItems.newPathItem(path));
        }

        finder.setPaths(pathItems);
    }

    /**
     * Sets the recursion type to use when loading resources.
     *
     * @param  recursionType  The recursion type.
     */
    public void setRecursionType(RecursionType recursionType) {
        finder.setRecursionType(recursionType);
    }

    /**
     * Returns the recursion type used to load resources.
     *
     * <p>By default this method returns {@link RecursionType#NonRecursive}.</p>
     *
     * @return  The recursion type used to load resources.
     */
    public RecursionType getRecursionType() {
        return finder.getRecursionType();
    }

    /**
     * Returns the resource matching the given <code>resourceName</code>.
     *
     * <p>If multiple resources match the given <code>resourceName</code>, only one of the resources is returned.</p>
     *
     * @param   resourceName  The name of the resource to locate.
     *
     * @return  A {@link Resource} for the given <code>resourceName</code> or <code>null</code> if one cannot be found.
     */
    public Resource getResource(String resourceName) {
        return finder.find(resourceName);
    }

    /**
     * Returns all of the resources that share the given <code>resourceName</code>.
     *
     * @param   resourceName  The name of the resources to locate.
     *
     * @return  A list of resources for the given <code>resourceName</code> or an empty list if there aren't any.
     */
    public List<Resource> getResources(String resourceName) {
        return finder.findAll(resourceName);
    }

    /**
     * Returns all of the resources that share the given file <code>extension</code>.
     *
     * @param   extension  The extension.
     *
     * @return  A list of resources for the given <code>extension</code> or an empty list if there aren't any.
     */
    public List<Resource> getResourcesForExtension(String extension) {
        return finder.findForExtension(extension);
    }
} // end class ResourceLoader
