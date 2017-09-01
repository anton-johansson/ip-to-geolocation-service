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

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Defines a search response from Elasticsearch.
 */
public class SearchResponse
{
    private @JsonProperty("took") int took;
    private @JsonProperty("timed_out") boolean timedOut;
    private @JsonProperty("hits") SearchHits hits = new SearchHits();

    public int getTook()
    {
        return took;
    }

    public void setTook(int took)
    {
        this.took = took;
    }

    public boolean isTimedOut()
    {
        return timedOut;
    }

    public void setTimedOut(boolean timedOut)
    {
        this.timedOut = timedOut;
    }

    public SearchHits getHits()
    {
        return hits;
    }

    public void setHits(SearchHits hits)
    {
        this.hits = hits;
    }
}
