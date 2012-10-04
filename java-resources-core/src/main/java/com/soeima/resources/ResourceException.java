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
 *
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2012/09/25
 */
public class ResourceException extends RuntimeException {

    /** The default serial version Id. */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new {@link ResourceException} object.
     */
    public ResourceException() {
    }

    /**
     * Creates a new {@link ResourceException} object.
     *
     * @param  message  The exception message.
     */
    public ResourceException(String message) {
        super(message);
    }

    /**
     * Creates a new {@link ResourceException} object.
     *
     * @param  cause  A chained exception of type {@link Throwable}.
     */
    public ResourceException(Throwable cause) {
        super(cause);
    }
} // end class ResourceException
