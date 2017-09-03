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
package com.antonjohansson.geolocation.provider.ipapi;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.cxf.jaxrs.client.WebClient;

import com.antonjohansson.geolocation.config.Configuration;
import com.antonjohansson.geolocation.framework.Provider;
import com.antonjohansson.geolocation.framework.domain.LookupResult;
import com.antonjohansson.geolocation.http.WebClientFactory;
import com.antonjohansson.geolocation.provider.ipapi.model.LookupRequest;
import com.antonjohansson.geolocation.provider.ipapi.model.LookupResponse;

/**
 * {@link Provider} implementation that utilizes the <a>http://ip-api.com</a> API.
 */
public class IpApiProvider implements Provider
{
    private static final int MAX_BATCH_SIZE = 100;
    private String endpoint = "http://ip-api.com";
    private String key = "";

    public void setEndpoint(String endpoint)
    {
        this.endpoint = endpoint;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    @Override
    public void validate(Configuration configuration)
    {
        if (configuration.getBatchSize() > MAX_BATCH_SIZE)
        {
            throw new RuntimeException("IP API does not allow more than 100 per batch");
        }
    }

    @Override
    public List<LookupResult> lookup(Collection<String> addresses)
    {
        List<LookupRequest> requests = addresses.stream()
                .map(LookupRequest::new)
                .collect(toList());

        WebClient client = WebClientFactory.endpoint(endpoint)
                .json()
                .build()
                .path("/batch");

        if (!isBlank(key))
        {
            client.query("key", key);
        }

        Collection<? extends LookupResponse> responses = client.postAndGetCollection(requests, LookupResponse.class);
        return responses.stream().map(this::toResult).collect(toList());
    }

    private LookupResult toResult(LookupResponse response)
    {
        LookupResult result = new LookupResult();
        result.setIp(response.getAddress());
        result.setLongitude(response.getLongitude());
        result.setLatitude(response.getLatitude());
        return result;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(endpoint, key);
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

        IpApiProvider that = (IpApiProvider) obj;
        return new EqualsBuilder()
                .append(this.endpoint, that.endpoint)
                .append(this.key, that.key)
                .isEquals();
    }

    @Override
    public String toString()
    {
        return reflectionToString(this, SHORT_PREFIX_STYLE);
    }
}
