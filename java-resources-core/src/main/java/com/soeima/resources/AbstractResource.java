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

import java.io.InputStream;

/**
 *
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2012/09/25
 */
public abstract class AbstractResource implements Resource {

    /** The path item that contains this resource. */
    private PathItem pathItem;

    /** The resource name. */
    private String name;

    /**
     * Creates a new {@link AbstractResource} object.
     *
     * @param  pathItem  The path item that contains this resource.
     * @param  name      The resource name.
     */
    public AbstractResource(PathItem pathItem, String name) {
        this.pathItem = pathItem;
        this.name = name;
    }

    /**
     * @see  Resource#getInputStream()
     */
    @Override public InputStream getInputStream() {
        return pathItem.getInputStream(name);
    }

    /**
     * Returns the name of the resource.
     *
     * @return  The name of the resource.
     */
    protected String getName() {
        return name;
    }

    /**
     * Returns the underlying path item.
     *
     * @return  The underlying {@link PathItem}.
     */
    protected PathItem getPathItem() {
        return pathItem;
    }

    /**
     * @see  Object#toString()
     */
    @Override public String toString() {
        return getURI().toString();
    }
} // end class AbstractResource
