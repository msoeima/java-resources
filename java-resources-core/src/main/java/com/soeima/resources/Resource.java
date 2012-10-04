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

import java.io.InputStream;
import java.net.URI;

/**
 * The {@link Resource} interface abstracts an actual resource, such as a {@link java.io.File}.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  $Revision$, 2012/09/25
 */
public interface Resource {

    /**
     * Returns an input stream for this resource.
     *
     * <p>It is the caller's responsibility to properly dispose of the input stream after it is no longer required.</p>
     *
     * @return  An {@link InputStream} for this resource.
     */
    InputStream getInputStream();

    /**
     * Returns the path to the resource.
     *
     * @return  The path to the resource.
     */
    String getPath();

    /**
     * Returns the <code>URI</code> to this resource.
     *
     * @return  The {@link URI} to this resource.
     */
    URI getURI();
}
