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
 * A path item represents a specific path and contains one or more resources.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  $Revision$, 2012/09/25
 */
public interface PathItem {

    /**
     * *
     *
     * @param   name
     * @param   recursionType
     *
     * @return  Resource
     */
    Resource findResource(String name, RecursionType recursionType);

    /**
     * *
     *
     * @param   name
     * @param   recursionType
     *
     * @return  List
     */
    List<Resource> findResources(String name, RecursionType recursionType);

    /**
     * *
     *
     * @param   extension
     * @param   recursionType
     *
     * @return  Resource
     */
    List<Resource> findResourcesForExtension(String extension, RecursionType recursionType);

    /**
     * Returns the input stream for the given <code>name</code>.
     *
     * @param   name  The name of the resource whose input stream is to be returned.
     *
     * @return  An {@link InputStream} for the given <code>name</code> or <code>null</code> if one cannot be found.
     */
    InputStream getInputStream(String name);

    /**
     * Returns the <code>URI</code> for this path item.
     *
     * @return  The {@link URI} for this path item.
     */
    URI getURI();
} // end interface PathItem
