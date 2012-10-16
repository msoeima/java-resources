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

package com.soeima.resources.ssh.util;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import java.io.IOException;

/**
 * Provides static convenience methods for working with <tt>SSH</tt> sessions.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2012/10/16
 */
public class SSHUtil {

    /**
     * Creates a new {@link SSHUtil} object.
     */
    private SSHUtil() {
    }

    /**
     * Silently closes the <code>ssh</code> session.
     *
     * @param  ssh  The <tt>SSH</tt> session to close.
     */
    public static void close(SSHClient ssh) {

        try {
            ssh.close();
        }
        catch (IOException e) {
            // Do nothing.
        }
    }

    /**
     * Silently closes the <code>sftp</code> session.
     *
     * @param  sftp  The<tt>SFTP</tt> session to close.
     */
    public static void close(SFTPClient sftp) {

        try {
            sftp.close();
        }
        catch (IOException e) {
            // Do nothing.
        }
    }
} // end class SSHUtil
