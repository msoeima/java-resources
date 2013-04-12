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

package com.soeima.resources.tar;

import com.soeima.resources.PathItem;
import com.soeima.resources.Resource;
import com.soeima.resources.archive.cache.AbstractArchivePathItem;
import com.soeima.resources.archive.cache.Archive;
import com.soeima.resources.util.Paths;

/**
 * A {@link PathItem} for the <code>tar</code> archive format.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2012/10/01
 */
public class TarPathItem extends AbstractArchivePathItem {

    /**
     * Creates a new {@link TarPathItem} object.
     *
     * @param  path  The path to the <tt>tar</tt> file.
     */
    public TarPathItem(String path) {
        super(path);
    }

    /**
     * @see  AbstractArchivePathItem#newArchive(String)
     */
    @Override public Archive newArchive(String path) {
        return new TarArchive(path);
    }

    /**
     * @see  AbstractArchivePathItem#newResource( String)
     */
    @Override public Resource newResource(String relativePath) {
        return new TarResource(this, relativePath);
    }

    /**
     * @see  AbstractArchivePathItem#toURL(String)
     */
    @Override protected String toURL(String path) {

        if (path.startsWith("tar:")) {
            return path;
        }

        return Paths.normalize("tar:file:/" + path + "!/", '/');
    }
} // end class TarPathItem
