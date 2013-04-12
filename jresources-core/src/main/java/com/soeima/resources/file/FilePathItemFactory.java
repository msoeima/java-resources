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

import com.soeima.resources.PathItem;
import com.soeima.resources.PathItemFactory;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Implements a {@link PathItemFactory} for {@link File}s.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2012/09/25
 */
public class FilePathItemFactory implements PathItemFactory {

    /**
     * Creates a new {@link FilePathItemFactory} object.
     */
    public FilePathItemFactory() {
    }

    /**
     * @see  PathItemFactory#pathItem(String)
     */
    @Override public PathItem pathItem(String path) {
        File file = null;

        try {
            file = path.startsWith("file:") ? new File(new URI(path)) : new File(path);
        }
        catch (URISyntaxException e) {
        }

        if (file.isDirectory()) {

        }

        return file.isDirectory() ? new FilePathItem(file) : null;
    }
} // end class FilePathItemFactory
