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

package com.soeima.resources;

/**
 * A generic archiver interface. Useful for unit tests since it facilitates the creation of archives.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  $Revision$, 2012/10/05
 */
public interface Archiver {

    /**
     * Sets the path for the archive that is to be created.
     *
     * @param  path  The path to the archive that is to be created..
     */
    void setPath(String path);

    /**
     * Returns the path to the archive file.
     *
     * @return  The path to the archive file.
     */
    String getPath();

    /**
     * Recursively archives the given <code>path</code>.
     *
     * @param  path  The path to the directory to archive.
     */
    void archive(String path);
}
