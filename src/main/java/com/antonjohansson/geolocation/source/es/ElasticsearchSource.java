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

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyMap;
import static java.util.stream.Collectors.toList;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.jaxrs.client.WebClient;

import com.antonjohansson.geolocation.framework.Source;
import com.antonjohansson.geolocation.source.es.model.SearchHit;
import com.antonjohansson.geolocation.source.es.model.SearchResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

/**
 * {@link Source} implementation that works against an Elasticsearch type.
 */
public class ElasticsearchSource implements Source<Document>
{
    private static final List<?> PROVIDERS = asList(provider());

    private static JacksonJaxbJsonProvider provider()
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);

        JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
        provider.setMapper(mapper);
        return provider;
    }

    private String endpoint = "";
    private String index = "";
    private String type = "";
    private String addressFieldName = "";

    public void setEndpoint(String endpoint)
    {
        this.endpoint = endpoint;
    }

    public void setIndex(String index)
    {
        this.index = index;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public void setAddressFieldName(String addressFieldName)
    {
        this.addressFieldName = addressFieldName;
    }

    @Override
    public List<Document> getData(int batchSize)
    {
        Map<String, Object> query = new HashMap<>();
        query.put("match_all", emptyMap());

        Map<String, Object> body = new HashMap<>();
        body.put("query", query);

        SearchResponse response = WebClient.create(endpoint, PROVIDERS)
                .accept(APPLICATION_JSON_TYPE)
                .type(APPLICATION_JSON_TYPE)
                .path("/{index}/{type}/_search", index, type)
                .query("size", batchSize)
                .post(body, SearchResponse.class);

        return response.getHits()
                .getHits()
                .stream()
                .map(this::toDocument)
                .collect(toList());
    }

    private Document toDocument(SearchHit hit)
    {
        String address = (String) hit.getSource().get(addressFieldName);
        String index = hit.getIndex();
        String type = hit.getType();
        String identifier = hit.getIdentifier();

        return new Document(address, index, type, identifier);
    }

    @Override
    public void update(Document data, BigDecimal longitude, BigDecimal latitude)
    {
    }
}
