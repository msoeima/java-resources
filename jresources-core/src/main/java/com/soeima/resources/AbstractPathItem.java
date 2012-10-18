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

import java.util.List;

/**
 * Implements an abstract {@link PathItem} that is to be used as a base class for all {@link PathItem} types.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2012/09/25
 */
public abstract class AbstractPathItem implements PathItem {

    /** The underlying path. */
    private String path;

    /**
     * Creates a new {@link AbstractPathItem} object.
     *
     * @param  path  The path to abstract.
     */
    public AbstractPathItem(String path) {
        this.path = path;
    }

    /**
     * Returns the underlying path.
     *
     * @return  The underlying path.
     */
    protected String getPath() {
        return path;
    }

    /**
     * @see  PathItem#findResource(String, RecursionType)
     */
    @Override public Resource findResource(String name, RecursionType recursionType) {

        if ((name == null) || name.isEmpty()) {
            return null;
        }

        List<Resource> resources = findResources(name, recursionType, 1);

        if (resources.isEmpty()) {
            return null;
        }

        return resources.get(0);
    }

    /**
     * @see  PathItem#findResources(String, RecursionType)
     */
    @Override public List<Resource> findResources(String name, RecursionType recursionType) {

        if ((name == null) || name.isEmpty()) {
            return null;
        }

        return findResources(name, recursionType, -1);
    }

    /**
     * Returns a list of resources for the given resource <code>name</code>.
     *
     * <p>This method is the primary lookup method for all classes derived from an {@link AbstractPathItem}.</p>
     *
     * @param   name           The name of the resource.
     * @param   recursionType  The recursion type used to find the resources.
     * @param   amount         The maximum amount of resources to return. A negative number indicates all resources that
     *                         match the the criteria.
     *
     * @return  A list of resources or an empty list if none can be found.
     */
    protected abstract List<Resource> findResources(String name, RecursionType recursionType, int amount);
} // end class AbstractPathItem
