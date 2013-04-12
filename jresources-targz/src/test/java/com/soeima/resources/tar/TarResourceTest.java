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

import com.soeima.resources.AbstractResourceTest;
import com.soeima.resources.util.Paths;
import org.junit.BeforeClass;

/**
 * Implements unit tests for the {@link TarResource}.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2012/10/05
 */
public class TarResourceTest extends AbstractResourceTest {

    /** The path to the <tt>tar</tt> file. */
    private static String tarPath;

    /**
     * Creates a new {@link TarResourceTest} object.
     */
    public TarResourceTest() {
    }

    /**
     * @see  AbstractResourceTest#setUp(String)
     */
    @BeforeClass public static void setUp() {
        AbstractResourceTest.setUp("tar-resource-test");
        TarArchiver archiver = new TarArchiver();
        archiver.setPath(Paths.join(getTestDirPath(), "tar-resource-test.tar"));
        archiver.archive(getTestDirPath());
        tarPath = archiver.getPath();
    }

    /**
     * @see  AbstractResourceTest#toURL(String)
     */
    @Override protected String toURL(String path) {
        String url =
            Paths.isExtension(path, "tar")
            ? ("tar:file:/" + path + "!/")
            : ("tar:file:/" + Paths.getParentPath(path) + "!/" + Paths.getBaseName(path));
        return Paths.normalize(url, '/');
    }

    /**
     * @see  AbstractResourceTest#getResourcePath()
     */
    @Override protected String getResourcePath() {
        return tarPath;
    }
} // end class TarResourceTest
