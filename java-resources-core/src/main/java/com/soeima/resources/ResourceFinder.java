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

import com.soeima.resources.util.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2012/09/25
 */
public class ResourceFinder {

    /** The search paths. */
    private List<PathItem> pathItems;

    /** recursionType */
    private RecursionType recursionType;

    /**
     * Creates a new {@link ResourceFinder} object.
     */
    public ResourceFinder() {
        pathItems = new ArrayList<PathItem>();
        recursionType = RecursionType.NonRecursive;
    }

    /**
     * Sets the recursion type to use when finding resources.
     *
     * @param  recursionType  The recursion type.
     */
    public void setRecursionType(RecursionType recursionType) {
        this.recursionType = recursionType;
    }

    /**
     * Returns the recursion type used to find resources.
     *
     * <p>By default this method returns {@link RecursionType#NonRecursive}.</p>
     *
     * @return  The recursion type used to load resources.
     */
    public RecursionType getRecursionType() {
        return recursionType;
    }

    /**
     * *
     *
     * @param  pathItem
     */
    public void addPath(PathItem pathItem) {
        pathItems.add(pathItem);
    }

    /**
     * *
     *
     * @param  pathItem
     */
    public void removePath(PathItem pathItem) {
        pathItems.remove(pathItem);
    }

    /**
     * Sets the paths
     *
     * @param  pathItems
     */
    public void setPaths(List<PathItem> pathItems) {
        this.pathItems.clear();
        this.pathItems.addAll(pathItems);
    }

    /**
     * Returns a resource for the given <code>name</code>.
     *
     * @param   name  The name of the resource to find.
     *
     * @return  A {@link Resource} for the given <code>name</code> or <code>null</code> if one cannot be found.
     */
    public Resource find(String name) {
        name = Paths.normalize(name, '/');

        for (PathItem pathItem : pathItems) {
            Resource resource = pathItem.findResource(name, recursionType);

            if (resource != null) {
                return resource;
            }
        }

        return null;
    }

    /**
     * *
     *
     * @param   name
     *
     * @return  List
     */
    public List<Resource> findAll(String name) {
        name = Paths.normalize(name, '/');
        List<Resource> resources = new ArrayList<Resource>();

        for (PathItem pathItem : pathItems) {
            resources.addAll(pathItem.findResources(name, recursionType));
        }

        return resources;
    }

    /**
     * *
     *
     * @param   extension
     *
     * @return  List
     */
    public List<Resource> findForExtension(String extension) {
        List<Resource> resources = new ArrayList<Resource>();

        for (PathItem pathItem : pathItems) {
            resources.addAll(pathItem.findResourcesForExtension(extension, recursionType));
        }

        return resources;
    }
} // end class ResourceFinder
