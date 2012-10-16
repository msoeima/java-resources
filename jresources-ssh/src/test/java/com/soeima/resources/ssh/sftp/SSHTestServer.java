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

import com.soeima.resources.util.Paths;
import org.apache.sshd.SshServer;
import org.apache.sshd.common.NamedFactory;
import org.apache.sshd.server.Command;
import org.apache.sshd.server.PasswordAuthenticator;
import org.apache.sshd.server.command.ScpCommandFactory;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.session.ServerSession;
import org.apache.sshd.server.sftp.SftpSubsystem.Factory;
import org.apache.sshd.server.shell.ProcessShellFactory;
import java.io.IOException;
import java.util.Collections;

/**
 * Provides a simple <tt>SSH</tt> based on <a href="http://mina.apache.org/sshd/">Apache Mina SSHD</a>.
 *
 * <p>Note that this server is used only for unit test purposes.</p>
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2012/10/16
 */
public class SSHTestServer {

    /** The <code>SSH</code> port. */
    private static int port = 3000 + (int)(Math.random() * ((4500 - 4000) + 1));

    /** The <tt>SSH</tt> server. */
    private SshServer ssh;

    /** user */
    private String user;

    /** password */
    private String password;

    /**
     * Creates a new {@link SSHTestServer} object.
     *
     * @param  root      The server root path.
     * @param  user      The name of the <tt>SSH</tt> user.
     * @param  password  The <tt>SSH</tt> user password.
     */
    public SSHTestServer(String root, String user, String password) {
        this.user = user;
        this.password = password;
        init(root);
    }

    /**
     * Returns the <tt>SSH</tt> server port.
     *
     * @return  The <tt>SSH</tt> server port.
     */
    public int getPort() {
        return port;
    }

    /**
     * Returns the user name.
     *
     * @return  The user name.
     */
    public String getUser() {
        return user;
    }

    /**
     * Returns the password.
     *
     * @return  The password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Initializes the <tt>SSH</tt> server.
     *
     * @param  path  The server root path.
     */
    private void init(String path) {
        ssh = SshServer.setUpDefaultServer();
        ssh.setPort(port);

        ssh.setSubsystemFactories(Collections.<NamedFactory<Command>>singletonList(new Factory()));
        ssh.setKeyPairProvider(new SimpleGeneratorHostKeyProvider(Paths.join(path, "hostkey.ser")));
        ssh.setPasswordAuthenticator(new PasswordAuthenticator() {

                /**
                 * @see  PasswordAuthenticator#authenticate(String, String, ServerSession)
                 */
                @Override public boolean authenticate(String name, String pwd, ServerSession session) {
                    return (user.equals(name) && password.equals(pwd));
                }
            });

        ssh.setShellFactory(new ProcessShellFactory(new String[] {
                                                        "/bin/sh",
                                                        "-i",
                                                        "-l"
                                                    }));
        ssh.setCommandFactory(new ScpCommandFactory());
    } // end method init

    /**
     * Starts the <tt>SSH</tt> server.
     */
    public void start() {

        try {
            ssh.start();
        }
        catch (IOException e) {
        }
    }

    /**
     * Stops the <tt>SSH</tt> server.
     */
    public void stop() {

        try {
            ssh.stop();
        }
        catch (InterruptedException e) {
        }
    }
} // end class SSHTestServer
