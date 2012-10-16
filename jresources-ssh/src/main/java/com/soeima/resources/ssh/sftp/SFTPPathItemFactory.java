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

import com.soeima.resources.PathItem;
import com.soeima.resources.PathItemFactory;
import com.soeima.resources.extensions.annotations.ResourceExtension;

/**
 * Loads resources using the secure file transfer protocol (<tt>SFTP</tt>).
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2012/10/09
 */
@ResourceExtension(
                   description = "Loads resources using the Secure File Transfer Protocol (SFTP).",
                   displayName = "SFTP Resource Extension",
                   name = "jresources-sftp"
                  )
public class SFTPPathItemFactory implements PathItemFactory {

    /**
     * Creates a new {@link SFTPPathItemFactory} object.
     */
    public SFTPPathItemFactory() {
    }

    /**
     * @see  PathItemFactory#pathItem(String)
     */
    @Override public PathItem pathItem(String path) {

        if (path.startsWith("sftp:")) {
            return new SFTPPathItem(path);
        }

        return null;
    }
}
