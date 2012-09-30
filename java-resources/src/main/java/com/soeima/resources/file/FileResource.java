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

package com.soeima.resources.file;

import com.soeima.resources.AbstractResource;
import com.soeima.resources.PathItem;
import com.soeima.resources.Resource;
import com.soeima.resources.util.Paths;
import java.io.File;
import java.net.URI;

/**
 * Implements a {@link Resource} for a {@link File}.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2012/09/25
 */
public class FileResource extends AbstractResource {

    /**
     * Creates a new {@link FileResource} object.
     *
     * @param  pathItem      The path item that contains the resource.
     * @param  relativePath  The relative path to the resource.
     */
    public FileResource(PathItem pathItem, String relativePath) {
        super(pathItem, relativePath);
    }

    /**
     * @see  Resource#getPath()
     */
    @Override public String getPath() {
        File file = ((FilePathItem)getPathItem()).getFile();
        return Paths.normalize(Paths.join(file.getAbsolutePath(), getName()), '/');
    }

    /**
     * @see  Resource#getURI()
     */
    @Override public URI getURI() {
        return new File(getPath()).toURI();
    }
} // end class FileResource
