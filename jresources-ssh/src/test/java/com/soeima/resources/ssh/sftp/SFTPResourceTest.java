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

package com.soeima.resources.ssh.sftp;

import com.soeima.resources.AbstractResourceTest;
import com.soeima.resources.util.Paths;
import com.soeima.resources.util.Strings;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * Provides unit tests for the {@link SFTPResource}.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2012/10/16
 */
public class SFTPResourceTest extends AbstractResourceTest {

    /** The <tt>SSH</tt> test server. */
    private static SSHTestServer sshServer;

    /**
     * Creates a new {@link SFTPResourceTest} object.
     */
    public SFTPResourceTest() {
        super();
    }

    /**
     * @see  AbstractResourceTest#setUp()
     */
    @BeforeClass public static void setUp() {
        AbstractResourceTest.setUp("sftp-resource-test");
        sshServer = new SSHTestServer(getTestDirPath(), "test", "test");
        sshServer.start();
    }

    /**
     * @see  AbstractResourceTest#tearDown()
     */
    @AfterClass public static void tearDown() {
        AbstractResourceTest.tearDown();
        sshServer.stop();
    }

    /**
     * @see  AbstractResourceTest#toURL(String)
     */
    @Override protected String toURL(String path) {
        return path;
    }

    /**
     * @see  AbstractResourceTest#toSafeURL(String)
     */
    @Override protected String toSafeURL(String path) {
        return Strings.substringBefore(path, "://") + "://" + Strings.substringAfter(path, "@");
    }

    /**
     * @see  AbstractResourceTest#getResourcePath()
     */
    @Override protected String getResourcePath() {
        StringBuilder sb = new StringBuilder();
        sb.append("sftp://");
        sb.append(sshServer.getUser());
        sb.append(':');
        sb.append(sshServer.getPassword());
        sb.append("@localhost");
        sb.append(':');
        sb.append(sshServer.getPort());
        sb.append(Paths.leadingSlash(getTestDirPath(), '/'));
        return sb.toString();
    }
} // end class SFTPResourceTest
