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

import com.soeima.resources.AbstractResourceTest;
import com.soeima.resources.util.Paths;
import org.junit.BeforeClass;
import java.util.Collections;
import java.util.List;

/**
 * Implements unit tests for the {@link JarResource}.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2012/09/30
 */
public class JarResourceTest extends AbstractResourceTest {

    /** The path to the <tt>Zip</tt> file. */
    private static String zipPath;

    /**
     * Creates a new {@link JarResourceTest} object.
     */
    public JarResourceTest() {
        super();
    }

    /**
     * @see  AbstractResourceTest#setUp(String)
     */
    @BeforeClass public static void setUp() {
        AbstractResourceTest.setUp("jar-resource-test");
        ZipArchiver archiver = new ZipArchiver(Paths.join(getTestDirPath(), "zip-resource-test.zip"));
        archiver.archive(getTestDirPath());
        zipPath = archiver.getPath();
    }

    /**
     * @see  AbstractResourceTest#getProtocolScheme()
     */
    @Override protected String getProtocolScheme() {
        return "jar:/";
    }

    /**
     * @see  AbstractResourceTest#getResourcePaths()
     */
    @Override protected List<String> getResourcePaths() {
        return Collections.singletonList(zipPath);
    }
} // end class JarResourceTest
