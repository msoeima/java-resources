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
import com.soeima.resources.ResourceException;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.jar.JarFile;

/**
 * Implements a {@link PathItem} for <tt>Jar URL</tt>s.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2012/09/30
 */
public class JarURLPathItem extends JarPathItem {

    /**
     * Creates a new {@link JarURLPathItem} object.
     *
     * @param  url  The <tt>Jar URL</tt>.
     */
    public JarURLPathItem(String url) {
        super(url, JarRetriever.getJar(url));
    }

    /**
     * Responsible for opening a <tt>URL</tt> connection and fetching the associated <tt>Jar</tt> file.
     *
     * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
     * @version  2012/09/30
     */
    private static class JarRetriever {

        /**
         * Creates a new {@link JarRetriever} object.
         */
        private JarRetriever() {
        }

        /**
         * Returns the <tt>Jar</tt> file located at the given <code>url</code>.
         *
         * @param   url  The <tt>URL</tt> where the <tt>Jar</tt> file is located.
         *
         * @return  A <tt>Jar</tt> file.
         *
         * @throws  ResourceException  If the <code>url</code> is not valid.
         */
        public static JarFile getJar(String url) {

            try {
                URLConnection connection = new URL(url).openConnection();
                return ((JarURLConnection)connection).getJarFile();
            }
            catch (MalformedURLException e) {
                throw new ResourceException(e);
            }
            catch (IOException e) {
                throw new ResourceException(e);
            }
        }
    } // end class JarRetriever
} // end class JarURLPathItem
