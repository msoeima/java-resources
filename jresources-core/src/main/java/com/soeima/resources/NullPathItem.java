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
import java.net.URI;
import java.util.List;

/**
 * Implements a <code>null</code> {@link PathItem}, where all methods simply return <code>null</code>.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2013/04/10
 */
public class NullPathItem implements PathItem {

    /** The unique {@link NullPathItem} instance. */
    public static final NullPathItem PathItem = new NullPathItem();

    /**
     * Creates a new {@link NullPathItem} object.
     */
    private NullPathItem() {
    }

    /**
     * @see  PathItem#findResource(String, RecursionType)
     */
    @Override public Resource findResource(String name, RecursionType recursionType) {
        return null;
    }

    /**
     * @see  PathItem#findResources(String, RecursionType)
     */
    @Override public List<Resource> findResources(String name, RecursionType recursionType) {
        return null;
    }

    /**
     * @see  PathItem#findResourcesForExtension(String, RecursionType)
     */
    @Override public List<Resource> findResourcesForExtension(String extension, RecursionType recursionType) {
        return null;
    }

    /**
     * @see  PathItem#getInputStream(String)
     */
    @Override public InputStream getInputStream(String name) {
        return null;
    }

    /**
     * @see  PathItem#getURI()
     */
    @Override public URI getURI() {
        return null;
    }

    /**
     * @see  PathItem#getPath()
     */
    @Override public String getPath() {
        return null;
    }
} // end class NullPathItem
