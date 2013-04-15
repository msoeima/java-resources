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

package com.soeima.resources.extensions;

import com.soeima.resources.PathItemFactory;
import com.soeima.resources.RecursionType;
import com.soeima.resources.Resource;
import com.soeima.resources.ResourceLoader;
import com.soeima.resources.util.IOUtil;
import com.soeima.resources.util.ReflectionUtil;
import com.soeima.resources.util.Strings;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Implements an extension loading mechanism, which is used to load protocol extensions.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2012/10/08
 */
public class ResourceExtensionLoader {

    /** Indicates if the extensions have already been loaded. */
    private boolean loaded;

    /**
     * Creates a new {@link ResourceExtensionLoader} object.
     */
    public ResourceExtensionLoader() {
    }

    /**
     * Loads the extension path item factories, i.e., those which are not built into the core.
     *
     * @return  The list of extension {@link PathItemFactory} objects.
     */
    public List<PathItemFactory> loadExtensions() {
        ResourceLoader rl = new ResourceLoader();
        rl.setRecursionType(RecursionType.Recursive);

        for (String path : Strings.split(System.getProperty("java.class.path"), File.pathSeparator)) {
            rl.addPath(path);
        }

        List<PathItemFactory> factories = new ArrayList<PathItemFactory>();

        for (Resource resource : rl.getResourcesForExtension(ResourceExtensionProperties.Extension)) {
            Properties properties = new Properties();
            InputStream is = null;

            try {
                is = resource.getInputStream();
                properties.load(is);
            }
            catch (IOException e) {
            }
            finally {
                IOUtil.close(is);
            }

            PathItemFactory factory = ReflectionUtil.newInstance(properties.getProperty(ResourceExtensionProperties.FactoryName));

            if (factory != null) {
                factories.add(factory);
            }
        }

        loaded = true;
        return factories;
    } // end method loadExtensions

    /**
     * Returns <code>true</code> if the extensions have already been loaded.
     *
     * @return  <code>true</code> if the extensions have already been loaded; <code>false</code> otherwise.
     */
    public boolean extensionsLoaded() {
        return loaded;
    }
} // end class ResourceExtensionLoader
