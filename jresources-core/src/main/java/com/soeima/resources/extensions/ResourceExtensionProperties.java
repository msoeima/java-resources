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

/**
 * Provides resource extension properties.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  $Revision$, 2012/10/01
 */
public interface ResourceExtensionProperties {

    /** The file extension. */
    String Extension = ".extension";

    /** The resource extension factory name. */
    String FactoryName = "FactoryName";

    /** The name of the resource extension. */
    String Name = "Name";

    /** The resource extension display name. */
    String DisplayName = "DisplayName";

    /** The resource extension description. */
    String Description = "Description";
}
