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

package com.soeima.resources.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * Provides static convenience methods for working with <tt>I/O</tt>.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2012/09/30
 */
public class IOUtil {

    /**
     * Creates a new {@link IOUtil} object.
     */
    private IOUtil() {
    }

    /**
     * Silently closes the given <code>closeables<s/code>.</code>
     *
     * @param  closeables  The closeable object to close.
     */
    public static void close(Closeable... closeables) {

        for (Closeable closeable : closeables) {

            if (closeable != null) {

                try {
                    closeable.close();
                }
                catch (IOException e) {
                }
            }
        }
    }
} // end class IOUtil
