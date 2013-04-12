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

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

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

    /**
     * Reads all of the bytes from the input stream, <code>is</code>, and returns an array of all the bytes read.
     *
     * @param   is  the input stream to read.
     *
     * @return  An array of bytes read from <code>is</code>.
     *
     * @throws  IOException  If an I/O error occurs while reading <code>is</code>.
     */
    public static byte[] toByteArray(InputStream is) throws IOException {
        ByteArrayOutputStream os = null;

        try {
            os = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytes = 0;

            while ((bytes = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytes);
            }

            return os.toByteArray();
        }
        finally {
            close(is, os);
        }
    }

    /**
     * Reads all of the bytes from the input stream, <code>is</code>, into a single string.
     *
     * @param   is  the input stream to read.
     *
     * @return  A string as read from <code>is</code>.
     *
     * @throws  IOException  If an I/O error occurs while reading <code>is</code>.
     */
    public static String toString(InputStream is) throws IOException {
        return new String(toByteArray(is));
    }
} // end class IOUtil
