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

import java.math.BigDecimal;
import java.util.List;

import com.antonjohansson.geolocation.framework.domain.SourceData;

/**
 * Defines a source from where to get IP addresses and update geo-location.
 *
 * @param <T> The type of the data that this source handles.
 */
public interface Source<T extends SourceData>
{
    /**
     * Get data to provide with geo-location data.
     *
     * @param batchSize The number of sources to provide.
     * @return Returns the list of data.
     */
    List<T> getData(int batchSize);

    /**
     * Updates a given item with geo-location data.
     *
     * @param data The data that was fetched.
     * @param longitude The longitude coordinate.
     * @param latitude The latitude coordinate.
     */
    void update(T data, BigDecimal longitude, BigDecimal latitude);
}
