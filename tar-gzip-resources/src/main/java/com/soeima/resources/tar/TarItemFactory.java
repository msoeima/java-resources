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
import com.soeima.resources.PathItemFactory;
import com.soeima.resources.annotations.PathItemPlugin;

/**
 * A {@link PathItem} plugin that handles both <tt>tar</tt> and <tt>g<ip</tt> archives.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2012/10/01
 */
@PathItemPlugin(
                description = "Provides a Tar and GZip resource loading cabilities.",
                displayName = "tar.gz Resource Loader",
                name = "targz"
               )
public class TarItemFactory implements PathItemFactory {

    /**
     * Creates a new {@link TarItemFactory} object.
     */
    public TarItemFactory() {
    }

    /**
     * @see  PathItemFactory#pathItem(String)
     */
    @Override public PathItem pathItem(String path) {

        if (path.endsWith(".tar") || path.endsWith(".tar.gz") || path.endsWith(".gz")) {
            return new TarPathItem(path);
        }

        return null;
    }
}
