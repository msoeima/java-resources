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

package com.soeima.resources.file;

import com.soeima.resources.AbstractResourceTest;
import org.junit.BeforeClass;
import java.io.File;

/**
 * Implements unit tests for the {@link FileResource}.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2012/09/25
 */
public class FileResourceTest extends AbstractResourceTest {

    /**
     * Creates a new {@link FileResourceTest} object.
     */
    public FileResourceTest() {
        super();
    }

    /**
     * @see  AbstractResourceTest#setUp(String)
     */
    @BeforeClass public static void setUp() {
        AbstractResourceTest.setUp("file-resource-test");
    }

    /**
     * @see  AbstractResourceTest#toURL(String)
     */
    @Override protected String toURL(String path) {
        return new File(path).toURI().toString();
    }

    /**
     * @see  AbstractResourceTest#getResourcePath()
     */
    @Override protected String getResourcePath() {
        return getTestDirPath();
    }
} // end class FileResourceTest
