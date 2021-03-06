/**
 * Copyright (c) Anton Johansson <antoon.johansson@gmail.com>
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
 */
package com.antonjohansson.geolocation.framework;

import java.util.Collection;
import java.util.List;

import com.antonjohansson.geolocation.config.Configuration;
import com.antonjohansson.geolocation.framework.domain.LookupResult;

/**
 * Provides geo-locations for IP addresses.
 */
public interface Provider
{
    /**
     * Validates the configuration for this provider.
     *
     * @param configuration The configuration to validate.
     */
    default void validate(Configuration configuration)
    {
    }

    /**
     * Performs a lookup for a given set of addresses.
     *
     * @param addresses The list of addresses to look up.
     * @return Returns the lookup results.
     */
    List<LookupResult> lookup(Collection<String> addresses);
}
