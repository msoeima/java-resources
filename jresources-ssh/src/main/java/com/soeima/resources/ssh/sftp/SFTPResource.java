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

package com.soeima.resources.ssh.sftp;

import com.soeima.resources.AbstractResource;
import com.soeima.resources.PathItem;
import com.soeima.resources.Resource;
import java.net.URI;

/**
 * Implements a {@link Resource} for the secure file transfer protocol, or <tt>SFTP</tt>.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2012/10/16
 */
public class SFTPResource extends AbstractResource {

    /**
     * Creates a new {@link SFTPResource} object.
     *
     * @param  pathItem      The parent {@link PathItem}.
     * @param  relativePath  The resource's relative path.
     */
    public SFTPResource(PathItem pathItem, String relativePath) {
        super(pathItem, relativePath);
    }

    /**
     * @see  Resource#getPath()
     */
    @Override public String getPath() {
        return null;
    }

    /**
     * @see  Resource#getURI()
     */
    @Override public URI getURI() {
        return null;
    }
}
