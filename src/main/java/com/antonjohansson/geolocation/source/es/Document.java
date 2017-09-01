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
package com.antonjohansson.geolocation.source.es;

import com.antonjohansson.geolocation.framework.domain.SourceData;

/**
 * Defines an Elasticsearch document.
 */
class Document implements SourceData
{
    private final String address;
    private final String index;
    private final String type;
    private final String identifier;

    Document(String address, String index, String type, String identifier)
    {
        this.address = address;
        this.index = index;
        this.type = type;
        this.identifier = identifier;
    }

    @Override
    public String getAddress()
    {
        return address;
    }

    public String getIndex()
    {
        return index;
    }

    public String getType()
    {
        return type;
    }

    public String getIdentifier()
    {
        return identifier;
    }
}
