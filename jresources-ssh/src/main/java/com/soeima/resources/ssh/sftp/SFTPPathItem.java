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
import net.schmizz.sshj.sftp.RemoteFile;
import net.schmizz.sshj.sftp.RemoteResourceFilter;
import net.schmizz.sshj.sftp.RemoteResourceInfo;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
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

    /** The <tt>SFTP</tt> user name. */
    private String username;

    /** The <tt>SFTP</tt> password. */
    private String password;

    /** The root path. */
    private String rootPath;

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
            rootPath = sftpURI.getPath();

            if (rootPath.isEmpty()) {
                rootPath = "/";
            }

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
        ssh.addHostKeyVerifier(new PromiscuousVerifier());
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
                    String path = resource.getPath();

                    if (resource.isDirectory()) {

                        if (recursionType == RecursionType.Recursive) {
                            return true;
                        }

                        return Paths.startsWithNormalized(Paths.getParentPath(name),
                                                          Paths.stripParentPath(path, rootPath));
                    }

                    return Paths.endsWithNormalized(path, name);
                }
            }, amount);
    }

    /**
     * @see  PathItem#getInputStream(String)
     */
    @Override public InputStream getInputStream(String name) {
        SSHClient ssh = null;
        SFTPClient sftp = null;

        try {
            ssh = connect();
            sftp = ssh.newSFTPClient();
            return new RemoteInputStream(Paths.normalize(Paths.join(rootPath, name), '/'), ssh, sftp);
        }
        catch (IOException e) {
        }

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
            Stack<String> paths = new Stack<String>();
            paths.add(rootPath);

            while (!paths.isEmpty()) {

                for (RemoteResourceInfo resource : sftp.ls(paths.pop(), filter)) {

                    if (resource.isDirectory()) {
                        paths.add(resource.getPath());
                        continue;
                    }

                    resources.add(new SFTPResource(this, Paths.stripParentPath(resource.getPath(), rootPath)));

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
            SSHUtil.close(sftp, ssh);
        }

        return resources;
    } // end method findResources

    /**
     * @see  PathItem#getURI()
     */
    @Override public URI getURI() {

        try {
            return new URI(sftpURI.getScheme(),
                           null,
                           sftpURI.getHost(),
                           sftpURI.getPort(),
                           sftpURI.getPath(),
                           sftpURI.getQuery(),
                           sftpURI.getFragment());
        }
        catch (URISyntaxException e) {
            throw new ResourceException(e);
        }
    }

    /**
     * Wraps a {@link RemoteFile} and ensures that it is properly closed when this input stream is also closed.
     *
     * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
     * @version  2012/10/17
     */
    private static class RemoteInputStream extends InputStream {

        /** The remote file. */
        private RemoteFile file;

        /** The remote input stream. */
        private InputStream is;

        /** The <tt>SSH</tt> client. */
        private SSHClient ssh;

        /** THe <tt>SFTP</tt> client. */
        private SFTPClient sftp;

        /**
         * Creates a new RemoteInputStream object.
         *
         * @param  fileName  The name of the file to retrieve via <tt>SFTP</tt>.
         * @param  ssh       The backing <tt>SSH</tt> client.
         * @param  sftp      The backing <tt>SFTP</tt> client.
         */
        public RemoteInputStream(String fileName, SSHClient ssh, SFTPClient sftp) {
            this.ssh = ssh;
            this.sftp = sftp;

            try {
                file = sftp.open(fileName);
                is = file.getInputStream();
            }
            catch (IOException e) {
            }
        }

        /**
         * @see  InputStream#read()
         */
        @Override public int read() throws IOException {
            return is.read();
        }

        /**
         * @see  InputStream#markSupported()
         */
        @Override public boolean markSupported() {
            return is.markSupported();
        }

        /**
         * @see  InputStream#mark(int)
         */
        @Override public void mark(int readLimit) {
            is.mark(readLimit);
        }

        /**
         * @see  InputStream#reset()
         */
        @Override public void reset() throws IOException {
            is.reset();
        }

        /**
         * @see  InputStream#skip(long)
         */
        @Override public long skip(long n) throws IOException {
            return is.skip(n);
        }

        /**
         * @see  InputStream#read()
         */
        @Override public int read(byte[] buffer, int off, int len) throws IOException {
            return is.read(buffer, off, len);
        }

        /**
         * @see  InputStream#close()
         */
        @Override public void close() throws IOException {
            super.close();
            SSHUtil.close(sftp, ssh);
            is.close();
            file.close();
        }
    } // end class RemoteInputStream
} // end class SFTPPathItem
