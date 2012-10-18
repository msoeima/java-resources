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

import com.soeima.resources.AbstractPathItem;
import com.soeima.resources.PathItem;
import com.soeima.resources.RecursionType;
import com.soeima.resources.Resource;
import com.soeima.resources.ResourceException;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

/**
 * A {@link PathItem} for the <code>tar</code> archive format.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2012/10/01
 */
public class TarPathItem extends AbstractPathItem {

    /**
     * Creates a new {@link TarPathItem} object.
     *
     * @param  path  The path to the <tt>tar</tt> file.
     */
    public TarPathItem(String path) {
        super(path);
    }

    /**
     * @see  PathItem#findResourcesForExtension(String, RecursionType)
     */
    @Override public List<Resource> findResourcesForExtension(String extension, RecursionType recursionType) {
        return null;
    }

    /**
     * @see  PathItem#getInputStream(String)
     */
    @Override public InputStream getInputStream(String name) {
        return null;
    }

    /**
     * @see  PathItem#findResources(String, RecursionType, int)
     */
    @Override protected List<Resource> findResources(String name, RecursionType recursionType, int amount) {
        TarArchiveInputStream is = null;

        try {
            is = new TarArchiveInputStream(new FileInputStream(getPath()));
        }
        catch (FileNotFoundException e) {
            throw new ResourceException(e);
        }

        try {

            for (TarArchiveEntry tarEntry = null; tarEntry != null; tarEntry = is.getNextTarEntry()) {
                tarEntry.getName();
            }
        }
        catch (IOException e) {
            throw new ResourceException(e);
        }

        return null;
    }

    /**
     * @see  PathItem#getURI()
     */
    @Override public URI getURI() {
        return null;
    }
} // end class TarPathItem
