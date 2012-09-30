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

package com.soeima.resources;

import com.soeima.resources.util.Paths;
import com.soeima.resources.util.ReflectionUtil;
import com.soeima.resources.util.Strings;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * A factory class that is responsible for instantiating {@link PathItem}s.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2012/09/25
 */
public class PathItems {

    /** Contains the fully qualified names of the built-in set of factories. */
    private static final String FACTORIES_FILE = "/com/soeima/resources/factories.properties";

    /** Property representing the built-in {@link PathItemFactory} objects. */
    private static final String BUILTIN_FACTORIES_PROPERTY = "resources.builtin.factories";

    /** The factories used to create {@link PathItem}s. */
    private static List<PathItemFactory> factories = new ArrayList<PathItemFactory>();

    static {
        Properties properties = new Properties();

        try {
            properties.load(PathItems.class.getResourceAsStream(FACTORIES_FILE));
        }
        catch (IOException e) {
            // Do nothing.
        }

        for (String factory : Strings.split(properties.getProperty(BUILTIN_FACTORIES_PROPERTY))) {
            factories.add(ReflectionUtil.<PathItemFactory>newInstance(factory));
        }
    }

    /**
     * Creates a new {@link PathItems} object.
     */
    private PathItems() {
    }

    /**
     * Creates an instance of a {@link PathItem} from the given <code>path</code>.
     *
     * @param   path  The path from which a {@link PathItem} is created.
     *
     * @return  A new {@link PathItem}.
     *
     * @throws  ResourceException  If a {@link PathItem} cannot be found for the <code>path</code>.
     */
    public static PathItem newPathItem(String path) {

        if (path == null) {
            throw new ResourceException("The path cannot be null.");
        }

        path = Paths.normalize(path, '/');

        for (PathItemFactory factory : factories) {
            PathItem pathItem = factory.pathItem(path);

            if (pathItem != null) {
                return pathItem;
            }
        }

        // Failed to find a PathItem for the path... throw an exception...
        throw new ResourceException("Unsupported path=" + path);
    }

    /**
     * Adds the given <code>factory</code> to this allocator.
     *
     * @param  factory  The factory to add to this allocator.
     */
    public static void addFactory(PathItemFactory factory) {
        factories.add(factory);
    }
} // end class PathItems
