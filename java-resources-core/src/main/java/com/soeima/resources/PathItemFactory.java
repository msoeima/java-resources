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

/**
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  $Revision$, 2012/09/25
 */
public interface PathItemFactory {

    /**
     * Returns a path item for the given <code>path</code>. The <code>path</code> is guaranteed to be in all lower-case.
     *
     * @param   path  The path for which a new {@link PathItem} is to be returned.
     *
     * @return  A {@link PathItem} or <code>null</code> if this factory cannot create a {@link PathItem} for the given
     *          <code>path</code>.
     */
    PathItem pathItem(String path);
}
