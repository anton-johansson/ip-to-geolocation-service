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

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.ws.rs.core.Response;

import org.apache.commons.lang3.builder.EqualsBuilder;

import com.antonjohansson.geolocation.framework.Source;
import com.antonjohansson.geolocation.http.WebClientFactory;
import com.antonjohansson.geolocation.source.es.UpdateDocumentRequest.UpdateDocumentLocation;
import com.antonjohansson.geolocation.source.es.model.SearchHit;
import com.antonjohansson.geolocation.source.es.model.SearchResponse;

/**
 * {@link Source} implementation that works against an Elasticsearch type.
 */
public class ElasticsearchSource implements Source<Document>
{
    private String endpoint = "";
    private String index = "";
    private String type = "";
    private String addressFieldName = "";
    private String coordinatesFieldName = "";

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

    public void setCoordinatesFieldName(String coordinatesFieldName)
    {
        this.coordinatesFieldName = coordinatesFieldName;
    }

    @Override
    public List<Document> getData(int batchSize)
    {
        Map<String, Object> exists = new HashMap<>();
        exists.put("field", addressFieldName);

        Map<String, Object> notExists = new HashMap<>();
        notExists.put("field", coordinatesFieldName);

        Map<String, Object> must = new HashMap<>();
        must.put("exists", exists);

        Map<String, Object> mustNot = new HashMap<>();
        mustNot.put("exists", notExists);

        List<Map<String, Object>> musts = asList(must);
        List<Map<String, Object>> mustNots = asList(mustNot);

        Map<String, Object> bool = new HashMap<>();
        bool.put("must", musts);
        bool.put("must_not", mustNots);

        Map<String, Object> filter = new HashMap<>();
        filter.put("bool", bool);

        Map<String, Object> body = new HashMap<>();
        body.put("filter", filter);

        SearchResponse response = WebClientFactory.endpoint(endpoint)
                .json()
                .build()
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
        UpdateDocumentLocation location = new UpdateDocumentLocation();
        location.setLongitude(longitude);
        location.setLatitude(latitude);

        Map<String, Object> document = new HashMap<>();
        document.put(coordinatesFieldName, location);

        UpdateDocumentRequest<Map<String, Object>> request = new UpdateDocumentRequest<>();
        request.setDocument(document);

        Response response = WebClientFactory.endpoint(endpoint)
                .json()
                .build()
                .path("/{index}/{type}/{identifier}/_update", data.getIndex(), data.getType(), data.getIdentifier())
                .post(request);

        System.out.println(response.readEntity(String.class));
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(endpoint, index, type);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null || obj.getClass() != getClass())
        {
            return false;
        }
        if (obj == this)
        {
            return true;
        }

        ElasticsearchSource that = (ElasticsearchSource) obj;
        return new EqualsBuilder()
                .append(this.endpoint, that.endpoint)
                .append(this.index, that.index)
                .append(this.type, that.type)
                .append(this.addressFieldName, that.addressFieldName)
                .isEquals();
    }

    @Override
    public String toString()
    {
        return reflectionToString(this, SHORT_PREFIX_STYLE);
    }
}
