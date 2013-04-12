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
import com.soeima.resources.archive.cache.AbstractArchivePathItem;
import com.soeima.resources.archive.cache.Archive;
import com.soeima.resources.util.Paths;
import java.util.jar.JarFile;

/**
 * Implements a {@link PathItem} for <tt>Jar</tt> and <tt>ZIP</tt> files.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2012/09/25
 */
public class JarPathItem extends AbstractArchivePathItem {

    /** The <tt>URL</tt> used to override one that is created dynamically. */
    private String url;

    /**
     * Creates a new {@link JarPathItem} object.
     *
     * @param  path  The path to the <tt>Jar</tt> or <tt>ZIP</tt> file.
     */
    public JarPathItem(String path) {
        super(path);
    }

    /**
     * Creates a new {@link JarPathItem} object.
     *
     * @param  url  The <tt>URL</tt> to the<tt>Jar</tt> or <tt>ZIP</tt> file.
     * @param  jar  The backing <tt>Jar</tt> file.
     */
    protected JarPathItem(String url, JarFile jar) {
        super(jar.getName());
        this.url = url;
    }

    /**
     * @see  AbstractArchivePathItem#toURL(String)
     */
    @Override protected String toURL(String path) {

        if (url != null) {
            return url;
        }

        return Paths.normalize("jar:file:/" + path + "!/", '/');
    }

    /**
     * @see  AbstractArchivePathItem#newArchive(String)
     */
    @Override public Archive newArchive(String path) {
        return new JarArchive(path);
    }

    /**
     * @see  AbstractArchivePathItem#newResource(String)
     */
    @Override public Resource newResource(String relativePath) {
        return new JarResource(this, relativePath);
    }
} // end class JarPathItem
