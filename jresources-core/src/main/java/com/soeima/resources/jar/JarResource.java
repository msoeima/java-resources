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

import com.soeima.resources.PathItem;
import com.soeima.resources.Resource;
import com.soeima.resources.archive.cache.AbstractArchiveResource;

/**
 * Implements a {@link Resource} for a <tt>Jar</tt> or <tt>ZIP</tt> entry.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2012/09/25
 */
public class JarResource extends AbstractArchiveResource {

    /**
     * Creates a new {@link JarResource} object.
     *
     * @param  pathItem  The path item that contains this resource.
     * @param  name      The resource name.
     */
    public JarResource(PathItem pathItem, String name) {
        super(pathItem, name);
    }
} // end class JarResource
