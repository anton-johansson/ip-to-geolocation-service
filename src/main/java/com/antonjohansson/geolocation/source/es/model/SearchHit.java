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
package com.antonjohansson.geolocation.source.es.model;

import static java.util.Collections.emptyMap;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Defines a single search hit from Elasticsearch.
 */
public class SearchHit
{
    private @JsonProperty("_index") String index = "";
    private @JsonProperty("_type") String type = "";
    private @JsonProperty("_id") String identifier = "";
    private @JsonProperty("_source") Map<String, Object> source = emptyMap();

    public String getIndex()
    {
        return index;
    }

    public void setIndex(String index)
    {
        this.index = index;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getIdentifier()
    {
        return identifier;
    }

    public void setIdentifier(String identifier)
    {
        this.identifier = identifier;
    }

    public Map<String, Object> getSource()
    {
        return source;
    }

    public void setSource(Map<String, Object> source)
    {
        this.source = source;
    }
}
