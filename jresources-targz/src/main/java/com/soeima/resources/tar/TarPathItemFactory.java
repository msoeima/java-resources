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
import com.soeima.resources.extensions.annotations.ResourceExtension;
import com.soeima.resources.util.Strings;

/**
 * A {@link PathItem} plugin that handles both <tt>tar</tt> and <tt>g<ip</tt> archives.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2012/10/01
 */
@ResourceExtension(
                   description = "Loads resources from tar archives.",
                   displayName = "tar Resource Loader",
                   name = "jresources-targz"
                  )
public class TarPathItemFactory implements PathItemFactory {

    /**
     * Creates a new {@link TarPathItemFactory} object.
     */
    public TarPathItemFactory() {
    }

    /**
     * @see  PathItemFactory#pathItem(String)
     */
    @Override public PathItem pathItem(String path) {

        if (path.startsWith("tar:")) {
            return new TarPathItem(Strings.substringBetween(path, ":/", "!/"));
        }

        if (path.endsWith(".tar")) {
            return new TarPathItem(path);
        }

        return null;
    }
} // end class TarPathItemFactory
