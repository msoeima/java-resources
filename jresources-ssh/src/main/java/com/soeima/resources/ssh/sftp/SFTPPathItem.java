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

import com.soeima.resources.AbstractPathItem;
import com.soeima.resources.PathItem;
import com.soeima.resources.RecursionType;
import com.soeima.resources.Resource;
import com.soeima.resources.ResourceException;
import com.soeima.resources.ssh.util.SSHUtil;
import com.soeima.resources.util.Paths;
import com.soeima.resources.util.Strings;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.RemoteResourceFilter;
import net.schmizz.sshj.sftp.RemoteResourceInfo;
import net.schmizz.sshj.sftp.SFTPClient;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * A {@link PathItem} suitable for working with the secure file transfer protocol, or <tt>SFTP</tt>.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2012/10/09
 */
public class SFTPPathItem extends AbstractPathItem {

    /** The associated <tt>SFTP URI</tt>. */
    private URI sftpURI;

    /** The <tt>SFTP</tt> username. */
    private String username;

    /** The <tt>SFTP</tt> password. */
    private String password;

    /**
     * Creates a new {@link SFTPPathItem} object.
     *
     * @param   path  The <tt>SFTP</tt> path.
     *
     * @throws  ResourceException  If the <code>path</code> is not valid.
     */
    public SFTPPathItem(String path) {
        super(path);

        try {
            sftpURI = new URI(path);
            String userInfo = sftpURI.getUserInfo();

            if (userInfo != null) {
                List<String> info = Strings.split(userInfo, ":");
                username = info.get(0);
                password = info.get(1);
            }
        }
        catch (URISyntaxException e) {
            throw new ResourceException("Not a valid SFTP URI=" + path);
        }
    }

    /**
     * Connects to the remote <tt>SSH</tt> server.
     *
     * @return  A new <tt>SSH</tt> client.
     *
     * @throws  IOException  If an error occurs.
     */
    private SSHClient connect() throws IOException {
        SSHClient ssh = new SSHClient();
        ssh.connect(sftpURI.getHost(), sftpURI.getPort());
        ssh.authPassword(username, password);
        return ssh;
    }

    /**
     * @see  PathItem#findResourcesForExtension(String, RecursionType)
     */
    @Override public List<Resource> findResourcesForExtension(final String extension,
                                                              final RecursionType recursionType) {
        return findResources(new RemoteResourceFilter() {

                /**
                 * @see  RemoteResourceFilter#accept(RemoteResourceInfo)
                 */
                @Override public boolean accept(RemoteResourceInfo resource) {

                    if (resource.isDirectory()) {
                        return recursionType == RecursionType.Recursive;
                    }

                    return Paths.isExtension(resource.getName(), extension);
                }
            }, -1);
    }

    /**
     * @see  AbstractPathItem#findResources(String, RecursionType, int)
     */
    @Override protected List<Resource> findResources(final String name, final RecursionType recursionType, int amount) {
        return findResources(new RemoteResourceFilter() {

                /**
                 * @see  RemoteResourceFilter#accept(RemoteResourceInfo)
                 */
                @Override public boolean accept(RemoteResourceInfo resource) {

                    if (resource.isDirectory()) {
                        return recursionType == RecursionType.Recursive;
                    }

                    return Paths.endsWithNormalized(resource.getPath(), name);
                }
            }, amount);
    }

    /**
     * @see  PathItem#getInputStream(String)
     */
    @Override public InputStream getInputStream(String name) {
        return null;
    }

    /**
     * Returns all of the resources that match the given <code>filter</code>. The <code>filter</code> is passed the
     * various remote file-system entries. If <code>amount</code> is a negative value, all matching entries are
     * returned, otherwise only the <code>amount</code> specified is returned.
     *
     * @param   filter  The filter criteria used to match the resources that are to be returned.
     * @param   amount  The maximum number of resources to return or a negative value indicating all matching resources.
     *
     * @return  A list of {@link Resource}s or an empty list if none are found.
     *
     * @throws  ResourceException  If an error occurs while fetching the resources.
     */
    private List<Resource> findResources(RemoteResourceFilter filter, int amount) {
        List<Resource> resources = new ArrayList<Resource>();
        SSHClient ssh = null;
        SFTPClient sftp = null;

        try {
            ssh = connect();
            sftp = ssh.newSFTPClient();

            String path = sftpURI.getPath();

            if (path.isEmpty()) {
                path = "/";
            }

            Stack<String> paths = new Stack<String>();
            paths.add(path);

            while (!paths.isEmpty()) {

                for (RemoteResourceInfo resource : sftp.ls(paths.pop(), filter)) {

                    if (resource.isDirectory()) {
                        paths.add(resource.getParent());
                        continue;
                    }

                    resources.add(new SFTPResource(this, resource.getPath()));

                    if (resources.size() == amount) {
                        break;
                    }
                }
            }
        }
        catch (IOException e) {
            throw new ResourceException(e);
        }
        finally {
            SSHUtil.close(ssh);
            SSHUtil.close(sftp);
        }

        return resources;
    } // end method findResources
} // end class SFTPPathItem
