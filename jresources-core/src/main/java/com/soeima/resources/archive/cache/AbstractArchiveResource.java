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

import com.soeima.resources.AbstractResource;
import com.soeima.resources.PathItem;
import com.soeima.resources.Resource;
import com.soeima.resources.ResourceException;
import com.soeima.resources.util.Paths;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Implements an abstract {@link Resource} class for {@link Archive} resources.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2013/04/16
 */
public abstract class AbstractArchiveResource extends AbstractResource {

    /**
     * Creates a new {@link AbstractArchiveResource} object.
     *
     * @param  pathItem  The path item that contains this resource.
     * @param  name      The resource name.
     */
    public AbstractArchiveResource(PathItem pathItem, String name) {
        super(pathItem, name);
    }

    /**
     * @see  Resource#getPath()
     */
    @Override public String getPath() {
        return Paths.join(getPathItem().getPath(), getName());
    }

    /**
     * @see  Resource#getURI()
     */
    @Override public URI getURI() {

        try {
            return new URI(getPathItem().getURI().toASCIIString() + getName());
        }
        catch (URISyntaxException e) {
            throw new ResourceException(e);
        }
    }
} // end class AbstractArchiveResource
